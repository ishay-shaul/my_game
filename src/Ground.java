import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Ground extends GameObject {

    private static final Color BASE_GROUND_COLOR = new Color(20, 150, 20);

    public Ground(Vector2 windowDimensions) {
        super(new Vector2(0, (windowDimensions.y()) * 0.8f),
                new Vector2(windowDimensions.x(), 70f),
                new RectangleRenderable(BASE_GROUND_COLOR));
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

}
