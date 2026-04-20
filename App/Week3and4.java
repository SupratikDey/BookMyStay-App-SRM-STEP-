import java.util.*;

public class BankingDSAApp {

    // ===================== MODELS =====================
    static class Transaction {
        String id;
        double fee;
        String timestamp;

        Transaction(String id, double fee, String timestamp) {
            this.id = id;
            this.fee = fee;
            this.timestamp = timestamp;
        }

        public String toString() {
            return id + ":" + fee + "@" + timestamp;
        }
    }

    static class Client {
        String name;
        int riskScore;
        double balance;

        Client(String name, int riskScore, double balance) {
            this.name = name;
            this.riskScore = riskScore;
            this.balance = balance;
        }

        public String toString() {
            return name + ":" + riskScore + " bal=" + balance;
        }
    }

    static class Trade {
        String id;
        int volume;

        Trade(String id, int volume) {
            this.id = id;
            this.volume = volume;
        }

        public String toString() {
            return id + ":" + volume;
        }
    }

    // ===================== PROBLEM 1 =====================
    // Bubble Sort (fee ASC)
    static void bubbleSortTransactions(List<Transaction> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Collections.swap(list, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    // Insertion Sort (fee + timestamp)
    static void insertionSortTransactions(List<Transaction> list) {
        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            int j = i - 1;

            while (j >= 0 &&
                    (list.get(j).fee > key.fee ||
                            (list.get(j).fee == key.fee &&
                                    list.get(j).timestamp.compareTo(key.timestamp) > 0))) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    static void findOutliers(List<Transaction> list) {
        for (Transaction t : list) {
            if (t.fee > 50) {
                System.out.println("Outlier: " + t);
            }
        }
    }

    // ===================== PROBLEM 2 =====================
    static void bubbleSortClients(Client[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    static void insertionSortClients(Client[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Client key = arr[i];
            int j = i - 1;

            while (j >= 0 &&
                    (arr[j].riskScore < key.riskScore ||
                            (arr[j].riskScore == key.riskScore &&
                                    arr[j].balance < key.balance))) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    static void topRisks(Client[] arr, int k) {
        for (int i = 0; i < Math.min(k, arr.length); i++) {
            System.out.println(arr[i]);
        }
    }

    // ===================== PROBLEM 3 =====================
    static void mergeSortTrades(Trade[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSortTrades(arr, l, m);
            mergeSortTrades(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    static void merge(Trade[] arr, int l, int m, int r) {
        Trade[] temp = new Trade[r - l + 1];
        int i = l, j = m + 1, k = 0;

        while (i <= m && j <= r) {
            if (arr[i].volume <= arr[j].volume)
                temp[k++] = arr[i++];
            else
                temp[k++] = arr[j++];
        }

        while (i <= m) temp[k++] = arr[i++];
        while (j <= r) temp[k++] = arr[j++];

        for (i = l, k = 0; i <= r; i++, k++) {
            arr[i] = temp[k];
        }
    }

    static void quickSortTrades(Trade[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortTrades(arr, low, pi - 1);
            quickSortTrades(arr, pi + 1, high);
        }
    }

    static int partition(Trade[] arr, int low, int high) {
        int pivot = arr[high].volume;
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].volume > pivot) { // DESC
                i++;
                Trade temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        Trade temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    // ===================== PROBLEM 5 =====================
    static int linearSearch(String[] arr, String target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(target)) return i;
        }
        return -1;
    }

    static int binarySearch(String[] arr, String target) {
        int low = 0, high = arr.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = arr[mid].compareTo(target);

            if (cmp == 0) return mid;
            else if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    // ===================== PROBLEM 6 =====================
    static void floorCeil(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int floor = -1, ceil = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] == target) {
                floor = ceil = arr[mid];
                break;
            }

            if (arr[mid] < target) {
                floor = arr[mid];
                low = mid + 1;
            } else {
                ceil = arr[mid];
                high = mid - 1;
            }
        }

        System.out.println("Floor: " + floor + ", Ceil: " + ceil);
    }

    // ===================== MAIN =====================
    public static void main(String[] args) {

        // -------- Problem 1 --------
        System.out.println("=== Problem 1 ===");
        List<Transaction> tx = new ArrayList<>();
        tx.add(new Transaction("id1", 10.5, "10:00"));
        tx.add(new Transaction("id2", 25.0, "09:30"));
        tx.add(new Transaction("id3", 5.0, "10:15"));

        bubbleSortTransactions(tx);
        System.out.println("Bubble Sorted: " + tx);

        insertionSortTransactions(tx);
        System.out.println("Insertion Sorted: " + tx);

        findOutliers(tx);

        // -------- Problem 2 --------
        System.out.println("\n=== Problem 2 ===");
        Client[] clients = {
                new Client("A", 20, 1000),
                new Client("B", 50, 2000),
                new Client("C", 80, 500)
        };

        bubbleSortClients(clients);
        System.out.println("Bubble Sorted Clients: " + Arrays.toString(clients));

        insertionSortClients(clients);
        System.out.println("Insertion Sorted Clients: " + Arrays.toString(clients));

        System.out.println("Top Risks:");
        topRisks(clients, 3);

        // -------- Problem 3 --------
        System.out.println("\n=== Problem 3 ===");
        Trade[] trades = {
                new Trade("T1", 500),
                new Trade("T2", 100),
                new Trade("T3", 300)
        };

        mergeSortTrades(trades, 0, trades.length - 1);
        System.out.println("Merge Sorted: " + Arrays.toString(trades));

        quickSortTrades(trades, 0, trades.length - 1);
        System.out.println("Quick Sorted DESC: " + Arrays.toString(trades));

        // -------- Problem 5 --------
        System.out.println("\n=== Problem 5 ===");
        String[] logs = {"accA", "accB", "accB", "accC"};
        System.out.println("Linear Search accB: " + linearSearch(logs, "accB"));
        System.out.println("Binary Search accB: " + binarySearch(logs, "accB"));

        // -------- Problem 6 --------
        System.out.println("\n=== Problem 6 ===");
        int[] risks = {10, 25, 50, 100};
        floorCeil(risks, 30);
    }
}