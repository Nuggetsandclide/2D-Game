import javax.swing.*;

public class Step {
	public static Step2 f;
	public static int width = 1600;
	//Easily change width and height
	public static int height = 900;
	
	
	public static void main(String[] args) {
		f = new Step2();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setTitle("Evolution");
		f.setSize(width, height);
		f.setLocationRelativeTo(null);
		
	}

}
