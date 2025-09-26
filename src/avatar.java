import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class avatar extends GameObject{
    // each character has:
    // velocity, current whereabouts, image
    private final String run0 = "C://Users//ishay//JAVA//google_game//assets//assets//run_0.png/";
    private final String run1 = "C://Users//ishay//JAVA//google_game//assets//assets//run_1.png/";
    private final String run2 = "C://Users//ishay//JAVA//google_game//assets//assets//run_2.png/";
    private final String run3 = "C://Users//ishay//JAVA//google_game//assets//assets//run_3.png/";
    private final String run4 = "C://Users//ishay//JAVA//google_game//assets//assets//run_4.png/";
    private final String run5 = "C://Users//ishay//JAVA//google_game//assets//assets//run_5.png/";

    public avatar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
    }
}
