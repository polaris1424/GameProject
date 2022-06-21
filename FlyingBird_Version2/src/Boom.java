import bagel.Image;
import bagel.Input;

/**
 * Boom class extends Weapon class used in level 0 and level 1
 */
public class Boom extends Weapon{
    private final int SHOOT_RANGE = 50;

    /**
     * Generate random y position of booms
     * @param boomImage
     * type:Image
     */
    public Boom(Image boomImage ){
        super(boomImage);
    }

    /**
     * Update Booms
     * @param input
     * type:Input
     * @param bird
     * Type:Bird
     * @param timeScale
     * type:int
     */
    public void update(Input input, Bird bird, int timeScale){
        super.update(input, bird, timeScale);
        super.renderWeapon(SHOOT_RANGE);
    }


}
