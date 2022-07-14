package eu.unicredit.dummy_java_app.repositories;

import eu.unicredit.dummy_java_app.pojos.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
