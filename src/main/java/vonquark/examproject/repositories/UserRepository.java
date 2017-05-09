package vonquark.examproject.repositories;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import vonquark.examproject.enteties.User;

import javax.persistence.LockModeType;

public interface UserRepository extends CrudRepository<User, Integer> {

//  @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
//  User findOne(Integer id);

  User findByFirstName(String firstName);
}
