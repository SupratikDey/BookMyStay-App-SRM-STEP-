import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay App
 *
 * Demonstrates centralized room inventory management using HashMap.
 *
 * @author SPD
 * @version 3.0
 */

// Inventory Class (Single Source of Truth)
class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor to initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initialize room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → Available: " + entry.getValue());
        }
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Hotel Booking System v3.0");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display inventory
        inventory.displayInventory();

        // Example: Update availability
        inventory.updateAvailability("Single Room", 4);

        System.out.println("\nAfter Updating Single Room Availability:");
        inventory.displayInventory();

        System.out.println("\nSystem ready for further operations...");
    }
}