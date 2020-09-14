package cs3500.animator;

import cs3500.animator.controller.AnimationEditController;
import cs3500.animator.controller.BasicController;
import cs3500.animator.controller.IController;
import cs3500.animator.controller.IEditController;
import cs3500.animator.model.scene.EditableScene;
import cs3500.animator.model.scene.IEditableScene;
import cs3500.animator.model.scene.IScene;
import cs3500.animator.model.scene.Scene.Builder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.ConcreteViewFactory;
import cs3500.animator.view.EditorView;
import cs3500.animator.view.IView;
import cs3500.animator.view.VisualView;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Our main class for this project. It runs the animations.
 */
public final class Excellence {

  /**
   * Main method for our animator project. Drives creation and display / writing of a view to a
   * file.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    // Create view and Model here
    String view = null;
    String in = null;
    String out = null;
    int speed = 1;
    StringBuilder errMessage = new StringBuilder();

    JFrame errorFrame = new JFrame();

    // Reading cmd line args
    for (int lcv = 0; lcv < args.length; lcv++) {
      switch (args[lcv]) {
        case "-view":
          lcv++;
          view = args[lcv].toLowerCase();
          break;
        case "-speed":
          // In this case, if -speed is passed something that isn't an int, it is parsed but not
          // updated
          lcv++;
          try {
            speed = Integer.parseInt(args[lcv]);
          } catch (NumberFormatException e) {
            // do nothing
          }
          break;
        case "-in":
          lcv++;
          in = args[lcv];
          // In case that the directory has a space in it
          try {
            while (!(args[lcv + 1].equalsIgnoreCase("-speed")
                || args[lcv + 1].equalsIgnoreCase("-out")
                || args[lcv + 1].equalsIgnoreCase("-in")
                || args[lcv + 1].equalsIgnoreCase("-view"))) {
              lcv++;
              in = in + " " + args[lcv];
            }
          } catch (IndexOutOfBoundsException e) {
            break;
          }
          break;
        case "-out":
          lcv++;
          out = args[lcv];
          break;
        default:
          errMessage.append("Invalid command line arg provided : " + args[lcv] + "\n");
      }
    }

    // Making the Scene (Model) from cmd line args

    // Reading in, making model
    File file = new File(in);
    Reader fr = null;
    IScene model = null;
    try {
      fr = new FileReader(file);
    } catch (IOException e) {
      errMessage.append("Could not read file " + file + "\n");
    }

    // If fileReader is nul.......
    if (fr == null) {
      // Do nothing
    } else {
      AnimationReader reader = new AnimationReader();
      Builder b = new Builder();
      // System.out.println the amount of time taken - we used this to track performance improvement
      long timeAtStart = System.currentTimeMillis();
      reader.parseFile(fr, b);
      model = b.build();
      System.out.println(System.currentTimeMillis() - timeAtStart + " ms");
    }

    // Something wrong with command line args - made a view that didn't quite work
    if (errMessage.length() > 2) {
      System.out.println(errMessage.toString());
      JOptionPane errorPopup = new JOptionPane();
      errorPopup.showMessageDialog(errorFrame, errMessage.toString(),
          "Something went wrong :(", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }

    // Making the View from cmd line args
    IView thisView = new ConcreteViewFactory().make(view);
    thisView.setTickRate(speed);
    thisView.setWidth(1000);
    thisView.setHeight(1000);

    // In the case that we're not making an edit view -
    // just use a basic controller

    if (!(view.equals("edit"))) {
      IController basicController = new BasicController();
      basicController.setModel(model);

      // if the out is null, then there is no file to print to, so print to console
      if (out == null || view.equals("visual")) {
        thisView.setFields(model);
        basicController.playAnimation(thisView);
      }

      // Otherwise, write to file
      else {
        basicController.makeFile(thisView, out);
      }
      return;
    }

    // If we're dealing with an editView - create an animationController, which has more
    // functionality
    else {
      IEditController animationController = new AnimationEditController();

      IEditableScene modelCopy = new EditableScene();
      modelCopy.setDelegate(model);
      animationController.setModel(modelCopy);

      EditorView thisEView = new EditorView((VisualView) thisView);
      thisEView.setFields(model);
      thisEView.setTickRate(speed);
      animationController.setTickRate(speed);
      animationController.setView(thisEView);
      animationController.playAnimation(thisEView);
    }
  }
}