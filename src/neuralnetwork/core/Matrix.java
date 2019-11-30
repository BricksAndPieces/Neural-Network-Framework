package neuralnetwork.core;

import neuralnetwork.util.Copyable;
import neuralnetwork.util.DimensionException;

import java.io.Serializable;
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
public class Matrix implements Copyable<Matrix>, Serializable
{
    private static final long serialVersionUID = 2L;

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

    /**
     * Constructor used for creating a copy of another Matrix
     * These matrixes contain the same values but are not linked with each other
     * This constructor is private, the copy method should be used to copy a Matrix
     *
     * @see Matrix#copy()
     *
     * @param o
     * The Matrix to copy when creating this Matrix object
     */
    private Matrix(Matrix o) {
        this(o.rows(), o.cols());
        for(int r = 0; r < rows(); r++)
            System.arraycopy(o.data[r], 0, data[r], 0, cols());
    }

    /**
     * Adds a value, x, to every value in this Matrix value
     * Uses the private helper class, MatrixEditor, to simplify matrix editing
     *
     * @see Matrix#edit(MatrixEditor)
     * @see MatrixEditor#edit(int, int, double)
     *
     * @param x
     * The value to be added to every value in the current Matrix object
     *
     * @return
     * Returns a new Matrix object with values equal to the current Matrix's
     * values plus the parameter value
     */
    public Matrix add(double x) {
        return new Matrix(this).edit((r, c, v) -> v + x);
    }

    /**
     * Adds a value from the provided Matrix to the corresponding value in this Matrix
     * Uses the private helper class, MatrixEditor, to simplify matrix editing
     *
     * @see Matrix#edit(MatrixEditor)
     * @see MatrixEditor#edit(int, int, double)
     *
     * @param o
     * The Matrix who's values should be added with the current Matrix
     *
     * @return
     * Returns a new Matrix object with values equal to the current Matrix's
     * values plus the provided Matrix's values
     */
    public Matrix add(Matrix o) {
        return new Matrix(this).edit(((r, c, v) -> v + o.data[r][c]));
    }

    /**
     * Subtracts a value, x, from every value in this Matrix value
     * Uses the private helper class, MatrixEditor, to simplify matrix editing
     *
     * @see Matrix#edit(MatrixEditor)
     * @see MatrixEditor#edit(int, int, double)
     *
     * @param x
     * The value to be subtracted from every value in the current Matrix object
     *
     * @return
     * Returns a new Matrix object with values equal to the current Matrix's
     * values minus the parameter value
     */
    public Matrix subtract(double x) {
        return new Matrix(this).edit((r, c, v) -> v - x);
    }

    /**
     * Subtracts a value from the provided Matrix, from the corresponding value in this Matrix
     * Uses the private helper class, MatrixEditor, to simplify matrix editing
     *
     * @see Matrix#edit(MatrixEditor)
     * @see MatrixEditor#edit(int, int, double)
     *
     * @param o
     * The Matrix who's values should be subtracted from the current Matrix
     *
     * @return
     * Returns a new Matrix object with values equal to the current Matrix's
     * values minus the provided Matrix's values
     */
    public Matrix subtract(Matrix o) {
        return new Matrix(this).edit(((r, c, v) -> v - o.data[r][c]));
    }

    /**
     * Multiplies a value, x, to every value in this Matrix value
     * Uses the private helper class, MatrixEditor, to simplify matrix editing
     *
     * @see Matrix#edit(MatrixEditor)
     * @see MatrixEditor#edit(int, int, double)
     *
     * @param x
     * The value to be multiplied with every value in the current Matrix object
     *
     * @return
     * Returns a new Matrix object with values equal to the current Matrix's
     * values times the parameter value
     */
    public Matrix multiply(double x) {
        return new Matrix(this).edit((r, c, v) -> v * x);
    }

    /**
     * Multiplies a value from the provided Matrix, with the corresponding value in this Matrix
     * Uses the private helper class, MatrixEditor, to simplify matrix editing
     *
     * @see Matrix#edit(MatrixEditor)
     * @see MatrixEditor#edit(int, int, double)
     *
     * @param o
     * The Matrix who's values should be multiplied with the current Matrix
     *
     * @return
     * Returns a new Matrix object with values equal to the current Matrix's
     * values multiplied with the provided Matrix's values
     */
    public Matrix multiply(Matrix o) {
        return new Matrix(this).edit((r, c, v) -> v * o.data[r][c]);
    }

    /**
     * Divides a value, x, from every value in this Matrix value
     * Uses the private helper class, MatrixEditor, to simplify matrix editing
     *
     * @see Matrix#edit(MatrixEditor)
     * @see MatrixEditor#edit(int, int, double)
     *
     * @param x
     * The value to be divided from every value in the current Matrix object
     *
     * @return
     * Returns a new Matrix object with values equal to the current Matrix's
     * values divided by the parameter value
     */
    public Matrix divide(double x) {
        return new Matrix(this).edit((r, c, v) -> v / x);
    }

    /**
     * Divides a value from the provided Matrix, from the corresponding value in this Matrix
     * Uses the private helper class, MatrixEditor, to simplify matrix editing
     *
     * @see Matrix#edit(MatrixEditor)
     * @see MatrixEditor#edit(int, int, double)
     *
     * @param o
     * The Matrix who's values should be divided from the current Matrix
     *
     * @return
     * Returns a new Matrix object with values equal to the current Matrix's
     * values divided by the provided Matrix's values
     */
    public Matrix divide(Matrix o) {
        return new Matrix(this).edit((r, c, v) -> v / o.data[r][c]);
    }

