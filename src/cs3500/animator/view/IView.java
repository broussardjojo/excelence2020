package cs3500.animator.view;

import cs3500.animator.model.scene.IScene;
import java.io.IOException;


/**
 * A view interface supporting multiple types of XML based views of a Scene or more!.
 */
public interface IView {

  /**
   * Produces the display to each view.
   */
  void produceDisplay();

  /**
   * Produces the display of the given scene to each view. Used in controller.
   * @param scene the scene to represent.
   */
  void produceDisplay(IScene scene);

  /**
   * Sets the fields of this view (mostly width / height) from the passed scene.
   * @param scene the scene to get some info from
   */
  void setFields(IScene scene);


  /**
   * Produces a file of the view of the animation.
   * @throws IOException if writing to file fails.
   */
  void toFile(String fileName) throws IOException;


  /**
   * writes a depiction of the passed scene to the passed file path.
   * @param scene the scene to write
   * @param fileName the file path to write to
   * @throws IOException if writing to file fails.
   */
  void toFile(IScene scene, String fileName) throws IOException;


  /**
   * Sets the tick rate to the given int.
   * @param tickRate the tick rate of the view
   * @throws IllegalArgumentException if the tick rate is invalid.
   */
  void setTickRate(int tickRate) throws IllegalArgumentException;

  /**
   * Gets the tick rate of the view.
   * @return an int that represents the tick rate of the view.
   */
  int getTickRate();


  /**
   * Sets the width of the view to the given int.
   * @param width an int that represents the width of the view.
   * @throws IllegalArgumentException if the width is negative
   */
  void setWidth(int width) throws IllegalArgumentException;


  /**
   * Gets the width of the view.
   * @return the width of the view.
   */
  int getWidth();

  /**
   * Sets the height of the view.
   * @param height an int that represents the height of the view.
   * @throws IllegalArgumentException if the height is negative
   */
  void setHeight(int height) throws IllegalArgumentException;


  /**
   * Gets the heights of the view.
   * @return an int that represents the height of the view.
   */
  int getHeight();
}
