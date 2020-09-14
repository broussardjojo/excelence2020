package cs3500.animator.model.motion;

import cs3500.animator.model.shape.Shape;

/**
 * Represents a change in a shape's position, width/height, and/or color.
 */
public class Motion implements IMotion {

  private int startTick;
  private int endTick;
  private Shape endShape;

  // INVARIANTS:
  // No ticks can be negative, and endTick will never be less than startTick
  // (verified in constructor).
  // A motion of a different endShape cannot be applied to a shape (verified in apply).


  /**
   * A constructor for a motion to mutate a shape object.
   *
   * @param startTick the tick to start the motion on.
   * @param endTick   the tick to finish the motion on.
   * @param endShape  the shape to be mutated into.
   * @throws IllegalArgumentException if
   */
  public Motion(int startTick, int endTick, Shape endShape) throws IllegalArgumentException {
    if (startTick < 0 || endTick < 0) {
      throw new IllegalArgumentException("Tick values cannot be negative.");
    }
    if (startTick > endTick) {
      throw new IllegalArgumentException("Start tick must be less than end tick");
    }
    this.startTick = startTick;
    this.endTick = endTick;
    this.endShape = endShape;
  }

  /**
   * Returns the start tick of this motion.
   *
   * @return the start tick of this motion.
   */
  @Override
  public int getStartTick() {
    return this.startTick;
  }

  /**
   * Returns the end tick of this motion.
   *
   * @return the end tick of this motion.
   */
  @Override
  public int getEndTick() {
    return this.endTick;
  }

  /**
   * Returns the shape at the given tick, midway through its transformation.
   *
   * @param shape the start shape
   * @param tick  the tick you want the shape of at
   * @return the shape at the given tick
   * @throws IllegalArgumentException if given tick is not in this motion
   */
  public Shape getShapeAt(Shape shape, int tick) throws IllegalArgumentException {
    if (tick < this.startTick || tick > this.endTick) {
      throw new IllegalArgumentException("Invalid tick provided :(");
    }

    Shape copy = shape.copy();

    int ticks = endTick - startTick;
    if (ticks == 0) {
      copy.set(this.endShape.getX(), this.endShape.getY(), this.endShape.getWidth(),
          this.endShape.getHeight(), this.endShape.getRed(), this.endShape.getGreen(),
          this.endShape.getBlue());
      return copy;
    }
    int partialTicks = tick - startTick;

    double deltaX = ((endShape.getX() - shape.getX()) / (double) ticks) * partialTicks;
    double deltaY = ((endShape.getY() - shape.getY()) / (double) ticks) * partialTicks;
    double deltaWidth = ((endShape.getWidth() - shape.getWidth()) / (double) ticks) * partialTicks;
    double deltaHeight = ((endShape.getHeight() - shape.getHeight()) / (double) ticks)
        * partialTicks;
    double deltaRed = ((endShape.getRed() - shape.getRed()) / (double) ticks) * partialTicks;
    double deltaGreen = ((endShape.getGreen() - shape.getGreen()) / (double) ticks) * partialTicks;
    double deltaBlue = ((endShape.getBlue() - shape.getBlue()) / (double) ticks) * partialTicks;

    copy.set(shape.getX() + deltaX, shape.getY() + deltaY, shape.getWidth() + deltaWidth,
        shape.getHeight() + deltaHeight, shape.getRed() + deltaRed,
        shape.getGreen() + deltaGreen, shape.getBlue() + deltaBlue);
    return copy;
  }


