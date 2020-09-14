import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.animator.view.ConcreteViewFactory;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.VisualView;
import org.junit.Test;

/**
 * This class contains tests for the Concrete View Factory class.
 */
public class ConcreteViewFactoryTest {

  @Test
  public void testFactoryVisual() {
    ConcreteViewFactory factory = new ConcreteViewFactory();
    assertTrue(factory.make("visual") instanceof VisualView);
  }

  @Test
  public void testFactorySVG() {
    ConcreteViewFactory factory = new ConcreteViewFactory();
    assertTrue(factory.make("svg") instanceof SVGView);
  }


  @Test
  public void testFactoryText() {
    ConcreteViewFactory factory = new ConcreteViewFactory();
    assertTrue(factory.make("text") instanceof TextView);
  }

  @Test
  public void testFactoryEdit() {
    ConcreteViewFactory factory = new ConcreteViewFactory();
    assertTrue(factory.make("edit") instanceof VisualView); // we know that this is the case
    // and it is purposeful
  }

  @Test
  public void testFactoryInvalidViewType() {
    try {
      ConcreteViewFactory factory = new ConcreteViewFactory();
      factory.make("invalidView");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid view type: invalidView", e.getMessage());
    }

  }

}
