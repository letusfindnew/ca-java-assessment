package cajavaassessment.service;

import cajavaassessment.dto.UserDTO;
import cajavaassessment.model.User;
import cajavaassessment.repository.UserRepository;
import cajavaassessment.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserDTO createUser(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUserName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setTasks(new ArrayList<>());
        repository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        return userDTO;
    }

    public UserDTO updateUser(Long userId, UserRequest request) {
        UserDTO userDTO = null;
        Optional<User> fromDB = repository.findById(userId);

        if (fromDB.isPresent()) {
            User user = fromDB.get();
            user.setUsername(request.getUserName());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            repository.save(user);

            userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUserName(user.getUsername());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
        }
        return userDTO;
    }

    public UserDTO getUser(Long userId) {
        UserDTO userDTO = null;
        Optional<User> fromDB = repository.findById(userId);
        if (fromDB.isPresent()) {
            User user = fromDB.get();
            userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUserName(user.getUsername());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
        }
        return userDTO;
    }

    public List<UserDTO> getUsers() {
        Iterable<User> users = repository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUserName(user.getUsername());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());

            usersDTO.add(userDTO);
        }
        return usersDTO;
    }

}
