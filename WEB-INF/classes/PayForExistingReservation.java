import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.URLDecoder;

@WebServlet("/PayForExistingReservation")
public class PayForExistingReservation extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String clientIdStr = request.getParameter("clientId");
        String serviceIdStr = request.getParameter("serviceId");
        String dateTimeStr = request.getParameter("dateTime");

        if (clientIdStr == null || serviceIdStr == null || dateTimeStr == null) {
            out.println("<p style='color: red;'>Missing payment data.</p>");
            return;
        }

        // Check if this is a form submission
        String cardNumber = request.getParameter("cardNumber");

        if (cardNumber == null) {
            // Display the payment form
            out.println(Utils.header("Menu"));
            out.println("<div class='author'>Rishi Kalpesh Shah. FR16 & 17. Process Payments</div>");
            out.println("<link rel='stylesheet' href='PaymentCard.css'>");
            out.println("<script src='PaymentCard.js' defer></script>");
           

            out.println("<div class='payment-form'>");
            out.println("<h1>Confirm Payment</h1>");
            out.println("<form id='paymentForm' action='PayForExistingReservation' method='GET'>");

            // Preserve reservation context
            out.printf("<input type='hidden' name='clientId' value='%s'>%n", clientIdStr);
            out.printf("<input type='hidden' name='serviceId' value='%s'>%n", serviceIdStr);
            out.printf("<input type='hidden' name='dateTime' value='%s'>%n", dateTimeStr); // already URL encoded

            out.println("<label for='cardNumber'>Card Number:</label>");
            out.println("<input type='text' id='cardNumber' name='cardNumber' required>");

            out.println("<label for='expiryDate'>Expiry Date:</label>");
            out.println("<input type='month' id='expiryDate' name='expiryDate' required>");

            out.println("<label for='cvv'>CVV:</label>");
            out.println("<input type='text' id='cvv' name='cvv' required>");

            out.println("<div id='errorMsg' class='error-message'></div>");
            out.println("<button type='submit'>Pay Now</button>");
            out.println("</form></div></body></html>");

        } else {
            try {
                int clientId = Integer.parseInt(clientIdStr);
                int serviceId = Integer.parseInt(serviceIdStr);

                // Decode and parse the datetime string (from ISO format)
                String decodedDateTime = URLDecoder.decode(dateTimeStr, "UTF-8");
                LocalDateTime dateTime = LocalDateTime.parse(decodedDateTime);

                Connection conn = ConnectionUtils1.getConnection(getServletConfig());
                boolean success = ReservationEntry.confirmReservationPayment(conn, clientId, serviceId, dateTime);
                ConnectionUtils1.close(conn);

                if (success) {
                    response.sendRedirect("ReservationHistoryView?clientId=" + clientId);
                } else {
                    out.println("<p style='color:red;'>Failed to update payment.</p>");
                }

            } catch (Exception e) {
                e.printStackTrace();
                out.println("<p style='color:red;'>Invalid data format.</p>");
            }
        }
    }
}
