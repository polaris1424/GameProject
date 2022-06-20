import bagel.*;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;
import org.lwjgl.system.CallbackI;

public class Pipes {
    private final Image pipesImage = new Image("C:\\Users\\mengliping\\swen20003\\lipingm-project-1\\project-1-skeleton\\project-1-skeleton_\\res\\pipe.png");
    private static final int STEP_SIZE = 5;
    private static final int TOP_HEIGHT = 300;
    private static final int PIPES_Y = 0;
    private static final int Y_TOP = -84;
    private static final int PIPES_X = 1000;
    private static final int PIPES_BOTTOM_Y = 468;
    private static final int Y_BOTTOM =852;
    private static final double LEN =2.0;

    private DrawOptions rotation = new DrawOptions();

    private int xTop;
    private int yTop;
    private int xBottom;
    private int yBottom;
    private double directionX;
    private double directionY;

    public Pipes(){
        xTop= PIPES_X;
        yTop = Y_TOP;

        xBottom = PIPES_X;
        yBottom = Y_BOTTOM;

        Vector2 d = new Vector2(PIPES_X,PIPES_Y);
        directionX = d.normalised().x;
        directionY = d.normalised().y;
    }

    public void update(){
        xTop -= directionX*STEP_SIZE;
        yTop -= directionY*STEP_SIZE;
        xBottom -= directionX*STEP_SIZE;
        yBottom -= directionY*STEP_SIZE;
        // draw image
        pipesImage.draw(xTop, yTop);
        pipesImage.draw(xBottom, yBottom,rotation.setRotation(Math.PI));

    }
    // make Rectangle of Top pipe
    public Rectangle pipesTop(){
        Rectangle pipesTopRect = new Rectangle(xTop-(pipesImage.getWidth()/LEN), PIPES_Y, pipesImage.getWidth(), TOP_HEIGHT);
        return pipesTopRect;
    }
    // make Rectangle of Top pipe
    public Rectangle pipesBottom(){
        Rectangle pipesBottomRect = new Rectangle(xBottom-(pipesImage.getWidth()/LEN), PIPES_BOTTOM_Y, pipesImage.getWidth(), TOP_HEIGHT);
        return pipesBottomRect;

    }
    // return top position
    public Point getTopPosition(){
        return new Point(xTop,yTop);

    }
    // return bottom position
    public Point getBoPosition(){
        return new Point(xBottom, yBottom);
    }
    public int getPipesX(){
        return xTop;
    }

}
