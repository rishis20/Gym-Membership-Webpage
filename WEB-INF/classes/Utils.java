public class Utils {

    public static String header(String title) {

        StringBuilder str = new StringBuilder();

        str.append("<!DOCTYPE HTML>");
        str.append("<html>");
        str.append("<head><title>" + title + "</title>");
        str.append("<link rel='stylesheet' href='style2.css'>");
        str.append("</head>");
        str.append("<body>");
        
        str.append("<div class='header'>");
		
		str.append("<img src='fitness-and-gym-logo-free-png.webp'>");
		str.append("<div class='header-links'>");
		str.append("<a href='index.html' class='home'>Home</a>");
        str.append("<a href='AboutUs'>About Us</a>");
		str.append("<a href='Locations'>Locations</a>");
		str.append("<a href='Valorations'>Valorations</a>");
        str.append("<a href='Contact'>Contact</a>");
        str.append("</div>");
		str.append("</div>");

        return str.toString();
    }

    public static String footer(String title) {

        StringBuilder str = new StringBuilder();
        str.append("</body>");
        str.append("</html>");
        return str.toString();
    }
}
