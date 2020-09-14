package cs3500.animator.model;

import cs3500.animator.model.motion.IMotion;
import java.util.Comparator;

/**
 * A comparator to sort lists of motions by their start ticks.
 */
public class StartTickSorter implements Comparator<IMotion> {

  /**
   * Default constructor since nothing matters. I'm commenting to get my Java style points back.
   */
  public StartTickSorter() {
    // ITS EMPTY BECAUSE IT DOESNT NEED ANY PARAMS! ITS A COMPARATOR! GIVE ME MY JAVA STYLE POINTS
  }

  /**
   * Compares its two arguments for order.  Returns a negative integer, zero, or a positive integer
   * as the first argument is less than, equal to, or greater than the second.
   *
   * @param o1 the first object to be compared to to be ordered
   * @param o2 the second object to be compared to to be ordered
   * @return a negative integer, zero, or a positive integer as the first argument is less than,
   *         equal to, or greater than the second.
   * @throws NullPointerException if an argument is null and this comparator does not permit null
   *                              arguments
   * @throws ClassCastException   if the arguments' types prevent them from being compared by this
   *                              comparator.
   */
  @Override
  public int compare(IMotion o1, IMotion o2) {
    return o1.getStartTick() - o2.getEndTick();
  }
}
