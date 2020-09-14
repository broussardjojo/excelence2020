package cs3500.animator.view;

import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.scene.IScene;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.Timer;

/**
 * A class to represent an interactive editable view, with a variety of tick control / display
 * control methods.
 */
public class EditorView implements IView {

  private VisualView delegate;
  private CellPanel animationPanel;
  private EditorFrame frame;
  private int tickRate;
  private int width;
  private int height;
  private Timer timer;


  /**
   * Constructor for an editorview.
   *
   * @param delegate the delegate to pass in.
   */
  public EditorView(VisualView delegate) {
    this.delegate = delegate;
    this.animationPanel = this.delegate.getAnimationPanel();
    this.width = 700;
    this.height = 1000;
    this.tickRate = 30;
    this.frame = null;
  }

  /**
   * Produces the display to each view. Not actually though.
   */
  @Override
  public void produceDisplay() {
    // Delegated elsewhere
  }


  /**
   * Produces the display of the given scene.
   * That's a lie - actually the controller does this, but we had it here while we were initially
   * making our project and working to convert to swing timers.
   * @param scene the scene to display.
   */
  @Override
  public void produceDisplay(IScene scene) {
    this.setFields(scene);
    this.frame.setVisible(true);
    this.delegate.setTickRate(this.tickRate);

    int maxTick = 0;
    for (IAnimation a : scene.getAnimations()) {
      int thisMax = a.getMotions().get(a.getMotions().size() - 1).getEndTick();
      if (thisMax > maxTick) {
        maxTick = thisMax;
      }
    }

    int delay = (1000 / this.tickRate);
    // Make it work first :)
    int finalMaxTick = maxTick;

    timer = new Timer(delay, new ActionListener() {
      private int counter = 0;
      int thisMaxTick = finalMaxTick;

      @Override
      public void actionPerformed(ActionEvent e) {
        frame.drawAtTick(counter);
        counter++;
        if (counter > thisMaxTick) {
          System.out.println("You made it! Time to stop!");
          timer.stop();
        }
      }
    });

    timer.start();
  }

  /**
   * Sets the fields of this view (mostly width / height) from the passed scene.
   *
   * @param scene the scene to get some info from
   */
  @Override
  public void setFields(IScene scene) {
    this.width = 700;
    this.height = 1000;

    this.delegate = new VisualView();
    delegate.setFields(scene);
    this.animationPanel = new CellPanel(scene);
    this.frame = new EditorFrame(delegate);

    // Setting fields for this JFrame
    frame.setSize(width, height);
  }

  /**
   * Produces a file of the view of the animation.
   *
   * @param fileName the file to write to if this method actually did anything
   */
  @Override
  public void toFile(String fileName) throws IOException {
    // Do nothing - entirely interactive
  }

  @Override
  public void toFile(IScene scene, String fileName) throws IOException {
    // Do nothing - entirely interactive
  }

  /**
   * Sets the tick rate to the given int.
   *
   * @param tickRate the tick rate of the view
   * @throws IllegalArgumentException if the tick rate is invalid.
   */
  @Override
  public void setTickRate(int tickRate) throws IllegalArgumentException {
    this.tickRate = tickRate;
  }

  /**
   * Gets the tick rate of the view.
   *
   * @return an int that represents the tick rate of the view.
   */
  @Override
  public int getTickRate() {
    return this.tickRate;
  }

  /**
   * Sets the width of the view to the given int.
   *
   * @param width an int that represents the width of the view.
   * @throws IllegalArgumentException if the width is negative
   */
  @Override
  public void setWidth(int width) throws IllegalArgumentException {
    // Delegated elsewhere
  }

  /**
   * Gets the width of the view.
   *
   * @return the width of the view.
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Sets the height of the view.
   *
   * @param height an int that represents the height of the view.
   * @throws IllegalArgumentException if the height is negative
   */
  @Override
  public void setHeight(int height) throws IllegalArgumentException {
    // Delegated elsewhere
  }

  /**
   * Gets the heights of the view.
   *
   * @return an int that represents the height of the view.
   */
  @Override
  public int getHeight() {
    return this.height;
  }


  /**
   * Sets all interactive java Swing elements' action listeners.
   *
   * @param e the actionlistener to set to.
   */
  public void setCommandButtonListener(ActionListener e) {
    this.frame.setCommandButtonListener(e);
  }

  /**
   * gets the frame.
   *
   * @return the frame.
   */
  public EditorFrame getFrame() {
    return this.frame;
  }

  /**
   * Sets the display message.
   *
   * @param message the message to display.
   */
  public void setDisplayMessage(String message) {
    this.frame.setMessage(message);
  }

  /**
   * Returns the entered shape name to add.
   *
   * @return the entered shape name to add.
   */
  public String getNewShapeName() {
    return this.frame.getNewShapeName();
  }

  /**
   * Adds given animation to this frame.
   *
   * @param animation the animation to add.
   */
  public void addAnimationToFrame(IAnimation animation) {
    this.frame.addNewAnimation(animation);
  }

  /**
   * Removes given animation from this frame.
   *
   * @param name the name of the animation to remove
   */
  public void removeAnimationFromFrame(String name) {
    this.frame.removeAnimationFromFrame(name);
  }

  /**
   * Removes the keyframe that this is given a string representation of.
   *
   * @param keyframe a string representation of the keyframe to remove.
   */
  public void removeKeyframe(String keyframe) {
    this.frame.removeKeyframe(keyframe);
  }

  /**
   * Returns a string representation of the selected shape in the first jcomobobxo.
   *
   * @return a string represntation of the first selecfdetd shape in the jkcsn.
   */
  public String getSelectedShape() {
    return this.frame.getSelectedShape();
  }

  /**
   * Returns a string represreenation of the selected shape in the sercond jcmonobxo.
   *
   * @return a string representation of the selected shape in the second jcombobx.
   */
  public String getSelectedShapeForKeyframe() {
    return this.frame.getSelectedShapeForKeyframe();
  }

  /**
   * Returns a string representatiuon of the selected keyframe in the bottom jcvmombsx,.
   *
   * @return a string rpersertnatoin of the selected keyframe in the bottom jcombobox.
   */
  public String getSelectedKeyframe() {
    return this.frame.getSelectedKeyframe();
  }

  /**
   * Returns the entered tick to add a keyframe at - taken from JTextField.
   *
   * @return the entered tick to add a keyframe at.
   */
  public int getTickToAddKeyframeAt() {
    return this.frame.getTickToAddKeyframeAt();
  }

  /**
   * Adds a keyframe to the animation with the given name at the given tick.
   *
   * @param name the name of tyhe animation to aadd to
   * @param tick the tick to add motion at
   */
  public void addKeyframe(String name, int tick) {
    this.frame.addKeyFrame(name, tick);
  }

  /**
   * Called whenever a keyframe is selected in the third combobox. Mutates all JTextFields that take
   * in fields for the keyframe's representation to be that which are in the selected keyframe.
   */
  public void setEditFields() {
    this.frame.setEditFields();
  }

  /**
   * Called on edit keyframe button. Mutates the selected keyframe to have the fields provided in
   * the bottom 7 jtextfields.
   */
  public void editKeyframe() {
    this.frame.editKeyframe();
  }

  /**
   * Called whenever the max tick is changed in this. Returns the greatest tick of any keyframe in
   * this.
   *
   * @return the greatest tick of any keyframe in this.
   */
  public int getMaxTick() {
    return this.frame.getMaxTick();
  }
}
