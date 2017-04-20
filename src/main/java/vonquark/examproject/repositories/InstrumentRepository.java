package vonquark.examproject.repositories;

import org.springframework.data.repository.CrudRepository;
import vonquark.examproject.enteties.Instrument;

public interface InstrumentRepository extends CrudRepository<Instrument, Integer> {
  Instrument findByHwId(String hwId);
}
