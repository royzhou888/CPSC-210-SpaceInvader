package ca.ubc.cpsc210.spaceinvaders.ui;

import ca.ubc.cpsc210.spaceinvaders.model.*;

import javax.swing.*;
import java.awt.*;

/*
 * The panel in which the game is rendered.
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	private static final String OVER = "Game Over!";
	private static final String REPLAY = "R to replay";
	private SIGame game;

	private Image bg;

	// EFFECTS:  sets size and background colour of panel,
	//           updates this with the game to be displayed
	public GamePanel(SIGame g) {
		setPreferredSize(new Dimension(SIGame.WIDTH, SIGame.HEIGHT));
		setBackground(Color.GRAY);
		this.game = g;
        this.bg = new ImageIcon("C:\\Users\\royzh\\Google Drive\\Education\\BCS\\CPSC 210\\Lecture\\SpaceInvadersComplete\\src\\ca\\ubc\\cpsc210\\spaceinvaders\\Resources\\bg.jpg").getImage();


    }
	
	@Override
	protected void paintComponent(Graphics g) { 
		super.paintComponent(g);


		g.drawImage(bg,0,0,this);

		drawGame(g);
		
		if (game.isOver()) {
			gameOver(g);
		}
	}

	// MODIFIES: g
	// EFFECTS:  draws the game onto g
	private void drawGame(Graphics g) {
		drawTank(g);
		drawInvaders(g);
		drawMissiles(g);
		drawInvaderMissiles(g);
		drawSupplies(g);
	}

	// MODIFIES: g
	// EFFECTS:  draws the tank onto g
	private void drawTank(Graphics g) {
		Tank t = game.getTank();
		Color savedCol = g.getColor();
		g.setColor(Tank.COLOR);
		g.fillRect(t.getX() - Tank.SIZE_X / 2, Tank.Y_POS - Tank.SIZE_Y / 2, Tank.SIZE_X, Tank.SIZE_Y);
        Polygon tankFront = createTankFront(t);
        g.fillPolygon(tankFront);
		g.setColor(savedCol);
	}

    // EFFECTS: returns a polygon that represents front of tank
    private Polygon createTankFront(Tank t) {
        Polygon tankFront = new Polygon();

        if (t.isFacingRight()) {
            tankFront.addPoint(t.getX() + Tank.SIZE_X / 2, Tank.Y_POS + Tank.SIZE_Y / 2);
            tankFront.addPoint(t.getX() + Tank.SIZE_X, Tank.Y_POS);
            tankFront.addPoint(t.getX() + Tank.SIZE_X / 2, Tank.Y_POS - Tank.SIZE_Y / 2);
        }
        else {
            tankFront.addPoint(t.getX() - Tank.SIZE_X / 2, Tank.Y_POS + Tank.SIZE_Y / 2);
            tankFront.addPoint(t.getX() - Tank.SIZE_X, Tank.Y_POS);
            tankFront.addPoint(t.getX() - Tank.SIZE_X / 2, Tank.Y_POS - Tank.SIZE_Y / 2);
        }

        return tankFront;
    }

	// MODIFIES: g
	// EFFECTS:  draws the invaders onto g
	private void drawInvaders(Graphics g) {
		for(Invader next : game.getInvaders()) {
			drawInvader(g, next);
		}
	}

	// MODIFIES: g
	// EFFECTS:  draws the invader i onto g
	private void drawInvader(Graphics g, Invader i) {
		Color savedCol = g.getColor();
		g.setColor(Invader.COLOR);
		g.fillOval(i.getX() - i.getSIZE_X() / 2, i.getY() - i.getSIZE_Y()/ 2, i.getSIZE_X(), i.getSIZE_Y());
		g.setColor(savedCol);
	}

	// MODIFIES: g
	// EFFECTS:  draws the missiles onto g
	private void drawMissiles(Graphics g) {
		for(Missile next : game.getMissiles()) {
			drawMissile(g, next);
		}
	}

    private void drawInvaderMissiles(Graphics g) {
        for(Missile next : game.getInvaderMissiles()) {
            drawInvaderMissile(g, next);
        }
    }

    private void drawInvaderMissile(Graphics g, Missile m) {
        Color savedCol = g.getColor();
        g.setColor(Missile.COLOR);
        g.fillOval(m.getX() - Missile.SIZE_X / 2, m.getY() - Missile.SIZE_Y / 2, Missile.SIZE_X, Missile.SIZE_Y);
        g.setColor(savedCol);
    }

    private void drawSupplies(Graphics g) {
        for(Supply next : game.getSupplies()) {
            drawSupply(g, next);
        }
    }

    private void drawSupply(Graphics g,Supply s) {
        Color savedCol = g.getColor();
        g.setColor(Supply.COLOR);
        g.fillOval(s.getX() - Supply.SIZE_X / 2, s.getY() - Supply.SIZE_Y / 2, Supply.SIZE_X, Supply.SIZE_Y);
        g.setColor(savedCol);

    }

	// MODIFIES: g
	// EFFECTS:  draws the missile m onto g
	private void drawMissile(Graphics g, Missile m) {
		Color savedCol = g.getColor();
		g.setColor(Missile.COLOR);
		g.fillOval(m.getX() - Missile.SIZE_X / 2, m.getY() - Missile.SIZE_Y / 2, Missile.SIZE_X, Missile.SIZE_Y);
		g.setColor(savedCol);
	}

	// MODIFIES: g
	// EFFECTS:  draws "game over" and replay instructions onto g
	private void gameOver(Graphics g) {
		Color saved = g.getColor();
		g.setColor(new Color( 0, 0, 0));
		g.setFont(new Font("Arial", Font.BOLD, 20));
		FontMetrics fm = g.getFontMetrics();
		centreString(OVER, g, fm, SIGame.HEIGHT / 2);
		centreString(REPLAY, g, fm, SIGame.HEIGHT / 2 + 50);
		g.setColor(saved);
	}

	// MODIFIES: g
	// EFFECTS:  centres the string str horizontally onto g at vertical position yPos
	private void centreString(String str, Graphics g, FontMetrics fm, int yPos) {
		int width = fm.stringWidth(str);
		g.drawString(str, (SIGame.WIDTH - width) / 2, yPos);
	}
}
