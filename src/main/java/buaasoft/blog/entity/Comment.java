package buaasoft.blog.entity;

import buaasoft.blog.utils.Date;

/**
 * @author Yirany
 * @version 1.0
 * @since 8/23/2019
 **/
public class Comment {
    private final String content;
    private final String commenterName;
    private final Date postDate;

    public Comment(String commenterName, String content, Date postDate) {
        this.content = content;
        this.commenterName = commenterName;
        this.postDate = postDate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", commenterName='" + commenterName + '\'' +
                ", postDate=" + postDate +
                '}';
    }

    public String getContent() {
        return content;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public Date getPostDate() {
        return postDate;
    }

}
