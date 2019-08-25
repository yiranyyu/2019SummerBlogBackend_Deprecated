package buaasoft.blog.mapping;

import buaasoft.blog.api.PostRepository;
import buaasoft.blog.api.TagRepository;
import buaasoft.blog.api.UserRepository;
import buaasoft.blog.entity.Post;
import buaasoft.blog.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class EditorPage {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;


//    public void addTagToPost(Post post, String tagName) {
//        post.addTag(tagName);
//        Optional<Tag> dbResult = tagRepository.findByName(tagName);
//        Tag tag;
//
//        if (dbResult.isEmpty()) {
//            tag = new Tag(tagName);
//        } else {
//            tag = dbResult.get();
//        }
//        tag.addPostID(post.getId());
//        tagRepository.save(tag);
//    }
}
