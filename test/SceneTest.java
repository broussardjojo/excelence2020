import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.animator.model.animation.Animation;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.motion.Motion;
import cs3500.animator.model.scene.IScene;
import cs3500.animator.model.scene.Scene;
import cs3500.animator.model.scene.Scene.Builder;
import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Shape;
import cs3500.animator.util.AnimationBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

// NOTE: We keep old tests that pass with the old constructor here, since they still are applicable
// and show that our methods work. New tests are written with the builder.


/**
 * This class contains tests for the Scene class, including its abstracted methods.
 */
public class SceneTest {

  Shape rect = new Rectangle(100, 100, 50, 50, 126, 126, 126);
  IMotion moveRight = new Motion(0, 100, new Rectangle(200, 100, 50,
      50, 126, 126, 126));
  IMotion moveDown = new Motion(100, 150, new Rectangle(200, 200, 50,
      50, 126, 126, 126));
  IAnimation rightThenDown = new Animation(rect, "rect",
      new ArrayList<IMotion>(Arrays.asList(moveRight, moveDown)));


  Shape rect2 = new Rectangle(200, 200, 100, 100, 0, 0, 0);
  IMotion moveUp = new Motion(10, 30, new Rectangle(200, 100, 100,
      100, 0, 0, 0));
  IAnimation animationsForRect2 = new Animation(rect2, "rect2",
      new ArrayList<IMotion>(Arrays.asList(moveUp)));

  IAnimation animationsForRect2SameName = new Animation(rect2, "rect",
      new ArrayList<IMotion>(Arrays.asList(moveUp)));

  Shape ellipse = new Ellipse(100, 100, 50, 50, 126, 126, 126);
  IMotion moveRightE = new Motion(0, 100, new Ellipse(200, 100, 50,
      50, 126, 126, 126));
  IMotion moveDownE = new Motion(100, 150, new Ellipse(200, 200, 50,
      50, 126, 126, 126));
  IAnimation rightThenDownE = new Animation(ellipse, "ellipse",
      new ArrayList<IMotion>(Arrays.asList(moveRightE, moveDownE)));


  @Test
  public void testInvalidSceneConstructor() {
    try {
      IAnimation animation1 = new Animation(rect, "rect",
          new ArrayList<IMotion>(Arrays.asList(moveUp)));
      IAnimation animation2 = new Animation(rect, "rect",
          new ArrayList<IMotion>(Arrays.asList(moveUp)));
      Scene badScene = new Scene(new ArrayList<IAnimation>(Arrays.asList(animation1, animation2)));

      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Animation with name rect already exists.", e.getMessage());
    }
  }

  @Test
  public void getAnimations() {
    IScene rectAndEllipse =
        new Scene(new ArrayList<IAnimation>(Arrays.asList(rightThenDown, rightThenDownE)));
    IAnimation ellipseCopy = new Animation(ellipse, "ellipse",
        new ArrayList<IMotion>(Arrays.asList(moveRightE, moveDownE)));
    IAnimation rectCopy = new Animation(rect, "rect",
        new ArrayList<IMotion>(Arrays.asList(moveRight, moveDown)));

    assertEquals(new ArrayList<IAnimation>(Arrays.asList(rectCopy, ellipseCopy)),
        rectAndEllipse.getAnimations());
  }


  @Test
  public void testRemoveAnimation() {
    IScene rectAndEllipse =
        new Scene(new ArrayList<IAnimation>(Arrays.asList(rightThenDown, rightThenDownE)));
    rectAndEllipse.removeAnimation("ellipse");
    assertEquals(new ArrayList<IAnimation>(Arrays.asList(rightThenDown)),
        rectAndEllipse.getAnimations());

  }


  @Test
  public void testRemoveAnimationWithNothingToRemove() {
    IScene rectAndEllipse =
        new Scene(new ArrayList<IAnimation>(Arrays.asList(rightThenDown, rightThenDownE)));
    rectAndEllipse.removeAnimation("rect2");
    assertEquals(new ArrayList<IAnimation>(Arrays.asList(rightThenDown, rightThenDownE)),
        rectAndEllipse.getAnimations());
  }


