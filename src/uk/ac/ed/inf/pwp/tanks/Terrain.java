package uk.ac.ed.inf.pwp.tanks;

import java.awt.Rectangle;

public class Terrain {
	
	private Rectangle r = new Rectangle();
	
	public Terrain(int a) {
		r.setBounds(0, a, 800, 800-a);
	}
	
	public Rectangle getr() {
		return r;
	}

}
