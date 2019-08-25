package buaasoft.blog.mapping;

import buaasoft.blog.api.PostRepository;
import buaasoft.blog.api.UserRepository;
import buaasoft.blog.entity.Post;
import buaasoft.blog.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PersonalPostList {
    private static final Logger log = LoggerFactory.getLogger(PersonalPostList.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping(path="/PersonalPostList ") // Map ONLY GET REQUESTs.
    @ResponseBody
    public String getPostList (@RequestParam String userName) {
        return toJSON(userName);
    }

    private String toJSON(String authorName){
        // TODO: not implemented
        return "";
    }

}
