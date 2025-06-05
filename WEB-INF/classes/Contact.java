import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Contact extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        toClient.println(Utils.header("Contact"));

        toClient.println("<div class='about-container'>");
        toClient.println("<div class='login-container'>");
        toClient.println("<div class='author'>Mikel Montejo. F02. Contact </div>");

        toClient.println("<h1>Contact</h1>");
        toClient.println("<p>You can reach us through multiple channels. Visit our gym in person, call us, or send us an email. Our staff is always available to answer your questions and help you get started on your fitness journey. We look forward to hearing from you!</p>");

        // Teléfono
        toClient.println("<a class='phone-link' href='tel:+943123456'>+34 943 123 456</a>");

        // Email
        toClient.println("<a class='email-button' href='SendEmail'>Send an Email</a>");

        // Volver a home (Session servlet)
        toClient.println("<a class='back-button' href='" + req.getContextPath() + "/Session'>Back to Home</a>");

        toClient.println("</div>");
        toClient.println("</div>");

        toClient.close();
    }
}
