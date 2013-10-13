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
	
	public int keyJump = KeyEvent.VK_W;
	public int keyLeft = KeyEvent.VK_A;
	public int keyRight = KeyEvent.VK_D;
	public int keySink = KeyEvent.VK_S;
	public int keyPause = KeyEvent.VK_ESCAPE;
	
	
	public int characterWidth = 24;
	public int characterHeight = 36;
	public int fps = 1000;
	public int fallingSpeed = 20;
	public int fallingFrame = 0;
	public int floorHeight = 50;
	public int movementSpeed = 1;
	public int movementFrame = -1;
	public int movementFallingSpeed = 1;
	public int movementResetSpeed = 1;
	public int jumpingHeight = 110;
	public int jumpingSpeed = fallingSpeed;
	public int jumpingFrame = 0;
	public int sinkingDepth = jumpingHeight/2;
	public int sinkingSpeed = jumpingSpeed;
	public int sinkingFrame = 0;
	public int xScroll = 0;
	public int yScroll = 0;
	
	
	public boolean objectsDefined = false;
	public boolean falling = false;
	public boolean running = true;
	public boolean right = false;
	public boolean left = false;
	public boolean jumping = true;
	public boolean sinking = true;
	public boolean onFloor;
	public boolean inGame = true;
	
	
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
					System.out.println(character.y);
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
				if (e.getKeyCode() == keyPause) {
					if (inGame) {
					inGame = false;
					} else {
						inGame = true;
						
					}
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
			g.fillRect(character.x - xScroll, character.y, character.width, character.height);
			g.fillRect(floor.x - xScroll, floor.y, floor.width, floor.height);
		
		if(inGame != true) {
			g.setColor(Color.GREEN);
			g.drawString("PAUSED (Esc to Un-Pause)", 10,20 );
		}
			
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
			if(inGame) {
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
		if(character.y == 814) {
			onFloor = true;
		} else {
			onFloor = false;
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
				
				yScroll -= 1;
				
				jumpingFrame += 1;
				 
				} else {
					jumpingFrame = 0;
					jumping = false;
					
				}
			}
			//sinking
			if (sinking && !onFloor) {
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
					character.x += 1;
					xScroll += 1;
				}
				if(left)  {
					character.x -=1;
					xScroll -= 1;
					
				}
				movementFrame = -1;
			} else {
				movementFrame += 1;
				
			}
			
			
		fpsSetter();
		
		repaint();
		
			}
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
