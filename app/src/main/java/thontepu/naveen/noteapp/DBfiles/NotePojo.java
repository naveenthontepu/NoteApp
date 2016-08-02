package thontepu.naveen.noteapp.DBfiles;


import java.io.Serializable;

/**
 * Created by mac on 8/2/16.
 */
public class NotePojo implements Serializable {
    String id;
    String title;
    String message;

    public NotePojo() {
    }

    public NotePojo(String message, String id, String title) {
        this.message = message;
        this.id = id;
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
