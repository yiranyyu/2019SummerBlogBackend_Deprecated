package buaasoft.blog.mapping;

import buaasoft.blog.api.PostRepository;
import buaasoft.blog.api.SessionRepository;
import buaasoft.blog.api.TagRepository;
import buaasoft.blog.api.UserRepository;
import buaasoft.blog.entity.Post;
import buaasoft.blog.entity.Session;
import buaasoft.blog.entity.Tag;
import buaasoft.blog.entity.User;
import buaasoft.blog.utils.Constants;
import buaasoft.blog.utils.Responses;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/editor")
public class EditorPage {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TagRepository tagRepository;


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

    // TODO: Test this function
    @PostMapping("/session")
    @ResponseBody
    public String storeSession(@RequestParam(value = "token", required = true) String token,
                               @RequestParam(value = "context", required = true) String context) {
        System.out.printf("Receive post of /editor/session with context = {%s...}", context.substring(0, Math.min(10, context.length())));
        Optional<Session> dbResult = sessionRepository.findById(token);
        if (dbResult.isEmpty()) {
            sessionRepository.save(new Session(token, context));
        } else {
            Session session = dbResult.get();
            session.setEditorContext(context);
            sessionRepository.save(session);
        }
        JSONObject response = new JSONObject();
        response.put(Constants.STATUS, true);
        return response.toJSONString();
    }

    @PostMapping("/publish")
    @ResponseBody
    public String publish(@RequestParam(value = "postID", required = true) Long postID) {
        System.out.printf("Receive get of /post/publish with {postID: %s}\n", postID);
        Optional<Post> dbResult = postRepository.findById(postID);
        if (dbResult.isEmpty()) {
            return Responses.postNotFoundResponse(postID);
        } else {
            Post post = dbResult.get();
            Optional<User> x = userRepository.findByUserName(post.getAuthorName());
            if (x.isEmpty()) {
                return Responses.userNotFoundResponse(post.getAuthorName());
            }
            x.get().publishPost(post);
            postRepository.save(post);
            JSONObject response = new JSONObject();
            response.put(Constants.STATUS, true);
            return response.toJSONString();
        }
    }

}
