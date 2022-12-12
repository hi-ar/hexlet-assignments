package exercise.controllers;

import io.javalin.http.Context;
import io.javalin.apibuilder.CrudHandler;
import io.ebean.DB;
import java.util.List;

import exercise.domain.User;
import exercise.domain.query.QUser;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.lang3.StringUtils;

public class UserController implements CrudHandler {

    public void getAll(Context ctx) {
        // BEGIN все польз в виде json DB.json().toJson()
        List<User> users = new QUser().id.asc().findList();
        String jsonUsers = DB.json().toJson(users);
        ctx.json(jsonUsers);
        // END
    };

    public void getOne(Context ctx, String id) {

        // BEGIN
        User user = new QUser().id.equalTo(Integer.parseInt(id)).findOne();
        String jsonUser = DB.json().toJson(user);
        ctx.json(jsonUser);
        // END
    };

    public void create(Context ctx) {

        // BEGIN
        String jsonUser = ctx.body();
        User user = DB.json().toBean(User.class, jsonUser);
        user.save();
        // END
    };

    public void update(Context ctx, String id) {
        // BEGIN
        String jsonUser = ctx.body();
        User user = DB.json().toBean(User.class, jsonUser);

        new QUser().id.equalTo(Integer.parseInt(id)).asUpdate()
                .set("firstName", user.getFirstName())
                .set("lastName", user.getLastName())
                .set("email", user.getEmail())
                .update();
        // END
    };

    public void delete(Context ctx, String id) {
        // BEGIN
        new QUser().id.equalTo(Integer.parseInt(id)).delete();
        // END
    };
}
