package cs3500.animator.model.scene;

import cs3500.animator.model.animation.Animation;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.motion.Motion;
import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Shape;
import cs3500.animator.util.AnimationBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * An class to represent a scene. A scene is a set of animations (animations are a specific shape
 * (ex: "rectangle1") and a list of motions that the shape goes through).
 */
public class Scene implements IScene {

  // Add fields of canvas here :)
  private int minX; // The X offset for the canvas
  private int minY; // The Y offset for the canvas
  private int maxX;
  private int maxY;

  protected Map<String, IAnimation> animations;
  protected List<IAnimation> animationList;

  /**
   * Default constructor.
   */
  private Scene() {
    this.minX = 0;
    this.minY = 0;
    this.maxX = 500;
    this.maxY = 500;
    this.animations = new LinkedHashMap<String, IAnimation>();
    this.animationList = new ArrayList<>();
  }

  /**
   * A constructor for a scene given its animations. Throws an IllegalArgumentException if any two
   * animations have the same name in the list of IAnimations. We used this constructor for tests
   * from homework 5, but tests from hwk6 instead use the builder pattern. Please don't take off
   * because we have this, we just want our old ones to work.
   *
   * @param animations The list of animations included in this scene.
   * @throws IllegalArgumentException Thrown if any two animations have the same name in the list of
   *                                  IAnimations.
   */
  public Scene(List<IAnimation> animations) throws IllegalArgumentException {
    this.animations = new LinkedHashMap<String, IAnimation>();
    this.animationList = new ArrayList<IAnimation>();

    List<String> holdAnimations = new ArrayList<String>(Arrays.asList());
    for (int i = 0; i < animations.size(); i++) {
      String currentName = animations.get(i).getName();
      // Make sure no names overlap
      if (holdAnimations.contains(currentName)) {
        throw new IllegalArgumentException("Animation with name " + currentName
            + " already exists.");
      }
      holdAnimations.add(currentName);
    }

    for (IAnimation a : animations) {
      this.animations.put(a.getName(), a);
      this.animationList.add(a);
    }

    // If there are no animations - initialize offset to 0,0
    if (animations.isEmpty()) {
      this.minX = 0;
      this.minY = 0;
      this.maxX = 0;
      this.maxY = 0;
    } else {
      int minX = animations.get(0).minX();
      int minY = animations.get(0).minY();
      int maxX = animations.get(0).maxX();
      int maxY = animations.get(0).maxY();
      for (IAnimation a : animations) {
        if (a.minX() < minX) {
          minX = a.minX();
        }
        if (a.minY() < minY) {
          minY = a.minY();
        }
        if (a.maxX() > maxX) {
          maxX = a.maxX();
        }
        if (a.maxY() > maxY) {
          maxY = a.maxY();
        }
      }
      this.minX = minX;
      this.minY = minY;
      this.maxX = maxX;
      this.maxY = maxY;
    }
  }


  /**
   * Returns the animations in this scene.
   *
   * @return the animations in this scene.
   */
  @Override
  public List<IAnimation> getAnimations() {
    return this.animationList;
  }

  @Override
  public String toString() {
    StringBuilder acc = new StringBuilder();
    for (int lcv = 0; lcv < this.animationList.size(); lcv++) {
      acc.append(this.animationList.get(lcv).toString());
    }
    return acc.toString();
  }

  /**
   * Adds a given animation to this scene.
   *
   * @param animation the animation to be added to this scene.
   * @throws IllegalArgumentException if an animation with the given name already exists.
   */
  public void addAnimation(IAnimation animation) throws IllegalArgumentException {
    if (animations.containsKey(animation.getName())) {
      throw new IllegalArgumentException("Animation with given name already exists.");
    }
    this.animations.putIfAbsent(animation.getName(), animation);
    this.animationList.add(animation);
  }

  /**
   * Removes a given animation from this scene, given its name. If this scene has no animation with
   * the given name, do nothing. Need to update this to remove from arraylist too.
   *
   * @param name the name of the animation to be removed
   */
  public void removeAnimation(String name) {
    IAnimation toRemove = this.animations.get(name);
    this.animations.remove(name);
    this.animationList.remove(toRemove);
  }

  /**
   * Returns the minimum value of X in this scene.
   *
   * @return the minimum value of X in this scene.
   */
  @Override
  public int getMinX() {
    return this.minX;
  }

  /**
   * Returns the minimum value of Y in this scene.
   *
   * @return the minimum value of Y in this scene.
   */
  @Override
  public int getMinY() {
    return this.minY;
  }

  /**
   * Returns the maximum value of X in this scene.
   *
   * @return the maximum value of X in this scene.
   */
  @Override
  public int getMaxX() {
    return this.maxX;
  }

