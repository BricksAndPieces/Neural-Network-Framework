package neuralnetwork.util;

public interface Copyable<T extends Copyable<T>> {

    T copy();
}