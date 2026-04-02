import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay App
 *
 * Demonstrates read-only room search using inventory + domain model.
 *
 * @author SPD
 * @version 4.0
 */

// ---------- ROOM DOMAIN MODEL ----------
abstract class Room {
    String type;
    int beds;
    double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

// ---------- INVENTORY (READ-ONLY ACCESS HERE) ----------
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // unavailable
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// ---------- MAIN CLASS ----------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Hotel Booking System v4.0\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects (domain model)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Search (READ-ONLY OPERATION)
        System.out.println("Available Rooms:\n");

        displayIfAvailable(single, inventory);
        displayIfAvailable(doubleRoom, inventory);
        displayIfAvailable(suite, inventory);

        System.out.println("System ready for further operations...");
    }

    // Helper method for safe search (no modification)
    public static void displayIfAvailable(Room room, RoomInventory inventory) {

        int available = inventory.getAvailability(room.type);

        // Defensive check → only show if available
        if (available > 0) {
            room.displayDetails();
            System.out.println("Available: " + available + "\n");
        }
    }
}