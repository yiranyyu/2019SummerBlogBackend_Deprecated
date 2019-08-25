package buaasoft.blog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;

/**
 * @author Yirany
 * @version 1.0
 * @since 8/23/2019
 **/
@Document(collection = "Tag")
public class Tag {
    @Id
    private String name;
    private HashSet<Long> postIDs;

    public Tag(String name) {
        this.name = name;
        this.postIDs = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", postIDs=" + postIDs +
                '}';
    }

    public String getName() {
        return name;
    }

    public void addPostID(long toAdd) {
        postIDs.add(toAdd);
    }

    public void removePostID(long toRemove) {
        postIDs.remove(toRemove);
    }

    public HashSet<Long> getPostIDs() {
        return postIDs;
    }
}
