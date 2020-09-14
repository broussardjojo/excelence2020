import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import cs3500.animator.controller.BasicController;
import cs3500.animator.controller.IController;
import cs3500.animator.model.scene.IScene;
import cs3500.animator.model.scene.Scene.Builder;
import cs3500.animator.view.IView;
import cs3500.animator.view.TextView;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

/**
 * Test class for our Text View.
 */
public class TextViewTest {

  @Test
  public void testToConsole() {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    b.declareShape("r", "rectangle");
    b.addKeyframe("r", 0, 50, 50, 50, 50, 255, 255, 255);
    b.addMotion("r", 0, 50, 50, 50, 50, 255, 255, 255,
        10, 100, 100, 100, 100, 255, 255, 255);
    IScene ourModel = b.build();
    IView ourTextView = new TextView();
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
    IScene ourModel = b.build();
    IView ourTextView = new TextView();
    IController controller = new BasicController();
    controller.setModel(ourModel);
    controller.makeFile(ourTextView, "C:\\Users\\Jojo"
        + " Broussard\\Documents\\OOD\\hwk06\\test1.txt");
    String data = new String(Files.readAllBytes(
        Paths.get("C:\\Users\\Jojo Broussard\\Documents\\OOD\\hwk06\\test1.txt")));
    assertEquals("canvas 0 0 200 200\n"
            + "shape r rectangle\n"
            + "motion     r     0   0   0   0   0 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255     0  50  50  50  50 255 255 255 \n"
            + "motion     r     0  50  50  50  50 255 255 255    10 100 100 100 100 255 255 255 \n",
        data);
  }

  @Test
  public void testToFileBlank() throws IOException {
    Builder b = new Builder();
    b.setBounds(0, 0, 200, 200);
    IScene ourModel = b.build();
    IView ourTextView = new TextView();
    IController controller = new BasicController();
    controller.setModel(ourModel);
    controller.makeFile(ourTextView, "C:\\Users\\Jojo"
        + " Broussard\\Documents\\OOD\\hwk06\\test1.svg");
    String data = new String(Files.readAllBytes(
        Paths.get("C:\\Users\\Jojo Broussard\\Documents\\OOD\\hwk06\\test1.svg")));
    assertEquals("canvas 0 0 200 200\n", data);
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
    IView ourTextView = new TextView();
    assertEquals(ourTextView.getTickRate(), 0);
    ourTextView.setTickRate(10);
    // set to 0 for text since it doesnt matter
    assertEquals(ourTextView.getTickRate(), 0);
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
    IView ourTextView = new TextView();
    assertEquals(ourTextView.getWidth(), 0);
    ourTextView.setWidth(300);
    assertEquals(ourTextView.getWidth(), 300);
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
    IView ourTextView = new TextView();
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
      IView ourTextView = new TextView();
      ourTextView.setHeight(-40);
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
      IView ourTextView = new TextView();
      ourTextView.setWidth(-40);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Width can't be negative", e.getMessage());
    }
  }


}
