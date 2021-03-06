package finalproject.socialnetwork.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<TokenResponse> registerUser(@RequestBody User user) {
        if (userService.existsUserByUsername(user.getUsername())) {
            return ResponseEntity.unprocessableEntity().build();
        }
        User persistUser = userService.addUser(user);
        TokenResponse tokenResponse = new TokenResponse(persistUser.getToken(), persistUser.getId());
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody User user) {
        Optional<User> userFromDb = userService.getUserByUsername(user.getUsername());
        if (userFromDb.isEmpty() || !userService.isPasswordCorrect(userFromDb, user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        TokenResponse tokenResponse = new TokenResponse(userFromDb.get().getToken(), userFromDb.get().getId());
        return ResponseEntity.ok(tokenResponse);
    }

    public static class TokenResponse {
        private String token;
        private int userId;

        public TokenResponse(String token, int userId) {
            this.token = token;
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public int getUserId() {
            return userId;
        }
    }

}