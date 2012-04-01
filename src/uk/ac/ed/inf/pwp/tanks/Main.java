package uk.ac.ed.inf.pwp.tanks;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Main extends JFrame implements ActionListener {
	
	Window w = new Window();

		public Main() {
			
			//Start of menu
	        JMenuBar menuBar;
	        JMenu filemenu;
	        JMenuItem ng,ex,man;
	        
	        //Create the menu bar.
	        menuBar = new JMenuBar();

	        //Create the menu.
	        filemenu = new JMenu("File");
	        filemenu.getAccessibleContext().setAccessibleDescription("General commands");
	        menuBar.add(filemenu);

	        //Menu items for the File Menu
	        ng = new JMenuItem("New Game",KeyEvent.VK_T);
	        ng.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
	        ng.getAccessibleContext().setAccessibleDescription("Starts a new game");
	        ng.addActionListener(this);
	        ng.setActionCommand("1");
	        filemenu.add(ng);
	        man = new JMenuItem("Manual", KeyEvent.VK_P);
	        man.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
	        man.getAccessibleContext().setAccessibleDescription("Shows the game manual");
	        man.addActionListener(this);
	        man.setActionCommand("2");
	        filemenu.add(man);
	        filemenu.addSeparator();
	        ex = new JMenuItem("Exit", KeyEvent.VK_P);
	        ex.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
	        ex.getAccessibleContext().setAccessibleDescription("Exits the game");
	        ex.addActionListener(this);
	        ex.setActionCommand("3");
	        filemenu.add(ex);
	        
	        this.setJMenuBar(menuBar);
	        //End of menu.
	        
	        add(w);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(800, 800);
	        setLocationRelativeTo(null);
	        setTitle("PWP Tanks");
	        setResizable(false);
	        setVisible(true);
	        w.run();
	    }

		public void actionPerformed(ActionEvent e) {
			switch (Integer.parseInt(e.getActionCommand())) {
			case 1:
				w.reset();
				break;
			case 2:
				this.setVisible(false);
				new Manual(this);
				break;
			case 3:
				System.exit(0);
				break;
			}
		}
		
	    public static void main(String[] args) {
	    	new Main();
	    }
}
