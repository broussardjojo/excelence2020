package cs3500.animator.model.animation;

import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.shape.Shape;
import java.util.HashMap;
import java.util.List;

/**
 * An interface to represent an animation (series of motions) of a shape.
 */
public interface IAnimation {

  /**
   * Returns a copy of the shape being mutated.
   *
   * @return a copy of the shape being mutated.
   */
  Shape getShape();


  /**
   * Returns a copy of the list of motions that this shape goes through.
   *
   * @return a copy of the list of motions that this shape goes through.
   */
  List<IMotion> getMotions();

  /**
   * Returns the name given to the shape that will go through this series of motions.
   *
   * @return the name given to the shape that will go through this series of motions.
   */
  String getName();

  /**
   * Return a string representation of this animation, including the shape's name and all of its
   * motions.
   *
   * @return a string representation of this animation.
   */
  @Override
  String toString();

  /**
   * Return a deep copy of this animation.
   *
   * @return a deep copy of this animation.
   */
  IAnimation copy();

  /**
   * Overrides .equals method - returns a boolean if these animations are extensionally identical.
   *
   * @param obj The object that this animation is being compared to
   * @return boolean true if this animation equals obj, false o'wise
   */
  boolean equals(Object obj);

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash
   * tables such as those provided by {@link HashMap}.
   *
   * @return a hash code value for this object.
   */
  @Override
  int hashCode();

  /**
   * Adds a motion to this animation's list of motions.
   *
   * @param motion the motion to be added
   * @throws IllegalArgumentException if the motion's time conflicts, or is for a different shape
   */
  void addMotion(IMotion motion) throws IllegalArgumentException;

  /**
   * Removes the element in this animation's list of motions that is extensionally equal to the
   * provided param motion.
   *
   * @param motion The motion to be removed from the list.
   */
  void removeMotion(IMotion motion);

  /**
   * Returns the smallest value of X (for SVG Purposes) present in the list of motions.
   *
   * @return the smallest value of X (for SVG Purposes) present in the list of motions.
   */
  int minX();

  /**
   * Returns the smallest value of Y (for SVG purposes) present in the list of motions.
   *
   * @return the smallest value of Y (for SVG purposes) present in the list of motions.
   */
  int minY();

  /**
   * Returns the largest value of X (for SVG purposes) present in the list of motions.
   *
   * @return the largest value of X (for SVG purposes) present in the list of motions.
   */
  int maxX();

  /**
   * Returns the largest value of Y (for SVG purposes) present in the list of motions.
   *
   * @return the largest value of Y (for SVG purposes) present in the list of motions.
   */
  int maxY();

  /**
   * Returns an SVG representation of this animation.
   *
   * @return an SVG representation of this animation.
   */
  String toSVGString(int tickRate);


  /**
   * Gets the state of the shape at the given tick.
   *
   * @param tick the tick you want the state of the shape at.
   * @return
   */
  Shape getShapeAt(int tick);

  /**
   * Returns the motion ending at the tick.
   *
   * @param tick the tick to get a motion ending at.
   * @return a motion ending at this tick
   */
  IMotion getKeyframeEndingAt(int tick);

  /**
   * Removes the motion ending at the given tick from this animation.
   *
   * @param tick the tick to remove a motion ending at.
   */
  void removeKeyframeEndingAt(int tick);

  /**
   * Changes the motion's endshape for the motion ending at the given tick.
   *
   * @param tick  the tick that the motion ends on
   * @param shape the shape to change endShape to
   */
  void editKeyframeEndingAt(int tick, Shape shape);


  /**
   * Splits one motion into two - used to add keyframes.
   *
   * @param tick the tick to add a keyframe at.
   */
  void split(int tick);

  /**
   * Returns the index of the motion in the list of motions that this tick is in.
   *
   * @return
   */
  static int binSearchByStartTick(int tick, IAnimation implementer) {
    int low;
    int mid;
    int high;

    low = 0;
    high = implementer.getMotions().size();

    while (low < high) {
      mid = (low + high) / 2;
      IMotion middleElement = implementer.getMotions().get(mid);
      int compare = middleElement.getStartTick() - tick;

      if (compare > 0) {
        high = mid;
      } else {
        low = mid + 1;
      }
    }
    return low;
  }

  /**
   * Finds the index of the motion that ends on tick tick; returns -1 if no motion exists.
   *
   * @param tick the end tick of the motion to return the index of
   * @return the index of the motion that ends at tick tick.
   */
  static int binSearchByEndTick(int tick, IAnimation implementer) {
    int low;
    int mid;
    int high;

    low = 0;
    high = implementer.getMotions().size();

    while (low < high) {
      mid = (low + high) / 2;
      IMotion middleElement = implementer.getMotions().get(mid);
      int compare = middleElement.getEndTick() - tick;
      if (compare == 0) {
        return mid;
      }

      if (compare > 0) {
        high = mid;
      } else {
        low = mid + 1;
      }
    }
    return -1;
  }

  /**
   * Returns the index of the motion in the list of motions that this tick is in.
   *
   * @return
   */
  static int binSearchByEndTickWhereToInsert(int tick, IAnimation implementer) {
    int low;
    int mid;
    int high;

    low = 0;
    high = implementer.getMotions().size();

    while (low < high) {
      mid = (low + high) / 2;
      IMotion middleElement = implementer.getMotions().get(mid);
      int compare = middleElement.getEndTick() - tick;

      if (compare > 0) {
        high = mid;
      } else {
        low = mid + 1;
      }
    }
    return low;
  }
}
