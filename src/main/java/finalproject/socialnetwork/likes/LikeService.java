package finalproject.socialnetwork.likes;

import finalproject.socialnetwork.posts.Post;
import finalproject.socialnetwork.posts.PostRepository;
import finalproject.socialnetwork.users.User;
import finalproject.socialnetwork.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    LikeRepository likeRepository;
    UserRepository userRepository;
    PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Like addLike(String token, int postId) {
        Optional<User> userFromDB = userRepository.findByToken(token);
        Optional<Post> postFromDB = postRepository.findById(postId);
        postFromDB.get().setLikes(postFromDB.get().getLikes() + 1);
        return new Like(userFromDB.get(), postFromDB.get());
    }

    public Like saveLike(Like like) {
        return likeRepository.save(like);
    }

    public Like removeLike(int postId, int id) {
        Optional<Post> postFromDB = postRepository.findById(postId);
        Optional<Like> likeFromDB = likeRepository.findById(id);
        postFromDB.get().setLikes(postFromDB.get().getLikes() - 1);
        return likeFromDB.get();
    }

    public void deleteLike(Like like) {
        likeRepository.delete(like);
    }


}
