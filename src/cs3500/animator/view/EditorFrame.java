package cs3500.animator.view;

import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.animation.INewAnimation;
import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.scene.EditableScene;
import cs3500.animator.model.scene.IEditableScene;
import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Shape;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * A class extending JFrame that also implements ItemListener; We use this frame to place the shapes
 * we're drawing, as well as draw any Swing elements that we need to listen to and get inputs from.
 */
public class EditorFrame extends JFrame implements ItemListener {

  private CellPanel animationPanel;

  private final VisualView delegate;

  private final JLabel lastActionDone;
  private final JCheckBox[] toggleBoxes; // toggle loop
  private final JButton[] commandButtons; // Pause/play, slow, fastforward, restart

  // In add shape panel
  private final JTextField createShapeField;
  private final JButton[] createShapeButtons;

  // In remove shape panel
  private final JComboBox<String> selectShapeToRemove;
  private final JButton removeShape;

  // KEYFRAME TIME
  private final JComboBox<String> selectShapeForKeyframe;
  private final JTextField addKeyframeAtTick;
  private final JButton addKeyframeButton;

  private final JComboBox<String> selectKeyFrame;
  private final JButton deleteKeyframe;
  private final JTextField[] editKeyFrameFields;
  private final JButton editKeyframe;

  private final IEditableScene model;


