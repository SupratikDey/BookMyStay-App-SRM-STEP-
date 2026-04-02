import java.util.*;

/**
 * Book My Stay App
 *
 * Demonstrates booking history tracking and reporting.
 *
 * @author SPD
 * @version 8.0
 */

// ---------- RESERVATION ----------
class Reservation {
    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// ---------- BOOKING HISTORY ----------
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// ---------- REPORT SERVICE ----------
class ReportService {

    public void generateReport(List<Reservation> reservations) {

        System.out.println("\n=== Booking Report ===");

        Map<String, Integer> roomCount = new HashMap<>();

        // Count bookings per room type
        for (Reservation r : reservations) {
            roomCount.put(r.roomType,
                    roomCount.getOrDefault(r.roomType, 0) + 1);
        }

        // Print summary
        for (Map.Entry<String, Integer> entry : roomCount.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue() + " bookings");
        }

        System.out.println("\nTotal Bookings: " + reservations.size());
    }
}

// ---------- MAIN CLASS ----------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Hotel Booking System v8.0\n");

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from UC6)
        history.addReservation(new Reservation("RES101", "Alice", "Single Room"));
        history.addReservation(new Reservation("RES102", "Bob", "Double Room"));
        history.addReservation(new Reservation("RES103", "Charlie", "Single Room"));
        history.addReservation(new Reservation("RES104", "David", "Suite Room"));

        // Display booking history
        System.out.println("Booking History:\n");
        for (Reservation r : history.getAllReservations()) {
            System.out.println(r);
        }

        // Generate report
        ReportService reportService = new ReportService();
        reportService.generateReport(history.getAllReservations());

        System.out.println("\nSystem ready for further operations...");
    }
}