import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Validator Class
class InvalidBookingValidator {

    public static void validate(String roomType, int roomsRequested, Map<String, Integer> inventory)
            throws InvalidBookingException {

        // Validate room type
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Validate room count
        if (roomsRequested <= 0) {
            throw new InvalidBookingException("Room count must be greater than zero.");
        }

        // Check availability
        int available = inventory.get(roomType);
        if (roomsRequested > available) {
            throw new InvalidBookingException(
                    "Not enough rooms available. Requested: " + roomsRequested + ", Available: " + available);
        }
    }
}

// Main System
public class UC9 {

    private static Map<String, Integer> inventory = new HashMap<>();

    // Initialize inventory
    static {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public static void bookRoom(String roomType, int count) {
        try {
            // Step 1: Validate input
            InvalidBookingValidator.validate(roomType, count, inventory);

            // Step 2: Update inventory (safe after validation)
            inventory.put(roomType, inventory.get(roomType) - count);

            System.out.println("Booking successful!");
            System.out.println("Remaining " + roomType + " rooms: " + inventory.get(roomType));

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        // Valid booking
        bookRoom("Single", 2);

        // Invalid room type
        bookRoom("Deluxe", 1);

        // Invalid count
        bookRoom("Double", 0);

        // Exceeding availability
        bookRoom("Suite", 5);

        // Valid booking again (system still stable)
        bookRoom("Double", 2);
    }
}