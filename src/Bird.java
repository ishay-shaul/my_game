import danogl.gui.ImageReader;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The bird is an obstacle which appears in the game. It will start from the right side of the screen and fly
 * to the left.
 * @see Main
 * @author Ishay Shaul
 */
public class Bird extends Obstacle {
    /** paths to the images of the bird flying*/
    private static final String BIRD_PATH_0 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_0.PNG";
    private static final String BIRD_PATH_1 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_1.PNG";
    private static final String BIRD_PATH_2 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_2.PNG";
    private static final String BIRD_PATH_3 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_3.PNG";
    private static final String BIRD_PATH_4 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_4.PNG";
    private static final String BIRD_PATH_5 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_5.PNG";
    private static final String BIRD_PATH_6 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_6.PNG";
    private static final String BIRD_PATH_7 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_7.PNG";

    /** dimensions of the bird*/
    private static final Vector2 DIMENSION = new Vector2(50, 30);

    /** time between each image of the bird. Will allow the movement to flow */
    private static final float TIME_BETWEEN_CLIPS = 0.1f;

    /** the speed of the bird*/
    private static final float SPEED = -100f;

    /**
     * constructs a Bird object. Receives a game manager for the Obstacle father class
     * @param topLeftCorner The starting point. the x will change with each bird
     * @param imageReader   Reads the file path into an image
     * @param gameManager   Manager allows adding the bird to teh game
     */
    public Bird(Vector2 topLeftCorner, ImageReader imageReader, Main gameManager) {
        super(topLeftCorner, DIMENSION,
                new AnimationRenderable(new String[]{BIRD_PATH_0, BIRD_PATH_1, BIRD_PATH_2, BIRD_PATH_3, BIRD_PATH_4,
                        BIRD_PATH_5, BIRD_PATH_6, BIRD_PATH_7}, imageReader, true, TIME_BETWEEN_CLIPS),
                gameManager);
        startMove();
    }

    public float getRightX(){
        return getTopLeftCorner().x() + DIMENSION.x();
    }

    /**
     * sets the velocity of the bird. Will fly directly left
     */
    public void startMove(){
        setVelocity(new Vector2(SPEED, getVelocity().y()));
    }

}

