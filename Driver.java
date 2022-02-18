import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Driver {
    public Driver() {
    }

    public static void main(String[] args) {

        int numberInitialValue = 3; // Number of initial values added to the set
        int maxIteration = 10; // Maximum number of iterations

        Set<Integer> set = new HashSet<>();
        // Add values so that `thread2` does not stop directly
        for (int i = 0; i < numberInitialValue; i++) {
            set.add(i);
        }

        // `thread1` adds a value to `set` every second
        Thread thread1 = new Thread(() -> {
            for (int i = numberInitialValue; i < maxIteration; ++i) {
                set.add(i);
                System.out.println("Adding " + i);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // `thread2` iterates through `set` every second
        Thread thread2 = new Thread(() -> {
            Iterator<Integer> itr = set.iterator();

            for (int i = 0; itr.hasNext() && i < maxIteration; i++) {
                try {
                    System.out.println("reading " + itr.next());
                } catch (ConcurrentModificationException e) {
                    e.printStackTrace();
                    System.out.println("ConcurrentModificationException obtained");
                    System.exit(0);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
