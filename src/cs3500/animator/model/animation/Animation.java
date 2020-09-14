package cs3500.animator.model.animation;

import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.motion.Motion;
import cs3500.animator.model.shape.Shape;
import cs3500.animator.model.StartTickSorter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class to represent an animation (series of motions) on a shape.
 */
public class Animation implements IAnimation {

  private Shape shape;
  private String name;
  private List<IMotion> motions;

  // INVARIANTS:
  // All motions in the list of motions are applied on the same type of shape
  // given by param shape. (verified in constructor)
  // motions will always be sorted by startTick of its composing motions.
  // There will never be overlapping motions in motions (verified in constructor and addMotion).

  /**
   * Constructor for an animation given all fields.
   *
   * @param shape   The shape for the series of motions to be applied to.
   * @param name    a name referring to the shape in this animation / the animation itself
   * @param motions A list of IMotions for the shape to be subjected to.
   */
  public Animation(Shape shape, String name, List<IMotion> motions) {
    this.shape = shape;
    this.name = name;
    // See if any of the motions cannot be applied to the given shape
    try {
      Shape copy = shape.copy();
      for (IMotion m : motions) {
        m.apply(copy);
      }
    } catch (IllegalArgumentException err) {
      throw new IllegalArgumentException(
          "One or more motions was unable to be applied to given shape.");
    }
    // Sort motions by startTick, then we check to see if there's any overlap
    motions.sort(new StartTickSorter());
    int lastEndTime = 0;
    for (IMotion m : motions) {
      if (m.getStartTick() < lastEndTime) {
        throw new IllegalArgumentException("Motions cannot overlap!");
      }
      lastEndTime = m.getEndTick();
    }

    this.motions = motions;
  }

  /**
   * Return a string representation of this animation, including the shape's name and all of its
   * motions.
   *
   * @return a string representation of this animation.
   */
  public String toString() {
    StringBuilder acc = new StringBuilder();
    Shape copyOfOriginal = this.shape.copy();
    acc.append("shape " + this.name + " " + this.shape.getShapeName() + '\n');
    for (int lcv = 0; lcv < motions.size(); lcv++) {
      acc.append(String
          .format("motion %5s %5d %20s", this.name, motions.get(lcv).getStartTick(),
              this.shape.getShapeInfo()));
      motions.get(lcv).apply(this.shape);
      acc.append(String.format(" %5d %20s \n", this.motions.get(lcv).getEndTick(),
          this.shape.getShapeInfo()));
    }
    this.shape.set(copyOfOriginal.getX(), copyOfOriginal.getY(), copyOfOriginal.getWidth(),
        copyOfOriginal.getHeight(), copyOfOriginal.getRed(), copyOfOriginal.getGreen(),
        copyOfOriginal.getBlue());
    return acc.toString();
  }

  /**
   * Return a deep copy of this animation.
   *
   * @return a deep copy of this animation.
   */
  @Override
  public IAnimation copy() {
    return new Animation(this.getShape(), this.getName(), this.getMotions());
  }

  /**
   * Gets the shape of this animation at the given tick.
   * @param tick the tick you want the state of the shape at.
   * @return the shape at the specified tick.
   */
  @Override
  public Shape getShapeAt(int tick) {
    // If no motions;
    if (this.motions.isEmpty()) {
      return this.shape;
    }

    // Check if less than start
    if (tick < motions.get(0).getStartTick()) {
      return this.shape;
    }

    // Check if greater than end
    if (tick >= motions.get(motions.size() - 1).getEndTick()) {
      return motions.get(motions.size() - 1).getEndShape();
    }

    // Check if inside first motion
    if (tick >= motions.get(0).getStartTick() && tick <= motions.get(0).getEndTick()) {
      return motions.get(0).getShapeAt(this.shape, tick);
    }

    // Check all other motions
    for (int lcv = 1; lcv < motions.size(); lcv++) {
      // Get shape if it's between motions - get end shape of last motion
      if (tick < motions.get(lcv).getStartTick()) {
        return motions.get(lcv - 1).getEndShape();
      }
      // Get shape if it's in this motion
      if (tick >= motions.get(lcv).getStartTick() && tick <= motions.get(lcv).getEndTick()) {
        return motions.get(lcv).getShapeAt(motions.get(lcv - 1).getEndShape(), tick);
      }
    }

    // To make java happie :)
    return motions.get(motions.size() - 1).getEndShape();
  }


