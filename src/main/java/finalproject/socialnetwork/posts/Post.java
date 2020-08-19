package finalproject.socialnetwork.posts;

import finalproject.socialnetwork.users.User;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @ManyToOne
    private User user;

    @NonNull
    @Column(name = "post_body")
    private String postBody;

    public Post() {
    }

    public Post(User user, String postBody) {
        this.user = user;
        this.postBody = postBody;
    }

    public Post(int id, @NonNull User user, @NonNull String postBody) {
        this.id = id;
        this.user = user;
        this.postBody = postBody;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public User getUser() {
        return user;
    }

    public void setUser(@NonNull User user) {
        this.user = user;
    }

    @NonNull
    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(@NonNull String postBody) {
        this.postBody = postBody;
    }
}