package puzzle;

import solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a configuration of a Sudoku puzzle.
 *
 * @author Hritik "Ricky" Gupta
 * @version 2021.7.8.4
 */
public class SudokuConfig implements Configuration {

    private char[][] board;
    public static int dim;
    public final static char EMPTY = '-';

    public SudokuConfig(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));

        dim = Integer.parseInt(in.readLine());
        this.board = new char[dim][dim];

        for (int row = 0; row < dim; ++row) {
            String[] fields = in.readLine().split("\\s+");
            for (int col = 0; col < dim; ++col) {
                this.board[row][col] = fields[col].charAt(0);
            }
        }

        in.close();
    }

    public SudokuConfig(SudokuConfig other) {
        this.board = new char[dim][dim];
        for (int row = 0; row < dim; ++row) {
            System.arraycopy(other.board[row], 0, this.board[row], 0, dim);
        }
    }

    @Override
    public boolean isSolution() { //board is solution if it is valid and there are no empty spaces spaces
        for (int row = 0; row < dim; ++row) {
            for (int col = 0; col < dim; ++col) {
                if (this.board[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        return isValid();
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        ArrayList<Configuration> successors = new ArrayList<>();
        for (int row = 0; row < dim; ++row) {
            for (int col = 0; col < dim; ++col) {
                if (this.board[row][col] == EMPTY) {
                    for (int i = 1; i <= dim; ++i) {
                        SudokuConfig successor = new SudokuConfig(this);
                        successor.board[row][col] = Character.forDigit(i, 10);
                        successors.add(successor);
                    }
                    return successors;
                }
            }
        }
        return successors;
    }

    @Override
    public boolean isValid() {
        HashSet<Integer> loopedCols = new HashSet<>();
        for (int row = 0; row < dim; ++row) {
            StringBuilder outputRow = new StringBuilder();
            for (int col = 0; col < dim; ++col) {
                outputRow.append(this.board[row][col]);
                if (loopedCols.add(col)) {
                    StringBuilder outputCol = new StringBuilder();
                    for (int colRow = 0; colRow < dim; ++colRow) {
                        outputCol.append(this.board[colRow][col]);
                    }
                    for (int i = 1; i <= dim; ++i) {
                        if (outputCol.toString().matches(".*" + i + ".*" + i + ".*")) {
                            return false;
                        }
                    }
                }
            }
            for (int i = 1; i <= dim; ++i) {
                if (outputRow.toString().matches(".*" + i + ".*" + i + ".*")) {
                    return false;
                }
            }
        }

        for (int subBoardRow = 0; subBoardRow < Math.sqrt(dim); ++subBoardRow) {
            for (int subBoardCol = 0; subBoardCol < Math.sqrt(dim); ++subBoardCol) {
                if (!checkSubBoard(subBoardRow, subBoardCol)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkSubBoard(int row, int col) { //sub board row and col
        StringBuilder subBoard = new StringBuilder();
        for (int subRow = 0; subRow < Math.sqrt(dim); ++subRow) {
            for (int subCol = 0; subCol < Math.sqrt(dim); ++subCol) {
                subBoard.append(this.board[(row * 3) + subRow][(col * 3) + subCol]);
            }
        }
        for (int i = 1; i <= dim; ++i) {
            if (subBoard.toString().matches(".*" + i + ".*" + i + ".*")) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String display() {
        StringBuilder output = new StringBuilder();
        output.append("\n");
        for (int row = 0; row < dim; ++row) {
            for (int col = 0; col < dim; ++col) {
                if (col % 3 == 0) {
                    output.append("|\s");
                }
                output.append(this.board[row][col]);
                output.append("\s");
            }
            output.append("|\n");
        }
        return output.toString();
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof SudokuConfig) {
            SudokuConfig s = (SudokuConfig) o;
            result = Arrays.deepEquals(this.board, s.board);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.board);
    }
}
