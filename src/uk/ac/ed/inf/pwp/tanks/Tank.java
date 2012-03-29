package uk.ac.ed.inf.pwp.tanks;
import java.awt.Rectangle;



public class Tank {
	
	private int x;
	private int y;
	private double angle;
	private int power;
	
	public int getx() {
		return x;
	}
	public int gety() {
		return y;
	}
	public double geta() {
		return angle;
	}
	public int getp() {
		return power;
	}
	
	public Tank (int x, int y, double a, int p) {
		this.x=x;
		this.y=y;
		angle=a;
		power=p;
	}
	
	public void changepos(int p) {
		x+=p;
	}
	
	public void changea(double a) {
		angle+=a;
	}
	
	public void changep(int p) {
		if (power>=250 && p>0) {}
		else if (power<=40 && p<0) {} else power+=p;
	}
	
	public Rectangle getBounds() {
        return new Rectangle(x, y, 16, 16);
    }


}
