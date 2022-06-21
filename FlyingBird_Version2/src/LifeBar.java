import bagel.Image;

/**
 * LifeBar class used in level 0 and level 1
 */
public class LifeBar {
    private final Image FULL_HEART = new Image("res/level/fullLife.png");
    private final Image EMPTY_HEART = new Image("res/level/noLife.png");
    private int maxLife;
    private int life; // current life
    private final int HEART_X_INITIAL = 100;
    private final int HEART_Y = 15;
    private final int HEART_GAP = 50;
    private final int LEVEL_0_LIFE = 3;
    private int heartX = 100;

    /**
     * Set maxLife is 3 and life is 3 in constructor because game starts from level 0
     */
    public LifeBar(){
        maxLife = LEVEL_0_LIFE; //3
        life = LEVEL_0_LIFE;
    }

    /**
     * update life bar
     */
    public void update(){
        renderLifeBar();
    }

    /**
     * set Life value
     * @param life
     * type:int
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * get Life value
     * @return int
     */
    public int getLife() {
        return life;
    }

    /**
     * set MaxLife
     * @param maxLife
     * type:int
     */
    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    /**
     * Draw life bar image on screen
     */
    public void renderLifeBar() {
        heartX = HEART_X_INITIAL;
        for(int i =1; i <= life; i++){
            FULL_HEART.drawFromTopLeft(heartX,HEART_Y);
            heartX += HEART_GAP;
        }
       for(int j = 1; j<= (maxLife-life); j++){
           EMPTY_HEART.drawFromTopLeft(heartX,HEART_Y);
           heartX += HEART_GAP;
       }
    }

}
