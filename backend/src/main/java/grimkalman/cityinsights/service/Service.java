package grimkalman.cityinsights.service;

import grimkalman.cityinsights.domain.CityInsight;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@org.springframework.stereotype.Service
public class Service {

    public CityInsight getCity(String searchQuery) throws JSONException, URISyntaxException, IOException {
        int pageId = getPageId(searchQuery);
        Document wikiHtml = getPageHtml(pageId);
        return new CityInsight(
                getCityName(wikiHtml),
                getCityDescription(wikiHtml),
                "",
                getImgUrl(wikiHtml),
                getWikiUrl(pageId));
    }

    public String getCityName(Document wikiHtml) {
        Element title = wikiHtml.select(".mw-page-title-main").first();
        return title.text();
    }

    public String getCityDescription(Document wikiHtml) {
        return wikiHtml.select(".mw-parser-output > p").get(1).text().replaceAll("\\[\\d+]|ⓘ","");
    }

    public String getImgUrl(Document wikiHtml) throws IOException {
        Element image = wikiHtml.select(".mw-file-description").first();
        Document wikiMediaHtml = Jsoup.connect(image.absUrl("href")).get();
        return wikiMediaHtml.select(".internal").first().absUrl("href");

    }

    public Document getPageHtml(int pageId) throws IOException {
        return Jsoup.connect(getWikiUrl(pageId)).get();
    }

    public String getWikiUrl(int pageId) {
        return "http://en.wikipedia.org/?curid=" + pageId;
    }

    public int getPageId(String searchQuery) throws URISyntaxException, JSONException {
        URI targetURI = new URI("https://en.wikipedia.org/w/api.php?action=query&list=search&srsearch=" + searchQuery + "&utf8=&format=json");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();
        HttpResponse<String> response;
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong with fetching the wiki search result");
        }
        return new JSONObject(response.body())
                .getJSONObject("query")
                .getJSONArray("search")
                .getJSONObject(0)
                .getInt("pageid");
    }
}
