import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.motion.Motion;
import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Shape;
import org.junit.Test;


/**
 * This class contains tests for Motions, including tests for all of IMotion's methods and on
 * creating different motion factories.
 */
public class MotionTest {


  IMotion motionRectangle = new Motion(0, 100,
      new Rectangle(0, 0, 100, 100, 0, 0, 0));

  IMotion motionRectangleDifferentXY = new Motion(50, 100,
      new Rectangle(100, 100, 100, 100, 0, 0, 0));

  IMotion motionEllipse = new Motion(40, 80,
      new Ellipse(0, 0, 100, 100, 0, 0, 0));


  @Test
  public void testIllegalMotionConstructorInvalidStartAndEnd() {
    try {
      IMotion illegalMotionRect = new Motion(30, 0,
          new Rectangle(0, 0, 100, 100, 0, 0, 0));
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Start tick must be less than end tick", e.getMessage());
    }

    try {
      IMotion illegalMotionEll = new Motion(10, 5,
          new Ellipse(0, 0, 100, 100, 0, 0, 0));
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Start tick must be less than end tick", e.getMessage());
    }

  }


  @Test
  public void testIllegalMotionConstructorNegativeTicks() {
    try {
      IMotion illegalMotionRect = new Motion(-30, -20,
          new Rectangle(0, 0, 100, 100, 0, 0, 0));
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Tick values cannot be negative.", e.getMessage());
    }

    try {
      IMotion illegalMotionEll = new Motion(1, -11,
          new Ellipse(0, 0, 100, 100, 0, 0, 0));
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Tick values cannot be negative.", e.getMessage());
    }

    try {
      IMotion illegalMotionEll = new Motion(-10, 11,
          new Ellipse(0, 0, 100, 100, 0, 0, 0));
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Tick values cannot be negative.", e.getMessage());
    }
  }

  @Test
  public void testGetShapeAt() {
    Shape baby = new Rectangle(0, 0, 100, 100, 128, 128, 128);
    IMotion downAndRight = new Motion(0, 100,
        new Rectangle(100, 100, 100, 100, 128, 128, 128));
    IMotion justRight = new Motion(0, 75,
        new Rectangle(100, 0, 100, 100, 128, 128, 128));
    assertTrue(downAndRight.getShapeAt(baby, 50).equals(
        new Rectangle(50, 50, 100, 100, 128, 128, 128)));
    // Rounds down
    assertTrue(justRight.getShapeAt(baby, 50).equals(
        new Rectangle(66, 0, 100, 100, 128, 128, 128)));
  }

  @Test
  public void testInvalidApplyDifferentShapes() {
    try {
      Shape ellipse = new Ellipse(0, 0, 1, 1, 0, 0, 0);
      motionRectangleDifferentXY.apply(ellipse);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Start and end shape must be of the same type", e.getMessage());
    }
  }


  @Test
  public void testApplyEllipse() {
    Shape e = new Ellipse(0, 0, 1, 1, 0, 0, 0);
    IMotion motionEllipseTest = new Motion(40, 100,
        new Ellipse(100, 100, 100, 100, 100, 100, 100));
    motionEllipseTest.apply(e);
    // makes sure that the new mutated ellipse equals the end shape that was given in the motion
    // constructor
    assertTrue(e.equals(motionEllipseTest.getEndShape()));
  }


  @Test
  public void testApplyRectangle() {
    Shape rect = new Rectangle(0, 0, 1, 1, 0, 0, 0);
    IMotion motionRectangleTest = new Motion(40, 100,
        new Rectangle(100, 100, 100, 100, 100, 100, 100));
    motionRectangleTest.apply(rect);
    // makes sure that the new mutated rectangle equals the end shape that was given in the motion
    // constructor
    assertTrue(rect.equals(motionRectangleTest.getEndShape()));
  }