  @Test
  public void getToString() {
    AnimationBuilder<IScene> builder = new Builder();
    builder.declareShape("Gonzo", "ellipse").addMotion("Gonzo", 0, 0,
        0, 0, 0, 0, 0, 0, 30, 250, 250, 100, 100,
        0, 0, 0);
    IScene test = builder.build();

    assertEquals("shape Gonzo ellipse\n"
            + "motion Gonzo     0   0   0   0   0 255 255 255     0   0   0   0   0   0   0   0 \n"
            + "motion Gonzo     0   0   0   0   0   0   0   0    30 250 250 100 100   0   0   0 \n",
        test.toString());

    IAnimation rect = new Animation(new Rectangle(2, 3, 4, 5, 6, 7,
        8),
        "rect", new ArrayList<>());
    builder.declareShape("rect", "rectangle").addMotion("rect", 1, 2,
        3, 4, 5, 6, 7, 8, 30, 250, 250, 100, 100, 0,
        0, 0);
    IScene test2 = builder.build();
    assertEquals("shape Gonzo ellipse\n"
            + "motion Gonzo     0   0   0   0   0 255 255 255     0   0   0   0   0   0   0   0 \n"
            + "motion Gonzo     0   0   0   0   0   0   0   0    30 250 250 100 100   0   0   0 \n"
            + "shape rect rectangle\n"
            + "motion  rect     1   0   0   0   0 255 255 255     1   2   3   4   5   6   7   8 \n"
            + "motion  rect     1   2   3   4   5   6   7   8    30 250 250 100 100   0   0   0 \n",
        test2.toString());

  }


  @Test
  public void testAddAnimations() {
    AnimationBuilder<IScene> builder = new Builder();
    builder.declareShape("Gonzo", "ellipse").addMotion("Gonzo", 0, 0,
        0, 0, 0, 255, 255, 255, 30, 250, 250, 100,
        100, 0, 0, 0);
    IScene test = builder.build();
    Shape gonzo = new Ellipse(0, 0, 0, 0, 255, 255, 255);
    IMotion gonzosMotion = new Motion(0, 30, new Ellipse(250, 250, 100,
        100, 0, 0, 0));
    IAnimation testAnimation = new Animation(gonzo, "Gonzo",
        new ArrayList<IMotion>(Arrays.asList(gonzosMotion)));
    assertEquals("shape Gonzo ellipse\n"
            + "motion Gonzo     0   0   0   0   0 255 255 255     0   0   0   0   0 255 255 255 \n"
            + "motion Gonzo     0   0   0   0   0 255 255 255    30 250 250 100 100   0   0   0 \n",
        test.toString());
  }


  @Test
  public void testInvalidAddAnimationsSameName() {
    try {
      AnimationBuilder<IScene> builder = new Builder();
      builder.declareShape("Gonzo", "ellipse").addMotion("Gonzo", 0, 0,
          0, 0, 0, 255, 255, 255, 30, 250, 250, 100,
          100, 0, 0, 0);

      Shape gonzo = new Ellipse(0, 0, 0, 0, 255, 255, 255);
      IMotion gonzosMotion = new Motion(0, 30, new Ellipse(250, 250,
          100, 100, 0, 0, 0));
      IAnimation testAnimation = new Animation(gonzo, "Gonzo",
          new ArrayList<IMotion>(Arrays.asList(gonzosMotion)));
      builder.declareShape("Gonzo", "rectangle").addMotion("Gonzo", 40, 1,
          1, 1, 1, 1, 1, 1, 60, 1, 1, 1, 1, 1, 1,
          1);
      IScene test = builder.build();
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Shape with given name Gonzo already exists", e.getMessage());
    }
  }

  @Test
  public void testToSVGString() {
    AnimationBuilder<IScene> builder = new Builder();
    builder.declareShape("Gonzo", "ellipse").addMotion("Gonzo", 0, 0,
        0, 0, 0, 0, 0, 0, 30, 250, 250, 100, 100, 0,
        0, 0);
    IScene test = builder.build();
    assertEquals("<svg viewBox=\"0 0 500 500\" version=\"1.1\"\n"
        + "     xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "\n"
        + "<ellipse id=\"Gonzo\" cx=\"0\" cy=\"0\" rx=\"0\" ry=\"0\" fill=\"rgb(255,255,255)\" "
        + "visibility=\"visible\" > \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"2ms\" attributeName=\"fill\" "
        + "from=\"rgb(255,255,255)\" to=\"rgb(0,0,0)\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"15000ms\" attributeName=\"cx\" "
        + "from=\"0\" to=\"250\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"15000ms\" attributeName=\"cy\" "
        + "from=\"0\" to=\"250\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"15000ms\" attributeName=\"rx\" "
        + "from=\"0\" to=\"50\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"15000ms\" attributeName=\"ry\" "
        + "from=\"0\" to=\"50\" fill=\"freeze\" /> \n"
        + "</ellipse>\n"
        + "\n"
        + "</svg>", test.toSVGString(500, 500, 2));
  }


