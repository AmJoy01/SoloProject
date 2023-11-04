package main;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 860;
	
	public GameFrame(GamePanel gamePanel) {
		setTitle("Solo Project");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(gamePanel);
		//setLocationRelativeTo(null);
		setResizable(false);
		pack();
		setVisible(true);
		addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {
				gamePanel.getGame().windowFocusLost();
				
			}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
}
