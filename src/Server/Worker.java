package Server;

//import static Server.;
import static Server.Server.arr_rd;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


import static Server.Server.point_bonus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static Server.Server.minute;

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
	private String user;

	public Worker(Socket socket, String id, String user) {
		this.socket = socket;
		this.id = id;
		this.user="";
	}

	public void run() {
		System.out.println("Client " + socket.toString() + "accepted");
		try {
			findAll();
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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

				if (input.equals("rank")) {
					System.out.print("vao day");
					List<Rank> rankList = new ArrayList<Rank>();
					rankList = findAll();
//					rankList.forEach((rank) -> {
//			           System.out.println("rank match " +rank.getN_match());
//			           try {
////						outobj.writeObject(rank);
////						outobj.flush();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}

//			        });

					outobj.writeObject(rankList);
					outobj.flush();

				}

				if (input.equals("close"))
					break;

				if (input.equals("sotieptheo")) {

					vitri++;
					for (Worker worker : Server.workers) {
						if (worker.idroom == idroom) {
							if (ktmayman(temp[vitri])) {
								worker.out.write("number#" + "mayman#" + temp[vitri] + "\n");
								worker.out.flush();

							}

							else if (ktuutien(temp[vitri])) {
								worker.out.write("number#" + "uutien#" + temp[vitri] + "\n");
								worker.out.flush();

							} else {
								worker.out.write("number#" + temp[vitri] + "\n");
								worker.out.flush();

							}
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
//							System.out.println("this  :"+this.user);
							room.setUser1(msg[1]);
							
							idroom = room.getId();
							
							break;
						}
						if (room.getPlayer2() == null) {

							arr_num = arr_rd;
							temp = arr_rd;

//						    Collections.shuffle(Arrays.asList(point_bonus));
							room.setPlayer2(this);

							room.setUser2(msg[1]);

							idroom = room.getId();
						

							for (Worker worker : Server.workers) {

								if (worker.idroom == idroom) {

									System.out.println("so phut : " + minute);

									worker.outobj.writeObject(arr_num);
									worker.outobj.flush();
									worker.out.write("minute#" + minute + "\n");
									worker.out.flush();
									System.out.println("user play1 : "+room.getPlayer1().user);
									System.out.println("user play2 :"+room.getPlayer2().user);

									
									worker.out.write("user1#"+room.getPlayer1().user+"\n");
									worker.out.flush();
									worker.out.write("user2#"+room.getPlayer2().user+"\n");
									worker.out.flush();

									if (ktmayman(temp[0])) {
										worker.out.write("number#" + "mayman#" + temp[0] + "\n");
										worker.out.flush();

									}
//									else if(ktuutien(temp[0])) {
//										worker.out.write("number#" +"uutien#"+temp[0] + "\n");
//										worker.out.flush();
//									
//									}
									else {
										worker.out.write("number#" + temp[0] + "\n");
										worker.out.flush();

									}

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
						int dem = 0;

						for (Worker worker : Server.workers) {
							System.out.println("work trong for " + worker);
							System.out.println("worker la : " + worker.id);

							if (worker.idroom == idroom && !worker.id.equals(id) && dem == 0) {
								for (Room room : Server.rooms) {
									System.out.println("worker vo la : " + worker.id);

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

					if (s.equals("play1Win")) {
						
						
						
						

					}

					if (s.equals("play2Win")) {
						

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
									if (ktmayman(x)) {
										worker.out.write("dung#" + player + "#mayman" + "\n");
										worker.out.flush();

									}

//                                    else if(ktuutien(x)) {
//										worker.out.write("dung#" + player +"#uutien" + "\n");
//										worker.out.flush();
//									
//									}
									else {
										worker.out.write("dung#" + player + "#sothuong" + "\n");
										worker.out.flush();
									}

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

	public Boolean ktmayman(int sokt) {
		System.out.println("sokt : " + sokt);
		System.out.println(point_bonus[0]);

		for (int i = 0; i < 25; i++) {
			if (point_bonus[i] == sokt)
				return true;
		}
		return false;
	}

	public Boolean ktuutien(int sokt) {
		System.out.println("souutien : " + sokt);
		System.out.println(point_bonus[0]);

		for (int i = 40; i < 60; i++) {
			if (point_bonus[i] == sokt)
				return true;
		}
		return false;
	}

	public void Login() throws IOException, ClassNotFoundException {

		System.out.println(msg[1]);
		System.out.println(msg[2]);
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT *FROM user WHERE user=? and password=?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, msg[1]);
			pst.setString(2, msg[2]);
			ResultSet resultSet = pst.executeQuery();
			if (resultSet.next()) {
				this.user=msg[1];

				String[] result = new String[3];
				result[0] = "success";
				result[1]=msg[1];
				result[2]=msg[2];

						

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

	public static List<Rank> findAll() throws IOException, ClassNotFoundException {

		List<Rank> rankList = new ArrayList<>();
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT *FROM history";
			pst = (PreparedStatement) conn.prepareStatement(sql);

			ResultSet resultSet = pst.executeQuery();
			while (resultSet.next()) {

				int id_user = resultSet.getInt("userId");

				String sql2 = "SELECT * FROM user WHERE id=? ";
				pst = (PreparedStatement) conn.prepareStatement(sql2);
				pst.setInt(1, id_user);

				ResultSet resultSet2 = pst.executeQuery();
				if (resultSet2.next()) {
					String user_name = resultSet2.getString("user");

					Rank rk = new Rank(resultSet.getInt("userId"), user_name, resultSet.getInt("point"),
							resultSet.getInt("N_match"), resultSet.getInt("win"), resultSet.getInt("lose"));
					rankList.add(rk);
				}
//				
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
		return rankList;

	}
}
