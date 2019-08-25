package buaasoft.blog.mapping;

import buaasoft.blog.api.PostRepository;
import buaasoft.blog.entity.Post;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Yirany
 * @version 1.0
 * @since 8/23/2019
 **/
@Controller
@RequestMapping(value = "/")
public class MainPage {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("")
    @ResponseBody
    public String home(@RequestParam(value = "numberOfPosts", required = false) Integer numberOfPosts) {
        if (numberOfPosts == null) {
            numberOfPosts = 10;
        }
        System.out.println("Receive home page get with number = " + numberOfPosts);

        List<Post> posts = postRepository.findAllByAsDraftIsFalse();
        numberOfPosts = Math.min(numberOfPosts, posts.size());
        posts = posts.subList(0, numberOfPosts);
        return JSONArray.toJSONString(posts);
    }

}
