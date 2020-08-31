package finalproject.socialnetwork.likes;

import finalproject.socialnetwork.posts.PostService;
import finalproject.socialnetwork.users.User;
import finalproject.socialnetwork.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@Controller
public class LikeController {

    LikeService likeService;
    UserService userService;
    PostService postService;

    public LikeController(LikeService likeService, UserService userService, PostService postService) {
        this.likeService = likeService;
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/likes")
    public ResponseEntity<Like> likePost(@RequestHeader("x-authorization-token") String token, @RequestHeader("postId") int postId) {
        Optional<User> userFromDB = userService.getUserByToken(token);
        if (userFromDB.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (postService.findById(postId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (likeService.isLikedByUser(token, postId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Like like = likeService.addLike(token, postId);
        return ResponseEntity.ok(likeService.saveLike(like));
    }

    @DeleteMapping("/likes")
    public ResponseEntity<Like> dislikePost(@RequestHeader("x-authorization-token") String token, @RequestHeader("postId") int postId) {
        Optional<User> userFromDB = userService.getUserByToken(token);
        if (userFromDB.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (postService.findById(postId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (!likeService.isLikedByUser(token, postId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Like like = likeService.removeLike(token, postId);
        likeService.deleteLike(like);
        return ResponseEntity.ok().build();
    }
}
