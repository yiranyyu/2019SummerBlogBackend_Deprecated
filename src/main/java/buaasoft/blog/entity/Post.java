package buaasoft.blog.entity;

import buaasoft.blog.utils.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

/**
 * @author Yirany
 * @version 1.0
 * @since 8/23/2019
 **/
@Document(collection = "Post")
public class Post {
    private static long nextPostId = 0;

    @Id
    private Long id;

    private String authorName;
    private Date postDate;
    private String title;
    private String content;
    private ArrayList<String> tagNames;
    private Date lastModifiedDate;
    private ArrayList<Comment> comments;
    private boolean asDraft;

    public Post() {
        id = 0L;
        authorName = "";
        postDate = Date.getNow();
    }

    public Post(String author, String title, String content, Date postDate) {
        this(author, title, content, postDate, new ArrayList<>());
    }

    /**
     * @param author   authorName of post
     * @param title    title of post
     * @param content  content of post
     * @param postDate post date of post
     * @param tagNames tagNames in string format
     */
    public Post(String author, String title, String content, Date postDate, ArrayList<String> tagNames) {
        this.authorName = author;
        this.title = title;
        this.content = content;
        this.postDate = postDate;
        this.tagNames = tagNames;
        this.lastModifiedDate = this.postDate;
        this.comments = new ArrayList<>();
        this.id = nextPostId++;
        this.asDraft = true;
//        System.out.println("##################### Post ctor called #####################");
    }

    public boolean isAsDraft() {
        return asDraft;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Date getPostDate() {
        return postDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        lastModifiedDate = Date.getNow();
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        lastModifiedDate = Date.getNow();
    }

    public ArrayList<String> getTagNames() {
        return tagNames;
    }

    public void addTag(Tag tag) {
        tagNames.add(tag.getName());
        lastModifiedDate = Date.getNow();
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", postDate=" + postDate +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tagNames=" + tagNames +
                ", lastModifiedDate=" + lastModifiedDate +
                ", comments=" + comments +
                ", asDraft=" + asDraft +
                '}';
    }

    /**
     * Publish this post
     * TODO: Caller should remember to add the <code>id</code> of this instance to the corresponding post_list of the tags in <code>tagNames</code>
     *
     * @return True is the post is not published yet before the calling
     */
    public boolean publish() {
        if (asDraft) {
            asDraft = false;
            return true;
        }
        return false;
    }
}