  /**
   * Removes all motions ending at a given tick.
   * @param tick the tick to check :)
   */
  @Override
  public void removeKeyframeEndingAt(int tick) {
    int index = IAnimation.binSearchByEndTick(tick, this);
    if (index == -1) {
      throw new IllegalArgumentException("No motion ending at tick " + tick
          + " in " + this.getName());
    } else {
      // Make sure we didnt fuck up anything - we shouldn't see this one
      if (this.motions.get(index).getEndTick() != tick) {
        System.out.println("Hey - your indexing is not correct in your binarySearchByEndTick");
        return;
      }
      this.motions.remove(index);
    }
  }

  /**
   * Given a tick, if the tick is in the middle of a motion, splits the motion into two - the first
   * part from (motion.getStartTick()) to tick, then the second from tick -> motion.getEndTick.
   *
   * @param tick the tick to split at
   */
  @Override
  public void split(int tick) {
    if (tick < 0) {
      throw new IllegalArgumentException("Cannot split at negative tick");
    }
    int index = IAnimation.binSearchByEndTickWhereToInsert(tick, this);
    // CASE: MOTIONS IS EMPTY
    if (this.motions.isEmpty()) {
      this.motions.add(new Motion(tick, tick, this.shape.copy()));
      return;
    }

    try {
      if (this.motions.get(index).getStartTick() == tick) {
        throw new IllegalArgumentException("Can't add at an existing tick bro");
      }
    }
    catch (IndexOutOfBoundsException e) {
      if (this.motions.get(index - 1).getEndTick() == tick) {
        throw new IllegalArgumentException("Can't add at an existing tick bro (other side!)");
      }
    }
    // CASE: INSERTING AT THE START
    if (index == 0) {
      this.motions.add(0, new Motion(tick, tick, this.shape.copy()));
      this.motions.get(1).setStartTick(tick);
      return;
    }
    // CASE: INSERTING AT THE END
    if (index == this.motions.size()) {
      this.motions.add(new Motion(motions.get(index - 1).getEndTick(),
          tick, this.getShapeAt(tick)));
      return;
    }

    // CASE: ADDING A KEYFRAME AT A MOTIONS START TICK (or is a keyframe)
    if (tick == this.motions.get(index).getStartTick()
        || tick == this.motions.get(index).getEndTick()) {
      return;
    }
    // CASE: ADD A MOTION INSIDE OF A MOTION
    if (tick > this.motions.get(index).getStartTick()
        && tick < this.motions.get(index).getEndTick()) {
      Shape midpoint = this.getShapeAt(tick);
      // First interval
      int oldEndTick = this.motions.get(index).getEndTick();
      Shape oldShape = this.motions.get(index).getEndShape();
      this.motions.get(index).setEndTick(tick);
      this.motions.get(index).setEndShape(midpoint);
      // Second interval
      this.motions.add(index + 1, new Motion(tick, oldEndTick, oldShape));
    }
    // CASE: MOTION BETWEEN TWO MOTIONS (shouldnt happen w no gaps)
    else {
      this.motions.add(index, new Motion(tick, tick, this.getShapeAt(tick)));
    }
    return;
  }

  /**
   * Returns the keyframe at the current tick.
   * @param tick the tick to get a motion ending at.
   * @return the keyframe ending at the current tick.
   */
  @Override
  public IMotion getKeyframeEndingAt(int tick) {
    int index = IAnimation.binSearchByEndTick(tick, this);
    if (index == -1) {
      throw new IllegalArgumentException("No motion ending at tick " + tick + " and you should"
          + "have thrown an error before this. "
          + "Check methods that delegate to IAnimation.getMotionEndingAt.");
    } else {
      return this.motions.get(index);
    }
  }

