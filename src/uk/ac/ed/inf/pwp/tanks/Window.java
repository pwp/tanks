package uk.ac.ed.inf.pwp.tanks;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.awt.image.ImageObserver;
import javax.swing.JPanel;


public class Window extends JPanel implements KeyListener {
	
	int i;
	private Tank t1 = new Tank(100,500,Math.PI/4,100);
	private Tank t2 = new Tank(650,500,3*Math.PI/4,100);
	private Missile m = null;
	private Terrain t;
	private TextField text = new TextField(15);
	private int turn = 0;
	private double wind=0;
	private int score1;
	private int score2;
	Image tankimg1 = Toolkit.getDefaultToolkit().getImage("tank1.png");
	Image tankimg2 = Toolkit.getDefaultToolkit().getImage("tank2.gif");
	Image misimg = Toolkit.getDefaultToolkit().getImage("bullet.gif");
	Image backgr = Toolkit.getDefaultToolkit().getImage("background.jpg");
	
	public Window() {
		this.setFocusable(true);
        this.addKeyListener(this);
		add(text);
        text.setText("Waiting...");
        i = 0;
        wind=2*(0.5-Math.random());
        score1 = 0;
    	score2 = 0;
    	t = new Terrain(515);
	}
	
	public void paint(Graphics g) {
		  Graphics2D g2 = (Graphics2D) g;
		  super.paint(g);
		  g2.drawImage(backgr,0,-200,this);
		  g.setColor(Color.BLACK);
		  //Strings
		  g2.drawString("Player "+(turn+1)+"'s turn", 352, 50);
		  if (wind>=0) g2.drawString("Wind is " + wind%.5 + " to the right", 310, 70);
		  else g2.drawString("Wind is "+ (-wind)%.5 + " to the left", 310, 70);
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
		  g2.drawImage(tankimg2, t2.getx(), t2.gety(), this);
		  Line2D lin2 = new Line2D.Float(t2.getx()+8, t2.gety()+8,(int) (Math.round(t2.getx()+8+(t2.getp()/4)*Math.cos(t2.geta()))),(int) Math.round(t2.gety()+8-(t2.getp()/4)*Math.sin(t2.geta())));
	      g2.draw(lin2);
	      //Missile
		  if (m!=null) g2.drawImage(misimg, (int) Math.round(m.getx()), (int) Math.round(m.gety()), (ImageObserver) this);
		  /*//Imaginary Ground
		  g2.setColor(new Color(0,180,0));
		  g2.fillRect(-10, 415, 820, 400);*/
		  //Terrain
		  g2.setColor(new Color(20,120,0));
		  g2.fill(t.getr());
		  g2.finalize();
	}
	
	public void run(){
		while(true) {
	          //i++;
	          if (m!=null) {
	        	  m.move(wind);
	        	  int check=collisionCheck();
	        	  if (m.getx()>=800 || m.getx()<=0 || m.gety()>=800 || check>0) {
	        		  if (check==0) text.setText("Player "+(turn+1)+" misses!");
	        		  text.repaint();
	        		  m=null;
	        		  turn = 1-turn;
	        		  wind=2*(0.5-Math.random());
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
			text.setText("Player "+(turn+1)+" hits!");
			if (turn==0) score1+=2; else score2+=2;
			return 3;
		}
		if (m.getBounds().intersects(t.getr())) {
			if (dist(m.getBounds(),otht().getBounds())<=75) {
				text.setText("Player "+(turn+1)+" partially hits!");
				if (turn==0) score1++; else score2++;
				return 2;
			}
			else {
				text.setText("Player "+(turn+1)+" misses!");
				return 1;
			}
		}
		return 0;
	}
	
	public void reset() {
		turn=0;
		text.setText("Waiting...");
        i = 0;
        wind=2*(0.5-Math.random());
        score1 = 0;
    	score2 = 0;
    	t = new Terrain(515);
		t1 = new Tank(100,500,Math.PI/4,100);
		t2 = new Tank(650,500,3*Math.PI/4,100);
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
	    	curt().changepos(-1);
	    	break;
	    case KeyEvent.VK_D:
	    	curt().changepos(1);
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
