package biz.growapp.baseandroidproject.network.response;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Post {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({PostType.PLAIN, PostType.POLL, PostType.ADMIN})
    public @interface PostType {
        String PLAIN = "plain";
        String POLL = "poll";
        String ADMIN = "admin";
    }

    private int id;
    private long ts;
    @PostType
    private String type;
    private boolean isLiked;
    private User user;
    private Photo photo;
    private String text;

    public int getId() {
        return id;
    }

    public Photo getPhoto() {
        return photo;
    }

    public long getTimestamp() {
        return ts;
    }

    public User getUser() {
        return user;
    }

    @PostType
    public String getType() {
        return type;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}
