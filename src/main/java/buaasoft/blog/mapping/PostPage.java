package buaasoft.blog.mapping;

import buaasoft.blog.api.PostRepository;
import buaasoft.blog.api.UserRepository;
import buaasoft.blog.entity.Comment;
import buaasoft.blog.entity.Post;
import buaasoft.blog.entity.User;
import buaasoft.blog.utils.Constants;
import buaasoft.blog.utils.Date;
import buaasoft.blog.utils.Responses;
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

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    @ResponseBody
    public String getPost(@RequestParam(value = "postID") Long postID) {
        System.out.println("Receive get of /post with postID " + postID);
        Optional<Post> dbResult = postRepository.findById(postID);
        if (dbResult.isEmpty()) {
            return Responses.postNotFoundResponse(postID);
        } else {
            return JSONObject.toJSONString(dbResult.get());
        }
    }

    @GetMapping("/addComment")
    @ResponseBody
    public String addComment(@RequestParam(value = "postID") Long postID,
                             @RequestParam(value = "commenter") String commenter,
                             @RequestParam(value = "content") String content) {
        System.out.printf("Receive get of /post/addComment with {postID: %s, commenter: %s, content: %s}\n", postID, commenter, content);
        Optional<Post> dbResult = postRepository.findById(postID);
        Optional<User> userResult = userRepository.findByUserName(commenter);
        if (dbResult.isEmpty()) {
            return Responses.postNotFoundResponse(postID);
        } else if (userResult.isEmpty()) {
            return Responses.userNotFoundResponse(commenter);
        } else {
            Post post = dbResult.get();
            post.addComment(new Comment(commenter, content, Date.getNow()));

            JSONObject response = new JSONObject();
            response.put(Constants.STATUS, true);
            return response.toJSONString();
        }
    }

}
