package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author VanNinh
 */
public class StartPanel2 extends JFrame {
	
	  public JPanel start;

//    SoundPlayer mySound = new SoundPlayer();

    public StartPanel2() {
    	start=new JPanel();
       

        
        ImagePanel background = new ImagePanel("background1.png", 0, 0, 800, 600);

        JButton oneButton = new JButton("2 Player");
        JButton twoButton = new JButton("3 Players");
        JButton LANButton = new JButton("Xếp hạng");
        JButton exitButton = new JButton("Exit");

        // Ä‘á»‹nh vá»‹ trĂ­ cĂ¡c button 
        oneButton.setBounds(350, 300, 100, 30);
        twoButton.setBounds(350, 350, 100, 30);
        LANButton.setBounds(350, 400, 100, 30);
        exitButton.setBounds(350, 450, 100, 30);
        start.add(oneButton);
        start.add(twoButton);
        start.add(LANButton);
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
       

      

       

    
    public static void main(String[] args) {

        try { // sử Jato libary có chức năng thay đổi giao diện game đẹp hơn 
            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
        } catch (Exception e) {
        };
        StartPanel2 start = new StartPanel2();
    }

}
