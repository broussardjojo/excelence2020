package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * This is a mock of the AnimationEditController that will allow us to test if action events are
 * handled properly.
 */
public class MockController extends AnimationEditController implements ActionListener {

  private String commandPressedByView;

  /**
   * Returns commandPressedByView so that we know which action event occurred.
   * @return a string representing an action event command
   */
  public String getCommandPressed() {
    return this.commandPressedByView;
  }

  /**
   * This sets the commandPressedByView field to a string representing an action event command.
   * It compares the command to a set of possible cases of actions.
   * @param e the ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "loop":
        this.commandPressedByView = "loop command performed";

        break;
      case "playButton":
        this.commandPressedByView = "play command performed";
        break;
      case "slowDown":
        this.commandPressedByView = "slow down command performed";
        break;

      case "fastForward":
        this.commandPressedByView = "fast forward command performed";
        break;

      case "restart":
        this.commandPressedByView = "restart command performed";

        break;

      // Not yet finished down here
      // Shape names are not allowed to have any whitespace characters; they are auto removed.
      case "echoEllipse":
        this.commandPressedByView = "echo ellipse command performed";

        break;
      case "echoRectangle":
        this.commandPressedByView = "echo rectangle command performed";
        break;

      case "selectShapeForShape":
        this.commandPressedByView = "select shape for shape command performed";

        break;
      case "selectShapeForKeyframe":
        this.commandPressedByView = "select shape for keyframe command performed";

        break;

      case "removeShape":
        this.commandPressedByView = "remove shape command performed";

        break;


      case "addKeyframe":
        this.commandPressedByView = "add key frame command performed";
        break;

      case "selectKeyframe":
        this.commandPressedByView = "select key frame command performed";
        break;

      case "deleteKeyframe":
        this.commandPressedByView = "delete key frame command performed";
        break;

      case "editKeyframe":
        this.commandPressedByView = "edit key frame command performed";

        break;

      default:
        this.commandPressedByView = "unknown command";
        break;

    }
  }
}
