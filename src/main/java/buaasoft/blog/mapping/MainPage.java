package buaasoft.blog.mapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Yirany
 * @version 1.0
 * @since 8/23/2019
 **/
@Controller
public class MainPage {
    @GetMapping("/")
    @ResponseBody
    public String home() {
        HttpSession session = getRequest().getSession();
        ArrayList<String> attributeNames = Collections.list(session.getAttributeNames());
        for (String name : attributeNames) {
            System.out.println(name + session.getAttribute(name));
        }
        System.out.println(attributeNames);
        return "This is home of MainPage wuuuu";
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
