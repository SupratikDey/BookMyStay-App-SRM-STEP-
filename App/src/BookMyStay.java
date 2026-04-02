import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay App
 *
 * Demonstrates booking request handling using Queue (FIFO).
 *
 * @author SPD
 * @version 5.0
 */

// ---------- RESERVATION CLASS ----------
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room: " + roomType;
    }
}

// ---------- MAIN CLASS ----------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Hotel Booking System v5.0\n");

        // Create booking request queue
        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Add booking requests (FIFO order)
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Double Room"));
        bookingQueue.add(new Reservation("Charlie", "Suite Room"));

        // Display queued requests
        System.out.println("Booking Requests (in arrival order):\n");

        for (Reservation r : bookingQueue) {
            System.out.println(r);
        }

        System.out.println("\nSystem ready for allocation processing...");
    }
}