package cs3500.animator.model.scene;

import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.animation.INewAnimation;
import cs3500.animator.model.animation.NewAnimation;
import cs3500.animator.model.shape.Shape;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to represent an editable model, where changes are displayed visually to the client.
 */
public class EditableScene implements IEditableScene {

  // Add fields of canvas here :)
  private int minX; // The X offset for the canvas
  private int minY; // The Y offset for the canvas
  private int maxX;
  private int maxY;

  protected Map<String, INewAnimation> animations;
  List<INewAnimation> animationList;
  IScene delegate;

  /**
   * Default constructor.
   */
  public EditableScene() {
    this.minX = 0;
    this.minY = 0;
    this.maxX = 0;
    this.maxY = 0;
    this.animations = new LinkedHashMap<>();
    this.animationList = new ArrayList<>();

    this.delegate = null;
  }

  /**
   * Constructor that takes in a delegate.
   *
   * @param delegate A scene delegate object.
   */
  public EditableScene(Scene delegate) {
    this.minX = delegate.getMinX();
    this.minY = delegate.getMinY();
    this.maxX = delegate.getMaxX();
    this.maxY = delegate.getMaxY();
    this.animations = new LinkedHashMap<>();
    this.animationList = new ArrayList<>();

    for (IAnimation a : delegate.animations.values()) {
      animations.put(a.getName(), new NewAnimation(a));
      animationList.add(new NewAnimation(a));
    }
    this.delegate = delegate;
  }

  /**
   * Copy constructor called after instantiated and we end up making changes in the model.
   *
   * @param copyBaby model to copy
   */
  public EditableScene(IEditableScene copyBaby) {
    this.minX = copyBaby.getMinX();
    this.minY = copyBaby.getMinY();
    this.maxX = copyBaby.getMaxX();
    this.maxY = copyBaby.getMaxY();

    this.animations = copyBaby.getNewAnimationsMap();
    this.animationList = copyBaby.getNewAnimations();

    this.delegate = copyBaby.getDelegate();
  }

  /**
   * Returns the animations in this scene.
   *
   * @return the animations in this scene.
   */
  @Override
  public List<IAnimation> getAnimations() {
    throw new UnsupportedOperationException("Did you mean - getNewAnimations() ?");
  }

  /**
   * returns the animation with the given name. delegates elsewhere.
   *
   * @param name the name of
   * @return
   */
  @Override
  public IAnimation getAnimationWithName(String name) {
    return null;
  }

  /**
   * Adds a given animation to this scene.
   *
   * @param animation the animation to be added to this scene.
   * @throws IllegalArgumentException if an animation with the given name already exists.
   */
  @Override
  public void addAnimation(IAnimation animation) throws IllegalArgumentException {
    if (animations.containsKey(animation.getName())) {
      throw new IllegalArgumentException("Animation with given name already exists.");
    }
    INewAnimation newAnimation = new NewAnimation(animation);
    this.animations.putIfAbsent(animation.getName(), newAnimation);
    this.animationList.add(newAnimation);
  }

  /**
   * Removes a given animation from this scene, given its name. If this scene has no animation with
   * the given name, do nothing.
   *
   * @param name the name of the animation to be removed
   */
  @Override
  public void removeAnimation(String name) {
    this.animations.remove(name);
    for (int lcv = 0; lcv < this.animationList.size(); lcv++) {
      if (animationList.get(lcv).getName() == name) {
        animationList.remove(lcv);
        return;
      }
    }
  }

  /**
   * Returns the minimum value of X in this scene. Delegated elsewhere.
   *
   * @return the minimum value of X in this scene.
   */
  @Override
  public int getMinX() {
    return 0;
  }

  /**
   * Returns the minimum value of Y in this scene. Delegated elsewhere.
   *
   * @return the minimum value of Y in this scene.
   */
  @Override
  public int getMinY() {
    return 0;
  }

  /**
   * Returns the maximum value of X in this scene. Delegated elsewhere.
   *
   * @return the maximum value of X in this scene.
   */
  @Override
  public int getMaxX() {
    return 0;
  }

  /**
   * Returns the maximum value of Y in this scene. Delegated elsewhere.
   *
   * @return the maximum value of Y in this scene.
   */
  @Override
  public int getMaxY() {
    return 0;
  }

