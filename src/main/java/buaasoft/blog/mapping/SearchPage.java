package buaasoft.blog.mapping;

import buaasoft.blog.entity.Post;
import buaasoft.blog.entity.Tag;
import buaasoft.blog.api.PostRepository;
import buaasoft.blog.api.TagRepository;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class SearchPage {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/search")
    @ResponseBody
    public String searchByTag(@RequestParam(value = "tag", required = true) String tagName){
        Optional<Tag> tag = tagRepository.findByName(tagName);
        if(tag.isEmpty()){
            return "";
        } else {
            HashSet<Long> postIDs = tag.get().getPostIDs();

            StringBuilder ret = new StringBuilder("{");
            for(Long id : postIDs){
                ret.append(JSONObject.toJSON(postRepository.findById(id).get()).toString()).append(",");
            }
            ret.append("}");

            return ret.toString();
        }
    }

    @GetMapping("/search")
    @ResponseBody
    public String searchByKeyword(@RequestParam(value = "keyword", required = true) String keyword){
        List<Post> posts = postRepository.findAll();

        StringBuilder ret = new StringBuilder("{");
        for(Post post : posts){
            if(post.getContent().contains(keyword) || post.getTitle().contains(keyword)){
                ret.append(JSONObject.toJSON(post).toString()).append(",");
            }
        }
        ret.append("}");
        return ret.toString();
    }

}