  @Test
  public void testToSVGStringMultipleShapes() {
    AnimationBuilder<IScene> builder = new Builder();
    builder.declareShape("Gonzo", "ellipse").addMotion("Gonzo", 0, 0,
        0, 0, 0, 0, 0, 0, 30, 250, 250, 100, 100, 0,
        0, 0);
    builder.declareShape("Rect1", "rectangle").addMotion("Rect1", 0, 0,
        0, 0, 0, 0, 0, 0, 40, 40, 40, 40, 40, 40,
        40, 40);
    IScene test = builder.build();
    assertEquals("<svg viewBox=\"0 0 500 500\" version=\"1.1\"\n"
        + "     xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "\n"
        + "<ellipse id=\"Gonzo\" cx=\"0\" cy=\"0\" rx=\"0\" ry=\"0\" fill=\"rgb(255,255,255)\" "
        + "visibility=\"visible\" > \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"2ms\" attributeName=\"fill\" "
        + "from=\"rgb(255,255,255)\" to=\"rgb(0,0,0)\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"15000ms\" attributeName=\"cx\" "
        + "from=\"0\" to=\"250\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"15000ms\" attributeName=\"cy\" "
        + "from=\"0\" to=\"250\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"15000ms\" attributeName=\"rx\" "
        + "from=\"0\" to=\"50\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"15000ms\" attributeName=\"ry\" "
        + "from=\"0\" to=\"50\" fill=\"freeze\" /> \n"
        + "</ellipse>\n"
        + "\n"
        + "<rect id=\"Rect1\" x=\"0\" y=\"0\" width=\"0\" height=\"0\" fill=\"rgb(255,255,255)\" "
        + "visibility=\"visible\" >\n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"1ms\" attributeName=\"fill\" "
        + "from=\"rgb(255,255,255)\" to=\"rgb(0,0,0)\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"20001ms\" attributeName=\"x\" "
        + "from=\"0\" to=\"40\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"20001ms\" attributeName=\"y\" "
        + "from=\"0\" to=\"40\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"20001ms\" attributeName=\"width\" "
        + "from=\"0\" to=\"40\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"20000ms\" attributeName=\"height\" "
        + "from=\"0\" to=\"40\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"20000ms\" attributeName=\"fill\" "
        + "from=\"rgb(0,0,0)\" to=\"rgb(40,40,40)\" fill=\"freeze\" /> \n"
        + "</rect> \n"
        + "\n"
        + "</svg>", test.toSVGString(500, 500, 2));
  }


  @Test
  public void testToSVGStringEmpty() {
    AnimationBuilder<IScene> builder = new Builder();
    IScene test = builder.build();
    assertEquals("<svg viewBox=\"0 0 500 500\" version=\"1.1\"\n"
            + "     xmlns=\"http://www.w3.org/2000/svg\">\n" + "\n" + "</svg>",
        test.toSVGString(500, 500, 2));
  }

  @Test
  public void testToStringEmpty() {
    AnimationBuilder<IScene> builder = new Builder();
    IScene test = builder.build();
    assertEquals("", test.toString());
  }

  @Test
  public void testGetMinXandMinYUsingSetBounds() {
    AnimationBuilder<IScene> builder = new Builder();
    IScene test = builder.build();
    assertEquals(0, test.getMinX());

    builder.declareShape("Rect", "rectangle").addMotion("Rect", 12, 5,
        1, 1, 1, 1, 1, 1, 20, 10, 2, 2, 2, 2,
        2, 2);
    builder.setBounds(5, 0, 50, 50);
    IScene test2 = builder.build();
    assertEquals(5, test2.getMinX());
    assertEquals(0, test2.getMinY());
  }

  @Test
  public void testGetMaxXUsingSetBounds() {
    AnimationBuilder<IScene> builder = new Builder();
    IScene test = builder.build();
    assertEquals(0, test.getMinX());

    builder.declareShape("Rect", "rectangle").addMotion("Rect", 12, 5,
        1, 1, 1, 1, 1, 1, 20, 10, 2, 2, 2, 2,
        2, 2);
    builder.setBounds(5, 0, 50, 50);
    IScene test2 = builder.build();
    assertEquals(55, test2.getMaxX());
    assertEquals(50, test2.getMaxY());
  }


