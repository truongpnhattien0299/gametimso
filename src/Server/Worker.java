package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
//import static Server.;
import static Server.Server.arr_rd;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Worker implements Runnable {

	BufferedReader in;
	BufferedWriter out;
	ObjectOutputStream outobj;
	ObjectInputStream inobj;
	private Socket socket;
	
	

	private String id;
	
	private int idroom = -1;
	private Integer[] arr_num;
	private Integer[] temp;

	
	String msg[];
	String arr_result[];

	public Worker(Socket socket, String id) {
		this.socket = socket;
		this.id = id;
	}

	public void run() {
		System.out.println("Client " + socket.toString() + "accepted");
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			outobj = new ObjectOutputStream(socket.getOutputStream());
			inobj = new ObjectInputStream(socket.getInputStream());

			String input = "";
			msg = (String[]) inobj.readObject();
			if (msg[0].equals("cl_login")) {
				System.out.println("dang nhap vao " + msg[0]);
				Login();

			}

			int vitri = 0;
			while (true) {
				input = in.readLine();
				System.out.println("input : " + input);

				if (input.equals("close"))
					break;

				if (input.equals("sotieptheo")) {
					vitri++;
					for (Worker worker : Server.workers) {
						if (worker.idroom == idroom) {
							worker.out.write("number#" + temp[vitri] + "\n");
							worker.out.flush();
						}
					}
				}
				///////////////////////////////////////////////////////////////////////////////////////////////////////

				if (input.equals("room")) {
					for (Room room : Server.rooms) {
						if (room.getPlayer1() == null) {
							
							arr_num = arr_rd;
							temp = arr_rd;
							room.setPlayer1(this);
							idroom = room.getId();
							break;
						}
						if (room.getPlayer2() == null) {
							

							arr_num = arr_rd;
							temp = arr_rd;
						
							room.setPlayer2(this);
							

							idroom = room.getId();

							for (Worker worker : Server.workers) {
								
								if (worker.idroom == idroom) {
									

									worker.outobj.writeObject(arr_num);
									worker.outobj.flush();
									worker.out.write("number#" + temp[0] + "\n");
									worker.out.flush();
								}
							}
							break;
						}
					}
				}

				StringTokenizer cat = new StringTokenizer(input, "#");
				
				if (cat.countTokens() > 1) {
					String s = cat.nextToken();

					///////////////////////////// bat su kien click vao
					///////////////////////////// so///////////////////////////////////////////////////
					if (s.equals("click")) {
						s = cat.nextToken();
						String id_num = cat.nextToken();
						int dem=0;

						for (Worker worker : Server.workers) {
							System.out.println("work trong for " + worker);
	                		System.out.println("worker la : " +worker.id);

							if (worker.idroom == idroom && !worker.id.equals(id) && dem == 0) {
								for (Room room : Server.rooms) {
									System.out.println("worker vo la : " +worker.id);

									if (room.getPlayer1().id == worker.id) {
										worker.out.write("s1_di#" + id_num + "#" + s + "\n");
										worker.out.flush();
										System.out.println("thang 1 di");

										break;
									}
									if (room.getPlayer2().id == worker.id) {
										worker.out.write("s2_di#" + id_num + "#" + s + "\n");
										worker.out.flush();
										System.out.println("thang 2 di");

										break;
									}
								}
								dem++;
								continue;

							}
						}

					}
					if (s.equals("value")) {
						String player = cat.nextToken();
						s = cat.nextToken();
						int x = Integer.parseInt(s);

						System.out.println("X la  : " + x);
						System.out.println("play la  : " + player);
						System.out.println("vt la  : " + vitri);

						System.out.println("temp la  : " + temp[vitri]);

						if (x == temp[vitri]) {
							for (Worker worker : Server.workers) {
								if (worker.idroom == idroom) {

									worker.out.write("dung#" + player + "\n");
									worker.out.flush();
								
									System.out.println("dung");
								}
							}

						} else {
							for (Worker worker : Server.workers) {
								if (worker.idroom == idroom) {

									worker.out.write("sai" + "\n");
									worker.out.flush();
									

									System.out.println("sai");
								}
							}
						}

					}

				}
////////////////////////////////////////////////////////////////////////////////////////////////////////

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Login() throws IOException, ClassNotFoundException {

		System.out.println(msg[1]);
		System.out.println(msg[2]);
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT *FROM user WHERE username=? and password=?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, msg[1]);
			pst.setString(2, msg[2]);
			ResultSet resultSet = pst.executeQuery();
			if (resultSet.next()) {

				String[] result = new String[2];
				result[0] = "success";

//				os = socket.getOutputStream();
//				ObjectOutputStream oos = new ObjectOutputStream(os);
				outobj.writeObject(result);
				outobj.flush();
//				Random_So();

//				
			} else {
				System.out.println("Login fail");
			}
		}

		catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException ex) {

				}
			}
		}

	}

}
