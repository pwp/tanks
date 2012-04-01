package uk.ac.ed.inf.pwp.tanks;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.awt.image.ImageObserver;
import javax.swing.JPanel;


public class Window extends JPanel implements KeyListener {
	
	int i;
	private Tank t1;
	private Tank t2;
	private Missile m = null;
	private Terrain t;
	private String text;
	private String windtext;
	private int turn = 0;
	private double wind=0;
	private int score1;
	private int score2;
	Image tankimg1 = Toolkit.getDefaultToolkit().getImage("tank1.png");
	Image tankimg2 = Toolkit.getDefaultToolkit().getImage("tank2.gif");
	Image misimg = Toolkit.getDefaultToolkit().getImage("ball.gif");
	Image backgr = Toolkit.getDefaultToolkit().getImage("background.jpg");
	
	public Window() {
		this.setFocusable(true);
        this.addKeyListener(this);
        text = "Waiting...";
        i = 0;
        doWind();
        score1 = 0;
    	score2 = 0;
    	t = new Terrain(500);
    	t1 = new Tank(100,t.geth(100),Math.PI/4,100);
    	t2 = new Tank(650,t.geth(650),3*Math.PI/4,100);
	}
	
	public void paint(Graphics g) {
		  Graphics2D g2 = (Graphics2D) g;
		  super.paint(g);
		  g2.drawImage(backgr,0,-200,this);
		  //Strings
		  g.setColor(Color.BLACK);
		  g2.setFont(new Font("Monospaced", Font.PLAIN, 14));
		  g2.drawString(text,400-text.length()*4,25); 
		  g2.drawString("It's ", 322, 50);
		  if (turn==0) g2.setColor(Color.RED); else g2.setColor(Color.CYAN);
		  g2.drawString("Player "+(turn+1)+"'s", 362, 50);
		  g2.setColor(Color.BLACK);
		  g2.drawString("turn", 447, 50);
		  g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		  g2.drawString(windtext, (int) Math.round(400-windtext.length()*3.5), 70);
		  g2.drawString("Player 1", 20, 25);
		  g2.drawString("Score: " + score1, 20, 45);
		  g2.drawString("Player 2", 715, 25);
		  g2.drawString("Score: " + score2, 715, 45);
		  //Tank 1
		  g.setColor(Color.RED);
		  g2.drawImage(tankimg1, t1.getx(), t1.gety(), this);
		  Line2D lin1 = new Line2D.Float(t1.getx()+8, t1.gety()+8,(int) (Math.round(t1.getx()+8+(t1.getp()/4)*Math.cos(t1.geta()))),(int) Math.round(t1.gety()+8-(t1.getp()/4)*Math.sin(t1.geta())));
		  g2.draw(lin1);
		  //Tank 2
		  g.setColor(Color.CYAN);
		  g2.drawImage(tankimg2, t2.getx(), t2.gety(), this);
		  Line2D lin2 = new Line2D.Float(t2.getx()+8, t2.gety()+8,(int) (Math.round(t2.getx()+8+(t2.getp()/4)*Math.cos(t2.geta()))),(int) Math.round(t2.gety()+8-(t2.getp()/4)*Math.sin(t2.geta())));
	      g2.draw(lin2);
	      //Missile
		  if (m!=null) g2.drawImage(misimg, (int) Math.round(m.getx()), (int) Math.round(m.gety()), (ImageObserver) this);
		  //Terrain
		  g.setColor(Color.GRAY);
		  g2.draw(new Line2D.Float(300,0,300,800));
		  g2.draw(new Line2D.Float(500,0,500,800));
		  g2.setColor(new Color(20,120,0));
		  for (Rectangle r : t.getr()) {
			  g2.fill(r);
		  }
		  //Victory
		  if (victorycheck()==1) {
			  this.removeKeyListener(this);
			  g2.setFont(new Font("Monospaced", Font.PLAIN, 22));
			  g2.setColor(Color.RED);
			  g2.drawString("Player 1 wins the game!", 260, 300);
		  }
		  if (victorycheck()==2) {
			  this.removeKeyListener(this);
			  g2.setFont(new Font("Monospaced", Font.PLAIN, 22));
			  g2.setColor(Color.CYAN);
			  g2.drawString("Player 2 wins the game!", 260, 300);
		  }
		  g2.finalize();
	}
	
