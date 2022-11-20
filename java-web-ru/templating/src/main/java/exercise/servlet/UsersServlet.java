package exercise.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import static exercise.Data.getUsers;

public class UsersServlet extends HttpServlet {

    private List<Map<String, String>> users = getUsers(); //лист пользователей

    private String getId(HttpServletRequest request) {
        return request.getParameter("id");
    }

    private String getAction(HttpServletRequest request) {      //разделяет, возвращает / "abc"
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 1, "");
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {                 //если path пустой отобразить юзеров
            showUsers(request, response);
            return;
        }

        String action = getAction(request);

        switch (action) {                   // разные действия от path abc
            case "show":
                showUser(request, response);   // show - showUser()
                break;
            case "delete":
                showDeletePage(request, response); // del - delUser()
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "delete":
                deleteUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showUsers(HttpServletRequest request,
                          HttpServletResponse response)
                throws IOException, ServletException {

        // В методе showUsers() реализуйте передачу управления в jsp-файл /users.jsp.
        // Для того, чтобы предать данные пользователей в jsp-файл, вам потребуется установить атрибуты
        // запроса при помощи метода setAttribute().
        request.setAttribute("users", users); //сохраняем атриб в запросе
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/users.jsp");
        requestDispatcher.forward(request, response); //передаем управление в jsp
        // END
    }


    private void showUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {
        // Получаем id пользователя из строки запроса
        String id = getId(request);

        // Получаем пользователя по его id
        Map<String, String> user = getUserById(id);

        // Если пользователь не найден, нужно вернуть код ответа 404
        if (user == null) {
            response.sendError(404);
            return;
        }
        // BEGIN
        request.setAttribute("user", user);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/show.jsp");
        requestDispatcher.forward(request, response); 
        // END
    }

    private void showDeletePage(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // BEGIN
        request.setAttribute("id", id);  //здесь ошибка
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/delete.jsp");
        requestDispatcher.forward(request, response);
        // END
    }

    private void deleteUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // BEGIN
        users.remove(user);
        response.sendRedirect("/users");
        // END
    }

    private Map<String, String> getUserById(String id) {
        Map<String, String> user = users
            .stream()
            .filter(u -> u.get("id").equals(id))
            .findAny()
            .orElse(null);

        return user;
    }
}
