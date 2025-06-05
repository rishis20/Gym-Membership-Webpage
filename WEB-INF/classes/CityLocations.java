import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CityLocations extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();
        String city = req.getParameter("city");

        StringBuilder json = new StringBuilder();
        json.append("[");

        try (Connection conn = ConnectionUtils.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Localizaciones WHERE Ciudad = ?");
            ps.setString(1, city);
            ResultSet rs = ps.executeQuery();

            boolean first = true;
            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{")
                    .append("\"name\":\"").append(escape(rs.getString("Nombre"))).append("\",")
                    .append("\"lat\":").append(rs.getDouble("Latitud")).append(",")
                    .append("\"lng\":").append(rs.getDouble("Longitud")).append(",")
                    .append("\"desc\":\"").append(escape(rs.getString("Descripcion"))).append("\"")
                    .append("}");
                first = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        json.append("]");
        out.print(json.toString());
    }

    private String escape(String text) {
        if (text == null) return "";
        return text.replace("\"", "\\\"").replace("\n", "").replace("\r", "");
    }
}

