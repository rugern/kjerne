import java.sql.Time;

public class Locale {

	private int ID;
	private String name;
	private boolean[][] reservationTable;

	public Locale(String name) {
		this.name = name;
	}

	public void setReserved(Time start, Time end) {

	}

	public void removeReserved(Time start, Time end) {

	}

	public boolean[][] getReservationTable() {
		return reservationTable;
	}

}