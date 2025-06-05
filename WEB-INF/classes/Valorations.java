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

@WebServlet("/Valorations")
public class Valorations extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        out.println(Utils.header("Valorations"));

        try {
            connection = ConnectionUtils.getConnection();

            // Obtener todos los servicios para el desplegable
            String sqlServicios = "SELECT ServicioID, NombreServicio FROM Servicios";
            pstmt = connection.prepareStatement(sqlServicios);
            rs = pstmt.executeQuery();

            out.println("<div class='valorations-container'>");

            // Menú desplegable
            out.println("<form method='get' action='Valorations'>");
            out.println("<label for='servicioID'>Select a service: </label>");
            out.println("<select name='servicioID' onchange='this.form.submit()'>");
            out.println("<option value=''>-- Choose one --</option>");

            String selectedServicioID = request.getParameter("servicioID");

            while (rs.next()) {
                int id = rs.getInt("ServicioID");
                String nombre = rs.getString("NombreServicio");
                String selected = (selectedServicioID != null && selectedServicioID.equals(String.valueOf(id))) ? "selected" : "";
                out.printf("<option value='%d' %s>%s</option>%n", id, selected, nombre);
            }

            out.println("</select>");
            out.println("</form>");

            // Mostrar solo el servicio seleccionado
            if (selectedServicioID != null && !selectedServicioID.isEmpty()) {
                int servicioID = Integer.parseInt(selectedServicioID);

                String sqlDetalle = "SELECT NombreServicio, Descripcion, Costo FROM Servicios WHERE ServicioID = ?";
                pstmt = connection.prepareStatement(sqlDetalle);
                pstmt.setInt(1, servicioID);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    String nombre = rs.getString("NombreServicio");
                    String descripcion = rs.getString("Descripcion");
                    double costo = rs.getDouble("Costo");
                    double media = obtenerMediaValoracion(connection, servicioID);

                    out.println("<div class='author'>Mikel Montejo . F05. Valorations </div>");
                    out.println("<div class='servicio-box'>");
                    out.println("<h2>" + nombre + "</h2>");
                    out.println("<p>" + descripcion + "</p>");
                    out.println("<p><strong>Price:</strong> $" + costo + "</p>");
                    out.printf("<p><strong>Average Rating:</strong> %.2f </p>", media);

                    // Formulario para votar
                    out.println("<form action='SubmitRating' method='post'>");
                    out.println("<input type='hidden' name='servicioID' value='" + servicioID + "'>");
                    out.println("<label>Vote: </label>");
                    out.println("<div style='display: flex; gap: 10px; margin-top: 5px;'>");
                    for (int i = 1; i <= 5; i++) {
                        out.println("<label><input type='radio' name='rating' value='" + i + "' required> " + i + "</label>");
                    }
                    out.println("</div>");
                    out.println("<input type='submit' value='Submit'>");
                    out.println("</form>");

                    // Botón para ver comentarios
                    out.println("<form action='Comments' method='get'>");
                    out.println("<input type='hidden' name='servicioID' value='" + servicioID + "'>");
                    out.println("<input type='submit' value='Comments' style='background-color: orange; color: white;'>");
                    out.println("</form>");

                    out.println("</div>");
                } else {
                    out.println("<p>Service not found.</p>");
                }
            }

            out.println("</div>");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<p>Error connecting to the database.</p>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        out.println(Utils.footer("Valorations"));
    }

    private double obtenerMediaValoracion(Connection connection, int servicioID) throws SQLException {
        PreparedStatement ps = null;
        ResultSet result = null;
        double media = 0;
        String sql = "SELECT AVG(Rating) AS Media FROM Valoraciones WHERE ServicioID = ?";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, servicioID);
            result = ps.executeQuery();

            if (result.next()) {
                media = result.getDouble("Media");
            }
        } finally {
            if (result != null) result.close();
            if (ps != null) ps.close();
        }

        return media;
    }
}
