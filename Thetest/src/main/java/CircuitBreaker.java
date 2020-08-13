import java.util.concurrent.atomic.AtomicInteger;

public class CircuitBreaker {
    private double rate;

    public CircuitBreaker(double rate) {
        this.rate = rate;
    }

    public boolean Allowable(AtomicInteger total, AtomicInteger unsuccess, boolean allowable) {
        total.incrementAndGet();
        unsuccess.incrementAndGet();
        if (unsuccess.get() / total.get() >= rate) {
            allowable = false;
        }
        return allowable;
    }
}
