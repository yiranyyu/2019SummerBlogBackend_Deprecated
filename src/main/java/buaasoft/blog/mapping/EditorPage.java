package buaasoft.blog.mapping;

import buaasoft.blog.api.SessionRepository;
import buaasoft.blog.api.TagRepository;
import buaasoft.blog.entity.Post;
import buaasoft.blog.entity.Session;
import buaasoft.blog.entity.Tag;
import buaasoft.blog.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/editor")
public class EditorPage {

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
}
