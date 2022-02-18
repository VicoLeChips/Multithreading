package ParallelAssignValues;

import java.util.concurrent.ThreadLocalRandom;

import static ParallelAssignValues.ParallelAssignValuesClass.parallelAssignValues;

public class TestParallelAssignValues {
    public static void main(String[] args) {
        int size = 9_000_000;
        int iterations = 100;

        long parallelTotalTime = 0;
        long parallelStartTime;
        long parallelEndTime;
        for (int i = 0; i < iterations; i++) {
            double[] parallel = new double[size];
            parallelStartTime = System.nanoTime();
            parallelAssignValues(parallel);
            parallelEndTime = System.nanoTime();
            parallelTotalTime += parallelEndTime - parallelStartTime;
        }
        parallelTotalTime /= iterations;
        System.out.printf("Average time needed to fill a %,d doubles array in parallel:  %,11d nanoseconds\n", size, parallelTotalTime);

        long sequentialTotalTime = 0;
        long sequentialStartTime;
        long sequentialEndTime;
        for (int i = 0; i < iterations; i++) {
            double[] sequential = new double[size];
            sequentialStartTime = System.nanoTime();
            sequentialAssignValues(sequential);
            sequentialEndTime = System.nanoTime();
            sequentialTotalTime += sequentialEndTime - sequentialStartTime;
        }
        sequentialTotalTime /= iterations;
        System.out.printf("Average time needed to fill a %,d doubles array sequentially: %,11d nanoseconds\n", size, sequentialTotalTime);
    }

    public static void sequentialAssignValues(double[] list) {
        for (int i = 0; i < list.length; i++) {
            list[i] = ThreadLocalRandom.current().nextDouble();;
        }
    }
}
