package buaasoft.blog.mapping;

import buaasoft.blog.api.UserRepository;
import buaasoft.blog.entity.Post;
import buaasoft.blog.entity.Tag;
import buaasoft.blog.api.PostRepository;
import buaasoft.blog.api.TagRepository;
import buaasoft.blog.entity.User;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

public class SearchPage {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/search_by_tag")
    @ResponseBody
    public String searchByTag(@RequestParam(value = "tag", required = true) String tagName){
        Optional<Tag> tag = tagRepository.findByName(tagName);
        if(tag.isEmpty()){
            return "";
        } else {
            HashSet<Long> postIDs = tag.get().getPostIDs();
            ArrayList<Post> posts = new ArrayList<>();

            for(Long id : postIDs){
                Optional<Post> tmpPost = postRepository.findById(id);
                tmpPost.ifPresent(posts::add);
            }

            return JSONArray.toJSON(posts).toString();
        }
    }

    @GetMapping("/search_by_keyword")
    @ResponseBody
    public String searchByKeyword(@RequestParam(value = "keyword", required = true) String keyword){
        List<Post> posts = postRepository.findAll();
        ArrayList<Post> ret = new ArrayList<>();
        for(Post post : posts){
            if(post.getContent().contains(keyword) || post.getTitle().contains(keyword)){
                ret.add(post);
            }
        }

        return JSONArray.toJSON(ret).toString();
    }

    @GetMapping("/search_by_user")
    @ResponseBody
    public String searchByUser(@RequestParam(value = "name", required = true) String name){
        ArrayList users = (ArrayList) userRepository.findByNickName(name);
        Optional<User> userNameUsers = userRepository.findByUserName(name);
        if(users.isEmpty() && userNameUsers.isEmpty()){
            // TODO  不存在用户
            return "";
        } else {
            userNameUsers.ifPresent(users::add);

            return JSONArray.toJSON(users).toString();
        }
    }



}
