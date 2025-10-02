import danogl.gui.ImageReader;
import danogl.util.Vector2;

import java.util.Random;

public class ObstacleFactory {
    private static final int POOL = 50;
    private static final int MAX_HEIGHT = 70;

    public Obstacle getObstacle(float groundHeight, ImageReader imageReader, Avatar avatar, Main gameManager){
        Random random = new Random();
        int pick = random.nextInt(POOL);
        return switch (pick) {
            case 1 -> new Tree(groundHeight, imageReader, avatar, gameManager);
            case 2 -> createBird(groundHeight, imageReader, avatar, gameManager, random);
            default -> null;
        };
    }

    private Bird createBird(float groundHeight, ImageReader imageReader, Avatar avatar,
                            Main gameManager, Random random){
        int newRand = random.nextInt(MAX_HEIGHT);
        return new Bird(new Vector2(gameManager.getWindowDimensions().x(), groundHeight - newRand),
                imageReader, avatar, gameManager);
    }
}
