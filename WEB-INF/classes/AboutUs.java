import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class AboutUs extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        toClient.println(Utils.header("About Us"));

        toClient.println("<div class='about-container'>");
        toClient.println("<div class='login-container'>");
        toClient.println("<div class='author'>Mikel Montejo. F01. AboutUs </div>");
        toClient.println("<h1>About Us</h1>");

        toClient.println("<p>Welcome to our fitness community! At our gym, we are committed to supporting you every step of the way on your journey to better health and wellness. Our state-of-the-art facilities are equipped with the latest workout machines, dedicated areas for strength and cardio training, and a wide range of group classes led by certified professionals.</p>");

        toClient.println("<p>We pride ourselves on creating an inclusive and motivating environment for people of all fitness levels. Whether you're just getting started or looking to push your limits, our experienced trainers are here to guide and inspire you.</p>");

        toClient.println("<p>We are proud to serve our members in multiple cities across Spain. You can find us in:</p>");
        toClient.println("<ul>");
        toClient.println("<p><strong>Donostia-San Sebastian</strong></p>");
        toClient.println("<p><strong>Barcelona</strong></p>");
        toClient.println("<p><strong>Madrid</strong></p>");
        toClient.println("</ul>");

        toClient.println("<h3>Opening Hours:</h3>");
        toClient.println("<p>Open every day from <strong>8:00 AM to 9:00 PM</strong></p>");

        toClient.println("<a class='button' href='Session'>Back to Home</a>");
        toClient.println("</div>");
        toClient.println("</div>");

        toClient.close();
    }
}
