package ca.ubc.cpsc210.spaceinvaders.model;

import java.awt.*;

/**
 * Represents a tank
 */
public class Tank {
	
	public static final int SIZE_X = 20;
	public static final int SIZE_Y = 15;
	public static final int DX = 10;
	public static final int Y_POS = SIGame.HEIGHT - 40;
	public static final Color COLOR = new Color(48, 250, 60);
    private static final int LEFT = -1;
    private static final int RIGHT = 1;

	private int direction;
    private int x;


	// EFFECTS: places tank at position (x, Y_POS) facing right.
	public Tank(int x) {
		this.x = x;
		this.direction = RIGHT;
	}
	
	public int getX() {
		return x;
	}

    // EFFECTS: returns true if tank is facing right, false otherwise
    public boolean isFacingRight() {
        return direction == RIGHT;
    }

	// MODIFIES: this
	// EFFECTS: tank is facing right
	public void faceRight() {
		direction = RIGHT;
	}

	// MODIFIES: this
	// EFFECTS: tank is facing left
	public void faceLeft() {
		direction = LEFT;
	}

	// MODIFIES: this
	// EFFECTS:  tank is moved DX units in whatever direction it is facing and is
	//           constrained to remain within vertical boundaries of game
	public void move() {
		x = x + direction * DX;

		
		handleBoundary();
	}

	// MODIFIES: this
	// EFFECTS: tank is constrained to remain within vertical boundaries of game
	private void handleBoundary() {
		if (x < 0)
			x = 0;
		else if (x > SIGame.WIDTH)
			x = SIGame.WIDTH;
	}

    public boolean collidedWith(Missile m) {
        Rectangle invaderBoundingRect = new Rectangle(getX() - SIZE_X / 2, Y_POS - SIZE_Y / 2, SIZE_X, SIZE_Y);
        Rectangle missileBoundingRect = new Rectangle(m.getX() - Missile.SIZE_X / 2, m.getY() - Missile.SIZE_Y/ 2,
                Missile.SIZE_X, Missile.SIZE_Y);
        return invaderBoundingRect.intersects(missileBoundingRect);
    }

}
