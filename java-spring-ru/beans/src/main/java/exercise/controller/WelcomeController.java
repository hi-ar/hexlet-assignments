package exercise.controller;

import exercise.daytime.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

// BEGIN
@RestController
@RequestMapping("/welcome")
class WelcomeController {

    @Autowired
    Daytime daytime;
    @GetMapping
    public String greeting() {
        return new String("It is " + daytime.getName() + " now! Welcome to Spring!");
    }
}
// END
