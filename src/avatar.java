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

/**
 * The Avatar class represents the game character which the user will be using. The class is in charge of
 * assuring that the character will jump according to the user command.
 * In order to assure that only one character will be played, this class is a singleton.
 * @author Ishay Shaul
 * @see Main
 */
public class Avatar extends GameObject{
    /** these images represent the character running.*/
    private static final String run0 = "C://Users//ishay//JAVA//google_game//assets//assets//run_0.png";
    private static final String run1 = "C://Users//ishay//JAVA//google_game//assets//assets//run_1.png";
    private static final String run2 = "C://Users//ishay//JAVA//google_game//assets//assets//run_2.png";
    private static final String run3 = "C://Users//ishay//JAVA//google_game//assets//assets//run_3.png";
    private static final String run4 = "C://Users//ishay//JAVA//google_game//assets//assets//run_4.png";
    private static final String run5 = "C://Users//ishay//JAVA//google_game//assets//assets//run_5.png";

    /** these images represent the character when nothing happens*/
    private static final String idle0 = "C:/Users/ishay/JAVA/google_game/assets/assets/idle_0.png";
    private static final String idle1 = "C:/Users/ishay/JAVA/google_game/assets/assets/idle_1.png";
    private static final String idle2 = "C:/Users/ishay/JAVA/google_game/assets/assets/idle_2.png";
    private static final String idle3 = "C:/Users/ishay/JAVA/google_game/assets/assets/idle_3.png";

    /** these images represent the character while jumping. */
    private static final String JUMP0 = "C:/Users/ishay/JAVA/google_game/assets/assets/jump_0.png";
    private static final String JUMP1 = "C:/Users/ishay/JAVA/google_game/assets/assets/jump_1.png";
    private static final String JUMP2 = "C:/Users/ishay/JAVA/google_game/assets/assets/jump_2.png";
    private static final String JUMP3 = "C:/Users/ishay/JAVA/google_game/assets/assets/jump_3.png";

    /** The time between each image frame*/
    private final double TIME_BETWEEN_CLIPS = 0.1f;

    /** dimensions of the avatar*/
    private static final Vector2 DIMENSIONS = new Vector2(50, 100);

    /** the coordinates on the screen where the avatar starts the game. */
    private static final Vector2 STARTING_PLACE = new Vector2(300, 600);

    /** The gravity affecting the avatar. */
    private final float GRAVITY = 400f;

    /** How strong the avatars can jump. */
    private final float JUMP_STRENGTH = -500f;


//    private static final int NUM_LIVES = 3;
    /** The singleton reference to the avatar. */
    private static Avatar singleton;

    /** A boolean representing if the avatar is currently running. */
    private static boolean IS_RUNNING = false;

    /** A boolean which represents whether the avatar is midair. */
    private static boolean isJumping = false;

    /** Reference to the object which can detect which keys the player is pressing*/
    private static UserInputListener listener;

    /** Animation representing the avatar being idle. */
    private final AnimationRenderable idleAnimation;

    /** Animation of the avatar while running. */
    private final AnimationRenderable runAnimation;

    /** Animation of the avatar jumping. */
    private final AnimationRenderable jumpAnimation;

    /** A float representing where on the screen the ground is.*/
    private final float yGround;

    /** A float which locates the avatar on the screen*/
    private float y;

    /** The velocity of the avatar. In this game, the avatar can only jump in place*/
    private float velocity = 0;

//    private LinkedList<Heart> lives;

    /**
     * The constructor for Avatar. The constructor is private and can only be accessed in the getInstance method.
     * @param topLeftCorner   where the avatar is placed on the screen
     * @param dimensions      the dimensions of the avatar
     * @param renderable      an image of the avatar which will be shown on screen
     * @param imageReader     an object which can read a filepath in order to receive a renderable
     * @param inputListener   the object which will listen to the board keys
     */
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

    /**
     * Most of the variables in the constructor are already predefined, leading this method to demand less
     * variables than the constructor
     * @param imageReader   the object which translates a filepath to an image
     * @param inputListener the object translating the keyboard commands
     * @return
     */
    public static Avatar getInstance(ImageReader imageReader, UserInputListener inputListener){
        if(singleton == null){
            singleton = new Avatar(STARTING_PLACE, DIMENSIONS, imageReader.readImage(idle0, true),
                    imageReader, inputListener);

        }
        return singleton;
    }

    /**
     * A method creating the image while the avatar is not running or jumping
     * @param imageReader the object which can read the filepath
     * @return an AnimationRenderable of the avatar
     */
    private AnimationRenderable getIdleAnimation(ImageReader imageReader){
        String[] clips = new String[]{idle0, idle1, idle2, idle3};
        return new AnimationRenderable(
                clips,
                imageReader,
                true,
                TIME_BETWEEN_CLIPS);
    }

    /**
     * method that creates the avatar running
     * @param imageReader the reader of the filepath
     * @return the running avatar animation
     */
    private AnimationRenderable getRunAnimation(ImageReader imageReader){
        String[] clips = new String[]{run0, run1, run2, run3, run4, run5};
        return new AnimationRenderable(clips,
                imageReader,
                true,
                TIME_BETWEEN_CLIPS);
    }

    /**
     * method which creates the animation of the avatar jumping
     * @param imageReader the reader of the filepath
     * @return a jumping animation of the avatar
     */
    private AnimationRenderable getJumpAnimation(ImageReader imageReader){
        String[] clips = new String[]{JUMP0, JUMP1, JUMP2, JUMP3};
        return new AnimationRenderable(clips,
                imageReader,
                true,
                TIME_BETWEEN_CLIPS);
    }

    /**
     * Method which ensures the avatar returning to the ground after a jump
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
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

    /**
     * An over riden version of update. The method checks if the user has pressed a key demanding
     * the avatar to jump, and acts accordingly.
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
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

    /**
     * a getter for axis of the ground
     * @return a float representing the ground height.
     */
    public float getyGround(){
        return yGround + DIMENSIONS.y();
    }

    /**
     * a method which acts when the avatar collides with another object in the game
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
    }
}
