import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cs3500.animator.model.animation.Animation;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.motion.Motion;
import cs3500.animator.model.scene.EditableScene;
import cs3500.animator.model.scene.IEditableScene;
import cs3500.animator.model.scene.IScene;
import cs3500.animator.model.scene.Scene.Builder;

import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.util.AnimationBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

/**
 * This class contains tests for the Editable Scene class.
 */
public class EditableSceneTest {


  @Test
  public void testAddAnimation() {
    IEditableScene scene = new EditableScene();
    IAnimation a = new Animation(new Rectangle(1, 1, 1,
        1, 1, 1, 1), "rect", new ArrayList<>());
    assertEquals(0, scene.getNewAnimations().size());
    scene.addAnimation(a);
    assertEquals(1, scene.getNewAnimations().size());
  }


  @Test
  public void testAddAnimationAlreadyThere() {
    try {
      IEditableScene scene = new EditableScene();
      AnimationBuilder<IScene> builder = new Builder();
      builder.declareShape("Gonzo", "ellipse").addMotion("Gonzo", 0, 0,
          0, 0, 0, 255, 255, 255, 30, 250, 250, 100,
          100, 0, 0, 0);
      IScene delegate = builder.build();
      scene.setDelegate(delegate);
      IAnimation a = new Animation(new Rectangle(1, 1, 1,
          1, 1, 1, 1), "Gonzo", new ArrayList<>());
      scene.addAnimation(a);
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Animation with given name already exists.", e.getMessage());
    }

  }


  @Test
  public void testRemoveAnimation() {
    IEditableScene scene = new EditableScene();
    IAnimation a = new Animation(new Rectangle(1, 1, 1,
        1, 1, 1, 1), "rect", new ArrayList<>());
    assertEquals(0, scene.getNewAnimations().size());
    scene.addAnimation(a);
    assertEquals(1, scene.getNewAnimations().size());
    scene.removeAnimation("rect");
    assertEquals(0, scene.getNewAnimations().size());
    scene.removeAnimation("notHere");
    assertEquals(0, scene.getNewAnimations().size());

  }


  @Test
  public void testGetShapesAt() {
    IEditableScene scene = new EditableScene();
    IMotion m = new Motion(1, 10, new Rectangle(100, 100, 100,
        100, 100, 100, 100));
    IAnimation a = new Animation(new Rectangle(1, 1, 1,
        1, 1, 1, 1), "rect", new ArrayList<IMotion>(Arrays.asList(m)));
    IMotion b = new Motion(10, 20, new Rectangle(100, 100, 100,
        100, 100, 200, 200));
    IAnimation a2 = new Animation(new Rectangle(1, 1, 1,
        1, 1, 1, 1), "rect2", new ArrayList<>(Arrays.asList(b)));
    scene.addAnimation(a);
    scene.addAnimation(a2);
    assertEquals(2, scene.getShapesAt(10).size());
  }


  @Test
  public void testAddKeyFrameNotHere() {
    try {
      IEditableScene scene = new EditableScene();
      IMotion m = new Motion(1, 10, new Rectangle(100, 100, 100,
          100, 100, 100, 100));
      IAnimation a = new Animation(new Rectangle(1, 1, 1,
          1, 1, 1, 1), "rect", new ArrayList<IMotion>(Arrays.asList(m)));
      IMotion b = new Motion(10, 20, new Rectangle(100, 100, 100,
          100, 100, 200, 200));
      IAnimation a2 = new Animation(new Rectangle(1, 1, 1,
          1, 1, 1, 1), "rect2", new ArrayList<>(Arrays.asList(b)));
      scene.addAnimation(a);
      scene.addAnimation(a2);
      scene.addKeyframe("notHere", 10);
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("No animation with name notHere exists.", e.getMessage());
    }
  }


  @Test
  public void testRemoveKeyFrameNotHere() {
    try {
      IEditableScene scene = new EditableScene();
      IMotion m = new Motion(1, 10, new Rectangle(100, 100, 100,
          100, 100, 100, 100));
      IAnimation a = new Animation(new Rectangle(1, 1, 1,
          1, 1, 1, 1), "rect", new ArrayList<IMotion>(Arrays.asList(m)));
      IMotion b = new Motion(10, 20, new Rectangle(100, 100, 100,
          100, 100, 200, 200));
      IAnimation a2 = new Animation(new Rectangle(1, 1, 1,
          1, 1, 1, 1), "rect2", new ArrayList<>(Arrays.asList(b)));
      scene.addAnimation(a);
      scene.addAnimation(a2);
      scene.removeKeyframe("notHere", 10);
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("No animation with name notHere exists.", e.getMessage());
    }
  }


  @Test
  public void testEditKeyFrameNotHere() {
    try {
      IEditableScene scene = new EditableScene();
      IMotion m = new Motion(1, 10, new Rectangle(100, 100, 100,
          100, 100, 100, 100));
      IAnimation a = new Animation(new Rectangle(1, 1, 1,
          1, 1, 1, 1), "rect", new ArrayList<IMotion>(Arrays.asList(m)));
      IMotion b = new Motion(10, 20, new Rectangle(100, 100, 100,
          100, 100, 200, 200));
      IAnimation a2 = new Animation(new Rectangle(1, 1, 1,
          1, 1, 1, 1), "rect2", new ArrayList<>(Arrays.asList(b)));
      scene.addAnimation(a);
      scene.addAnimation(a2);
      scene.editKeyframe("notHere", 10, new Rectangle(0, 0, 0, 0,
          0, 0, 0));
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("No animation with name notHere exists.", e.getMessage());
    }
  }


  @Test
  public void testGetNewAnimations() {
    IEditableScene scene = new EditableScene();
    assertTrue(scene.getNewAnimations().isEmpty());
  }

  @Test
  public void testGetNewAnimationsMap() {
    IEditableScene scene = new EditableScene();
    assertTrue(scene.getNewAnimationsMap().isEmpty());
  }

  @Test
  public void testGetAndSetDelegate() {
    IEditableScene scene = new EditableScene();
    assertEquals(null, scene.getDelegate());
    AnimationBuilder<IScene> builder = new Builder();
    builder.declareShape("Gonzo", "ellipse").addMotion("Gonzo", 0, 0,
        0, 0, 0, 255, 255, 255, 30, 250, 250, 100,
        100, 0, 0, 0);
    IScene delegate = builder.build();
    scene.setDelegate(delegate);
    assertEquals(delegate, scene.getDelegate());
  }
}
