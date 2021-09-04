package puzzle;

import solver.Configuration;
import solver.Solver;

import java.io.IOException;
import java.util.Optional;

/**
 * The main method.
 *
 * @author Hritik "Ricky" Gupta
 * @version 2021.7.8.2
 */
public class Sudoku {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Sudoku filename");
        }

        Solver solver = new Solver();
        SudokuConfig config = new SudokuConfig(args[0]);
        double start = System.currentTimeMillis();

        Optional<Configuration> solution = solver.solve(config);
        double end = System.currentTimeMillis();

        if (solution.isEmpty()) {
            System.out.println("\nNo solution for this puzzle.");
        } else {
            System.out.println("\nSolution found:");
            System.out.println(solution.get().display());
        }

        System.out.println("Time elapsed: " + (end - start) / 1000.0 + " seconds.");
        System.out.println("Number of configs generated: " + solver.getNumConfigs());
    }
}