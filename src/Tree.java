import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Tree extends Obstacle{
    private static final String TREE_PATH = "C:/Users/ishay/JAVA/google_game/pictures/trees/9114148.jpg";

    private static final Vector2 DIMENSION = new Vector2(70, 150);
    private final float SPEED = -200f;


    public Tree(float groundHeight, ImageReader imageReader, Avatar avatar, Main gameManager,
                Vector2 windowDimensions) {
        super(new Vector2(windowDimensions.x(), groundHeight - DIMENSION.y()),
                DIMENSION,
                imageReader.readImage(TREE_PATH, true),
                avatar, gameManager);
        startMove();
    }

    public void startMove(){
        setVelocity(new Vector2(SPEED, getVelocity().y()));
    }

    public float getRightX(){
        return getTopLeftCorner().x() + DIMENSION.x();
    }
}
