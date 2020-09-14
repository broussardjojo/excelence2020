import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Shape;
import org.junit.Test;


/**
 * This class contains tests for the Ellipse class, including tests for methods from the
 * AbstractShape class.
 */
public class EllipseTest {


  Shape e1 = new Ellipse(5, 10, 1, 2, 255, 126, 0);

  @Test
  public void InvalidConstructorNegativeDimensions() {
    try {
      Shape eInvalidWidth = new
          Ellipse(5, 10, -1, 2, 255, 126, 0);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Dimensions cannot be negative", e.getMessage());
    }
    try {
      Shape eInvalidHeight = new
          Ellipse(5, 10, 1, -2, 255, 126, 0);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Dimensions cannot be negative", e.getMessage());
    }
    try {
      Shape eInvalidWandH = new
          Ellipse(5, 10, -1, -2, 255, 126, 0);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Dimensions cannot be negative", e.getMessage());
    }
  }


  @Test
  public void InvalidConstructorInvalidColor() {
    try {
      Shape eInvalidR = new Ellipse(5, 10, 1, 2, 256, 126, 0);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }
    try {
      Shape eInvalidG = new Ellipse(5, 10, 1, 2, 255, 256, 0);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }
    try {
      Shape eInvalidB = new Ellipse(5, 10, 1, 2, 255, 255, 256);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }

    try {
      Shape eInvalidRNegative =
          new Ellipse(5, 10, 1, 2, -1, 255, 255);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }

    try {
      Shape eInvalidGNegative =
          new Ellipse(5, 10, 1, 2, 1, -1, 255);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }

    try {
      Shape eInvalidBNegative =
          new Ellipse(5, 10, 1, 2, -1, 255, -1);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }

    try {
      Shape eInvalidAllNegative =
          new Ellipse(5, 10, 1, 2, -1, -255, -255);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }

    try {
      Shape eInvalidAllInvalid =
          new Ellipse(5, 10, 1, 2, 256, 256, 256);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }

  }

  @Test
  public void testGetX() {
    assertEquals(5, e1.getX());
  }

  @Test
  public void testGetY() {
    assertEquals(10, e1.getY());
  }

  @Test
  public void testGetWidth() {
    assertEquals(1, e1.getWidth());
  }

  @Test
  public void testGetHeight() {
    assertEquals(2, e1.getHeight());
  }

  @Test
  public void testGetRed() {
    assertEquals(255, e1.getRed());
  }

  @Test
  public void testGetGreen() {
    assertEquals(126, e1.getGreen());
  }

  @Test
  public void testGetBlue() {
    assertEquals(0, e1.getBlue());
  }


  @Test
  public void testGetShapeName() {
    assertEquals("ellipse", e1.getShapeName());
  }


  @Test
  public void testEquals() {
    Shape rectWithSameE1Parameters = new Rectangle(5, 10, 1, 2, 255, 126, 0);
    Shape e1WithSlightlyDifferentValues =
        new Ellipse(6, 10, 1, 2, 254, 126, 0);
    Shape e1Copy = new Ellipse(5, 10, 1, 2, 255, 126, 0);
    assertEquals(false, e1.equals(rectWithSameE1Parameters));
    assertFalse(e1.equals(e1WithSlightlyDifferentValues));
    assertTrue(e1.equals(e1Copy));
  }


  @Test
  public void testGetShapeInfo() {
    assertEquals("  5  10   1   2 255 126   0", e1.getShapeInfo());
  }

  @Test
  public void testSet() {
    Shape eToSet = new Ellipse(5, 10, 1, 2, 255, 126, 0);
    eToSet.set(1, 2, 3, 4, 5, 6, 7);
    assertEquals("  1   2   3   4   5   6   7", eToSet.getShapeInfo());
  }


  @Test
  public void testCopy() {
    assertTrue(e1.equals(e1.copy()));
  }

  // new tests to make:

  @Test
  public void testGetMinXForSVG() {
    Shape e1 = new Ellipse(5, 10, 1, 2, 255, 126, 0);
    assertEquals(5, e1.getMinXForSVG());
  }

  @Test
  public void testGetMinYForSVG() {
    Shape e1 = new Ellipse(5, 10, 1, 2, 255, 126, 0);
    assertEquals(9, e1.getMinYForSVG());
  }

  @Test
  public void testGetMaxXForSVG() {
    Shape e1 = new Ellipse(5, 10, 1, 2, 255, 126, 0);
    assertEquals(5, e1.getMaxXForSVG());
  }

  @Test
  public void testGetMaxYForSVG() {
    Shape e1 = new Ellipse(5, 10, 1, 2, 255, 126, 0);
    assertEquals(11, e1.getMaxYForSVG());
  }


}