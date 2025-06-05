import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DetectarMarcaTarjeta")
public class DetectarMarcaTarjeta extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String numero = request.getParameter("numero");
        String marca = "";

        if (numero != null) {
            // Visa
            if (numero.matches("^4.*")) {
                marca = "visa";
            }
            // MasterCard
            else if (numero.matches("^5[1-5].*") || numero.matches("^2(2[2-9]|[3-6]|7[01]).*")) {
                marca = "mastercard";
            }
            // American Express
            else if (numero.matches("^3[47].*")) {
                marca = "amex";
            }
            // Discover
            else if (numero.matches("^6(?:011|5).*")) {
                marca = "discover";
				
            }
			
        }

        response.setContentType("text/plain");
        response.getWriter().write(marca);
    }
}
