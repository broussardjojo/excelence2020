package cs3500.animator.view;

import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.scene.IScene;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/**
 * A visual representation of a Scene object. It consists of a CellFrame and a CellPanel, which the
 * animations are drawn on.
 */
public class VisualView extends JFrame implements IView {


  protected int width;
  protected int height;
  protected IScene model;
  private int tickRate;
  private CellPanel animationPanel;
  private Timer timer;

  //INVARIANTS:
  // Tick rate is greater than zero (verified by methods and constructor).
  // The width and height cannot be negative (verified by the methods and constructor).

  /**
   * Constructor passed a cellpanel to draw on.
   * @param animationPanel the cellpanel to draw on.
   */
  public VisualView(CellPanel animationPanel) {
    this.width = 500;
    this.height = 500;
    this.tickRate = 1;
    this.animationPanel = animationPanel;
  }

  /**
   * Default constructor.
   */
  public VisualView() {
    this.width = 500;
    this.height = 500;
    this.tickRate = 1;
    this.animationPanel = null;
    this.timer = null;
  }

  /**
   * Sets the width of the view to the given int.
   * @param width an int that represents the width of the view.
   * @throws IllegalArgumentException if the width is negative
   */
  @Override
  public void setWidth(int width) throws IllegalArgumentException {
    if (width < 0) {
      throw new IllegalArgumentException("Width can't be negative");
    }
    this.width = width;
  }

  /**
   * Sets the height of the view.
   * @param height an int that represents the height of the view.
   * @throws IllegalArgumentException if the height is negative
   */
  @Override
  public void setHeight(int height) {
    if (height < 0) {
      throw new IllegalArgumentException("Height can't be negative");
    }
    this.height = height;
  }


  /**
   * Sets the tick rate to the given int.
   * @param tickRate the tick rate of the view
   * @throws IllegalArgumentException if the tick rate is invalid.
   */
  @Override
  public void setTickRate(int tickRate) throws IllegalArgumentException {
    if (tickRate <= 0) {
      throw new IllegalArgumentException("TickRate must be greater than 0");
    }
    this.tickRate = tickRate;
  }

  /**
   * Gets the tick rate of the view.
   * @return an int that represents the tick rate of the view.
   */
  @Override
  public int getTickRate() {
    return this.tickRate;
  }


  /**
   * Produces the display to each view. ahhhh deprecatied.
   */
  @Override
  public void produceDisplay() {
    System.out.println("No arg produce display in VisualView");
  }

  /**
   * Produces the display of the given scene to each view. Used in controller.
   * @param scene the scene to represent.
   */
  @Override
  public void produceDisplay(IScene scene) {
    this.setFields(scene);
    this.setTitle("ExCellence >:)");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.setSize(new Dimension(this.width, this.height));

    this.model = scene;

    // Add JScrollPane to Panel
    JScrollPane scrollPane = new JScrollPane(this.animationPanel,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    // Add the JScrollPane which also has the panel in it onto the window
    this.add(scrollPane);

    this.setVisible(true);
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
        animationPanel.drawAtTick(counter);
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
   * @param scene the scene to get some info from
   */
  @Override
  public void setFields(IScene scene) {
    this.animationPanel = new CellPanel(scene);
    this.model = scene;
  }

  /**
   * Produces a file of the view of the animation.
   *
   * @param fileName the fileneame tto write to.
   */
  @Override
  public void toFile(String fileName) throws IOException {
    // You can't do this for a visual
  }

  /**
   * Produces a file of the view of the animation.
   * @throws IOException if writing to file fails.
   */
  @Override
  public void toFile(IScene scene, String fileName) throws IOException {
    // Do nothing
  }

  /**
   * Gets the cell panel. This probably shouldnt exist but its 6pm now and i would love to make
   * our code pretty and good but i dont have TIME.
   * @return cell poanel.
   */
  public CellPanel getAnimationPanel() {
    return this.animationPanel;
  }
}
