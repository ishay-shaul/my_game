import danogl.gui.ImageReader;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Bird extends Obstacle {
    private static final String BIRD_PATH_0 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_0.PNG";
    private static final String BIRD_PATH_1 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_1.PNG";
    private static final String BIRD_PATH_2 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_2.PNG";
    private static final String BIRD_PATH_3 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_3.PNG";
    private static final String BIRD_PATH_4 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_4.PNG";
    private static final String BIRD_PATH_5 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_5.PNG";
    private static final String BIRD_PATH_6 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_6.PNG";
    private static final String BIRD_PATH_7 = "C:/Users/ishay/JAVA/google_game/pictures/birds_final/bird_flying_7.PNG";

    private static final Vector2 DIMENSION = new Vector2(50, 30);

    private static final float TIME_BETWEEN_CLIPS = 0.1f;

    private static final float SPEED = -5f;

    public Bird(Vector2 topLeftCorner, ImageReader imageReader, Avatar avatar, Main gameManager) {
        super(topLeftCorner, DIMENSION,
                new AnimationRenderable(new String[]{BIRD_PATH_0, BIRD_PATH_1, BIRD_PATH_2, BIRD_PATH_3, BIRD_PATH_4,
                        BIRD_PATH_5, BIRD_PATH_6, BIRD_PATH_7}, imageReader, true, TIME_BETWEEN_CLIPS),
                avatar, gameManager);
        startMove();
    }

    public float getRightX(){
        return getTopLeftCorner().x() + DIMENSION.x();
    }

    public void startMove(){
        setVelocity(new Vector2(SPEED, getVelocity().y()));
    }

}

