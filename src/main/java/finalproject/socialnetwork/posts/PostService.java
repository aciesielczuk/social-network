package finalproject.socialnetwork.posts;

import finalproject.socialnetwork.users.User;
import finalproject.socialnetwork.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById((long) id);
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public Post addPost(String token, String postBody) {
        Optional<User> userFromDB = userRepository.findByToken(token);
        return new Post(userFromDB.get(), postBody);
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }


}
