package finalproject.socialnetwork.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/users")
    public ResponseEntity getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody User user) {
        if (userService.existsUserByUsername(user.getUsername())) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(userService.addUser(user));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        Optional <User> userFromDb = userService.getUserByUsername(user.getUsername());
        if (userFromDb.isEmpty() || !userService.isPasswordCorrect(userFromDb,user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }

}