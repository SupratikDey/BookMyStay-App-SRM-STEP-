
import java.util.*;

// Custom Exception
class InvalidCancellationException extends Exception {
    public InvalidCancellationException(String message) {
        super(message);
    }
}

// Cancellation Service
class CancellationService {

    public static void cancelBooking(String bookingId,
                                     Map<String, String> bookings,
                                     Map<String, Integer> inventory,
                                     Stack<String> rollbackStack)
            throws InvalidCancellationException {

        // Validate booking existence
        if (!bookings.containsKey(bookingId)) {
            throw new InvalidCancellationException("Booking ID does not exist: " + bookingId);
        }

        // Get room type
        String roomType = bookings.get(bookingId);

        // Step 1: Push to rollback stack (LIFO)
        rollbackStack.push(bookingId);

        // Step 2: Restore inventory
        inventory.put(roomType, inventory.get(roomType) + 1);

        // Step 3: Remove booking
        bookings.remove(bookingId);

        System.out.println("Cancellation successful for Booking ID: " + bookingId);
        System.out.println("Restored " + roomType + " room. Available now: " + inventory.get(roomType));
    }
}

// Main System
public class UC10 {

    private static Map<String, Integer> inventory = new HashMap<>();
    private static Map<String, String> bookings = new HashMap<>();
    private static Stack<String> rollbackStack = new Stack<>();

    // Initialize inventory
    static {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    // Simulate booking (for testing cancellation)
    public static void addBooking(String bookingId, String roomType) {
        if (inventory.containsKey(roomType) && inventory.get(roomType) > 0) {
            inventory.put(roomType, inventory.get(roomType) - 1);
            bookings.put(bookingId, roomType);
            System.out.println("Booking successful: " + bookingId + " (" + roomType + ")");
        } else {
            System.out.println("Booking failed for " + bookingId);
        }
    }

    public static void main(String[] args) {

        // Create some bookings
        addBooking("B1", "Single");
        addBooking("B2", "Double");

        try {
            // Valid cancellation
            CancellationService.cancelBooking("B1", bookings, inventory, rollbackStack);

            // Invalid cancellation (already cancelled)
            CancellationService.cancelBooking("B1", bookings, inventory, rollbackStack);

        } catch (InvalidCancellationException e) {
            System.out.println("Cancellation failed: " + e.getMessage());
        }

        // Show rollback stack
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}