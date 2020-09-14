import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.animation.Animation;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.motion.Motion;
import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Shape;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;


/**
 * This class contains tests for Animation, including tests for all the methods in the abstract
 * class and the concrete class.
 */
public class AnimationTest {


  // makes sure that the constructor sorts the list of motions
  @Test
  public void testConstructorSortedList() {

    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200, 200,
        50, 50, 126, 126, 126));

    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2)));

    //makes sure that the list of IMotions has been sorted according to startTick
    assertEquals(new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect2, moveDownRect)),
        rightThenDownRect.getMotions());
  }


  @Test
  public void testInvalidConstructorOverlappingMotionsRectangle() {
    try {
      Shape rect = new Rectangle(100, 100, 50, 50, 126, 126,
          126);
      IMotion moveRight = new Motion(0, 100, new Rectangle(200, 100,
          50, 50, 126, 126, 126));
      IMotion moveDown = new Motion(50, 150, new Rectangle(200, 200,
          50, 50, 126, 126, 126));
      IAnimation rightThenDown = new Animation(rect, "rect",
          new ArrayList<IMotion>(Arrays.asList(moveRight, moveDown)));
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Motions cannot overlap!", e.getMessage());
    }
  }


  @Test
  public void testInvalidConstructorOverlappingMotionsEllipse() {
    try {
      Shape e = new Ellipse(100, 100, 50, 50, 126, 126, 126);
      IMotion moveRight = new Motion(0, 100, new Ellipse(200, 100, 50,
          50, 126, 126, 126));
      IMotion moveDown = new Motion(50, 150, new Ellipse(200, 200, 50,
          50, 126, 126, 126));
      IAnimation rightThenDown = new Animation(e, "e",
          new ArrayList<IMotion>(Arrays.asList(moveRight, moveDown)));
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Motions cannot overlap!", e.getMessage());
    }
  }


  @Test
  public void testInvalidConstructorDifferentShapes() {
    try {
      Shape e = new Ellipse(100, 100, 50, 50, 126, 126, 126);
      Shape rect = new Rectangle(100, 100, 50, 50, 126, 126,
          126);
      IMotion moveRightEllipse = new Motion(20, 100, new Ellipse(200, 100,
          50, 50, 126, 126, 126));
      IMotion moveDownRect = new Motion(0, 10, new Rectangle(200, 200,
          50, 50, 126, 126, 126));
      IAnimation rightThenDown = new Animation(e, "e",
          new ArrayList<IMotion>(Arrays.asList(moveRightEllipse, moveDownRect)));
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("One or more motions was unable to be applied to given shape.",
          e.getMessage());
    }
  }

  @Test
  public void testOverlappingMotionsButDifferentShapes() {
    try {
      Shape e = new Ellipse(100, 100, 50, 50, 126, 126, 126);
      Shape rect = new Rectangle(100, 100, 50, 50, 126, 126,
          126);
      IMotion moveRight = new Motion(0, 100, new Ellipse(200, 100, 50,
          50, 126, 126, 126));
      IMotion moveDown = new Motion(50, 150, new Rectangle(200, 200,
          50, 50, 126, 126, 126));
      IAnimation rightThenDown = new Animation(e, "e",
          new ArrayList<IMotion>(Arrays.asList(moveRight, moveDown)));
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("One or more motions was unable to be applied to given shape.",
          e.getMessage());
    }
  }


  @Test
  public void testCopy() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRight = new Motion(0, 100, new Rectangle(200, 100, 50,
        50, 126, 126, 126));
    IMotion moveDown = new Motion(100, 150, new Rectangle(200, 200, 50,
        50, 126, 126, 126));
    IAnimation rightThenDown = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRight, moveDown)));
    assertEquals(new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRight, moveDown))), rightThenDown.copy());

    Shape e = new Ellipse(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightE = new Motion(0, 100, new Ellipse(200, 100, 50,
        50, 126, 126, 126));
    IMotion moveDownE = new Motion(100, 150, new Ellipse(200, 200, 50,
        50, 126, 126, 126));
    IAnimation rightThenDownE = new Animation(e, "ellipse",
        new ArrayList<IMotion>(Arrays.asList(moveRightE, moveDownE)));
    assertEquals(new Animation(e, "ellipse",
        new ArrayList<IMotion>(Arrays.asList(moveRightE, moveDownE))), rightThenDownE.copy());
  }


  @Test
  public void testToStringRectangleAnimationMoveRightThenDown() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRight = new Motion(0, 100, new Rectangle(200, 100, 50,
        50, 126, 126, 126));
    IMotion moveDown = new Motion(100, 150, new Rectangle(200, 200, 50,
        50, 126, 126, 126));
    IAnimation rightThenDown = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRight, moveDown)));
    assertEquals("shape rect rectangle\n"
            + "motion  rect     0 100 100  50  50 126 126 126   100 200 100  50  50 126 126 126 \n"
            + "motion  rect   100 200 100  50  50 126 126 126   150 200 200  50  50 126 126 126 \n",
        rightThenDown.toString());
  }


  @Test
  public void testToStringEllipseAnimationMoveRightThenDown() {
    Shape e = new Ellipse(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRight = new Motion(0, 100, new Ellipse(200, 100, 50,
        50, 126, 126, 126));
    IMotion moveDown = new Motion(100, 150, new Ellipse(200, 200, 50,
        50, 126, 126, 126));
    IAnimation rightThenDown = new Animation(e, "ellipse",
        new ArrayList<IMotion>(Arrays.asList(moveRight, moveDown)));
    assertEquals("shape ellipse ellipse\n"
            + "motion ellipse     0 100 100  50  50 126 126 126   "
            + "100 200 100  50  50 126 126 126 \n"
            + "motion ellipse   100 200 100  50  50 126 126 126   "
            + "150 200 200  50  50 126 126 126 \n",
        rightThenDown.toString());
  }


  @Test
  public void testAnimationRectangleChangeDimensions() {
    Shape r = new Rectangle(100, 100, 100, 100, 100, 100, 100);
    IMotion increaseWidth = new Motion(0, 40, new Rectangle(100, 100,
        150, 100, 100, 100, 100));
    IMotion increaseHeight = new Motion(40, 80, new Rectangle(100, 100,
        150, 200, 100, 100, 100));
    IAnimation changeDimensionsRect = new Animation(r, "rectangle",
        new ArrayList<IMotion>(Arrays.asList(increaseWidth, increaseHeight)));
    assertEquals("shape rectangle rectangle\n"
            + "motion rectangle     0 100 100 100 100 100 100 100    "
            + "40 100 100 150 100 100 100 100 \n"
            + "motion rectangle    40 100 100 150 100 100 100 100    "
            + "80 100 100 150 200 100 100 100 \n",
        changeDimensionsRect.toString());
  }

  @Test
  public void testAnimationRectangleChangeColor() {
    Shape r = new Rectangle(100, 100, 100, 100, 100, 100, 100);
    IMotion changeRed = new Motion(0, 10, new Rectangle(100, 100,
        100, 100, 200, 100, 100));
    IMotion changeGreen = new Motion(10, 20, new Rectangle(100, 100,
        100, 100, 200, 200, 100));
    IMotion changeBlue = new Motion(20, 30, new Rectangle(100, 100,
        100, 100, 200, 200, 200));
    IAnimation changeColor = new Animation(r, "rectangle",
        new ArrayList<IMotion>(Arrays.asList(changeBlue, changeGreen, changeRed)));
    assertEquals("shape rectangle rectangle\n"
        + "motion rectangle     0 100 100 100 100 100 100 100    "
        + "10 100 100 100 100 200 100 100 \n"
        + "motion rectangle    10 100 100 100 100 200 100 100    "
        + "20 100 100 100 100 200 200 100 \n"
        + "motion rectangle    20 100 100 100 100 200 200 100    "
        + "30 100 100 100 100 200 200 200 \n", changeColor.toString());

  }


  @Test
  public void testGetShape() {
    Shape e = new Ellipse(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRight = new Motion(0, 100, new Ellipse(200, 100, 50,
        50, 126, 126, 126));
    IMotion moveDown = new Motion(100, 150, new Ellipse(200, 200, 50,
        50, 126, 126, 126));
    IAnimation rightThenDown = new Animation(e, "ellipse",
        new ArrayList<IMotion>(Arrays.asList(moveRight, moveDown)));
    assertEquals(new Ellipse(100, 100, 50, 50, 126, 126, 126),
        rightThenDown.getShape());

    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 100, new Rectangle(200, 100, 50,
        50, 126, 126, 126));
    IMotion moveDownRect = new Motion(100, 150, new Rectangle(200, 200, 50,
        50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect)));
    assertEquals(new Rectangle(100, 100, 50, 50, 126, 126, 126),
        rightThenDownRect.getShape());
  }


  @Test
  public void testGetMotions() {
    Shape e = new Ellipse(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRight = new Motion(0, 100, new Ellipse(200, 100, 50,
        50, 126, 126, 126));
    IMotion moveDown = new Motion(100, 150, new Ellipse(200, 200, 50,
        50, 126, 126, 126));
    IAnimation rightThenDown = new Animation(e, "ellipse",
        new ArrayList<IMotion>(Arrays.asList(moveRight, moveDown)));
    assertEquals(new ArrayList<IMotion>(Arrays.asList(moveRight, moveDown)),
        rightThenDown.getMotions());

    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 100, new Rectangle(200, 100, 50,
        50, 126, 126, 126));
    IMotion moveDownRect = new Motion(100, 150, new Rectangle(200, 200, 50,
        50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect)));

    List<IMotion> goose = new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect));
    int motionCount = rightThenDownRect.getMotions().size();
    for (int lcv = 0; lcv < motionCount; lcv++) {
      assertTrue(goose.get(lcv).equals(rightThenDownRect.getMotions().get(lcv)));
    }
  }


  @Test
  public void testGetName() {
    Shape e = new Ellipse(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRight = new Motion(0, 100, new Ellipse(200, 100, 50,
        50, 126, 126, 126));
    IMotion moveDown = new Motion(100, 150, new Ellipse(200, 200, 50,
        50, 126, 126, 126));
    IAnimation rightThenDown = new Animation(e, "ellipse",
        new ArrayList<IMotion>(Arrays.asList(moveRight, moveDown)));
    assertEquals("ellipse", rightThenDown.getName());

    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 100, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(100, 150, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect)));
    assertEquals("rect", rightThenDownRect.getName());
  }


  @Test
  public void testAddMotionInBetweenValues() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect)));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    rightThenDownRect.addMotion(moveDownRect2);
    assertEquals(new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect2, moveDownRect)),
        rightThenDownRect.getMotions());
  }

  @Test
  public void testAddMotionInBetweenValuesAndTouching() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect)));
    IMotion moveDownRect2 = new Motion(10, 30, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    rightThenDownRect.addMotion(moveDownRect2);
    assertTrue(rightThenDownRect.getMotions().contains(moveDownRect2));
  }


  @Test
  public void testAddMotionToFront() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveDownRect)));

    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));

    rightThenDownRect.addMotion(moveRightRect);
    assertEquals(new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect)),
        rightThenDownRect.getMotions());
  }


  @Test
  public void testAddMotionToBack() {

    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));

    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect)));

    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 126, 126, 126));

    rightThenDownRect.addMotion(moveDownRect);
    assertEquals(new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect)),
        rightThenDownRect.getMotions());
  }


  @Test
  public void testInvalidAddMotionDifferentShapes() {
    try {
      Shape rect = new Rectangle(100, 100, 50, 50, 126, 126,
          126);
      IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
          50, 50, 126, 126, 126));
      IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
          50, 50, 126, 126, 126));
      IAnimation motionInvalidDiffShapes = new Animation(rect, "rect",
          new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect)));

      IMotion moveDownEllipse = new Motion(10, 25, new Ellipse(200, 200,
          50, 50, 126, 126, 126));
      motionInvalidDiffShapes.addMotion(moveDownEllipse);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Motion is inapplicable to the shape in this animation; "
              + "Trying to turn rectangle to ellipse",
          e.getMessage());
    }
  }


  @Test
  public void testRemoveMotionThatIsNotInList() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200,
        200, 50, 50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect)));

    rightThenDownRect.removeMotion(moveDownRect2);
    // method does not do anything if the motion to be removed is not in list
    assertEquals(Arrays.asList(moveRightRect, moveDownRect), rightThenDownRect.getMotions());
  }

  @Test
  public void testEquals() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect)));

    Shape e = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightE = new Motion(0, 10, new Rectangle(200, 100, 50,
        50, 126, 126, 126));
    IMotion moveDownE = new Motion(30, 60, new Rectangle(200, 200, 50,
        50, 126, 126, 126));
    IAnimation rightThenDownE = new Animation(e, "ellipse",
        new ArrayList<IMotion>(Arrays.asList(moveRightE, moveDownE)));

    assertFalse(rightThenDownE.equals(rightThenDownRect));
    assertTrue(rightThenDownE.equals(rightThenDownE.copy()));
    assertFalse(rightThenDownE.equals(moveDownE));
  }


  @Test
  public void testGetShapeAt() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 126, 126, 126));

    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect)));

    assertTrue(rightThenDownRect.getShapeAt(20).equals(
        new Rectangle(200, 100, 50, 50, 126, 126, 126)));
    assertTrue(rightThenDownRect.getShapeAt(45).equals(
        new Rectangle(200, 150, 50, 50, 126, 126, 126)));
    assertTrue(rightThenDownRect.getShapeAt(100).equals(
        new Rectangle(200, 200, 50, 50, 126, 126, 126)));
  }


  @Test
  public void testEmptyAnimation() {
    Shape rectEmptyMotions = new Rectangle(100, 100, 50, 50, 126,
        126, 126);
    IAnimation emptyAnimation = new Animation(rectEmptyMotions, "rectangleEmpty",
        new ArrayList<IMotion>());
    assertEquals(new ArrayList<IMotion>(), emptyAnimation.getMotions());
    assertEquals(100, emptyAnimation.minX());
    assertEquals(100, emptyAnimation.minY());
    assertEquals(150, emptyAnimation.maxX());
    assertEquals(150, emptyAnimation.maxY());
  }


  @Test
  public void testEmptyToString() {
    Shape rectEmptyMotions = new Rectangle(100, 100, 50, 50, 126,
        126, 126);
    IAnimation emptyAnimation = new Animation(rectEmptyMotions, "rectangleEmpty",
        new ArrayList<IMotion>());
    assertEquals("shape rectangleEmpty rectangle\n", emptyAnimation.toString());
  }


  @Test
  public void testEmptyToSVGString() {
    Shape rectEmptyMotions = new Rectangle(100, 100, 50, 50, 126,
        126, 126);
    IAnimation emptyAnimation = new Animation(rectEmptyMotions, "rectangleEmpty",
        new ArrayList<IMotion>());
    assertEquals("<rect id=\"rectangleEmpty\" x=\"100\" y=\"100\" width=\"50\""
            + " height=\"50\" fill=\"rgb(126,126,126)\" visibility=\"visible\" >\n"
            + "</rect> \n\n",
        emptyAnimation.toSVGString(1));
  }

  @Test
  public void testToSVGString() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200,
        200, 50, 50, 126, 126, 126));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200,
        200, 50, 50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2)));
    assertEquals("<rect id=\"rect\" x=\"100\" y=\"100\" width=\"50\" height=\"50\" "
        + "fill=\"rgb(126,126,126)\" visibility=\"visible\" >\n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"5001ms\" attributeName=\"x\" "
        + "from=\"100\" to=\"200\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"2501ms\" attributeName=\"y\" "
        + "from=\"100\" to=\"200\" fill=\"freeze\" /> \n"
        + "</rect> \n\n", rightThenDownRect.toSVGString(2));

  }


  @Test
  public void testGetKeyFrameEndingAt() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200,
        200, 50, 50, 126, 126, 126));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200,
        200, 50, 50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2)));
    assertEquals(moveDownRect2, rightThenDownRect.getKeyframeEndingAt(25));
    assertEquals(moveRightRect, rightThenDownRect.getKeyframeEndingAt(10));
  }

  @Test
  public void testGetKeyFrameEndingAtEdgeCases() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200,
        200, 50, 50, 126, 126, 126));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200,
        200, 50, 50, 126, 126, 126));
    IMotion inBetween = new Motion(20, 20, new Rectangle(100, 100,
        100, 100, 100, 100, 100));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2,
            inBetween)));
    assertEquals(inBetween, rightThenDownRect.getKeyframeEndingAt(20));
  }

  @Test
  public void testRemoveKeyFrameEndingAt() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200,
        200, 50, 50, 126, 126, 126));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200,
        200, 50, 50, 126, 126, 126));
    IMotion inBetween = new Motion(20, 20, new Rectangle(100, 100,
        100, 100, 100, 100, 100));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2,
            inBetween)));
    IAnimation rightThenDownRectRemoved = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2)));
    rightThenDownRect.removeKeyframeEndingAt(20);
    assertTrue(rightThenDownRect.getMotions().size() == 3);
    assertEquals("shape rect rectangle\n"
            + "motion  rect     0 100 100  50  50 126 126 126    10 200 100  50  50 126 126 126 \n"
            + "motion  rect    20 200 100  50  50 126 126 126    25 200 200  50  50 126 126 126 \n"
            + "motion  rect    30 200 200  50  50 126 126 126    60 200 200  50  50 126 126 126 \n",
        rightThenDownRect.toString());
  }

  @Test
  public void testEditKeyFrameEndingAt() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200,
        200, 50, 50, 126, 126, 126));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200,
        200, 50, 50, 126, 126, 126));
    IMotion inBetween = new Motion(20, 20, new Rectangle(100, 100,
        100, 100, 100, 100, 100));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2,
            inBetween)));
    rightThenDownRect.editKeyframeEndingAt(25,
        new Rectangle(300, 300, 300, 300, 9, 10, 11));
    assertEquals(new Rectangle(300, 300, 300, 300, 9, 10, 11),
        moveDownRect2.getEndShape());
    rightThenDownRect.editKeyframeEndingAt(20,
        new Rectangle(300, 300, 300, 300, 9, 10, 11));
    assertEquals(new Rectangle(300, 300, 300, 300, 9, 10, 11),
        inBetween.getEndShape());
  }


  @Test
  public void testSplitNegativeTick() {
    try {
      Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
      IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
          50, 50, 126, 126, 126));
      IMotion moveDownRect = new Motion(30, 60, new Rectangle(200,
          200, 50, 50, 126, 126, 126));
      IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200,
          200, 50, 50, 126, 126, 126));
      IMotion inBetween = new Motion(20, 20, new Rectangle(100, 100,
          100, 100, 100, 100, 100));
      IAnimation rightThenDownRect = new Animation(rect, "rect",
          new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2,
              inBetween)));
      rightThenDownRect.split(-1);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot split at negative tick", e.getMessage());
    }

  }


  @Test
  public void testSplitEmptyMotionList() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList()));
    rightThenDownRect.split(1);
    assertEquals(1, rightThenDownRect.getMotions().size());
    assertEquals("shape rect rectangle\n"
            + "motion  rect     1 100 100  50  50 126 126 126     1 100 100  50  50 126 126 126 \n",
        rightThenDownRect.toString());
  }


  @Test
  public void testSplitAtStart() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200,
        200, 50, 50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveDownRect)));
    rightThenDownRect.split(10);
    assertEquals(2, rightThenDownRect.getMotions().size());
    assertEquals("shape rect rectangle\n"
            + "motion  rect    10 100 100  50  50 126 126 126    10 100 100  50  50 126 126 126 \n"
            + "motion  rect    10 100 100  50  50 126 126 126    60 200 200  50  50 126 126 126 \n",
        rightThenDownRect.toString());
  }

  @Test
  public void testSplitAtEnd() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200,
        200, 50, 50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveDownRect)));
    rightThenDownRect.split(70);
    assertEquals(2, rightThenDownRect.getMotions().size());
    assertEquals("shape rect rectangle\n"
            + "motion  rect    30 100 100  50  50 126 126 126    60 200 200  50  50 126 126 126 \n"
            + "motion  rect    60 200 200  50  50 126 126 126    70 200 200  50  50 126 126 126 \n",
        rightThenDownRect.toString());
  }

  @Test
  public void testSplitAtExistingTick() {
    try {
      Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
      IMotion moveDownRect = new Motion(30, 60, new Rectangle(200,
          200, 50, 50, 126, 126, 126));
      IAnimation rightThenDownRect = new Animation(rect, "rect",
          new ArrayList<IMotion>(Arrays.asList(moveDownRect)));
      rightThenDownRect.split(30);
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Can't add at an existing tick bro", e.getMessage());
    }

    try {
      Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
      IMotion moveDownRect = new Motion(30, 60, new Rectangle(200,
          200, 50, 50, 126, 126, 126));
      IAnimation rightThenDownRect = new Animation(rect, "rect",
          new ArrayList<IMotion>(Arrays.asList(moveDownRect)));
      rightThenDownRect.split(60);
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Can't add at an existing tick bro (other side!)", e.getMessage());
    }

  }


  @Test
  public void testSplitInMiddleOfMotion() {
    Shape rect = new Rectangle(100, 100, 50, 50, 200, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200,
        200, 50, 50, 200, 126, 126));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200,
        200, 50, 50, 100, 126, 126));
    IMotion inBetween = new Motion(20, 20, new Rectangle(100, 100,
        100, 100, 100, 100, 100));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(
            Arrays.asList(moveRightRect, inBetween, moveDownRect2, moveDownRect)));
    rightThenDownRect.split(22);
    assertEquals(5, rightThenDownRect.getMotions().size());
    assertEquals("shape rect rectangle\n"
            + "motion  rect     0 100 100  50  50 200 126 126    10 200 100  50  50 126 126 126 \n"
            + "motion  rect    20 200 100  50  50 126 126 126    20 100 100 100 100 100 100 100 \n"
            + "motion  rect    20 100 100 100 100 100 100 100    22 140 140  80  80 100 110 110 \n"
            + "motion  rect    22 140 140  80  80 100 110 110    25 200 200  50  50 100 126 126 \n"
            + "motion  rect    30 200 200  50  50 100 126 126    60 200 200  50  50 200 126 126 \n",
        rightThenDownRect.toString());
  }

}
