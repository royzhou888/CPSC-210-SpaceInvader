package ca.ubc.cpsc210.spaceinvaders.model;

import java.awt.Color;
import java.awt.Rectangle;

/*
 * Represents a space invader.
 */
public class Invader {

	public static final Color COLOR = new Color(188, 27, 31);
	private static final int JIGGLE_X = 1;

	private int x;
	private int y;
	public int SIZE_Y = 15;
    public int SIZE_X = 15;
    public int DY = 2;
	public Missile invaderMissile;



	// EFFECTS: invader is positioned at coordinates (x, y)
	public Invader(int x, int y, int invaderSizeX, int invaderSizeY) {
		this.x = x;
		this.y = y;
		this.SIZE_X = invaderSizeX;
		this.SIZE_Y = invaderSizeY;
	}

	public int getSIZE_Y (){return SIZE_Y;}

	public int getSIZE_X(){return SIZE_X;}

	public int getDY(){return DY;}


	
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
	public boolean collidedWith(Missile m) {
		Rectangle invaderBoundingRect = new Rectangle(getX() - getSIZE_X() / 2, getY() - getSIZE_Y() / 2, getSIZE_X(), getSIZE_Y());
		Rectangle missileBoundingRect = new Rectangle(m.getX() - Missile.SIZE_X / 2, m.getY() - Missile.SIZE_Y/ 2,
				Missile.SIZE_X, Missile.SIZE_Y);
		return invaderBoundingRect.intersects(missileBoundingRect);
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
