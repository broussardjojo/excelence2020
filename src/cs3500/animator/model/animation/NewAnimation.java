package cs3500.animator.model.animation;

import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.motion.Motion;
import cs3500.animator.model.shape.Shape;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent an animation as a set of keyframes. Serves mostly as a mapping of Animation
 * class to a keyframe based representation, and delegates all other functionality.
 */
public class NewAnimation implements INewAnimation {

  private Shape shape;
  private String name;
  private List<IMotion> motions;
  // Motions is now a list of keyFrames
  // A keyFrame is a motion that has the same start and end tick

  /**
   * Constructor for a newAnimation - takes in an IAnimation delegate and maps it to keyframe
   * representation.
   *
   * @param delegate the IAnimation to convert
   */
  public NewAnimation(IAnimation delegate) {
    this.shape = delegate.getShape();
    this.name = delegate.getName();
    this.motions = new ArrayList<>();
    if (delegate.getMotions().isEmpty()) {
      // Do nothing
    } else {
      this.motions.add(new Motion(delegate.getMotions().get(0).getStartTick(),
          delegate.getMotions().get(0).getStartTick(),
          delegate.getShape()));
      for (IMotion m : delegate.getMotions()) {
        this.motions.add(new Motion(m.getEndTick(), m.getEndTick(), m.getEndShape()));
      }
    }
  }

