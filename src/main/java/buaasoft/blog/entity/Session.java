package buaasoft.blog.entity;

import buaasoft.blog.utils.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Yirany
 * @version 1.0
 * @since 8/25/2019
 **/
@Document(collection = "Session")
public class Session {
    @Id
    private String id;

    private String editorContext;

    public Session() {
    }

    /**
     * Please delete the origin session from database before assign a new session to user
     *
     * @param user          User to have this session
     * @param editorContext last modified editor context
     */
    public Session(User user, String editorContext) {
        id = Date.getNow() + user.getUserName();
        user.setSessionID(id);
        this.editorContext = editorContext;
    }

    public String getEditorContext() {
        return editorContext;
    }

    public void setEditorContext(String editorContext) {
        this.editorContext = editorContext;

    }

    public String getId() {
        return id;
    }
}
