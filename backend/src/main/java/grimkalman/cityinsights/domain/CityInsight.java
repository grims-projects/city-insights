package grimkalman.cityinsights.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class CityInsight {

    @Id
    @UuidGenerator
    private String id;

    private String cityName;

    @Column(length = 1000)
    private String cityDescription;
    private String insightText;
    private String imageUrl;
    private String wikiUrl;

    protected CityInsight() {}

    public CityInsight(String cityName,
                       String cityDescription,
                       String insightText,
                       String imageUrl,
                       String wikiUrl) {
        this.cityName = cityName;
        this.cityDescription = cityDescription;
        this.insightText = insightText;
        this.imageUrl = imageUrl;
        this.wikiUrl = wikiUrl;
    }

    public String getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    @Column(length = 1000)
    public String getCityDescription() {
        return cityDescription;
    }

    public String getInsightText() {
        return insightText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }
}
