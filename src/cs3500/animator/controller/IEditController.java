package cs3500.animator.controller;

import cs3500.animator.model.scene.IEditableScene;
import cs3500.animator.view.IView;

/**
 * An interface to represent a controller for an animation that can be edited at runtime with
 * changes shown immediately.
 */
public interface IEditController extends IController {


  /**
   * Sets the view field to the given IView.
   *
   * @param view the given IView to set the view field to
   */
  void setView(IView view);

  /**
   * Sets the model to the given IEditableScene.
   *
   * @param model an IEditableScene that the model will be set to
   */
  void setModel(IEditableScene model);

  /**
   * Gets the tick rate of the view.
   *
   * @return an int representing the tick rate of the view
   */
  int getTickRate();

  /**
   * Sets the tick rate of the view.
   *
   * @param tickRate an int representing the tick rate of the view
   */
  void setTickRate(int tickRate);
}
