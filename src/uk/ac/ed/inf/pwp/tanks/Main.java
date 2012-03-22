package uk.ac.ed.inf.pwp.tanks;
import javax.swing.JFrame;

public class Main extends JFrame {

		public Main() {
			Window w = new Window();
	        add(w);
	        
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(800, 800);
	        setLocationRelativeTo(null);
	        setTitle("PWP Tanks");
	        setResizable(false);
	        setVisible(true);
	        w.run();
	    }

	    public static void main(String[] args) {
	    	new Main();
	    }
}
