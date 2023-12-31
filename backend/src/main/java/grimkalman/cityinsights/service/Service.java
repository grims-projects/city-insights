package grimkalman.cityinsights.service;

import grimkalman.cityinsights.domain.CityInsight;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static grimkalman.cityinsights.utils.WikipediaPageUtils.*;

@org.springframework.stereotype.Service
public class Service {

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final String WIKI_API_URL = "https://en.wikipedia.org/w/api.php";
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String MODEL = "gpt-3.5-turbo";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final String chatGptApiKey;

    private static final String PROMPT = "Give the most interesting fact in one sentence from the provided text on the form: Hey, did you know...?";

    public Service(@Value("${chatgpt.api.key}") String chatGptApiKey) {
        this.chatGptApiKey = chatGptApiKey;
    }

    public String getFact(String wikiUrl) throws IOException, JSONException, InterruptedException {
        return getChatGptResponse(
                textRandomizer(
                        extractReadableText(
                                Jsoup.connect(wikiUrl).get())));
    }

    private String textRandomizer(String text) {
        List<String> sentences = splitIntoSentences(text);
        if (sentences.size() < 2) {
            return text;
        }
        Random random = new Random();
        int numberOfSentences = 2 + random.nextInt(Math.min(3, sentences.size() - 1));
        Collections.shuffle(sentences, random);
        return sentences.stream()
                .limit(numberOfSentences)
                .collect(Collectors.joining(" "));
    }

    private List<String> splitIntoSentences(String text) {
        return Arrays.stream(text.split("[.!?]\\s+"))
                .map(String::trim)
                .filter(sentence -> !sentence.isEmpty())
                .collect(Collectors.toList());
    }

    private String getChatGptResponse(String randomizedText) throws JSONException, IOException, InterruptedException {
        return parseResponse(
                HTTP_CLIENT.send(buildHttpRequest(buildPayload(randomizedText)),
                HttpResponse.BodyHandlers.ofString()).body());
    }

    private JSONObject buildPayload(String text) throws JSONException {
        return new JSONObject()
                .put("model", MODEL)
                .put("messages", new JSONArray()
                        .put(new JSONObject().put("role", "system").put("content", PROMPT))
                        .put(new JSONObject().put("role", "user").put("content", text)));
    }

    private HttpRequest buildHttpRequest(JSONObject payload) {
        return HttpRequest.newBuilder()
                .uri(URI.create(OPENAI_API_URL))
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(AUTHORIZATION, BEARER + chatGptApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
    }

    private String parseResponse(String responseBody) throws JSONException {
        return new JSONObject(responseBody)
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }

    public CityInsight getCity(String searchQuery) throws JSONException, IOException, InterruptedException, ExecutionException {
        int pageId = getPageId(searchQuery);
        Document wikiHtml = getPageHtml(pageId);
        CompletableFuture<String> cityNameFuture = CompletableFuture.supplyAsync(() -> getCityName(wikiHtml));
        CompletableFuture<String> cityDescriptionFuture = CompletableFuture.supplyAsync(() -> getCityDescription(wikiHtml));
        CompletableFuture<String> imageUrlFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return getImgUrl(wikiHtml);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        CompletableFuture.allOf(cityNameFuture, cityDescriptionFuture, imageUrlFuture).join();
        return new CityInsight(
                cityNameFuture.get(),
                cityDescriptionFuture.get(),
                "",
                imageUrlFuture.get(),
                getWikiUrl(pageId));
    }

    private int getPageId(String searchQuery) throws IOException, InterruptedException, JSONException {
        return new JSONObject(HTTP_CLIENT.send(HttpRequest.newBuilder()
                .uri(URI.create(WIKI_API_URL + "?action=query&list=search&srsearch=" + searchQuery + "&utf8=&format=json"))
                .GET()
                .build(), HttpResponse.BodyHandlers.ofString()).body())
                .getJSONObject("query")
                .getJSONArray("search")
                .getJSONObject(0)
                .getInt("pageid");
    }
}