  /**
   * Applies this motion to the given shape object, mutating it.
   *
   * @param shape the shape to be mutated with this motion.
   * @throws IllegalArgumentException if the shapes are not of the same type.
   */
  public void apply(Shape shape) throws IllegalArgumentException {
    if (shape.getClass() != endShape.getClass()) {
      throw new IllegalArgumentException("Start and end shape must be of the same type");
    }

    // Local variables to control gradual change of the shape
    int ticks = endTick - startTick;
    if (ticks == 0) {
      shape.set(this.endShape.getX(), this.endShape.getY(), this.endShape.getWidth(),
          this.endShape.getHeight(), this.endShape.getRed(), this.endShape.getGreen(),
          this.endShape.getBlue());
      return;
    }
    double deltaX = (endShape.getX() - shape.getX()) / (double) ticks;
    double deltaY = (endShape.getY() - shape.getY()) / (double) ticks;
    double deltaWidth = (endShape.getWidth() - shape.getWidth()) / (double) ticks;
    double deltaHeight = (endShape.getHeight() - shape.getHeight()) / (double) ticks;
    double deltaRed = (endShape.getRed() - shape.getRed()) / (double) ticks;
    double deltaGreen = (endShape.getGreen() - shape.getGreen()) / (double) ticks;
    double deltaBlue = (endShape.getBlue() - shape.getBlue()) / (double) ticks;

    double thisX = shape.getX();
    double thisY = shape.getY();
    double thisWidth = shape.getWidth();
    double thisHeight = shape.getHeight();
    double thisRed = shape.getRed();
    double thisGreen = shape.getGreen();
    double thisBlue = shape.getBlue();

    for (int lcv = 0; lcv < ticks; lcv++) {
      thisX += deltaX;
      thisY += deltaY;
      thisWidth += deltaWidth;
      thisHeight += deltaHeight;
      thisRed += deltaRed;
      thisGreen += deltaGreen;
      thisBlue += deltaBlue;
      shape.set(Math.round(thisX), Math.round(thisY), Math.round(thisWidth), Math.round(thisHeight),
          Math.round(thisRed), Math.round(thisGreen), Math.round(thisBlue));
    }
  }

  /**
   * Returns a deep copy of the shape mutated into.
   *
   * @return a deep copy of the shape mutated into.
   */
  public Shape getEndShape() {
    return this.endShape.copy();
  }

  /**
   * Return a deep copy of this motion.
   *
   * @return a deep copy of this motion.
   */
  @Override
  public IMotion copy() {
    return new Motion(this.startTick, this.endTick, this.endShape.copy());
  }

