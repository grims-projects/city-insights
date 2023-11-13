package grimkalman.cityinsights;

import grimkalman.cityinsights.utils.WikipediaPageUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class CityInsightsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityInsightsApplication.class, args);
	}
}
