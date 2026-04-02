import java.util.*;

/**
 * Book My Stay App
 *
 * Demonstrates add-on service selection using
 * Map + List (one-to-many relationship).
 *
 * @author SPD
 * @version 7.0
 */

// ---------- SERVICE CLASS ----------
class Service {
    String name;
    double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return name + " (₹" + cost + ")";
    }
}

// ---------- MAIN CLASS ----------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Hotel Booking System v7.0\n");

        // Map: Reservation ID → List of Services
        Map<String, List<Service>> serviceMap = new HashMap<>();

        // Sample reservation IDs (from UC6)
        String res1 = "RES101";
        String res2 = "RES102";

        // Create services
        Service breakfast = new Service("Breakfast", 500);
        Service wifi = new Service("WiFi", 200);
        Service spa = new Service("Spa", 1500);

        // Add services to RES101
        serviceMap.putIfAbsent(res1, new ArrayList<>());
        serviceMap.get(res1).add(breakfast);
        serviceMap.get(res1).add(wifi);

        // Add services to RES102
        serviceMap.putIfAbsent(res2, new ArrayList<>());
        serviceMap.get(res2).add(spa);

        // Display services + cost
        System.out.println("Add-On Services for Reservations:\n");

        for (Map.Entry<String, List<Service>> entry : serviceMap.entrySet()) {

            String reservationId = entry.getKey();
            List<Service> services = entry.getValue();

            System.out.println("Reservation ID: " + reservationId);

            double totalCost = 0;

            for (Service s : services) {
                System.out.println(" - " + s);
                totalCost += s.cost;
            }

            System.out.println("Total Add-On Cost: ₹" + totalCost + "\n");
        }

        System.out.println("System ready for further operations...");
    }
}