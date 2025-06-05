import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddReservationPaid extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String clientIdStr = request.getParameter("clientId");
        String serviceIdStr = request.getParameter("serviceId");
        String dateStr = request.getParameter("selectedDate");
        String timeStr = request.getParameter("selectedTime");

        if (clientIdStr == null || serviceIdStr == null || dateStr == null || timeStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters.");
            return;
        }

        Connection conn = null;

        try {
            int clientId = Integer.parseInt(clientIdStr);
            int serviceId = Integer.parseInt(serviceIdStr);

            // Combine and parse the datetime
            String dateTimeStr = dateStr.trim() + " " + timeStr.trim(); // e.g., "2025-04-25 08:00"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime reservationDateTime = LocalDateTime.parse(dateTimeStr, formatter);

            // Insert into ReservationEntry table (custom insert function)
            conn = ConnectionUtils1.getConnection(getServletConfig());
            boolean success = ReservationEntry.insertReservation(conn, clientId, serviceId, reservationDateTime, true);

            if (success) {
                response.sendRedirect("ReservationHistoryView?clientId=" + clientId);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save reservation.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().println("Failed to parse or save input:");
            response.getWriter().println("clientId = " + clientIdStr);
            response.getWriter().println("serviceId = " + serviceIdStr);
            response.getWriter().println("date = " + dateStr);
            response.getWriter().println("time = " + timeStr);
            response.getWriter().println("Combined = " + dateStr.trim() + " " + timeStr.trim());
            response.getWriter().println("Error: " + e.getMessage());
        } finally {
            ConnectionUtils1.close(conn);
        }
    }
}
