package cs3500.animator.model.scene;

import cs3500.animator.model.animation.INewAnimation;
import java.util.List;
import java.util.Map;

/**
 * An interface to represent a scene (a set of animations) that can be edited.
 */
public interface IEditableScene extends IScene {

  /**
   * Returns a list of the animations in this shape as keyframes.
   *
   * @return a list of the animations in this shape as keyframes.
   */
  List<INewAnimation> getNewAnimations();

  /**
   * Returns the map of key, value [String, INewAnimations] in this scene.
   *
   * @return the map of key, value [String, INewAnimations] in this scene.
   */
  Map<String, INewAnimation> getNewAnimationsMap();

  /**
   * Returns the delegate scene for this EditableScene, where drawing is delegated to.
   *
   * @return the delegate scene for this EditableScene, where drawing is delegated to.
   */
  IScene getDelegate();

  /**
   * Sets the delegate of this scene.
   *
   * @param delegate the new delegate of this scene.
   */
  void setDelegate(IScene delegate);
}
