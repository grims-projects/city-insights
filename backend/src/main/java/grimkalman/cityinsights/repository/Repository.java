package grimkalman.cityinsights.repository;

import grimkalman.cityinsights.domain.CityInsight;
import org.springframework.data.repository.CrudRepository;

@org.springframework.stereotype.Repository
public interface Repository extends CrudRepository<CityInsight, String> {
}
