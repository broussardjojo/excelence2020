package cs3500.animator.model.motion;

import cs3500.animator.model.shape.Shape;
import java.util.HashMap;

/**
 * An interface to represent a simple motion of a shape (a change in x, y, width, height, r, g,
 * and/or b).
 */
public interface IMotion {


  /**
   * Applies this motion to a shape, mutating it.
   *
   * @param shape the shape to be mutated with this motion.
   */
  void apply(Shape shape);

  /**
   * Returns the start tick of this motion.
   *
   * @return the start tick of this motion.
   */
  int getStartTick();

  /**
   * Returns the end tick of this motion.
   *
   * @return the end tick of this motion.
   */
  int getEndTick();

  /**
   * Returns a deep copy of the end shape that is being mutated into.
   *
   * @return a deep copy of the end shape that is being mutated into.
   */
  Shape getEndShape();

  /**
   * Return a deep copy of this motion.
   *
   * @return a deep copy of this motion.
   */
  IMotion copy();

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash
   * tables such as those provided by {@link HashMap}.
   *
   * @return a hash code value for this object.
   */
  @Override
  int hashCode();

  /**
   * Overrides .equals method - returns a boolean if these motions are extensionally identical.
   *
   * @param obj The object that this motion is being compared to
   * @return boolean true if this motion equals obj, false o'wise
   */
  @Override
  boolean equals(Object obj);

  /**
   * Returns the shape at the given tick in this motion.
   *
   * @param shape The start shape in this motion.
   * @param tick  The tick to get the shape at.
   * @return the state of the shape at the given tick.
   * @throws IllegalArgumentException if the tick is not inside this motion.
   */
  Shape getShapeAt(Shape shape, int tick) throws IllegalArgumentException;

  /**
   * Returns an SVG representation of this motion.
   *
   * @param startShape the startShape of this motion
   * @param tickRate   the speed to represent this motion working at
   * @return an SVG representation of this motion.
   */
  String toSVGString(Shape startShape, int tickRate);

  /**
   * Sets the start tick of this motion.
   *
   * @param tick the new start tick of this motion.
   */
  void setStartTick(int tick);

  /**
   * Sets the end tick of this motion.
   *
   * @param tick the new end tick of this motion.
   */
  void setEndTick(int tick);

  /**
   * Sets the end shape of this motion.
   *
   * @param shape the new endShape of this motion.
   */
  void setEndShape(Shape shape);
}
