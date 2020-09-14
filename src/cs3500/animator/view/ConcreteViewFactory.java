package cs3500.animator.view;

/**
 * A factory that returns default constructors of various view types.
 */
public class ConcreteViewFactory {

  /**
   * Default constructor.
   */
  public ConcreteViewFactory() {
    // Just chill
  }

  /**
   * Given a valid String of a type of view, returns a default object of that view type.
   *
   * @param type a string representing the type of view to create
   * @return a default object of the passed string type
   */
  public IView make(String type) {
    switch (type) {
      case "visual":
      case "edit": // Edit goes here because we use the visualView as a delegate
        return new VisualView();
      case "svg":
        return new SVGView();
      case "text":
        return new TextView();
      default:
        throw new IllegalArgumentException("Invalid view type: " + type);
    }
  }
}
