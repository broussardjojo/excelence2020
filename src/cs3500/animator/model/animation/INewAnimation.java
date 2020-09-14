package cs3500.animator.model.animation;

import cs3500.animator.model.shape.Shape;

/**
 * An interface to represent a set of motions on a shape as keyframes.
 */
public interface INewAnimation extends IAnimation {

  /**
   * Adds a keyframe at the given tick.
   * @param tick the tick to add a keyframe at.
   */
  void addKeyFrame(int tick);

  /**
   * Removes a keyframe occurring at the given tick.
   * If there is no keyframe at this tick, do nothing.
   * @param tick the tick of the keyframe to remove.
   */
  void removeKeyFrame(int tick);

  /**
   * Edits a keyframe occurring at the given tick.
   * @param tick the tick of the keyframe to edit
   * @param shape the shape to change the keyframe to
   */
  void editKeyFrame(int tick, Shape shape);
}