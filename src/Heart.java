import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.HashSet;
import java.util.LinkedList;

public class Heart extends GameObject {
    private static final String HEART_PATH = "C:/Users/ishay/JAVA/google_game/assets/assets/heart.png";

    private static final Vector2 FIRST_HEART_TLC = new Vector2(30, 50);

    private static final Vector2 DIMENSIONS = new Vector2(30,30);

    private static final float DISTANCE_BETWEEN = 50f;


    public Heart(Vector2 topLeftCorner, ImageReader imageReader) {
        super(topLeftCorner, DIMENSIONS, imageReader.readImage(HEART_PATH, true));
    }

    public static LinkedList<Heart> initializeHearts(ImageReader imageReader, int lives){
        LinkedList<Heart> hearts = new LinkedList<>();
        for(int i = 0; i < lives; i++){
            hearts.add( new Heart(new Vector2(FIRST_HEART_TLC.x() + (i * DISTANCE_BETWEEN), FIRST_HEART_TLC.y()),
                    imageReader));
        }
        return hearts;
    }
}
