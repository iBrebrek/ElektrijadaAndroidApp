package hr.fer.elektrijada.activities.bluetooth;


/**
 * Created by Ivica Brebrek
 */
public interface IComparable<T> {
    /** Ovo bi proverava je li npr. dogadaj isti. */
    boolean equals(Object o);
    /** Ovo se koristi samo ako je isSame true, ovo provjerava jesu li i ostali podatci isti. */
    boolean detailsSame(T o);
}