  /**
   * Returns a copy of the shape being mutated.
   * @return a copy of the shape being mutated.
   */
  @Override
  public Shape getShape() {
    return this.shape.copy();
  }


  /**
   * Returns a copy of the list of motions that this shape goes through.
   *
   * @return a copy of the list of motions that this shape goes through.
   */
  @Override
  public List<IMotion> getMotions() {
    List<IMotion> newList = new ArrayList<IMotion>();
    for (IMotion m : this.motions) {
      newList.add(m.copy());
    }
    return newList;
  }

  /**
   * Returns the name given to the shape that will go through this series of motions.
   *
   * @return the name given to the shape that will go through this series of motions.
   */
  @Override
  public String getName() {
    return this.name;
  }


  /**
   * Overrides .equals method - returns a boolean if these animations are extensionally identical.
   *
   * @param obj The object that this animation is being compared to
   * @return boolean true if this animation equals obj, false o'wise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (this.getClass() != obj.getClass()) {
      return false;
    } else {
      IAnimation that = (IAnimation) obj;
      if (this.getShape().equals(that.getShape()) && this.getName().equals(that.getName())) {
        int motionCount = this.motions.size();
        for (int lcv = 0; lcv < motionCount; lcv++) {
          if (!this.getMotions().get(lcv).equals(that.getMotions().get(lcv))) {
            return false;
          }
        }
        return true;
      } else {
        return false;
      }
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
    return hashCode();
  }

  /**
   * Adds a motion to this animation's list of motions.
   *
   * @param motion the motion to be added
   * @throws IllegalArgumentException if the motion's time conflicts, or is for a different shape
   */
  @Override
  public void addMotion(IMotion motion) throws IllegalArgumentException {
    // See if the given motion is of the correct type to apply to the shape in this animation
    if (this.getShape().getShapeName() != motion.getEndShape().getShapeName()) {
      throw new IllegalArgumentException("Motion is inapplicable to the shape in this animation; "
          + "Trying to turn " + this.getShape().getShapeName() + " to " + motion.getEndShape()
          .getShapeName());
    }

    // We initially tried to use our static method in our interface for this; however, it took
    // WAY longer so we just moved this stuff here

    int low;
    int mid;
    int high;
    StartTickSorter comparator = new StartTickSorter();

    low = 0;
    high = this.motions.size();

    while (low < high) {
      mid = (low + high) / 2;
      IMotion middleElement = this.motions.get(mid);
      int compare = comparator.compare(middleElement, motion);

      // Check overlap
      if ((motion.getStartTick() < middleElement.getEndTick()
          && motion.getStartTick() > middleElement.getStartTick())
          || (motion.getEndTick() < middleElement.getEndTick()
          && motion.getEndTick() > middleElement.getStartTick())) {
        throw new IllegalArgumentException("Given motion overlaps with another motion");
      }

      if (compare > 0) {
        high = mid;
      } else {
        low = mid + 1;
      }
    }
    this.motions.add(low, motion);
  }

  /**
   * Removes the element in this animation's list of motions that is extensionally equal to the
   * provided param motion. If the given motion is not in the list of motions, do nothing.
   *
   * @param motion The motion to be removed from the list.
   */
  @Override
  public void removeMotion(IMotion motion) {
    int index = IAnimation.binSearchByEndTick(motion.getEndTick(), this);
    if (index == -1) {
      System.out.println("No motion ending at tick " + motion.getEndTick() + " and I think you "
          + "should have caught an error before this.");
    } else {
      this.motions.remove(index);
    }
  }

  /**
   * Edits the keyframe at the given tick.
   * @param tick the tick that the motion ends on
   * @param shape the shape to change endShape to
   */
  @Override
  public void editKeyframeEndingAt(int tick, Shape shape) {
    int index = IAnimation.binSearchByEndTick(tick, this);
    if (index == -1) {
      System.out.println("No motion ending at tick " + tick + " to edit and I think you "
          + "should have caught an error before this. [Animation.editKeyFrameEndingAt()]");
    } else {
      this.motions.get(index).setEndShape(shape);
    }
  }

