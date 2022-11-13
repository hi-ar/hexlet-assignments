package exercise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.ArrayUtils;

public class UsersServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String pathInfo = request.getPathInfo(); // :5000 "users/14"

        if (pathInfo == null) {
            showUsers(request, response);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String id = ArrayUtils.get(pathParts, 1, "");

        showUser(request, response, id);
    }

    // Реализуйте в методе getUsers() логику получения списка пользователей из файла
    // src/main/resources/users.json. Для парсинга содержимого файла в объект List используйте
    // библиотеку jackson (нужные классы уже импортированы в файл).
    private List<Map<String, String>> getUsers() throws JsonProcessingException, IOException {
        // BEGIN
        String fileName = "users.json";
        Path file = Paths.get("src", "main", "resources", fileName).toAbsolutePath().normalize();
        String fileContent = Files.readString(file);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(fileContent,
                new TypeReference<List<Map<String, String>>>() {
                });
        // END
    }

    //В методе showUsers() получите список пользователей, с помощью ранее созданного метода getUsers(),
    // и выведите его в браузер используя HTML. Отобразите список в табличном виде с полями:
    // id и fullName (состоит из имени пользователя и фамилии разделённых пробелом).
    // Список будет доступен в браузере по пути /users.
    private void showUsers(HttpServletRequest request,
                           HttpServletResponse response)
            throws IOException {

        // BEGIN
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        out.write("<html><head></head><body><table>");
        for (Map<String, String> map : getUsers()) {
            String fullName = map.get("firstName") + " " + map.get("lastName");
            out.write("<tr><td>" + map.get("id") + "</td><td><a href=\"/users/" + map.get("id") + "\">" + fullName
                    + "</a></td></tr>");
        }
        out.write("</table></body></html>");
        out.close();
        // END
    }

    private void showUser(HttpServletRequest request,
                          HttpServletResponse response,
                          String id)
            throws IOException {

        // BEGIN
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        out.write("<html><head></head><body><table>");
        if (!id.isEmpty()) {
            for (Map<String, String> map : getUsers()) {
                if (map.get("id").equals(id)) {
                    String fullName = map.get("firstName") + " " + map.get("lastName");
                    out.write("<tr><td>" + map.get("id") + "</td>"
                            + "<td>" + fullName + "</td>"
                            + "<td>" + map.get("email") + "</td></tr>");
                    out.write("</table></body></html>");
                    out.close();
                    return;
                }
            }
        }
        out.write("</table><h1>Not found</h1></body></html>");
        out.close();
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        // END
    }
}
