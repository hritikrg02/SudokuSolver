package solver;

import java.util.Optional;

/**
 * Attempts to find a solution using the DFS algorithm
 *
 * @author Hritik "Ricky" Gupta
 * @version 2021.7.8.1
 */
public class Solver {

    private long numConfigs = 0;

    public long getNumConfigs() {
        return this.numConfigs;
    }

    public Optional<Configuration> solve(Configuration config) {
        if (config.isSolution()) {
            return Optional.of(config);
        } else {
            for (Configuration child : config.getSuccessors()) {
                ++this.numConfigs;
                if (child.isValid()) {
                    Optional<Configuration> sol = solve(child);
                    if (sol.isPresent()) {
                        return sol;
                    }
                }
            }
        }
        return Optional.empty();
    }
}
