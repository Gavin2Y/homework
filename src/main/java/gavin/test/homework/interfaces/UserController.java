package gavin.test.homework.interfaces;

import gavin.test.homework.application.UserApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserApplication userApplication;

    @GetMapping("/{userId}/session")
    public String login(@PathVariable("userId") String userId) {
        return userApplication.login(userId);
    }

}
