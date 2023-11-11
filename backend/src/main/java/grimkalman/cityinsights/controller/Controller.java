package grimkalman.cityinsights.controller;

import grimkalman.cityinsights.domain.CityInsight;
import grimkalman.cityinsights.repository.Repository;
import grimkalman.cityinsights.service.Service;
import org.apache.el.stream.Stream;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/insight")
public class Controller {

    Repository repository;
    Service service;

    public Controller(Repository repository, Service service) {
        this.repository = repository;
        this.service = service;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    CityInsight newCityInsight(@RequestBody CityInsight cityInsight) {
        return repository.save(cityInsight);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/city")
    ResponseEntity<String> getCityFact(@RequestBody String wikiUrl) throws IOException, JSONException, InterruptedException {
        return ResponseEntity.ok(service.getFact(wikiUrl));
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    ResponseEntity<CityInsight> getCityInsight(@RequestParam(value="city") String searchQuery) throws IOException, JSONException, InterruptedException {
        return ResponseEntity.ok(service.getCity(searchQuery));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/city")
    ResponseEntity<List<CityInsight>> getAllCityInsight() {
        List<CityInsight> cityInsights = new ArrayList<>();
        repository.findAll().forEach(cityInsights::add);
        return ResponseEntity.ok(cityInsights);
    }
    @GetMapping("/{id}")
    CityInsight getCityInsightById(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException(id));
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    void deleteCityInsight(@PathVariable String id) {
        repository.deleteById(id);
    }
}
