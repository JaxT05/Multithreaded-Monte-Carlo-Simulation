import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

public class SingleThreadedSimulation {
    private static void runModel(long totalPoints) {
        // Record the start time in milliseconds
        Instant start = Instant.now();

        // Total number of random points to generate
        // You can change this value to test different sample sizes
        long insideCircle = 0;

        // Perform the Monte Carlo simulation
        for(long i=0; i<totalPoints; i++){
            // We generate a random point with coordinates (x, y)
            // inside this square, which is centered at the origin
            // and goes from -1.0 to 1.0 in both the x and y directions.
            double x = ThreadLocalRandom.current().nextDouble(-1.0,1.0);
            double y = ThreadLocalRandom.current().nextDouble(-1.0,1.0);
            // Check if the point (x, y) is inside the unit circle centered
            // at (0, 0). A point is inside the circle if its distance from
            // the origin is less than or equal to 1, i.e., x*x + y*y <= 1
            if(x*x+y*y <= 1.0){
                insideCircle ++;
            }
        }

        // Estimate the value of pi using the formula:
        // pi = 4 * (number of points inside the circle) / (total number of points)
        double piEstimate = (double) insideCircle /totalPoints * 4.0;

        // Record the end time and compute total runtime
        Instant finish = Instant.now();
        long runtimeMillis = Duration.between(start, finish).toMillis();

        System.out.printf("Estimated pi: %.6f\n", piEstimate);
        System.out.printf("Total runtime: %.3f seconds\n", runtimeMillis / 1000.0);
    }

    public static void main(String[] args) {
        long[] pointAmounts = {1_000_000L, 100_000_000L, 10_000_000_000L};

        for(long amount : pointAmounts) {
            runModel(amount);
        }
    }
}
