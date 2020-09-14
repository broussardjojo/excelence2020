package cs3500.animator.model.shape;

/**
 * A class to represent a rectangle by its center, color, and width/height.
 */
public class Rectangle extends AbstractShape {

  /**
   * Default constructor for a rectangle.
   */
  public Rectangle() {
    this(0, 0, 0, 0, 255, 255, 255);
  }


  /**
   * Constructor for a rectangle given all parameters.
   *
   * @param x      the x position of the center of the rectangle.
   * @param y      the y position of the center of the rectangle.
   * @param width  the width of the rectangle.
   * @param height the height of the rectangle.
   * @param red    the red value of the rectangle
   * @param green  the green value of the rectangle
   * @param blue   the blue value of the rectangle
   * @throws IllegalArgumentException if height or width are negative, or RGB values are invalid
   */
  public Rectangle(int x, int y, int width, int height, int red, int green, int blue)
      throws IllegalArgumentException {
    // Verify inputs
    if (height < 0 || width < 0) {
      throw new IllegalArgumentException("Dimensions cannot be negative");
    }

    if (red > 255 || red < 0 || green > 255 || green < 0 || blue > 255 || blue < 0) {
      throw new IllegalArgumentException("RGB values must be between 0 and 255");
    }

    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.red = red;
    this.blue = blue;
    this.green = green;
  }

  /**
   * Returns a String of the name of this type of shape (it's a rectangle).
   *
   * @return String "rectangle"
   */
  public String getShapeName() {
    return "rectangle";
  }

  /**
   * Return a deep copy of this rectangle object.
   *
   * @return a deep copy of this rectangle object
   */
  public Shape copy() {
    return new Rectangle(
        this.getX(),
        this.getY(),
        this.getWidth(),
        this.getHeight(),
        this.getRed(),
        this.getGreen(),
        this.getBlue());
  }

  /**
   * Gets the minimum X value of this shape for the SVG offset.
   *
   * @return the minimum X value of this shape for the SVG offset.
   */
  @Override
  public int getMinXForSVG() {
    return this.x;
  }

  /**
   * Gets the minumum Y value of this shape for the SVG offset.
   *
   * @return the minimum Y Value of this shape for the SVG offset.
   */
  @Override
  public int getMinYForSVG() {
    return this.y;
  }

  /**
   * Gets the max X value of this shape to help determine width of the panel.
   *
   * @return the maximum X value of this shape.
   */
  @Override
  public int getMaxXForSVG() {
    return this.x + this.width;
  }

  /**
   * Gets the max Y value of this shape to help determine height of the panel.
   *
   * @return the maximum Y value of this shape.
   */
  @Override
  public int getMaxYForSVG() {
    return this.y + this.height;
  }
}
