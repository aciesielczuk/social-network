package finalproject.socialnetwork.comments;

import finalproject.socialnetwork.posts.Post;
import finalproject.socialnetwork.posts.PostService;
import finalproject.socialnetwork.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class CommentController {

    CommentService commentService;
    UserService userService;
    PostService postService;

    public CommentController(CommentService commentService, UserService userService, PostService postService) {
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add-comment")
    public ResponseEntity addCommentToPost(@RequestHeader("username") String username, @RequestHeader("postId") int postId, @RequestBody String commentBody) {
        if (!userService.existsUserByUsername(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (postService.findById(postId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Comment comment = commentService.addComment(username, postId, commentBody);
        return ResponseEntity.ok(commentService.saveComment(comment));
    }
}
