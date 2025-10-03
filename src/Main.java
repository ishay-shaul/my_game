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

import java.util.Random;


public class Main extends GameManager{

    private final String backroundString = "C:/Users/ishay/JAVA/google_game/pictures/backround/41524.jpg";

    private ObstacleFactory factory;

    private Avatar avatar;

    private ImageReader imageReader;

    private WindowController windowController;

    private float timeSinceLastSpawn = 0f;

    private final float SPAWN_TIME = 3f;

    private static final int MAX_HEIGHT = 100;

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

        Heart[] avatarLives = addHearts(imageReader);
        this.avatar = Avatar.getInstance(imageReader, inputListener);
        avatar.addLives(avatarLives);
        gameObjects().addGameObject(avatar);
        Wall wall = new Wall(windowDimensions.y());
        gameObjects().addGameObject(wall);
        avatar.setTag("avatar");
        wall.setTag("wall");
    }

    public Heart[] addHearts(ImageReader imageReader){
        Heart[] avatarLives = Heart.initializeHearts(imageReader);
        for(int i = 0; i < avatarLives.length; i++){
            gameObjects().addGameObject(avatarLives[i], Layer.STATIC_OBJECTS);
        }
        return avatarLives;
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
            Bird bird = createBird();
            gameObjects().addGameObject(bird, Layer.DEFAULT);
            bird.setTag("bird");
//            Obstacle addedObstacle = factory.getObstacle(avatar.getyGround(), imageReader, avatar,
//                    this, windowController.getWindowDimensions());
//            if(addedObstacle != null){
//                gameObjects().addGameObject(addedObstacle, Layer.FOREGROUND);
//            }
        }
    }

    private void addObstacles(Avatar avatar, ImageReader imageReader){

    }

    public Vector2 getWindowDimensions(){
        return windowController.getWindowDimensions();
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
