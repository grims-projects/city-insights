package grimkalman.cityinsights.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WikipediaPageUtils {

    public static String getWikiUrl(int pageId) {
        return "http://en.wikipedia.org/?curid=" + pageId;
    }

    public static Document getPageHtml(int pageId) throws IOException {
        return Jsoup.connect(getWikiUrl(pageId)).get();
    }

    public static String extractReadableText(Document wikiHtml) {
        return wikiHtml.select(".mw-parser-output p")
                .text()
                .replaceAll("\\[\\d+]|ⓘ","");
    }

    public static String getCityName(Document wikiHtml) {
        return wikiHtml.select(".mw-page-title-main")
                .first()
                .text();
    }

    public static String getCityDescription(Document wikiHtml) {
        return wikiHtml.select(".mw-parser-output > p")
                .get(1)
                .text()
                .replaceAll("\\[\\d+]|ⓘ","");
    }

    public static String getImgUrl(Document wikiHtml) throws IOException {
        return getActualImageUrl(getImagePageUrl(wikiHtml));
    }

    private static String getImagePageUrl(Document wikiHtml) {
        return wikiHtml.select(".mw-file-description")
                .first()
                .absUrl("href");
    }

    private static String getActualImageUrl(String imagePageUrl) throws IOException {
        return Jsoup.connect(imagePageUrl)
                .get()
                .select(".internal")
                .first()
                .absUrl("href");
    }
}
