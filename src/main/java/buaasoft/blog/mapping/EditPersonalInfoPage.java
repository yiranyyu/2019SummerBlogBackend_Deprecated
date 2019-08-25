package buaasoft.blog.mapping;

import buaasoft.blog.api.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class EditPersonalInfoPage {
    private static final Logger log = LoggerFactory.getLogger(UserPage.class);

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
