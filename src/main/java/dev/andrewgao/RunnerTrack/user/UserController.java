package dev.andrewgao.RunnerTrack.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserHttpClient client;

    UserController(UserHttpClient client) {
        this.client = client;
    }

    @GetMapping("")
    List<User> getAllUsers() {
        return client.getAllUsers();
    }

    @GetMapping("/{id}")
    User getUserById(@PathVariable Integer id) {
        return client.getUserById(id);
    }
}
