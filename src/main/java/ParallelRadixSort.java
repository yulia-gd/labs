import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelRadixSort extends RadixSort{

    public void countSort(int[] array, int exp) {
        int n = array.length;
        int[] output = new int[n];
        int[] count = new int[10];

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        int chunkSize = (n + Runtime.getRuntime().availableProcessors() - 1) / Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < n; i += chunkSize) {
            final int start = i;
            final int end = Math.min(i + chunkSize, n);
            executorService.submit(() -> {
                int[] localCount = new int[10];
                for (int j = start; j < end; j++) {
                    localCount[(array[j] / exp) % 10]++;
                }
                synchronized (count) {
                    for (int k = 0; k < 10; k++) {
                        count[k] += localCount[k];
                    }
                }
            });
        }

        shutdownExecutor(executorService);


        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int digit = (array[i] / exp) % 10;
            output[--count[digit]] = array[i];
        }

        System.arraycopy(output, 0, array, 0, n);
    }


    private static void shutdownExecutor(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
