package vonquark.examproject.repositories;

import org.springframework.data.repository.CrudRepository;
import vonquark.examproject.enteties.User;

public interface UserRepository extends CrudRepository<User, Integer> {

  User findByFirstName(String firstName);
}
