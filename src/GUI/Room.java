package GUI;
public enum Room {




	String startTimeMinutes;

	if ((Integer) addingEventGUI.startTimeMinuteSpin.getValue() < 10) {
		startTimeMinutes = String.valueOf("0"
				+ changeEventGUI.startTimeMinuteSpin.getValue());
	} else {
		startTimeMinutes = String
				.valueOf(changeEventGUI.startTimeMinuteSpin
						.getValue());
	}

	String startTimeHour;

	if ((Integer) addingEventGUI.startTimeHourSpin.getValue() < 10) {
		startTimeHour = String.valueOf("0"
				+ changeEventGUI.startTimeHourSpin.getValue());
	} else {
		startTimeHour = String
				.valueOf(changeEventGUI.startTimeHourSpin
						.getValue());
	}

	String startTime = startTimeHour + ":" + startTimeMinutes;
	
	
	
}
