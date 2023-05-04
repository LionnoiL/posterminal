package ua.gaponov.posterminal.database;

/**
 * @author Andriy Gaponov
 */
public class Parameter<T> {
    private T value;

    public Parameter(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
