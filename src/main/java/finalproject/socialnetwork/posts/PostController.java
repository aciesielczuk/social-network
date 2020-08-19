package finalproject.socialnetwork.posts;

import finalproject.socialnetwork.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    PostService postService;
    UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add-post")
    public ResponseEntity addPost(@RequestHeader("username") String username, @RequestBody String postBody) {
        if (!userService.existsUserByUsername(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Post post = postService.addPost(username, postBody);
        return ResponseEntity.ok(postService.savePost(post));
    }
}
