import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

// will be singleton

public class Avatar extends GameObject{
    // each character has:
    // velocity, current whereabouts, image
    private final String run0 = "C://Users//ishay//JAVA//google_game//assets//assets//run_0.png/";
    private final String run1 = "C://Users//ishay//JAVA//google_game//assets//assets//run_1.png/";
    private final String run2 = "C://Users//ishay//JAVA//google_game//assets//assets//run_2.png/";
    private final String run3 = "C://Users//ishay//JAVA//google_game//assets//assets//run_3.png/";
    private final String run4 = "C://Users//ishay//JAVA//google_game//assets//assets//run_4.png/";
    private final String run5 = "C://Users//ishay//JAVA//google_game//assets//assets//run_5.png/";

    private static final String idle0 = "C:/Users/ishay/JAVA/google_game/assets/assets/idle_0.png";
    private static final String idle1 = "C:/Users/ishay/JAVA/google_game/assets/assets/idle_1.png";
    private final String idle2 = "C:/Users/ishay/JAVA/google_game/assets/assets/idle_2.png";
    private final String idle3 = "C:/Users/ishay/JAVA/google_game/assets/assets/idle_3.png";

    private final double TIME_BETWEEN_CLIPS = 0.1f;

    private static final Vector2 DIMENSIONS = new Vector2(50, 100);

    private static final Vector2 STARTING_PLACE = new Vector2(600, 500);

    private static Avatar singleton;

    private Avatar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

    public static Avatar getInstance(ImageReader imageReader){
        if(singleton == null){
            singleton = new Avatar(STARTING_PLACE, DIMENSIONS, imageReader.readImage(idle0, false));
        }
        return singleton;
    }

    private AnimationRenderable getIdleAnimation(ImageReader imageReader){
        String[] clips = new String[]{idle0, idle1, idle2, idle3};
        return new AnimationRenderable(
                clips,
                imageReader,
                true,
                TIME_BETWEEN_CLIPS);
    }

    public void addAvatar(GameManager gameManager, ImageReader imageReader){
        Renderable avatarImage = imageReader.readImage(idle0, false);
        super.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
    }
}