  /**
   * Returns the maximum value of X in this scene.
   *
   * @return the maximum value of X in this scene.
   */
  @Override
  public int getMaxY() {
    return this.maxY;
  }

  /**
   * Updates the canvas values to be the smallest values of x and y present in list of motions from
   * any shape.
   */
  @Override
  public void updateCanvas() {
    if (this.animations.isEmpty()) {
      this.minX = 0;
      this.minY = 0;
      this.maxX = 0;
      this.maxY = 0;
      return;
    } else {
      int minX = animations.get(0).minX();
      int minY = animations.get(0).minY();
      int maxX = animations.get(0).maxX();
      int maxY = animations.get(0).maxY();
      for (IAnimation a : this.animationList) {
        int thisX = a.minX();
        int thisY = a.minY();
        int thisMaxX = a.maxX();
        int thisMaxY = a.maxY();
        if (thisX < minX) {
          this.minX = thisX;
        }
        if (thisY < minY) {
          this.minY = thisY;
        }
        if (thisMaxX > maxX) {
          this.maxX = maxX;
        }
        if (thisMaxY > maxY) {
          this.maxY = maxY;
        }
      }
    }
  }

  /**
   * Updates the canvas' bounds to the given parameters.
   *
   * @param minX   the x position of the top left corner of the canvas
   * @param minY   the y position of the top left corner of the canvas
   * @param width  the width of the canvas
   * @param height the height of the canvas
   */
  @Override
  public void updateCanvas(int minX, int minY, int width, int height) {
    this.minX = minX;
    this.minY = minY;
    this.maxX = minX + width;
    this.maxY = minY + height;
  }

  /**
   * Returns an SVG representation of this model.
   *
   * @param width    the width of the SVG's viewbox to create
   * @param height   the height of the SVG's viewbox to create
   * @param tickRate the tickrate to make the SVG play at
   * @return
   */
  @Override
  public String toSVGString(int width, int height, int tickRate) {
    StringBuilder acc = new StringBuilder();
    acc.append(String.format("<svg viewBox=\"%d %d %d %d\" version=\"1.1\"\n"
        + "     xmlns=\"http://www.w3.org/2000/svg\">\n\n", this.minX, this.minY, width, height));
    for (IAnimation a : this.animationList) {
      acc.append(a.toSVGString(tickRate));
    }
    acc.append("</svg>");
    return acc.toString();
  }

  /**
   * Returns all shapes in this scene at the given tick.
   *
   * @param tick the tick to get shapes at.
   * @return all shapes in this scene at the given tick.
   */
  @Override
  public List<Shape> getShapesAt(int tick) {
    List<Shape> acc = new ArrayList<Shape>();
    for (IAnimation a : this.animationList) {
      acc.add(a.getShapeAt(tick));
    }
    return acc;
  }

  /**
   * Adds a keyframe to the animation with the given name at tick tick.
   *
   * @param name the name of the animation to add to.
   * @param tick the tick to add a new keyframe at.
   */
  @Override
  public void addKeyframe(String name, int tick) {
    IAnimation addTickTo = this.animations.getOrDefault(name, null);
    if (addTickTo == null) {
      throw new IllegalArgumentException("Hey! There is no animation with name " + name);
    }
    addTickTo.split(tick);
  }

  /**
   * Removes a keyframe from the animation with the given name at tick tick.
   *
   * @param name the name of the animation to remove a keyframe from.
   * @param tick the tick to remove motion at
   */
  @Override
  public void removeKeyframe(String name, int tick) {
    IAnimation removeFrameFrom = this.animations.getOrDefault(name, null);
    if (removeFrameFrom == null) {
      throw new IllegalArgumentException("There is no animation with name " + name + " to remove");
    }
    removeFrameFrom.removeKeyframeEndingAt(tick);
  }

  /**
   * Edits a motion from the animation with the given name at tick tick.
   *
   * @param name the name of the animation to edit a motion from.
   * @param tick the tick to edit motion at
   */
  @Override
  public void editKeyframe(String name, int tick, Shape shape) {
    IAnimation editFrameOf = this.animations.getOrDefault(name, null);
    if (editFrameOf == null) {
      throw new IllegalArgumentException("There is no animation with name " + name + " to edit");
    }
  }

  /**
   * Static builder subclass to create objects of this type. I hate this stupid loser design pattern
   * that made us get rid of our abstract class.
   */
  public static final class Builder implements AnimationBuilder<IScene> {

    private Scene scene;

    public Builder() {
      this.scene = new Scene();
    }

    /**
     * Constructs a final document.
     *
     * @return the newly constructed document
     */
    @Override
    public IScene build() {
      return this.scene;
    }

