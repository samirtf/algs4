package week.a1;

import java.util.Arrays;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 3-SUM in quadratic time. Design an algorithm for the 3-SUM problem that takes time//NOPMD//NOPMD
 * proportional to n2 in the worst case. You may
 * assume that you can sort the n integers in time proportional to n2 or better.
 */
public class Week1Question1 implements Executable {//NOPMD

  /**
   * Class name.
   */
  private static final String CLASS_NAME = Week1Question1.class.getName();

  /**
   * Logger.
   */
  private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

  /**
   * Executes the question.
   */
  @Override
  public void execute() {
    //LOGGER.log(Level.INFO,"%s must be implemented...", CLASS_NAME);

    final int[] input = {-40, 2,-10, 5, 2, 7, 10, 40, 0, 15, -1, 2, -50, 40, -2, -3, -1, 15, 2, -10,
        -10};

    //3-SUM
    // Sort O(N lg(N))
    Arrays.sort(input);
    int count = 0;
    for(int i = 0; i < input.length -2; i++) {
      System.out.println("i: " + i);
      int start = i + 1;
      int end = input.length - 1;
      /*System.out.println(new Formatter().format("in[i]:%s :: int[start]:%s :: in[end]: %s",
          input[i],
          input[start],
          input[end])
          .toString());*/

      while(start < end) {

        if(input[i] + input[start] + input[end] == 0) {
          System.out.println(new Formatter().format("\tequal\tin[i]:%s\t :: int[start]:%s :: " +
                  "in[end]: %s",
              input[i],
              input[start],
              input[end])
              .toString());
          count++;
        }
        if( -(input[start] + input[end]) > input[i] && start < end) {
          System.out.println(new Formatter().format("\tless\tin[i]:%s\t :: int[start]:%s ::" +
                  " " +
                  "in[end]: %s",
              input[i],
              input[start],
              input[end])
              .toString());
          start++;
        }
        if( -(input[start] + input[end]) <= input[i] && start < end) {
          System.out.println(new Formatter().format("\thigher\tin[i]:%s\t :: int[start]:%s :: " +
                  "in[end]: %s",
              input[i],
              input[start],
              input[end])
              .toString());
          end--;
        }
      }
    }

    System.out.println("count:" + count);

  }

  public static void main(String[] args) {
    new Week1Question1().execute();
  }

}
