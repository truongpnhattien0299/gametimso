package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import DAL.DateLabelFormatter;
import GUI.LoginGUI.ButtonLogin;
import GUI.LoginGUI.ButtonSignUp;

public class SignUp extends JFrame {
	public JPanel start;
	private JTextField jTextFieldUsername, txthoten;
	private JRadioButton radioNam, radioNu;
	private JButton btnButtonSignUp;
	private JPasswordField passwordField;
	private JDatePickerImpl datePicker;
	public static DAL.Cl_Connect cl;
//   SoundPlayer mySound = new SoundPlayer();

	public SignUp() {
		start = new JPanel();

		ImagePanel background = new ImagePanel("main.png", 0, 0, 800, 600);

		JLabel lblNewLabel = new JLabel("Sign Up");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel.setBounds(300, 24, 145, 30);
		start.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Please enter your infomation");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(300, 65, 259, 14);
		start.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Username");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(200, 106, 70, 14);
		start.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(200, 142, 63, 14);
		start.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Họ tên");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_4.setBounds(200, 175, 63, 14);
		start.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Giới tính");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_5.setBounds(200, 208, 63, 14);
		start.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Ngày sinh");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_6.setBounds(200, 240, 63, 20);
		start.add(lblNewLabel_6);

		jTextFieldUsername = new JTextField();
		jTextFieldUsername.setBounds(303, 104, 163, 30);
		start.add(jTextFieldUsername);
		jTextFieldUsername.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(300, 141, 163, 30);
		start.add(passwordField);

		txthoten = new JTextField();
		txthoten.setBounds(300, 178, 163, 30);
		start.add(txthoten);
//		emailField.setColumns(10);

		radioNam = new JRadioButton();
		radioNam.setBounds(300, 215, 60, 15);
		radioNam.setText("Nam");
		start.add(radioNam);
//		emailField.setColumns(10);

		radioNu = new JRadioButton();
		radioNu.setBounds(370, 215, 50, 15);
		radioNu.setText("Nữ");
		start.add(radioNu);

		radioNam.setSelected(true);
		ButtonGroup G = new ButtonGroup();
		G.add(radioNam);
		G.add(radioNu);
		LocalDate date = LocalDate.now();
		UtilDateModel model = new UtilDateModel();
		model.setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(300, 240, 163, 28);
		start.add(datePicker);

		btnButtonSignUp = new JButton("Sign Up");
		btnButtonSignUp.setBounds(270, 350, 230, 60);
		start.add(btnButtonSignUp);

		JButton exitButton = new JButton("Exit");

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
		actionListener();
	}

	public void actionListener() {
		ActionListener ButtonSignup = new ButtonSignUp();
		btnButtonSignUp.addActionListener(ButtonSignup);
	}

	class ButtonSignUp implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {

			String username = jTextFieldUsername.getText();
			String password = String.valueOf(passwordField.getPassword());
			String hoten = txthoten.getText();
			String gender = "nam";
			if (radioNu.isSelected())
				gender = "nu";
			String dob = datePicker.getModel().getDay() + "/" + datePicker.getModel().getMonth() + "/"
					+ datePicker.getModel().getYear();

			if (username.equals("") || password.equals("") || hoten.equals("")) {
				JOptionPane.showConfirmDialog(rootPane, "Some Fields Are is Empty", "Error", 1);
			} else {
				if (isValid(username)) {
					int check = CheckSignUp(username, password, hoten, gender, dob);
					if (check == 1) {
						dispose();
						System.out.println("Sign up success!!");
						new StartPanel2();
					} else if (check == 3) {
						JOptionPane.showConfirmDialog(rootPane, "Username Đã tồn tại", "Error", 1);
					} else
						JOptionPane.showConfirmDialog(rootPane, "Username hoặc Password không đúng", "Error", 1);
				} else {
					JOptionPane.showConfirmDialog(rootPane, "Username Không đúng định dạng 'Example@mail.com'", "Error",
							1);
				}
			}

		}

	}

	public Boolean isValid(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	public int CheckSignUp(String user, String pass, String hoten, String gender, String dob) {
		int check = 2;
		try {
			cl = new DAL.Cl_Connect();
			cl.CreateSocket("127.0.0.1", 1234);
			check = cl.Signup(user, pass, hoten, gender, dob);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return check;
	}

	public static void main(String[] args) {

		try { // sử Jato libary có chức năng thay đổi giao diện game đẹp hơn
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		} catch (Exception e) {
		}
		SignUp sign = new SignUp();
	}

}
