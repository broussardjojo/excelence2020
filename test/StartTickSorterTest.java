import static org.junit.Assert.assertEquals;

import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.motion.Motion;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.StartTickSorter;
import org.junit.Test;


/**
 * This class contains tests for the StartTickSorter class.
 */
public class StartTickSorterTest {


  StartTickSorter s = new StartTickSorter();


  IMotion motionRectangle0 = new Motion(101, 102,
      new Rectangle(0, 0, 100, 100, 0, 0, 0));

  IMotion motionRectangle0B = new Motion(100, 101,
      new Rectangle(1, 4, 100, 100, 1, 2, 3));


  IMotion motionRectangle1 = new Motion(1, 200,
      new Rectangle(0, 0, 100, 100, 0, 0, 0));

  IMotion motionRectangle2 = new Motion(0, 50,
      new Rectangle(0, 0, 100, 100, 0, 0, 0));


  @Test
  public void testCompareSameTick() {
    assertEquals(0, s.compare(motionRectangle0, motionRectangle0B));
  }


  @Test
  public void testCompareNegativeResult() {
    assertEquals(-100, s.compare(motionRectangle0B, motionRectangle1));
  }


  @Test
  public void testComparePositiveResult() {
    assertEquals(50, s.compare(motionRectangle0B, motionRectangle2));
  }
}