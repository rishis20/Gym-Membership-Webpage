import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Tarjeta")
public class Tarjeta extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println(Utils.header("New Credit Card"));

        out.println("<!DOCTYPE html>");

        out.println("<html><head><title>Pay with new Credit Card</title>");

        // JavaScript con validación AJAX
        out.println("<script>");
        out.println("function validateCard(event) {");
        out.println("  event.preventDefault();");

        out.println("  const numero = document.getElementById('numero').value;");
        out.println("  const fecha = document.getElementById('fecha').value;");
        out.println("  const cvv = document.getElementById('cvv').value;");
        out.println("  const errorBox = document.getElementById('error');");
        out.println("  errorBox.innerText = '';");

        out.println("  fetch('ValidarTarjetaAjax', {");
        out.println("    method: 'POST',");
        out.println("    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },");
        out.println("    body: 'numero=' + encodeURIComponent(numero) + '&fecha=' + encodeURIComponent(fecha) + '&cvv=' + encodeURIComponent(cvv)");
        out.println("  })");
        out.println("  .then(res => res.json())");
        out.println("  .then(data => {");
        out.println("    if (data.valido) {");
        out.println("      document.getElementById('creditForm').submit();");
        out.println("    } else {");
        out.println("      errorBox.innerHTML = \"<span style='color:red; font-weight:bold;'>\\u26A0\\uFE0F Invalid required fields</span>\";");
        out.println("    }");
        out.println("  });");
        out.println("}");
        out.println("</script>");

        out.println("</head><body>");
				out.println("<p></p>");

        out.println("<div class='author'>Jorge Calonge. F04. New Credit Card</div>");
				out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
        out.println("<h2>Enter your credit card details</h2>");

        out.println("<form id='creditForm' action='Balance' method='get' onsubmit='validateCard(event)'>");
        out.println("Card number: <input type='text' id='numero' name='numero' maxlength='16'><br><br>");
        out.println("Expiration date (MM/YYYY): <input type='month' id='fecha' name='fecha'><br><br>");
        out.println("CVV: <input type='password' id='cvv' name='cvv' maxlength='3'><br><br>");
        out.println("<p style='font-size: small; color: gray;'>(This card will not be stored in your account)</p><br>");
        out.println("<input class='button' type='submit' value='Pay'>");
        out.println("</form>");

        out.println("<p id='error'></p>");

        out.println("<br><a href='Balance' class='menu'>Back without paying</a>");
        out.println("</body></html>");
    }
}
