package datamodell;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import database.Query;

public class Locales extends Query {
	
	/**
	 * This function gets four strings, a clock time as a string on the form hh:mm and a date time as a string on the form DD/MM/YYYY as a start time and a clock time as a string on the form hh:mm and a date time as a string on the form DD/MM/YYYY and returns an two dimensional Integer array with all the available roomnr, and the rooms capasity in the given period
	 * @param startTime
	 * @param startDate
	 * @param endTime
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("null")
	public ArrayList<Locale> getLocales(String startTime, String startDate, String endTime, String endDate) throws SQLException {
		ResultSet result;
		long[][] Rooms = new long[10][3];
		long start = StringsToLong(startDate,startTime);
		long end = StringsToLong(endDate, endTime);
		result = getRoomsAndDate(); //returns the result after an sql query for all rooms(int) in use in a event and the connected start time(String)(hh:mm), end time(String)(hh:mm), start date (String)(DD/MM/YYYY) and end date(String)(DD/MM/YYYY) for these.
		
		int i=0;
		
//		takes the result from sql query getRoomsAndDate, and put it in the table Rooms
		while (result.next()) {
			Rooms[i][0]=Long.valueOf(result.getInt(1));
			Rooms[i][1]=StringsToLong(result.getString(4), result.getString(2)); //saves the start time as a long on the form YYYYMMDDhhmm
			Rooms[i][2]=StringsToLong(result.getString(5), result.getString(3)); //saves the end time as a long on the form YYYYMMDDhhmm
			i++;
		}
		
		ResultSet result2 = getRooms(); //Returns the result after an sql query for all rooms, and the rooms capacity, that exist 
		
		ArrayList<Integer> listRooms = new ArrayList<Integer>();
		ArrayList<Integer> listCapacity = new ArrayList<Integer>();
		
//		takes the result from sql query getRooms, and put the information of roomnr in listRooms and the information on capacity in listCapacity
		while (result2.next()) {
			listRooms.add(result2.getInt(1));
			listCapacity.add(result2.getInt(2));
		}
		
//		compares the given start time and end time with all the start time and end time rooms that have been put in an event to see if the new event crash with any old event, and removes the room in use in the events that crash from the list of available rooms, it also removes the linked information of capacity from the list of capacity
		for (i=0; i<Rooms.length; i++) {
			boolean crashStartTime = start>=Rooms[i][1] && start<Rooms[i][2];
			boolean crashEndTime = end>Rooms[i][1] && end<=Rooms[i][2];
			boolean crashMeeting = crashStartTime || crashEndTime;
			
			if (crashMeeting) {
				int x = listRooms.indexOf(new Integer((int) Rooms[i][0]));
				listCapacity.remove(x);
				listRooms.remove(new Integer((int) Rooms[i][0]));
			}
		}
		
		Integer[] availableRooms = listRooms.toArray(new Integer[listRooms.size()]);
		Integer[] RoomCapasity = listCapacity.toArray(new Integer[listCapacity.size()]);
		
		Integer[][] RoomsInformation = new Integer[listCapacity.size()][2];

		ArrayList<Locale> locales = new ArrayList<Locale>();
		
		
		
//		puts the information of available rooms and the linked information of the room capacity in a two dimensional integer array
		for (int y=0;y<listCapacity.size();y++) {
			RoomsInformation[y][0] = availableRooms[y];
			RoomsInformation[y][1] = RoomCapasity[y];
			locales.add(new Locale( (int) availableRooms[y], (int) RoomCapasity[y]));
		}
		
		return locales;
	}
	
	
	/**
	 * This function takes to strings, one is a date on the form DD/MM/YYYY, and the other one is a clock time on the form hh:mm, and returns it as a long value on the form YYYYMMDDhhmm
	 * @param date
	 * @param time
	 * @return
	 */
	public long StringsToLong(String date, String time) {
		String strDate = date.substring(6, 10);
		strDate += date.substring(3, 5);
		strDate += date.substring(0, 2);
		strDate += time.substring(0, 2);
		strDate += time.substring(3, 5);
		long longDate = Long.parseLong(strDate);
		
		return longDate;
	}
	
}