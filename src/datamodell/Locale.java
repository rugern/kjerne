package datamodell;


public class Locale {
	
	private int ID;
	private int size;
	
	public Locale(int ID, int size) {
		this.ID = ID;
		this.size = size;
	}
	
	public int getID(){
		return this.ID;
	}
	
	
	public int getCapcity(){
		return this.size;
	}
	
}
