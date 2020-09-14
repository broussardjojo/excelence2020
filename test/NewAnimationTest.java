import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.animation.Animation;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.animation.INewAnimation;
import cs3500.animator.model.animation.NewAnimation;
import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.motion.Motion;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Shape;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

/**
 * This class exists to test methods of the NewAnimation class.
 */
public class NewAnimationTest {

  @Test
  public void testGetShapeAt() {

    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200, 200,
        50, 50, 126, 126, 126));

    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2)));

    IAnimation animation = new NewAnimation(rightThenDownRect);
    assertTrue(animation.getShapeAt(0).equals(
        new Rectangle(100, 100, 50, 50, 126, 126, 126)));
    assertTrue(animation.getShapeAt(5).equals(
        new Rectangle(150, 100, 50, 50, 126, 126, 126)));
    assertTrue(animation.getShapeAt(70).equals(
        new Rectangle(200, 200, 50, 50, 126, 126, 126)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetShapeAtBeforeInitialized() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(6, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200, 200,
        50, 50, 126, 126, 126));

    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2)));

    IAnimation animation = new NewAnimation(rightThenDownRect);

    Shape broken = animation.getShapeAt(0);
  }

  @Test
  public void testToString() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(6, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200, 200,
        50, 50, 126, 126, 126));

    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2)));

    IAnimation animation = new NewAnimation(rightThenDownRect);
    assertEquals("shape rect rectangle\n"
        + "keyframe  rect     6 100 100  50  50 126 126 126 \n"
        + "keyframe  rect    10 200 100  50  50 126 126 126 \n"
        + "keyframe  rect    25 200 200  50  50 126 126 126 \n"
        + "keyframe  rect    60 200 200  50  50 126 126 126 \n", animation.toString());
  }

  @Test
  public void testAddKeyFrame() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(6, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 255, 255, 0));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2)));
    INewAnimation animation = new NewAnimation(rightThenDownRect);

    animation.addKeyFrame(50);
    assertEquals("shape rect rectangle\n"
            + "keyframe  rect     6 100 100  50  50 126 126 126 \n"
            + "keyframe  rect    10 200 100  50  50 126 126 126 \n"
            + "keyframe  rect    25 200 200  50  50 126 126 126 \n"
            + "keyframe  rect    50 200 200  50  50 218 218  36 \n"
            + "keyframe  rect    60 200 200  50  50 255 255   0 \n",
        animation.toString());

    animation.addKeyFrame(0);
    assertEquals("shape rect rectangle\n"
            + "keyframe  rect     0 100 100  50  50 126 126 126 \n"
            + "keyframe  rect     6 100 100  50  50 126 126 126 \n"
            + "keyframe  rect    10 200 100  50  50 126 126 126 \n"
            + "keyframe  rect    25 200 200  50  50 126 126 126 \n"
            + "keyframe  rect    50 200 200  50  50 218 218  36 \n"
            + "keyframe  rect    60 200 200  50  50 255 255   0 \n",
        animation.toString());

    animation.addKeyFrame(70);
    assertEquals("shape rect rectangle\n"
            + "keyframe  rect     0 100 100  50  50 126 126 126 \n"
            + "keyframe  rect     6 100 100  50  50 126 126 126 \n"
            + "keyframe  rect    10 200 100  50  50 126 126 126 \n"
            + "keyframe  rect    25 200 200  50  50 126 126 126 \n"
            + "keyframe  rect    50 200 200  50  50 218 218  36 \n"
            + "keyframe  rect    60 200 200  50  50 255 255   0 \n"
            + "keyframe  rect    70 200 200  50  50 255 255   0 \n",
        animation.toString());
  }

  @Test
  public void testRemoveKeyFrame() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(6, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 255, 255, 0));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2)));
    INewAnimation animation = new NewAnimation(rightThenDownRect);

    animation.removeKeyFrame(25);
    assertEquals("shape rect rectangle\n"
            + "keyframe  rect     6 100 100  50  50 126 126 126 \n"
            + "keyframe  rect    10 200 100  50  50 126 126 126 \n"
            + "keyframe  rect    60 200 200  50  50 255 255   0 \n",
        animation.toString());

    animation.removeKeyFrame(25);
    assertEquals("shape rect rectangle\n"
            + "keyframe  rect     6 100 100  50  50 126 126 126 \n"
            + "keyframe  rect    10 200 100  50  50 126 126 126 \n"
            + "keyframe  rect    60 200 200  50  50 255 255   0 \n",
        animation.toString());

    animation.removeKeyFrame(7);
    assertEquals("shape rect rectangle\n"
            + "keyframe  rect     6 100 100  50  50 126 126 126 \n"
            + "keyframe  rect    10 200 100  50  50 126 126 126 \n"
            + "keyframe  rect    60 200 200  50  50 255 255   0 \n",
        animation.toString());
  }


  @Test(expected = IllegalArgumentException.class)
  public void addKeyFrameThatAlreadyExists() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(6, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 255, 255, 0));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2)));
    INewAnimation animation = new NewAnimation(rightThenDownRect);

    animation.addKeyFrame(25);
  }

  @Test
  public void testEditKeyFrame() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(6, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 255, 255, 0));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2)));
    INewAnimation animation = new NewAnimation(rightThenDownRect);

    animation.editKeyFrame(6, new Rectangle(25, 25, 100, 100,
        255, 255, 0));
    assertEquals("shape rect rectangle\n"
            + "keyframe  rect     6 100 100  50  50 126 126 126 \n"
            + "keyframe  rect    10 200 100  50  50 126 126 126 \n"
            + "keyframe  rect    25 200 200  50  50 126 126 126 \n"
            + "keyframe  rect    60 200 200  50  50 255 255   0 \n",
        animation.toString());
    animation.addKeyFrame(0);
    assertEquals("shape rect rectangle\n"
            + "keyframe  rect     0 100 100  50  50 126 126 126 \n"
            + "keyframe  rect     6 100 100  50  50 126 126 126 \n"
            + "keyframe  rect    10 200 100  50  50 126 126 126 \n"
            + "keyframe  rect    25 200 200  50  50 126 126 126 \n"
            + "keyframe  rect    60 200 200  50  50 255 255   0 \n",
        animation.toString());
    animation.editKeyFrame(0, new Rectangle(100, 100, 100, 100,
        255, 255, 255));
    assertEquals("shape rect rectangle\n"
            + "keyframe  rect     0 100 100  50  50 126 126 126 \n"
            + "keyframe  rect     6 100 100  50  50 126 126 126 \n"
            + "keyframe  rect    10 200 100  50  50 126 126 126 \n"
            + "keyframe  rect    25 200 200  50  50 126 126 126 \n"
            + "keyframe  rect    60 200 200  50  50 255 255   0 \n",
        animation.toString());
  }


  @Test
  public void testGetMotions() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200, 200,
        50, 50, 126, 126, 126));

    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2)));

    IAnimation animation = new NewAnimation(rightThenDownRect);
    assertEquals(3, animation.getMotions().size());
  }


  @Test
  public void testGetName() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(0, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200, 200,
        50, 50, 126, 126, 126));

    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2)));

    IAnimation animation = new NewAnimation(rightThenDownRect);
    assertEquals("rect", animation.getName());
  }


  @Test
  public void testRemoveKeyframeEndingAt() {
    Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
    IMotion moveRightRect = new Motion(6, 10, new Rectangle(200, 100,
        50, 50, 126, 126, 126));
    IMotion moveDownRect2 = new Motion(20, 25, new Rectangle(200, 200,
        50, 50, 126, 126, 126));
    IMotion moveDownRect = new Motion(30, 60, new Rectangle(200, 200,
        50, 50, 255, 255, 0));
    IAnimation rightThenDownRect = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRightRect, moveDownRect, moveDownRect2)));
    INewAnimation animation = new NewAnimation(rightThenDownRect);
    animation.removeKeyframeEndingAt(25);
    assertEquals("shape rect rectangle\n"
        + "keyframe  rect     6 100 100  50  50 126 126 126 \n"
        + "keyframe  rect    25 200 200  50  50 126 126 126 \n"
        + "keyframe  rect    60 200 200  50  50 255 255   0 \n", animation.toString());
  }
}