  /**
   * Returns an SVG representation of this scene. delegated elsewhere.
   *
   * @param width    the width of the SVG's viewbox to create
   * @param height   the height of the SVG's viewbox to create
   * @param tickRate the tickrate to make the SVG play at
   * @return
   */
  @Override
  public String toSVGString(int width, int height, int tickRate) {
    return null;
  }

  /**
   * Updates the canvas values to be the smallest values of x and y present in list of motions from
   * any shape.
   */
  @Override
  public void updateCanvas() {
    // nothing
  }

  /**
   * Updates the bounds of this canvas. delegated elsewhere.
   *
   * @param minX   the x position of the top left corner of the canvas
   * @param minY   the y position of the top left corner of the canvas
   * @param width  the width of the canvas
   * @param height the height of the canvas
   */
  @Override
  public void updateCanvas(int minX, int minY, int width, int height) {
    // Nothing
  }


  /**
   * Returns all shapes in this scene at the given tick.
   *
   * @param tick the tick to get shapes at.
   * @return all shapes in this scene at the given tick.
   */
  @Override
  public List<Shape> getShapesAt(int tick) {
    List<Shape> newList = new ArrayList<>();
    for (INewAnimation a : this.animationList) {
      newList.add(a.getShapeAt(tick));
    }
    return newList;
  }

  /**
   * Adds a keyframe to the animation with the given name at tick tick.
   *
   * @param name the name of the animation to add to.
   * @param tick the tick to add a new keyframe at.
   */
  @Override
  public void addKeyframe(String name, int tick) {
    if (animations.get(name) == null) {
      throw new IllegalArgumentException("No animation with name " + name + " exists.");
    }
    animations.get(name).addKeyFrame(tick);
  }

  /**
   * Removes a keyframe from the animation with the given name at tick tick.
   *
   * @param name the name of the animation to remove a keyframe from.
   * @param tick the tick to remove motion at
   */
  @Override
  public void removeKeyframe(String name, int tick) {
    if (animations.get(name) == null) {
      throw new IllegalArgumentException("No animation with name " + name + " exists.");
    }
    animations.get(name).removeKeyFrame(tick);
    delegate.removeKeyframe(name, tick);
  }

  /**
   * Edits a motion from the animation with the given name at tick tick.
   *
   * @param name the name of the animation to edit a motion from.
   * @param tick the tick to edit motion at
   */
  @Override
  public void editKeyframe(String name, int tick, Shape shape) {
    if (animations.get(name) == null) {
      throw new IllegalArgumentException("No animation with name " + name + " exists.");
    }
    animations.get(name).editKeyFrame(tick, shape);
  }

  /**
   * Returns a list of the animations in this shape as keyframes.
   *
   * @return a list of the animations in this shape as keyframes.
   */
  @Override
  public List<INewAnimation> getNewAnimations() {
    List<INewAnimation> newList = new ArrayList<>();
    for (Map.Entry<String, INewAnimation> entry : this.animations.entrySet()) {
      newList.add(entry.getValue());
    }
    this.animationList = newList;
    return this.animationList;
  }

  /**
   * Returns the map of key, value [String, INewAnimations] in this scene.
   *
   * @return the map of key, value [String, INewAnimations] in this scene.
   */
  @Override
  public Map<String, INewAnimation> getNewAnimationsMap() {
    return this.animations;
  }

  /**
   * Returns the delegate scene for this EditableScene, where drawing is delegated to.
   *
   * @return the delegate scene for this EditableScene, where drawing is delegated to.
   */
  @Override
  public IScene getDelegate() {
    return this.delegate;
  }


  /**
   * Sets the delegate of this scene.
   *
   * @param delegate the new delegate of this scene.
   */
  @Override
  public void setDelegate(IScene delegate) {
    this.delegate = delegate;
    this.minX = delegate.getMinX();
    this.minY = delegate.getMinY();
    this.maxX = delegate.getMaxX();
    this.maxY = delegate.getMaxY();
    this.animations = new LinkedHashMap<>();
    this.animationList = new ArrayList<>();

    if (!(delegate instanceof EditableScene)) {
      for (IAnimation a : delegate.getAnimations()) {
        animations.put(a.getName(), new NewAnimation(a));
        animationList.add(new NewAnimation(a));
      }
      this.delegate = delegate;
    } else {
      for (IAnimation a : ((EditableScene) delegate).getNewAnimations()) {
        animations.put(a.getName(), new NewAnimation(a));
        animationList.add(new NewAnimation(a));
      }
    }
  }
}
