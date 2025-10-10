import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * The wall is used as a barrier for removing obstacle as they leave the game
 */
public class Wall extends GameObject {
    /** the width of the wall. */
    private static final float xDistance = 5f;
    /** the top left corner is to be set at the top of the screen*/
    private static final Vector2 TOP_LEFT_CORNER = new Vector2(0,0);
    /** THe color of the wall was chosen to be green in order to blend into the game*/
    private static final Color COLOR = new Color(20, 150, 20);

    /**
     * constructor for a wall. Using the height of the screen, it sets the wall so that any object
     * reaching the end of the screen is to be removed
     * @param yWindowSize the height of the window
     */
    public Wall(float yWindowSize){
        super(TOP_LEFT_CORNER, new Vector2(xDistance, yWindowSize),
                new RectangleRenderable(COLOR));
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

}
