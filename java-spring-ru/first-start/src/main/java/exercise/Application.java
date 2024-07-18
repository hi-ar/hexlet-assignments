package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@SpringBootApplication
@RestController
class Application {

    @GetMapping(path = "about")
    String home() {
        return "Welcome to Hexlet!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
// END
