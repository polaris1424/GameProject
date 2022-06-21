import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.Random;

/**
 * Weapon class used in level 0 and level 1
 */
public class Weapon {
    private  final Image WEAPON_IMAGE;
    private final double RANDOM_POSITION_Y;
    private final int WEAPON_SPEED = 5;
    private final double SPEED_CHANGE = 1.5;
    private boolean isPickedUp = false; // check whether a weapon is picked up by bird.
    private boolean  isShoot  = false;  // check whether shoot
    private boolean disappear = false;  // used for pipes, weapons disappear
    private boolean overlap = false;  // check whether overlap
    private double weaponX = Window.getWidth();
    private double weaponY;
    private double weaponSpeed = 5;
    private int distance = 0;
    double start = 100;
    double end = 500;


    /**
     * Generate random y position of weapons
     * @param weaponImage
     * type:Image
     */
    public Weapon(Image weaponImage){
        WEAPON_IMAGE = weaponImage;
        double r = new Random().nextDouble();
        RANDOM_POSITION_Y = start + (r* (end-start)); // generate random y position of weapon
        weaponY = RANDOM_POSITION_Y- WEAPON_IMAGE.getHeight()/2;
    }

    /**
     * update weapons
     * @param input
     * type:Input
     * @param bird
     * type:Bird
     * @param timeScale
     * type:int
     */
    public void update(Input input, Bird bird, int timeScale){
        changeSpeed(timeScale);
        if(!isPickedUp){
            this.weaponX -= weaponSpeed;
        }else{
            if(bird.getHoldWeapon() && !isShoot){
                if(input.wasPressed(Keys.S)){
                    bird.setHoldWeapon(false);
                    this.isShoot = true;
                }
                setWeaponX(bird.getBox().right());
                setWeaponY(bird.getY());
            }
        }
    }

    /**
     * Change speed of weapon
     * @param timeScale
     * type:int
     */
    public void changeSpeed(int timeScale){
        weaponSpeed = WEAPON_SPEED;
        for(int i =1; i < timeScale; i++){
            weaponSpeed = weaponSpeed * SPEED_CHANGE ;
        }
    }

    /**
     * Draw weapon image on screen
     * @param shootRange
     * type:int
     */
    public void renderWeapon(int shootRange){
        if(isShoot){
            if(distance <= shootRange){
                this.weaponX += WEAPON_SPEED;
                distance ++;
            }else {
                this.disappear = true;

            }
        }
        if(!disappear && !overlap){
            WEAPON_IMAGE.draw(weaponX, weaponY);
        }
    }

    /**
     * Create Rectangle of weapon
     * @return Rectangle
     */
    public Rectangle getWeaponBox(){
        return WEAPON_IMAGE.getBoundingBoxAt(new Point(weaponX,weaponY));
    }

    /**
     * Get weapon image
     * @return Image
     */
    public Image getWEAPON_IMAGE() {
        return WEAPON_IMAGE;
    }

    /**
     * Get PickedUp
     * @return boolean
     */
    public boolean getPickedUp() {
        return isPickedUp;
    }

    /**
     * Set PickedUp
     * @param pickedUp
     * type:boolean
     */
    public void setPickedUp(boolean pickedUp) {
        isPickedUp = pickedUp;
    }

    /**
     * Get Shoot
     * @return boolean
     */
    public boolean getShoot() {
        return isShoot;
    }

    /**
     *Get Disappear
     * @return boolean
     */
    public boolean getDisappear() {
        return disappear;
    }

    /**
     * Set Disappear
     * @param disappear
     * type:double
     */
    public void setDisappear(boolean disappear) {
        this.disappear = disappear;
    }

    /**
     * Get Overlap
     * @return boolean
     */
    public boolean getOverlap() {
        return overlap;
    }

    /**
     * Set Overlap
     * @param overlap
     * type:boolean
     */
    public void setOverlap(boolean overlap) {
        this.overlap = overlap;
    }

    /**
     * Set WeaponX
     * @param weaponX
     * type:double
     */
    public void setWeaponX(double weaponX) {
        this.weaponX = weaponX;
    }

    /**
     * set WeaponY
     * @param weaponY
     * type:double
     */
    public void setWeaponY(double weaponY) {
        this.weaponY = weaponY;
    }


}
