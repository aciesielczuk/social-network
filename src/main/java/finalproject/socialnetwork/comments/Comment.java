package finalproject.socialnetwork.comments;

import finalproject.socialnetwork.posts.Post;
import finalproject.socialnetwork.users.User;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name = "comment_body")
    private String commentBody;

    @NonNull
    @ManyToOne
    private User user;

    @NonNull
    @ManyToOne
    private Post post;

    public Comment() {
    }

    public Comment(int id, @NonNull String commentBody, @NonNull User user, @NonNull Post post) {
        this.id = id;
        this.commentBody = commentBody;
        this.user = user;
        this.post = post;
    }

    public Comment(@NonNull User user, @NonNull Post post, @NonNull String commentBody) {
        this.commentBody = commentBody;
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
    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(@NonNull String commentBody) {
        this.commentBody = commentBody;
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
