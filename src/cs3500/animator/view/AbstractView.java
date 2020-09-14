package cs3500.animator.view;

import cs3500.animator.model.scene.IScene;
import java.io.IOException;

/**
 * An abstract view class; overloads common functionality in a few classes. It contains
 * the width and height of the view's canvas and a model.
 */
public abstract class AbstractView implements IView {

  protected int width;
  protected int height;
  protected IScene model;

  //INVARIANTS:
  // The width and height cannot be negative. (verified by the methods)

  /**
   * Produces a file of the view of the animation.
   */
  @Override
  public void toFile(String fileName) throws IOException {
    // Nothing in here
  }


  /**
   * Just returns, since not all implementations of the IView have tickRate fields.
   * @param tickRate the tick rate of the view
   */
  @Override
  public void setTickRate(int tickRate) {
    // nothing in here
    return;
  }


  /**
   * Gets the tick rate. In this case, we just say 0, since not all implementations have the
   * tickRate field (since they don't all need it).
   * @return the int 0
   */
  @Override
  public int getTickRate() {
    return 0;
  }

  /**
   * Sets the width of the view to the given int.
   * @param width an int that represents the width of the view.
   * @throws IllegalArgumentException if the width is negative
   */
  @Override
  public void setWidth(int width) throws IllegalArgumentException {
    if (width < 0) {
      throw new IllegalArgumentException("Width can't be negative");
    }
    this.width = width;
  }

  /**
   * Sets the height of the view.
   * @param height an int that represents the height of the view.
   * @throws IllegalArgumentException if the height is negative
   */
  @Override
  public void setHeight(int height) {
    if (height < 0) {
      throw new IllegalArgumentException("Height can't be negative");
    }
    this.height = height;
  }

  /**
   * Gets the width of the view.
   * @return the width of the view.
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Gets the heights of the view.
   * @return an int that represents the height of the view.
   */
  @Override
  public int getHeight() {
    return this.height;
  }


}
