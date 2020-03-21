import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** */
public class AtomicLoL {

    /**
     * initializes atomically
     * 
     * @param atomicRef
     * @param supplier
     * @param disp
     * @return the non-null atomic reference value
     */
    static <R> R init(AtomicReference<R> atomicRef, Supplier<? extends R> supplier, Consumer<? super R> disp) {
        while(true) {
            R currentValue = atomicRef.get();
            if(currentValue == null) {
                // supply a value in case of null
                R supplied = supplier.get();
                // when executing compareAndSet with the supplied value,
                // if the currentValue was no longer null (because of some concurrent modification)
                // then we dispose of the supplied value and retry
                if(!atomicRef.compareAndSet(null, supplied)) {
                    if(disp != null && supplied != null) {
                        disp.accept(supplied);
                    }
                    continue;
                }
            }
            return currentValue;
        }
    }

}
