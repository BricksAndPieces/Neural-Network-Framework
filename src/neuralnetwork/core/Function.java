package neuralnetwork.core;

@SuppressWarnings("All")
public interface Function {

    double calculate(double v);

    Function LINEAR = v -> v;

    Function STEP = v -> v > 0 ? 1 : 0;

    Function SIGMOID = v -> (1 / (1 + Math.pow(Math.E, -v)));

    Function TANH = Math::tanh;

    Function RELU = v -> Math.max(0, v);

    Function LEAKY_RELU = v -> v > 0 ? v : v * 0.01;

    // ------ - - -- -- - --- -- -- - -- -- -- - -- -- -- -- - - -- - //

    Function SIGMOID_DER = v -> v * (1 - v);

    Function TANH_DER = v -> 1 - v * v;
}