package buaasoft.blog.mapping;

import buaasoft.blog.api.PostRepository;
import buaasoft.blog.entity.Post;
import buaasoft.blog.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

/**
 * @author Yirany
 * @version 1.0
 * @since 8/25/2019
 **/
@Controller
@RequestMapping("/post")
public class PostPage {
    @Autowired
    private PostRepository postRepository;


    private String postNotFoundResponse(long postID) {
        JSONObject response = new JSONObject();
        response.put(Constants.STATUS, false);
        response.put(Constants.errorMessage, "Cannot find post " + postID);
        return response.toJSONString();
    }

    @GetMapping("")
    @ResponseBody
    public String getPost(@RequestParam(value = "postID") Long postID) {
        System.out.println("Receive get of /post with postID " + postID);
        Optional<Post> dbResult = postRepository.findById(postID);
        if (dbResult.isEmpty()) {
            return postNotFoundResponse(postID);
        } else {
            return JSONObject.toJSONString(dbResult.get());
        }
    }
}
