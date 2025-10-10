import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * An abstract class which unites all the obstacles in the game. In this specific game, there will only be one type
 * of obstacle, the bird, but more can be added to the game.
 * @see Main
 * @author Ishay Shaul
 */
public abstract class Obstacle extends GameObject {
    /** The game manager which permits the addition of teh obstacle to the game*/
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
                    Main gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        this.setTag("obstacle");
    }

    /**
     * when an obstacle collides with another object, it will leave the game and update the lives of the avatar
     * if need be.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if ("wall".equals(other.getTag())){
            gameManager.removeObject(this);
        }
        else{
            gameManager.removeObject(this);
            boolean isAlive = gameManager.removeLife();
            if(!isAlive){
                gameManager.endGame();
            }
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
