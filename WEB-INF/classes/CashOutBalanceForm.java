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

@WebServlet("/CashOutBalanceForm")
public class CashOutBalanceForm extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        String correo = (String) session.getAttribute("correo");

        if (correo == null) {
            response.sendRedirect("ClientMenuData");
            return;
        }

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionUtils.getConnection();
            String sql = "SELECT Balance FROM Clientes WHERE Correo = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, correo);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("Balance");

                out.println(Utils.header("Cash out Balance"));
				out.println("<!DOCTYPE html>");
				out.println("<html><head><title>Cash out Balance</title>");
				out.println("<script>");		
				out.println("function toggleForm(id) {");
				out.println("  var form = document.getElementById(id);");
				out.println("  form.style.display = form.style.display === 'none' ? 'block' : 'none';");
				out.println("}");
				out.println("</script></head><body>");

				// Estilo autor y título
				out.println("<div class='author'>Jorge Calonge. F02. Cash Out</div>");
				out.println("<p></p>");
				out.println("<p></p>");
				out.println("<p></p>");
				out.println("<p></p>");
				out.println("<p></p>");
				out.println("<p></p>");
				out.println("<div class='page-title'>How much money do you want to withdraw?</div>");

				// Formulario
				out.println("<form action='CashOutBalance' method='post'>");
				out.println("Amount: <input type='number' step='0.50' name='amount' min='0' max='" + balance + "'><br><br>");
				out.println("Reason (optional): <input type='text' name='motivo' placeholder='optional'><br><br>");
				out.println("<input class='button' type='submit' value='Confirm'>");
				out.println("</form>");


                out.println("<br><a href='Balance' class='menu' >Back</a>");
                out.println("</body></html>");
            } else {
                out.println("<p>No se encontró el cliente.</p>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error en base de datos.</p>");
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
