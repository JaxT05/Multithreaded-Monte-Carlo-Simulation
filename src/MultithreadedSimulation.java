import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadedSimulation {

    private static void runModel(long totalPoints) throws ExecutionException, InterruptedException {
        Instant start = Instant.now();
        int NUM_THREADS = 4;
        ExecutorService es = Executors.newFixedThreadPool(NUM_THREADS);

        long totalInsideCircle = 0;

        List<Future<Long>> results = new ArrayList<>();
        for (int i = 0; i < NUM_THREADS; i++) {
            results.add(es.submit(new MonteCarloCalculationTask((totalPoints / NUM_THREADS))));
        }

        for (Future<Long> r : results) {
            totalInsideCircle += r.get();
        }

        double piEstimate = (double) totalInsideCircle /totalPoints * 4.0;

        Instant finish = Instant.now();
        long runtimeMillis = Duration.between(start, finish).toMillis();

        System.out.printf("Estimated pi: %.6f\n", piEstimate);
        System.out.printf("Total runtime: %.3f seconds\n", runtimeMillis / 1000.0);

        es.shutdown();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long[] pointAmounts = {1_000_000L, 100_000_000L, 10_000_000_000L};

        for(long amount : pointAmounts) {
            runModel(amount);
        }
    }
}
