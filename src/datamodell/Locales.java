package datamodell;

import java.sql.ResultSet;
import java.sql.SQLException;
import database.Query;

public class Locales extends Query {
	
	/**
	 * This function gets four strings, a clock time as a string on the form hh:mm and a date time as a string on the form DD/MM/YYYY as a start time and a clock time as a string on the form hh:mm and a date time as a string on the form DD/MM/YYYY and returns an int array with all the available roomnr in the given period
	 * @param startTime
	 * @param startDate
	 * @param endTime
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("null")
	public int[] getLocales(String startTime, String startDate, String endTime, String endDate) throws SQLException {
		ResultSet result;
		long[][] Rooms = null;
		int[] availableRooms = null;
		long start = StringsToLong(startDate,startTime);
		long end = StringsToLong(endDate, endTime);
		
		result = getRoomsAndDate(); //returns the result after an sql query for all rooms(int) in use in a event and the connected start time(String)(hh:mm), end time(String)(hh:mm), start date (String)(DD/MM/YYYY) and end date(String)(DD/MM/YYYY) for these.
		
		int i=0;
		
//		takes the result from sql query and put it in the table Rooms
		while (result.next()) {
			Rooms[i][0]=result.getInt(1);
			Rooms[i][1]=StringsToLong(result.getString(3),result.getString(1)); //saves the start time as a long on the form YYYYMMDDhhmm
			Rooms[i][2]=StringsToLong(result.getString(4),result.getString(2)); //saves the end time as a long on the form YYYYMMDDhhmm
			
			i++;
		}
		
		i=0;
		int j=0;
		
//		Put rooms from the table Rooms that isn't used in the given start and end time and put the roomnr in the table availableRooms
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
