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

@WebServlet("/AddBalanceForm")
public class AddBalanceForm extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

        out.println(Utils.header("Add Balance"));
        out.println("<!DOCTYPE html>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
		
        out.println("<html><head><title>Add Balance</title>");

        // JavaScript para mostrar formularios y validar monto con AJAX
		out.println("<script>");
		out.println("function validarMonto(event) {");
		out.println("  event.preventDefault();");
		out.println("  const amount = document.querySelector(\"input[name='amount']\").value;");

		out.println("  fetch('ValidarMonto', {");
		out.println("    method: 'POST',");
		out.println("    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },");
		out.println("    body: 'amount=' + encodeURIComponent(amount)");
		out.println("  })");
		out.println("  .then(res => res.json())");
		out.println("  .then(data => {");
		out.println("    if (data.valido) {");
		out.println("      document.getElementById('mensajeError').innerText = '';");
		out.println("      document.getElementById('formAddBalance').submit();");
		out.println("    } else {");
		out.println("document.getElementById('mensajeError').innerHTML = \"<span style='color:red; font-weight:bold;'>&#128547; The minimum deposit is 5 \u20AC</span>\";");
		out.println("    }");
		out.println("  });");
		out.println("}");
		out.println("</script>");
		
		
        out.println("</head><body>");
        out.println("<div class='author'>Jorge Calonge. F02. Add Balance</div>");
		out.println("<div class='page-title'>How much money do you want to deposit?</div>");

		out.println("<form id='formAddBalance' action='AddBalance' method='post'>");
		out.println("Amount: <input type='number' step='0.50' name='amount' min='0'><br><br>");
		out.println("Reason for deposit:<br>");
		out.println("<textarea name='motivo' rows='3' cols='30' placeholder='Enter a reason...'></textarea><br><br>");
		out.println("<input class='button' type='submit' value='Confirm' onclick='validarMonto(event)'>");
		out.println("<p id='mensajeError' style='color:red;'></p>");
		out.println("</form>");


        out.println("<br><a href='Balance' class='menu'>Back</a>");
        out.println("</body></html>");
    }
}
