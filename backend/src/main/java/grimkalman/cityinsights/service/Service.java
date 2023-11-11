package grimkalman.cityinsights.service;

import grimkalman.cityinsights.domain.CityInsight;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
    private static final int MAX_TEXT_LENGTH = 2000;
    private final String chatGptApiKey;

    public Service(@Value("${chatgpt.api.key}") String chatGptApiKey) {
        this.chatGptApiKey = chatGptApiKey;
    }

    public String getFact(String wikiUrl) throws IOException, JSONException, InterruptedException {
        Document wikiHtml = Jsoup.connect(wikiUrl).get();
        String text = wikiHtml.select(".mw-parser-output").text();
        String truncatedString = text.substring(0, text.length() - (text.length() / 5));
         int length = Math.min(truncatedString.length() / 10, MAX_TEXT_LENGTH);
        String randomizedText = textRandomizer(truncatedString, length);
        return getChatGptResponse(randomizedText);
    }

    private String textRandomizer(String text, int randomizedTextLength) {
        int randomNumber = (int) Math.floor(Math.random() * (text.length() - randomizedTextLength + 1));
        return text.substring(randomNumber, randomNumber + randomizedTextLength);
    }

    private String getChatGptResponse(String randomizedText) throws IOException, JSONException, InterruptedException {
        JSONObject payload = new JSONObject()
                .put("model", MODEL)
                .put("messages", new JSONArray()
                        .put(new JSONObject().put("role", "system").put("content", "Give the most interesting fact in one sentence from the provided text on the form: Hey, did you know...?"))
                        .put(new JSONObject().put("role", "user").put("content", randomizedText))
                );
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(OPENAI_API_URL))
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(AUTHORIZATION, BEARER + chatGptApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body())
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }

    public CityInsight getCity(String searchQuery) throws JSONException, IOException, InterruptedException {
        int pageId = getPageId(searchQuery);
        Document wikiHtml = getPageHtml(pageId);
        return new CityInsight(
                getCityName(wikiHtml),
                getCityDescription(wikiHtml),
                "",
                getImgUrl(wikiHtml),
                getWikiUrl(pageId));
    }

    private String getCityName(Document wikiHtml) {
        Element title = wikiHtml.select(".mw-page-title-main").first();
        return title.text();
    }

    private String getCityDescription(Document wikiHtml) {
        return wikiHtml.select(".mw-parser-output > p").get(1).text().replaceAll("\\[\\d+]|ⓘ","");
    }

    private String getImgUrl(Document wikiHtml) throws IOException {
        Element image = wikiHtml.select(".mw-file-description").first();
        Document wikiMediaHtml = Jsoup.connect(image.absUrl("href")).get();
        return wikiMediaHtml.select(".internal").first().absUrl("href");
    }

    private Document getPageHtml(int pageId) throws IOException {
        return Jsoup.connect(getWikiUrl(pageId)).get();
    }

    private String getWikiUrl(int pageId) {
        return "http://en.wikipedia.org/?curid=" + pageId;
    }

    private int getPageId(String searchQuery) throws IOException, InterruptedException, JSONException {
        URI targetURI = URI.create(WIKI_API_URL + "?action=query&list=search&srsearch=" + searchQuery + "&utf8=&format=json");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body())
                .getJSONObject("query")
                .getJSONArray("search")
                .getJSONObject(0)
                .getInt("pageid");
    }
}
