package ua.gaponov.posterminal.dataexchange;

/**
 * @author Andriy Gaponov
 */
public interface ExchangeBuilder<T, E> {
    T create(E exchangeData);
}
