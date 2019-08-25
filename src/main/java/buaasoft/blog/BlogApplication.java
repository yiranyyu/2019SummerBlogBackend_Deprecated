package buaasoft.blog;

import buaasoft.blog.api.PostRepository;
import buaasoft.blog.api.TagRepository;
import buaasoft.blog.api.UserRepository;
import buaasoft.blog.entity.Comment;
import buaasoft.blog.entity.Post;
import buaasoft.blog.entity.Tag;
import buaasoft.blog.entity.User;
import buaasoft.blog.utils.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    private void testUserRepository() {
        userRepository.deleteAll();
        User alice = new User("Alice", "123", "hhh");
        alice.addFollowing("Bob");

        userRepository.save(alice);
        userRepository.save(new User("Bob", "abc", "hhh", "hh"));

        System.out.println("Users found with findAll():");
        for (User user : userRepository.findAll()) {
            System.out.println(user);
        }
        System.out.println("-------------------------------");

        System.out.println("Users found with findByFirstName('Alice'):");
        System.out.println(userRepository.findByUserName("Alice"));
        System.out.println("--------------------------------");

        System.out.println("Users found with findByLastName('Smith'):");
        System.out.println(userRepository.findByUserName("Smith"));
        System.out.println("--------------------------------");
        System.out.println("End");
    }

    public void addTagToPost(Post post, String tagName) {
        post.addTag(tagName);
        Optional<Tag> dbResult = tagRepository.findByName(tagName);
        Tag tag;

        if (dbResult.isEmpty()) {
            tag = new Tag(tagName);
        } else {
            tag = dbResult.get();
        }
        tag.addPostID(post.getId());
        tagRepository.save(tag);
    }


    private void testPostRepository() {
        postRepository.deleteAll();

        Post post1 = new Post("Alice", "titleA", "content", Date.getNow());
        post1.addComment(new Comment("Bob", "Bob", Date.getNow()));
        post1.publish();

        for (int i = 0; i < 100; ++i) {
            Post toAdd = new Post("Alice", "title_" + i, "content", Date.getNow());
            addTagToPost(toAdd, "" + (i % 10));
            postRepository.save(toAdd);
        }

        postRepository.save(post1);
        postRepository.save(new Post("Alice", "titleB", "content", Date.getNow()));

        System.out.println("Posts found with findAll():");
        for (Post post : postRepository.findAll()) {
            System.out.println(post);
        }
        System.out.println("-------------------------------");

        System.out.println("Posts written by Alice");
        for (Post post : postRepository.findAllByAuthorNameAndAsDraftIsTrue("Alice")) {
            System.out.println(post);
        }
        System.out.println("-------------------------------");
    }

    private void testDatabase() {
        testUserRepository();
        testPostRepository();
    }

    @Override
    public void run(String... args) {
        testDatabase();
    }
}