  @Test
  public void testApplyRectangleMoveXY() {
    Shape rect = new Rectangle(0, 0, 100, 100, 0, 0, 0);
    motionRectangleDifferentXY.apply(rect);
    // makes sure that the new mutated rectangle equals the end shape that was given in the motion
    // constructor
    assertEquals(rect, motionRectangleDifferentXY.getEndShape());
  }


  @Test
  public void testApplyEllipseMoveXY() {
    Shape e = new Ellipse(0, 0, 100, 100, 0, 0, 0);
    IMotion motionEllipseMove = new Motion(0, 100,
        new Ellipse(100, 100, 100, 100, 0, 0, 0));
    motionEllipseMove.apply(e);
    // makes sure that the new mutated rectangle equals the end shape that was given in the motion
    // constructor
    assertEquals(e, motionEllipseMove.getEndShape());
  }

  @Test
  public void testApplyRectangleChangeDimensions() {
    Shape rect = new Rectangle(0, 0, 1, 1, 0, 0, 0);
    IMotion motionRectangleTest = new Motion(40, 100,
        new Rectangle(0, 0, 100, 100, 0, 0, 0));
    motionRectangleTest.apply(rect);

    assertTrue(rect.equals(motionRectangleTest.getEndShape()));
  }

  @Test
  public void testApplyRectangleChangeColor() {
    Shape rect = new Rectangle(0, 0, 1, 1, 0, 0, 0);
    IMotion motionRectangleTest = new Motion(40, 100,
        new Rectangle(0, 0, 1, 1, 100, 100, 100));
    motionRectangleTest.apply(rect);

    assertTrue(rect.equals(motionRectangleTest.getEndShape()));
  }

  @Test
  public void testApplyRectangleTestsWithDifferentTickValuesToCheckRounding() {
    Shape rect = new Rectangle(0, 0, 1, 1, 0, 0, 0);
    IMotion motionRectangleTest = new Motion(40, 90,
        new Rectangle(100, 100, 100, 100, 100, 100, 100));
    motionRectangleTest.apply(rect);
    // makes sure that the new mutated rectangle equals the end shape that was given in the motion
    // constructor
    assertTrue(rect.equals(motionRectangleTest.getEndShape()));

    Shape rect2 = new Rectangle(0, 0, 1, 1, 0, 0, 0);
    IMotion motionRectangleTest2 = new Motion(40, 70,
        new Rectangle(100, 100, 101, 100, 100, 100, 100));
    motionRectangleTest2.apply(rect2);
    // makes sure that the new mutated rectangle equals the end shape that was given in the motion
    // constructor
    assertTrue(rect2.equals(motionRectangleTest2.getEndShape()));
  }


  @Test
  public void testGetStartTick() {
    assertEquals(0, motionRectangle.getStartTick());
    assertEquals(40, motionEllipse.getStartTick());
  }


  @Test
  public void testGetEndTick() {
    assertEquals(100, motionRectangle.getEndTick());
    assertEquals(80, motionEllipse.getEndTick());
  }

  @Test
  public void testGetEndShape() {
    assertEquals(new Rectangle(0, 0, 100, 100, 0, 0, 0),
        motionRectangle.getEndShape());
    assertEquals(new Ellipse(0, 0, 100, 100, 0, 0, 0),
        motionEllipse.getEndShape());
  }


  @Test
  public void testGetCopy() {
    IMotion motionRectangle = new Motion(0, 100,
        new Rectangle(0, 0, 100, 100, 0, 0, 0));
    assertTrue(motionRectangle.copy().equals(motionRectangle));

    IMotion motionEllipse = new Motion(0, 100,
        new Ellipse(0, 0, 100, 100, 0, 0, 0));
    assertTrue(motionEllipse.copy().equals(motionEllipse));
  }


