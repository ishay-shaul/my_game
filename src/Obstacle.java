import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public abstract class Obstacle extends GameObject {
    private final Avatar avatar;
    private final Main gameManager;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Obstacle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                    Avatar avatar, Main gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.avatar = avatar;
        this.gameManager = gameManager;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other == avatar){
            avatar.removeLife(gameManager);
        }
    }

    /**
     * determines whether the obstacle has left the screen
     * @return true if it got out, false otherwise
     */
    public boolean gotOut(float x){
        if(x <= 0){
            return true;
        }
        return false;
    }
}
