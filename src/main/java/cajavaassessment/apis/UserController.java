package cajavaassessment.apis;

import cajavaassessment.dto.UserDTO;
import cajavaassessment.request.UserRequest;
import cajavaassessment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User resource
 *
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * Create a user
     *
     * @param request description
     * @return new user
     */
    @PostMapping("/users")
    public List<UserDTO> createUser(@RequestBody UserRequest request) {
        List<UserDTO> usersDTO = new ArrayList<>(1);
        UserDTO userDTO = userService.createUser(request);
        if (Objects.nonNull(userDTO)) {
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }

    /**
     * Update a user
     *
     * @param userId description
     * @param request description
     * @return updated user
     */
    @PutMapping("/users/{userId}")
    public List<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserRequest request) {
        List<UserDTO> usersDTO = new ArrayList<>();
        UserDTO userDTO = userService.updateUser(userId, request);
        if (Objects.nonNull(userDTO)) {
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }

    /**
     * Get a User info
     *
     * @return a user
     */
    @GetMapping("/users/{userId}")
    public List<UserDTO> getUser(@PathVariable(value = "userId") Long userId) {
        List<UserDTO> usersDTO = new ArrayList<>();
        UserDTO userDTO = userService.getUser(userId);
        if (Objects.nonNull(userDTO)) {
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }

    /**
     * Get Users info
     *
     * @return list of users
     */
    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

}
