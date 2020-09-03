package finalproject.socialnetwork.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.socialnetwork.security.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {UserController.class, UserService.class, UserRepository.class, SecurityConfiguration.class})
@WebMvcTest(value = UserController.class)
class UserControllerTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserService userService;

    @MockBean
    UserController userController;

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
    void getUsersTest() throws Exception {

        User user = new User(1, "testUser1", "testPassword");
        List<User> testUsers = new ArrayList<>();
        testUsers.add(user);

        Mockito.when(userController.getUsers()).thenReturn(ResponseEntity.ok(testUsers));

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\": 1, \"username\": \"testUser1\"}]"))
                .andReturn();
    }

    @Test
    void registerUserTest() throws Exception {
        User user = new User(1, "testUser1", "testPassword");
        user.setToken("testToken");
        UserController.TokenResponse tokenResponse = new UserController.TokenResponse(user.getToken(), user.getId());

        ResponseEntity<UserController.TokenResponse> responseEntity = new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        Mockito.when(userController.registerUser(Mockito.any(User.class))).thenReturn((responseEntity));

        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"token\":\"testToken\",\"userId\":1}"))
                .andReturn();
    }

    @Test
    void login() throws Exception {
        User user = new User(1, "testUser1", "testPassword");
        user.setToken("testToken");
        UserController.TokenResponse tokenResponse = new UserController.TokenResponse(user.getToken(), user.getId());

        ResponseEntity<UserController.TokenResponse> responseEntity = new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        Mockito.when(userController.registerUser(Mockito.any(User.class))).thenReturn((responseEntity));

        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"token\":\"testToken\",\"userId\":1}"))
                .andReturn();
    }
}