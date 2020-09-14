package cs3500.animator.view;

import cs3500.animator.model.shape.Shape;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Given a graphics object, uses it to draw shapes in the provided model.
 */
public class ShapeDrawer {

  private Graphics g;

  /**
   * Constructs the ShapeDrawer with a graphics object.
   *
   * @param g the graphics object passed
   */
  public ShapeDrawer(Graphics g) {
    this.g = g;
  }

  /**
   * Draws a given shape. We were going to do this with a visitor but we didn't wanna add accept
   * into our Shape class- maybe we'll come back to this :).
   *
   * @param shape the shape to be drawn
   */
  void draw(Shape shape) {
    switch (shape.getShapeName()) {
      case "rectangle":
        g.setColor(new Color(shape.getRed(), shape.getGreen(), shape.getBlue()));
        g.drawRect(shape.getMinXForSVG(), shape.getMinYForSVG(),
            shape.getWidth(), shape.getHeight());
        g.fillRect(shape.getMinXForSVG(), shape.getMinYForSVG(),
            shape.getWidth(), shape.getHeight());
        break;
      case "ellipse":
        g.setColor(new Color(shape.getRed(), shape.getGreen(), shape.getBlue()));
        g.drawOval(shape.getMinXForSVG(), shape.getMinYForSVG(),
            shape.getWidth(), shape.getHeight());
        g.fillOval(shape.getMinXForSVG(), shape.getMinYForSVG(),
            shape.getWidth(), shape.getHeight());
        break;
      default:
        // Do nothing
    }
  }


}
