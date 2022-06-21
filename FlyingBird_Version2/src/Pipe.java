import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * PIpe class used in level 0 and level 1
 */
public class Pipe{
    private final Image PIPE_IMAGE = new Image("res/level/plasticPipe.png"); // for level 1
    private double pipeSpeed = 5;
    private int count=0;
    private double TopPipeY;
    private double BottomPipeY;
    private boolean collideOnce = false;
    private boolean passedOnce = false;
    private final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI); // rotation pipes
    private double pipeX = 1024.0;

    /**
     * return CollideOnce
     * @return boolean
     */
    public boolean getCollideOnce() {
        return collideOnce;
    }

    /**
     * set the collideOnce
     * @param collideOnce
     * type:boolean
     */
    public void setCollideOnce(boolean collideOnce) {
        this.collideOnce = collideOnce;
    }

    /**
     * get the PassedOnce
     * @return boolean
     */
    public boolean getPassedOnce() {
        return passedOnce;
    }

    /**
     * set the PassedOnce
     * @param passedOnce
     * type:boolean
     */
    public void setPassedOnce(boolean passedOnce) {
        this.passedOnce = passedOnce;
    }

    /**
     * update pipes
     * @param timeScale
     * type:int
     */
    public void update(int timeScale) {
        //draw image
        renderPipeSet();
        pipeX -= pipeSpeed; //-5 pipes move left
    }

    /**
     * Draw pipe sets image on the screen
     */
    public void renderPipeSet() {
        if(!collideOnce){
            PIPE_IMAGE.draw(pipeX, TopPipeY);
            PIPE_IMAGE.draw(pipeX, BottomPipeY, ROTATOR);
        }
    }

    /**
     * Change speed
     * @param timeScale
     * type:int
     */
    public void changeSpeed(int timeScale){
         pipeSpeed = 5;
        for(int i =1; i < timeScale; i++){
            pipeSpeed = pipeSpeed*1.5;
        }
    }

    /**
     * create TOP pipes rectangle
     * @return Rectangle
     */
    public Rectangle getTopBox() { //create TOP pipes rectangle
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, TopPipeY));

    }

    /**
     * create BOTTOM pipes rectangle
     * @return Rectangle
     */
    public Rectangle getBottomBox() {// create BOTTOM pipes rectangle
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, BottomPipeY));
    }


}
