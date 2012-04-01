package uk.ac.ed.inf.pwp.tanks;

import java.awt.Rectangle;

public class Terrain {
	
	private Rectangle[] r = new Rectangle[400];
	
	public Terrain(int a) {
		r[0] = new Rectangle();
		r[0].setBounds(0, a, 8, 800-a);
		double rand=0;
		int y;
		int longcheck=0;
		boolean g=false;
		for (int i=1; i<400; i++) {
			r[i] = new Rectangle();
			if (i%50==0) {
				rand=Math.random();
				if (longcheck==3) g=false;
				else {
					if (longcheck==-3) g=true;
					else {
						if (rand>=0.5) g=true; else g=false; 
					}
				}
				if (g) longcheck++; else longcheck--;
			}
			if (g) {
				if (i%50<10 || i%50>40) y = (800-r[i-1].height) + (int) Math.round(Math.random());
				else y = (800-r[i-1].height) + (int) Math.round(2*Math.random());
			}
			else {
				if (i%50<10 || i%50>40) y = (800-r[i-1].height) - (int) Math.round(Math.random());
				else y = (800-r[i-1].height) - (int) Math.round(2*Math.random());
			}
			r[i].setBounds(i*2, y, 2, 800-y);
		}
	}
	
	public int geth(int x) {
		return (r[x/2+4].y-16);
	}
	
	public Rectangle[] getr() {
		return r;
	}

}
