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

public class mainGUI extends JFrame {

	private JPanel contentPane;
	private JTextField jTextFieldUsername;
	private JButton btnButtonLogin,btnbutonvaogame;
	private JPasswordField passwordField;
	Image img;
	boolean oke = true;
	 private Socket socket = null;
    BufferedWriter out = null;
    BufferedReader in  = null;
    ExecutorService executor;

	
	public mainGUI(Socket socket) {
		 System.out.println("vao cho");
		initcomponents();
		actionListener();
		try {
			this.socket= socket;
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			}
		}
	}
	void dong(String s) throws ClassNotFoundException, IOException
	{
		oke=false;
		
		new StartGUI(socket,s);
		this.setVisible(false);
		
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

		btnButtonLogin = new JButton("Vào Xem");
		btnButtonLogin.setBounds(170, 214, 89, 23);
		contentPane.add(btnButtonLogin);
		
		btnbutonvaogame = new JButton("Vào Game");
		btnbutonvaogame.setBounds(170, 100, 89, 23);
		contentPane.add(btnbutonvaogame);

	}
	public void actionListener() {
		ActionListener ButtonLogin = new ButtonLogin();
		btnButtonLogin.addActionListener(ButtonLogin);
		
		ActionListener butonvaogame = new Buttonvaogame();
		btnbutonvaogame.addActionListener(butonvaogame);
	}

	class ButtonLogin implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				dong("vaoxem");
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class Buttonvaogame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				dong("vaogame");
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	
}
