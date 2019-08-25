package buaasoft.blog.mapping;

import buaasoft.blog.api.UserRepository;
import buaasoft.blog.entity.User;
import buaasoft.blog.utils.Constants;
import buaasoft.blog.utils.Date;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/logins")
public class LoginAndSignUpPage {
    @Autowired
    private UserRepository userRepository;

    private String generateToken(String userName) {
        return userName + " " + Date.getNow();
    }

    @GetMapping(path = "")
    @ResponseBody
    public String loginGet() {
        System.out.println("Receive login get");
        return "This is home of login page";
    }

    @PostMapping(path = "")
    @ResponseBody
    public String loginByPost(@RequestParam(value = "username", required = true) String username,
                              @RequestParam(value = "password", required = true) String password,
                              @RequestParam(value = "token", required = false) String token) {
        System.out.printf("Receive login post {name: %s, password: %s, token:%s}\n", username, password, token);
        Optional<User> dbResult = userRepository.findByUserName(username);
        JSONObject response = new JSONObject();

        if (dbResult.isEmpty()) {
            response.put(Constants.STATUS, true);
        } else {
            User user = dbResult.get();
            if (user.checkPassword(password)) {
                if (token == null) {
                    token = generateToken(username);
                }
                response.put(Constants.STATUS, true);
                response.put("token", token);
            } else {
                response.put(Constants.STATUS, false);
                response.put(Constants.errorMessage, "Invalid password" + password);
            }
        }
        System.out.println("sdf" + response.toJSONString());
        return "hhh";
//        return response.toJSONString();
    }

    @PostMapping(path = "/signUp")
    @ResponseBody
    public String signUp(@RequestParam(value = "username", required = true) String username,
                         @RequestParam(value = "nickname", required = true) String nickname,
                         @RequestParam(value = "password", required = true) String password) {
        System.out.printf("Receive sign up post {name: %s, password: %s}\n", username, password);
        Optional<User> dbResult = userRepository.findByUserName(username);
        JSONObject response = new JSONObject();

        if (dbResult.isEmpty()) {
            User user = new User(username, password, nickname);
            userRepository.save(user);
            response.put(Constants.STATUS, true);
        } else {
            response.put(Constants.STATUS, false);
        }
        return response.toJSONString();
    }

}
