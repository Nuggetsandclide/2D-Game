import java.awt.GridLayout;

import javax.swing.*;

public class Step2 extends JFrame {
    public Step3 panel;
   
	
	public Step2() {
    	  panel = new Step3 (this);
    	  setLayout(new GridLayout(1,1,0,0));
    	  add(panel);
    	  
    	  

	}

}
