import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by Екатерина on 24.05.2017.
 */
public class MyFirstServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Database db = null;
        try {
            db = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String user = (String) session.getAttribute("user");

        if (user != null) {
            PrintWriter out = resp.getWriter();
            ArrayList<ArrayList<String>> messages = db.getMessages();

            out.print("[");
            for (ArrayList<String> message : messages) {
                out.print("{\"user\":\"" + message.get(0) + "\",\"message\":\"" + message.get(2) + "\",\"date\":\"" + message.get(1) + "\"}");
            }
            out.print("]");
        } else {
            PrintWriter out = resp.getWriter();
            out.print("[{\"user\":\"SYSTEM\",\"message\":\"Authentication Failed\",\"date\":\""
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
                    + "\"}]");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Database db = null;
        try {
            db = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (db.createMessage((String) req.getParameter("message"), (String) req.getSession().getAttribute("user"))) {
            resp.setStatus(201);
        } else {
            resp.setStatus(500);
        }
    }
}
