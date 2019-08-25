package buaasoft.blog.mapping;

import buaasoft.blog.api.PostRepository;
import buaasoft.blog.api.SessionRepository;
import buaasoft.blog.api.UserRepository;
import buaasoft.blog.entity.Post;
import buaasoft.blog.entity.Session;
import buaasoft.blog.entity.User;
import buaasoft.blog.utils.Constants;
import buaasoft.blog.utils.Date;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

/**
 * @author Yirany
 * @version 1.0
 * @since 8/23/2019
 **/
@Controller
public class MainPage {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;

    static String userNotFoundResponse(String username) {
        JSONObject response = new JSONObject();
        response.put(Constants.STATUS, false);
        response.put(Constants.errorMessage, "Cannot find user " + username);
        return response.toJSONString();
    }

    private static String passwordErrorResponse() {
        JSONObject response = new JSONObject();
        response.put(Constants.STATUS, false);
        response.put(Constants.errorMessage, "Invalid password");
        return response.toJSONString();
    }

    @GetMapping("/posts")
    @ResponseBody
    public String recommendedPosts(@RequestParam(value = "numberOfPosts", required = false) Integer numberOfPosts) {
        System.out.println("Receive get of /posts with number " + numberOfPosts);
        if (numberOfPosts == null) {
            numberOfPosts = 10;
        }

        List<Post> posts = postRepository.findAllByAsDraftIsFalse();
        numberOfPosts = Math.min(numberOfPosts, posts.size());
        posts = posts.subList(0, numberOfPosts);
        return JSONArray.toJSONString(posts);
    }

    @GetMapping("/userInfo")
    @ResponseBody
    public String userInfo(@RequestParam(value = "username", required = true) String username) {
        System.out.println("Receive get of /userInfo of " + username);
        Optional<User> dbResult = userRepository.findByUserName(username);
        if (dbResult.isEmpty()) {
            return userNotFoundResponse(username);
        } else {
            JSONObject response = new JSONObject();
            response.put(Constants.STATUS, true);
            response.put("user", dbResult.get());
            return response.toJSONString();
        }
    }

    private String generateToken(String userName) {
        return userName + " " + Date.getNow();
    }

    @GetMapping(path = "/login")
    @ResponseBody
    public String loginGet() {
        System.out.println("Receive get of /login");
        JSONObject response = new JSONObject();
        response.put(Constants.STATUS, true);
        response.put("value", "This is home of login page");
        return response.toJSONString();
    }

    @PostMapping(path = "/login")
    @ResponseBody
    public String loginByPost(@RequestParam(value = "username", required = true) String username,
                              @RequestParam(value = "password", required = true) String password,
                              @RequestParam(value = "token", required = false) String token) {
        System.out.printf("Receive post of /login with {name: %s, password: %s, token:%s}\n", username, password, token);
        Optional<User> dbResult = userRepository.findByUserName(username);

        if (dbResult.isEmpty()) {
            return userNotFoundResponse(username);
        } else {
            User user = dbResult.get();
            if (user.checkPassword(password)) {
                return loginSucceedResponse(user, username, token);
            } else {
                return passwordErrorResponse();
            }
        }
    }

    @PostMapping(path = "/signUp")
    @ResponseBody
    public String signUp(@RequestParam(value = "username", required = true) String username,
                         @RequestParam(value = "nickname", required = true) String nickname,
                         @RequestParam(value = "password", required = true) String password) {
        System.out.printf("Receive post of sign up with {name: %s, password: %s}\n", username, password);
        Optional<User> dbResult = userRepository.findByUserName(username);

        if (dbResult.isEmpty()) {
            return signUpSucceedResponse(username, password, nickname);
        } else {
            return userAlreadyExistResponse(username);
        }
    }

    private String loginSucceedResponse(User user, String username, String token) {
        JSONObject response = new JSONObject();
        if (token == null) {
            token = generateToken(username);
            if (user.getSessionID() != null) {
                String sessionID = user.getSessionID();
                sessionRepository.deleteById(sessionID);
            }
            user.setSessionID(token);
            sessionRepository.save(new Session(user, ""));
            response.put("token", token);
        }
        response.put(Constants.STATUS, true);
        return response.toJSONString();
    }

    private String signUpSucceedResponse(String username, String password, String nickname) {
        JSONObject response = new JSONObject();
        User user = new User(username, password, nickname);
        userRepository.save(user);
        response.put(Constants.STATUS, true);
        return response.toJSONString();
    }

    private String userAlreadyExistResponse(String username) {
        JSONObject response = new JSONObject();
        response.put(Constants.STATUS, false);
        response.put(Constants.errorMessage, "User " + username + " already exists");
        return response.toJSONString();
    }

}
