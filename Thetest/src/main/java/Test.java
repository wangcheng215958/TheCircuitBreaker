import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Test {
    public static void main(String[] args) {
        Supplier<CodeDefualt> defaul = CodeDefualt::new;
        Supplier<CodeFailBack> failback = CodeFailBack::new;
        CircuitBreakerRunner cir = new CircuitBreakerRunner(defaul, failback, 0.5, 2000, TimeUnit.MILLISECONDS);
        cir.Runner();
    }
}
