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

    private final String backroundString = "C:/Users/ishay/JAVA/google_game/pictures/backround/41524.jpg";

    private static final String GAME_OVER_PATH = "C:/Users/ishay/JAVA/google_game/pictures/2143848.jpg";

    private static final int NUM_LIVES = 3;

    private static final float GAME_OVER_TLC = 0.25f;

    private static final float GAME_OVER_DIM = 0.5f;

    private static final int ONE_SECOND_FRAME = 1000;

    private ObstacleFactory factory;

    private Avatar avatar;

    private ImageReader imageReader;

    private WindowController windowController;

    private float timeSinceLastSpawn = 0f;

    private final float SPAWN_TIME = 3f;

    private static final int MAX_HEIGHT = 100;

    private LinkedList<Heart> lives;

    public Main() {
        super();
    }

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

        factory = new ObstacleFactory();

        addHearts(imageReader);// put this inside the avatar class
        this.avatar = Avatar.getInstance(imageReader, inputListener);
//        avatar.addLives(avatarLives);
        gameObjects().addGameObject(avatar);
        Wall wall = new Wall(windowDimensions.y());
        gameObjects().addGameObject(wall);
        avatar.setTag("avatar");
        wall.setTag("wall");
    }

    public void addHearts(ImageReader imageReader){
        LinkedList<Heart> avatarLives = Heart.initializeHearts(imageReader, NUM_LIVES);
        for(Heart curHeart: avatarLives){
            gameObjects().addGameObject(curHeart, Layer.STATIC_OBJECTS);
        }
        lives = avatarLives;
    }

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

    public void removeObject(GameObject obj){
        gameObjects().removeGameObject(obj);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastSpawn += deltaTime;
        if(timeSinceLastSpawn >= SPAWN_TIME){
            timeSinceLastSpawn = 0;
//            Bird bird = createBird();
//            gameObjects().addGameObject(bird, Layer.DEFAULT);
//            bird.setTag("bird");
            addObstacles();
        }
    }

    private void addObstacles(){
        Random random = new Random();
        int pick = random.nextInt(2);
        if(pick == 1){
            Bird bird = createBird();
            gameObjects().addGameObject(bird, Layer.DEFAULT);
            bird.setTag("bird");
        }
    }

    public Vector2 getWindowDimensions(){
        return windowController.getWindowDimensions();
    }

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

    public static void main(String[] args){
        new Main().run();
    }

    private Bird createBird(){
        Random random = new Random();
        int newRand = random.nextInt(MAX_HEIGHT);
        return new Bird(new Vector2(windowController.getWindowDimensions().x(), avatar.getyGround() - newRand),
                imageReader, avatar, this);
    }
}