  @Test
  public void testEquals() {
    IMotion motionRectangle = new Motion(0, 100,
        new Rectangle(0, 0, 100, 100, 0, 0, 0));
    IMotion motionEllipse = new Motion(0, 100,
        new Ellipse(0, 0, 100, 100, 0, 0, 0));
    Rectangle r = new Rectangle();
    assertFalse(motionRectangle.equals(motionEllipse));
    assertTrue(motionRectangle.equals(motionRectangle.copy()));
    assertTrue(motionEllipse.equals(motionEllipse.copy()));
    assertFalse(motionRectangle.equals(r));
  }


  @Test
  public void testToSVGString() {
    assertEquals("\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"50001ms\" "
            + "attributeName=\"width\" from=\"50\" to=\"100\" fill=\"freeze\" /> \n"
            + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"50000ms\" "
            + "attributeName=\"height\" from=\"50\" to=\"100\" fill=\"freeze\" /> \n"
            + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"50000ms\" "
            + "attributeName=\"fill\" "
            + "from=\"rgb(200,200,200)\" to=\"rgb(0,0,0)\" fill=\"freeze\" /> \n",
        motionRectangle.toSVGString(new Rectangle(0, 0, 50, 50,
            200, 200, 200), 2));
  }


  @Test
  public void testSetStartTick() {
    Shape e = new Ellipse(1, 2, 3, 4, 5, 6, 7);
    IMotion motionEllipseTest = new Motion(40, 100,
        new Ellipse(100, 100, 100, 100, 100, 100, 100));
    motionEllipseTest.setStartTick(30);
    assertEquals(30, motionEllipseTest.getStartTick());
  }

  @Test
  public void testSetStartTickBad() {
    try {
      Shape e = new Ellipse(1, 2, 3, 4, 5, 6, 7);
      IMotion motionEllipseTest = new Motion(40, 100,
          new Ellipse(100, 100, 100, 100, 100, 100, 100));
      motionEllipseTest.setStartTick(-1);
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Tick cannot be negative", e.getMessage());
    }
    try {
      Shape e = new Ellipse(1, 2, 3, 4, 5, 6, 7);
      IMotion motionEllipseTest = new Motion(40, 100,
          new Ellipse(100, 100, 100, 100, 100, 100, 100));
      motionEllipseTest.setStartTick(110);
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Start tick cannot be greater than end tick", e.getMessage());
    }
  }

  @Test
  public void testSetEndTick() {
    Shape e = new Ellipse(1, 2, 3, 4, 5, 6, 7);
    IMotion motionEllipseTestNew = new Motion(40, 90,
        new Ellipse(100, 100, 100, 100, 100, 100, 100));
    motionEllipseTestNew.setEndTick(100);
    assertEquals(100, motionEllipseTestNew.getEndTick());
  }

  @Test
  public void testSetEndTickBad() {
    try {
      Shape e = new Ellipse(1, 2, 3, 4, 5, 6, 7);
      IMotion motionEllipseTest = new Motion(40, 100,
          new Ellipse(100, 100, 100, 100, 100, 100, 100));
      motionEllipseTest.setEndTick(-1);
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Tick cannot be negative", e.getMessage());
    }
    try {
      Shape e = new Ellipse(1, 2, 3, 4, 5, 6, 7);
      IMotion motionEllipseTest = new Motion(40, 100,
          new Ellipse(100, 100, 100, 100, 100, 100, 100));
      motionEllipseTest.setEndTick(30);
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Start tick cannot be greater than end tick", e.getMessage());
    }
  }

  @Test
  public void testSetEndShape() {
    Shape e = new Ellipse(1, 2, 3, 4, 5, 6, 7);
    IMotion motionEllipseTest = new Motion(40, 100,
        new Ellipse(100, 100, 100, 100, 100, 100, 100));
    motionEllipseTest.setEndShape(new Ellipse(200, 200, 200, 200,
        200, 200, 200));
    assertEquals(new Ellipse(200, 200, 200, 200,
        200, 200, 200), motionEllipseTest.getEndShape());
  }


}