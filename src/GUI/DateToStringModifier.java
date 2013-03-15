package GUI;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateToStringModifier {

	List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May",
			"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

	// use cal.getDate for date and cal.getDate.getYear for year
	public String getCompleteDate(Date date, int year) {

		String yearString = String.valueOf(year + 1900);
		String monthString = String.valueOf(date).replaceAll(" ", "")
				.substring(3, 6);

		for (int i = 0; i < months.size(); i++) {
			if (monthString.equals(months.get(i))) {
				if (i > 10) {
					monthString = String.valueOf(i + 1);
				} else {
					monthString = "0" + String.valueOf(i + 1);
				}
			}
		}

		String dateString = String.valueOf(date.getDate());

		if (date.getDate() < 10) {
			dateString = "0" + date.getDate();
		}

		return  dateString + "/" + monthString  + "/"+ yearString;
	}

	public int getWeeksNumber(String dateString) {

		SimpleDateFormat sdf;
		Calendar cal;
		Date date;
		int week = 0;
		cal = Calendar.getInstance();
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			date = sdf.parse(dateString);
			cal.setTime(date);
			week = cal.get(Calendar.WEEK_OF_YEAR);
		}catch (ParseException e) {
			System.out.println("Parseerror");
		}

		return week;
	}
	
}