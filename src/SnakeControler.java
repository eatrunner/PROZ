import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public final class SnakeControler extends JFrame{
	SnakeModel snakeMod;
	SnakeView snakeView;
	
	public static void main(String[] args) {
		new SnakeControler();

	}
	
	public SnakeControler()
	{
		this.snakeMod = new SnakeModel(30);
		this.snakeView = new SnakeView(this.snakeMod);
		this.setTitle("SnakeControler");	
		this.setSize(100,100);
		this.setLocationRelativeTo(null);
		this.setResizable(false);		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		this.snakeMod.addSnack();
		this.snakeMod.refresh();
		
		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) 
			{
				 if (e.getKeyCode()==38)
				 {
					 System.out.println("UP");
					 snakeMod.moveSnake(Directions.UP);
					 snakeMod.refresh();	 
				 }else if(e.getKeyCode()==40)
				 {
					 System.out.println("DOWN");
					 snakeMod.moveSnake(Directions.DOWN);
					 snakeMod.refresh();
				 }else if(e.getKeyCode()==39)
				 {
					 System.out.println("RIGHT");
					 snakeMod.moveSnake(Directions.RIGHT);
					 snakeMod.refresh();
				 }else if(e.getKeyCode()==37)
				 {
					 System.out.println("LEFT");
					 snakeMod.moveSnake(Directions.LEFT);
					 snakeMod.refresh();
				 }
					 	                
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		this.setVisible(true);
		
	}
	

}
