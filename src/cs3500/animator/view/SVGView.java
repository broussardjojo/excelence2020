package cs3500.animator.view;

import cs3500.animator.model.scene.IScene;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class to represent an SVG XML view of a scene.
 */
public class SVGView extends AbstractView {

  private int tickRate;

  //INVARIANTS:
  // Tick rate is greater than zero (verified by methods and constructor).

  /**
   * Constructs the SVGView without taking in any fields and sets width and height to 0, model to
   * null, and tickRate to 1.
   */
  public SVGView() {
    this.width = 0;
    this.height = 0;
    this.model = null;
    this.tickRate = 1;
  }

  /**
   * Produces the display to each view.
   */
  // DEPRECIATED
  @Override
  public void produceDisplay() {
    System.out.println(model.toSVGString(width, height, tickRate));
  }

  /**
   * Produces the display of the given scene to each view. Used in controller.
   *
   * @param scene the scene to represent.
   */
  @Override
  public void produceDisplay(IScene scene) {
    System.out.println(scene.toSVGString(this.width, this.height, this.tickRate));
  }

  /**
   * Sets the fields of this view (mostly width / height) from the passed scene.
   *
   * @param scene the scene to get some info from
   */
  @Override
  public void setFields(IScene scene) {
    this.width = scene.getMaxX() - scene.getMinX();
    this.height = scene.getMaxY() - scene.getMinY();

  }

  /**
   * Produces a file of the view of the animation.
   *
   * @param fileName file to write to
   */
  @Override
  public void toFile(String fileName) throws IOException {
    try {
      FileWriter writer = new FileWriter(fileName);
      writer.write(model.toSVGString(width, height, tickRate));
      writer.close();
    } catch (IOException err) {
      throw new IOException("Something went wrong :( " + err.getStackTrace());
    }
  }

  /**
   * writes a depiction of the passed scene to the passed file path.
   *
   * @param scene    the scene to write
   * @param fileName the file path to write to
   * @throws IOException if writing to file fails.
   */
  @Override
  public void toFile(IScene scene, String fileName) throws IOException {
    try {
      FileWriter writer = new FileWriter(fileName);
      writer.write(scene.toSVGString((scene.getMaxX() - scene.getMinX()),
          (scene.getMaxY() - scene.getMinY()), this.tickRate));
      writer.close();
    } catch (IOException err) {
      throw new IOException("Something went wrong :( " + err.getStackTrace());
    }
  }

  /**
   * Sets the tick rate to the given int.
   *
   * @param tickRate the tick rate of the view
   * @throws IllegalArgumentException if the tick rate is invalid.
   */
  @Override
  public void setTickRate(int tickRate) throws IllegalArgumentException {
    if (tickRate <= 0) {
      throw new IllegalArgumentException("TickRate must be greater than 0");
    }
    // So that duration won't be 0ms
    if (tickRate > 999) {
      this.tickRate = 999;
    }
    this.tickRate = tickRate;
  }

  /**
   * Gets the tick rate of the view.
   *
   * @return an int that represents the tick rate of the view.
   */
  @Override
  public int getTickRate() {
    return this.tickRate;
  }
}