  /**
   * Returns the smallest value of X present in the list of motions; if the list of motions is empty
   * or the smallest X value is that of the starting shape, then return the X value of the starting
   * shape.
   *
   * @return the smallest value of X present in the list of motions.
   */
  @Override
  public int minX() {
    int minX = this.shape.getMinXForSVG();
    for (IMotion m : this.motions) {
      int thisX = m.getEndShape().getMinXForSVG();
      if (thisX < minX) {
        minX = thisX;
      }
    }
    return minX;
  }

  /**
   * Returns the smallest value of Y present in the list of motions; if the list of motions is empty
   * or the smallest Y value is that of the starting shape, then return the Y value of the starting
   * shape.
   *
   * @return the smallest value of Y present in the list of motions.
   */
  @Override
  public int minY() {
    int minY = this.shape.getMinYForSVG();
    for (IMotion m : this.motions) {
      int thisY = m.getEndShape().getMinYForSVG();
      if (thisY < minY) {
        minY = thisY;
      }
    }
    return minY;
  }

  /**
   * Returns the largest value of X (for SVG purposes) present in the list of motions.
   *
   * @return the largest value of X (for SVG purposes) present in the list of motions.
   */
  @Override
  public int maxX() {
    int maxX = this.shape.getMaxXForSVG();
    for (IMotion m : this.motions) {
      int thisX = m.getEndShape().getMaxXForSVG();
      if (thisX > maxX) {
        maxX = thisX;
      }
    }
    return maxX;
  }

  /**
   * Returns the largest value of Y (for SVG purposes) present in the list of motions.
   *
   * @return the largest value of Y (for SVG purposes) present in the list of motions.
   */
  @Override
  public int maxY() {
    int maxY = this.shape.getMaxYForSVG();
    for (IMotion m : this.motions) {
      int thisY = m.getEndShape().getMaxYForSVG();
      if (thisY > maxY) {
        maxY = thisY;
      }
    }
    return maxY;
  }

  /**
   * Returns an SVG representation of this animation.
   *
   * @return an SVG representation of this animation.
   */
  @Override
  public String toSVGString(int tickRate) {
    String name = this.name;
    int x = this.shape.getX();
    int y = this.shape.getY();
    int width = this.shape.getWidth();
    int height = this.shape.getHeight();
    int red = this.shape.getRed();
    int green = this.shape.getGreen();
    int blue = this.shape.getBlue();

    StringBuilder acc = new StringBuilder();
    switch (this.shape.getShapeName()) {
      case "rectangle":
        acc.append(String.format(
            "<rect id=\"%s\" x=\"%d\" y=\"%d\" width=\"%d\" "
                + "height=\"%d\" fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" >\n",
            name, x, y, width, height, red, green, blue));
        toSVGStringHelper(tickRate, acc);
        acc.append("</rect> \n\n");
        return acc.toString();
      case "ellipse":
        acc.append(String.format(
            "<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" "
                + "ry=\"%d\" fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" > \n",
            name, x, y, width / 2, height / 2, red, green, blue));
        toSVGStringHelper(tickRate, acc);
        acc.append("</ellipse>\n\n");
        return acc.toString();
      default:
        // Don't do anything :)

    }

    return acc.toString();
  }

  /**
   * A helper method for toSVGString.
   * @param tickRate the tick rate to make motions go at in the svg view.
   * @param acc accumulator variable.
   */
  private void toSVGStringHelper(int tickRate, StringBuilder acc) {
    if (!this.motions.isEmpty()) {
      // First motion
      acc.append(this.motions.get(0).toSVGString(this.shape, tickRate));
      // Rest of the motions
      for (int lcv = 1; lcv < this.motions.size(); lcv++) {
        acc.append(this.motions.get(lcv)
            .toSVGString(this.motions.get(lcv - 1).getEndShape(), tickRate));
      }
    }
  }
}

