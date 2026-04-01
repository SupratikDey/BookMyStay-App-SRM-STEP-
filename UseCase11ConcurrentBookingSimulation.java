import java.util.*;

// Booking Request Class
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking Processor
class ConcurrentBookingProcessor {

    private Queue<BookingRequest> bookingQueue = new LinkedList<>();
    private Map<String, Integer> inventory = new HashMap<>();

    public ConcurrentBookingProcessor() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    // Add booking request (Producer)
    public synchronized void addRequest(BookingRequest request) {
        bookingQueue.add(request);
        System.out.println(request.guestName + " requested " + request.roomType);
    }

    // Process booking (Consumer - Thread Safe)
    public void processBooking() {
        while (true) {
            BookingRequest request;

            // Critical section: safely get request
            synchronized (this) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                request = bookingQueue.poll();
            }

            // Critical section: inventory update
            synchronized (this) {
                int available = inventory.getOrDefault(request.roomType, 0);

                if (available > 0) {
                    inventory.put(request.roomType, available - 1);
                    System.out.println(
                        Thread.currentThread().getName() +
                        " booked " + request.roomType +
                        " for " + request.guestName +
                        " | Remaining: " + inventory.get(request.roomType)
                    );
                } else {
                    System.out.println(
                        Thread.currentThread().getName() +
                        " FAILED booking for " + request.guestName +
                        " (No " + request.roomType + " rooms left)"
                    );
                }
            }

            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Thread class
class BookingThread extends Thread {

    private ConcurrentBookingProcessor processor;

    public BookingThread(ConcurrentBookingProcessor processor, String name) {
        super(name);
        this.processor = processor;
    }

    public void run() {
        processor.processBooking();
    }
}

// Main Class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor();

        // Simulate multiple guest requests
        processor.addRequest(new BookingRequest("Guest1", "Single"));
        processor.addRequest(new BookingRequest("Guest2", "Single"));
        processor.addRequest(new BookingRequest("Guest3", "Single"));
        processor.addRequest(new BookingRequest("Guest4", "Double"));
        processor.addRequest(new BookingRequest("Guest5", "Double"));

        // Create multiple threads
        Thread t1 = new BookingThread(processor, "Thread-1");
        Thread t2 = new BookingThread(processor, "Thread-2");
        Thread t3 = new BookingThread(processor, "Thread-3");

        // Start threads (concurrent execution)
        t1.start();
        t2.start();
        t3.start();
    }
}