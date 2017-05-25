import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Екатерина on 24.05.2017.
 */
public class MyFirstServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {

            req.getRequestDispatcher("mypage.jsp").forward(req, resp);
        } catch (Exception e) {PrintWriter out = resp.getWriter();
            out.print("<h1>File is not found</h1>");}



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().write(req.getParameter("message"));
        resp.setStatus(200);

    }
}
/*

    <dependency>
      <groupId>joda-time</groupId>
      <artifactId>joda-time</artifactId>
      <version>2.2</version>
    </dependency>
    */