  /**
   * Our constructor, given a delegateView that we draw to.
   *
   * @param delegateView the delegateView to draw to.
   */
  public EditorFrame(VisualView delegateView) {
    super();
    // Set initial important fields.
    this.setTitle("Animation Editor");
    this.setSize(new Dimension(500, 500));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Creating the main panel that all other panels are drawn on.
    JPanel mainPanel = new JPanel();
    // Set layout to place elements under one another.
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

    // Creating the panel which has the shapes drawn on it.
    this.delegate = delegateView;
    this.animationPanel = delegateView.getAnimationPanel();
    JScrollPane scrollPane = new JScrollPane(this.animationPanel,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    mainPanel.add(scrollPane);

    // Creating the text field that says last action done
    lastActionDone = new JLabel("welcome to jojo and Erin's animation editor!");
    lastActionDone.setFont(new Font("Dialog", Font.PLAIN, 16));
    mainPanel.add(lastActionDone);
    lastActionDone.setAlignmentX(Component.CENTER_ALIGNMENT);

    // This next panel is used to draw basic commands - start/pause, loop, slowDown, speedUp,
    // and restart.

    // Buttons Panel
    JPanel clickPanel = new JPanel();
    Border basicCommandBorder = BorderFactory.createLineBorder(Color.lightGray, 2);
    TitledBorder titledBasicCommandBorder = BorderFactory.createTitledBorder(basicCommandBorder,
        "Basic commands");
    clickPanel.setLayout(new FlowLayout());
    clickPanel.setBorder(titledBasicCommandBorder);

    // ToggleBoxes - It's an array because we thought at first we would have play/pause be a
    // toggleBox too.
    this.toggleBoxes = new JCheckBox[1];
    toggleBoxes[0] = new JCheckBox("loop");
    JPanel checkboxPanel = new JPanel();
    checkboxPanel.setLayout(new FlowLayout());
    for (JCheckBox box : toggleBoxes) {
      checkboxPanel.add(box);
    }
    clickPanel.add(checkboxPanel);

    // Creating JButtons
    this.commandButtons = new JButton[4];
    commandButtons[0] = new JButton("►❚❚");
    commandButtons[0].setFont(new Font("Dialog", Font.PLAIN, 12));
    commandButtons[1] = new JButton("slow down");
    commandButtons[2] = new JButton("fast forward");
    commandButtons[3] = new JButton("restart");

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    for (JButton button : commandButtons) {
      buttonPanel.add(button);
    }

    clickPanel.add(buttonPanel);
    mainPanel.add(clickPanel);

    // End of basic commands panel.

    // START OF SHAPEPANEL - This panel adds listeners to edit
    JPanel shapePanel = new JPanel();
    Border shapeCommandBorder = BorderFactory.createLineBorder(Color.lightGray, 2);
    TitledBorder titledShapeCommandBorder = BorderFactory.createTitledBorder(shapeCommandBorder,
        "Shape commands");
    shapePanel.setBorder(titledShapeCommandBorder);
    shapePanel.setLayout(new BoxLayout(shapePanel, BoxLayout.PAGE_AXIS));

    // The panel to draw add Shape stuff on.
    JPanel addShapePanel = new JPanel();
    addShapePanel.setLayout(new BoxLayout(addShapePanel, BoxLayout.PAGE_AXIS));
    addShapePanel.setBorder(BorderFactory.createTitledBorder("Add a shape"));

    // Instructions to add shape
    JLabel addShapeLabel = new JLabel("This command line is used to add a shape of given name");
    addShapePanel.add(addShapeLabel);
    addShapeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // JPanel to hold text box and echo shape buttons
    JPanel holdsCreateShapeTextAndButtons = new JPanel();
    holdsCreateShapeTextAndButtons.setLayout(new FlowLayout());

    // Text box to take in shape name
    createShapeField = new JTextField(10);
    createShapeField.setAlignmentX(Component.CENTER_ALIGNMENT);
    holdsCreateShapeTextAndButtons.add(createShapeField);

    // Buttons to create either an ellipse or a rectangle
    createShapeButtons = new JButton[2];
    createShapeButtons[0] = new JButton("echo ellipse");
    createShapeButtons[1] = new JButton("echo rectangle");
    for (JButton button : createShapeButtons) {
      holdsCreateShapeTextAndButtons.add(button);
    }

    addShapePanel.add(holdsCreateShapeTextAndButtons);

    shapePanel.add(addShapePanel);

    // END OF ADDSHAPEPANEL

    // BEGINNING OF REMOVESHAPEPANEL
    // A panel to draw remove shape functionality on.
    JPanel removeShapePanel = new JPanel();
    removeShapePanel.setLayout(new BoxLayout(removeShapePanel, BoxLayout.PAGE_AXIS));
    removeShapePanel.setBorder(BorderFactory.createTitledBorder("Remove a shape"));

    // ComboBox List of Shape Names
    JPanel comboboxPanel = new JPanel();
    comboboxPanel.setLayout(new FlowLayout());
    removeShapePanel.add(comboboxPanel);

    JLabel labelComboBox = new JLabel("Select a shape to remove");
    comboboxPanel.add(labelComboBox);
    labelComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

    model = new EditableScene();
    model.setDelegate(animationPanel.getScene());

    List<String> shapeNameList = new ArrayList<>();
    for (IAnimation a : model.getNewAnimations()) {
      shapeNameList.add(a.getName());
    }

    selectShapeToRemove = new JComboBox<>();
    selectShapeToRemove.addItem("<none>");
    //the event listener when an option is selected
    //combobox.setActionCommand("Size options");
    //combobox.addActionListener(this);
    for (String name : shapeNameList) {
      selectShapeToRemove.addItem(name);
    }

    comboboxPanel.add(selectShapeToRemove);

    // Adding remove shape button
    removeShape = new JButton("remove");

    comboboxPanel.add(removeShape);
    shapePanel.add(removeShapePanel);
    removeShapePanel.add(comboboxPanel);
    mainPanel.add(shapePanel);

    // END OF DELETESHAPE
    // END OF SHAPECOMMANDS

    // BEGINNING OF KEYFRAME COMMANDS
    // The panel where we draw keyFrame commands.
    JPanel keyframePanel = new JPanel();
    Border keyframeCommandBorder = BorderFactory.createLineBorder(Color.lightGray, 2);
    TitledBorder titledKeyframeCommandBorder =
        BorderFactory.createTitledBorder(keyframeCommandBorder, "Keyframe commands");
    keyframePanel.setBorder(titledKeyframeCommandBorder);
    keyframePanel.setLayout(new BoxLayout(keyframePanel, BoxLayout.PAGE_AXIS));

    // Panel to have ComboBox of shapes to select
    JPanel selectAShapeKeyframePanel = new JPanel();
    selectAShapeKeyframePanel.setLayout(new FlowLayout());

    JLabel thisSaysSelectAShape = new JLabel("Select a shape");

    // Combobox of shapes to display
    selectShapeForKeyframe = new JComboBox<>();
    selectShapeForKeyframe.addItem("<none>");
    for (String name : shapeNameList) {
      selectShapeForKeyframe.addItem(name);
    }

    selectAShapeKeyframePanel.add(thisSaysSelectAShape);
    selectAShapeKeyframePanel.add(selectShapeForKeyframe);

    keyframePanel.add(selectAShapeKeyframePanel);

    // BEGIN ADD KEY FRAME PANEL
    JPanel addAKeyFrame = new JPanel();
    addAKeyFrame.setLayout(new FlowLayout());
    addAKeyFrame.setBorder(BorderFactory.createTitledBorder("Add a keyframe"));

    JLabel debriefAddKeyFrame = new JLabel("Enter a tick to make a new keyframe at");
    addAKeyFrame.add(debriefAddKeyFrame);

    // Text field
    addKeyframeAtTick = new JTextField(7);
    addAKeyFrame.add(addKeyframeAtTick);

    // JButton to add keyframe at tick
    addKeyframeButton = new JButton("add");
    addAKeyFrame.add(addKeyframeButton);

    // Put addAKeyFrame on the bigger panel
    keyframePanel.add(addAKeyFrame);

    // BEGIN EDIT OR DELETE KEYFRAME

    JPanel editAndDeletePanel = new JPanel();

    editAndDeletePanel.setLayout(new BoxLayout(editAndDeletePanel, BoxLayout.PAGE_AXIS));
    editAndDeletePanel.setBorder(BorderFactory.createTitledBorder("Delete or edit keyframe"));

    JPanel selectAndDelete = new JPanel();
    selectAndDelete.setLayout(new FlowLayout());

    JLabel thisSaysSelectAKeyframe = new JLabel("Select a keyframe");
    selectAndDelete.add(thisSaysSelectAKeyframe);

    // JComboBox of all keyframes for now
    selectKeyFrame = new JComboBox<>();
    selectKeyFrame.addItem("<none>");

    List<String> keyframesAsStrings = new ArrayList<>();

    // Initializing combobox of keyframes

    for (INewAnimation a : this.model.getNewAnimations()) {
      for (IMotion m : a.getMotions()) {
        keyframesAsStrings.add(String.format("Shape: %s Tick: %d ; fields: %d %d %d %d %d %d %d",
            a.getName(), m.getEndTick(), m.getEndShape().getX(), m.getEndShape().getY(),
            m.getEndShape().getWidth(), m.getEndShape().getHeight(), m.getEndShape().getRed(),
            m.getEndShape().getGreen(), m.getEndShape().getBlue()));
      }
    }

    for (String s : keyframesAsStrings) {
      selectKeyFrame.addItem(s);
    }

    selectAndDelete.add(selectKeyFrame);

    // keyframe delete button
    deleteKeyframe = new JButton("delete");
    selectAndDelete.add(deleteKeyframe);

    editAndDeletePanel.add(selectAndDelete);

    // Creating edit panel

    // Labelling each input
    JPanel editKeyframePanel = new JPanel();
    editKeyframePanel.setLayout(new FlowLayout());

    JLabel[] labelForFields = new JLabel[7];
    labelForFields[0] = new JLabel("x");
    labelForFields[1] = new JLabel("y");
    labelForFields[2] = new JLabel("width");
    labelForFields[3] = new JLabel("height");
    labelForFields[4] = new JLabel("red");
    labelForFields[5] = new JLabel("green");
    labelForFields[6] = new JLabel("blue");

    editKeyFrameFields = new JTextField[7];
    for (int lcv = 0; lcv < editKeyFrameFields.length; lcv++) {
      if (lcv < 4) {
        editKeyFrameFields[lcv] = new JTextField(5);
      } else {
        editKeyFrameFields[lcv] = new JTextField(3);
      }
    }

    for (int lcv = 0; lcv < 7; lcv++) {
      editKeyframePanel.add(labelForFields[lcv]);
      editKeyframePanel.add(editKeyFrameFields[lcv]);
    }

    // Add edit button
    editKeyframe = new JButton("edit");
    editKeyframePanel.add(editKeyframe);

    editAndDeletePanel.add(editKeyframePanel);

    keyframePanel.add(editAndDeletePanel);

    mainPanel.add(keyframePanel);

    // ActionCommands
    // Loop checkBox
    toggleBoxes[0].setActionCommand("loop");

    // Buttons
    commandButtons[0].setActionCommand("playButton");
    commandButtons[1].setActionCommand("slowDown");
    commandButtons[2].setActionCommand("fastForward");
    commandButtons[3].setActionCommand("restart");

    // For shape commands

    // Buttons
    createShapeButtons[0].setActionCommand("echoEllipse");
    createShapeButtons[1].setActionCommand("echoRectangle");
    removeShape.setActionCommand("removeShape");

    // ComboBox
    selectShapeToRemove.setActionCommand("selectShapeForShape");

    // For keyframe commands

    // Buttons
    addKeyframeButton.setActionCommand("addKeyframe");
    deleteKeyframe.setActionCommand("deleteKeyframe");
    editKeyframe.setActionCommand("editKeyframe");

    // ComboBoxes
    selectShapeForKeyframe.setActionCommand("selectShapeForKeyframe");
    selectKeyFrame.setActionCommand("selectKeyframe");

    this.add(mainPanel);

  }

  /**
   * Invoked when an item has been selected or deselected by the user. The code written for this
   * method performs the operations that need to occur when an item is selected (or deselected).
   *
   * @param e The itemEvent to process.
   */
  @Override
  public void itemStateChanged(ItemEvent e) {
    // In actionEvent
  }

  /**
   * Provide the view with an action listener for the button that should cause the program to
   * process a command.
   *
   * @param actionEvent The actionEvent to listen for to trigger an event
   */
  public void setCommandButtonListener(ActionListener actionEvent) {

    // Make sure to comment why we have listeners here
    // "Delineation between view and controller in swing :)"

    // Basic commands

    // Loop checkBox
    toggleBoxes[0].addActionListener(actionEvent);

    // Buttons
    for (JButton button : commandButtons) {
      button.addActionListener(actionEvent);
    }

    // Shape commands

    // Buttons
    for (JButton button : createShapeButtons) {
      button.addActionListener(actionEvent);
    }
    removeShape.addActionListener(actionEvent);

    // ComboBox
    selectShapeToRemove.addActionListener(actionEvent);

    // Keyframe commands

    // Buttons
    addKeyframeButton.addActionListener(actionEvent);
    deleteKeyframe.addActionListener(actionEvent);
    editKeyframe.addActionListener(actionEvent);

    // ComboBoxes
    selectShapeForKeyframe.addActionListener(actionEvent);
    selectKeyFrame.addActionListener(actionEvent);
  }

  /**
   * Updates the animationPanel to draw all shapes at the given tick.
   * @param tick the tick to draw all shapes at.
   */
  public void drawAtTick(int tick) {
    this.animationPanel = this.delegate.getAnimationPanel();
    this.animationPanel.drawAtTick(tick);
  }

  /**
   * Gets the name of a new shape to be initialized from JTextField.
   * @return the name of a new shape to be initialized.
   */
  public String getNewShapeName() {
    String newShapeName = this.createShapeField.getText();
    this.createShapeField.setText("");
    return newShapeName;
  }

  /**
   * Sets the message in the editorView to display the given message.
   * Used to provide information to client about what was changed, or why an action may have
   * failed.
   * @param message the message to display.
   */
  public void setMessage(String message) {
    this.lastActionDone.setText(message);
  }

  /**
   * Adds a new animation to this frame, and redraws itself.
   * @param animation the animation to add.
   */
  public void addNewAnimation(IAnimation animation) {
    this.selectShapeToRemove.addItem(animation.getName());
    this.selectShapeForKeyframe.addItem(animation.getName());

    this.animationPanel.getScene().addAnimation(animation);
    this.repaint();
  }

  /**
   * Removes an animation with the given name from this, then redraws.
   * @param name the name of the animation to remove.
   */
  public void removeAnimationFromFrame(String name) {
    this.selectShapeToRemove.removeItem(name);
    this.selectShapeForKeyframe.removeItem(name);
    this.selectShapeToRemove.setSelectedIndex(0);
    this.animationPanel.getScene().removeAnimation(name);

    List<String> keyframesAsStrings = this.getKeyframesAsStrings();

    // Consider optimizing!
    // We would optimize this to only remove relevant items if we had more time but we do not
    selectKeyFrame.removeAllItems();
    selectKeyFrame.addItem("<none>");
    for (String s : keyframesAsStrings) {
      selectKeyFrame.addItem(s);
    }
    this.repaint();
  }

  /**
   * Adds keyframe to the animation with given name at given tick.
   * @param name the name of the animation to add to
   * @param tick tick to add motion at
   */
  public void addKeyFrame(String name, int tick) {
    this.animationPanel.getScene().addKeyframe(name, tick);
    this.model.setDelegate(animationPanel.getScene());

    List<String> keyframesAsStrings = this.getKeyframesAsStrings();

    // Consider optimizing!
    selectKeyFrame.removeAllItems();
    selectKeyFrame.addItem("<none>");
    for (String s : keyframesAsStrings) {
      selectKeyFrame.addItem(s);
    }
    this.repaint();
  }

  /**
   * Removes a keyframe with the given string representation from this frame.
   * @param keyframe the keyframe to remove, in its string representation defined in comboboxes.
   */
  public void removeKeyframe(String keyframe) {
    this.selectKeyFrame.removeItem(keyframe);
    this.selectKeyFrame.setSelectedIndex(0);

    String[] inputKeyframe = keyframe.split("\\s+");

    String shapeName = inputKeyframe[1];
    int keyframeTick = Integer.parseInt(inputKeyframe[3]);
    this.animationPanel.removeKeyframe(shapeName, keyframeTick);

    this.model.setDelegate(animationPanel.getScene());
    this.repaint();
  }

  /**
   * Returns a string of the selected shape in the first jcombobox.
   * @return a string of the selected shape in the first jcombobox.
   */
  public String getSelectedShape() {
    return (String) selectShapeToRemove.getSelectedItem();
  }

  /**
   * Returns a string of the selected shape in the second jcombox. (shape for keyframe).
   * @return a string of the selected shape in the second jcombobox. (shape for keyuframe).
   */
  public String getSelectedShapeForKeyframe() {
    return (String) selectShapeForKeyframe.getSelectedItem();
  }

  /**
   * Returns the selected keyframe in the third jcombobxo.
   * @return the selected keyframe in the third jcombobxo.
   */
  public String getSelectedKeyframe() {
    return (String) selectKeyFrame.getSelectedItem();
  }

  /**
   * Reads the tick to add a keyframe at from the JTextField.
   * @return the tick to add a keyframe at from the JTextField.
   */
  public int getTickToAddKeyframeAt() {
    String stringOfInt = "bogus";
    try {
      stringOfInt = this.addKeyframeAtTick.getText();
      this.addKeyframeAtTick.setText("");
      int input = Integer.parseInt(stringOfInt);
      if (input < 0) {
        throw new IllegalArgumentException("Cannot add a keyframe at negative tick");
      }
      return Integer.parseInt(stringOfInt);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("tick must be an integer; got " + stringOfInt + "instead.");
    }
  }

  /**
   * Called whenever a keyframe is selected in the third combobox.
   * Mutates all JTextFields that take in fields for the keyframe's representation to be that which
   * are in the selected keyframe.
   */
  public void setEditFields() {
    String selection;
    String[] fields;
    try {
      selection = this.getSelectedKeyframe();
      fields = selection.split("\\s");
    } catch (NullPointerException e) {
      // This is what happens when you end up removing everything from the combobox
      return;
    }

    if (fields.length == 1) {
      return;
    }

    for (int lcv = 0; lcv < 7; lcv++) {
      this.editKeyFrameFields[lcv].setText((fields[lcv + 6]));
    }
  }

  /**
   * Called on edit keyframe button.
   * Mutates the selected keyframe to have the fields provided in the bottom 7 jtextfields.
   */
  public void editKeyframe() {
    int[] params = new int[7];
    try {
      for (int lcv = 0; lcv < 7; lcv++) {
        params[lcv] = Integer.parseInt(editKeyFrameFields[lcv].getText());
      }
    } catch (NumberFormatException e) {
      throw new NumberFormatException("At least one field was not parseable as an int");
    }

    String selection = this.getSelectedKeyframe();
    String[] fields = selection.split("\\s");

    this.setMessage("Edited shape " + fields[1] + "'s keyframe at tick " + fields[3]);
    int tick = Integer.parseInt(fields[3]);
    String animationName = fields[1];

    IAnimation animationInCellPanel =
        this.animationPanel.getScene().getAnimationWithName(animationName);

    String shapeType = animationInCellPanel.getShape().getShapeName();
    Shape newShape;
    switch (shapeType) {
      case ("ellipse"):
        newShape = new Ellipse(params[0], params[1], params[2], params[3],
            params[4], params[5], params[6]);
        break;
      case ("rectangle"):
        newShape = new Rectangle(params[0], params[1], params[2], params[3],
            params[4], params[5], params[6]);
        break;
      default:
        throw new IllegalArgumentException("I have absolutely no clue how you've gotten here.");
    }
    animationInCellPanel.editKeyframeEndingAt(tick, newShape);

    // Consider extracting or optimizing
    List<String> keyframesAsStrings = this.getKeyframesAsStrings();

    this.selectKeyFrame.removeAllItems();
    this.selectKeyFrame.addItem("<none>");
    for (String s : keyframesAsStrings) {
      this.selectKeyFrame.addItem(s);
    }

    for (int lcv = 0; lcv < 7; lcv++) {
      editKeyFrameFields[lcv].setText("");
    }

    this.repaint();
  }

  /**
   * Called whenever the max tick is changed in this.
   * Returns the greatest tick of any keyframe in this.
   * @return the greatest tick of any keyframe in this.
   */
  public int getMaxTick() {
    int maxTick = 0;
    for (IAnimation a : this.animationPanel.getScene().getAnimations()) {
      for (IMotion m : a.getMotions()) {
        if (m.getEndTick() > maxTick) {
          maxTick = m.getEndTick();
        }
      }
    }
    return maxTick;
  }

  /**
   * Returns a list of string representations of all keyframes. Used to update JComboBoxes.
   * @return a list of string representations of all keyframes.
   */
  private List<String> getKeyframesAsStrings() {
    List<String> keyframesAsStrings = new ArrayList<>();
    for (IAnimation a : this.animationPanel.getScene().getAnimations()) {
      for (IMotion m : a.getMotions()) {
        keyframesAsStrings.add(String.format("Shape: %s Tick: %d ; fields: %d %d %d %d %d %d %d",
            a.getName(), m.getEndTick(), m.getEndShape().getX(), m.getEndShape().getY(),
            m.getEndShape().getWidth(), m.getEndShape().getHeight(), m.getEndShape().getRed(),
            m.getEndShape().getGreen(), m.getEndShape().getBlue()));
      }
    }

    return keyframesAsStrings;
  }
}
