/*-------------------------  Some codes, attributes and methods came from Project 1 solution on Canvas---------------------------------------------------*/

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

/**Bird class
 * Some codes, attributes and methods came from Project 1 solution on Canvas
 */
public class Bird {
    private final Image WING_DOWN_IMAGE_0 = new Image("res/level-0/birdWingDown.png");
    private final Image WING_UP_IMAGE_0 = new Image("res/level-0/birdWingUp.png");
    private final Image WING_DOWN_IMAGE_1 = new Image("res/level-1/birdWingDown.png");
    private final Image WING_UP_IMAGE_1 = new Image("res/level-1/birdWingUp.png");
    private final double X = 200; // bird position x
    private final double FLY_SIZE = 6;  // fly up V=6;
    private final double FALL_SIZE = 0.4;
    private final double INITIAL_Y = 350;  // bird position y
    private final double Y_TERMINAL_VELOCITY = 10;  // max V
    private final double SWITCH_FRAME = 10;   //flap change every 10 frame
    private boolean holdWeapon= false;

    private int frameCount = 0;
    private double y;  // distance
    private double yVelocity; // V
    private Rectangle boundingBox;
    private boolean levelUp = false;

    /**
     *Constructor set initial y position of bird and Velocity
     */
    public Bird() {
        y = INITIAL_Y;
        yVelocity = 0;
        if(!levelUp){
            boundingBox = WING_DOWN_IMAGE_0.getBoundingBoxAt(new Point(X, y));
        }else{
            boundingBox = WING_DOWN_IMAGE_1.getBoundingBoxAt(new Point(X, y));
        }

    }

    /**
     * Update bird.
     * @param input
     * type:Input
     * @return Rectangle
     */

    public Rectangle update(Input input) { //bird fly(change V) and  create Rec and return Rec
        frameCount += 1;
        if (input.wasPressed(Keys.SPACE)) {
            yVelocity = -FLY_SIZE;//6
            if(!levelUp){
                WING_DOWN_IMAGE_0.draw(X, y);
            }else { WING_DOWN_IMAGE_0.draw(X, y);}
        } else {
            yVelocity = Math.min(yVelocity + FALL_SIZE, Y_TERMINAL_VELOCITY);
            if (frameCount % SWITCH_FRAME == 0) {
                if(!levelUp){
                    WING_UP_IMAGE_0.draw(X, y);
                    boundingBox = WING_UP_IMAGE_0.getBoundingBoxAt(new Point(X, y));
                }else{ WING_UP_IMAGE_1.draw(X, y);
                    boundingBox = WING_UP_IMAGE_1.getBoundingBoxAt(new Point(X, y));}

            }
            else {
                if(!levelUp){
                    WING_DOWN_IMAGE_0.draw(X, y);
                    boundingBox = WING_DOWN_IMAGE_0.getBoundingBoxAt(new Point(X, y));
                }else {
                    WING_DOWN_IMAGE_1.draw(X, y);
                    boundingBox = WING_DOWN_IMAGE_1.getBoundingBoxAt(new Point(X, y));
                }
            }
        }
        y += yVelocity;
        return boundingBox;
    }

    /**
     * Get HoldWeapon
     * @return boolean
     */
    public boolean getHoldWeapon() {
        return holdWeapon;
    }

    /**
     * Set HoldWeapon
     * @param holdWeapon
     * type:boolean
     */
    public void setHoldWeapon(boolean holdWeapon) {
        this.holdWeapon = holdWeapon;
    }

    /**
     * Set LevelUp
     * @param levelUp
     * type:boolean
     */
    public void setLevelUp(boolean levelUp) {
        this.levelUp = levelUp;
    }

    /**
     * Return bird y position
     * @return double
     */
    public double getY() {
        return y;
    }

    /**
     * Return bird X position
     * @return double
     */
    public double getX() {
        return X;
    }

    /**
     * Get rectangle of bird
     * @return double
     */
    public Rectangle getBox() {
        return boundingBox;
    }

    /**
     * Set y position of bird
     * @param y
     * type:double
     */
    public void setY(double y) {
        this.y = y;
    }


}