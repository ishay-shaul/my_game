import danogl.gui.ImageReader;
import danogl.util.Vector2;

import java.util.Random;

public class ObstacleFactory {
    private static final int POOL = 2;
    private static final int MAX_HEIGHT = 100;

    public Obstacle getObstacle(float groundHeight, ImageReader imageReader, Avatar avatar,
                                Main gameManager, Vector2 windowDimensions){
        Random random = new Random();
        int pick = random.nextInt(POOL);
        return switch (pick) {
            case 1 -> new Tree(groundHeight, imageReader, avatar, gameManager, windowDimensions);
            case 0 -> createBird(groundHeight, imageReader, avatar, gameManager, random, windowDimensions);
            default -> null;
        };
    }

    private Bird createBird(float groundHeight, ImageReader imageReader, Avatar avatar,
                            Main gameManager, Random random, Vector2 windowDimensions){
        int newRand = random.nextInt(MAX_HEIGHT);
        return new Bird(new Vector2(windowDimensions.x(), groundHeight - newRand),
                imageReader, avatar, gameManager);
    }
}
