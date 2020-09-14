package cs3500.animator.model.scene;

import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.shape.Shape;
import java.util.List;

/**
 * An interface to represent a scene. A scene is a set of animations (animations
 * are a specific shape (ex: "rectangle1") and a list of motions that the shape
 * goes through).
 */
public interface IScene {

  /**
   * Returns the animations in this scene.
   * @return the animations in this scene.
   */
  List<IAnimation> getAnimations();

  /**
   * Returns the animation with the given name.
   * @param name the name of the animation to find.
   * @return the animation with the given name.
   */
  IAnimation getAnimationWithName(String name);

  /**
   * Returns a string representation of this scene object, including a summary of all animations.
   * @return a string representation of this scene object
   */
  String toString();


  /**
   * Adds a given animation to this scene.
   * @param animation the animation to be added to this scene.
   * @throws IllegalArgumentException if an animation with the given name already exists.
   */
  void addAnimation(IAnimation animation) throws IllegalArgumentException;

  /**
   * Removes a given animation from this scene, given its name. If this scene has no animation
   * with the given name, do nothing.
   * @param name the name of the animation to be removed
   */
  void removeAnimation(String name);

  /**
   * Returns the minimum value of X in this scene.
   * @return the minimum value of X in this scene.
   */
  int getMinX();

  /**
   * Returns the minimum value of Y in this scene.
   * @return the minimum value of Y in this scene.
   */
  int getMinY();

  /**
   * Returns the maximum value of X in this scene.
   * @return the maximum value of X in this scene.
   */
  int getMaxX();

  /**
   * Returns the maximum value of X in this scene.
   * @return the maximum value of X in this scene.
   */
  int getMaxY();

  /**
   * Returns an SVG representation of this model.
   * @param width the width of the SVG's viewbox to create
   * @param height the height of the SVG's viewbox to create
   * @param tickRate the tickrate to make the SVG play at
   * @return
   */
  String toSVGString(int width, int height, int tickRate);

  /**
   * Updates the canvas values to be the smallest values of x and y present in list of motions
   * from any shape.
   */
  void updateCanvas();

  /**
   * Updates the canvas' bounds to the given parameters.
   * @param minX the x position of the top left corner of the canvas
   * @param minY the y position of the top left corner of the canvas
   * @param width the width of the canvas
   * @param height the height of the canvas
   */
  void updateCanvas(int minX, int minY, int width, int height);

  /**
   * Returns all shapes in this scene at the given tick.
   * @param tick the tick to get shapes at.
   * @return all shapes in this scene at the given tick.
   */
  List<Shape> getShapesAt(int tick);

  /**
   * Adds a keyframe to the animation with the given name at tick tick.
   * @param name the name of the animation to add to.
   * @param tick the tick to add a new keyframe at.
   */
  void addKeyframe(String name, int tick);

  /**
   * Removes a keyframe from the animation with the given name at tick tick.
   * @param name the name of the animation to remove a keyframe from.
   * @param tick the tick to remove motion at
   */
  void removeKeyframe(String name, int tick);

  /**
   * Edits a motion from the animation with the given name at tick tick.
   * @param name the name of the animation to edit a motion from.
   * @param tick the tick to edit motion at
   */
  void editKeyframe(String name, int tick, Shape shape);
}
