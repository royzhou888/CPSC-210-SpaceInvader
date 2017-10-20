package ca.ubc.cpsc210.spaceinvaders.test;

import ca.ubc.cpsc210.spaceinvaders.model.Invader;
import ca.ubc.cpsc210.spaceinvaders.model.Missile;
import ca.ubc.cpsc210.spaceinvaders.model.SIGame;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Unit tests for the Invader class.
 */
public class InvaderTest {
	private static final int XLOC = SIGame.WIDTH / 2;
	private static final int YLOC = 50;
	private Invader invader;
	
	@Before
	public void runBefore() {
		invader = new Invader(XLOC, YLOC,15,15);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(XLOC, invader.getX());
        assertEquals(YLOC, invader.getY());
	}
	
	@Test
	public void testUpdate() {
		final int NUM_UPDATES = 8;
		
		invader.move();
		// can't test XLOC due to random jiggle behaviour
		assertEquals(YLOC + invader.getDY(), invader.getY());
		
		for(int count = 1; count < NUM_UPDATES; count++) {
			invader.move();
		}
		
		assertEquals(YLOC + NUM_UPDATES * invader.getDY(), invader.getY());
	}
	
	@Test 
	public void testCollideWith() {
		Missile m = new Missile(0, 0);
		assertFalse(invader.collidedWith(m));
		
		m = new Missile(invader.getX(), invader.getY());
		assertTrue(invader.collidedWith(m));
		
		m = new Missile(invader.getX() + invader.getSIZE_X() / 2 + Missile.SIZE_X / 2, invader.getY());
		assertTrue(invader.collidedWith(m));
		
		m = new Missile(invader.getX() + invader.getSIZE_X()/ 2 + Missile.SIZE_X / 2 + 1, invader.getY());
		assertFalse(invader.collidedWith(m));
		
		m = new Missile(invader.getX() - invader.getSIZE_X() / 2 - Missile.SIZE_X / 2, invader.getY());
		assertTrue(invader.collidedWith(m));
		
		m = new Missile(invader.getX() - invader.getSIZE_X() / 2 - Missile.SIZE_X / 2 - 1, invader.getY());
		assertFalse(invader.collidedWith(m));
		
		m = new Missile(invader.getX(), invader.getY() + invader.getSIZE_Y() / 2 + Missile.SIZE_Y / 2);
		assertTrue(invader.collidedWith(m));

		m = new Missile(invader.getX(), invader.getY() + invader.getSIZE_Y() / 2 + Missile.SIZE_Y / 2 + 1);
		assertFalse(invader.collidedWith(m));
	}
	
}
