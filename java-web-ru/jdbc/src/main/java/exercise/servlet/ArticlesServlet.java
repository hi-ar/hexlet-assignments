package exercise.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang3.ArrayUtils;

import exercise.TemplateEngineUtil;


public class ArticlesServlet extends HttpServlet {

    private String getId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return null;
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 1, null);
    }

    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return "list";
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 2, getId(request));
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "list":
                showArticles(request, response);
                break;
            default:
                showArticle(request, response);
                break;
        }
    }

    private void showArticles(HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException, ServletException {

        ServletContext context = request.getServletContext();
        Connection connection = (Connection) context.getAttribute("dbConnection");
        // BEGIN выводит спис всех стат по 10 стр. Ссылк впер, назд. page в риквест
        String pageStr = request.getParameter("page");
        int page = (pageStr == null) ? 1 : Integer.parseInt(pageStr);
        int artsPerPage = 10;
        List<Map<String, String>> articles = new ArrayList<>();
        String query = "SELECT * FROM articles ORDER BY id LIMIT ? OFFSET ?";
        int sizeOfDB = 0;
        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT count(*) FROM articles");
            rs.next();
            sizeOfDB = rs.getInt(1);

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, artsPerPage);
            statement.setInt(2, artsPerPage * (page - 1));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Map<String, String> article = new HashMap<>();
                article.put("id", resultSet.getString("id"));
                article.put("title", resultSet.getString("title"));
                article.put("body", resultSet.getString("body"));
                articles.add(article);
            }

        } catch (SQLException e) {
            response.sendError(500, "sizeOfDB: " + sizeOfDB);
            return;
        }
        boolean hasNext = sizeOfDB > page * artsPerPage;
        boolean hasPrev = page > 1;
        request.setAttribute("sizeOfDB", sizeOfDB);
        request.setAttribute("articles", articles);
        request.setAttribute("page", page);
        request.setAttribute("hasNext", hasNext);
        request.setAttribute("hasPrev", hasPrev);
        // END
        TemplateEngineUtil.render("articles/index.html", request, response);
    }

    private void showArticle(HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException, ServletException {

        ServletContext context = request.getServletContext();
        Connection connection = (Connection) context.getAttribute("dbConnection");
        // BEGIN кажд стат содерж 3 поля (id title, body)
        //int id = getId(request) == null ? 99 : Integer.parseInt(getId(request));
        int id = Integer.parseInt(getId(request));
        Map<String, String> article = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM articles WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            article.put("id", resultSet.getString("id"));
            article.put("title", resultSet.getString("title"));
            article.put("body", resultSet.getString("body"));

        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        request.setAttribute("article", article);
        // END
        TemplateEngineUtil.render("articles/show.html", request, response);
    }
}
