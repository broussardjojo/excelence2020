package cs3500.animator.controller;

import cs3500.animator.model.scene.IScene;
import cs3500.animator.view.IView;
import cs3500.animator.view.VisualView;
import java.io.IOException;

/**
 * This class represents an implementation of the IController that works for the Visual, SVG,
 * and Text Views. It is a basic version of the controller, with methods to play the animation
 * or make a file.
 */
public class BasicController implements IController {


  // Fields
  private IScene scene;

  /**
   * Constructs the BasicController with a null scene and view.
   */
  public BasicController() {
    this.scene = null;
  }

  /**
   * Sets the model for this controller to display.
   * @param scene the IScene to set the model to
   */
  @Override
  public void setModel(IScene scene) {
    this.scene = scene;
  }

  /**
   * Passed an IView, produces display (to console for XML representations or in a Java popup for
   * visual) representing this controller's model's animations.
   * @param view the view of the animation
   */
  @Override
  public void playAnimation(IView view) {

    if (view == null) {
      throw new IllegalArgumentException("View type cannot be null");
    }

    // Setting the size of the visualView
    if (view instanceof VisualView) {
      view.setWidth(500);
      view.setHeight(500);
    }
    view.produceDisplay(scene);
  }


  /**
   * For XML views, writes a representation of the model in this controller to a file with a given
   * path. Never called with non-xml views, but if it was, the toFile in them does nothing
   * @param view The view of the animation
   * @param out the file to print to
   */
  @Override
  public void makeFile(IView view, String out) {
    try {
      view.toFile(scene, out);
    } catch (IOException e) {
      System.out.println("Something went wrong :(");
      e.printStackTrace();
    }
  }

}
