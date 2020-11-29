package Server;

import BLL.UserBLL;

public class Room {
	private UserBLL userBLL = new UserBLL();
	private int id;
	private Worker player1 = null;
	private Worker player2 = null;
	private Integer[] arr;
	private Integer[] temp;
	
	public Room(int id) {
		this.id = id;
		arr = userBLL.so(100);
		temp = userBLL.so(100);
	}
	
	public Integer[] getArr() {
		return arr;
	}
	public Integer[] gettemp() {
		return temp;
	}

	public void setArr(Integer[] arr) {
		this.arr = arr;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Worker getPlayer1() {
		return player1;
	}
	public void setPlayer1(Worker player1) {
		this.player1 = player1;
	}
	public Worker getPlayer2() {
		return player2;
	}
	public void setPlayer2(Worker player2) {
		this.player2 = player2;
	}
	
}
