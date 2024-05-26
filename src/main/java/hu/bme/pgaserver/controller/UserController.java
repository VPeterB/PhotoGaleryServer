package hu.bme.pgaserver.controller;

import hu.bme.pgaserver.dto.UserLoginDTO;
import hu.bme.pgaserver.dto.UserRegDTO;
import hu.bme.pgaserver.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@Validated
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegDTO userDTO) {
        return userService.register(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDTO userDTO) {
        return userService.login(userDTO);
    }

    @GetMapping("/checkName/{name}")
    public ResponseEntity<Boolean> checkUserNameExist(@PathVariable String name) {
        return userService.checkUserNameExist(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}