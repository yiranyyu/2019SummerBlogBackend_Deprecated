package buaasoft.blog.api;

import buaasoft.blog.entity.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TagRepository extends MongoRepository<Tag, String> {
    Optional<Tag> findByName(String s);
}
