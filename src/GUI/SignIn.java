package GUI;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class SignIn extends JFrame {
	 public JPanel start;
	 private JTextField jTextFieldUsername ,emailField,phoneField;
		private JButton btnButtonLogin;
		private JPasswordField passwordField;

//   SoundPlayer mySound = new SoundPlayer();

   public SignIn() {
   	start=new JPanel();
      

       
       ImagePanel background = new ImagePanel("main.png", 0, 0, 800, 600);

      
       
       JLabel lblNewLabel = new JLabel("Sign In");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel.setBounds(300, 24, 145, 30);
		start.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Please enter your infomation");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(300, 65, 259, 14);
		start.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("User");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(200, 106, 46, 14);
		start.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(200, 142, 63, 14);
		start.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Email");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_4.setBounds(200, 175, 63, 14);
		start.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Phone");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_5.setBounds(200, 208, 63, 14);
		start.add(lblNewLabel_5);

		jTextFieldUsername = new JTextField();
		jTextFieldUsername.setBounds(303, 104, 163, 30);
		start.add(jTextFieldUsername);
		jTextFieldUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(300, 141, 163, 30);
		start.add(passwordField);
		
		emailField = new JTextField();
		emailField .setBounds(300, 178, 163, 30);
		start.add(emailField);
//		emailField.setColumns(10);
		
		phoneField = new JTextField();
		phoneField.setBounds(300, 215, 163, 30);
		start.add(phoneField);
//		emailField.setColumns(10);

		

		
		
		
		
		
		
		btnButtonLogin = new JButton("Login");
		btnButtonLogin.setBounds(270, 270, 230, 60);
		start.add(btnButtonLogin);
       
       JButton exitButton = new JButton("Exit");

       // Ä‘á»‹nh vá»‹ trĂ­ cĂ¡c button 
       
      
       exitButton.setBounds(270, 350, 230, 60);
       
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
       SignIn sign = new SignIn();
   }

}
