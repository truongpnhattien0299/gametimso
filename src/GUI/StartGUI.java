package GUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
import javax.swing.Timer;
import static DAL.Cl_Connect.socket;
import static Server.Server.minute;
import static DAL.Cl_Connect.in;
import static DAL.Cl_Connect.out;
import static DAL.Cl_Connect.outobj;
import static DAL.Cl_Connect.inobj;
import static DAL.Cl_Connect.arr_result;

public class StartGUI {
//	private BufferedReader in;
//	private BufferedWriter out;
//	private ObjectOutputStream outobj;
//	private ObjectInputStream inobj;

	boolean oke = true;
	private JLabel label;

	private JFrame mainJFrame;
	private JButton num_bt, num_demo, num_pp;
	private JLabel num_lbl, labelClock, lb_iconClock, Player1, Player2, Sound, Search, Start, numfind,Point1,Point2;
	private JPanel mainJPanel;
	private JTextField enterChat;
	private Timer thoigian;// Tao doi tuong dem thoi gian
	private Integer second, minute=0;
	private ArrayList<JButton> list_bt;
	private ExecutorService executor;
	private static int pointX=0;
	private static int pointY=0;

	public static int id_btn;
	static boolean flag = false;
	boolean winner;

	int tong_num=0;
	private int x = 27, y = 15, index;
	private ArrayList<JButton> bt;
	private static String value = "";
	Integer[] arr = new Integer[100];

	public StartGUI() throws UnknownHostException, IOException {

		StreamWorker();

	}

	public void StreamWorker() throws UnknownHostException, IOException {

		try {

//			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		
//			outobj = new ObjectOutputStream(socket.getOutputStream());
//			inobj = new ObjectInputStream(socket.getInputStream());

			out.write("room\n");
			out.flush();

			arr = (Integer[]) inobj.readObject();
			

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
			
			try {
                createAndShow();
				while (true) {

					if (oke == true) {
					
						String data = in.readLine();
						StringTokenizer cat = new StringTokenizer(data, "#");
						String s = cat.nextToken();
						
						if (s.equals("user1")) {
							s=cat.nextToken();
							System.out.println("user 1 : " +s);
							Player1.setText(s);
						}
                        if (s.equals("user2")) {
							s=cat.nextToken();
							System.out.println("user 2 :"  + s );

							Player2.setText(s);
							
						}
						if (s.equals("minute")) {
							minute = Integer.parseInt(cat.nextToken());	
							System.out.println("so phut : " +minute);
							thoigian = new Timer(1000, new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									String minute_time = minute.toString();
									String second_time = second.toString();
									if (minute_time.length() == 1) {
										minute_time = "0" + minute_time;
									}
									if (second_time.length() == 1) {
										second_time = "0" + second_time;
									}
									
									if(second == 0 && minute==0)
									{
										labelClock.setText(minute_time + ":" + second_time);
										if(pointX>pointY)
										{
											JOptionPane.showMessageDialog(mainJFrame, "Player 1 Win");
											thoigian.stop();
											try {
												out.write("play1Win#"+"\n");
												out.flush();
											} catch (IOException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											
										}
										else if(pointY>pointX)
										{
											JOptionPane.showMessageDialog(mainJFrame, "Player 2 Win");
											thoigian.stop();
											try {
												out.write("play2Win#"+"\n");
												out.flush();
											} catch (IOException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
										}
										else 
										{
											JOptionPane.showMessageDialog(mainJFrame, "Hoa");
											thoigian.stop();
											
											try {
												out.write("hoa#"+"\n");
												out.flush();
											} catch (IOException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
										}
									}
									
									else if (second == 0) {
										labelClock.setText(minute_time + ":" + second_time);
										minute--;
										second = 59;
									} else {
										labelClock.setText(minute_time + ":" + second_time);
										second--;
									}
								}

							});

							thoigian.start();

						}

						if (s.equals("s1_di")) {
							s = cat.nextToken();
							id_btn = Integer.parseInt(cat.nextToken());
							out.write("value#" + "s1#" + s + "\n");
							out.flush();

						}
						if (s.equals("s2_di")) {
							s = cat.nextToken();
							id_btn = Integer.parseInt(cat.nextToken());
							out.write("value#" + "s2#" + s + "\n");
							out.flush();

						}

						if (s.equals("number")) {
							s = cat.nextToken();
							if(s.equals("mayman"))
							{
								s = cat.nextToken();
								numfind.setText("Lucky :"+s);
								numfind.setForeground(Color.RED);
							}
							
							else if(s.equals("uutien"))
							{
								s = cat.nextToken();
								numfind.setText("Advantage :"+s);
								numfind.setForeground(Color.GREEN);
							}
							
							else
							{
							
							numfind.setText(s);
							numfind.setForeground(Color.WHITE);
							}
						}

						if (s.equals("dung")) {
							s = cat.nextToken();

							System.out.println("vao dung ");

							if (s.equals("s1")) {
								s = cat.nextToken();
								bt.get(id_btn).setBackground(Color.RED);
								if(s.equals("mayman"))
								{
									pointX+=3;
								}
								else
								pointX++;
								
								Point1.setText(""+pointX);
								if( pointX>(arr.length/2))
								{
									 JOptionPane.showMessageDialog(mainJFrame, "Player 1 Win");
									 out.write("play2Win#"+"\n");
									 out.flush();
								}
								System.out.println("vao dung s1");
							} else if (s.equals("s2")) {
								s = cat.nextToken();
								System.out.println("vao dung s2");
								
								if(s.equals("mayman"))
								{
									pointY+=3;
								}
								else
								pointY++;
								
								Point2.setText(""+pointY);
								if( pointX>(arr.length/2))
								{
									JOptionPane.showMessageDialog(mainJFrame, "Player 2 Win");
									out.write("play1Win#"+"\n");
									out.flush();
								}
								bt.get(id_btn).setBackground(Color.YELLOW);
							}

							out.write("sotieptheo" + "\n");
							out.flush();
						}

					}

				}
			} catch (IOException e) {
			}
		}
	}

	public void createAndShow() throws UnknownHostException, IOException {

		
		list_bt = new ArrayList<JButton>();

		mainJFrame = new JFrame();
		mainJPanel = new JPanel();
		labelClock = new JLabel();
		lb_iconClock = new JLabel();
		Player1 = new JLabel();
		Player2 = new JLabel();
		numfind = new JLabel();
		Sound = new JLabel();
		Start = new JLabel();
		Search = new JLabel();
		bt = new ArrayList<>();
		Point1 = new JLabel();
		Point2 = new JLabel();

		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				index = i * 10 + j - 10;
				if (index == 100)
					break;
				if (index % 2 == 0) {
					x += 27;
					y += 10;
				} else {
					x -= 27;
					y -= 20;
				}
				if (index % 7 == 0) {
					y += 10;
				}
				num_pp = new JButton();
				bt.add(num_pp);
				num_pp.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
				num_pp.setBorderPainted(false);
//				num_pp.setContentAreaFilled( false );
//				num_pp.setFocusPainted( false );
				num_pp.setHorizontalAlignment(SwingConstants.LEFT);
				num_pp.setBounds(x, y, 48, 48);
				num_pp.setBackground(new Color(0, 113, 139));
				String stringCommand = Integer.toString(index - 1);
				num_pp.setActionCommand(stringCommand);
				num_pp.setText("" + arr[index - 1]);
				num_pp.setForeground(Color.BLACK);
				bt.get(index - 1).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton button = (JButton) e.getSource();
						String command = button.getActionCommand();
						id_btn = Integer.parseInt(command);

						value = button.getText();
						System.out.println("value click : " + value);

						try {

							out.write("click#" + id_btn + "#" + value + "\n");
							out.flush();

						} catch (IOException e1) {
							e1.printStackTrace();
						}

					}
				});
				num_pp.setVisible(true);
				num_pp.setEnabled(true);

				mainJPanel.add(num_pp);

				y += 70;
			}
			if (i % 2 == 0)
				y = 10;
			else
				y = 30;
			x += 132;
		}
