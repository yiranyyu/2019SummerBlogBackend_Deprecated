package buaasoft.blog.mapping;

import buaasoft.blog.api.PostRepository;
import buaasoft.blog.api.UserRepository;
import buaasoft.blog.entity.Post;
import buaasoft.blog.entity.User;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class PersonalPostList {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping(path="/PersonalPostList ") // Map ONLY GET REQUESTs.
    @ResponseBody
    public String getPostList (@RequestParam(value = "username", required = true) String userName) {
        class AsDraft {
            private List<Post> draft;
            private List<Post> notDraft;

            private AsDraft(List<Post> draft, List<Post> notDraft) {
                this.draft = draft;
                this.notDraft = notDraft;
            }

            private List<Post> getDraft() {
                return draft;
            }

            private List<Post> getNotDraft() {
                return notDraft;
            }
        }
        AsDraft asDraft = new AsDraft(
                (List<Post>) postRepository.findAllByAuthorNameAndAsDraftIsTrue(userName),
                (List<Post>) postRepository.findAllByAuthorNameAndAsDraftIsFalse(userName)
        );
        if(asDraft.getDraft().isEmpty() && asDraft.getNotDraft().isEmpty()){
            return "No post";
        } else {
            return JSONArray.toJSON(asDraft).toString();
        }

    }

    private String toJSON(String authorName){
        // TODO: not implemented
        return "";
    }

}
