import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;


public class Wall extends GameObject {
    private static final float xDistance = 5f;
    private static final Vector2 TOP_LEFT_CORNER = new Vector2(0,0);
    private static final Color COLOR = new Color(20, 150, 20);

    public Wall(float yWindowSize){
        super(TOP_LEFT_CORNER, new Vector2(xDistance, yWindowSize),
                new RectangleRenderable(COLOR));
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

}
