import bagel.*;
import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.Input;
import bagel.DrawOptions;
import bagel.util.Rectangle;
import org.lwjgl.system.CallbackI;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2021
 *
 * Please filling your name below
 * @author: MLP
 */
public class ShadowFlap extends AbstractGame {
    /***********************Image ******************************8********/
    private Image backgroundImage = new Image("C:\\Users\\mengliping\\swen20003\\lipingm-project-1\\project-1-skeleton\\project-1-skeleton_\\res\\background.png");
    private Image birdWingDownImage = new Image("C:\\Users\\mengliping\\swen20003\\lipingm-project-1\\project-1-skeleton\\project-1-skeleton_\\res\\birdWingDown.png");
    private Image birdWingUPImage = new Image("C:\\Users\\mengliping\\swen20003\\lipingm-project-1\\project-1-skeleton\\project-1-skeleton_\\res\\birdWingUp.png");
    private final Font font = new Font("C:\\Users\\mengliping\\swen20003\\lipingm-project-1\\project-1-skeleton\\project-1-skeleton_\\res\\slkscr.ttf", 48);
    private final Image pipesImage = new Image("C:\\Users\\mengliping\\swen20003\\lipingm-project-1\\project-1-skeleton\\project-1-skeleton_\\res\\pipe.png");
    /***********************Attribute******************************8********/
    private static final int STEP_OVER = 75;
    private static final double ACCELERATE_DOWN= 0.4;
    private static final int STEP_SIZE_UP = 6;
    private static final int MAX_VELOCITY =10;
    private static final int MOD = 10;
    private static final int DIS = 100;

    private static final int CASE0 = 0;
    private static final int CASE1 = 1;
    private static final int CASE2 = 2;
    private static final int CASE3 = 3;

    private static int score = 0;
    public static int count = 0;
    public static int num =CASE1;
    private boolean win = false;
    private boolean lose = false;

    private static int birdX= 200;
    private static int birdY= 350;
    private static double v = 0;

    // for drawing win or lose message
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final double LEN = 2.0;
    private static final double LEN2 = 4.0;
    private static final int GAME_OVER_LEN = 9;
    private static final int SCORE_LEN = 14;
    private static final double START_PAGE_LEN = 5.0;
    private static final double OVER_PAGE_LEN = 15.0;
    private static final double SCORE_PAGE_LEN = 18.0;
    // point for drawing win or lose message
    private static final Point SCORE_POINT = new Point(DIS,DIS);
    private static final Point START_POINT = new Point(WIDTH /START_PAGE_LEN,HEIGHT/LEN);
    private static final Point GAME_OVER_POINT = new Point(WIDTH/LEN-(WIDTH/LEN2*GAME_OVER_LEN/OVER_PAGE_LEN), HEIGHT/LEN);
    private static final Point WIN_POINT = new Point(WIDTH/LEN2,HEIGHT/LEN);
    private static final Point FINAL_SCORE_POINT = new Point(WIDTH/LEN-(WIDTH/LEN2*SCORE_LEN/SCORE_PAGE_LEN),HEIGHT/LEN+STEP_OVER);

    private final Pipes pipes = new Pipes();
    public ShadowFlap() {
        super(WIDTH, HEIGHT, "ShadowFlap");
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }
    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */

    @Override
    public void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        backgroundImage.draw(Window.getWidth() / LEN,Window.getHeight() / LEN);

        // press key, game start
        if (input.wasPressed(Keys.SPACE)){
            num =CASE0 ;
        }
        if(num == CASE1){
            font.drawString("PRESS SPACE TO START", START_POINT.x, START_POINT.y);
        }
        if(num ==CASE0 && !win && !lose){
            pipes.update();
            font.drawString("SCORE: "+ score, SCORE_POINT.x, SCORE_POINT.y);
            if (input.wasPressed(Keys.SPACE)){
                v -= STEP_SIZE_UP;
                birdY -= v;
            } else{
                v += ACCELERATE_DOWN; // v+= 0.4
                if(v >= MAX_VELOCITY){ // v> = 10
                    v = MAX_VELOCITY;   // v= 10
                }
                birdY += v/LEN;    // v/2.0
            }
            // flap
            if(count % 10 == 0){
                birdWingUPImage.draw(birdX,birdY);
            }else{
                birdWingDownImage.draw(birdX,birdY);
            }
            count++;

            // collision detection
            if(pipes.pipesTop().intersects(new Point(birdX,birdY))){
                //con =CASE2;
                lose = true;
            }
            if(pipes.pipesBottom().intersects(new Point(birdX,birdY))){
                //con = CASE2;
                lose = true;
            }
            if(birdY < 0){
               // con =CASE2;
                lose = true;
            }
            if(birdY > HEIGHT){
                //con =CASE2;
                lose = true;
            }

            // win detection
            if(birdX > pipes.getPipesX()){ //bird's x coordinate surpasses the x coordinate of a pair of pipes, win
               // con= CASE3;
                win =true;
                score++;
            }
        }

        if(lose){ // lose game
            font.drawString("GAME OVER",GAME_OVER_POINT.x, GAME_OVER_POINT.y);
            font.drawString("FINAL SCORE: "+score, FINAL_SCORE_POINT.x, FINAL_SCORE_POINT.y);
        }
        if(win){ // win the game
            font.drawString("CONGRATULATIONS!", WIN_POINT.x, WIN_POINT.y);
            font.drawString("FINAL SCORE: "+score, FINAL_SCORE_POINT.x, FINAL_SCORE_POINT.y);
        }


    }
}

