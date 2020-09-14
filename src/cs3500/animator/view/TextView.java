package cs3500.animator.view;

import cs3500.animator.model.scene.IScene;
import cs3500.animator.model.scene.Scene;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class to represent a textual view of a scene.
 */
public class TextView extends AbstractView {

  /**
   * Constructs the TextView without taking in any fields, sets the width and height to 0, and sets
   * model to null.
   */
  public TextView() {
    this.width = 0;
    this.height = 0;
    this.model = null;
  }

  /**
   * Constructs the TextView by taking in all three fields.
   *
   * @param width  the width of the Canvas
   * @param height the height of the canvas
   * @param model  the model to represent
   */
  public TextView(int width, int height, Scene model) {
    this.width = width;
    this.height = height;
    this.model = model;
  }

  /**
   * Produces the display to each view.
   */
  // DEPRECIATED
  @Override
  public void produceDisplay() {
    System.out.println("canvas " + model.getMinX() + " " + model.getMinY() + " "
        + width + " " + height);
    System.out.println(model.toString());
  }

  /**
   * Produces the display of the given scene to each view. Used in controller.
   *
   * @param scene the scene to represent.
   */
  @Override
  public void produceDisplay(IScene scene) {
    this.setFields(scene);
    System.out.println("canvas " + scene.getMinX() + " " + scene.getMinY() + " "
        + this.width + " " + this.height);
    System.out.println(scene.toString());
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
   * Produces a file of the view of the animation. Depreciated.
   *
   * @throws IOException if writing to file fails.
   */
  @Override
  public void toFile(String fileName) throws IOException {
    try {
      FileWriter writer = new FileWriter(fileName);
      writer.write("canvas " + model.getMinX() + " " + model.getMinY() + " "
          + width + " " + height + '\n');
      writer.write(model.toString());
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
      this.setFields(scene);
      writer.write("canvas " + scene.getMinX() + " " + scene.getMinY() + " "
          + this.width + " " + this.height + '\n');
      writer.write(scene.toString());
      writer.close();
    } catch (IOException err) {
      throw new IOException("Something went wrong :( " + err.getStackTrace());
    }
  }
}