//		
//		
		
		
		
//		 x=54;
//			for (int i = 1; i <= 5; i++) {
//				for (int j = 1; j <= 10; j++) {
//					index = i * 10 + j - 10;
//					if (index == 50)
//						break;
//					if (index % 2 == 0) {
//						x += 91;
//						y += 10;
//					} else {
//						x -= 91;
//						y -= 20;
//					}
//					if (index % 7 == 0) {
//						y += 10;
//						x+=16;
//					}
//					if (index % 5 == 0) {
//						y -= 10;
//						x-=16;
//					}
//					num_pp = new JButton();
//					bt.add(num_pp);
//					num_pp.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
//					num_pp.setBorderPainted(false);
////					num_pp.setContentAreaFilled( false );
////					num_pp.setFocusPainted( false );
//					num_pp.setHorizontalAlignment(SwingConstants.LEFT);
//					num_pp.setBounds(x, y, 48, 48);
//					num_pp.setBackground(new Color(0, 113, 139));
//					String stringCommand = Integer.toString(index - 1);
//					num_pp.setActionCommand(stringCommand);
//					num_pp.setText("" + arr[index - 1]);
//					num_pp.setForeground(Color.BLACK);
//					bt.get(index - 1).addActionListener(new ActionListener() {
//						@Override
//						public void actionPerformed(ActionEvent e) {
//							JButton button = (JButton) e.getSource();
//							String command = button.getActionCommand();
//							id_btn = Integer.parseInt(command);
//
//							value = button.getText();
//							System.out.println("value click : " + value);
//
//							try {
//
//								out.write("click#" + id_btn + "#" + value + "\n");
//								out.flush();
//
//							} catch (IOException e1) {
//								e1.printStackTrace();
//							}
//
//						}
//					});
//					num_pp.setVisible(true);
//					num_pp.setEnabled(true);
//
//					mainJPanel.add(num_pp);
//					y += 70;
//				}
//				if (i % 2 == 0)
//					y = 10;
//				else
//					y = 30;
//				x += 150;
//			}
			
			
			
			

		labelClock.setBounds(1000, 20, 80, 20);
		labelClock.setForeground(Color.WHITE);

		mainJFrame.add(labelClock);
		second = 0;
		

		
		
		Player1.setBounds(400, 20, 100, 20);
		Player1.setForeground(Color.RED);
		
		mainJFrame.add(Player1);

		
		Point1.setBounds(482, 20, 80, 20);
		Point1.setForeground(Color.BLACK);
		Point1.setText(""+pointX);
		mainJFrame.add(Point1);
		
		numfind.setBounds(590, 20, 80, 20);
		numfind.setForeground(Color.WHITE);
		mainJFrame.add(numfind);

		Player2.setBounds(700, 18, 100, 20);
		Player2.setForeground(Color.YELLOW);
		
		mainJFrame.add(Player2);
		
		
		Point2.setBounds(782, 18, 80, 20);
		Point2.setForeground(Color.BLACK);
		Point2.setText(""+pointY);
		mainJFrame.add(Point2);

		try {
			BufferedImage image = ImageIO.read(new File("./image/dongho.jpg"));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(45, 45, image.SCALE_SMOOTH));
			lb_iconClock.setIcon(icon);
		} catch (IOException ex) {
		}
		lb_iconClock.setBounds(910, 0, 45, 45);
		mainJFrame.add(lb_iconClock);

		try {
			BufferedImage image = ImageIO.read(new File("./image/start.png"));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(45, 45, image.SCALE_SMOOTH));
			Start.setIcon(icon);
		} catch (IOException ex) {
		}
		Start.setBounds(90, 0, 45, 45);
		mainJFrame.add(Start);

		try {
			BufferedImage image = ImageIO.read(new File("./image/loa.png"));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(45, 45, image.SCALE_SMOOTH));
			Sound.setIcon(icon);
		} catch (IOException ex) {
		}
		Sound.setBounds(250, 0, 45, 45);
		mainJFrame.add(Sound);

		try {
			BufferedImage image = ImageIO.read(new File("./image/search.png"));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(45, 45, image.SCALE_SMOOTH));
			Search.setIcon(icon);
		} catch (IOException ex) {
		}
		Search.setBounds(560, 0, 45, 45);
		mainJFrame.add(Search);

		mainJPanel.setLayout(null);
		mainJPanel.setBounds(0, 48, 1270, 1150);
		mainJPanel.setBackground(new Color(0, 113, 139));
		mainJFrame.add(mainJPanel);

