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
	private JButton btnButtonLogin,btnbutonvaogame,oneButton,twoButton,rankingButton,exitButton;
	private JPasswordField passwordField;
	Image img;
	boolean oke = true;
	 private Socket socket = null;
    BufferedWriter out = null;
    BufferedReader in  = null;
    ExecutorService executor;
    public JPanel start;
    
    

	
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
start=new JPanel();
       

        
        ImagePanel background = new ImagePanel("background1.png", 0, 0, 800, 600);

         oneButton = new JButton("2 Player");
         twoButton = new JButton("3 Players");
         rankingButton = new JButton("Xếp hạng");
         exitButton = new JButton("Exit");

        // Ä‘á»‹nh vá»‹ trĂ­ cĂ¡c button 
        oneButton.setBounds(350, 300, 100, 30);
        twoButton.setBounds(350, 350, 100, 30);
        rankingButton.setBounds(350, 400, 100, 30);
        exitButton.setBounds(350, 450, 100, 30);
        start.add(oneButton);
        start.add(twoButton);
        start.add(rankingButton);
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
//		ActionListener ButtonLogin = new ButtonLogin();
//		btnButtonLogin.addActionListener(ButtonLogin);
//		
//		ActionListener butonvaogame = new Buttonvaogame();
//		btnbutonvaogame.addActionListener(butonvaogame);
		
//		ActionListener rankingAction = new rankingAction();
//		rankingButton.addActionListener(rankingAction);
		ActionListener oneAction = new oneAction();
		oneButton.addActionListener(oneAction);
		
	}
	
	
//	class rankingAction implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent arg0) {

//		}
//	}
	
	class oneAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			Waiting wait = new Waiting();
		}
	}

//	class ButtonLogin implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			try {
//				dong("vaoxem");
//			} catch (ClassNotFoundException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//	class Buttonvaogame implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			try {
//				dong("vaogame");
//			} catch (ClassNotFoundException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
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
