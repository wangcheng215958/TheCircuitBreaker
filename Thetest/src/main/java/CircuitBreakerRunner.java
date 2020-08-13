import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class CircuitBreakerRunner<T> {

    public Supplier<T> defaul;
    public Supplier<T> failback;

    private CircuitBreaker circuitBreaker;

    private long delay;

    private boolean allowable = true;

    private AtomicInteger total;
    private AtomicInteger unsuccess;

    public CircuitBreakerRunner(Supplier<T> defaul, Supplier<T> failback, double rate, long delay, TimeUnit delayUnit) {
        this.circuitBreaker = new CircuitBreaker(rate);
        this.failback = failback;
        this.defaul = defaul;
        this.delay = delayUnit.toMillis(delay);
    }


    public T Runner() {
        while (true) {
            try {
                if (allowable) {
                    return defaul.get();
                }
            } catch (Exception e) {
                boolean b = this.circuitBreaker.Allowable(total, unsuccess, allowable);
                if (!b) {
                    allowable = false;
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            allowable = true;
                        }
                    }, delay);
                }
            }
            if (failback != null) {
                return failback.get();
            }
            return null;

        }
    }
}
