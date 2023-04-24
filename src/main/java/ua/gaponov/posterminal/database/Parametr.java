package ua.gaponov.posterminal.database;

/**
 *
 * @author wmcon
 */
public class Parametr<T> {
    private T value;

    public Parametr(T value) {
        this.value = value;
    }

    public static Parametr getParametr (Object value){
        return new Parametr(value);
    }

    public T getValue() {
        return value;
    }
}