  @Test
  public void testGetShapeSAt() {
    AnimationBuilder<IScene> builder = new Builder();
    builder.declareShape("Gonzo", "ellipse").addMotion("Gonzo", 0, 0,
        0, 0, 0, 0, 1, 0, 30, 250, 250, 100, 100, 0,
        0, 0);
    IScene test = builder.build();
    assertEquals(1, test.getShapesAt(1).size());
    assertEquals("ellipse", test.getShapesAt(1).get(0).getShapeName());
    assertEquals("  0   0   0   0   0   1   0",
        test.getShapesAt(0).get(0).getShapeInfo());
    builder.declareShape("rect", "rectangle").addMotion("rect", 4, 0,
        0, 0, 0, 255, 255, 255, 50, 0, 0, 0, 0, 0,
        0, 0);
    IScene test2 = builder.build();
    assertEquals(2, test.getShapesAt(4).size());
    assertEquals(2, test.getShapesAt(30).size());
    assertEquals("rectangle", test.getShapesAt(30).get(1).getShapeName());
    assertEquals("  0   0   0   0 255 255 255",
        test.getShapesAt(4).get(1).getShapeInfo());
  }


  @Test
  public void testAddMotionEllipse() {
    AnimationBuilder<IScene> builder = new Builder();
    builder.declareShape("Gonzo", "ellipse").addMotion("Gonzo", 1, 2,
        0, 0, 0, 4, 0, 0, 30, 250, 250, 100, 100, 0,
        0, 0);
    IScene test = builder.build();

    assertEquals("<svg viewBox=\"0 0 500 500\" version=\"1.1\"\n"
        + "     xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "\n"
        + "<ellipse id=\"Gonzo\" cx=\"0\" cy=\"0\" rx=\"0\" ry=\"0\" fill=\"rgb(255,255,255)\" "
        + "visibility=\"visible\" > \n"
        + "\t<animate attributeType=\"xml\" begin=\"500ms\" dur=\"1ms\" attributeName=\"cx\" "
        + "from=\"0\" to=\"2\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"500ms\" dur=\"2ms\" attributeName=\"fill\" "
        + "from=\"rgb(255,255,255)\" to=\"rgb(4,0,0)\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"500ms\" dur=\"14500ms\" attributeName=\"cx\" "
        + "from=\"2\" to=\"250\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"500ms\" dur=\"14500ms\" attributeName=\"cy\" "
        + "from=\"0\" to=\"250\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"500ms\" dur=\"14500ms\" attributeName=\"rx\" "
        + "from=\"0\" to=\"50\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"500ms\" dur=\"14500ms\" attributeName=\"ry\" "
        + "from=\"0\" to=\"50\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"500ms\" dur=\"14501ms\" attributeName=\"fill\" "
        + "from=\"rgb(4,0,0)\" to=\"rgb(0,0,0)\" fill=\"freeze\" /> \n"
        + "</ellipse>\n"
        + "\n"
        + "</svg>", test.toSVGString(500, 500, 2));
    assertEquals("shape Gonzo ellipse\n"
            + "motion Gonzo     1   0   0   0   0 255 255 255     1   2   0   0   0   4   0   0 \n"
            + "motion Gonzo     1   2   0   0   0   4   0   0    30 250 250 100 100   0   0   0 \n",
        test.toString());
  }


