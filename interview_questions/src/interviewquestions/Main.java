package interviewquestions;


import week.a1.Executable;
import week.a1.Week1Question1;

import java.util.ArrayList;
import java.util.List;

/**
 * Class is responsible for run all user cases.
 */
public class Main {

    /**
     * Runs all user cases.
     *
     * @param args command line input.
     */
    public static void main(final String[] args) {
        final List<Executable> executables = new ArrayList<>();
        executables.add(new Week1Question1());
        for (Executable exe : executables) {
            exe.execute();
        }
    }
}
