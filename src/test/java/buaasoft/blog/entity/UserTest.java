package buaasoft.blog.entity;

import buaasoft.blog.utils.Date;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotEquals;

public class UserTest {
    private User user;

    @Before
    public void setUser() {
        user = new User("TestUser", "1234", "orz", "notApplicable");
    }

    @Test
    public void addFollowing() {
        String toFollow = "addFollowingTestUser";
        assertFalse(user.getFollowingUserNames().contains(toFollow));
        user.addFollowing(toFollow);
        assertTrue(user.getFollowingUserNames().contains(toFollow));
    }

    @Test
    public void removeFollowing() {
        String toFollow = "RemoveFollowingTestUser";
        assertFalse(user.getFollowingUserNames().contains(toFollow));

        user.addFollowing(toFollow);
        assertTrue(user.getFollowingUserNames().contains(toFollow));

        user.removeFollowing(toFollow);
        assertFalse(user.getFollowingUserNames().contains(toFollow));
    }

    @Test
    public void addPost() {
        long toAdd = new Post(user.getUserName(), "Title", "", Date.getNow()).getId();
        assertFalse(user.getPostIDs().contains(toAdd));

        user.addPost(toAdd);
        assertTrue(user.getPostIDs().contains(toAdd));
    }

    @Test
    public void removePost() {
        long toRemove = new Post(user.getUserName(), "Title", "", Date.getNow()).getId();
        assertFalse(user.getPostIDs().contains(toRemove));

        user.addPost(toRemove);
        assertTrue(user.getPostIDs().contains(toRemove));

        user.removePost(toRemove);
        assertFalse(user.getPostIDs().contains(toRemove));
    }

    @Test
    public void getPosts() {
        setUser();
        assertEquals(user.getPostIDs().size(), 0);

        user.addPost(new Post(user.getUserName(), "Title", "", Date.getNow()).getId());
        assertEquals(user.getPostIDs().size(), 1);
    }

    @Test
    public void getFollowing() {
        setUser();
        assertEquals(user.getFollowingUserNames().size(), 0);

        user.addFollowing(user.getUserName());
        assertEquals(user.getFollowingUserNames().size(), 1);
    }

    @Test
    public void getUserName() {
        String name = "Name";
        user = new User(name, "123456", "abc");
        assertEquals(user.getUserName(), name);
    }

    @Test
    public void getPasswordHash() {
        String password = "hhh";
        User otherUser = new User("123", password, "");
        user = new User("456", password, "");

        assertEquals(user.getPasswordHash(), otherUser.getPasswordHash());
    }

    @Test
    public void setPassword() {
        String password = "TestSetPassword";
        user.setPassword(password);
        assertTrue(user.checkPassword(password));
    }

    @Test
    public void getNickName() {
        String nickName = "Name";
        user = new User(nickName, "123456", "abc");
        assertEquals(user.getUserName(), nickName);

    }

    @Test
    public void setNickName() {
        String nickName = "TestSetNickname";
        user.setNickName(nickName);
        assertEquals(user.getNickName(), nickName);
    }

    @Test
    public void getAvatar() {
        String avatar = "TestGetAvatar";
        user = new User("TestUser", "password", "nickName", avatar);
        assertEquals(user.getAvatar(), avatar);
    }

    @Test
    public void setAvatar() {
        String avatar = "TestSetAvatar";
        user.setAvatar(avatar);
        assertEquals(user.getAvatar(), avatar);
    }

    @Test
    public void equals1() {
        User a = new User("Name", "password", "nick");
        User b = new User("Name", "otherPassword", "otherNick");
        User c = new User("otherName", "password", "nick");
        assertEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(b, c);
    }

    @Test
    public void hashCode1() {
        User a = new User("Name", "password", "nick");
        User b = new User("Name", "otherPassword", "otherNick");
        User c = new User("otherName", "password", "nick");
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a.hashCode(), c.hashCode());
        assertNotEquals(b.hashCode(), c.hashCode());
    }
}