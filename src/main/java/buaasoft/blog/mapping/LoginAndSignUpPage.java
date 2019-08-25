package buaasoft.blog.mapping;

import buaasoft.blog.api.SessionRepository;
import buaasoft.blog.api.UserRepository;
import buaasoft.blog.entity.Session;
import buaasoft.blog.entity.User;
import buaasoft.blog.utils.Constants;
import buaasoft.blog.utils.Date;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/login")
public class LoginAndSignUpPage {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    private String generateToken(String userName) {
        return userName + " " + Date.getNow();
    }

    @GetMapping(path = "")
    @ResponseBody
    public String loginGet() {
        System.out.println("Receive login get");
        JSONObject response = new JSONObject();
        response.put(Constants.STATUS, true);
        response.put("value", "This is home of login page");
        return response.toJSONString();
    }


    @PostMapping(path = "")
    @ResponseBody
    public String loginByPost(@RequestParam(value = "username", required = true) String username,
                              @RequestParam(value = "password", required = true) String password,
                              @RequestParam(value = "token", required = false) String token) {
        System.out.printf("Receive login post {name: %s, password: %s, token:%s}\n", username, password, token);
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
        System.out.printf("Receive sign up post {name: %s, password: %s}\n", username, password);
        Optional<User> dbResult = userRepository.findByUserName(username);

        if (dbResult.isEmpty()) {
            return signUpSucceedResponse(username, password, nickname);
        } else {
            return userAlreadyExistResponse(username);
        }
    }


    private String userNotFoundResponse(String username) {
        JSONObject response = new JSONObject();
        response.put(Constants.STATUS, false);
        response.put(Constants.errorMessage, "Cannot find user " + username);
        return response.toJSONString();
    }

    private String passwordErrorResponse() {
        JSONObject response = new JSONObject();
        response.put(Constants.STATUS, false);
        response.put(Constants.errorMessage, "Invalid password");
        return response.toJSONString();
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
