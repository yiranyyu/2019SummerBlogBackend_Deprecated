package buaasoft.blog.api;

import buaasoft.blog.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends MongoRepository<Post, Long> {
    /**
     * Find the post of given <code>id</code>
     *
     * @param id id of post
     * @return post if found, otherwise empty
     */
    @Override
    Optional<Post> findById(Long id);

    /**
     * Find the drafts, i.e. the unpublished posts of <code>author</code>
     *
     * @param authorName Name of author of found posts
     * @return published posts of <code>author</code>
     */
    Iterable<Post> findAllByAuthorNameAndAsDraftIsTrue(String authorName);

    /**
     * Find the published posts of author
     *
     * @param authorName author of found posts
     * @return published posts of <code>author</code>
     */
    Iterable<Post> findAllByAuthorNameAndAsDraftIsFalse(String authorName);

    /**
     * Find all published posts
     *
     * @return all published posts
     */
    List<Post> findAllByAsDraftIsFalse();
}
