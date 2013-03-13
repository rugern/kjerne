package datamodell;

import GUI.*;
import java.sql.Time;
import java.util.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Locales {
//	private static String sql = "SELECT Roomnr, StartTime, EndTime FROM Locale AS L INNER JOIN Event AS E ON (L.Roomnr=E.Roomnr)"; //returns all rooms in use in a event, and the start time and end time for that event
	
	@SuppressWarnings("null")
	public int[] getLocales(String startTime, String endTime) { // får jeg dette som string???
		ResultSet result;
		double[][] Rooms = null;
		int[] availableRooms = null;
		double start;
		double end;
		
		result = getRoomsAndDate(); //returns the result after an sql query for all rooms(int) in use in a event and the connected start time(String)(tt:mm), end time(String)(tt:mm), start date (String)(dd/mm/YYYY) and end date(String)(dd/mm/YYYY) for these.
		
		int i=0;
		
		while (result.next()) {
			Rooms[i][0]=result.getInt(1);
			Rooms[i][1]=StringsToDouble(result.getString(3),result.getString(1)); //saves the start time as an double on the form YYYYMMDDhhmm
			Rooms[i][2]=StringsToDouble(result.getString(4),result.getString(2)); //saves the end time as an double on the form YYYYMMDDhhmm
			
			i++;
		}
		
		i=0;
		int j=0;
		
		while (i < Rooms.length) {
			if (Rooms[i][1]>end) {
				availableRooms[j] = (int) Rooms[i][0];
				j++;
			} else if (start>Rooms[i][2]) {
				availableRooms[j] = (int) Rooms[i][0];
				j++;
			}
			i++;
		}
		return availableRooms;
	}
	
	public double StringsToDouble(String date, String time) {
		String strDate = date.substring(6, 10);
		strDate += date.substring(3, 5);
		strDate += date.substring(0, 2);
		strDate += time.substring(0, 2);
		strDate += time.substring(3, 5);
		double doubleDate = Double.parseDouble(strDate);
		
		return doubleDate;
	}
	
	//Sets locale as reserved from start time to end time
	public void setReservedLocale(String locale, String start, String end) {
		//TODO, needed for event class
		long startDate = Long.parseLong(start);
		long endDate = Long.parseLong(end);
	}
}
