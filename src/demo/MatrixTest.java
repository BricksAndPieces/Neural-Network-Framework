package demo;

import neuralnetwork.core.Matrix;

public class MatrixTest {

    public static void main(String[] args) {
        Matrix m1 = new Matrix(new double[][]{{1,1,1,1},
                                              {1,1,1,1},
                                              {1,1,1,1},
                                              {1,1,1,1}});

        Matrix m2 = new Matrix(new double[][]{{2,2,2,2},
                                              {2,2,2,2},
                                              {2,2,2,2},
                                              {2,2,2,2}});

        Matrix m3 = m1.crossover(m2);
        System.out.println(m3);
    }
}