    /**
     * Calculates the dot product between this Matrix and provided Matrix
     * Uses the private helper class, MatrixEditor, to simplify matrix editing
     *
     * @see Matrix#edit(MatrixEditor)
     * @see MatrixEditor#edit(int, int, double)
     *
     * @param o
     * The Matrix who's values should be used to calculate the dot product with
     * the current Matrix. {https://en.wikipedia.org/wiki/Matrix_multiplication}
     *
     * @throws DimensionException
     * Throws a Dimension exception if the number of columns in this matrix does
     * not match the number of rows in the provided matrix
     *
     * @return
     * Returns a new Matrix object with the dot product of the current Matrix with
     * the provided Matrix
     */
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

    /**
     * Performs a function on every value of this Matrix
     * Uses the private helper class, MatrixEditor, to simplify matrix editing
     *
     * @see Matrix#edit(MatrixEditor)
     * @see MatrixEditor#edit(int, int, double)
     *
     * @param f
     * The function to be performed on every value of this Matrix
     *
     * @return
     * Returns a new Matrix object with values equal to the function of
     * the current Matrix's values
     */
    public Matrix function(Function f) {
        return new Matrix(this).edit((r, c, v) -> f.calculate(v));
    }

    /**
     * Rotates the matrix (flips the rows and columns)
     * Uses the private helper class, MatrixEditor, to simplify matrix editing
     *
     * @see Matrix#edit(MatrixEditor)
     * @see MatrixEditor#edit(int, int, double)
     *
     * @return
     * Returns a new Matrix object that is a rotated version of
     * the current Matrix object
     */
    public Matrix transpose() {
        return new Matrix(cols(), rows()).edit((r, c, v) -> data[c][r]);
    }

    /**
     * Randomizes all values in the Matrix (between -1 and 1)
     * Uses the private helper class, MatrixEditor, to simplify matrix editing
     *
     * @see Matrix#edit(MatrixEditor)
     * @see MatrixEditor#edit(int, int, double)
     *
     * @return
     * Returns a new Matrix object with randomized values
     */
    public Matrix randomize() {
        return new Matrix(rows(), cols()).edit(((r, c, v) -> Math.random() * 2 - 1));
    }

    /**
     * Mutates the values of this Matrix based on random chance
     * Uses the private helper class, MatrixEditor, to simplify matrix editing
     *
     * Based on the provided chance, the value can either be shifted a bit or
     * the value can be completely randomized.
     *
     * @see Matrix#edit(MatrixEditor)
     * @see MatrixEditor#edit(int, int, double)
     *
     * @param chance
     * The chance that a mutation should occur and whether that mutation should be random
     *
     * @param rng
     * The Random object to used to get the random shift for the mutation
     *
     * @return
     * Returns a new Matrix object that is a mutated version of the current
     * Matrix object
     */
    public Matrix mutate(double chance, Random rng) {
        return new Matrix(this.edit((r, c, v) -> {
            if(Math.random() > chance) return v;
            else return Math.random() > chance ? v + rng.nextGaussian()/50 : Math.random()*2-1;
        }));
    }

    /**
     * Creates a child matrix from two parent matrixes
     * Uses the private helper class, MatrixEditor, to simplify matrix editing
     *
     * Crossover is performed by splitting the matrix in half and combining one half
     * from parent1 and the other half from parent2
     *
     * @see Matrix#edit(MatrixEditor)
     * @see MatrixEditor#edit(int, int, double)
     *
     * @param o
     * The other matrix to be used in the crossover process
     *
     * @return
     * Returns a child Matrix which is a result of the crossover between the
     * two parent Matrices (current Matrix and provided Matrix)
     */
    public Matrix crossover(Matrix o) {
        final int rand = (int) (Math.random() * rows() * cols());
        return new Matrix(rows(), cols()).edit((r, c, v) -> (c * cols() + r <= rand ? data : o.data)[r][c]);
    }

    /**
     * @return
     * The amounts of rows in this Matrix
     */
    public int rows() {
        return data.length;
    }

    /**
     * @return
     * The amounts of columns in this Matrix
     */
    public int cols() {
        return data[0].length;
    }

    /**
     * @param i
     * The index of the row
     *
     * @return
     * Returns a row from the matrix as a single dimensional array
     */
    public double[] getRow(int i) {
        return data[i];
    }

    /**
     * @param i
     * The index of the column
     *
     * @return
     * Returns a column from the matrix as a single dimensional array
     */
    public double[] getCol(int i) {
        double[] col = new double[rows()];
        for(int r = 0; r < col.length; r++)
            col[r] = data[r][i];

        return col;
    }

    /**
     * @return
     * The raw 2 dimensional array storing all the values
     */
    public double[][] getData() {
        return data;
    }

    /**
     * @return
     * Returns a copy of the current Matrix object
     */
    @Override
    public Matrix copy() {
        return new Matrix(this);
    }

    /**
     * @return
     * Returns String representation of the Matrix for easy debugging
     */
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

    /**
     * Private helper method to simplify the editing of a Matrix
     * Acts as a wrapper for the for loops needed to edit the Matrix
     *
     * @see MatrixEditor#edit(int, int, double)
     *
     * @param editor
     * The MatrixEditor to use to edit the Matrix
     *
     * @return
     * Returns the current Matrix
     */
    private Matrix edit(MatrixEditor editor) {
        for(int r = 0; r < rows(); r++)
            for(int c = 0; c < cols(); c++)
                data[r][c] = editor.edit(r, c, data[r][c]);

        return this;
    }

    /**
     * Private helper interface to simplify the editing of a Matrix
     * Contains one method MatrixEditor#edit() that returns 3 doubles.
     * Returns: row, column and value
     *
     * @see Matrix#edit(MatrixEditor)
     */
    private interface MatrixEditor {
        double edit(int r, int c, double v);
    }
}