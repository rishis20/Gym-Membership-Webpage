import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class ReservationEntry {
    private String serviceName;
    private LocalDateTime dateTime;
    private boolean confirmed;
    private int serviceId = -1; // Optional service ID, default -1 if unknown

    // Original constructor (unchanged)
    public ReservationEntry(String serviceName, LocalDateTime dateTime, boolean confirmed) {
        this.serviceName = serviceName;
        this.dateTime = dateTime;
        this.confirmed = confirmed;
    }

    // 🔹 New constructor with service ID
    public ReservationEntry(String serviceName, LocalDateTime dateTime, boolean confirmed, int serviceId) {
        this.serviceName = serviceName;
        this.dateTime = dateTime;
        this.confirmed = confirmed;
        this.serviceId = serviceId;
    }

    // ✅ Getter methods
    public String getServiceName() {
        return serviceName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public int getServiceId() {
        return serviceId;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public String getStatus() {
        return confirmed ? "Paid" : "Pending";
    }

    public boolean isWithinLast30Days() {
        return dateTime.isAfter(LocalDateTime.now().minusDays(30));
    }

    public boolean isInFuture() {
        return dateTime.isAfter(LocalDateTime.now());
    }

    public String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        return dateTime.format(formatter);
    }

    public static boolean insertReservation(Connection conn, int clientId, int servicioId, LocalDateTime dateTime,
                                            boolean confirmed) {
        String sql = "INSERT INTO ReservationEntry (ClienteID, ServicioID, [DateTime], Confirmed) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            stmt.setInt(2, servicioId);
            stmt.setTimestamp(3, Timestamp.valueOf(dateTime));
            stmt.setInt(4, confirmed ? 1 : 0);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean confirmReservationPayment(Connection conn, int clientId, int serviceId,
                                                    LocalDateTime dateTime) {
        String sql = "UPDATE ReservationEntry SET Confirmed = true WHERE ClienteID = ? AND ServicioID = ? AND [DateTime] = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            stmt.setInt(2, serviceId);
            stmt.setTimestamp(3, Timestamp.valueOf(dateTime));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Vector<ReservationEntry> getByClientId(Connection conn, int clientId) {
        Vector<ReservationEntry> list = new Vector<>();
        String sql = "SELECT ReservationEntry.ServicioID, ReservationEntry.[DateTime], ReservationEntry.Confirmed, Servicios.NombreServicio " +
                    "FROM ReservationEntry INNER JOIN Servicios ON ReservationEntry.ServicioID = Servicios.ServicioID " +
                    "WHERE ReservationEntry.ClienteID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String serviceName = rs.getString("NombreServicio");
                Timestamp ts = rs.getTimestamp("DateTime");
                LocalDateTime dateTime = ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                boolean confirmed = rs.getInt("Confirmed") == 1;
                int serviceId = rs.getInt("ServicioID");

                list.add(new ReservationEntry(serviceName, dateTime, confirmed, serviceId));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
