package dto;

import java.io.Serializable;

public class NetData implements Serializable{

	private static final long serialVersionUID = -1489478848965142663L;
	private int number;
	private int turn;
	private String name;
	
	public NetData() {}

	public NetData(int number,String name, int turn) {
		this.number = number;
		this.turn = turn;
		this.name = name;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "NetData [number=" + number + ", name=" + name + ", turn=" + turn +"]";
	}	
}
