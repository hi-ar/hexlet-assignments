package exercise.controller;
import exercise.model.User;
import exercise.model.QUser;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

// Зависимости для самостоятельной работы
// import org.springframework.data.querydsl.binding.QuerydslPredicate;
// import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    // BEGIN
    @GetMapping
    public Iterable<User> findUsers(
            @RequestParam(value = "firstName", required = false) String name2search,
            @RequestParam(value = "lastName", required = false) String last2search) {

        name2search = name2search == null ? "" : name2search;
        last2search = last2search == null ? "" : last2search;

        Iterable<User> result = userRepository.findAll(
                QUser.user.firstName.containsIgnoreCase(name2search).and(
                        QUser.user.lastName.containsIgnoreCase(last2search)
                )
        );
        return result;
    }
}