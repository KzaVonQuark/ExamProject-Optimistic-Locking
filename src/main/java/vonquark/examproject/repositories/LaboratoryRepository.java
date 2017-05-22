package vonquark.examproject.repositories;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import vonquark.examproject.enteties.Laboratory;

import javax.persistence.LockModeType;

public interface LaboratoryRepository extends CrudRepository<Laboratory, Integer> {
  Laboratory findByName(String firstName);
}
