package finalproject.socialnetwork.users;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

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
        return userRepository.save(user);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean isPasswordCorrect(Optional<User> userFromDb, User user) {
        return userFromDb.get().getPassword().equals(user.getPassword());
    }

}