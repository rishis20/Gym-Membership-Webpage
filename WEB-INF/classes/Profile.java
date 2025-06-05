import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Profile")
public class Profile extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        String correo = (String) session.getAttribute("correo");

        if (correo == null) {
            response.sendRedirect("LogIn");
            return;
        }

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionUtils.getConnection();
            String sql = "SELECT Nombre, Correo, DNI, Telefono, Contra FROM Clientes WHERE Correo = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, correo);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("Nombre");
                String dni = rs.getString("DNI");
                String telefono = rs.getString("Telefono");
				String Contrasena = rs.getString("Contra");

                out.println(Utils.header("Perfil de Cliente"));
                out.println("<div class='author'>Alvaro Sola. F04. Profile </div>");
														out.println("<p></p>");
						out.println("<p></p>");
				out.println("<p></p>");
							out.println("<p></p>");
				out.println("<p></p>");
				out.println("<p></p>");
                out.println("<div class='profile-container'>");
                out.println("<img id='profile-pic' class='profile-picture' src='' onclick='changeProfilePicture()'>");

                // Formulario para editar datos

                out.println("<form method='post' action='Profile'>");

                out.println("<label>Nombre:</label>");
                out.println("<input type='text' name='nuevoNombre' value='" + nombre + "' required><br>");

                out.println("<label>DNI:</label>");
                out.println("<input type='text' name='nuevoDni' value='" + dni + "' required><br>");

                out.println("<label>Telefono:</label>");
                out.println("<input type='text' name='nuevoTelefono' value='" + telefono + "' required><br>");

                out.println("<button class='button' type='submit'>Guardar Cambios</button>");
				out.println("<p></p>");
				out.println("<a href='TarjetaGuardada' class='button'>Tarjeta</a>");
                out.println("</form>");

                out.println("<h2 id='correo'>" + correo + "</h2>");
                out.println("<button class='back-button' onclick='location.href=\"ClientMenuData\"'>Volver</button>");
                out.println("</div>");

                // JavaScript para imagen
                out.println("<script>");
                out.println("window.onload = function() {");
                out.println("  const savedProfilePic = localStorage.getItem('profilePicture');");
                out.println("  if (savedProfilePic) { document.getElementById('profile-pic').src = savedProfilePic; }");
                out.println("};");
                out.println("function changeProfilePicture() { window.location.href = 'upload_picture.html'; }");
                out.println("</script>");

                out.println(Utils.footer("GYMNASIO"));
            } else {
                out.println("<p>No se encontró el perfil.</p>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error en el servidor.</p>");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nuevoNombre = request.getParameter("nuevoNombre");
        String nuevoDni = request.getParameter("nuevoDni");
        String nuevoTelefono = request.getParameter("nuevoTelefono");

        HttpSession session = request.getSession();
        String correo = (String) session.getAttribute("correo");

        if (correo == null) {
            response.sendRedirect("LogIn");
            return;
        }

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = ConnectionUtils.getConnection();
            String sql = "UPDATE Clientes SET Nombre = ?, DNI = ?, Telefono = ? WHERE Correo = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, nuevoDni);
            pstmt.setString(3, nuevoTelefono);
			pstmt.setString(4, correo);
			
			
            pstmt.executeUpdate();

            response.sendRedirect("Profile");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }
    }
}
