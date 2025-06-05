import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/PaymentCard")
public class PaymentCard extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Parse reservation data from URL
        String clientId = request.getParameter("clientId");
        String serviceId = request.getParameter("serviceId");
        String selectedDate = request.getParameter("selectedDate");
        String selectedTime = request.getParameter("selectedTime");

        // Show payment form
        out.println(Utils.header("Menu"));
        out.println("<link rel='stylesheet' href='PaymentCard.css'>");
        out.println("<script src='PaymentCard.js' defer></script>");

        out.println("<div class='author'>Rishi Kalpesh Shah. FR16 & 17. Process Payments</div>");
        
        

        out.println("<div class='payment-form'>");
        out.println("<h1>Enter Payment Details</h1>");
        out.println("<form id='paymentForm' action='AddReservationPaid' method='GET'>");

        // Card Info Inputs
        out.println("<label for='cardNumber'>Card Number:</label>");
        out.println("<input type='text' id='cardNumber' name='cardNumber' required>");

        out.println("<label for='expiryDate'>Expiry Date:</label>");
        out.println("<input type='month' id='expiryDate' name='expiryDate' required>");

        out.println("<label for='cvv'>CVV:</label>");
        out.println("<input type='text' id='cvv' name='cvv' required>");

        out.println("<div id='errorMsg' class='error-message'></div>");

        // Hidden reservation fields
        out.printf("<input type='hidden' name='clientId' value='%s'>%n", clientId);
        out.printf("<input type='hidden' name='serviceId' value='%s'>%n", serviceId);
        out.printf("<input type='hidden' name='selectedDate' value='%s'>%n", selectedDate);
        out.printf("<input type='hidden' name='selectedTime' value='%s'>%n", selectedTime);

        out.println("<button type='submit'>Pay Now</button>");
        out.println("</form>");
        out.println("</div></body></html>");
    }
}
