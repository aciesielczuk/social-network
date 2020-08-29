package finalproject.socialnetwork.users;

import finalproject.socialnetwork.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public boolean existsUserByUsername(String username) {
        return userRepository.existsUserByUsername(username);
    }

    public User addUser(User user) {
        user.setToken(TokenGenerator.generateToken());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean isPasswordCorrect(Optional<User> userFromDb, User user) {
        System.out.println(userFromDb.get().getPassword());
        System.out.println(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        return passwordEncoder.matches(user.getPassword(), userFromDb.get().getPassword());
        //return userFromDb.get().getPassword().equals(passwordEncoder.encode(user.getPassword()));
    }

    public Optional<User> getUserByToken(String token) {
        return userRepository.findByToken(token);
    }

}