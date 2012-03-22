package uk.ac.ed.inf.pwp.tanks;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.awt.image.ImageObserver;
import javax.swing.JPanel;


public class Window extends JPanel implements KeyListener {
	
	int i;
	private Tank t1 = new Tank(100,400,Math.PI/4,100);
	private Tank t2 = new Tank(650,400,3*Math.PI/4,100);
	private Missile m = null;
	private TextField text = new TextField(12);
	private int turn = 0;
	Image tankimg1 = Toolkit.getDefaultToolkit().getImage("tank1.png");
	Image tankimg2 = Toolkit.getDefaultToolkit().getImage("tank2.gif");
	Image misimg = Toolkit.getDefaultToolkit().getImage("bullet.gif");
	
	public Window() {
		this.setFocusable(true);
        this.addKeyListener(this);
		add(text);
        text.setText("Waiting...");
        i = 0;
	}
	
	public void paint(Graphics g) {
		  Graphics2D g2 = (Graphics2D) g;
		  super.paint(g);
		  g2.drawString("Player "+(turn+1)+"'s turn", 355, 50);
		  //Tank 1
		  g2.drawImage(tankimg1, t1.getx(), t1.gety(), this);
		  Line2D lin1 = new Line2D.Float(t1.getx()+8, t1.gety()+8,(int) (Math.round(t1.getx()+8+(t1.getp()/4)*Math.cos(t1.geta()))),(int) Math.round(t1.gety()+8-(t1.getp()/4)*Math.sin(t1.geta())));
		  g2.draw(lin1);
		  //Tank 2
		  g2.drawImage(tankimg2, t2.getx(), t2.gety(), this);
		  Line2D lin2 = new Line2D.Float(t2.getx()+8, t2.gety()+8,(int) (Math.round(t2.getx()+8+(t2.getp()/4)*Math.cos(t2.geta()))),(int) Math.round(t2.gety()+8-(t2.getp()/4)*Math.sin(t2.geta())));
	      g2.draw(lin2);
	      //Missile
		  if (m!=null) g2.drawImage(misimg, (int) Math.round(m.getx()), (int) Math.round(m.gety()), (ImageObserver) this);
		  //Imaginary Ground
		  g2.setColor(new Color(0,180,0));
		  g2.fillRect(-10, 415, 820, 400); 
		  g2.finalize();
	}
	
	public void run(){
		while(true) {
	          //i++;
	          if (m!=null) {
	        	  m.move();
	        	  if (m.getx()>=800 || (m.getx()<=0) || (m.gety()>=800) || (collisionCheck())) {
	        		  if (!collisionCheck()) text.setText("Player "+(turn+1)+" misses!");
	        		  m=null;
	        		  turn = 1-turn;
	        		  this.addKeyListener(this);
	        	  }
	          }
	          repaint();
	          try {
	               Thread.sleep(1000/60);
	          } catch (InterruptedException e) { ; }
		}
	}
	
	private boolean collisionCheck() {
		if (m.getBounds().intersects(otht().getBounds())) {
			text.setText("Player "+(turn+1)+" hits!");
			return true;
		}
		return false;
	}
	
	//Retrieves the current active/inactive tank.
	private Tank curt () {
		if (turn==0) return t1;
		return t2;
	}
	private Tank otht () {
		if (turn==0) return t2;
		return t1;
	}
	
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
		case KeyEvent.VK_SPACE:
			m = new Missile (curt().getx(),curt().gety(),Math.cos(curt().geta())*curt().getp(),-Math.sin(curt().geta())*curt().getp());
			this.removeKeyListener(this);
			break;
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
