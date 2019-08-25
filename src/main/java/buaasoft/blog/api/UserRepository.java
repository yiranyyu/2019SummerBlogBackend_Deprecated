package buaasoft.blog.api;

import buaasoft.blog.entity.User;
import buaasoft.blog.entity.Visitor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUserName(String firstName);
    Iterable<User> findByNickName(String nickName);
}
