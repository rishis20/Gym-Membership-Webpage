import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SignUpData")
public class SignUpData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String dni = request.getParameter("dni");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");
        String direccion = request.getParameter("direccion");
        String fechaRegis = request.getParameter("fechaRegis");
        String contra = request.getParameter("contra");

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection =  ConnectionUtils.getConnection();
            String sql = "INSERT INTO Clientes (Nombre, Apellidos, DNI, Telefono, Correo, Direccion, FechaRegis, Contra) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellidos);
            pstmt.setString(3, dni);
            pstmt.setString(4, telefono);
            pstmt.setString(5, correo);
            pstmt.setString(6, direccion);
            pstmt.setString(7, fechaRegis);
            pstmt.setString(8, contra);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<p>Registro exitoso.</p>");
				response.sendRedirect("Session");
            } else {
                out.println("<p>Error en el registro.</p>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<p>Error en la base de datos.</p>");
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
