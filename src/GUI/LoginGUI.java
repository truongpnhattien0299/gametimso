package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import BLL.UserBLL;
import GUI.StartGUI.ReceiveFromServer;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField jTextFieldUsername;
	private JButton btnButtonLogin;
	private JPasswordField passwordField;
	Image img;
	boolean oke = true;
 private Socket socket = null;
    BufferedWriter out = null;
    BufferedReader in  = null;
    ExecutorService executor;
    private boolean onl = true;
     private static String host = "localhost";
    public static LoginGUI frame;
	public LoginGUI() {
		 try {
			socket = new Socket("127.0.0.1", 1234);
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initcomponents();
		actionListener();
		executor = Executors.newFixedThreadPool(1);
		ReceiveFromServer recv = new ReceiveFromServer(socket, in);
		executor.execute(recv);
		
		
	}
	public class ReceiveFromServer implements Runnable {
		private Socket socket;
		private BufferedReader in;

		public ReceiveFromServer(Socket socket, BufferedReader in) {
			this.socket = socket;
			this.in = in;
		}
		public void run() {
			while (true) {
				if(oke == false) break;
					String data;
					try {
						data = in.readLine();
						
						StringTokenizer cat = new StringTokenizer(data, "#");
	                    String s= cat.nextToken();
	                    if(s.equals("dangnhap"))
	                    {
	                    	s= cat.nextToken();
	                    	if(s.equals("ok"))
	                    		{
	                    			System.out.print("vodangnhap");
	                    			dong();
	                    		}
	                    	else
	                    		 JOptionPane.showMessageDialog(contentPane, "Đăng nhập sai mật khẩu hoặc pass hoặc tài khoản đã đượ đăng nhập ở 1 nơi khác.");
	                        
	                    }
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
					
				
				
			}
		}
	}
	void dong() throws ClassNotFoundException, IOException
	{
		oke=false;
		
		mainGUI framex = new mainGUI(socket);
		framex.setVisible(true);
		frame.setVisible(false);
		
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
			} else 
			{
					String s ="dangnhap#"+username+"#"+password;	
					send(s);
				   }
		}
	}
	void send(String s)
	{
		try {
			out.write(s);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
