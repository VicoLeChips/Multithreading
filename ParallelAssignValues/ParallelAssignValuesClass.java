package ParallelAssignValues;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

public class ParallelAssignValuesClass extends RecursiveAction {
    static final int THRESHOLD = 300;
    double[] list;
    int start;
    int end;

    public ParallelAssignValuesClass(double[] list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }

    public ParallelAssignValuesClass(double[] list) {
        this(list, 0, list.length - 1);
    }

    @Override
    protected void compute() {
        if (end - start < THRESHOLD) {
            for (int i = start; i <= end; i++) {
                list[i] = ThreadLocalRandom.current().nextDouble();
            }
        } else {
            int middle = (start + end) / 2;
            ParallelAssignValuesClass r1 = new ParallelAssignValuesClass(list, start, middle);
            ParallelAssignValuesClass r2 = new ParallelAssignValuesClass(list, middle + 1, end);
            invokeAll(r1, r2);
        }
    }

    public static void parallelAssignValues(double[] list) {
        ParallelAssignValuesClass assign = new ParallelAssignValuesClass(list);
        ForkJoinPool.commonPool().invoke(assign);
    }
}
