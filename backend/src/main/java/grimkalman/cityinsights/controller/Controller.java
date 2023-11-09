package grimkalman.cityinsights.controller;

import grimkalman.cityinsights.domain.CityInsight;
import grimkalman.cityinsights.repository.Repository;
import grimkalman.cityinsights.service.Service;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
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

    @PostMapping
    CityInsight newCityInsight(@RequestBody CityInsight cityInsight) {
        return repository.save(cityInsight);
    }

    @GetMapping
    ResponseEntity<CityInsight> getCityInsight(@RequestParam(value="city") String searchQuery) throws IOException, JSONException, URISyntaxException {
        return ResponseEntity.ok(service.getCity(searchQuery));
    }

    @GetMapping("/{id}")
    CityInsight getCityInsightById(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException(id));
    }

    @DeleteMapping("/{id}")
    void deleteCityInsight(@PathVariable String id) {
        repository.deleteById(id);
    }
}
