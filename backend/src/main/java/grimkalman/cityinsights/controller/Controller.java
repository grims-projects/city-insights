package grimkalman.cityinsights.controller;

import grimkalman.cityinsights.domain.CityInsight;
import grimkalman.cityinsights.repository.Repository;
import grimkalman.cityinsights.service.Service;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/insight")
@CrossOrigin(origins = "http://localhost:3000")
public class Controller {

    Repository repository;
    Service service;

    public Controller(Repository repository, Service service) {
        this.repository = repository;
        this.service = service;
    }

    @PostMapping
    ResponseEntity<CityInsight> newCityInsight(@RequestBody CityInsight cityInsight) {
        return ResponseEntity.ok(repository.save(cityInsight));
    }


    @GetMapping
    ResponseEntity<CityInsight> getCityInsight(@RequestParam(value="city") String searchQuery) throws IOException, JSONException, InterruptedException {
        return ResponseEntity.ok(service.getCity(searchQuery));
    }

    @GetMapping("/city")
    ResponseEntity<List<CityInsight>> getAllCityInsight() {
        return ResponseEntity.ok((List<CityInsight>) repository.findAll());
    }

    @PostMapping("/city")
    ResponseEntity<String> getCityFact(@RequestBody String wikiUrl) throws IOException, JSONException, InterruptedException {
        return ResponseEntity.ok(service.getFact(wikiUrl));
    }

    @GetMapping("/{id}")
    ResponseEntity<CityInsight> getCityInsightById(@PathVariable String id) {
        return repository.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCityInsight(@PathVariable String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