  /**
   * Returns a copy of the shape being mutated.
   *
   * @return a copy of the shape being mutated.
   */
  @Override
  public Shape getShape() {
    return null;
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
    if (!newList.isEmpty()) {
      newList.remove(0);
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
   * Return a deep copy of this animation.
   *
   * @return a deep copy of this animation.
   */
  @Override
  public IAnimation copy() {
    return null;
  }

  @Override
  public String toString() {
    StringBuilder acc = new StringBuilder();
    acc.append("shape " + this.name + " " + this.shape.getShapeName() + '\n');
    for (IMotion m : this.motions) {
      acc.append(String
          .format("keyframe %5s %5d %20s \n",
              this.name, m.getEndTick(), m.getEndShape().getShapeInfo()));
    }
    return acc.toString();
  }


  /**
   * Adds a motion to this animation's list of motions.
   *
   * @param motion the motion to be added
   * @throws IllegalArgumentException if the motion's time conflicts, or is for a different shape
   */
  @Override
  public void addMotion(IMotion motion) throws IllegalArgumentException {
    // delegated elsewhere
  }

  /**
   * Adds a keyframe to this motion at the given tick. If the tick is between two keyframes, it
   * automatically tweens itself to have the shape that it would be at that tick. If before all
   * keyframes, it initializes itself to copy the keyFrame that was previously the first in the list
   * and if it is after all keyframes, it initializes itself to copy the previous final keyframe.
   *
   * @param tick the tick to add the keyframe at
   * @throws IllegalArgumentException if a keyframe already exists at the given tick.
   */
  public void addKeyFrame(int tick) throws IllegalArgumentException {
    int low;
    int mid;
    int high;

    low = 0;
    high = this.motions.size();

    while (low < high) {
      mid = (low + high) / 2;
      IMotion middleElement = this.motions.get(mid);
      int compare = middleElement.getStartTick() - tick;

      if (compare > 0) {
        high = mid;
      } else if (compare == 0) {
        throw new IllegalArgumentException("Keyframe already exists at tick " + tick);
      } else {
        low = mid + 1;
      }
    }

    try {
      this.motions.add(low, new Motion(tick, tick, this.getShapeAt(tick)));
    }
    // In the case you're inserting a keyframe before any other keyframe for the animation -
    // initialize it to the first shape
    catch (IllegalArgumentException e) {
      this.motions.add(low, new Motion(tick, tick, this.motions.get(0).getEndShape()));
    }
  }

  /**
   * Removes the keyframe at the given tick from this animation. If no keyframe exists at this time,
   * do nothing.
   *
   * @param tick the tick of the keyframe to remove
   */
  public void removeKeyFrame(int tick) {
    int low;
    int mid;
    int high;

    low = 0;
    high = this.motions.size();

    while (low < high) {
      mid = (low + high) / 2;
      IMotion middleElement = this.motions.get(mid);
      int compare = middleElement.getStartTick() - tick;

      if (compare > 0) {
        high = mid;
      } else if (compare == 0) {
        this.motions.remove(mid);
        return;
      } else {
        low = mid + 1;
      }
    }
  }

  /**
   * Sets the keyFrame at the given tick to be this tick.
   *
   * @param tick  the tick of the keyframe to edit
   * @param shape the shape to set the keyframe to be
   * @throws IllegalArgumentException if there is no keyframe at the given tick, or given shape is
   *                                  of a different type than the animation's shape.
   */
  public void editKeyFrame(int tick, Shape shape) throws IllegalArgumentException {
    // Delegated elsewhere
  }


  /**
   * Removes the element in this animation's list of motions that is extensionally equal to the
   * provided param motion.
   *
   * @param motion The motion to be removed from the list.
   */
  @Override
  public void removeMotion(IMotion motion) {
    // Delegated elsewhere
  }

  /**
   * Returns the smallest value of X (for SVG Purposes) present in the list of motions.
   *
   * @return the smallest value of X (for SVG Purposes) present in the list of motions.
   */
  @Override
  public int minX() {
    return 0;
  }

  /**
   * Returns the smallest value of Y (for SVG purposes) present in the list of motions.
   *
   * @return the smallest value of Y (for SVG purposes) present in the list of motions.
   */
  @Override
  public int minY() {
    return 0;
  }

  /**
   * Returns the largest value of X (for SVG purposes) present in the list of motions.
   *
   * @return the largest value of X (for SVG purposes) present in the list of motions.
   */
  @Override
  public int maxX() {
    return 0;
  }

  /**
   * Returns the largest value of Y (for SVG purposes) present in the list of motions.
   *
   * @return the largest value of Y (for SVG purposes) present in the list of motions.
   */
  @Override
  public int maxY() {
    return 0;
  }

  /**
   * Returns an SVG representation of this animation.
   *
   * @param tickRate the tickrate for the svgstring to be represented in
   * @return an SVG representation of this animation.
   */
  @Override
  public String toSVGString(int tickRate) {
    return null;
  }

  /**
   * gets the shape at the given tick.
   *
   * @param tick the tick you want the state of the shape at.
   * @return the shape at its state at the given tick.
   */
  @Override
  public Shape getShapeAt(int tick) {
    // Some kind of binary search by start tick (the only tick)
    // Return the index of the lower field
    // see the shapes it's between

    int low;
    int mid;
    int high;

    low = 0;
    high = this.motions.size();

    while (low < high) {
      mid = (low + high) / 2;
      IMotion middleElement = this.motions.get(mid);
      int compare = middleElement.getStartTick() - tick;

      if (compare > 0) {
        high = mid;
      } else if (compare == 0) {
        return middleElement.getEndShape();
      } else {
        low = mid + 1;
      }
    }
    low--;
    if (low == -1) {
      throw new IllegalArgumentException("Given tick before shape was instantiated");
    }
    // Low is now the index of the shape before, or in the case of being given exact tick, the shape

    // Here we know it's not on an exact tick
    try {
      Shape thisShape = this.motions.get(low).getEndShape();
      int firstTick = this.motions.get(low).getStartTick();

      Shape nextShape = this.motions.get(low + 1).getEndShape();
      int nextTick = this.motions.get(low + 1).getStartTick();

      double percentTransformed = (tick - firstTick) / (double) (nextTick - firstTick);
      int deltaX = nextShape.getX() - thisShape.getX();
      int deltaY = nextShape.getY() - thisShape.getY();
      int deltaW = nextShape.getWidth() - thisShape.getWidth();
      int deltaH = nextShape.getHeight() - thisShape.getHeight();
      int deltaR = nextShape.getRed() - thisShape.getRed();
      int deltaG = nextShape.getGreen() - thisShape.getGreen();
      int deltaB = nextShape.getBlue() - thisShape.getBlue();

      Shape copy = thisShape.copy();
      copy.set(
          copy.getX() + (percentTransformed * deltaX),
          copy.getY() + (percentTransformed * deltaY),
          copy.getWidth() + (percentTransformed * deltaW),
          copy.getHeight() + (percentTransformed * deltaH),
          copy.getRed() + (percentTransformed * deltaR),
          copy.getGreen() + (percentTransformed * deltaG),
          copy.getBlue() + (percentTransformed * deltaB));
      return copy;
    } catch (IndexOutOfBoundsException e) { // If we get the final
      // motion in the animation with search
      return this.motions.get(low).getEndShape();
    }
  }

  /**
   * Removes the keyframe at the given tick.
   *
   * @param tick the tick to remove a motion ending at.
   */
  @Override
  public void removeKeyframeEndingAt(int tick) {
    this.motions.remove(this.getKeyframeEndingAt(tick));
  }

  /**
   * Edits the keyframe at the given tick.
   *
   * @param tick  the tick that the motion ends on
   * @param shape the shape to change endShape to
   */
  @Override
  public void editKeyframeEndingAt(int tick, Shape shape) {
    // Delegated elsewhere
  }

  /**
   * Splits the moition. doesnt do anythjing here.
   *
   * @param tick the tick to add a keyframe at.
   */
  @Override
  public void split(int tick) {
    // Delegated elsewhere
  }

  /**
   * gets keyframe at the given tick.
   *
   * @param tick the tick to get a motion ending at.
   * @return keyframe at given tick.
   */
  @Override
  public IMotion getKeyframeEndingAt(int tick) {
    int index = IAnimation.binSearchByEndTick(tick, this);
    if (index == -1) {
      throw new IllegalArgumentException("There is no motion ending at tick " + tick
          + " and you should have probably caught this error before you got to "
          + "NewAnimation.getMotionEndingAt");
    } else {
      return this.motions.get(index);
    }
  }

}
