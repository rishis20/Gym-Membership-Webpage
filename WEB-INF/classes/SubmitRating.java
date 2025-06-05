import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SubmitRating")
public class SubmitRating extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String servicioIDStr = request.getParameter("servicioID");
        String ratingStr = request.getParameter("rating");

        if (servicioIDStr == null || ratingStr == null) {
            out.println("<p>Error: Datos incompletos.</p>");
            return;
        }

        int servicioID = Integer.parseInt(servicioIDStr);
        int rating = Integer.parseInt(ratingStr);

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = ConnectionUtils.getConnection();

            String sql = "INSERT INTO Valoraciones (ServicioID, Rating, Tiempo) VALUES (?, ?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, servicioID);
            pstmt.setInt(2, rating);

            // Fecha y hora actual
            Timestamp ahora = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(3, ahora);

            pstmt.executeUpdate();

            // Redirige de vuelta a la página de valoraciones
            response.sendRedirect("Valorations");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<p>Error al guardar la valoración.</p>");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
