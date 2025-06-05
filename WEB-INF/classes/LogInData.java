import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogInData")
public class LogInData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String correo = request.getParameter("correo");
        String contra = request.getParameter("contra");

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionUtils.getConnection();
            String sql = "SELECT Correo, Contra FROM Clientes WHERE Correo = ? AND Contra = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, correo);
            pstmt.setString(2, contra);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Inicio de sesión exitoso
                HttpSession session = request.getSession();
                session.setAttribute("correo", correo);
                
                out.println("<p>Inicio de sesión exitoso.</p>");
                response.sendRedirect("ClientMenuData"); // Redirigir a la página principal
            } else {
                // Credenciales incorrectas
                out.println("<p>Error: Correo o contraseña incorrectos.</p>");
				response.sendRedirect("LogIn");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<p>Error en la base de datos.</p>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}