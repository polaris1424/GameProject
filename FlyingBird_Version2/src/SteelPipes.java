import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Steel class extends Pipe class used in level 0 and level 1
 */
public class SteelPipes extends Pipe{
    private final Image STEEL_PIPE_IMAGE = new Image("res/level-1/steelPipe.png");
    private final Image FLAME_IMAGE = new Image("res/level-1/flame.png");
    private final int SWITCH_FLAME = 20;
    private final int FLAME_DURATION = 30;
    private final int PIPE_GAP = 168;

    private int frameCount = 0;
    private int flameDuration =0;
    private double pipeSpeed = 5;
    private int count=0;
    private double TopPipeY;
    private double BottomPipeY;
    private boolean collideOnce = false;
    private boolean passedOnce = false;
    private boolean collideWithFlame = false;
    private double start = 100;
    private double end = 500;
    private double randomPosition_Y;
    private final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI); // rotation pipes
    private double pipeX = 1024.0;


    /**
     * Randomly generate y position of steel pipes
     * @param typePipeIndex
     * type:double
     * @param leveledUp
     * type:double
     */
    public SteelPipes(double typePipeIndex, boolean leveledUp) {
        if(leveledUp){
            randomPosition_Y = start +(typePipeIndex*(end-start));
            TopPipeY = randomPosition_Y - STEEL_PIPE_IMAGE .getHeight()/2;
            BottomPipeY= STEEL_PIPE_IMAGE.getHeight()/2 + randomPosition_Y + PIPE_GAP;
        }
    }

    /**
     * update steel pipes
     * @param timeScale
     * type:int
     */
    public void update(int timeScale) {
        renderPipeSet();  //draw image
        pipeX -= pipeSpeed; //-5 pipes move left
        changeSpeed(timeScale);
        frameCount++;
        if(frameCount % SWITCH_FLAME == 0){
            if(!getCollideOnce() && flameDuration < FLAME_DURATION){
                shootFlame();
                this.frameCount -= 1;
                this.flameDuration += 1;
            }
        }
        if(flameDuration == FLAME_DURATION){
            flameDuration =0;
            frameCount+= FLAME_DURATION;
        }
    }

    /**
     * Change speed of pipes
     * @param timeScale
     * type:int
     */
    public void changeSpeed(int timeScale){
        //super.changeSpeed(timeScale);
        pipeSpeed = 5;
        for(int i =1; i<timeScale;i++){
            pipeSpeed = pipeSpeed*1.5;
        }
    }

    /**
     * Shoot flames from steel pipes
     */
    public void shootFlame(){
        FLAME_IMAGE.draw(pipeX, TopPipeY+ Window.getHeight()/2.0 +10);
        FLAME_IMAGE.draw(pipeX, BottomPipeY-Window.getHeight()/2.0-10, ROTATOR);

    }

    /**
     * Draw steel pipes images on screen
     */
    public void renderPipeSet() {
        if(!collideOnce){
            STEEL_PIPE_IMAGE.draw(pipeX, TopPipeY);
            STEEL_PIPE_IMAGE.draw(pipeX, BottomPipeY, ROTATOR);
        }
    }

    /**
     * Create TOP pipes rectangle
     * @return Rectangle
     */
    public Rectangle getTopBox() {
        return STEEL_PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, TopPipeY));

    }

    /**
     * Create BOTTOM pipes rectangle
     * @return Rectangle
     */
    public Rectangle getBottomBox() {
        return STEEL_PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, BottomPipeY));
    }

    /**
     * Get FrameCount
     * @return int
     */
    public int getFrameCount() {
        return frameCount;
    }

    /**
     * Get CollideOnce
     * @return boolean
     */
    public boolean getCollideOnce() {
        return collideOnce;
    }

    /**
     * Set CollideOnce
     * @param collideOnce
     * type:boolean
     */
    public void setCollideOnce(boolean collideOnce) {
        this.collideOnce = collideOnce;
    }

    /**
     * Set CollideWithFlame
     * @return boolean
     */
    public boolean getCollideWithFlame() {
        return collideWithFlame;
    }

    /**
     * Set CollideWithFlame
     * @param collideWithFlame
     * type:boolean
     */
    public void setCollideWithFlame(boolean collideWithFlame) {
        this.collideWithFlame = collideWithFlame;
    }

    /**
     * Get PassedOnce
     * @return boolean
     */
    public boolean getPassedOnce() {
        return passedOnce;
    }

    /**
     * Set PassedOnce
     * @param passedOnce
     * type:Bolean
     */
    public void setPassedOnce(boolean passedOnce) {
        this.passedOnce = passedOnce;
    }



}
