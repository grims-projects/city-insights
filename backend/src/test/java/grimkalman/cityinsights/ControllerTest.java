package grimkalman.cityinsights;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import grimkalman.cityinsights.controller.Controller;
import grimkalman.cityinsights.domain.CityInsight;
import grimkalman.cityinsights.repository.Repository;
import grimkalman.cityinsights.service.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ControllerTest {

    private MockMvc mockMvc;

    @Mock
    private Repository repository;

    @Mock
    private Service service;

    @InjectMocks
    private Controller controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void testNewCityInsight() throws Exception {
        // Arrange
        CityInsight insight = new CityInsight("Uppsala", "City Description", "Insight Text", "Image URL", "Wiki URL");
        String insightJson = "{\"cityName\":\"Uppsala\",\"cityDescription\":\"City Description\",\"insightText\":\"Insight Text\",\"imageUrl\":\"Image URL\",\"wikiUrl\":\"Wiki URL\"}";

        when(repository.save(any(CityInsight.class))).thenReturn(insight);

        // Act & Assert
        mockMvc.perform(post("/api/insight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(insightJson))
                .andExpect(status().isOk());

        verify(repository, times(1)).save(any(CityInsight.class));
    }

    @Test
    public void testGetCityInsight() throws Exception {
        // Arrange
        String searchQuery = "Stockholm";
        CityInsight insight = new CityInsight("Stockholm", "Description", "Insight Text", "Image URL", "Wiki URL");

        when(service.getCity(searchQuery)).thenReturn(insight);

        // Act & Assert
        mockMvc.perform(get("/api/insight")
                        .param("city", searchQuery))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(service, times(1)).getCity(searchQuery);
    }

    @Test
    public void testGetCityInsightById() throws Exception {
        String id = "test-id";
        CityInsight insight = new CityInsight("City", "Description", "Insight", "Image URL", "Wiki URL");

        when(repository.findById(id)).thenReturn(Optional.of(insight));

        mockMvc.perform(get("/api/insight/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cityName").value(insight.getCityName()))
                .andExpect(jsonPath("$.cityDescription").value(insight.getCityDescription()))
                .andExpect(jsonPath("$.insightText").value(insight.getInsightText()))
                .andExpect(jsonPath("$.imageUrl").value(insight.getImageUrl()))
                .andExpect(jsonPath("$.wikiUrl").value(insight.getWikiUrl()));

        verify(repository, times(1)).findById(id);
    }

    @Test
    public void testGetCityInsightById_NotFound() throws Exception {
        // Arrange
        String id = "non-existent-id";
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Arrange
        mockMvc.perform(get("/api/insight/{id}", id))
                .andExpect(status().isNotFound());

        verify(repository, times(1)).findById(id);
    }

    @Test
    public void testGetAllCityInsight() throws Exception {
        // Arrange
        List<CityInsight> insights = Arrays.asList(new CityInsight("City1", "Desc1", "Text1", "Image1", "Wiki1"),
                new CityInsight("City2", "Desc2", "Text2", "Image2", "Wiki2"));

        when(repository.findAll()).thenReturn(insights);

        // Act & Assert
        mockMvc.perform(get("/api/insight/city"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(repository, times(1)).findAll();
    }


    @Test
    public void testGetCityFact() throws Exception {
        // Arrange
        String wikiUrl = "http://example.com/wiki/Uppsala";
        String expectedFact = "Fact about Uppsala";

        when(service.getFact(wikiUrl)).thenReturn(expectedFact);

        // Act & Assert
        mockMvc.perform(post("/api/insight/city")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wikiUrl))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedFact));

        verify(service, times(1)).getFact(wikiUrl);
    }
}