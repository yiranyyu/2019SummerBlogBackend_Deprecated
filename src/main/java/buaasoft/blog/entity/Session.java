package buaasoft.blog.entity;

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
    private String token;

    private String editorContext;

    public Session() {
    }

    /**
     * Please delete the origin session from database before assign a new session to user
     *
     * @param token token of session
     * @param editorContext editor context
     */
    public Session(String token, String editorContext) {
        this.token = token;
        this.editorContext = editorContext;
    }

    public String getEditorContext() {
        return editorContext;
    }

    public void setEditorContext(String editorContext) {
        this.editorContext = editorContext;

    }

    public String getToken() {
        return token;
    }
}
