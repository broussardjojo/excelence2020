Welcome to jojo and Erin's EasyAnimator :)

Invariants are included in abstract classes.
Anything regarding actually seeing our animations besides in a String is not yet implemented.

There are four packages:

SHAPE PACKAGE:
includes Shape interface, AbstractShape abstract class, and implementing concrete classes Rectangle and Ellipse
These are used to represent shapes.
- A shape is represented by the X and Y position of its center, its width and height, and its RGB values.
- Concrete classes only provide constructors, getName, and copy; all other methods are implemented in abstract class.
- Concrete shape classes include default constructors that set all values to zero.

MOTION PACKAGE:
includes IMotion interface, AbstractMotion abstract class, Motion concrete class, and MotionFactory, a factory for motion objects.
These are used to represent simple motions - one change over time in a Shape's RGB, XY, and/or size.
- A motion is represented by the tick it begins at, the tick it ends at, and the Shape it ends up becoming.
- Factory method allows us to change only RGB, XY, or size without providing all parameters to make it easier for client
- The apply method morphs the given shape to the endShape of the motion being applied to it.

ANIMATION PACKAGE:
Includes IAnimation interface, AbstractAnimation abstract class, and Animation concrete class.
These are used to represent animations - a set of motions on a single shape.
- An animation is represented by a shape that it operates on, a String name, and a list<IMotion>.
- The name helps keep track of each specific shape and all motions that come with it.
- The constructor sorts the animations by their start tick, and throws errors if any motions cannot be applied because
	they are for a different shape type or they overlap with a different motion of this shape.

SCENE PACKAGE:
Includes IScene interface, AbstractScene abstract class, and Scene concrete class.
These are used to represent a set of animations.
- A scene is represented by a List<IAnimation>.
- Each member of the List<IAnimation> is one object and all motions that come with it.


This project also includes the class StartTickSorter, which implements Comparator<IMotion>
to sort motions in an animation by their start tick. This is used in the Animation constructor.