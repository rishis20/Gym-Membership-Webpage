import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.regex.*;

public class SendEmail extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println(Utils.header("Contact"));
        toClient.println("<!DOCTYPE html>");
        toClient.println("<html lang='en'>");
        toClient.println("<head>");
        toClient.println("<meta charset='UTF-8'>");
        toClient.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
		toClient.println("<p></p>");
		toClient.println("<p></p>");
		toClient.println("<p></p>");
		toClient.println("<p></p>");
				toClient.println("<p></p>");
		toClient.println("<p></p>");
		toClient.println("<p></p>");
        toClient.println("<title>Send Email</title>");
        toClient.println("<link rel='stylesheet' href='style2.css'>");
        toClient.println("</head>");
        toClient.println("<body>");
        toClient.println("<div class='author'>Mikel Montejo. F03. SendEmail </div>");
        toClient.println("<div class='content'>");
        toClient.println("<h1>Send an Email</h1>");
        toClient.println("<form id='emailForm'>");
        toClient.println("<input type='text' name='name' id='name' placeholder='Your Name' required>");
        toClient.println("<input type='email' name='email' id='email' placeholder='Your Email' required>");
        toClient.println("<textarea name='message' id='message' rows='5' placeholder='Your Message' required maxlength='1000'></textarea>");
        toClient.println("<button type='button' id='submitButton'>Send</button>");
        toClient.println("</form>");
        toClient.println("<a class='back-button' href='Contact'>Back to Contact</a>");
        toClient.println("<div id='responseMessage'></div>");
        toClient.println("</div>");
        toClient.println("<script src='https://code.jquery.com/jquery-3.6.0.min.js'></script>");
        toClient.println("<script>");
        toClient.println("$(document).ready(function() {");
        toClient.println("  $('#submitButton').click(function() {");
        toClient.println("    var name = $('#name').val();");
        toClient.println("    var email = $('#email').val();");
        toClient.println("    var message = $('#message').val();");
        toClient.println("    if (!isValidEmail(email)) {");
        toClient.println("      $('#responseMessage').html('<p>Error: The email address is not valid.</p>');");
        toClient.println("      return;");
        toClient.println("    }");
        toClient.println("    if (message.length > 1000 || message.length == 0) {");
        toClient.println("      $('#responseMessage').html('<p>Error: The message is not valid. Maximum 1000 characters.</p>');");
        toClient.println("      return;");
        toClient.println("    }");
        toClient.println("    $.ajax({");
        toClient.println("      type: 'POST',");
        toClient.println("      url: 'SendEmail',");
        toClient.println("      data: { name: name, email: email, message: message },");
        toClient.println("      success: function(response) {");
        toClient.println("        $('#responseMessage').html('<p>Message sent successfully!</p>');");
        toClient.println("      },");
        toClient.println("      error: function() {");
        toClient.println("        $('#responseMessage').html('<p>Error: There was an issue with sending the email.</p>');");
        toClient.println("      }");
        toClient.println("    });");
        toClient.println("  });");
        toClient.println("});");
        toClient.println("function isValidEmail(email) {");
        toClient.println("  var regex = /^[A-Za-z0-9+_.-]+@(.+)$/;");
        toClient.println("  return regex.test(email);");
        toClient.println("}");
        toClient.println("</script>");
        toClient.println("</body>");
        toClient.println("</html>");

        toClient.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String nombre = request.getParameter("name");
        String mail = request.getParameter("email");
        String mensaje = request.getParameter("message");

        if (!isValidEmail(mail)) {
            out.println("<p>Error: The email address is not valid.</p>");
            return;
        }

        if (mensaje.length() > 1000) {
            out.println("<p>Error: The message is too long. Maximum 1000 characters.</p>");
            return;
        }

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = ConnectionUtils.getConnection();
            String sql = "INSERT INTO Contacto (Nombre, Mail, Mensaje) VALUES (?, ?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, mail);
            pstmt.setString(3, mensaje);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                out.println("<p>Message sent successfully!</p>");
            } else {
                out.println("<p>Error: The message could not be saved.</p>");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<p>Error: There was an error saving the data in the database.</p>");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
