package neuralnetwork.core;

import util.Copyable;
import util.DimensionException;

import java.util.Random;

/**
 * Java class representing a Matrix
 * Acts as a wrapper for a 2 dimensional array of doubles
 * Used as a utility class to remove complicated array math
 *
 * Structure:
 * {{..}, {..}, {..}, {..},
 *  {..}, {..}, {..}, {..},
 *  {..}, {..}, {..}, {..}}
 *
 *  This Matrix has 3 rows and 4 columns
 *  Code form: 'double[][] raw = new double[rows][cols]'
 */

@SuppressWarnings("All")
public class Matrix implements Copyable<Matrix> {

    /**
     * Raw container for the double values stored in the Matrix object
     */
    private final double[][] data;

    /**
     * Simple contructor for a Matrix object
     * If this constructor is used, all values are 0
     *
     * @param rows
     * The number of rows in the Matrix
     *
     * @param cols
     * The number of columns in this matrix
     */
    public Matrix(int rows, int cols) {
        this.data = new double[rows][cols];
    }

    /**
     * Constructor used for creating a Matrix from a 1 dimensional array
     * There will be only one row in the matrix if this constructor is used
     *
     * @param singleDim
     * The single dimensional array to use to create the Matrix object
     */
    public Matrix(double[] singleDim) {
        this(1, singleDim.length);
        System.arraycopy(singleDim, 0, data[0], 0, singleDim.length);
    }

    /**
     * Constructor used for creating a Matrix from a 2 dimensional array
     * Array is copied but editing the original array will not change the matrix
     *
     * @param doubleDim
     * The double dimensional array to use to create the Matrix object
     */
    public Matrix(double[][] doubleDim) {
        this(doubleDim.length, doubleDim[0].length);
        for(int r = 0; r < rows(); r++)
            System.arraycopy(doubleDim[r], 0, data[r], 0, cols());
    }

    private Matrix(Matrix o) {
        this(o.rows(), o.cols());
        for(int r = 0; r < rows(); r++)
            System.arraycopy(o.data[r], 0, data[r], 0, cols());
    }

    // -------------------------------------------------------------------------------------------------- //

    public Matrix add(double x) {
        return new Matrix(this).edit((r, c, v) -> v + x);
    }

    public Matrix add(Matrix o) {
        return new Matrix(this).edit(((r, c, v) -> v + o.data[r][c]));
    }

    public Matrix subtract(double x) {
        return new Matrix(this).edit((r, c, v) -> v - x);
    }

    public Matrix subtract(Matrix o) {
        return new Matrix(this).edit(((r, c, v) -> v - o.data[r][c]));
    }

    public Matrix multiply(double x) {
        return new Matrix(this).edit((r, c, v) -> v * x);
    }

    public Matrix multiply(Matrix o) {
        return new Matrix(this).edit((r, c, v) -> v * o.data[r][c]);
    }

    public Matrix divide(double x) {
        return new Matrix(this).edit((r, c, v) -> v / x);
    }

    public Matrix divide(Matrix o) {
        return new Matrix(this).edit((r, c, v) -> v / o.data[r][c]);
    }

    // -------------------------------------------------------------------------------------------------- //

    public Matrix dotProduct(Matrix o) {
        if(cols() != o.rows())
            throw new DimensionException("Cols does not match Rows of other matrix");

        return new Matrix(rows(), o.cols()).edit((r, c, v) -> {
            double sum = 0;
            for(int i = 0; i < cols(); i++)
                sum += data[r][i] * o.data[i][c];

            return sum;
        });
    }

    public Matrix function(Function f) {
        return new Matrix(this).edit((r, c, v) -> f.calculate(v));
    }

    public Matrix transpose() {
        return new Matrix(cols(), rows()).edit((r, c, v) -> data[c][r]);
    }

    public Matrix randomize() {
        return new Matrix(rows(), cols()).edit(((r, c, v) -> Math.random() * 2 - 1));
    }

    // -------------------------------------------------------------------------------------------------- //

    public Matrix mutate(double chance, Random rng) {
        return new Matrix(this.edit((r, c, v) -> {
            if(Math.random() > chance) return v;
            else return Math.random() > chance ? v + rng.nextGaussian()/50 : Math.random()*2-1;
        }));
    }

    public Matrix crossover(Matrix o) {
        final int rand = (int) (Math.random() * rows() * cols());
        return new Matrix(rows(), cols()).edit((r, c, v) -> (c * cols() + r <= rand ? data : o.data)[r][c]);
    }

    // -------------------------------------------------------------------------------------------------- //

    public int rows() {
        return data.length;
    }

    public int cols() {
        return data[0].length;
    }

    public double[][] getData() {
        return data;
    }

    // -------------------------------------------------------------------------------------------------- //

    @Override
    public Matrix copy() {
        return new Matrix(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int r = 0; r < rows(); r++) {
            sb.append("[");
            for(int c = 0; c < cols(); c++) {
                if(c > 0) sb.append(", ");
                sb.append(data[r][c]);
            }
            sb.append("]\n");
        }

        return new String(sb);
    }

    // -------------------------------------------------------------------------------------------------- //

    private Matrix edit(MatrixEditor editor) {
        for(int r = 0; r < rows(); r++)
            for(int c = 0; c < cols(); c++)
                data[r][c] = editor.edit(r, c, data[r][c]);

        return this;
    }

    private interface MatrixEditor {
        double edit(int r, int c, double v);
    }
}