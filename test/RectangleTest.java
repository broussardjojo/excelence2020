import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Shape;
import org.junit.Test;


/**
 * This class contains tests for the Rectangle class, including tests for methods from the
 * AbstractShape class.
 */
public class RectangleTest {

  Shape r1 = new Rectangle(5, 10, 1, 2, 255, 126, 0);


  @Test
  public void InvalidConstructorNegativeDimensions() {
    try {
      Shape rInvalidWidth = new
          Rectangle(5, 10, -1, 2, 255, 126, 0);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Dimensions cannot be negative", e.getMessage());
    }
    try {
      Shape rInvalidHeight = new
          Rectangle(5, 10, 1, -2, 255, 126, 0);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Dimensions cannot be negative", e.getMessage());
    }
    try {
      Shape rInvalidWandH = new
          Rectangle(5, 10, -1, -2, 255, 126, 0);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Dimensions cannot be negative", e.getMessage());
    }
  }


  @Test
  public void InvalidConstructorInvalidColor() {
    try {
      Shape rInvalidR = new Rectangle(5, 10, 1, 2, 256, 126, 0);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }
    try {
      Shape rInvalidG = new Rectangle(5, 10, 1, 2, 255, 256, 0);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }
    try {
      Shape rInvalidB = new Rectangle(5, 10, 1, 2, 255, 255, 256);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }

    try {
      Shape rInvalidRNegative =
          new Rectangle(5, 10, 1, 2, -1, 255, 255);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }

    try {
      Shape rInvalidGNegative =
          new Rectangle(5, 10, 1, 2, 1, -1, 255);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }

    try {
      Shape rInvalidBNegative =
          new Rectangle(5, 10, 1, 2, -1, 255, -1);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }

    try {
      Shape rInvalidAllNegative =
          new Rectangle(5, 10, 1, 2, -1, -255, -255);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }

    try {
      Shape rInvalidAllInvalid =
          new Rectangle(5, 10, 1, 2, 256, 256, 256);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }

  }


  @Test
  public void testGetX() {
    assertEquals(5, r1.getX());
  }

  @Test
  public void testGetY() {
    assertEquals(10, r1.getY());
  }

  @Test
  public void testGetWidth() {
    assertEquals(1, r1.getWidth());
  }

  @Test
  public void testGetHeight() {
    assertEquals(2, r1.getHeight());
  }

  @Test
  public void testGetRed() {
    assertEquals(255, r1.getRed());
  }

  @Test
  public void testGetGreen() {
    assertEquals(126, r1.getGreen());
  }

  @Test
  public void testGetBlue() {
    assertEquals(0, r1.getBlue());
  }


  @Test
  public void testGetShapeName() {
    assertEquals("rectangle", r1.getShapeName());
  }


  @Test
  public void testEquals() {
    Shape ellipseWithSameR1Parameters = new
        Ellipse(5, 10, 1, 2, 255, 126, 0);
    Shape r1WithSlightlyDifferentValues =
        new Rectangle(6, 10, 1, 2, 254, 126, 0);
    Shape r1Copy = new Rectangle(5, 10, 1, 2, 255, 126, 0);
    assertEquals(false, r1.equals(ellipseWithSameR1Parameters));
    assertFalse(r1.equals(r1WithSlightlyDifferentValues));
    assertTrue(r1.equals(r1Copy));
  }


  @Test
  public void testGetShapeInfo() {
    assertEquals("  5  10   1   2 255 126   0", r1.getShapeInfo());
  }


  @Test
  public void testCopy() {
    assertTrue(r1.equals(r1.copy()));
  }


  @Test
  public void testSet() {
    Shape rectToSet = new Rectangle(5, 10, 1, 2, 255, 126, 0);
    rectToSet.set(1, 2, 3, 4, 5, 6, 7);
    assertEquals("  1   2   3   4   5   6   7", rectToSet.getShapeInfo());
  }


  @Test
  public void testGetMinXForSVG() {
    Shape r1 = new Rectangle(5, 10, 1, 2, 255, 126, 0);
    assertEquals(5, r1.getMinXForSVG());
  }

  @Test
  public void testGetMinYForSVG() {
    Shape r1 = new Rectangle(5, 10, 1, 2, 255, 126, 0);
    assertEquals(10, r1.getMinYForSVG());
  }

  @Test
  public void testGetMaxXForSVG() {
    Shape r1 = new Rectangle(5, 10, 1, 2, 255, 126, 0);
    assertEquals(6, r1.getMaxXForSVG());
  }

  @Test
  public void testGetMaxYForSVG() {
    Shape r1 = new Rectangle(5, 10, 1, 2, 255, 126, 0);
    assertEquals(12, r1.getMaxYForSVG());
  }


}