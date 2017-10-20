package ca.ubc.cpsc210.spaceinvaders.model;

import java.awt.*;

/**
 * Created by royzh on 2017-10-05.
 */
public class Supply {

    public static final int SIZE_X = 25;
    public static final int SIZE_Y = 25;
    public static final int DY = 1;
    public static final Color COLOR = new Color(188, 164, 23);
    private static final int JIGGLE_X = 1;

    private int x;
    private int y;
    private Invader invader;


    // EFFECTS: invader is positioned at coordinates (x, y)
    public Supply(int x, int y) {
        this.x = x;
        this.y = y;
    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // MODIFIES: this
    // EFFECTS:  invader is moved down the screen DY units and randomly takes
    //           a step of size no greater than JIGGLE_X to the left or right
    public void move() {
        x = x + SIGame.RND.nextInt(2 * JIGGLE_X + 1) - JIGGLE_X;
        y = y + DY;

        handleBoundary();
    }


    // MODIFIES: none
    // EFFECTS:  returns true if this invader has collided with missile m,
    //           false otherwise
    public boolean collidedWith(Tank t) {
        Rectangle supplyBoundingRect = new Rectangle(getX() - SIZE_X / 2, getY() - SIZE_Y / 2, SIZE_X, SIZE_Y);
        Rectangle missileBoundingRect = new Rectangle(t.getX() - Missile.SIZE_X / 2, t.Y_POS - Missile.SIZE_Y/ 2,
                t.SIZE_X, t.SIZE_Y);
        return supplyBoundingRect.intersects(missileBoundingRect);
    }

    // MODIFIES: this
    // EFFECTS: tank is constrained to remain within horizontal boundaries of game
    private void handleBoundary() {
        if (x < 0)
            x = 0;
        else if (x > SIGame.WIDTH)
            x = SIGame.WIDTH;
    }


}
