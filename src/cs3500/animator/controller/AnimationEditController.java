package cs3500.animator.controller;

import cs3500.animator.model.animation.Animation;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.scene.EditableScene;
import cs3500.animator.model.scene.IEditableScene;
import cs3500.animator.model.scene.IScene;
import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Shape;
import cs3500.animator.view.EditorView;
import cs3500.animator.view.IView;
import cs3500.animator.view.VisualView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.Timer;

// This controller will deal only with EditableView.

/**
 * A class to serve as a controller for an editable view of an animation.
 * Is able to listen to events occuring on it and react accordingly.
 */
public class AnimationEditController implements IEditController, ActionListener {


  // Fields
  private IEditableScene scene;
  private EditorView view;

  private int maxTick;
  private Timer timer;
  private ActionListener timerTick;
  private int tickRate;
  private int currentTick = 0;

  private boolean doesLoop;

  // Used for convenience; held here when obtained from the frame
  private String shapeToRemove;
  private String shapeForKeyframe;
  private String keyframeToEdit;

  // Static initializer shapes
  static Shape initialEllipse = new Ellipse();
  static Shape initialRectangle = new Rectangle();

  /**
   * Default constructor for this controller.
   */
  public AnimationEditController() {
    this.scene = null;
    this.view = null;

    this.shapeToRemove = "<none>";
    this.shapeForKeyframe = "<none>";
    this.keyframeToEdit = "<none>";
  }

  /**
   * Sets the view for this controller to write to.
   * @param view the given IView to set the view field to
   */
  public void setView(IView view) {
    if (view instanceof EditorView) {
      this.view = (EditorView) view;
    } else {
      this.view = new EditorView((VisualView) view);
    }
  }

  /**
   * Sets the model to represent in the view for this controller.
   * @param model an IEditableScene that the model will be set to
   */
  @Override
  public void setModel(IEditableScene model) {
    this.scene = new EditableScene(model);
  }

  /**
   * Sets the model of this controller.
   * @param scene the IScene to set the model to
   */
  @Override
  public void setModel(IScene scene) {
    this.scene = (EditableScene) scene;
  }

  /**
   * Writes the animation to the given view.
   * @param viewIn the view to write to.
   */
  @Override
  public void playAnimation(IView viewIn) {
    // Set views fields to those from scene
    if (viewIn == null) {
      throw new IllegalArgumentException("View type cannot be null");
    }
    if (viewIn instanceof VisualView) {
      view.setWidth(500);
      view.setHeight(500);
    }
    if (!(view instanceof EditorView)) {
      view.produceDisplay(scene);
    } else {
      this.view = (EditorView) viewIn;
      this.view.setCommandButtonListener(this);
      this.view.getFrame().setVisible(true);
      maxTick = 0;
      this.updateMaxTick();
      int delay = (1000 / this.tickRate);
      int finalMaxTick = maxTick;
      timerTick = new ActionListener() {
        Integer thisMaxTick = finalMaxTick;
        @Override
        public void actionPerformed(ActionEvent e) {
          view.getFrame().drawAtTick(currentTick);
          currentTick++;
          if (currentTick > thisMaxTick) {
            if (doesLoop) {
              currentTick = 0;
            }
          }
        }
      };
      timer = new Timer(delay, timerTick);
      timer.start();
    }
  }

  /**
   * In this method, doesn't do anything.
   * @param view The view of the animation
   * @param out the file to print to
   */
  @Override
  public void makeFile(IView view, String out) {
    return;
  }


