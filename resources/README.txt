Homework 7 - Animation editor - jojo broussard and Erin Biscaia
We've attached old READMEs in case it helps you look through previous iterations of what
	we've done.

DESIGN TWEAKS
- Added IEditableScene interface with implementing class EditableScene
	this new scene class takes animations that are represented in keyframes
- Added INewAnimation interface w implementing class NewAnimation
	this new animation class is defined by keyframes rather than motions
- New ViewFactory, ConcreteViewFactory - last time we had too much going on in
	the view factory, and we moved some functionality back to main.
- Added field String out to all views
- Updated motion.toSVGString - added local variable durationTickRate, which if the motion
	was a keyframe, made the change instantaneous; this is because we originallly would
	have had it be instantaneous but SVG does not allow dur=0ms
- Added a few safety methods i.e Motion.setStartTick, etc - these should never throw the errors
	that they now handle, but if the model was to be extended it's possible that they could.
	not anymore.
- added setFields methods to views
- added a couple public facing getters / setters to tighten up access modifiers.
- Changed VisualView to basically be our CellFrame, got rid of cellframe
	previously had VisualView with a JFrame field, but now VisualView directly extends
	JFrame
- Increased documentation
- Changed addMotion - when you add a keyFrame inside of another motion it doesn't break,
	but instead splits the motion in two
- Better documentation in some places
- Fatter tests.
- When producing display, visualView no longer uses Thread.sleep - we now use a Swing timer
	with a given actionListener. EditorView works the same, but the actionListener can
	be changed to accomadate changes in the max tick.

ABOUT NEW CLASSES / INTERFACES
- IEditableScene and EditableScene - This is an interface and a class that we use in our
	editorView that implements newAnimations. This interface and implementing class
	allow us to immediately update changes taken in from controller.
- ALL CONTROLLERS - Previously, display was created directly from main / viewFactory. 
	Not anymore! BasicController serves to work with Visual, SVG, and Text views, while our
	AnimationEditController works with EditorViews.
	MockController is used for controller tests, and all classes in package myactionevents
	are also used for testing. They don't matter.
- ConcreteViewFactory - deleted viewFactory, replaced with ConcreteViewFactory. ViewFactory
	was previously just doing too much, so we made concreteViewFactory to just take in
	a string and return an object of that type of view.
- INewAnimation and NewAnimation - interface and class to represent an animation by keyframes
	rather than by motions like previously used.

JUSTIFICATIONS
- "tickrate is now ..." not always accurate, especially when rendering huge animations -
	We set tickrate to be the provided tickRate, but depending on computer performance
	it might not always be that - it's more of like a maxTickRate and your computer strives
	to reach it.
- The frame does some controllery stuff, namely giving actionEvents to the controller. This is
	because in the Swing library, these are kind of combined - when a JButton is made to be
	visible, it's then clickable. We tried to work around it and didn't see a clean way.
- Big switch in Controller - We would have loved to use the command pattern to clean up this
	thing and just throw a bunch of commands into a map; however, we've just run out of time.
	We know how to do it and at one point had the interface to implement CommandControls,
	but it didn't end up panning out. Our interface would have had the go method take
	in the controller as an arg.
- Various uncalled methods - This one really hurt us. We initially had all methods in 
	NewAnimation implemented, but we got caught up in having to use the visual view as a
	delegate - in order to avoid ~ 25 lines of code duplication, we made a big mess. 
	We know, and we're sorry. These methods are never called and are delegated to the
	normal animation class for visualView. Editorview having two of the same startTick
	this goes back to the NewAnimation - Animation debacle. 
- Big ugly binary insert in animation even though we made static methods - We tried using the
	static methods! however, when you're calling a seperate static method tens of thousands
	of times, it ends up hurting performance - our toh-12 took about 35+ seconds when we used
	those static methods, while with the built in one it took ~ 3 seconds.
- Can't increase the drawing frame size even if you add a really big new keyframe - we thought
	about this! ultimately we decided not to, since the canvas size is given in the file
	that is read instead of being dynamic based on the largest elements in a model.

We've spent ten+ hours a day on this for the last week. This is our child, and we know that it
has faults, and we'd love to fix them - but as parents, there's a point where you need to just
turn your child in so you don't fail OOD, as much as you would like to spend another day or two
cleaning it up. We've learned a WHOLE lot about making programs more extensible - we wish that
we could have a calm conversation with ourselves three weeks ago and teach them a little bit.
This has truly been a trial by fire. We know vaguely what good design looks like and especially
what it isn't - we are just also human beings with lives and other classes that make it hard
to spend more than 55 hours a week on one homework for a 4 credit hour class - Erin has an
essay due at 11:59 the day we're submitting this assignment, and jojo has two weeks of advanced
writing to do by EOD Sunday. This isn't groveling or anything - OOD has thouroughly beaten us
up, but we've learned and came out stronger.

COOL THINGS ABOUT OUR EDITORVIEW
- We think it looks pretty good :)
- A message is displayed to the client informing them of actions taken, or why an attempted
	action may have failed.
- When you select a keyframe at the bottom to edit, it automatically sets the fields to what
	they are in the frame so you don't have to do tedious stuff if you just wanna change
	color.
- We've spent an actually ludicrous amount of time trying to break it interactively, and it
	might not always be pretty, but it does what it's expected to. If you find an edge case
	we didn't consider and it blows up for you, please let us know.
- If you add a keyframe at a new highest tick, the animation automatically fixes when it's
	supposed to loop. It also does this when you remove frames.
