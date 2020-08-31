package finalproject.socialnetwork.comments;

import finalproject.socialnetwork.posts.PostService;
import finalproject.socialnetwork.users.User;
import finalproject.socialnetwork.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    CommentService commentService;
    UserService userService;
    PostService postService;

    public CommentController(CommentService commentService, UserService userService, PostService postService) {
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/comments")
    public ResponseEntity addCommentToPost(@RequestHeader("x-authorization-token") String token, @RequestHeader("postId") int postId, @RequestBody Comment commentBody) {
        Optional<User> userFromDB = userService.getUserByToken(token);
        if (userFromDB.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (postService.findById(postId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Comment comment = commentService.addComment(token, postId, commentBody);
        return ResponseEntity.ok(commentService.saveComment(comment));
    }
}
