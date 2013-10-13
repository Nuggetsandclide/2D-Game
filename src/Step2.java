import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Step2 extends JFrame {
    public Step3 panel;
	public int PauseButtonX = Step.width /2 -157;
	public int PauseButtonY = Step.height /2 -20;
	public int PauseButtonWidth = 100;
	public int PauseButtonHeight = 50;
	
	public boolean PauseButtonClicked;
	
   
	
	public Step2() {
    	  panel = new Step3 (this);
    	  setLayout(new GridLayout(1,1,0,0));
    	  add(panel);

	}

}