    /**
     * Specify the bounding box to be used for the animation. Our Scene currently has fields minX,
     * minY, maxX, maxY; minx and miny can be taken directly from this, while maxX = minX + width
     * maxY = minY + height And changing our updateCanvas bad boy.
     *
     * @param x      The leftmost x value
     * @param y      The topmost y value
     * @param width  The width of the bounding box
     * @param height The height of the bounding box
     * @return This {@link AnimationBuilder}
     */
    @Override
    public AnimationBuilder<IScene> setBounds(int x, int y, int width, int height) {
      this.scene.updateCanvas(x, y, width, height);
      return this;
    }

    /**
     * Adds a new shape to the growing document. Assume we will never get a repeat name of a shape.
     *
     * @param name The unique name of the shape to be added. No shape with this name should already
     *             exist.
     * @param type The type of shape (e.g. "ellipse", "rectangle") to be added. The set of supported
     *             shapes is unspecified, but should include "ellipse" and "rectangle" as a
     *             minimum.
     * @return This {@link AnimationBuilder}
     */
    @Override
    public AnimationBuilder<IScene> declareShape(String name, String type) {
      if (this.scene.animations.containsKey(name)) {
        throw new IllegalArgumentException("Shape with given name " + name + " already exists");
      }

      switch (type) {
        case "rectangle":
          this.scene.addAnimation(new Animation(new Rectangle(), name, new ArrayList<IMotion>()));
          return this;
        case "ellipse":
          this.scene.addAnimation(new Animation(new Ellipse(), name, new ArrayList<IMotion>()));
          return this;
        default:
          // in the case the shape isn't recognized, just return this builder
          return this;
      }
    }

    /**
     * Adds a transformation to the growing document.
     *
     * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
     * @param t1   The start time of this transformation
     * @param x1   The initial x-position of the shape
     * @param y1   The initial y-position of the shape
     * @param w1   The initial width of the shape
     * @param h1   The initial height of the shape
     * @param r1   The initial red color-value of the shape
     * @param g1   The initial green color-value of the shape
     * @param b1   The initial blue color-value of the shape
     * @param t2   The end time of this transformation
     * @param x2   The final x-position of the shape
     * @param y2   The final y-position of the shape
     * @param w2   The final width of the shape
     * @param h2   The final height of the shape
     * @param r2   The final red color-value of the shape
     * @param g2   The final green color-value of the shape
     * @param b2   The final blue color-value of the shape
     * @return This {@link AnimationBuilder}
     */
    @Override
    public AnimationBuilder<IScene> addMotion(String name, int t1, int x1, int y1, int w1, int h1,
        int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
      IAnimation a = this.scene.animations.get(name);
      switch (a.getShape().getShapeName()) {
        case "ellipse":
          if (a.getMotions().size() == 0) {
            a.addMotion(new Motion(t1, t1, new Ellipse(x1, y1, w1, h1, r1, g1, b1)));
          }
          a.addMotion(new Motion(t1, t2, new Ellipse(x2, y2, w2, h2, r2, g2, b2)));
          break;
        case "rectangle":
          if (a.getMotions().size() == 0) {
            a.addMotion(new Motion(t1, t1, new Rectangle(x1, y1, w1, h1, r1, g1, b1)));
          }
          a.addMotion(new Motion(t1, t2, new Rectangle(x2, y2, w2, h2, r2, g2, b2)));
          break;
        default:
          // Do nothing
      }
      return this;
    }

    /**
     * Adds an individual keyframe to the growing document.
     *
     * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
     * @param t    The time for this keyframe
     * @param x    The x-position of the shape
     * @param y    The y-position of the shape
     * @param w    The width of the shape
     * @param h    The height of the shape
     * @param r    The red color-value of the shape
     * @param g    The green color-value of the shape
     * @param b    The blue color-value of the shape
     * @return This {@link AnimationBuilder}
     */
    @Override
    public AnimationBuilder<IScene> addKeyframe(String name, int t, int x, int y, int w, int h,
        int r, int g, int b) {
      IAnimation a = this.scene.animations.get(name);
      this.addMotion(name, t, x, y, w, h, r, g, b, t, x, y, w, h, r, g, b);
      return this;
    }
  }

  /**
   * Returns the animation with the given name.
   *
   * @param name the name of the animation to find.
   * @return the animation with the given name.
   */
  @Override
  public IAnimation getAnimationWithName(String name) {
    IAnimation ourBaby = this.animations.getOrDefault(name, null);
    if (ourBaby == null) {
      throw new IllegalArgumentException("No animation with name " + name + " exists");
    } else {
      return ourBaby;
    }
  }
}
