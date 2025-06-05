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

@WebServlet("/ElegirMetodoPago")
public class ElegirMetodoPago extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println(Utils.header("Método de Pago"));
        out.println("<!DOCTYPE html>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<p></p>");
        out.println("<html><head><title>Select desired option</title></head><body>");

        // Contenido
        out.println("<div class='author'>Jorge Calonge. F03. Metodo de Pago</div>");
        out.println("<h1>Select desired option</h1><br>");

        // Botones para elegir el método
        out.println("<a href='TarjetaGuardadaPagar'><button class='button'>Saved card</button></a><br><br>");
        out.println("<a href='Tarjeta'><button class='button'>New card</button></a><br><br>");

        // Volver atrás
        out.println("<a href='Balance' class='menu'>Volver</a>");

        out.println("</body></html>");
    }
}
