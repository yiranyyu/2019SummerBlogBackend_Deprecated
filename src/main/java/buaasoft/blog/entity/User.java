package buaasoft.blog.entity;

import buaasoft.blog.utils.HashGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;


/**
 * @author Yirany
 * @version 1.0
 * @since 8/23/2019
 **/
@Document(collection = "User")
public class User implements Visitor {
    private static final String DEFAULT_AVATAR = "default";

    @Id
    private String userName;

    private String passwordHash;
    private String nickName;
    private ArrayList<Long> postIDs;
    private HashSet<String> followingUserNames;
    private String avatar;
    private String sessionID;

    public User() {
        userName = "";
    }

    public User(String userName, String password, String nickName) {
        this(userName, password, nickName, DEFAULT_AVATAR);
    }


    public User(String userName, String password, String nickName, String avatar) {
        this.userName = userName;
        this.passwordHash = generatePasswordHash(password);
        this.nickName = nickName;
        this.avatar = avatar;
        this.postIDs = new ArrayList<>();
        this.followingUserNames = new HashSet<>();
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    private String generatePasswordHash(String x) {
        return HashGenerator.sha256(x);
    }

    public boolean checkPassword(String x) {
        return this.passwordHash.equals(generatePasswordHash(x));
    }

    public void addFollowing(String toFollow) {
        followingUserNames.add(toFollow);
    }

    public void removeFollowing(String toRemove) {
        followingUserNames.remove(toRemove);
    }

    public void addPost(long toAdd) {
        postIDs.add(toAdd);
    }

    public void removePost(long toRemove) {
        postIDs.remove(toRemove);
    }

    public ArrayList<Long> getPostIDs() {
        return postIDs;
    }

    public HashSet<String> getFollowingUserNames() {
        return followingUserNames;
    }

    public String getUserName() {
        return userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPassword(String password) {
        this.passwordHash = generatePasswordHash(password);
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", nickName='" + nickName + '\'' +
                ", postIDs=" + postIDs +
                ", followingUserNames=" + followingUserNames +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
