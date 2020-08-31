package finalproject.socialnetwork.likes;

import finalproject.socialnetwork.posts.Post;
import finalproject.socialnetwork.users.User;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @ManyToOne
    private User user;

    @NonNull
    @ManyToOne
    private Post post;


    public Like() {
    }

    public Like(@NonNull User user, @NonNull Post post) {
        this.user = user;
        this.post = post;
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
    public Post getPost() {
        return post;
    }

    public void setPost(@NonNull Post post) {
        this.post = post;
    }
}
