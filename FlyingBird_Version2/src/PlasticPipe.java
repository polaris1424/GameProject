import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Plastic class extends Pipe class used in level 0 and level 1
 */
public class PlasticPipe extends Pipe{
    private final Image PIPE_IMAGE = new Image("res/level/plasticPipe.png");
    private final int PIPE_GAP = 168;
    private final double SPEED_CHANGE = 1.5;
    private final int SPEED_INITIAL = 5;
    private final int pipeImageType = 0;
    private final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI); // rotation pipes
    private final double TOP_PIPE_Y_HIGH = 100-384.0;
    private final double BOTTOM_PIPE_Y_HIGH = 100+ PIPE_GAP+384; // 384=768/2
    private final double TOP_PIPE_Y_MID= 300-384.0; //648
    private final double BOTTOM_PIPE_Y_MID = 300+ PIPE_GAP+384; // 384=768/2
    private final double TOP_PIPE_Y_LOW= 500-384.0;
    private final double BOTTOM_PIPE_Y_LOW = 500+ PIPE_GAP+384; // 384=768/2

    private double pipeSpeed = 5;
    private double TopPipeY;
    private double BottomPipeY;
    private boolean collideOnce = false;
    private boolean passedOnce = false;
    private double start = 100;
    private double end = 500;
    private double randomPosition_Y;
    private double pipeX = 1024.0; //1024 not int, need change

    /**
     * Generate different types of plastic pipes
     * @param typePipeIndex
     * type:double
     * @param leveledUp
     * type:boolean
     */
    public PlasticPipe(double typePipeIndex, boolean leveledUp) {
         if(!leveledUp){
             if(typePipeIndex==0){
                 TopPipeY = TOP_PIPE_Y_HIGH;
                 BottomPipeY = BOTTOM_PIPE_Y_HIGH;
             }
             if(typePipeIndex==1){
                 TopPipeY = TOP_PIPE_Y_MID;
                 BottomPipeY = BOTTOM_PIPE_Y_MID;
             }
             if(typePipeIndex==2){
                 TopPipeY = TOP_PIPE_Y_LOW;
                 BottomPipeY = BOTTOM_PIPE_Y_LOW;
             }
         }else{
             randomPosition_Y = start +(typePipeIndex*(end-start));
             TopPipeY = randomPosition_Y - PIPE_IMAGE.getHeight()/2;
             BottomPipeY= PIPE_IMAGE.getHeight()/2 + randomPosition_Y + PIPE_GAP;
         }
    }

    /**
     * get CollideOnce
     * @return boolean
     */
    public boolean getCollideOnce() {
        return collideOnce;
    }

    /**
     * set CollideOnce
     * @param collideOnce
     * type:boolean
     */
    public void setCollideOnce(boolean collideOnce) {
        this.collideOnce = collideOnce;
    }

    /**
     * get PassedOnce
     * @return boolean
     */
    public boolean getPassedOnce() {
        return passedOnce;
    }

    /**
     * set PassedOnce
     * @param passedOnce
     * type:boolean
     */
    public void setPassedOnce(boolean passedOnce) {
        this.passedOnce = passedOnce;
    }

    /**
     * update plastic pipes
     * @param timeScale
     * type:int
     */
    public void update(int timeScale) {
        //draw image
        renderPipeSet();
        pipeX -= pipeSpeed; //-5 pipes move left
        changeSpeed(timeScale);
    }

    /**
     * Draw plastic pipes images on screen
     */
    public void renderPipeSet() {
        if(!collideOnce){
            PIPE_IMAGE.draw(pipeX, TopPipeY);
            PIPE_IMAGE.draw(pipeX, BottomPipeY, ROTATOR);
        }
    }

    /**
     * Change timescale of pipes
     * @param timeScale
     * type:int
     */
    public void changeSpeed(int timeScale){
        //super.changeSpeed(timeScale);
        pipeSpeed = SPEED_INITIAL;
        for(int i = 1; i < timeScale;i++){
          pipeSpeed = pipeSpeed * SPEED_CHANGE;
         }
    }

    /**
     * create TOP pipes rectangle
     * @return Rectangle
     */
    public Rectangle getTopBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, TopPipeY));

    }

    /**
     * create BOTTOM pipes rectangle
     * @return Rectangle
     */
    public Rectangle getBottomBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, BottomPipeY));

    }


}
