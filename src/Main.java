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


public class Main extends GameManager{

    private final String backroundString = "C:/Users/ishay/JAVA/google_game/pictures/backround/41524.jpg";

    public Main() {
        super();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Renderable gameBackroundImage = imageReader.readImage(backroundString, false);

        Vector2 windowDimensions = windowController.getWindowDimensions();
        GameObject backround = new GameObject(Vector2.ZERO, windowDimensions, gameBackroundImage);
        backround.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(backround, Layer.BACKGROUND);

        Heart[] avatarLives = addHearts(imageReader);
        Avatar avatar = Avatar.getInstance(imageReader, inputListener);
        avatar.addLives(avatarLives);
        gameObjects().addGameObject(avatar, Layer.FOREGROUND);

        gameObjects().addGameObject(new Ground(windowDimensions));
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

    public static void main(String[] args){
        new Main().run();
    }
}
