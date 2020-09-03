package finalproject.socialnetwork.posts;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.socialnetwork.security.SecurityConfiguration;
import finalproject.socialnetwork.users.User;
import finalproject.socialnetwork.users.UserController;
import finalproject.socialnetwork.users.UserRepository;
import finalproject.socialnetwork.users.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {UserController.class, UserService.class, UserRepository.class, SecurityConfiguration.class})
@WebMvcTest(value = UserController.class)
class PostControllerTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    PostController postController;

    @MockBean
    PostService postService;


    @Autowired
    private MockMvc mockMvc;

    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addPost() throws Exception {
        User user = new User(1, "testUser1", "testPassword");
        user.setToken("testToken");
        Post post = new Post(1, user, "testPostBody");

        Mockito.when(postController.addPost(eq(user.getToken()), Mockito.any(Post.class))).thenReturn(ResponseEntity.ok(post));

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("x-authorization-token", user.getToken())
                .content(asJsonString(post))
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":1,\"user\":{\"id\":1,\"username\":\"testUser1\"},\"postBody\":\"testPostBody\",\"likes\":null}"))
                .andReturn();
    }

    @Test
    void getPosts() throws Exception {
        User user = new User(1, "testUser1", "testPassword");
        user.setToken("testToken");
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1, user, "testPostBody1"));
        posts.add(new Post(2, user, "testPostBody2"));

        Mockito.when(postController.getPosts(eq(user.getToken()))).thenReturn(ResponseEntity.ok(posts));

        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("x-authorization-token", user.getToken())
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .string("[{\"id\":1,\"user\":{\"id\":1,\"username\":\"testUser1\"},\"postBody\":\"testPostBody1\",\"likes\":null},{\"id\":2,\"user\":{\"id\":1,\"username\":\"testUser1\"},\"postBody\":\"testPostBody2\",\"likes\":null}]"))
                .andReturn();
    }
}