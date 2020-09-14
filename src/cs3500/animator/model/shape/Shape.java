package cs3500.animator.model.shape;

import java.util.HashMap;

/**
 * An interface to represent a shape defined by its center, width, height, and color.
 */
public interface Shape {

  /**
   * Returns the x (horizontal) position of the center of this shape object.
   *
   * @return the x position of the center of this shape object.
   */
  int getX();

  /**
   * Returns the y (vertical) position of the center of this shape object.
   *
   * @return the y position of the center of this shape object.
   */
  int getY();

  /**
   * Returns the width value of the shape object.
   *
   * @return the integer width of the shape object.
   */
  int getWidth();

  /**
   * Returns the height value of the shape object.
   *
   * @return the integer height of the shape object.
   */
  int getHeight();


  /**
   * Returns the red value of this shape object as an int (0 - 255).
   *
   * @return the red value of this shape object as an int (0 - 255).
   */
  int getRed();

  /**
   * Returns the green value of this shape object as an int (0 - 255).
   *
   * @return the green value of this shape object as an int (0 - 255).
   */
  int getGreen();

  /**
   * Returns the blue value of this shape object as an int (0 - 255).
   *
   * @return the blue value of this shape object as an int (0 - 255).
   */
  int getBlue();


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
  void set(int x, int y, int width, int height, int red, int green, int blue);

  /**
   * Sets the values of this shape to those provided in the parameters (with doubles this time).
   *
   * @param x      new x value of the center of the shape
   * @param y      new y value of the center of the shape
   * @param width  new width value of the center of the shape
   * @param height new height value of the center of the shape
   * @param red    new red value of the center of the shape
   * @param green  new green value of the center of the shape
   * @param blue   new blue value of the center of the shape
   */
  void set(double x, double y, double width, double height, double red, double green, double blue);

  /**
   * Returns a string representation of this shape's fields (x, y, width, height, r, g, b).
   *
   * @return a string representation of this shape's fields (x, y, width, height, r, g, b)
   */
  String getShapeInfo();

  /**
   * Compares two objects - returns true if they represent the same shape.
   *
   * @param obj the object to compare this to
   * @return a boolean true if the objects represent the same shape, false otherwise
   */
  @Override
  boolean equals(Object obj);

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash
   * tables such as those provided by {@link HashMap}.
   *
   * @return a hash code value for this object.
   */
  int hashCode();

  /**
   * Returns a string representing what kind of shape this object is.
   *
   * @return a string representing what kind of shape this object is.
   */
  String getShapeName();

  /**
   * Returns a deep copy of this shape.
   *
   * @return a deep copy of this shape.
   */
  Shape copy();

  /**
   * Gets the minimum X value of this shape for the SVG offset.
   *
   * @return the minimum X value of this shape for the SVG offset.
   */
  int getMinXForSVG();

  /**
   * Gets the minumum Y value of this shape for the SVG offset.
   *
   * @return the minimum Y Value of this shape for the SVG offset.
   */

  int getMinYForSVG();

  /**
   * Gets the max X value of this shape to help determine width of the panel.
   *
   * @return the maximum X value of this shape.
   */
  int getMaxXForSVG();

  /**
   * Gets the max Y value of this shape to help determine height of the panel.
   *
   * @return the maximum Y value of this shape.
   */
  int getMaxYForSVG();
}
