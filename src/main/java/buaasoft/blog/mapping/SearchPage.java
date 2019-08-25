package buaasoft.blog.mapping;

import buaasoft.blog.api.PostRepository;
import buaasoft.blog.api.TagRepository;
import buaasoft.blog.api.UserRepository;
import buaasoft.blog.entity.Post;
import buaasoft.blog.entity.Tag;
import buaasoft.blog.entity.User;
import buaasoft.blog.utils.Constants;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/search")
public class SearchPage {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;

    private String tagNotFoundResponse(String tagName) {
        JSONObject response = new JSONObject();
        response.put(Constants.STATUS, false);
        response.put(Constants.errorMessage, "Cannot find tag " + tagName);
        return response.toJSONString();
    }


    @GetMapping("/tag")
    @ResponseBody
    public String searchByTag(@RequestParam(value = "tagName", required = true) String tagName) {
        Optional<Tag> tag = tagRepository.findByName(tagName);
        if (tag.isEmpty()) {
            return tagNotFoundResponse(tagName);
        } else {
            HashSet<Long> postIDs = tag.get().getPostIDs();
            ArrayList<Post> posts = new ArrayList<>();

            for (Long id : postIDs) {
                postRepository.findById(id).ifPresent(posts::add);
            }

            return JSONArray.toJSONString(posts);
        }
    }

    @GetMapping("/contentAndTitle")
    @ResponseBody
    public String searchByKeyword(@RequestParam(value = "keyword", required = true) String keyword) {
        List<Post> posts = postRepository.findAll();
        ArrayList<Post> ret = new ArrayList<>();
        for (Post post : posts) {
            if (post.getContent().contains(keyword) || post.getTitle().contains(keyword)) {
                ret.add(post);
            }
        }

        return JSONArray.toJSONString(ret);
    }

    @GetMapping("/user")
    @ResponseBody
    public String searchByUser(@RequestParam(value = "username", required = true) String username) {
        Optional<User> x = userRepository.findByNickName(username);
        Optional<User> y = userRepository.findByUserName(username);
        ArrayList<User> users = new ArrayList<>(2);
        x.ifPresent(users::add);
        y.ifPresent(users::add);
        return JSONArray.toJSONString(users);
    }


}