  /**
   * Invoked when an action occurs.
   * We would have LOVED to make this using the command pattern, but we were honestly so pressed
   * for time. We know it would be better, cleaner design, and we made a Command interface at one
   * point, but we've spend probably 50 hours since Monday on hwk07... Please understand. We know
   * good design and it hurts us not to implement it, but we feel that it's most important to have
   * functonial stuff. I have not gone outside in days. Please understand we know what is right
   * and we would have made it right but we've been spending ten hours a day on this already and
   * i (jojo) have two weeks of advanced writing to do before Sunday is over.
   * @param e action event that occurs
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    // Turn this shit into a map!
    String command = e.getActionCommand();
    switch (e.getActionCommand()) {
      case "loop":
        JCheckBox isLooping = (JCheckBox) e.getSource();
        if (isLooping.isSelected()) {
          this.doesLoop = true;
          this.view.setDisplayMessage("Looping is now enabled");
        } else {
          this.doesLoop = false;
          this.view.setDisplayMessage("Looping is now disabled");
        }
        break;
      case "playButton":
        if (timer.isRunning()) {
          timer.stop();
          this.view.setDisplayMessage("Animation paused");
        } else {
          timer.start();
          this.view.setDisplayMessage("Animation resumed");
        }

        break;
      case "slowDown":
        if (tickRate > 5) {
          tickRate -= 5;
          tickRate = (tickRate / 5) * 5;
          timer.setDelay(1000 / tickRate);
          this.view.setDisplayMessage("tickRate reduced to " + tickRate);
        } else {
          this.view.setDisplayMessage("tickRate cannot be less than 1 tick per second");
        }
        break;
      case "fastForward":
        if (tickRate > 994) {
          this.view.setDisplayMessage("Already at max tickRate");
        } else {
          tickRate += 5;
          tickRate = (tickRate / 5) * 5;
          timer.setDelay(1000 / tickRate);
          this.view.setDisplayMessage("tickRate increased to " + tickRate);
        }
        break;
      case "restart":
        this.view.setDisplayMessage("You pressed restart");
        this.currentTick = 0;
        break;

      // Not yet finished down here
      // Shape names are not allowed to have any whitespace characters; they are auto removed.
      case "echoEllipse":
        String ellipseName = this.view.getNewShapeName();
        IAnimation newEllipseAnimation = (new Animation(initialEllipse,
            ellipseName, new ArrayList<IMotion>()));
        ellipseName = ellipseName.replaceAll("\\s", "");
        try {
          this.view.addAnimationToFrame(newEllipseAnimation);
          this.view.setDisplayMessage("New ellipse " + ellipseName + " added");
        } catch (IllegalArgumentException wedontcare) {
          this.view.setDisplayMessage("Shape with name " + ellipseName + " already exists");
        }
        break;
      case "echoRectangle":
        this.view.setDisplayMessage("You pressed echoRectangle");
        String rectName = this.view.getNewShapeName();
        IAnimation newRectAnimation = (new Animation(initialRectangle,
            rectName, new ArrayList<IMotion>()));
        rectName = rectName.replaceAll("\\s", "");
        try {
          this.view.addAnimationToFrame(newRectAnimation);
          this.view.setDisplayMessage("New rectangle " + rectName + " added");
        } catch (IllegalArgumentException asdonb) {
          this.view.setDisplayMessage("Shape with name " + rectName + " already exists");
        }
        break;
      case "selectShapeForShape":
        this.shapeToRemove = this.view.getSelectedShape();
        break;
      case "selectShapeForKeyframe":
        this.shapeForKeyframe = this.view.getSelectedShapeForKeyframe();
        break;

      case "removeShape":
        if (shapeToRemove.equals("<none>")) {
          this.view.setDisplayMessage("Cannot remove that; try selecting a real shape");
        } else {
          this.view.setDisplayMessage("Removed shape " + shapeToRemove);
          this.view.removeAnimationFromFrame(this.shapeToRemove);
        }
        break;

      // ONLY FUNCTIONALITY LEFT TO IMPLEMENT
      case "addKeyframe":
        int tickToAddAt = -1;
        try {
          tickToAddAt = this.view.getTickToAddKeyframeAt();
        } catch (IllegalArgumentException tickIsNotNum) {
          this.view.setDisplayMessage(tickIsNotNum.getMessage());
          return;
        }
        String animationToAddTo = this.view.getSelectedShapeForKeyframe();
        try {
          this.view.addKeyframe(animationToAddTo, tickToAddAt);
          if (tickToAddAt > maxTick) {
            this.updateMaxTick();
          }
        } catch (IllegalArgumentException negativeTickErr) {
          this.view.setDisplayMessage(negativeTickErr.getMessage());
          return;
        }
        this.view
            .setDisplayMessage("Added new keyframe to shape " + animationToAddTo + " at tick "
                + tickToAddAt);
        break;
      case "selectKeyframe":
        this.keyframeToEdit = this.view.getSelectedKeyframe();
        this.view.setEditFields();
        break;
      case "deleteKeyframe":
        String[] fields = this.keyframeToEdit.split("\\s");
        if (keyframeToEdit.equals("<none>")) {
          this.view.setDisplayMessage("Cannot remove that; try selecting a real keyframe");
        } else {
          this.view.removeKeyframe(this.keyframeToEdit);
        }
        if (Integer.parseInt(fields[3]) == this.maxTick) {
          this.updateMaxTick();
        }
        this.view.setDisplayMessage("Removed keyframe from shape " + fields[1] + " at tick "
            + fields[3]);
        break;
      case "editKeyframe":
        try {
          this.view.editKeyframe();
        } catch (NumberFormatException err) {
          this.view.setDisplayMessage(err.getMessage());
        } catch (IllegalArgumentException err1) {
          this.view.setDisplayMessage(err1.getMessage());
        }
        break;
      default:
        this.view.setDisplayMessage("You got something else!" + e.getActionCommand());
    }
  }

  /**
   * Gets the tick rate this controller is currently set to play at.
   * @return the tick rate this controller is currently set to play at.
   */
  @Override
  public int getTickRate() {
    return this.tickRate;
  }

  /**
   * Sets the tickRate for the controller to play the animation at.
   * @param tickRate an int representing the tick rate of the view
   */
  @Override
  public void setTickRate(int tickRate) {
    if (tickRate < 0) {
      throw new IllegalArgumentException("Tick rate cannot be negative.");
    }
    this.tickRate = tickRate;
  }

  /**
   * Updates the max tick of this controller.
   * This is called whenever a keyframe that has the largest endTick of any motions in the model
   * is deleted, or when a new keyframe is added with a tick larger than the current maxTick.
   */
  public void updateMaxTick() {
    this.maxTick = this.view.getMaxTick();
    this.updateTimer();
  }

  /**
   * Updates the timer whenever a newMaxTick is created.
   */
  public void updateTimer() {
    if (timerTick != null) {
      this.timer.removeActionListener(timerTick);
      timerTick = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          view.getFrame().drawAtTick(currentTick);
          currentTick++;
          if (currentTick > maxTick) {
            if (doesLoop) {
              currentTick = 0;
            }
          }
        }
      };
      this.timer.addActionListener(timerTick);
    }
  }

}