  /**
   * Returns an SVG representation of this motion.
   *
   * @param startShape the startShape of this motion
   * @param tickRate   the speed to represent this motion working at
   * @return an SVG representation of this motion.
   */
  @Override
  public String toSVGString(Shape startShape, int tickRate) {
    StringBuilder acc = new StringBuilder();
    int deltaX = this.endShape.getX() - startShape.getX();
    int deltaY = this.endShape.getY() - startShape.getY();
    int deltaWidth = this.endShape.getWidth() - startShape.getWidth();
    int deltaHeight = this.endShape.getHeight() - startShape.getHeight();
    int deltaRed = this.endShape.getRed() - startShape.getRed();
    int deltaGreen = this.endShape.getGreen() - startShape.getGreen();
    int deltaBlue = this.endShape.getBlue() - startShape.getBlue();
    String template = new String();

    int ticks = this.endTick - this.startTick;

    // If ticks = 0, we have a keyframe and we want the shape to change instantly
    int durationTickRate = tickRate;

    if (ticks == 0) {
      durationTickRate = 999;
    }

    if (ticks < 1) {
      ticks = 1;
    }

    switch (this.endShape.getShapeName()) {
      case "ellipse":
        template = "\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
            + "attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\" /> \n";
        if (deltaX != 0) {
          acc.append(String.format(template, (this.startTick * 1000 / tickRate),
              (ticks * 1000 / durationTickRate),
              "cx", startShape.getX(), this.endShape.getX()));
        }
        if (deltaY != 0) {
          acc.append(String.format(template, (this.startTick * 1000 / tickRate),
              (ticks * 1000 / durationTickRate),
              "cy", startShape.getY(), this.endShape.getY()));
        }
        if (deltaWidth != 0) {
          acc.append(String.format(template, (this.startTick * 1000 / tickRate),
              (ticks * 1000 / durationTickRate),
              "rx", startShape.getWidth() / 2, this.endShape.getWidth() / 2));
        }
        if (deltaHeight != 0) {
          acc.append(String.format(template, (this.startTick * 1000 / tickRate),
              (ticks * 1000 / durationTickRate),
              "ry", startShape.getHeight() / 2, this.endShape.getHeight() / 2));
        }
        if (deltaRed != 0 || deltaGreen != 0 || deltaBlue != 0) {
          acc.append(String.format("\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                  + "attributeName=\"fill\" from=\"rgb(%d,%d,%d)\""
                  + " to=\"rgb(%d,%d,%d)\" fill=\"freeze\" /> \n",
              this.startTick * 1000 / tickRate,
              (ticks * 1000 / durationTickRate) + 1,
              startShape.getRed(), startShape.getGreen(), startShape.getBlue(),
              this.endShape.getRed(), this.endShape.getGreen(), this.endShape.getBlue()));
        }
        break;
      case "rectangle":
        template = "\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
            + "attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\" /> \n";
        if (deltaX != 0) {
          acc.append(String.format(template, (this.startTick * 1000 / tickRate),
              (ticks * 1000 / durationTickRate) + 1,
              "x", startShape.getX(), this.endShape.getX()));
        }
        if (deltaY != 0) {
          acc.append(String.format(template, (this.startTick * 1000 / tickRate),
              (ticks * 1000 / durationTickRate) + 1,
              "y", startShape.getY(), this.endShape.getY()));
        }
        if (deltaWidth != 0) {
          acc.append(String.format(template, (this.startTick * 1000 / tickRate),
              (ticks * 1000 / durationTickRate) + 1,
              "width", startShape.getWidth(), this.endShape.getWidth()));
        }
        if (deltaHeight != 0) {
          acc.append(String.format(template, (this.startTick * 1000 / tickRate),
              (ticks * 1000 / durationTickRate),
              "height", startShape.getHeight(), this.endShape.getHeight()));
        }
        if (deltaRed != 0 || deltaGreen != 0 || deltaBlue != 0) {
          acc.append(String.format("\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                  + "attributeName=\"fill\" from=\"rgb(%d,%d,%d)\" "
                  + "to=\"rgb(%d,%d,%d)\" fill=\"freeze\" /> \n",
              this.startTick * 1000 / tickRate,
              (ticks * 1000 / durationTickRate),
              startShape.getRed(), startShape.getGreen(), startShape.getBlue(),
              this.endShape.getRed(), this.endShape.getGreen(), this.endShape.getBlue()));
        }
        break;
      default:
        // Do nothing
    }
    return acc.toString();
  }

  /**
   * Sets the start tick of this motion.
   *
   * @param tick the new start tick of this motion.
   */
  @Override
  public void setStartTick(int tick) {
    if (tick < 0) {
      throw new IllegalArgumentException("Tick cannot be negative");
    }
    if (tick > this.getEndTick()) {
      throw new IllegalArgumentException("Start tick cannot be greater than end tick");
    }
    this.startTick = tick;
  }


  /**
   * Sets the end tick of this motion.
   *
   * @param tick the new end tick of this motion.
   */
  @Override
  public void setEndTick(int tick) {
    if (tick < 0) {
      throw new IllegalArgumentException("Tick cannot be negative");
    }
    if (tick < this.getStartTick()) {
      throw new IllegalArgumentException("Start tick cannot be greater than end tick");
    }
    this.endTick = tick;
  }

  /**
   * Sets the end shape of this motion.
   *
   * @param shape the new endShape of this motion.
   */
  @Override
  public void setEndShape(Shape shape) {
    this.endShape = shape;
  }

  /**
   * Overrides .equals method - returns a boolean if these motions are extensionally identical.
   *
   * @param obj The object that this motion is being compared to
   * @return boolean true if this motion equals obj, false o'wise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (this.getClass() != obj.getClass()) {
      return false;
    } else {
      IMotion that = (IMotion) obj;
      return (
          this.getStartTick() == that.getStartTick()
              && this.getEndTick() == that.getEndTick()
              && this.getEndShape().equals(that.getEndShape()));
    }
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return this.endShape.toString().hashCode();
  }


}
