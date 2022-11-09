package exercise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static exercise.Data.getCompanies;

@WebServlet("/companies")
public class CompaniesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        PrintWriter out = response.getWriter();
        if (request.getQueryString() == null || Objects.equals(request.getParameter("search"), "")) {
            for (String company : getCompanies()) {
                out.write(company + "\n");
            }
            out.close();
            return;
        }
        String searchingValue = request.getParameter("search");

        List<String> result = getCompanies().stream()
                .filter(company -> company.contains(searchingValue))
                .toList();

        if (result.isEmpty()) {
            out.println("Companies not found");
            return;
        }

        result.forEach(out::println);
        out.close();
        // END
    }
}
