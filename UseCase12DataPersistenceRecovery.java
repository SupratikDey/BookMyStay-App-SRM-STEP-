import java.io.*;
import java.util.*;

// Serializable System State
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    Map<String, String> bookings;

    public SystemState(Map<String, Integer> inventory, Map<String, String> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.ser";

    // Save state to file
    public static void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("✅ System state saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving system state: " + e.getMessage());
        }
    }

    // Load state from file
    public static SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) ois.readObject();
            System.out.println("✅ System state loaded successfully.");
            return state;
        } catch (FileNotFoundException e) {
            System.out.println("⚠️ No previous state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error loading state. Starting with safe defaults.");
        }

        // fallback safe state
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);

        return new SystemState(inventory, new HashMap<>());
    }
}

// Main System
public class UseCase12DataPersistenceRecovery {

    private static Map<String, Integer> inventory;
    private static Map<String, String> bookings;

    public static void main(String[] args) {

        // Step 1: Load persisted state (Recovery)
        SystemState state = PersistenceService.load();
        inventory = state.inventory;
        bookings = state.bookings;

        // Simulate booking
        addBooking("B1", "Single");
        addBooking("B2", "Double");

        // Display current state
        System.out.println("Current Inventory: " + inventory);
        System.out.println("Current Bookings: " + bookings);

        // Step 2: Save state before shutdown
        PersistenceService.save(new SystemState(inventory, bookings));
    }

    // Simple booking logic
    public static void addBooking(String bookingId, String roomType) {
        if (inventory.containsKey(roomType) && inventory.get(roomType) > 0) {
            inventory.put(roomType, inventory.get(roomType) - 1);
            bookings.put(bookingId, roomType);
            System.out.println("Booking successful: " + bookingId + " (" + roomType + ")");
        } else {
            System.out.println("Booking failed: " + bookingId);
        }
    }
}