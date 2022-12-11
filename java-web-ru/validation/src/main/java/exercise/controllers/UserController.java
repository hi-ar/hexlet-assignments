package exercise.controllers;

import io.javalin.http.Handler;

import java.util.List;
import java.util.Map;

import io.javalin.core.validation.Validator;
import io.javalin.core.validation.ValidationError;
import io.javalin.core.validation.JavalinValidation;
import org.apache.commons.lang3.StringUtils;

import exercise.domain.User;
import exercise.domain.query.QUser;
import org.apache.commons.validator.routines.EmailValidator;

public final class UserController {

    public static Handler listUsers = ctx -> {

        List<User> users = new QUser()
                .orderBy()
                .id.asc()
                .findList();

        ctx.attribute("users", users);
        ctx.render("users/index.html");
    };

    public static Handler showUser = ctx -> {
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);

        User user = new QUser()
                .id.equalTo(id)
                .findOne();

        ctx.attribute("user", user);
        ctx.render("users/show.html");
    };

    public static Handler newUser = ctx -> {

        ctx.attribute("errors", Map.of());
        ctx.attribute("user", Map.of());
        ctx.render("users/new.html");
    };

    public static Handler createUser = ctx -> {
        // BEGIN Валид: ФИ не пуст. мейл валид. пасворд не меньш 4 симв и только цифры.
        // если не прошли код ответа 422. Стран созд
        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        Validator<String> nameValidator = ctx.formParamAsClass("firstName", String.class)
                .check(value -> !value.isEmpty(), "Name is not difined");

        Validator<String> lastNameValidator = ctx.formParamAsClass("lastName", String.class)
                .check(value -> !value.isEmpty(), "Last name is not difined");

        Validator<String> emailValidator = ctx.formParamAsClass("email", String.class)
                .check(value -> EmailValidator.getInstance().isValid(value), "Email is wrong");

        Validator<String> pwdValidator = ctx.formParamAsClass("password", String.class)
                .check(value -> StringUtils.isNumeric(value), "pwd must contain only digits")
                .check(value -> value.length() >= 4, "pwd must be 4 chars at least");

        Map<String, List<ValidationError<? extends Object>>> errors = JavalinValidation.collectErrors(
                nameValidator, lastNameValidator,emailValidator, pwdValidator);

        if (!errors.isEmpty()) {
            ctx.status(422);
            ctx.attribute("errors", errors);

            User invalidUser = new User(firstName, lastName, email, password);
            ctx.attribute("user", invalidUser);
            ctx.render("users/new.html");
            return;
        }
        User user = new User(firstName, lastName, email, password);
        user.save();
        ctx.sessionAttribute("flash", "User has successfully created");
        ctx.redirect("/users");
        // END
    };
}
