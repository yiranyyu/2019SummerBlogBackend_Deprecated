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
public class EditPersonalInfoPage {
    private static final Logger log = LoggerFactory.getLogger(PersonalPostList.class);

    @Autowired
    private UserRepository userRepository;

//    @GetMapping(path = "/edit_personal_information")
//    @ResponseBody
//    public String newUserInfo(@RequestParam String newInfo, Model model) {
//        ObjectMapper mapper=new ObjectMapper();
//        try {
//            User newUser = mapper.readValue(newInfo, User.class);
//            updateUser(newUser);
//            return "successfully edit personal information";
//        } catch (IOException e){
//            return "failed to edit personal information";
//        }
//    }

//    private void updateUser(User newUser){
//        User user = (User) userRepository.findByUserName(newUser.getUserName());
//        user.setNickName(newUser.getNickName());
//        user.setAvatar(newUser.getAvatar());
//        user.setPassword(newUser.getPasswordHash());
//    }
}
