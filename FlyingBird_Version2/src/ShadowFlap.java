import bagel.*;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 * SWEN20003 Project 1, Semester 2, 2021.
 * Some codes, attributes and methods came from Project 1 solution on Canvas
 * @author Liping Meng
 */

public class ShadowFlap extends AbstractGame{
    private final Image LEVEL0_BACKGROUND_IMAGE = new Image("res/level-0/background.png");
    private final Image LEVEL1_BACKGROUND_IMAGE =new Image("res/level-1/background.png");
    private final Image BOOM_IMAGE = new Image("res/level-1/bomb.png"); // for level_1
    private final Image ROCK_IMAGE = new Image("res/level-1/rock.png"); // for level_1

    private final String INSTRUCTION_MSG = "PRESS SPACE TO START";
    private final String GAME_OVER_MSG = "GAME OVER!";
    private final String CONGRATS_MSG = "CONGRATULATIONS!";
    private final String SCORE_MSG = "SCORE: ";
    private final String FINAL_SCORE_MSG = "FINAL SCORE: ";
    private final String LEVEL_UP_MSG ="LEVEL-UP!";
    private final String INSTRUCTION_SHOOT_MSG = "PRESS 'S' TO SHOOT";
    private final int FONT_SIZE = 48;
    private final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);

    private final int SCORE_MSG_OFFSET = 75;
    private final int INS_SHOOT_OFFSET = 68;
    private final int MAX_SCALE = 5;
    private final int MIN_SCALE = 1;
    private final int WEAPON_FRAME_CHANGE = 177;
    private final int BIRD_Y = 350;
    private final double SPEED_CHANGE = 1.5;
    private final int INITIAL_FRAME = 100;
    private final int WIN_SCORE = 30;
    private final int LEVEL_SCORE = 10;
    private final int LEVEL1_LIFE = 6;
    private final int FLAME = 20;

    private static int timeScale = 1;
    private int score;
    private static int pipeCount = 0; // calculate frame
    private int levelCount = 0; // 20 frame to render level up ! message
    private long frameCount;
    private boolean gameOn;
    private boolean collision;
    private boolean flameCollision;
    private boolean win;
    private boolean levelUp = false;
    private boolean leveledUp = false;
    private boolean levelUp_msg =false;

    private ArrayList<PlasticPipe> pipeSets0 = new ArrayList<>(); //for level 0
    private ArrayList<Pipe> pipeSet1 = new ArrayList<>(); // for level 1
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<Integer> flag= new ArrayList<>(); //for level 0 pipeMoveLevel() function
    private ArrayList<Integer> flagL1 = new ArrayList<>(); //for level 1 pipeMoveLevel1() function
    Random r = new Random();
    private Bird bird;
    private LifeBar lifeBar;

    /**
     *create a new bird, a new life bar, set score be 0, gameOn be false, collision be false, win be false
     */
    public ShadowFlap() {
        super(1024, 768, "ShadowFlap");
        bird = new Bird();
        lifeBar = new LifeBar();
        score = 0;
        gameOn = false;
        collision = false;
        win = false;
    }

    /**
     * The entry point for the program.
     * @param args
     * main method
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
        if(!levelUp){  // for level 0
            LEVEL0_BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0); // draw background
        }
        if(!levelUp_msg){ // for level 0 level up image
            LEVEL0_BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0); // draw background
        }
        if(levelUp && levelUp_msg){ // for level 1
            LEVEL1_BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        }
        // window close
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();}

        // game has not started
        if (!gameOn) {
            renderInstructionScreen(input);
        }

        // game over
        if(lifeBar.getLife() == 0){
            renderGameOverScreen();
        }

        // out of bound, lose a life
        if(birdOutOfBound()){
            if(lifeBar.getLife() > 0){
                lifeBar.setLife(lifeBar.getLife() - 1);
                bird.setY(BIRD_Y);
            }
        }

        // game won
        if(win){
            renderWinScreen();
        }

        // game active
        if (gameOn && !(lifeBar.getLife() == 0) && !win) {
            if (!levelUp) {
                bird.update(input);
                lifeBar.update();
                pipeSetMove(); // every pipe moves from right to left and update
                changeTimeScale(input); // Time Scale will change if press key
                Rectangle birdBox = bird.getBox();
                collisionTest(pipeSets0, birdBox);  // collision test for pipes and bird
                if(score == 0){
                    String scoreMsg = SCORE_MSG + score;
                    FONT.drawString(scoreMsg, 100, 100);
                }
            }
            // level up shows in screen
            if(levelUp && !leveledUp){
                renderLevelUpScreen();
                levelCount++;
                if(levelCount == 150) {
                    resetGame();
                }
            }
            //  Level 1
            if(leveledUp && gameOn){
                if(score == 0){
                    String scoreMsg = SCORE_MSG + score;
                    FONT.drawString(scoreMsg, 100, 100);
                }
                bird.update(input);
                lifeBar.update();
                pipeMoveLevel1(pipeSet1);
                changeTimeScale(input); // Time Scale will change
                Rectangle birdBox = bird.getBox();
                collisionTest1(pipeSet1, birdBox);
                if(pipeCount % WEAPON_FRAME_CHANGE ==0){
                    randomGenerateWeapon();
                    weapons.get(weapons.size()-1).setOverlap(overlapDetect(weapons.get(weapons.size()-1).getWeaponBox(), pipeSet1));
                }
                updateWeapons(birdBox, input);
            }
        }
    }

    /**
     * Render Instruction message on screen
     * @param input
     * type:Input
     */
    public void renderInstructionScreen(Input input) {
        FONT.drawString(INSTRUCTION_MSG, (Window.getWidth()/2.0-(FONT.getWidth(INSTRUCTION_MSG)/2.0)), (Window.getHeight()/2.0+(FONT_SIZE/2.0)));
        if(levelUp){
            FONT.drawString(INSTRUCTION_SHOOT_MSG,(Window.getWidth()/2.0-(FONT.getWidth(INSTRUCTION_SHOOT_MSG)/2.0)),(Window.getHeight()/2.0+(FONT_SIZE/2.0))+ INS_SHOOT_OFFSET);

        }
        if (input.wasPressed(Keys.SPACE)) {
            gameOn = true;
        }
    }

    /**
     * Render Game Over message on screen. This method comes from project 1 solution on Canvas and author is Betty Lin
     */
    public void renderGameOverScreen() {
        FONT.drawString(GAME_OVER_MSG, (Window.getWidth() / 2.0-(FONT.getWidth(GAME_OVER_MSG) / 2.0)), (Window.getHeight() / 2.0 + (FONT_SIZE / 2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth() / 2.0-(FONT.getWidth(finalScoreMsg) / 2.0)), (Window.getHeight() / 2.0+(FONT_SIZE / 2.0))+SCORE_MSG_OFFSET);
    }

    /**
     * Render Win messages on screen.
     * This method comes from project 1 solution on Canvas and author is Betty Lin
     */
    public void renderWinScreen() {
        FONT.drawString(CONGRATS_MSG, (Window.getWidth() / 2.0 - (FONT.getWidth(CONGRATS_MSG) / 2.0)), (Window.getHeight() / 2.0 + (FONT_SIZE / 2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth() / 2.0 - (FONT.getWidth(finalScoreMsg) / 2.0)), (Window.getHeight() / 2.0 + (FONT_SIZE / 2.0)) + SCORE_MSG_OFFSET);
    }

    /**
     * Render Level Up message on screen.
     * This method comes from project 1 solution on Canvas and author is Betty Lin
     */
    public void renderLevelUpScreen(){
        FONT.drawString(LEVEL_UP_MSG,(Window.getWidth() / 2.0 - (FONT.getWidth(GAME_OVER_MSG) / 2.0)), (Window.getHeight() / 2.0 + (FONT_SIZE / 2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth() / 2.0-(FONT.getWidth(finalScoreMsg) / 2.0)), (Window.getHeight() / 2.0+(FONT_SIZE / 2.0)) + SCORE_MSG_OFFSET);
    }

    /**
     * Check for out of bound. If our of bound, return true, otherwise return false
     * This method comes from project 1 solution on Canvas and author is Betty Lin
     * @return boolean
     */
    public boolean birdOutOfBound() {
        return (bird.getY() > Window.getHeight()) || (bird.getY() < 0);
    }

    /**
     * Check for collision between bird and pipe sets. If collided, return true, otherwise return false
     * This method comes from project 1 solution on Canvas and author is Betty Lin
     * @param birdBox
     * type:Rectangle
     * @param topPipeBox
     * type:Rectangle
     * @param bottomPipeBox
     * type:Rectangle
     * @return boolean
     */
    public boolean detectCollision(Rectangle birdBox, Rectangle topPipeBox, Rectangle bottomPipeBox) {
        return birdBox.intersects(topPipeBox) ||
                birdBox.intersects(bottomPipeBox);
    }

    /**
     * Change timescale.The timescale should not go below 1 or above 5. When pressed key L,
     * timescale increase by 1. When pressed Key K, timescale decrease by 1.
     * @param input
     * type: Input
     */
    public void changeTimeScale(Input input){
        if(input.wasPressed(Keys.L) && timeScale< MAX_SCALE){
            timeScale += 1;
        }
        if(input.wasPressed(Keys.K) && timeScale> MIN_SCALE){
            timeScale -= 1;
        }
    }

    /**
     * Let plastic pipe set moves in level 0
     */
    public void pipeSetMove(){  // level 0 pipe moves
        pipeCount++;
        double typePipeIndex;
        if(timeScale==1 && pipeCount % INITIAL_FRAME == 0){
            typePipeIndex = r.nextInt(3);
            pipeSets0.add(new PlasticPipe(typePipeIndex,leveledUp));
            flag.add(1);
        }
        if(timeScale !=1){
            frameCount = INITIAL_FRAME; // 100
            for(int i =1; i<timeScale;i++){
                frameCount = Math.round( frameCount/SPEED_CHANGE);}
            if(pipeCount % frameCount == 0){
                typePipeIndex = r.nextInt(3);
                pipeSets0.add(new PlasticPipe(typePipeIndex,leveledUp));
                flag.add(1);
            }
        }
        for(int i=0; i<flag.size();i++){
            if(flag.get(i) == 1){
                pipeSets0.get(i).update(timeScale);

            }
        }
    }

    /**
     * For level 0 collision test between pipe sets and bird
     * @param pipeSets
     * type: ArrayList
     * @param birdBox
     * type: Rectangle
     */
    public void  collisionTest(ArrayList<PlasticPipe> pipeSets, Rectangle birdBox){ // for level 0 collision test
        for(PlasticPipe i : pipeSets){ // i is a set of pipe
            Rectangle topPipeBox = i.getTopBox();
            Rectangle bottomPipeBox = i.getBottomBox();
            collision = detectCollision(birdBox, topPipeBox, bottomPipeBox);
            if(collision&& !i.getCollideOnce()){
                lifeBar.setLife(lifeBar.getLife() - 1);
                i.setCollideOnce(true);
            }
            updateScore(i);
        }
    }

    /**
     * Let plastic and steel pipe sets moves in level 0
     * @param pipeSet2
     * type: ArrayList
     */
    public void pipeMoveLevel1(ArrayList<Pipe> pipeSet2){
        double typePipeIndex;
        pipeCount++;
        typePipeIndex = r.nextDouble();
        if(timeScale == 1 && pipeCount % INITIAL_FRAME == 0){
            randomGeneratePipeType(typePipeIndex);
        }else{
            frameCount = INITIAL_FRAME;
            for(int i =1; i<timeScale;i++){
                frameCount = Math.round( frameCount/SPEED_CHANGE);}
            if(pipeCount % frameCount == 0){
                randomGeneratePipeType(typePipeIndex );
            }
        }
        for(int i=0; i< flagL1.size();i++){
            if(flagL1.get(i) == 1 || flagL1.get(i) == 2){
                pipeSet2.get(i).update(timeScale);}
        }
    }


    /**
     *  For level 1 collision test between pipe sets and bird
     * @param pipeSets1
     * type: ArrayList
     * @param birdBox
     * type: Rectangle
     */
    public void  collisionTest1(ArrayList<Pipe> pipeSets1, Rectangle birdBox){
        for(int i = 0; i < flagL1.size(); i++){
            Rectangle topPipeBox = pipeSets1.get(i).getTopBox();
            Rectangle bottomPipeBox = pipeSets1.get(i).getBottomBox();
            collision = detectCollision(birdBox, topPipeBox, bottomPipeBox);
            if(collision && !pipeSets1.get(i).getCollideOnce()){
                lifeBar.setLife(lifeBar.getLife() -1 );
                pipeSets1.get(i).setCollideOnce(true);
            }
            updateScore1(pipeSets1.get(i));
            if(flagL1.get(i) == 2){ // STEEL IMAGE
                SteelPipes steelPipe = (SteelPipes)pipeSets1.get(i);
                Rectangle topFlameBox = steelPipe.getTopBox();
                Rectangle bottomFlameBox = steelPipe.getBottomBox();
                if(steelPipe.getFrameCount() % FLAME == 0){
                    flameCollision = detectCollision(birdBox, topFlameBox, bottomFlameBox);
                    if(flameCollision && !steelPipe.getCollideWithFlame()){
                        steelPipe.setCollideWithFlame(true);
                    }
                }
            }
        }
    }

    /**
     * Used in level 1 to randomly generate plastic pipes and steel pipes
     * @param typePipeIndex
     * type: double
     */
    public void randomGeneratePipeType(double typePipeIndex) {
        if (!r.nextBoolean()) {
            pipeSet1.add(new PlasticPipe(typePipeIndex, leveledUp));
            flagL1.add(1);
        } else {
            pipeSet1.add(new SteelPipes(typePipeIndex, leveledUp));
            flagL1.add(2);
        }
    }

    /**
     * Used in level 1 to randomly generate weapons
     */
    public void randomGenerateWeapon(){
        if(r.nextBoolean()){
            weapons.add(new Rock(ROCK_IMAGE));
        }else weapons.add(new Boom(BOOM_IMAGE));
    }

    /**
     * update weapons
     * @param birdBox
     * type: Rectangle
     * @param input
     * type: Input
     */
    public void updateWeapons(Rectangle birdBox, Input input){
        for(Weapon i : weapons){
            Rectangle weaponBox = i.getWeaponBox();
            if(birdBox.intersects(weaponBox) && !i.getPickedUp() && !i.getOverlap()){
                if(!bird.getHoldWeapon()){
                    bird.setHoldWeapon(true);
                    i.setPickedUp(true);
                }
            }
            i.update(input, bird, timeScale);
            checkWeaponPipeCollision(i);
        }
    }

    /**
     * Detect collision between pipes and weapons
     * @param weapon
     * type: Weapon
     */
    public void checkWeaponPipeCollision(Weapon weapon){
        Rectangle weaponBox = weapon.getWeaponBox();
        for(int i =0; i<flagL1.size(); i++){
            Rectangle topPipeBox = pipeSet1.get(i).getTopBox();
            Rectangle bottomBox = pipeSet1.get(i).getBottomBox();
            if(!weapon.getDisappear() && weapon.getShoot()){
                boolean weaponCollision =detectCollision(weaponBox, topPipeBox,bottomBox);
                if(weapon.getWEAPON_IMAGE().equals(ROCK_IMAGE) && flagL1.get(i)==1 && weaponCollision ){ // Rock and plastic Pipe
                    weapon.setDisappear(true);
                    pipeSet1.get(i).setCollideOnce(true);
                    pipeSet1.get(i).setPassedOnce(true);
                    score+=1;
                }
                if(weapon.getWEAPON_IMAGE().equals(BOOM_IMAGE) && weaponCollision){
                    weapon.setDisappear(true);
                    pipeSet1.get(i).setCollideOnce(true);
                    pipeSet1.get(i).setPassedOnce(true);
                    score+=1;
                }
            }
        }
    }

    /**
     * Detect whether weapons are overlapped with pipes
     * @param weaponBox
     * type:Rectangle
     * @param pipeSet2
     * type:ArrayList
     * @return boolean
     */
    public boolean overlapDetect(Rectangle weaponBox, ArrayList<Pipe> pipeSet2){  // detect collision pipe and weapon
        for(Pipe i : pipeSet2){
            Rectangle topPipeBox = i.getTopBox();
            Rectangle bottomBox = i.getBottomBox();
            boolean overlapCollision = detectCollision(weaponBox, topPipeBox, bottomBox);
            if(overlapCollision){
                return true;
            }
        }
        return false;
    }

    /**
     * Reset game after finishing level 0
     */
    public void resetGame(){
        levelUp_msg =true;
        leveledUp = true;
        gameOn = false;
        lifeBar.setLife(LEVEL1_LIFE);
        lifeBar.setMaxLife(LEVEL1_LIFE);
        score=0;
        pipeCount =0;
        bird.setLevelUp(true);
        bird.setY(BIRD_Y);
        timeScale =1;
    }

    /**
     * Used in level 0 to update score
     * @param i
     * type:PlasticPIpe
     */
    public void updateScore(PlasticPipe i) { // for level 0
        if (bird.getX() > i.getTopBox().right() && !i.getPassedOnce() && !collision && i.getCollideOnce() ==false) {
            score += 1;
            i.setPassedOnce(true);
        }
        String scoreMsg = SCORE_MSG + score;
        FONT.drawString(scoreMsg, 100, 100);
        if (score == LEVEL_SCORE && !leveledUp) {
            levelUp = true;
        }
    }

    /**
     * Used in level 1 to update score
     * @param i
     * type:Pipe
     */
    public void updateScore1(Pipe i) { // for level 1
        if (bird.getX() > i.getTopBox().right() && !i.getPassedOnce() && !collision && i.getCollideOnce() ==false) {
            score += 1;
            i.setPassedOnce(true);
        }
        String scoreMsg = SCORE_MSG + score;
        FONT.drawString(scoreMsg, 100, 100);
        if(score == WIN_SCORE){
            win = true;
        }
    }


}


