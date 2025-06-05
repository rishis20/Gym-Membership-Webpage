import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

@WebServlet("/ReservationMenu")
public class ReservationMenu extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String clientIdStr = request.getParameter("clientId");
        if (clientIdStr == null || clientIdStr.isEmpty()) {
            out.println("<p style='color: red;'>Missing client ID.</p>");
            return;
        }

        int clientId = Integer.parseInt(clientIdStr);
        Connection conn = ConnectionUtils1.getConnection(getServletConfig());
        Vector<Services> services = Services.getServicesList(conn);

        out.println(Utils.header("Make a Reservation")); // this includes <html>, <head>, <body>
        out.println(
                "<div class='author'>Rishi Kalpesh Shah. FR 11. Schedule Reservation and Select Service</div>");
       
        out.println("<link rel='stylesheet' href='ReservationMenu.css'>");
        out.println("<script src='ReservationMenu.js' defer></script>");
        out.println("<body>");

        out.println("<h1></h1>");

        out.println("<div class='menu'>");
        out.println("<div id='reservationForm'>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
        out.println("<table>");
		out.println("<h1>Make a Reservation</h1>");
        out.println("<thead><tr><th>Service</th><th>Description</th><th>Cost</th></tr></thead>");
        out.println("<tbody>");
        for (Services s : services) {
            out.printf(
                    "<tr data-service-id='%d' onclick=\"selectService(this, %d)\"><td>%s</td><td>%s</td><td>%.2f</td></tr>",
                    s.getId(), s.getId(), s.getName(), s.getDescription(), s.getCost());

        }
        out.println("</tbody></table>");
        out.println("<input type='hidden' id='selectedServiceID'>");
        out.println("</div>");

        out.println("<div id='dateAndTimeForm' class='hidden'>");
        out.println("  <label for='selectedDate'>Select a date:</label>");
        out.println("  <input type='date' id='selectedDate'>");

        out.println("  <label for='selectedTime'>Select a time slot:</label>");
        out.println("  <select id='selectedTime'>");
        out.println("    <option value=''>-- Select a time --</option>");

        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("h:mm a");
        DateTimeFormatter valueFormat = DateTimeFormatter.ofPattern("HH:mm");

        for (int hour = 8; hour < 21; hour++) {
            LocalTime start = LocalTime.of(hour, 0);
            String displayLabel = start.format(displayFormat) + " - " + start.plusHours(1).format(displayFormat);
            String value = start.format(valueFormat);
            out.printf("<option value='%s'>%s</option>%n", value, displayLabel);
        }

        out.println("  </select>");

        out.println("  <div class='payment-buttons' style='display: flex; gap: 10px;'>");
        out.println("    <button type='button' id='payWithCardButton'>Pay with Card</button>");
        out.println("    <button type='button' id='payAtGymButton'>Pay at the Gym</button>");
        out.println("  </div>");


        out.println("</div>"); // end dateAndTimeForm
        out.println("</div>"); // end menu
        out.println("</body></html>");
        out.println(Utils.footer("Reservation Menu"));
        ConnectionUtils1.close(conn);
    }
}