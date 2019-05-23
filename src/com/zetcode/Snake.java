package com.zetcode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JPanel implements ActionListener {
	
private final int P_WIDTH=400;				/*all the constants that will be used in the game */
private final int P_LENGHT=400;
private final int DOT_SIZE=10;
private final int ALL_DOTS=1600;
private final int APPLE_RANDOM_POSSITION=30;
private final int DELAY=100;


private final int x[]= new int [ALL_DOTS];		// one array that will store 
private final int y[]= new int [ALL_DOTS];

private int dots; 				// length of the snake 
private int appleX;  			// apples position X & Y they used for the random position 
private int appleY;

private int score=-1;

private boolean leftDirection= false; 
private boolean rightDirection= true; 
private boolean upDirection=false;
private boolean downDirection=false;
private boolean inGame=true;          // more constants...movements of the snake!!! 
private boolean isPaused=false;

private Timer timer;  		// constants!!!!
private Image dot;
private Image apple;
private Image head; 

public 	Snake() {			  //CONSTRUCTOR
	     initBoard();                    
}
			// DRAW THE PANEL 
private void initBoard() {    // method to initialise Board
	addKeyListener(new TAdapter());  // a built in method this is a design for patterns
	setBackground(Color.BLACK);
	setFocusable(true);  			// this is  a built in method
	setPreferredSize(new Dimension (P_WIDTH,P_LENGHT)); 			// another built in method which is similar to set size()
	loadImages();
	initGame();
}
			private void loadImages() {      // image icon is a built in method to load images
				ImageIcon d= new ImageIcon("src/pictures/dot.png");
				dot=d.getImage();
				ImageIcon h= new ImageIcon("src/pictures/head.png");
				head=h.getImage();
				ImageIcon a= new ImageIcon("src/pictures/apple.png");
				apple=a.getImage();
}
			private void initGame() {   // to start the game
				dots=3;
				for (int i=0; i<dots; i++) {    // create the snake
					x[i]= 50-i*10;
					y[i]= 50;
				}
				locateApple();                 // method that does what it describes 
			timer= new Timer(DELAY,this);		// we create the delay  
			timer.start();						// we start the delay 
			}
			private void locateApple() {		// Random location of the apple 
				int x = (int) (Math.random()*APPLE_RANDOM_POSSITION);
				appleX= (x*DOT_SIZE);
				
				int y = (int) (Math.random()*APPLE_RANDOM_POSSITION);
				appleY= (y*DOT_SIZE);
				score++;
			}
			@Override
			public void paintComponent (Graphics g) {
				super.paintComponent(g);
				doDrawing(g);
			}
			private void doDrawing(Graphics g) {  		// edo zografizei to fidi 
			if (inGame) {
				g.drawImage(apple,appleX,appleY,this);
				
				for (int i=0; i<dots; i++) {
					if (i==0) {
						g.drawImage(head, x[i], y[i], this);
					}else {
						g.drawImage(dot, x[i], y[i], this);
					}
				}
				Toolkit.getDefaultToolkit().sync();				//WHAT THE FUCK IS THAT!!!
				
			}else { gameOver(g);
			}
			g.setColor(Color.WHITE);
			g.setFont(new Font("serif",Font.BOLD,25));
			g.drawString(""+score, 20, 30);
			
			}
			private void gameOver(Graphics g) {			// the msg when you loose
				String messageGO="HO! HO! HO! You Lost Bitch!!";
				String messageGi="Your Score is: "+ score;
				Font small = new Font("Helvetica", Font.BOLD, 14);
				FontMetrics metr = getFontMetrics(small);
				 g.setColor(Color.white);
			     g.setFont(small);
			     g.drawString(messageGO, (P_WIDTH - metr.stringWidth(messageGO)) / 2, P_LENGHT / 2);
			     g.drawString(messageGi, (P_WIDTH - metr.stringWidth(messageGi)) / 2, P_LENGHT / 3);
			}
		private void checkApple() { 		// method just in case the apple goes to the corner. 
			if (( x[0]==appleX && y[0]==appleY  )) {
				dots++;
				locateApple();
			}
		}
	private void move() {				// NEED TO REVISIT THIS!!!
		for (int i=dots; i>0; i-- ) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		if (leftDirection) {
			x[0] -=DOT_SIZE;
		}
		if (rightDirection) {
			x[0] += DOT_SIZE;
		}
		if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
		
	}
	private void checkCollision() { 		// TO REVISIT 
		for (int i= dots; i>0; i--) {
			if ((i>4) && (x[0]==x[i]) && (y[0]==y[i])){
				inGame=false;
			}
		}
		if (y[0] >= P_WIDTH) {
            inGame = false;
        }
		if (x[0] >= P_LENGHT) {
            inGame = false;
        }
		 if (y[0] < 0) {
	            inGame = false;
	        }
		 if (x[0] < 0) {
	            inGame = false;
	            }
	          if (!inGame) {
	           timer.stop();
	          }}
	@Override
	public void actionPerformed (ActionEvent e) {
		if (inGame) {
			checkApple();
            checkCollision();
            move();
		}
		
		repaint();
	}
			
private class TAdapter extends KeyAdapter{
	
	@Override
	public void keyPressed (KeyEvent e) {
		int key= e.getKeyCode();
		
		if ((key==KeyEvent.VK_LEFT) && (!rightDirection)) {  ///CAN I MAKE CHANGES AND STILL RUN????CHECK
			leftDirection = true;
            upDirection = false;
            downDirection = false;
		}
		if ((key==KeyEvent.VK_RIGHT) && (!leftDirection)) {  ///CAN I MAKE CHANGES AND STILL RUN????CHECK
			rightDirection = true;
            upDirection = false;
            downDirection = false;
		}
		if ((key == KeyEvent.VK_UP) && (!downDirection)) {    ///CAN I MAKE CHANGES AND STILL RUN????CHECK
            upDirection = true;
            rightDirection = false;
            leftDirection = false;
        }

        if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {     ///CAN I MAKE CHANGES AND STILL RUN????CHECK
            downDirection = true;
            rightDirection = false;
            leftDirection = false;
           
	}	
        if ((key==KeyEvent.VK_SPACE)) {
        	pause();
	}
}
	
	private void pause() {


	}

}}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

