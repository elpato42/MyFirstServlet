import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet(name = "Auth")
public class Auth extends HttpServlet {
    private Database db = new Database();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getRequestURI().substring(1);
        PrintWriter out = response.getWriter();

        if (action.equals("login")) {
            try {
                if (db.login(request.getParameter("login"), request.getParameter("password"))) {
                    request.getSession().setAttribute("user", request.getParameter("login"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else if (action.equals("logout")) {
            request.getSession().invalidate();
        } else {
            out.print("<h1>Not Implemented</h1>");
        }

        response.sendRedirect("/");
    }
}
