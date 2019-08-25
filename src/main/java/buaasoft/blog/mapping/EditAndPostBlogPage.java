package buaasoft.blog.mapping;

import buaasoft.blog.api.PostRepository;
import buaasoft.blog.api.UserRepository;
import buaasoft.blog.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EditAndPostBlogPage {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping(path = "/edit_post")
    @ResponseBody
    public String post(@RequestParam String postJSON, Model model) {
        return "";
    }
}
