import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;


public class Manual extends JFrame implements ActionListener, KeyListener {
	private Button left = new Button("<<");
    private Button right = new Button(">>");
	private Button close = new Button("Close");
	private int page=1;
	Main m;
	
	public Manual(Main m) {
		super("How To Play PWP Tanks");
		this.m=m;
		setFocusable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setLayout(null);
        this.addKeyListener(this);
        left.setBounds(210, 210, 32, 22);
        right.setBounds(245, 210, 32, 22);
        close.setBounds(216, 238, 55, 25);
        add(left);
        add(right);
        add(close);
        left.addActionListener(this);
        left.setActionCommand("1");
        right.addActionListener(this);
        right.setActionCommand("2");
        close.addActionListener(this);
        close.setActionCommand("3");
        page++; page--;
	}
	
	public void paint(Graphics g) {
		  super.paint(g);
		  //g.setColor(Color.RED);
		  g.setFont(new Font("Monospaced", Font.PLAIN, 16));
		  switch (page) {
		  case 1:
			  g.drawString("Hello and welcome to PWP's awesome Tanks game!",14,50);
			  g.drawString("I hope you have fun!",14,72);
			  break;
		  case 2:
			  g.drawString("The goal of the game is to damage and destroy", 12, 50);
			  g.drawString("your opponent's tank.", 12, 72);
			  break;
		  }
		  g.setColor(Color.BLACK);
		  g.setFont(new Font("Monospaced", Font.PLAIN, 13));
		  g.drawString("Page " + page + "/2", 213, 230);
	}

	public void actionPerformed(ActionEvent e) {
		switch (Integer.parseInt(e.getActionCommand())) {
		case 1:
			if (page>1) page--;
			repaint();
			break;
		case 2:
			if (page<2) page++;
			repaint();
			break;
		case 3:
			m.setVisible(true);
			this.dispose();
			break;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
	    case KeyEvent.VK_LEFT:
	    	if (page>1) page--;
	    	repaint();
	    	break;
	    case KeyEvent.VK_RIGHT:
	    	if (page<2) page++;
	    	repaint();
	    	break;
		}
	}
	
	public static void main (String[] args) {
		new Manual(null);
	}

	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}

}
