package finalproject.socialnetwork.posts;

import finalproject.socialnetwork.users.User;
import finalproject.socialnetwork.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://snetwork-app.herokuapp.com"})
public class PostController {

    PostService postService;
    UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping(value="/posts", produces = "application/json; charset=UTF-8")
    public ResponseEntity addPost(@RequestHeader("x-authorization-token") String token, @RequestBody Post postBody) {
        Optional<User> userFromDB = userService.getUserByToken(token);
        if (userFromDB.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Post post = postService.addPost(token, postBody);
        return ResponseEntity.ok(postService.savePost(post));
    }

    @GetMapping(value="/posts", produces = "application/json; charset=UTF-8")
    public ResponseEntity getPosts(@RequestHeader("x-authorization-token") String token) {
        Optional<User> userFromDB = userService.getUserByToken(token);
        if (userFromDB.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(postService.getPosts());
    }
}
