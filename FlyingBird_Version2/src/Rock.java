import bagel.Image;
import bagel.Input;

/**
 * Rock class extends Weapon class
 */
public class Rock extends Weapon{
    private final int SHOOT_RANGE = 25;

    /**
     * Generate random y position of rocks
     * @param rockImage
     * type:Image
     */
    public Rock(Image rockImage ){
        super(rockImage);
    }

    /**
     * Update Rock
     * @param input
     * type:Input
     * @param bird
     * type:Bird
     * @param timeScale
     * type:int
     */
    public void update(Input input, Bird bird, int timeScale){
        super.update(input, bird, timeScale);
        super.renderWeapon(SHOOT_RANGE);
    }

}
