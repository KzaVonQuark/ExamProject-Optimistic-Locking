package vonquark.examproject.repositories;

import org.springframework.data.repository.CrudRepository;
import vonquark.examproject.enteties.Laboratory;

public interface LaboratoryRepository extends CrudRepository<Laboratory, Integer> {
  Laboratory findByName(String firstName);
}
