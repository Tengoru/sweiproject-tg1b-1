package base.acitvitymeter;

/**
 * Created by Sümeyye on 14.01.2018.
 */
import org.springframework.data.repository.CrudRepository;

// from https://spring.io/guides/gs/accessing-data-jpa/
// https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html
public interface MailRepository extends CrudRepository<Mail, Long>{}
