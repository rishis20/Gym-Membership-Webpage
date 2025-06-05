import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LogIn")
public class LogIn extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(Utils.header("Login"));
        out.println("<div class='author'>Alvaro Sola. F02. LogIn </div>");
        out.println("<h2></h2>");
		out.println("<div class='menu'>");
		out.println("<div class='label'>");
		out.println("<div class='botton'>");
        out.println("<form action='LogInData' method='post'>");
        out.println("<label for='correo'>Email:</label>");
		out.println("<input type='email' id='correo' name='correo' required>");
		out.println(" <label for='contra'>Password:</label>");
		out.println("<input type='password' id='contra' name='contra' placeholder='contra' required>");
		out.println("</div>");
        out.println("<button type='submit' class='button'>Login</button>");
        out.println("</form>");
    }
}