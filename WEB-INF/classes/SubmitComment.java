import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SubmitComment")
public class SubmitComment extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String comentario = request.getParameter("comentario");
        String servicioIDStr = request.getParameter("servicioID");

        if (comentario == null || servicioIDStr == null) {
            out.println("<p>Error: comentario o servicio no especificado.</p>");
            return;
        }

        int servicioID = Integer.parseInt(servicioIDStr);

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = ConnectionUtils.getConnection();

            String sql = "INSERT INTO Comentarios (ServicioID, Comentario, Tiempo) VALUES (?, ?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, servicioID);
            pstmt.setString(2, comentario);

            // Timestamp en formato compatible con Access
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String fechaHora = timestamp.toLocalDateTime().toString().replace('T', ' ').substring(0, 19);
            pstmt.setString(3, fechaHora);

            pstmt.executeUpdate();

            response.sendRedirect("Comments?servicioID=" + servicioID);

        } catch (Exception e) {
            e.printStackTrace(out); // Mostrar error exacto en el navegador
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace(out);
            }
        }
    }
}