package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import BLL.UserBLL;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField jTextFieldUsername;
	private JButton btnButtonLogin;
	private JPasswordField passwordField;
	Image img;

	public LoginGUI() {
		initcomponents();
		actionListener();
	}

	public void initcomponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel.setBounds(158, 24, 145, 30);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Please enter your username and password");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(0, 65, 259, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("User");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(0, 106, 46, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(0, 143, 63, 14);
		contentPane.add(lblNewLabel_3);

		jTextFieldUsername = new JTextField();
		jTextFieldUsername.setBounds(126, 104, 133, 20);
		contentPane.add(jTextFieldUsername);
		jTextFieldUsername.setColumns(10);

		btnButtonLogin = new JButton("Login");
		btnButtonLogin.setBounds(170, 214, 89, 23);
		contentPane.add(btnButtonLogin);

		passwordField = new JPasswordField();
		passwordField.setBounds(126, 141, 133, 20);
		contentPane.add(passwordField);
	}
	public void actionListener() {
		ActionListener ButtonLogin = new ButtonLogin();
		btnButtonLogin.addActionListener(ButtonLogin);
	}

	class ButtonLogin implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			UserBLL userBLL = new UserBLL();
			String username = jTextFieldUsername.getText();
			String password = String.valueOf(passwordField.getPassword());
			if (username.equals("") || password.equals("")) {
				JOptionPane.showConfirmDialog(rootPane, "Some Fields Are is Empty", "Error", 1);
			} else if (userBLL.Login(username, password) == true) {
						dispose();
						System.out.println("login success");
						new StartGUI();
				   }
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
