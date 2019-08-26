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
import org.springframework.web.bind.annotation.*;

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
    public String getPost(@RequestParam(value = "username") String username,
                          @RequestParam(value = "postID") Long postID) {
        System.out.printf("Receive get of /post with postID %d for %s\n", postID, username);
        Optional<Post> dbResult = postRepository.findById(postID);
        Optional<User> x = userRepository.findByUserName(username);
        if (x.isEmpty()) {
            return Responses.userNotFoundResponse(username);
        } else if (dbResult.isEmpty()) {
            return Responses.postNotFoundResponse(postID);
        } else {
            Post post = dbResult.get();
            if (!x.get().getUserName().equals(post.getAuthorName())) {
                post.addView();
                postRepository.save(post);
            }
            return JSONObject.toJSONString(dbResult.get());
        }
    }

    @GetMapping("/like")
    @ResponseBody
    public String checkIfLikePost(@RequestParam(value = "username") String username,
                                  @RequestParam(value = "postID") Long postID) {
        System.out.printf("Receive get of /post/like with {username: %s, postID: %s}\n", username, postID);
        Optional<User> dbResult = userRepository.findByUserName(username);
        Optional<Post> postResult = postRepository.findById(postID);
        if (dbResult.isEmpty()) {
            return Responses.userNotFoundResponse(username);
        } else if (postResult.isEmpty()) {
            return Responses.postNotFoundResponse(postID);
        } else {
            JSONObject response = new JSONObject();
            response.put(Constants.STATUS, true);
            response.put("like", dbResult.get().isLikingPost(postID));
            return response.toJSONString();
        }
    }

    @PostMapping("/like")
    @ResponseBody
    public String likePost(@RequestParam(value = "username") String username,
                           @RequestParam(value = "postID") Long postID) {
        System.out.printf("Receive post of /post/like with {username: %s, postID: %s}\n", username, postID);
        Optional<User> dbResult = userRepository.findByUserName(username);
        Optional<Post> postResult = postRepository.findById(postID);
        if (dbResult.isEmpty()) {
            return Responses.userNotFoundResponse(username);
        } else if (postResult.isEmpty()) {
            return Responses.postNotFoundResponse(postID);
        } else {
            User user = dbResult.get();
            user.likePost(postID);
            JSONObject response = new JSONObject();
            response.put(Constants.STATUS, true);
            return response.toJSONString();
        }
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
