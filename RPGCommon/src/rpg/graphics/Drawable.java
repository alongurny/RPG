package rpg.graphics;

/**
 * A drawable is something that can be drawn on the screen. It must provide a
 * {@link Drawer} - which will draw it.
 * 
 * @author Alon
 *
 */
public interface Drawable {
	/**
	 * The drawer which will draw this drawable.
	 * 
	 * @return a drawer
	 */
	Drawer getDrawer();
}
