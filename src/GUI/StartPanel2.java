package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import GUI.LoginGUI.ButtonLogin;

/**
 *
 * @author VanNinh
 */
public class StartPanel2 extends JFrame {
	
	  public JPanel start;
	  JButton oneButton;
	  JButton twoButton;
	  JButton rankButton;
	  JButton exitButton;

//    SoundPlayer mySound = new SoundPlayer();

    public StartPanel2() {
    	initcomponents();
    	actionListener();
    	
    }
    public void initcomponents() {
    	start=new JPanel();
       

        
        ImagePanel background = new ImagePanel("background1.png", 0, 0, 800, 600);

        oneButton = new JButton("2 Player");
        twoButton = new JButton("3 Players");
        rankButton = new JButton("Rank");
        exitButton = new JButton("Exit");

        // Ä‘á»‹nh vá»‹ trĂ­ cĂ¡c button 
        oneButton.setBounds(350, 300, 100, 30);
        twoButton.setBounds(350, 350, 100, 30);
        rankButton.setBounds(350, 400, 100, 30);
        exitButton.setBounds(350, 450, 100, 30);
        start.add(oneButton);
        start.add(twoButton);
        start.add(rankButton);
        start.add(exitButton);
        start.add(background);
        
        start.setLayout(null);
        start.setBounds(0, 0, 800, 600);
        this.add(start);
        
        this.setLayout(null);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        
        
        
  
               

            }
    
    
    public void actionListener() {
		ActionListener twoPlay = new twoPlay();
		oneButton.addActionListener(twoPlay);
	}

	public class twoPlay implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			
				dispose();
				System.out.println("vao ttwo");
				try {
					new StartGUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   
		}
	}
	
       

      

       

    
    public static void main(String[] args) {

        try { // sử Jato libary có chức năng thay đổi giao diện game đẹp hơn 
            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
        } catch (Exception e) {
        };
        StartPanel2 start = new StartPanel2();
    }

}
