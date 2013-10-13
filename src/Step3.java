import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Step3 extends JPanel implements Runnable {
	public Rectangle character;
	public Rectangle floor;
	
	public int keyJump = KeyEvent.VK_SPACE;
	public int keyLeft = KeyEvent.VK_A;
	public int keyRight = KeyEvent.VK_D;
	public int keySink = KeyEvent.VK_S;
	
	
	public int characterWidth = 24;
	public int characterHeight = 36;
	public int fps = 1000;
	public int fallingSpeed = 20;
	public int fallingFrame = 0;
	public int floorHeight = 70;
	public int movementSpeed = 1;
	public int movementFrame = -1;
	public int movementFallingSpeed = 1;
	public int movementResetSpeed = 1;
	public int jumpingHeight = 50;
	public int jumpingSpeed = fallingSpeed;
	public int jumpingFrame = 0;
	public int sinkingDepth = 50;
	public int sinkingSpeed = jumpingSpeed;
	public int sinkingFrame = 0;
	
	
	public boolean objectsDefined = false;
	public boolean falling = false;
	public boolean running = true;
	public boolean right = false;
	public boolean left = false;
	public boolean jumping = true;
	public boolean sinking = true;
	
	
	public Thread game ;
	
	
	public Step3(Step2 f) {
		setBackground(Color.BLACK);
	
		defineObjects();
		
		game = new Thread(this) ;
		game.start();
		
		f.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == keyLeft) {
					left = true;
				}
				if(e.getKeyCode() == keyRight) {
					right = true;
				}
				if (e.getKeyCode() == keyJump) {
					jumping = true;
					
				}
				if (e.getKeyCode() == keySink) {
					sinking = true;
					
				}
			}
			public void keyReleased(KeyEvent e)  {
				if(e.getKeyCode() == keyLeft) {
					left = false;
				}
				if(e.getKeyCode() == keyRight) {
					right = false;
				}
			    if(e.getKeyCode() == keyJump) {
			    	jumping = false;
			    }
				if (e.getKeyCode() == keySink) {
					sinking = false;
					
				}
			
			}
		
		}) ;
	}//public Step3

	public void paintComponent(Graphics g)   {
		super.paintComponent(g);
		
		if (objectsDefined) {
			
			g.setColor(Color.WHITE);
			g.fillRect(character.x, character.y, character.width, character.height);
			g.fillRect(floor.x, floor.y, floor.width, floor.height);
		}//if objectsDefined
		
	}//paintComponents
	
	public void defineObjects() {
		
		character = new Rectangle((Step.width/2) - (characterWidth/2), (Step.height/2) - (characterHeight/2) , characterWidth , characterHeight );
		floor = new Rectangle(-10, Step.height-floorHeight, Step.width +10, floorHeight);
		
		 
		
		objectsDefined = true ;
		
		repaint();
	}//defineObjects

	public void run() {
		while(running) { // Falling/landing
			Point pt1 = new Point(character.x, character.y + character.height);
			Point pt2 = new Point(character.x + character.width, character.y + character.height);
		if(jumping != true) {
			if(fallingFrame >= fallingSpeed) {
				if(floor.contains(pt1) || floor.contains(pt2)) {
					
					falling = false;
					
				} else {
					character.y += 1;
					falling = true;
					
				}//else (after if)
				
				if(falling) {
					character.y += 1;
					
				}
				
				fallingFrame = 0;
				
			} else {
				fallingFrame += 1;
				
			}//else2 (after if)
			
		}
			//Movement speed check
			if(falling)  {
				movementSpeed = movementFallingSpeed;
				
			} else {
				movementSpeed = movementResetSpeed;
				
			}
			//jumping
			if (jumping) {
			 if (jumpingFrame <= jumpingHeight){
				
				character.y -= 1;
				
				jumpingFrame += 1;
				 
				} else {
					jumpingFrame = 0;
					jumping = false;
					
				}
			}
			//sinking
			if (sinking) {
				if (sinkingFrame <= sinkingDepth) {
					
					character.y += 1;
					
					sinkingFrame += 1;
					
				} else {
					sinkingFrame = 0;
					sinking = false;
					
				}
			}
			// movement
			
			if(movementFrame >= movementSpeed) {
				if(right)  {
					character.x +=1;
				}
				if(left)  {
					character.x -=1;
				}
				movementFrame = -1;
			} else {
				movementFrame += 1;
				
			}
			
			
		fpsSetter();
		
		repaint();
		
		}//while running
		
	}//public void run
	
	public void fpsSetter() {
		try {
			game.sleep(fps/1000);
		} catch(Exception e) {
			e.printStackTrace();
			
		}//Catch
		
	}//fpsSetter
	
	
}
