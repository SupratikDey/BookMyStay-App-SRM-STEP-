/**
 * Book My Stay App
 *
 * Demonstrates Room Modeling using Abstraction and Inheritance
 * with static availability representation.
 *
 * @author SPD
 * @version 2.0
 */

// Abstract class
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

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

// Main class
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Hotel Booking System v2.0\n");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability (simple variables)
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display details
        System.out.println("Single Room Details:");
        single.displayDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        System.out.println("Double Room Details:");
        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        System.out.println("Suite Room Details:");
        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable + "\n");

        System.out.println("System ready for further operations...");
    }
}