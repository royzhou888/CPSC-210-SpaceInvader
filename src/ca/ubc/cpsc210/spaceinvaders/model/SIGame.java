package ca.ubc.cpsc210.spaceinvaders.model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Represents a space invaders game.
 */
public class SIGame {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	public static final int MAX_MISSILES = 30;

	public static final Random RND = new Random();
	private static final int INVASION_PERIOD = 200;   // on average, one invader each 250 updates
    private static final int SUPPLIES_PERIOD = 300;   // on average, one invader each 250 updates


    public static double missilesFired = 1;
    public static int enemyKilled = 0;
	private List<Missile> missiles;
	private List<Invader> invaders;
	private List<Missile> invaderMissiles;
	private List<Supply> supplies;
	private Tank tank;
	private boolean isGameOver;
	private int numInvadersDestroyed;
	private int invaderSizeX;
	private int invaderSizeY;
	public int counter;

	// EFFECTS:  creates empty lists of missiles and invaders, centres tank on screen
	public SIGame() {
		missiles = new ArrayList<Missile>();
		invaders = new ArrayList<Invader>();
		invaderMissiles = new ArrayList<Missile>();
		supplies = new ArrayList<Supply>();
		setUp();
		invaderSizeX = 15;
		invaderSizeY = 15;
	}

	// MODIFIES: this
	// EFFECTS:  updates tank, missiles and invaders
	public void update() {
		moveMissiles();
		moveInvaders();
		moverInvaderMissiles();
		moveSupplies();
		tank.move();
		
		checkMissiles();
        invade();
        provideSupply();
		checkCollisions();
		checkSupplyCollisions();
		checkGameOver();
        counter++;
	}


	// MODIFIES: this
	// EFFECTS:  turns tank, fires missiles and resets game in response to
	//           given key pressed code
	public void keyPressed(int keyCode) {
		if (keyCode == KeyEvent.VK_SPACE)
			fireMissile();
		else if (keyCode == KeyEvent.VK_R && isGameOver)
			setUp();
		else if (keyCode == KeyEvent.VK_X)
			System.exit(0);
		else
			tankControl(keyCode);
	}
	
	// Exercise: fill in the documentation for this method
	public boolean isOver() {
		return isGameOver;
	}
	
	public int getNumMissiles() {
		return missiles.size();
	}
	
	public int getNumInvadersDestroyed() {
		return numInvadersDestroyed;
	}
	
	public List<Invader> getInvaders() {
		return invaders;
	}
	
	public List<Missile> getMissiles() {
		return missiles;
	}

    public List<Missile> getInvaderMissiles() {
        return invaderMissiles;
    }

    public List<Supply> getSupplies() {
        return supplies;
    }
	
	public Tank getTank() {
		return tank;
	}

	// MODIFIES: this
	// EFFECTS:  clears list of missiles and invaders, initializes tank
	private void setUp() {
		invaders.clear();
		missiles.clear();
		tank = new Tank(WIDTH / 2);
		isGameOver = false;
		numInvadersDestroyed = 0;
	}

	// MODIFIES: this
	// EFFECTS:  fires a missile if max number of missiles in play has
	//           not been exceeded, otherwise silently returns
	private void fireMissile() {
		if (missiles.size() < MAX_MISSILES) {
			Missile m = new Missile(tank.getX(), Tank.Y_POS);
			missiles.add(m);
			missilesFired++;
		}
	}


	// MODIFIES: this
	// EFFECTS: turns tank in response to key code
	private void tankControl(int keyCode) {
        if (keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT) {
            tank.faceLeft();
            tank.move();
        } else if (keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT){
            tank.faceRight();
            tank.move();
        }
    }

	// MODIFIES: this
	// EFFECTS: moves the missiles
	private void moveMissiles() {
		for (Missile next : missiles ) {
			next.move();
		}	
	}

    private void moverInvaderMissiles() {
        for (Missile next : invaderMissiles ) {
            next.invaderMissileMover();
        }
    }

	// MODIFIES: this
	// EFFECTS: moves the invaders
	private void moveInvaders() {
		for (Invader next : invaders) {
			next.move();
           //createInvaderMissiles(next);
		}
	}

    private void moveSupplies() {
        for (Supply next : supplies) {
            next.move();
            //createInvaderMissiles(next);
        }
    }

	// MODIFIES: this
	// EFFECTS:  removes any missile that has traveled off top of screen
	private void checkMissiles() {
		List<Missile> missilesToRemove = new ArrayList<Missile>();
		
		for (Missile next : missiles) {
            if (next.getY() < 0) {
                missilesToRemove.add(next);
			}
		}
		
		missiles.removeAll(missilesToRemove);
	}

	private void createInvaderMissiles (Invader i){

        Missile m = new Missile(i.getX(), i.getY());
            invaderMissiles.add(m);

    }
	
	// Exercise: add the documentation for this method
	private void invade() {
		if (RND.nextInt(INVASION_PERIOD) < 1) {
			Invader i = new Invader(RND.nextInt(SIGame.WIDTH), 0,invaderSizeX,invaderSizeY);
            invaders.add(i);
            createInvaderMissiles(i);
		}
	}

    private void provideSupply() {
        if (RND.nextInt(SUPPLIES_PERIOD) < 1) {
            Supply i = new Supply(RND.nextInt(SIGame.WIDTH), 0);
            supplies.add(i);
        }
    }

	// MODIFIES: this
	// EFFECTS:  removes any invader that has been shot with a missile
	//           and removes corresponding missile from play
	private void checkCollisions() {
		List<Invader> invadersToRemove = new ArrayList<Invader>();
		List<Missile> missilesToRemove = new ArrayList<Missile>();
		
		for (Invader target : invaders) {
			if (checkInvaderHit(target, missilesToRemove)) {
				invadersToRemove.add(target);
				enemyKilled++;
			}
		}
		
		invaders.removeAll(invadersToRemove);
		missiles.removeAll(missilesToRemove);
	}
	
	// Exercise:  fill in the documentation for this method
	private boolean checkInvaderHit(Invader target,  List<Missile> missilesToRemove) {
		for (Missile next : missiles) {
			if (target.collidedWith(next)) {
                missilesToRemove.add(next);
                numInvadersDestroyed++;
                return true;
            }
		}
		
		return false;
	}


	private void slowDownInvader(){
        for(Invader i: invaders){
            invaderSizeX = invaderSizeX+2;
            invaderSizeY = invaderSizeY+2;

        }

    }

    private void checkSupplyCollisions() {
        List<Supply> suppliesToRemove = new ArrayList<Supply>();


        for (Supply target : supplies) {
            if (checkSupplyHit(target, suppliesToRemove)) {
                suppliesToRemove.add(target);
                slowDownInvader();

            }
        }

        supplies.removeAll(suppliesToRemove);

    }

    // Exercise:  fill in the documentation for this method
    private boolean checkSupplyHit(Supply target, List<Supply> suppliesToRemove) {
        if (target.collidedWith(tank)) {
                suppliesToRemove.add(target);

                return true;
            }


        return false;
    }

	// MODIFIES: this
	// EFFECTS:  if an invader has landed, game is marked as
	//           over and lists of invaders & missiles cleared
	private void checkGameOver() {
		for (Invader next : invaders) {
			if (next.getY() > HEIGHT) {
				isGameOver = true;
			}
		}


		
		if (isGameOver) {
			invaders.clear();
			missiles.clear();
		}
	}
}
