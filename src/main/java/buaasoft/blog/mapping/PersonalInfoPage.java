package buaasoft.blog.mapping;

import buaasoft.blog.api.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonalInfoPage {
    private static final Logger log = LoggerFactory.getLogger(PersonalPostList.class);

    @Autowired
    private UserRepository userRepository;


}
