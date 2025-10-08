import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.CoordinateSpace;
import danogl.components.GameObjectPhysics;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.LinkedList;

// will be singleton

public class Avatar extends GameObject{
    // each character has:
    // velocity, current whereabouts, image
    private static final String run0 = "C://Users//ishay//JAVA//google_game//assets//assets//run_0.png";
    private static final String run1 = "C://Users//ishay//JAVA//google_game//assets//assets//run_1.png";
    private static final String run2 = "C://Users//ishay//JAVA//google_game//assets//assets//run_2.png";
    private static final String run3 = "C://Users//ishay//JAVA//google_game//assets//assets//run_3.png";
    private static final String run4 = "C://Users//ishay//JAVA//google_game//assets//assets//run_4.png";
    private static final String run5 = "C://Users//ishay//JAVA//google_game//assets//assets//run_5.png";

    private static final String idle0 = "C:/Users/ishay/JAVA/google_game/assets/assets/idle_0.png";
    private static final String idle1 = "C:/Users/ishay/JAVA/google_game/assets/assets/idle_1.png";
    private static final String idle2 = "C:/Users/ishay/JAVA/google_game/assets/assets/idle_2.png";
    private static final String idle3 = "C:/Users/ishay/JAVA/google_game/assets/assets/idle_3.png";

    private static final String JUMP0 = "C:/Users/ishay/JAVA/google_game/assets/assets/jump_0.png";
    private static final String JUMP1 = "C:/Users/ishay/JAVA/google_game/assets/assets/jump_1.png";
    private static final String JUMP2 = "C:/Users/ishay/JAVA/google_game/assets/assets/jump_2.png";
    private static final String JUMP3 = "C:/Users/ishay/JAVA/google_game/assets/assets/jump_3.png";

    private final double TIME_BETWEEN_CLIPS = 0.1f;

    private static final Vector2 DIMENSIONS = new Vector2(50, 100);

    private static final Vector2 STARTING_PLACE = new Vector2(300, 600);

    private final float GRAVITY = 500f;

    private final float JUMP_STRENGTH = -400f;

    private static Avatar singleton;

    private static boolean IS_RUNNING = false;

    private static boolean isJumping = false;

    private static UserInputListener listener;

    private final AnimationRenderable idleAnimation;

    private final AnimationRenderable runAnimation;

    private final AnimationRenderable jumpAnimation;

    private final float yGround;

    private float y;

    private float velocity = 0;

    private LinkedList<Heart> lives;

    private Avatar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                   ImageReader imageReader, UserInputListener inputListener) {
        super(topLeftCorner, dimensions, renderable);
        idleAnimation = getIdleAnimation(imageReader);
        runAnimation = getRunAnimation(imageReader);
        jumpAnimation = getJumpAnimation(imageReader);
        listener = inputListener;
        renderer().setRenderable(idleAnimation);
        yGround = topLeftCorner.y();
        y = topLeftCorner.y();
    }

    public static Avatar getInstance(ImageReader imageReader, UserInputListener inputListener){
        if(singleton == null){
            singleton = new Avatar(STARTING_PLACE, DIMENSIONS, imageReader.readImage(idle0, true),
                    imageReader, inputListener);

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

    private AnimationRenderable getRunAnimation(ImageReader imageReader){
        String[] clips = new String[]{run0, run1, run2, run3, run4, run5};
        return new AnimationRenderable(clips,
                imageReader,
                true,
                TIME_BETWEEN_CLIPS);
    }

    private AnimationRenderable getJumpAnimation(ImageReader imageReader){
        String[] clips = new String[]{JUMP0, JUMP1, JUMP2, JUMP3};
        return new AnimationRenderable(clips,
                imageReader,
                true,
                TIME_BETWEEN_CLIPS);
    }

    public void addAvatar(GameManager gameManager, ImageReader imageReader){
        Renderable avatarImage = imageReader.readImage(idle0, false);
        super.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

    }

    public void comeDown(float deltaTime){
        velocity += GRAVITY * deltaTime;
        y += velocity * deltaTime;

        if( y >= yGround){
            velocity = 0;
            y = yGround;
            isJumping = false;
            renderer().setRenderable(runAnimation);
        }

        transform().setTopLeftCorner(new Vector2(getTopLeftCorner().x(), y));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(!isJumping && listener.isKeyPressed(KeyEvent.VK_SPACE)){
            velocity = JUMP_STRENGTH;
            renderer().setRenderable(jumpAnimation);
            isJumping = true;
        }
        // Run right
        else if(listener.isKeyPressed(KeyEvent.VK_RIGHT)){
            renderer().setRenderable(runAnimation);
        }
        // Idle if pressing left or no movement keys
        else if(!isJumping){
            renderer().setRenderable(idleAnimation);
        }

        comeDown(deltaTime);
    }

    public void addLives(LinkedList<Heart> lives){
        this.lives = lives;
    }

    public boolean removeLife(Main gameManager){
        if(lives.isEmpty()){
            return false;
        }
        else{
            gameManager.removeObject(lives.getLast());
            lives.remove(lives.getLast());
            return true;
        }
    }

    public float getyGround(){
        return yGround + DIMENSIONS.y();
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
    }
}
