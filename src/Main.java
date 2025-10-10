import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.LinkedList;
import java.util.Random;


public class Main extends GameManager{
    /** filepath to the backround image*/
    private static final String backroundString = "C:/Users/ishay/JAVA/google_game/pictures/backround/41524.jpg";
    /** filepath to the game over image*/
    private static final String GAME_OVER_PATH = "C:/Users/ishay/JAVA/google_game/pictures/2143848.jpg";
    /** number of lives the user starts the game with*/
    private static final int NUM_LIVES = 3;
    /** the game over sign will be placed at the middle. For that the top left corner is a quarter of the screen
     * away from (0,0)*/
    private static final float GAME_OVER_TLC = 0.25f;
    /** The dimension of the game over sign is half of that of the screen*/
    private static final float GAME_OVER_DIM = 0.5f;
    /** number of frames in which the game over sign will be shown*/
    private static final int ONE_SECOND_FRAME = 1000;
    /** The avatar of the game*/
    private Avatar avatar;
    /** An image reader which reads a filepath into an image*/
    private ImageReader imageReader;
    /** the window controller gives access to the dimensions of the screen*/
    private WindowController windowController;
    /** resets each time an obstacle has been added*/
    private float timeSinceLastSpawn = 0f;
    /** each number of seconds an obstacle will appear*/
    private final float SPAWN_TIME = 3f;
    /** maximum height where the birds fly*/
    private static final int MAX_HEIGHT = 100;
    /** a linked list keeping all the remaining lives of the avatar*/
    private LinkedList<Heart> lives;

    /**
     * the constructor for the Main
     */
    public Main() {
        super();
    }

    /**
     * the method will start by adding all objects to the screen excluding the obstacles
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Renderable gameBackroundImage = imageReader.readImage(backroundString, false);
        this.windowController = windowController;

        Vector2 windowDimensions = windowController.getWindowDimensions();
        GameObject backround = new GameObject(Vector2.ZERO, windowDimensions, gameBackroundImage);
        backround.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.imageReader = imageReader;
        gameObjects().addGameObject(backround, Layer.BACKGROUND);

        addHearts(imageReader);// put this inside the avatar class
        this.avatar = Avatar.getInstance(imageReader, inputListener);
        gameObjects().addGameObject(avatar);
        Wall wall = new Wall(windowDimensions.y());
        gameObjects().addGameObject(wall);
        avatar.setTag("avatar");
        wall.setTag("wall");
    }

    /**
     * Initializes the number of lives and adds them to the top left side of the screen
     * @param imageReader will read the filepath for the heart image
     */
    public void addHearts(ImageReader imageReader){
        LinkedList<Heart> avatarLives = Heart.initializeHearts(imageReader, NUM_LIVES);
        for(Heart curHeart: avatarLives){
            gameObjects().addGameObject(curHeart, Layer.STATIC_OBJECTS);
        }
        lives = avatarLives;
    }

    /**
     * removes a life from the user. if the user has none the game ends
     * @return true if the player has lives remaining, false otherwise
     */
    public boolean removeLife(){
        if(lives.isEmpty()){
            return false;
        }
        else{
            gameObjects().removeGameObject(lives.getLast(), Layer.STATIC_OBJECTS);
            lives.remove(lives.getLast());
            return true;
        }
    }

    /**
     * removes an object from the game
     * @param obj the object being removed
     */
    public void removeObject(GameObject obj){
        gameObjects().removeGameObject(obj);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastSpawn += deltaTime;
        if(timeSinceLastSpawn >= SPAWN_TIME){
            timeSinceLastSpawn = 0;
            addObstacles();
        }
    }

    /**
     * adds an obstacle to the game
     */
    private void addObstacles(){
        Random random = new Random();
        int pick = random.nextInt(2);
        if(pick == 1){
            Bird bird = createBird();
            gameObjects().addGameObject(bird, Layer.DEFAULT);
            bird.setTag("bird");
        }
    }

    /**
     * gives us the dimensions of the window screen
     * @return the dimensions
     */
    public Vector2 getWindowDimensions(){
        return windowController.getWindowDimensions();
    }

    /**
     * Method is activated if the avatar has died. Will generate a game over sign, and then
     * after a second the window will close
     */
    public void endGame(){
        Vector2 gameOverDimensions = getWindowDimensions().mult(GAME_OVER_DIM);
        Vector2 tLC = getWindowDimensions().mult(GAME_OVER_TLC);
        Renderable image = imageReader.readImage(GAME_OVER_PATH, true);
        GameObject gameOver = new GameObject(tLC, gameOverDimensions, image);
        gameObjects().addGameObject(gameOver, Layer.FOREGROUND);
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                windowController.closeWindow();
            }
        }, ONE_SECOND_FRAME);
    }

    /**
     * creates a bird obstacle and adds to the game
     * @return a bird that will be added to the game
     */
    private Bird createBird(){
        Random random = new Random();
        int newRand = random.nextInt(MAX_HEIGHT);
        return new Bird(new Vector2(windowController.getWindowDimensions().x(), avatar.getyGround() - newRand),
                imageReader, this);
    }

    /**
     * the main function, which will just run the game
     * @param args command arguments. None needed for this game
     */
    public static void main(String[] args){
        new Main().run();
    }

}
