package cs3500.animator.controller.myactionevents;

import java.awt.event.ActionEvent;

/**
 * This is a class that is a mock of an action event for AddKeyframe in the Mock Controller.
 */
public class MAEAddKeyframe extends ActionEvent {

  /**
   * Constructs the mock of the action event. Needs to contain a super of source, id, and
   * command since it extends ActionEvent.
   */
  public MAEAddKeyframe() {
    super("", 1, "");
  }

  /**
   * Overrides the getActionCommand to return a string that represents the action's command.
   * @return a string representing the action's command
   */
  @Override
  public String getActionCommand() {
    return "addKeyframe";
  }
}