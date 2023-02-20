package exercise;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
class WelcomeController {
    @GetMapping("/")
    public static String greeting() {
        return "Welcome to Spring";
    }

    @GetMapping("/hello")  // /hello?name=John
    public static String greetingByName(@RequestParam(defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }
}
// END