//		mainJFrame.setIconImage(Images.getImage(Images.ID_BIRD_RIGHT));
		mainJFrame.setLayout(null);
		mainJFrame.setSize(1270, 1250);
		mainJFrame.setLocationRelativeTo(null);
		mainJFrame.getContentPane().setBackground(new Color(0, 74, 80));
		mainJFrame.setVisible(true);
		mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

//	public class HighlightMouseListener extends MouseAdapter {
//		public JButton previous;
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			
//			 JButton button = (JButton) e.getSource();
//             String command = button.getActionCommand();
//             id_btn = Integer.parseInt(command);
//			
////			JButton label = (JLabel) e.getSource();
////			String command = label.getActionCommand();
////			int id_btn = Integer.parseInt(command);
//			Component source = e.getComponent();
//			if (!(source instanceof JButton)) {
//				return;
//			}
//			button = (JButton) source;
//			if (previous != null) {
//				previous.setBackground(null);
//				previous.setForeground(null);
//				previous.setOpaque(false);
//			}
//			previous = button;
//			button.setForeground(Color.WHITE);
//			bu.setBackground(Color.YELLOW);
//			label.setOpaque(true)
//			String i =button.getText();
//			button.setOpaque(true);
//			try {
//				
//				out.write("click#"+i+"\n");
//				out.flush();
//				
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//
//		}
//
//	}

//	public class HighlightListenerYellow extends MouseAdapter {
//		public JButton previous;
//
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			Component source = e.getComponent();
//			if (!(source instanceof JButton)) {
//				return;
//			}
//			JButton button = (JButton) source;
//			if (previous != null) {
//				previous.setBackground(null);
//				previous.setForeground(null);
//				previous.setOpaque(false);
//			}
//			previous = button;
//			label.setForeground(Color.WHITE);
//			label.setBackground(Color.YELLOW);
//			label.setOpaque(true);
//		}
//
//	}

}
