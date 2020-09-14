import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import cs3500.animator.controller.BasicController;
import cs3500.animator.controller.IController;
import cs3500.animator.model.scene.IScene;
import cs3500.animator.model.scene.Scene.Builder;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

/**
 * Test class for our SVG view.
 */
public class SVGViewTest {

  @Test
  public void testInvalidSVGViewConstructor() {
    try {
      Builder b = new Builder();
      b.setBounds(0, 0, 200, 200);
      b.declareShape("r", "rectangle");
      b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
      b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
          10, 100, 100, 100, 100, 255, 255, 255);
      IScene ourModel = b.build();
      IController controller = new BasicController();
      controller.setModel(ourModel);
      IView ourTextView = new SVGView();
      ourTextView.setTickRate(-3);
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("TickRate must be greater than 0", e.getMessage());
    }

    try {
      Builder b = new Builder();
      b.setBounds(0, 0, 200, 200);
      b.declareShape("r", "rectangle");
      b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
      b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
          10, 100, 100, 100, 100, 255, 255, 255);
      IScene ourModel = b.build();
      IView ourTextView = new SVGView();
      IController controller = new BasicController();
      controller.setModel(ourModel);
      ourTextView.setTickRate(0);
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("TickRate must be greater than 0", e.getMessage());
    }
  }


  @Test
  public void testToConsole() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    IScene ourModel = b.build();
    IView ourTextView = new SVGView();
    IController controller = new BasicController();
    controller.setModel(ourModel);
    controller.playAnimation(ourTextView);
    // We see at runtime the stuff on the console.
    assertNotNull(ourModel);
  }

  @Test
  public void testToFile() throws IOException {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    b.declareShape("e", "ellipse");
    b.addKeyframe("e", 0, 100, 100, 100, 100, 100, 100, 100);
    b.addMotion("e", 0, 100, 100, 100, 100, 100, 100, 100,
        50, 200, 200, 200, 200, 100, 100, 100);
    b.addMotion("e", 50, 200, 200, 200, 200, 100, 100, 100,
        200, 200, 200, 200, 100, 100, 100, 255);
    IScene ourModel = b.build();
    IController controller = new BasicController();
    controller.setModel(ourModel);
    IView ourTextView = new SVGView();
    controller.makeFile(ourTextView, "C:\\Users\\Jojo Broussard\\Documents\\OOD\\hwk06\\test1.svg");
    String data = new String(Files.readAllBytes(
        Paths.get("C:\\Users\\Jojo Broussard\\Documents\\OOD\\hwk06\\test1.svg")));
    assertEquals("<svg viewBox=\"0 0 200 200\" version=\"1.1\"\n"
        + "     xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "\n"
        + "<rect id=\"r\" x=\"0\" y=\"0\" width=\"0\" height=\"0\" fill=\"rgb(255,255,255)\""
        + " visibility=\"visible\" >\n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"2ms\" attributeName=\"x\""
        + " from=\"0\" to=\"50\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"2ms\" attributeName=\"y\" "
        + "from=\"0\" to=\"50\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"2ms\" attributeName=\"width\" "
        + "from=\"0\" to=\"50\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"1ms\" attributeName=\"height\""
        + " from=\"0\" to=\"50\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"10001ms\" attributeName=\"x\""
        + " from=\"50\" to=\"100\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"10001ms\" attributeName=\"y\""
        + " from=\"50\" to=\"100\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"10001ms\" attributeName=\"width\""
        + " from=\"50\" to=\"100\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"10000ms\" attributeName=\"height\""
        + " from=\"50\" to=\"100\" fill=\"freeze\" /> \n"
        + "</rect> \n"
        + "\n"
        + "<ellipse id=\"e\" cx=\"0\" cy=\"0\" rx=\"0\" ry=\"0\" fill=\"rgb(255,255,255)\""
        + " visibility=\"visible\" > \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"1ms\" attributeName=\"cx\""
        + " from=\"0\" to=\"100\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"1ms\" attributeName=\"cy\""
        + " from=\"0\" to=\"100\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"1ms\" attributeName=\"rx\""
        + " from=\"0\" to=\"50\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"1ms\" attributeName=\"ry\""
        + " from=\"0\" to=\"50\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"2ms\" attributeName=\"fill\" "
        + "from=\"rgb(255,255,255)\" to=\"rgb(100,100,100)\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"50000ms\" attributeName=\"cx\""
        + " from=\"100\" to=\"200\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"50000ms\" attributeName=\"cy\""
        + " from=\"100\" to=\"200\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"50000ms\" attributeName=\"rx\""
        + " from=\"50\" to=\"100\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"50000ms\" attributeName=\"ry\""
        + " from=\"50\" to=\"100\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"50000ms\" dur=\"150000ms\" attributeName=\"ry\""
        + " from=\"100\" to=\"50\" fill=\"freeze\" /> \n"
        + "\t<animate attributeType=\"xml\" begin=\"50000ms\" dur=\"150001ms\""
        + " attributeName=\"fill\" from=\"rgb(100,100,100)\""
        + " to=\"rgb(100,100,255)\" fill=\"freeze\""
        + " /> \n"
        + "</ellipse>\n"
        + "\n"
        + "</svg>", data);
  }


  @Test
  public void testToFileBlank() throws IOException {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    IScene ourModel = b.build();
    IView ourTextView = new SVGView();
    IController controller = new BasicController();
    controller.setModel(ourModel);
    controller.makeFile(ourTextView, "C:\\Users\\Jojo "
        + "Broussard\\Documents\\OOD\\hwk06\\test1.svg");
    String data = new String(Files.readAllBytes(
        Paths.get("C:\\Users\\Jojo Broussard\\Documents\\OOD\\hwk06\\test1.svg")));
    assertEquals("<svg viewBox=\"0 0 200 200\" version=\"1.1\"\n"
        + "     xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "\n"
        + "</svg>", data);
  }

  @Test
  public void testTickRate() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    IScene ourModel = b.build();
    IView ourTextView = new SVGView();
    assertEquals(ourTextView.getTickRate(), 1);
    ourTextView.setTickRate(10);
    assertEquals(ourTextView.getTickRate(), 10);
  }

  @Test
  public void testWidth() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 250);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    IScene ourModel = b.build();
    IView ourTextView = new SVGView();
    assertEquals(ourTextView.getWidth(), 0);
    ourTextView.setWidth(250);
    assertEquals(ourTextView.getWidth(), 250);

  }

  @Test
  public void testHeight() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 250);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    IScene ourModel = b.build();
    IView ourTextView = new SVGView();
    assertEquals(ourTextView.getHeight(), 0);
    ourTextView.setHeight(300);
    assertEquals(ourTextView.getHeight(), 300);

  }


  @Test
  public void testBadHeightValues() {
    try {
      Builder b = new Builder();
      b.setBounds(0, 0, 200, 250);
      b.declareShape("r", "rectangle");
      b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
      b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
          10, 100, 100, 100, 100, 255, 255, 255);
      IScene ourModel = b.build();
      IView ourSVGView = new SVGView();
      ourSVGView.setHeight(-40);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Height can't be negative", e.getMessage());
    }

  }


  @Test
  public void testBadWidthValues() {
    try {
      Builder b = new Builder();
      b.setBounds(0, 0, 200, 250);
      b.declareShape("r", "rectangle");
      b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
      b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
          10, 100, 100, 100, 100, 255, 255, 255);
      IScene ourModel = b.build();
      IView ourTextView = new SVGView();
      ourTextView.setWidth(-40);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Width can't be negative", e.getMessage());
    }
  }


  @Test
  public void testBadTickRate() {
    try {
      Builder b = new Builder();
      b.setBounds(0, 0, 200, 250);
      b.declareShape("r", "rectangle");
      b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
      b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
          10, 100, 100, 100, 100, 255, 255, 255);
      IScene ourModel = b.build();
      IView ourTextView = new SVGView();
      ourTextView.setTickRate(-40);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("TickRate must be greater than 0", e.getMessage());
    }
    try {
      Builder b = new Builder();
      b.setBounds(0, 0, 200, 250);
      b.declareShape("r", "rectangle");
      b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
      b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
          10, 100, 100, 100, 100, 255, 255, 255);
      IScene ourModel = b.build();
      IView ourTextView = new SVGView();
      ourTextView.setTickRate(0);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("TickRate must be greater than 0", e.getMessage());
    }

  }

}

