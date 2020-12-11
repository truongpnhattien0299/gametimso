package Server;



public class Room {
	
	private int id;
	private Worker player1 = null;
	private Worker player2 = null;
	
	
	public Room(int id) {
		this.id = id;
		
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
