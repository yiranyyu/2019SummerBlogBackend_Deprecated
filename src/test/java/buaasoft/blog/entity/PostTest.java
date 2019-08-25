package buaasoft.blog.entity;

import buaasoft.blog.utils.Date;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PostTest {

    private Post post;

    @Before
    public void setPost() {
        User author = new User("TestPostAuthor", "123", "");
        post = new Post(author.getUserName(), "", "", Date.getNow());
    }

    @Test
    public void isDraft() {
        setPost();
        assertTrue(post.isAsDraft());
    }

    @Test
    public void publish() {
        setPost();
        post.publish();
        assertFalse(post.isAsDraft());
    }
}