  @Test
  public void testAddMotionRectangle() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    IScene ourModel = b.build();
    // since we just add a motion, there are no other motions in the list. There are two motions
    // here since the first motion instantiates the shape
    assertEquals("shape r rectangle\n"
            + "motion     r     0   0   0   0   0 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255    10 100 100 100 100 255 255 255 \n",
        ourModel.toString());
  }


  @Test
  public void testAddOneMotion() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    IScene ourModel = b.build();
    // the addKeyFrame makes its own motion, so there are two here in the list
    assertEquals("shape r rectangle\n"
            + "motion     r     0   0   0   0   0 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255    10 100 100 100 100 255 255 255 \n",
        ourModel.toString());
  }


  @Test
  public void testAddMoreThanOneMotion() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    b.addMotion("r", 10, 50, 50, 50, 50, 255, 255, 255,
        20, 100, 100, 100, 100, 255, 255, 255);
    IScene ourModel = b.build();
    assertEquals("shape r rectangle\n"
            + "motion     r     0   0   0   0   0 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255    10 100 100 100 100 255 255 255 \n"
            + "motion     r    10 100 100 100 100 255 255 255    20 100 100 100 100 255 255 255 \n",
        ourModel.toString());
  }


  @Test
  public void testAddKeyFrameRectangle() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    IScene ourModel = b.build();
    assertEquals(1, ourModel.getShapesAt(0).size());
    assertEquals("shape r rectangle\n"
            + "motion     r     0   0   0   0   0 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255     0  50  50  50  50 255 255 255 \n",
        ourModel.toString());
  }


  @Test
  public void testAddKeyFrameEllipse() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("e", "ellipse");
    b.addKeyframe("e", 0, 50, 50, 50, 50, 255, 255, 255);
    IScene ourModel = b.build();
    assertEquals(1, ourModel.getShapesAt(0).size());
    assertEquals("shape e ellipse\n"
            + "motion     e     0   0   0   0   0 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     e     0  50  50  50  50 255 255 255     0  50  50  50  50 255 255 255 \n",
        ourModel.toString());
  }


  @Test
  public void testAddMultipleKeyFramesEllipse() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("e", "ellipse");
    b.addKeyframe("e", 0, 50, 50, 50, 50, 255, 255, 255);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    b.declareShape("r2", "rectangle");
    b.addKeyframe("r2", 0, 0, 0, 0, 0, 0, 0, 0);
    IScene ourModel = b.build();
    assertEquals(3, ourModel.getShapesAt(0).size());
    assertEquals("shape e ellipse\n"
            + "motion     e     0   0   0   0   0 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     e     0  50  50  50  50 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "shape r rectangle\n"
            + "motion     r     0   0   0   0   0 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "shape r2 rectangle\n"
            + "motion    r2     0   0   0   0   0 255 255 255     0   0   0   0   0   0   0   0 \n"
            + "motion    r2     0   0   0   0   0   0   0   0     0   0   0   0   0   0   0   0 \n",
        ourModel.toString());
  }


  // missed tests from last hw:
  // make an empty scene
  @Test
  public void testEmptyScene() {
    AnimationBuilder<IScene> builder = new Builder();
    IScene test = builder.build();
    assertEquals("", test.toString());
    assertEquals(new ArrayList<Shape>(), test.getShapesAt(1));
    assertEquals(new ArrayList<IAnimation>(), test.getAnimations());
  }


  @Test
  public void testDeclareMultipleShapes() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    b.declareShape("r2", "rectangle");
    b.declareShape("r3", "rectangle");
    b.declareShape("e", "rectangle");
    IScene ourModel = b.build();
    assertEquals(4, ourModel.getShapesAt(0).size());
  }


  @Test
  public void testDeclareRectangle() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    IScene ourModel = b.build();
    assertEquals(1, ourModel.getShapesAt(0).size());
    assertEquals("rectangle", ourModel.getShapesAt(0).get(0).getShapeName());
  }

  @Test
  public void testDeclareEllipse() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("e", "ellipse");
    IScene ourModel = b.build();
    assertEquals(1, ourModel.getShapesAt(0).size());
    assertEquals("ellipse", ourModel.getShapesAt(0).get(0).getShapeName());

  }

  @Test
  public void testDeclareUnknownShape() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("s", "star");
    IScene ourModel = b.build();
    assertEquals(0, ourModel.getShapesAt(0).size());

  }

  @Test
  public void testDeclareInvalidShape() {
    try {
      Builder b = new Builder();
      b.setBounds(0, 0, 200, 200);
      b.declareShape("e", "ellipse");
      b.declareShape("e", "rectangle");
      IScene ourModel = b.build();
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Shape with given name e already exists", e.getMessage());
    }
  }


  @Test
  public void testAddKeyFrameBetweenAddMotions() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    b.addMotion("r", 10, 50, 50, 50, 50, 255, 255, 255,
        20, 100, 100, 100, 100, 0, 0, 0);
    b.addKeyframe("r", 20, 40, 50, 50, 60, 0, 0, 0);
    b.addMotion("r", 30, 30, 30, 30, 30, 30, 30, 30, 40,
        40, 40, 40, 40, 40, 40, 40);
    IScene ourModel = b.build();
    assertEquals("shape r rectangle\n"
            + "motion     r     0   0   0   0   0 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255    10 100 100 100 100 255 255 255 \n"
            + "motion     r    10 100 100 100 100 255 255 255    20 100 100 100 100   0   0   0 \n"
            + "motion     r    20 100 100 100 100   0   0   0    20  40  50  50  60   0   0   0 \n"
            + "motion     r    30  40  50  50  60   0   0   0    40  40  40  40  40  40  40  40 \n",
        ourModel.toString());

  }


  @Test
  public void testAddKeyFrameAfterMotion() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    b.addMotion("r", 10, 50, 50, 50, 50, 255, 255, 255,
        20, 100, 100, 100, 100, 255, 255, 255);
    b.addKeyframe("r", 20, 50, 50, 50, 50, 255, 255, 255);
    IScene ourModel = b.build();
    assertEquals("shape r rectangle\n"
            + "motion     r     0   0   0   0   0 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255    10 100 100 100 100 255 255 255 \n"
            + "motion     r    10 100 100 100 100 255 255 255    20 100 100 100 100 255 255 255 \n"
            + "motion     r    20 100 100 100 100 255 255 255    20  50  50  50  50 255 255 255 \n",
        ourModel.toString());
  }


  @Test
  public void testGetShapesAt() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    b.addMotion("r", 10, 50, 50, 50, 50, 255, 255, 255,
        20, 100, 100, 100, 100, 255, 255, 255);
    IScene ourModel = b.build();
    assertEquals(1, ourModel.getShapesAt(10).size());
    b.declareShape("e", "ellipse");
    b.addMotion("e", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    assertEquals(2, ourModel.getShapesAt(10).size());
  }


  @Test
  public void testAddKeyframe() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    b.addMotion("r", 10, 50, 50, 50, 50, 255, 255, 255,
        20, 100, 100, 100, 100, 255, 255, 255);
    IScene ourModel = b.build();
    // after all motions
    ourModel.addKeyframe("r", 30);
    assertEquals("shape r rectangle\n"
            + "motion     r     0   0   0   0   0 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255    10 100 100 100 100 255 255 255 \n"
            + "motion     r    10 100 100 100 100 255 255 255    20 100 100 100 100 255 255 255 \n"
            + "motion     r    20 100 100 100 100 255 255 255    30 100 100 100 100 255 255 255 \n",
        ourModel.toString());
    // in between a motion
    ourModel.addKeyframe("r", 15);
    assertEquals("shape r rectangle\n"
            + "motion     r     0   0   0   0   0 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255    10 100 100 100 100 255 255 255 \n"
            + "motion     r    10 100 100 100 100 255 255 255    15 100 100 100 100 255 255 255 \n"
            + "motion     r    15 100 100 100 100 255 255 255    20 100 100 100 100 255 255 255 \n"
            + "motion     r    20 100 100 100 100 255 255 255    30 100 100 100 100 255 255 255 \n",
        ourModel.toString());
  }

  @Test
  public void testAddKeyframeBeforeStart() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");

    b.addMotion("r", 10, 50, 50, 50, 50, 255, 255, 255,
        20, 100, 100, 100, 100, 255, 255, 255);
    IScene ourModel = b.build();
    // after all motions
    ourModel.addKeyframe("r", 5);
    assertEquals("shape r rectangle\n"
            + "motion     r     5   0   0   0   0 255 255 255     5   0   0   0   0 255 255 255 \n"
            + "motion     r     5   0   0   0   0 255 255 255    10  50  50  50  50 255 255 255 \n"
            + "motion     r    10  50  50  50  50 255 255 255    20 100 100 100 100 255 255 255 \n",
        ourModel.toString());

  }


  @Test
  public void testAddKeyframeBad() {
    try {
      Builder b = new Builder();
      b.setBounds(0, 0, 200, 200);
      b.declareShape("r", "rectangle");
      b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
      b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
          10, 100, 100, 100, 100, 255, 255, 255);
      b.addMotion("r", 10, 50, 50, 50, 50, 255, 255, 255,
          20, 100, 100, 100, 100, 255, 255, 255);
      IScene ourModel = b.build();
      ourModel.addKeyframe("ellipse", 30);
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Hey! There is no animation with name ellipse", e.getMessage());

    }
  }

  @Test
  public void testRemoveKeyframe() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    b.addMotion("r", 10, 50, 50, 50, 50, 255, 255, 255,
        20, 100, 100, 100, 100, 255, 255, 255);
    IScene ourModel = b.build();
    ourModel.removeKeyframe("r", 10);
    assertEquals("shape r rectangle\n"
            + "motion     r     0   0   0   0   0 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r    10  50  50  50  50 255 255 255    20 100 100 100 100 255 255 255 \n",
        ourModel.toString());

  }
}




