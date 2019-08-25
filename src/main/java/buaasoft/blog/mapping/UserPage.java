package buaasoft.blog.mapping;

import buaasoft.blog.api.PostRepository;
import buaasoft.blog.api.UserRepository;
import buaasoft.blog.entity.User;
import buaasoft.blog.utils.Responses;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserPage {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;


    @GetMapping("/followings")
    @ResponseBody
    public String getFollowings(@RequestParam(value = "username", required = true) String username) {
        System.out.println("Receive get of /followings with username = " + username);
        Optional<User> dbResult = userRepository.findByUserName(username);
        if (dbResult.isEmpty()) {
            return Responses.userNotFoundResponse(username);
        } else {
            HashSet<String> followings = dbResult.get().getFollowingUserNames();
            ArrayList<User> followingUsers = new ArrayList<>(followings.size());
            for (String name : followings) {
                followingUsers.add(userRepository.findByUserName(name).get());
            }
            return JSONArray.toJSONString(followingUsers);
        }
    }
}
