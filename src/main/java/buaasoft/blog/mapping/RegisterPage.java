package buaasoft.blog.mapping;

import buaasoft.blog.api.UserRepository;
import buaasoft.blog.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class RegisterPage {
    private static final Logger log = LoggerFactory.getLogger(RegisterPage.class);

    @Autowired
    private UserRepository userRepository;


    /**
     * Register method, requires userName, nickname & password.
     * The legality of the username and password will be checked on the frontend
     * and this method will check if the username has been already registered.
     * If the username and password is legal, add a new user to the repository.
     *
     * @param userInfo {username : username, nickname: nickname, password: password};
     * @return
     */
    @GetMapping(path = "/register") // Map ONLY GET REQUESTs.
    @ResponseBody
    public String register(@RequestParam String userInfo, Model model) {
        // @ResponseBody means the returned String is a response, not a view name.
        try {
            User user = AnalysisUserInfo(userInfo);
            if (duplicateUserName(user.getUserName())) {
                return "duplicate username";
            }

            userRepository.save(user);
            return "register successfully";
        } catch (IOException e) {
            return "failed to register";
        }
        // TODO how to jump to user's main page?
    }

    private boolean duplicateUserName(String userName) {
        return userRepository.findByUserName(userName) != null;
    }

    private User AnalysisUserInfo(String userInfo) throws IOException {
        class tmpUsr {
            private String userName;
            private String nickName;
            private String password;

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getUserName() {
                return userName;
            }

            public String getNickName() {
                return nickName;
            }

            public String getPassword() {
                return password;
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        tmpUsr usr = mapper.readValue(userInfo, tmpUsr.class);
        return new User(usr.getUserName(), usr.getPassword(), usr.getNickName());
    }
}
