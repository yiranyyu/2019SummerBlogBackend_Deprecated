package buaasoft.blog.api;

import buaasoft.blog.entity.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Session, String> {
}
