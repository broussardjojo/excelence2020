package cs3500.animator.model.shape;

/**
 * A class to represent an ellipse by its center, color, width, and height.
 */
public class Ellipse extends AbstractShape {

  /**
   * Default constructor for an ellipse.
   */
  public Ellipse() {
    this(0, 0, 0, 0, 255, 255, 255);
  }

  /**
   * Constructor for an ellipse given all parameters.
   *
   * @param x      the x position of the center of the ellipse
   * @param y      the y position of the center of the ellipse
   * @param width  the width of the ellipse
   * @param height the height of the ellipse
   * @param red    the red value of the ellipse
   * @param green  the green value of the ellipse
   * @param blue   the blue value of the ellipse
   * @throws IllegalArgumentException if height or width are negative, or RGB values are invalid
   */
  public Ellipse(int x, int y, int width, int height, int red, int green, int blue)
      throws IllegalArgumentException {
    // Input verification
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
   * Constructor for an ellipse given all parameters as doubles - used to prevent casting errors.
   *
   * @param x      the x position of the center of the ellipse
   * @param y      the y position of the center of the ellipse
   * @param height the height of the ellipse
   * @param width  the width of the ellipse
   * @param red    the red value of the ellipse
   * @param green  the green value of the ellipse
   * @param blue   the blue value of the ellipse
   * @throws IllegalArgumentException if height or width are negative, or RGB values are invalid
   */
  public Ellipse(double x, double y, double height, double width, double red, double green,
      double blue) throws IllegalArgumentException {
    // Input verification
    if (height < 0 || width < 0) {
      throw new IllegalArgumentException("Dimensions cannot be negative");
    }

    if (red > 255 || red < 0 || green > 255 || green < 0 || blue > 255 || blue < 0) {
      throw new IllegalArgumentException("RGB values must be between 0 and 255");
    }

    this.x = (int) x;
    this.y = (int) y;
    this.height = (int) height;
    this.width = (int) width;
    this.red = (int) red;
    this.blue = (int) blue;
    this.green = (int) green;
  }

  /**
   * Returns a String of the name of this type of shape (it's an ellipse).
   *
   * @return String "ellipse"
   */
  public String getShapeName() {
    return "ellipse";
  }

  /**
   * Return a deep copy of this ellipse object.
   *
   * @return a deep copy of this ellipse object
   */
  public Shape copy() {
    return new Ellipse(this.getX(), this.getY(), this.getWidth(), this.getHeight(),
        this.getRed(), this.getGreen(), this.getBlue());
  }

  /**
   * Gets the minimum X value of this shape for the SVG offset.
   *
   * @return the minimum X value of this shape for the SVG offset.
   */
  @Override
  public int getMinXForSVG() {
    return this.x - (this.width / 2);
  }

  /**
   * Gets the minumum Y value of this shape for the SVG offset.
   *
   * @return the minimum Y Value of this shape for the SVG offset.
   */
  @Override
  public int getMinYForSVG() {
    return this.y - (this.height / 2);
  }

  /**
   * Gets the max X value of this shape to help determine width of the panel.
   *
   * @return the maximum X value of this shape.
   */
  @Override
  public int getMaxXForSVG() {
    return this.x + (this.width / 2);
  }

  /**
   * Gets the max Y value of this shape to help determine height of the panel.
   *
   * @return the maximum Y value of this shape.
   */
  @Override
  public int getMaxYForSVG() {
    return this.y + (this.height / 2);
  }
}