	public void run(){
		while(true) {
	          //i++;
	          if (m!=null) {
	        	  m.move(wind);
	        	  int check=collisionCheck();
	        	  if (m.getx()>=800 || m.getx()<=0 || m.gety()>=800 || check>0) {
	        		  if (check==0) text = ("Player "+(turn+1)+" misses!");
	        		  m=null;
	        		  turn = 1-turn;
	        		  doWind();
	        		  this.addKeyListener(this);
	        	  }
	          }
	          repaint();
	          try {
	               Thread.sleep(1000/60);
	          } catch (InterruptedException e) { ; }
		}
	}
	
	
	private int collisionCheck() {
		if (m.getBounds().intersects(otht().getBounds())) {
			text = ("Player "+(turn+1)+" hits!");
			if (turn==0) score1+=2; else score2+=2;
			return 3;
		}
		for (Rectangle r : t.getr()) {
			if (m.getBounds().intersects(r)) {
				if (dist(m.getBounds(), otht().getBounds())<=75) {
					text = ("Player "+(turn+1)+" partially hits!");
					if (turn==0) score1++; else score2++;
					return 2;
				}
				else {
					text = ("Player "+(turn+1)+" misses!");
					return 1;
				}
			}
		}
		return 0;
	}
	
	public void reset() {
		turn=0;
		text = ("Waiting...");
        i = 0;
        wind=2*(0.5-Math.random());
        score1 = 0;
    	score2 = 0;
    	t = new Terrain(500);
    	t1 = new Tank(100,t.geth(100),Math.PI/4,100);
    	t2 = new Tank(650,t.geth(650),3*Math.PI/4,100);
	}
	
	//Helper Functions
	//Retrieves the current active/inactive tank.
	private Tank curt () {
		if (turn==0) return t1;
		return t2;
	}
	private Tank otht () {
		if (turn==0) return t2;
		return t1;
	}
	//Returns distance between two rectangles' centers
	private double dist(Rectangle r1, Rectangle r2) { //returns distance between two rectangles' centers
		return Math.sqrt(
				Math.pow(Math.abs(r1.getCenterX()-r2.getCenterX()), 2) + 
				Math.pow(Math.abs(r1.getCenterY()-r2.getCenterY()), 2));
	}
	//Sets the Wind value and text
	private void doWind() {
		  double rand=Math.random();
		  if (rand<0.1)  { wind=0.15; windtext = "Wind is Weak, towards the East"; }
		  if (rand>=0.1 && rand<0.2) { wind=-0.15; windtext= "Wind is Weak, towards the West"; }
		  if (rand>=0.2 && rand<0.3)  { wind=0.3; windtext = "Wind is Medium, towards the East"; }
		  if (rand>=0.3 && rand<0.4) { wind=-0.3; windtext= "Wind is Medium, towards the West"; }
		  if (rand>=0.4 && rand<0.5)  { wind=0.45; windtext = "Wind is Strong, towards the East"; }
		  if (rand>=0.5 && rand<0.6) { wind=-0.45; windtext= "Wind is Strong, towards the West"; }
		  if (rand>=0.6 && rand<0.7)  { wind=0.6; windtext = "Wind is Very Strong, towards the East"; }
		  if (rand>=0.7 && rand<0.8) { wind=-0.6; windtext= "Wind is Very Strong, towards the West"; }
		  if (rand>=0.8) {wind=0; windtext= "No wind"; }
	}
	//Checks for Victory
	private int victorycheck() {
		if (score1>=10) return 1;
		if (score2>=10) return 2;
		return 0;
	}
	//End of Helper Functions
	
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
	    case KeyEvent.VK_LEFT:
	    	curt().changea(Math.PI/180);
	        break;
	    case KeyEvent.VK_RIGHT:
	    	curt().changea(-Math.PI/180);
	        break;
	    case KeyEvent.VK_UP:
	    	curt().changep(1);
	    	break;
	    case KeyEvent.VK_DOWN:
	    	curt().changep(-1);
	    	break;
	    case KeyEvent.VK_A:
	    	curt().changepos(-1, t);
	    	break;
	    case KeyEvent.VK_D:
	    	curt().changepos(1, t);
	    	break;
		case KeyEvent.VK_SPACE:
			m = new Missile (curt().getx(),curt().gety(),Math.cos(curt().geta())*curt().getp(),-Math.sin(curt().geta())*curt().getp());
			this.removeKeyListener(this);
			break;
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
