package neuralnetwork.core;

import java.io.Serializable;

/**
 * Functional interface that represents different mathematical
 * functions. These are used as activation functions or during training.
 */
@FunctionalInterface
@SuppressWarnings("All")
public interface Function extends Serializable  {

    static final long serialVersionUID = 3L;

    double calculate(double v);

    Function LINEAR = v -> v;

    Function STEP = v -> v > 0 ? 1 : 0;

    Function SIGMOID = v -> 1 / (1 + Math.pow(Math.E, -v));

    Function CUSTOM_SIGMOID =  v -> 1 / (1 + Math.pow(Math.E, -4.9 * v));

    Function TANH = Math::tanh;

    Function RELU = v -> Math.max(0, v);

    Function LEAKY_RELU = v -> v > 0 ? v : v * 0.01;

    // ------ - - -- -- - --- -- -- - -- -- -- - -- -- -- -- - - -- - //

    Function SIGMOID_DER = v -> v * (1 - v);

    Function TANH_DER = v -> 1 - v * v;
}