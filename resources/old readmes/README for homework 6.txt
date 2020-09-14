CHANGES SO FAR:
- Changed toString in Scene, Animation - 
	removed newlines between each different animation and its motions
- Added fields minX, minY to Scene - represent the offset for a canvas
- Added toSVGString in Animation, Motion, and Scene
- Made motions able to start and end on the same tick
- Added methods getMin/Max X/Y ForSVG in Shape
- Added getShapeAt(int tick) in motion, Animation, and scene
- Removed AbstractScene abstract class, and made Scene implement IScene; all functionality
	moved to Scene class.
- In Scene, changed ArrayList<Animation> to HashMap<animation>, and refactored code throughout
	the project - improves runtime to make a scene from O(animations ^ 2 + 

NOTE: If there are two instantaneous transformations on an object, the visual view will only
	display the last one added.
NOTE: Visual view will always have the top left corner be the minimum (x, y) position
	that any shape ever takes during any motion during its animation.

TODO: Change updateCanvas in abstractScene