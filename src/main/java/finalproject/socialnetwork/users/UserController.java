package finalproject.socialnetwork.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> addUser(@RequestBody User user) {
        if (userService.existsUserByUsername(user.getUsername())) {
            return ResponseEntity.unprocessableEntity().build();
        }
        User persistUser = userService.addUser(user);
        TokenResponse tokenResponse = new TokenResponse(persistUser.getToken());
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        Optional<User> userFromDb = userService.getUserByUsername(user.getUsername());
        if (userFromDb.isEmpty() || !userService.isPasswordCorrect(userFromDb, user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // stworzyć sesję użytkownika
        return ResponseEntity.ok().build();
    }

    public class TokenResponse {
        private String token;

        public TokenResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

}