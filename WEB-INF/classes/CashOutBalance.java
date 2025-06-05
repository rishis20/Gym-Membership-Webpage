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

@WebServlet("/CashOutBalance")
public class CashOutBalance extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        String correo = (String) session.getAttribute("correo");

        if (correo == null) {
            response.sendRedirect("LogIn");
            return;
        }

        String amountStr = request.getParameter("amount");
        String motivo = request.getParameter("motivo");
        String fechaStr = request.getParameter("fecha");

        double amount;
        java.sql.Date fechaMovimiento = null;

        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            out.println("<p>Invalid amount.</p>");
            return;
        }

        if (fechaStr != null && !fechaStr.isEmpty()) {
            try {
                fechaMovimiento = java.sql.Date.valueOf(fechaStr);
            } catch (IllegalArgumentException e) {
                out.println("<p>Invalid date format.</p>");
                return;
            }
        }

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionUtils.getConnection();

            int clienteId = -1;
            String sqlCliente = "SELECT ClienteID FROM Clientes WHERE Correo = ?";
            pstmt = connection.prepareStatement(sqlCliente);
            pstmt.setString(1, correo);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                clienteId = rs.getInt("ClienteID");
            } else {
                out.println("<p>Usuario no encontrado.</p>");
                return;
            }

            rs.close();
            pstmt.close();

            // Restar al balance
            String sqlUpdate = "UPDATE Clientes SET Balance = Balance - ? WHERE ClienteID = ?";
            pstmt = connection.prepareStatement(sqlUpdate);
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, clienteId);
            pstmt.executeUpdate();
            pstmt.close();

			// Insertar transacción CASHOUT
			String sqlInsert = "INSERT INTO MovimientosBalance (ClienteID, Tipo, Cantidad, Motivo, Fecha) VALUES (?, ?, ?, ?, ?)";
			pstmt = connection.prepareStatement(sqlInsert);
			pstmt.setInt(1, clienteId);
			pstmt.setString(2, "CASHOUT");
			pstmt.setDouble(3, amount);
			pstmt.setString(4, motivo);
			pstmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.executeUpdate();


            response.sendRedirect("ElegirMetodoPago");

        } catch (Exception e) {
            e.printStackTrace(out);
            out.println("<p>Error en la operación.</p>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
