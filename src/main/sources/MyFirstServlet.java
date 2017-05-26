import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * Created by Екатерина on 24.05.2017.
 */
public class MyFirstServlet extends HttpServlet {

    private Database db = new Database();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        String user = (String) session.getAttribute("user");

        if (user != null) {
            PrintWriter out = resp.getWriter();
            ArrayList<ArrayList<String>> messages = db.getMessages(req.getParameter("limit"));

            if (messages.size() > 0) {
                StringBuilder b = new StringBuilder();
                b.append("[");
                for (ArrayList<String> message : messages) {
                    b.append("{\"user\":\"");
                    b.append(message.get(0));
                    b.append("\",\"message\":\"");
                    b.append(message.get(2));
                    b.append("\",\"date\":\"");
                    b.append(message.get(1));
                    b.append("\"},");
                }
                b.deleteCharAt(b.length() - 1);
                b.append("]");
                out.print(b);
            }
        } else {
            PrintWriter out = resp.getWriter();
            out.print("[{\"user\":\"SYSTEM\",\"message\":\"Authentication Failed\",\"date\":\""
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    + "\"}]");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (db.createMessage((String) req.getParameter("message"), (String) req.getSession().getAttribute("user"))) {
            resp.setStatus(201);
        } else {
            resp.setStatus(500);
        }
    }
}
