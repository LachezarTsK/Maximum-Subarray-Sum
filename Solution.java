import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int numberOfQuieries = scanner.nextInt();

    for (int i = 0; i < numberOfQuieries; i++) {
      int lengthArray = scanner.nextInt();
      long valueForModulo = scanner.nextLong();
      Element[] elements = new Element[lengthArray];

      elements[0] = new Element(0, scanner.nextLong());
      elements[0].sumFromStart_modulo = elements[0].inputValue % valueForModulo;

      for (int j = 1; j < lengthArray; j++) {
        elements[j] = new Element(j, scanner.nextLong());
        elements[j].sumFromStart_modulo = (elements[j - 1].sumFromStart_modulo + elements[j].inputValue) % valueForModulo;
      }
      System.out.println(find_maxSum_modulo_Subarray(elements, valueForModulo));
    }
    scanner.close();
  }

  /**
   * Finds the maximum subarray sum, modulo valueForModulo.
   *
   * 1. Iterating through the array, sorted by sumFromStart_modulo in ascending order.
   * 2. If the original input array index is less than the original input array index 
   *    of the preceding element, then check for maximum sum, modulo valueForModulo, 
   *    at the subarray between these two indexes: exclusive the smaller index, inclusive the larger index.
   *
   * Example: 
   * valueForModulo: 8.
   * Array, sorted by sumFromStart_modulo in ascending order:[2, 4, 5, 6] 
   * Original input array index:                              1  0  3  2 
   * candidate_maxSumModulo: 
   * subaray(index=0(exclusive), index=1(inclusive)) ==> (valueForModulo - (4 - 2)) 
   * subaray(index=2(exclusive), index=3(inclusive)) ==> (valueForModulo - (6 - 5)) 
   *
   * @return A long integer, representing the maximum sum of a subarray, modulo valueForModulo.
   */
  private static long find_maxSum_modulo_Subarray(Element[] elements, long valueForModulo) {

    // Sort by sumFromStart_modulo in ascending order.
     Arrays.sort(elements);
    long maxSum_modulo = elements[elements.length - 1].sumFromStart_modulo;

    for (int i = elements.length - 1; i > 0; i--) {

      if (elements[i].inputIndex < elements[i - 1].inputIndex) {
        long candidate_maxSumModulo = valueForModulo - (elements[i].sumFromStart_modulo - elements[i - 1].sumFromStart_modulo);
        candidate_maxSumModulo = candidate_maxSumModulo % valueForModulo;
        maxSum_modulo = Math.max(maxSum_modulo, candidate_maxSumModulo);
      }
    }
    return maxSum_modulo;
  }

  private static class Element implements Comparable<Element> {

    // sum, modulo valueForModulo, of the subarray(0(inclusive), inputIndex(inclusive)).
    long sumFromStart_modulo;
    int inputIndex;
    long inputValue;

    public Element(int inputIndex, long inputValue) {
      this.inputIndex = inputIndex;
      this.inputValue = inputValue;
    }

    // Sort by sumFromStart_modulo in ascending order.
    @Override
    public int compareTo(Element other) {
      return Long.valueOf(this.sumFromStart_modulo).compareTo(Long.valueOf(other.sumFromStart_modulo));
    }
  }
}
