package cs3500.animator.view;

import cs3500.animator.model.scene.IScene;
import cs3500.animator.model.shape.Shape;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JPanel;

/**
 * The panel on which all shapes are drawn.
 */
public class CellPanel extends JPanel {

  private List<Shape> shapes;
  private IScene model;

  /**
   * Constructs the CellPanel by taking in the model. It's size is set to the difference between the
   * model's max X and min X values for width, and the difference between the model's max Y and min
   * Y for height. It's background is set to white.
   *
   * @param model the scene to draw.
   */
  public CellPanel(IScene model) {
    this.model = model;
    shapes = model.getShapesAt(0);
    this.setPreferredSize(new Dimension(model.getMaxX() - model.getMinX(),
        model.getMaxY() - model.getMinY()));
    this.setBackground(Color.WHITE);
  }

  /**
   * Sets the list of shapes to the given list of shapes.
   *
   * @param shapes a list of shapes
   */
  public void setShapes(List<Shape> shapes) {
    this.shapes = shapes;
  }

  public void drawAtTick(int tick) {
    this.setShapes(model.getShapesAt(tick));
    this.repaint();
  }

  /**
   * Calls the UI delegate's paint method, if the UI delegate is non-null.  We pass the delegate a
   * copy of the Graphics object to protect the rest of the paint code from irrevocable changes. It
   * draws the shapes on the panel.
   *
   * @param g the Graphics object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g1 = (Graphics2D) g;
    g1.translate(this.model.getMinX() * -1, this.model.getMinY() * -1);
    ShapeDrawer shapeDrawer = new ShapeDrawer(g1);
    for (Shape s : shapes) {
      shapeDrawer.draw(s);
    }
  }

  /**
   * Returns a copy of the model, to give info to controller.
   *
   * @return a copy of the model, to give info to controller
   */
  public IScene getScene() {
    return this.model;
  }

  /**
   * Removes the motion at the given tick from animation with the given name.
   *
   * @param name the name of the animation to remove from.
   * @param tick the tick to remove motion at.
   */
  public void removeKeyframe(String name, int tick) {
    this.model.getAnimationWithName(name).removeKeyframeEndingAt(tick);
  }

  /**
   * Adds a keyframe at the given tick to animation with given name. Auto tweens so that it doesn't
   * look any different.
   *
   * @param name the name of the animation to add to
   * @param tick the tick to add motion at
   */
  public void addKeyframe(String name, int tick) {
    this.model.getAnimationWithName(name).split(tick);
  }
}
