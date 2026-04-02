import java.util.*;

/**
 * Book My Stay App
 *
 * Demonstrates booking confirmation and room allocation
 * with uniqueness and inventory consistency.
 *
 * @author SPD
 * @version 6.0
 */

// ---------- RESERVATION ----------
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// ---------- INVENTORY ----------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, getAvailability(roomType) - 1);
    }
}

// ---------- MAIN CLASS ----------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Hotel Booking System v6.0\n");

        // Queue (UC5)
        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Single Room"));
        bookingQueue.add(new Reservation("Charlie", "Single Room")); // extra request
        bookingQueue.add(new Reservation("David", "Suite Room"));

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Track allocated room IDs
        Set<String> allocatedRoomIds = new HashSet<>();

        // Map room type → allocated IDs
        Map<String, Set<String>> allocationMap = new HashMap<>();

        int idCounter = 1;

        System.out.println("Processing Booking Requests...\n");

        while (!bookingQueue.isEmpty()) {

            Reservation request = bookingQueue.poll();
            String type = request.roomType;

            if (inventory.getAvailability(type) > 0) {

                // Generate unique room ID
                String roomId = type.replace(" ", "").substring(0, 2).toUpperCase() + idCounter++;

                // Ensure uniqueness
                if (!allocatedRoomIds.contains(roomId)) {
                    allocatedRoomIds.add(roomId);

                    // Map allocation
                    allocationMap.putIfAbsent(type, new HashSet<>());
                    allocationMap.get(type).add(roomId);

                    // Update inventory
                    inventory.decrement(type);

                    // Confirm booking
                    System.out.println("Booking Confirmed:");
                    System.out.println("Guest: " + request.guestName);
                    System.out.println("Room Type: " + type);
                    System.out.println("Room ID: " + roomId + "\n");
                }

            } else {
                System.out.println("Booking Failed (No Availability): " +
                        request.guestName + " → " + type + "\n");
            }
        }

        // Final allocation summary
        System.out.println("Final Allocation Summary:");
        for (Map.Entry<String, Set<String>> entry : allocationMap.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }

        System.out.println("\nSystem ready for further operations...");
    }
}