package cs3500.animator.controller;

import cs3500.animator.model.scene.IScene;
import cs3500.animator.view.IView;

/**
 * An interface to represent a controller of an animation, to mediate between its
 * view and its model.
 */
public interface IController {

  /**
   * Plays the animation in a certain format, depending on what type of view it takes in.
   * For example, it prints to console if the view is an SVGView or a TextView. If the view
   * is Visual, then it plays the animation in a separate window.
   * @param view the view of the animation
   */
  void playAnimation(IView view);


  /**
   * Prints the view to the out file given.
   * @param view The view of the animation
   * @param out the file to print to
   */
  void makeFile(IView view, String out);


  /**
   * Sets the model to the given IScene.
   * @param scene the IScene to set the model to
   */
  void setModel(IScene scene);
}
