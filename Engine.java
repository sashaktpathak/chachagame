package chachagame;


import java.io.*;
import javax.sound.sampled.*; 
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.JFrame;

import chachagame.GameBoard.TileType;
import chachagame.Snake.Direction;

public class Engine extends KeyAdapter {

	private static final int UPDATES_PER_SECOND = 10;
	
	private static final Font FONT_SMALL = new Font("Times New Roman", Font.BOLD, 20);
	private static final Font FONT_MEDIUM = new Font("Times New Roman", Font.BOLD, 25);
	private static final Font FONT_ES = new Font("Times New Roman", Font.BOLD, 22);
	private static final Font FONT_LARGE = new Font("Times New Roman", Font.BOLD, 40);
		
	private Canvas canvas;
	
	private GameBoard board;
	
	private Snake snake;
    private	long speedconvt = 0L;
	private int score;
	private Clip clip;
	private boolean gameOver;
				
	public Engine(Canvas canvas) {
		try
        {File soundFile = new File("C:/music.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
             clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(clip.LOOP_CONTINUOUSLY);
        }catch(UnsupportedAudioFileException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(LineUnavailableException e)
        {
        e.printStackTrace();
        }
	    this.canvas = canvas;
		this.board = new GameBoard();
		this.snake = new Snake(board);
		
		resetGame();
		
		canvas.addKeyListener(this);
	}
	
	
	public void startGame() throws NumberFormatException{
		canvas.createBufferStrategy(2);
		
		Graphics2D g = (Graphics2D)canvas.getBufferStrategy().getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		String speed = JOptionPane.showInputDialog("Enter speed of Warrior Snake\n1450 is medium\n<1000 Hard\n>1800 Easy");
		long start = 0L;
		long sleepDuration = 0L;
		
		while(true) {
			start = System.currentTimeMillis();

			update();
			render(g);

			canvas.getBufferStrategy().show();
            
			g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			speedconvt = Long.parseLong(speed);
			sleepDuration = (speedconvt/ UPDATES_PER_SECOND) - (System.currentTimeMillis() - start);
              
              
			if(sleepDuration > 0) {
				try {
					Thread.sleep(sleepDuration);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void update() {
		if(gameOver || !canvas.hasFocus()) {
			return;
		}
		TileType snakeTile = snake.updateSnake();
		if(snakeTile == null || snakeTile.equals(TileType.SNAKE)) {
			gameOver = true;
		} else if(snakeTile.equals(TileType.FRUIT)) {
			score += 10;
			spawnFruit();
		}
	}
	
	private void render(Graphics2D g) {
		board.draw(g);
		
		g.setColor(Color.WHITE);
		
		if(gameOver) {
			g.setFont(FONT_LARGE);
			String message = new String("Final Score: " + score);
			g.drawString(message, canvas.getWidth() / 2 - (g.getFontMetrics().stringWidth(message) / 2), 250);
			g.setFont(FONT_LARGE);
			message = new String("Enemies Killed: " + (score/10));
			g.drawString(message, canvas.getWidth() / 2 - (g.getFontMetrics().stringWidth(message) / 2), 350);
			clip.stop();
			g.setFont(FONT_SMALL);
			message = new String("Press Enter to Restart");
			g.drawString(message, canvas.getWidth() / 2 - (g.getFontMetrics().stringWidth(message) / 2), 450);
			g.setFont(FONT_SMALL);
			message = new String("Press Q to Exit");
			g.drawString(message, canvas.getWidth() / 2 - (g.getFontMetrics().stringWidth(message) / 2), 500);
            
              
            }else {
			g.setFont(FONT_SMALL);
			g.drawArc(360,360,100,100,0,360);
			g.drawArc(360,360,101,101,360,0);
			g.drawArc(360,360,102,102,0,360);
			
			g.drawArc(312,312,200,200,0,360);
			g.drawArc(312,312,201,201,0,360);
			g.drawArc(312,312,202,202,0,360);
			
			g.drawArc(265,265,300,300,0,360);
			g.drawArc(265,265,301,301,0,360);
			g.drawArc(265,265,302,302,0,360);
			
			g.drawArc(220,220,395,395,0,360);
			g.drawArc(220,220,396,396,0,360);
			g.drawArc(220,220,397,397,0,360);
			
			g.drawArc(220,220,398,398,360,0);
			g.drawArc(220,220,399,399,360,0);
			
			
			g.drawArc(160,160,495,495,0,360);
			g.drawArc(160,160,496,496,0,360);
			g.drawArc(160,160,497,497,0,360);
			
			
			g.drawArc(112,112,595,595,360,0);
			g.drawArc(112,112,596,596,360,0);
			g.drawArc(112,112,597,597,0,360);
			
			
			g.drawArc(63,63,695,695,0,360);
			g.drawArc(63,63,696,696,0,360);
			g.drawArc(63,63,697,697,0,360);
			
			
			g.drawArc(8,10,795,795,0,360);
			g.drawArc(8,10,796,796,0,360);
			g.drawArc(8,10,797,797,360,0);
			
			g.drawArc(1,1,807,810,360,0);
			g.drawArc(1,1,808,811,360,0);
			g.drawArc(1,1,809,812,360,0);
			
			g.drawString("Score:" + score, 10, 20);
			g.drawString("M = Mute/UnMute", 640,20);
			g.setFont(FONT_ES);
			g.drawString("CHACHA CHAUDHARY's RADAR", 10,785);
			g.setFont(FONT_MEDIUM);
			g.drawString("Red Dot = Enemies" ,590,767);
			g.setFont(FONT_SMALL);
			g.drawString("White part = Our Remote Controlled Snake",430,787);
		}
	}
	
	private void resetGame() {
		board.resetBoard();
		snake.resetSnake();
		score = 0;
		
		clip.start();
		   clip.loop(clip.LOOP_CONTINUOUSLY);
		gameOver = false;
		spawnFruit();
	}
	
	private void spawnFruit() {
		int random = (int)(Math.random() * ((GameBoard.MAP_SIZE * GameBoard.MAP_SIZE) - snake.getSnakeLength()));
		
		int emptyFound = 0;
		int index = 0;
		while(emptyFound < random) {
			index++;
			if(board.getTile(index % GameBoard.MAP_SIZE, index / GameBoard.MAP_SIZE).equals(TileType.EMPTY)) {
				emptyFound++;
			}
		}
		board.setTile(index % GameBoard.MAP_SIZE, index / GameBoard.MAP_SIZE, TileType.FRUIT);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			snake.setDirection(Direction.NORTH);
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			snake.setDirection(Direction.SOUTH);
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			snake.setDirection(Direction.WEST);
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			snake.setDirection(Direction.EAST);
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER && gameOver) {
			resetGame();
		}
		if(e.getKeyCode() == KeyEvent.VK_Q&& gameOver)
		{
		    System.exit(2);
		  }
		  
		  if(e.getKeyCode() == KeyEvent.VK_M) {

			if(clip.isRunning())
			clip.stop();
			else{
           clip.start();
            clip.loop(clip.LOOP_CONTINUOUSLY);
	     }
	   }
	     
	}
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("CHACHA CHAUDHARY AND RAAKA BATTLES AGAIN Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		Canvas canvas = new Canvas();
		canvas.setBackground(Color.GREEN);
		canvas.setPreferredSize(new Dimension(GameBoard.MAP_SIZE * GameBoard.TILE_SIZE, GameBoard.MAP_SIZE * GameBoard.TILE_SIZE));
		
		frame.add(canvas);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
            
		new Engine(canvas).startGame();
	}
	
}
