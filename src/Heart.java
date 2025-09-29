import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Heart extends GameObject {
    private static final String HEART_PATH = "C:/Users/ishay/JAVA/google_game/assets/assets/heart.png";

    private static final Vector2 FIRST_HEART_TLC = new Vector2(30, 50);

    private static final Vector2 DIMENSIONS = new Vector2(30,30);

    private static final float DISTANCE_BETWEEN = 50f;


    private Heart(Vector2 topLeftCorner, ImageReader imageReader) {
        super(topLeftCorner, DIMENSIONS, imageReader.readImage(HEART_PATH, true));
    }

    public static Heart[] initializeHearts(ImageReader imageReader){
        Heart[] hearts = new Heart[3];
        for(int i = 0; i < hearts.length; i++){
            hearts[i] = new Heart(new Vector2(FIRST_HEART_TLC.x() + (i * DISTANCE_BETWEEN), FIRST_HEART_TLC.y()),
                    imageReader);
        }
        return hearts;
    }
}
