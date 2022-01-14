import mrc.common.tools.JsonUtil;
import mrc.common.tools.ListUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;

public class SparseMatrix {

    private final static double DAMPING_FACTOR = 0.85;
    private final static double REST_OF_DAMPING_FACTOR = 1 - DAMPING_FACTOR;
    private double[][] map = null;
    private int size = 0;
    public double randomizer = 0;
    private double rest = 0;

    public SparseMatrix(int size) {
        this.randomizer = 1 / (double) size;
        this.rest = REST_OF_DAMPING_FACTOR * this.randomizer;
        this.size = size;
        this.map = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.map[i][j] = -1;
            }
        }
    }

    public void setValue(int row, int column, double valueToAdd) {
        map[row][column] = valueToAdd * DAMPING_FACTOR + rest;
    }

    public double[] createVector(double[] initPG) {
        double[] result = new double[size];
        boolean[] seen = new boolean[size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] != -1) {
                    if (seen[i]) {
                        double sum = result[i];
                        result[i] = map[i][j] * initPG[j] + sum;
                        seen[i] = true;
                    } else {
                        result[i] = map[i][j] * initPG[j];
                        seen[i] = true;
                    }
                } else {
                    double value = REST_OF_DAMPING_FACTOR * randomizer * initPG[j];
                    if (seen[i]) {
                        double sum = result[i];
                        result[i] = (value + sum);
                        seen[i] = true;
                    }
                }
            }
        }
        return result;
    }
}
