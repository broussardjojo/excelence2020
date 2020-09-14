package cs3500.animator.model.shape;

import java.util.HashMap;

/**
 * An abstract class to represent a shape defined by its center, width, height, and color.
 */
public abstract class AbstractShape implements Shape {

  // Fields
  // INVARIANTS:
  // Width and height will never be negative
  // RGB values will be between 0-255, inclusive
  protected int x;
  protected int y;
  protected int width;
  protected int height;
  protected int red;
  protected int green;
  protected int blue;
  // Might want an angle arg later so that the shape can be rotated

  // Getter functions

  /**
   * Returns the width value of the shape object.
   *
   * @return the integer width of the shape object.
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Returns the height value of the shape object.
   *
   * @return the integer height of the shape object.
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * Gets the x value of the center of this shape.
   *
   * @return the x value of the center of this shape.
   */
  @Override
  public int getX() {
    return this.x;
  }

  /**
   * Gets the y value of the center of this shape.
   *
   * @return the y value of the center of this shape.
   */
  @Override
  public int getY() {
    return this.y;
  }

  /**
   * Returns the red value of this shape object as an int (0 - 255).
   *
   * @return the red value of this shape object as an int (0 - 255).
   */
  @Override
  public int getRed() {
    return this.red;
  }

  /**
   * Returns the green value of this shape object as an int (0 - 255).
   *
   * @return the green value of this shape object as an int (0 - 255).
   */
  @Override
  public int getGreen() {
    return this.green;
  }

  /**
   * Returns the blue value of this shape object as an int (0 - 255).
   *
   * @return the blue value of this shape object as an int (0 - 255).
   */
  @Override
  public int getBlue() {
    return this.blue;
  }

  /**
   * Sets the values of this shape to those provided in the parameters.
   *
   * @param x      new x value of the center of the shape
   * @param y      new y value of the center of the shape
   * @param width  new width value of the center of the shape
   * @param height new height value of the center of the shape
   * @param red    new red value of the center of the shape
   * @param green  new green value of the center of the shape
   * @param blue   new blue value of the center of the shape
   */
  @Override
  public void set(int x, int y, int width, int height, int red, int green, int blue) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Sets the values of this shape to those provided in the parameters.
   *
   * @param x      new x value of the center of the shape
   * @param y      new y value of the center of the shape
   * @param width  new width value of the center of the shape
   * @param height new height value of the center of the shape
   * @param red    new red value of the center of the shape
   * @param green  new green value of the center of the shape
   * @param blue   new blue value of the center of the shape
   */
  @Override
  public void set(double x, double y, double width, double height, double red, double green,
      double blue) {
    this.x = (int) x;
    this.y = (int) y;
    this.width = (int) width;
    this.height = (int) height;
    this.red = (int) red;
    this.green = (int) green;
    this.blue = (int) blue;
  }

  /**
   * Returns a string representation of this shape's fields (x, y, width, height, r, g, b).
   *
   * @return a string representation of this shape's fields (x, y, width, height, r, g, b)
   */
  @Override
  public String getShapeInfo() {
    String out = String
        .format("%3d %3d %3d %3d %3d %3d %3d",
            this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getRed(),
            this.getGreen(), this.getBlue());
    return out;
  }

  /**
   * Compares two objects - returns true if they represent the same shape.
   *
   * @param obj the object to compare this to
   * @return a boolean true if the objects represent the same shape, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (this.getClass() != obj.getClass()) {
      return false;
    } else {
      Shape that = (Shape) obj;
      return (
          this.getX() == that.getX()
              && this.getY() == that.getY()
              && this.getWidth() == that.getWidth()
              && this.getHeight() == that.getHeight()
              && this.getRed() == that.getRed() && this.getGreen() == that.getGreen()
              && this.getBlue() == that.getBlue());
    }
  }

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash
   * tables such as those provided by {@link HashMap}.
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return this.getShapeInfo().hashCode();
  }

  /**
   * Returns a string representing what kind of shape this object is.
   *
   * @return a string representing what kind of shape this object is.
   */
  public String getShapeName() {
    return this.getShapeName();
  }

  /**
   * Returns a deep copy of this shape.
   *
   * @return a deep copy of this shape.
   */
  @Override
  public Shape copy() {
    return this.copy();
  }

}
