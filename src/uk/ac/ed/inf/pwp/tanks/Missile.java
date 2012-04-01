package uk.ac.ed.inf.pwp.tanks;

import java.awt.Rectangle;

public class Missile {
	private double x;
	private double y;
	private double speedx;
	private double speedy;
	
	public Missile(double x, double y, double sx, double sy) {
		this.x = x;
		this.y = y;
		speedx = sx;
		speedy = sy;
	}
	
	public double getx() {
		return x;
	}
	public double gety() {
		return y;
	}
	public double getsx() {
		return speedx;
	}
	public double getsy() {
		return speedy;
	}
	
	public void changex(double dx) {
		x+=dx;
	}
	public void changey(double dy) {
		y+=dy;
	}
	public void changesx(double dsx) {
		speedx+=dsx;
	}
	public void changesy(double dsy) {
		speedy+=dsy;
	}
	
	public void move(double wind) {
		x+=(speedx/30);
		y+=(speedy/30);
		speedy+=1;
		speedx+=wind;
	}
	
	public Rectangle getBounds() {
        return new Rectangle((int) Math.round(x),(int) Math.round(y), 10, 10);
    }
	
}
