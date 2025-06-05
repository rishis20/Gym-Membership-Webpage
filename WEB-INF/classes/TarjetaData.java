import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class TarjetaData extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener datos del formulario
        String numero = request.getParameter("numero");
        String fecha = request.getParameter("fecha");
        String cvv = request.getParameter("cvv");

        // Obtener el correo desde la sesión del usuario
        HttpSession session = request.getSession();
        String correo = (String) session.getAttribute("correo");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (correo == null) {
            out.println("Error: sesión expirada o no iniciada.");
            return;
        }

        // Obtener cliente_id desde la base de datos usando el correo
        int clienteId = obtenerClienteId(correo);

        if (clienteId == -1) {
            out.println("Error: usuario no encontrado.");
            return;
        }

        Connection connection = null;
        PreparedStatement pstmt = null;
        
        try {
            // Usar el método de ConnectionUtils para obtener la conexión
            connection = ConnectionUtils.getConnection();

            // Insertar la tarjeta de crédito
            String sql = "INSERT INTO TarjetasCredito (clienteId, numero, fecha, cvv) VALUES (?, ?, ?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, clienteId);
            pstmt.setString(2, numero);
            pstmt.setString(3, fecha);
            pstmt.setString(4, cvv);

            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                out.println("Tarjeta agregada exitosamente.");
            } else {
                out.println("Error al agregar la tarjeta.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(out);
            out.println("<p>Error en la base de datos.</p>");
        } finally {
            // Cerrar la conexión y los recursos
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para obtener el cliente_id a partir del correo
    private int obtenerClienteId(String correo) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int clienteId = -1;

        try {
            // Usar el método de ConnectionUtils para obtener la conexión
            connection = ConnectionUtils.getConnection();
            
            String sql = "SELECT id FROM Clientes WHERE Correo = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, correo);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                clienteId = rs.getInt("id");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clienteId;
    }
}
