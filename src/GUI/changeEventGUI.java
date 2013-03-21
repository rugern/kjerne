package GUI;

import datamodell.Employee;
import datamodell.EventMaker;
import datamodell.Locale;
import datamodell.Locales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import GUI.AddingEventGUI.dateTimeListener;
import GUI.AddingEventGUI.roomRenderer;

import com.toedter.calendar.JDateChooser;

import database.Query;
import datamodell.Locale;

public class changeEventGUI extends JPanel {

	JPanel mainPanel;

	JPanel mainWestPanel;
	JPanel westUpperPanel;
	JPanel westCenterPanel;
	JPanel westLowerPanel;

	JPanel mainEastPanel;
	JPanel eastUpperPanel;
	JPanel eastCenterPanel;
	JPanel leftEastCentePanel;
	JPanel centerEastCenterPanel;
	JPanel rightEastCenterPanel;
	JPanel eastLowerPanel;

	JPanel southPanel;

	JButton updateEventButton = new JButton("Oppdater");
	JButton cancelUpdateButton = new JButton("Avbryt");

	DefaultComboBoxModel roomModel = new DefaultComboBoxModel();
	ArrayList<Locale> localList;

	// Needed components
	JTextField titleTextField;
	JComboBox eventTypeSelecter;
	JButton addParticipant;
	JButton removeParticipant;
	public JDateChooser startDateChooser = new JDateChooser();
	public JDateChooser endDateChooser = new JDateChooser();
	JSpinner startTimeMinuteSpin;
	JSpinner startTimeHourSpin;
	JSpinner endTimeHourSpin;
	JSpinner endTimeMinuteSpin;
	JComboBox roomSelecter;
	JTextField placeField;
	JTextField descriptionTextField;
	JList chosenParticipants;
	JList potensialParticipants;

	DefaultListModel potensialEmployeesModel;
	DefaultListModel chosenEmployeesModel;

	DateToStringModifier dtsm = new DateToStringModifier();

	public changeEventGUI() {

		setLayout(new BorderLayout());
		setBackground(new Color(0xd6d5d7));

		startDateChooser.setDate(new Date());
		endDateChooser.setDate(new Date());
		
		// WestUpperPanel
		westUpperPanel = new JPanel();
		westUpperPanel.setPreferredSize(new Dimension(70, 150));
		westUpperPanel.setBackground(Color.WHITE);

		JLabel title = new JLabel("Tittel");
		JLabel descriptionLabel = new JLabel("Beskrivelse");
		JLabel dateLabel = new JLabel("    Dato    ");
		JLabel startTimeLabel = new JLabel("Starttid");
		JLabel endTimeLabel = new JLabel("Slutttid");
		JLabel localeLabel = new JLabel("Rom / Sted");
		JLabel type = new JLabel("Type");

		westUpperPanel.add(title);
		westUpperPanel.add(descriptionLabel);
		westUpperPanel.add(dateLabel);
		westUpperPanel.add(startTimeLabel);
		westUpperPanel.add(endTimeLabel);
		westUpperPanel.add(localeLabel);
		westUpperPanel.add(type);

		// WestCenterPanel
		westCenterPanel = new JPanel();
		westCenterPanel.setPreferredSize(new Dimension(70, 155));
		westCenterPanel.setBackground(Color.WHITE);

		JLabel particantLabel = new JLabel("Deltagere");

		westCenterPanel.add(particantLabel);

		// WestLowerPanel
		westLowerPanel = new JPanel();
		westLowerPanel.setBackground(Color.WHITE);
		westLowerPanel.setPreferredSize(new Dimension(70, 25));

		JLabel alarmLabel = new JLabel("Varsling");

		westLowerPanel.add(alarmLabel);

		// MainWestPanel
		mainWestPanel = new JPanel();
		mainWestPanel.setPreferredSize(new Dimension(80, 350));
		mainWestPanel.setBackground(Color.WHITE);

		mainWestPanel.add(westUpperPanel, BorderLayout.NORTH);
		mainWestPanel.add(westCenterPanel, BorderLayout.CENTER);
		mainWestPanel.add(westLowerPanel, BorderLayout.SOUTH);

		// EastUpperPanel
		eastUpperPanel = new JPanel();
		eastUpperPanel.setBackground(Color.WHITE);
		eastUpperPanel.setPreferredSize(new Dimension(250, 150));

		titleTextField = new JTextField(20);
		titleTextField.setName("TitleTextField");
		titleTextField.setPreferredSize(new Dimension(50, 16));

		descriptionTextField = new JTextField(20);
		descriptionTextField.setName("descriptionTextField");
		descriptionTextField.setPreferredSize(new Dimension(50, 16));

		startDateChooser.setName("startDateChooser");
		startDateChooser.setPreferredSize(new Dimension(100, 16));
		startDateChooser.addPropertyChangeListener(new dateAndTimeChanger());

		endDateChooser.setName("endDateChooser");
		endDateChooser.setPreferredSize(new Dimension(100, 16));
		endDateChooser.addPropertyChangeListener(new dateAndTimeChanger());

		JLabel toLabel = new JLabel(" til");
		JLabel widerLabel2 = new JLabel(
				"                                            ");
		JLabel widerLabel3 = new JLabel(
				"                                            ");
		JLabel widerLabel4 = new JLabel(
				"                                       ");

		startTimeMinuteSpin = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
		JComponent startTimeMinuteField = ((JSpinner.DefaultEditor) ((JSpinner) startTimeMinuteSpin)
				.getEditor());
		startTimeMinuteSpin.addChangeListener(new dateTimeListener());
		startTimeMinuteField.setPreferredSize(new Dimension(20, 12));

		startTimeHourSpin = new JSpinner(new SpinnerNumberModel(10, 0, 23, 1));
		startTimeHourSpin.addChangeListener(new dateTimeListener());
		JComponent startTimeHourField = ((JSpinner.DefaultEditor) ((JSpinner) startTimeHourSpin)
				.getEditor());
		startTimeHourField.setPreferredSize(new Dimension(20, 12));

		endTimeHourSpin = new JSpinner(new SpinnerNumberModel(10, 0, 23, 1));
		endTimeHourSpin.addChangeListener(new dateTimeListener());
		JComponent endTimeHourField = ((JSpinner.DefaultEditor) ((JSpinner) endTimeHourSpin)
				.getEditor());
		endTimeHourField.setPreferredSize(new Dimension(20, 12));

		endTimeMinuteSpin = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
		endTimeMinuteSpin.addChangeListener(new dateTimeListener());
		JComponent endtimeMinuteField = ((JSpinner.DefaultEditor) ((JSpinner) endTimeMinuteSpin)
				.getEditor());
		endtimeMinuteField.setPreferredSize(new Dimension(20, 12));

		try {

			String startDate = dtsm.getCompleteDate(startDateChooser.getDate(),
					startDateChooser.getDate().getYear());
			String endDate = dtsm.getCompleteDate(endDateChooser.getDate(),
					endDateChooser.getDate().getYear());

			String startTimeMinutes;

			if ((Integer) startTimeMinuteSpin.getValue() < 10) {
				startTimeMinutes = String.valueOf("0"
						+ startTimeMinuteSpin.getValue());
			} else {
				startTimeMinutes = String.valueOf(startTimeMinuteSpin
						.getValue());
			}

			String startTimeHour;

			if ((Integer) startTimeHourSpin.getValue() < 10) {
				startTimeHour = String.valueOf("0"
						+ startTimeHourSpin.getValue());
			} else {
				startTimeHour = String.valueOf(startTimeHourSpin.getValue());
			}

			String startTime = startTimeHour + ":" + startTimeMinutes;

			String endTimeMinutes;

			if ((Integer) endTimeMinuteSpin.getValue() < 10) {
				endTimeMinutes = String.valueOf("0"
						+ endTimeMinuteSpin.getValue());
			} else {
				endTimeMinutes = String.valueOf(endTimeMinuteSpin.getValue());
			}

			String endTimeHour;

			if ((Integer) endTimeHourSpin.getValue() < 10) {
				endTimeHour = String.valueOf("0" + endTimeHourSpin.getValue());
			} else {
				endTimeHour = String.valueOf(endTimeHourSpin.getValue());
			}

			String endTime = endTimeHour + ":" + endTimeMinutes;

			localList = new Locales().getLocales(startTime, startDate, endTime,
					endDate);

			for (int i = 0; i < localList.size(); i++) {
				roomModel.addElement(localList.get(i));
			}

			roomSelecter = new JComboBox(roomModel);
			roomSelecter.setRenderer(new roomRenderer());
			roomSelecter.setPreferredSize(new Dimension(80, 16));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		placeField = new JTextField(12);
		placeField.setPreferredSize(new Dimension(50, 16));

		eventTypeSelecter = new JComboBox(EventTypes.values());
		eventTypeSelecter.setPreferredSize(new Dimension(100, 16));
		eventTypeSelecter.addActionListener(new selecetedEventType());

		eastUpperPanel.add(titleTextField);
		eastUpperPanel.add(descriptionTextField);
		eastUpperPanel.add(startDateChooser);
		eastUpperPanel.add(toLabel);
		eastUpperPanel.add(endDateChooser);
		eastUpperPanel.add(startTimeHourSpin);
		eastUpperPanel.add(startTimeMinuteSpin);
		eastUpperPanel.add(widerLabel2);
		eastUpperPanel.add(endTimeHourSpin);
		eastUpperPanel.add(endTimeMinuteSpin);
		eastUpperPanel.add(widerLabel3);
		eastUpperPanel.add(roomSelecter);
		eastUpperPanel.add(placeField);
		eastUpperPanel.add(eventTypeSelecter);
		eastUpperPanel.add(widerLabel4);

		// LeftEastCenterPanel
		leftEastCentePanel = new JPanel();
		leftEastCentePanel.setBackground(Color.WHITE);
		leftEastCentePanel.setPreferredSize(new Dimension(110, 140));

		chosenEmployeesModel = new DefaultListModel();

		chosenParticipants = new JList(chosenEmployeesModel);
		chosenParticipants.setCellRenderer(new EmployeeRenderer());
		chosenParticipants.setFixedCellWidth(100);
		chosenParticipants.setFixedCellHeight(30);

		JScrollPane chosenParticipantsScrollPane = new JScrollPane(
				chosenParticipants);
		chosenParticipantsScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chosenParticipantsScrollPane.setPreferredSize(new Dimension(100, 130));

		leftEastCentePanel.add(chosenParticipantsScrollPane);

		// CenterEastCentePanel

		centerEastCenterPanel = new JPanel();
		centerEastCenterPanel.setPreferredSize(new Dimension(70, 70));
		centerEastCenterPanel.setBackground(Color.WHITE);

		addParticipant = new JButton("<");
		addParticipant.addActionListener(new ParticipantAdministrater());
		addParticipant.setPreferredSize(new Dimension(45, 25));

		removeParticipant = new JButton(">");
		removeParticipant.addActionListener(new ParticipantAdministrater());
		removeParticipant.setPreferredSize(new Dimension(45, 25));

		centerEastCenterPanel.add(addParticipant);
		centerEastCenterPanel.add(removeParticipant);

		// RightEastCenterPanel
		rightEastCenterPanel = new JPanel();
		rightEastCenterPanel.setPreferredSize(new Dimension(110, 140));
		rightEastCenterPanel.setBackground(Color.WHITE);

		try {
			potensialEmployeesModel = new DefaultListModel();
			ArrayList<Employee> employees = new Query().getEmployees();

			for (int i = 0; i < employees.size(); i++) {
				potensialEmployeesModel.addElement(employees.get(i));
			}

		} catch (SQLException e) {
			System.out.println("SQL error on getEmployees");
		}

		potensialParticipants = new JList(potensialEmployeesModel);
		potensialParticipants.setCellRenderer(new EmployeeRenderer());
		potensialParticipants.setFixedCellWidth(100);
		potensialParticipants.setFixedCellHeight(30);

		JScrollPane potensialParticipantsScrollPane = new JScrollPane(
				potensialParticipants);
		potensialParticipantsScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		potensialParticipantsScrollPane
				.setPreferredSize(new Dimension(100, 130));

		rightEastCenterPanel.add(potensialParticipantsScrollPane);

		// EastCenterPanel
		eastCenterPanel = new JPanel();
		eastCenterPanel.setBackground(Color.WHITE);
		eastCenterPanel.setPreferredSize(new Dimension(340, 155));

		eastCenterPanel.add(leftEastCentePanel, BorderLayout.WEST);
		eastCenterPanel.add(centerEastCenterPanel, BorderLayout.CENTER);
		eastCenterPanel.add(rightEastCenterPanel, BorderLayout.EAST);

		// EastLowerPanel
		eastLowerPanel = new JPanel();
		eastLowerPanel.setBackground(Color.WHITE);
		eastLowerPanel.setPreferredSize(new Dimension(250, 25));

		JCheckBox soundAlarm = new JCheckBox("Lyd");
		soundAlarm.setPreferredSize(new Dimension(50, 20));
		soundAlarm.setBackground(Color.WHITE);

		JCheckBox textAlarm = new JCheckBox("Tekst");
		textAlarm.setPreferredSize(new Dimension(70, 20));
		textAlarm.setBackground(Color.WHITE);

		eastLowerPanel.add(soundAlarm);
		eastLowerPanel.add(textAlarm);

		// MainEastPanel
		mainEastPanel = new JPanel();
		mainEastPanel.setBackground(Color.WHITE);
		mainEastPanel.setPreferredSize(new Dimension(350, 350));

		mainEastPanel.add(eastUpperPanel, BorderLayout.NORTH);
		mainEastPanel.add(eastCenterPanel, BorderLayout.CENTER);
		mainEastPanel.add(eastLowerPanel, BorderLayout.SOUTH);

		// SouthPanel
		southPanel = new JPanel();
		southPanel.setBackground(Color.WHITE);
		southPanel.setPreferredSize(new Dimension(435, 40));

		southPanel.add(updateEventButton);
		southPanel.add(cancelUpdateButton);

		// MainPanel
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(400, 400));
		mainPanel.setBackground(new Color(0xd6d5d7));

		mainPanel.add(mainWestPanel, BorderLayout.WEST);
		mainPanel.add(mainEastPanel, BorderLayout.EAST);
		mainPanel.add(southPanel, BorderLayout.SOUTH);

		// Actionslisteners må addes i MainProfileGUI

		add(mainPanel);

	}

	public class roomRenderer extends JLabel implements ListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList arg0, Object arg1,
				int arg2, boolean arg3, boolean arg4) {

			Locale locale = (Locale) arg1;

			setText(String.valueOf(locale.getID()) + "  Cap: "
					+ String.valueOf(locale.getCapcity()));

			return this;
		}

	}

	public class ParticipantAdministrater implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == addParticipant
					&& potensialEmployeesModel.size() > 0) {

				EventMaker temp_EventMaker = (EventMaker) potensialParticipants
						.getSelectedValue();
				chosenEmployeesModel.addElement(temp_EventMaker);
				potensialEmployeesModel.removeElement(temp_EventMaker);

			} else if (e.getSource() == removeParticipant
					&& chosenEmployeesModel.size() > 0) {

				EventMaker temp_EventMaker = (EventMaker) chosenParticipants
						.getSelectedValue();
				potensialEmployeesModel.addElement(chosenParticipants
						.getSelectedValue());
				chosenEmployeesModel.removeElement(temp_EventMaker);

			}

			chosenParticipants.setSelectedIndex(0);
			potensialParticipants.setSelectedIndex(0);

		}

	}

	public class selecetedEventType implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (eventTypeSelecter.getSelectedIndex() == 1) {
				addParticipant.setVisible(false);
				removeParticipant.setVisible(false);
			} else {
				addParticipant.setVisible(true);
				removeParticipant.setVisible(true);
			}

		}

	}

	public class EmployeeRenderer extends JLabel implements ListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList arg0, Object arg1,
				int arg2, boolean isSelected, boolean arg4) {

			EventMaker eventmaker = (EventMaker) arg1;

			try {
				setText(eventmaker.getName());

			} catch (Exception e) {
				e.getMessage();
			}

			if (isSelected) {
				setForeground(Color.RED);
			} else {
				setForeground(Color.BLACK);
			}

			return this;

		}

	}

	public class dateAndTimeChanger implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent arg0) {

			String startDate = dtsm.getCompleteDate(startDateChooser.getDate(),
					startDateChooser.getDate().getYear());
			String endDate = dtsm.getCompleteDate(endDateChooser.getDate(),
					endDateChooser.getDate().getYear());

			String startTimeMinutes;

			if ((Integer) startTimeMinuteSpin.getValue() < 10) {
				startTimeMinutes = String.valueOf("0"
						+ startTimeMinuteSpin.getValue());
			} else {
				startTimeMinutes = String.valueOf(startTimeMinuteSpin
						.getValue());
			}

			String startTimeHour;

			if ((Integer) startTimeHourSpin.getValue() < 10) {
				startTimeHour = String.valueOf("0"
						+ startTimeHourSpin.getValue());
			} else {
				startTimeHour = String.valueOf(startTimeHourSpin.getValue());
			}

			String startTime = startTimeHour + ":" + startTimeMinutes;

			String endTimeMinutes;

			if ((Integer) endTimeMinuteSpin.getValue() < 10) {
				endTimeMinutes = String.valueOf("0"
						+ endTimeMinuteSpin.getValue());
			} else {
				endTimeMinutes = String.valueOf(endTimeMinuteSpin.getValue());
			}

			String endTimeHour;

			if ((Integer) endTimeHourSpin.getValue() < 10) {
				endTimeHour = String.valueOf("0" + endTimeHourSpin.getValue());
			} else {
				endTimeHour = String.valueOf(endTimeHourSpin.getValue());
			}

			String endTime = endTimeHour + ":" + endTimeMinutes;

			ArrayList<EventMaker> participants = new ArrayList<EventMaker>();

			System.out.println(roomModel.getSize());
			roomModel.removeAllElements();
			System.out.println(roomModel.getSize());

			try {
				localList = new Locales().getLocales(startTime, startDate,
						endTime, endDate);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int i = 0; i < localList.size(); i++) {
				roomModel.addElement(localList.get(i));

			}

		}
	}

	public class dateTimeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {

			String startDate = dtsm.getCompleteDate(startDateChooser.getDate(),
					startDateChooser.getDate().getYear());
			String endDate = dtsm.getCompleteDate(endDateChooser.getDate(),
					endDateChooser.getDate().getYear());

			String startTimeMinutes;

			if ((Integer) startTimeMinuteSpin.getValue() < 10) {
				startTimeMinutes = String.valueOf("0"
						+ startTimeMinuteSpin.getValue());
			} else {
				startTimeMinutes = String.valueOf(startTimeMinuteSpin
						.getValue());
			}

			String startTimeHour;

			if ((Integer) startTimeHourSpin.getValue() < 10) {
				startTimeHour = String.valueOf("0"
						+ startTimeHourSpin.getValue());
			} else {
				startTimeHour = String.valueOf(startTimeHourSpin.getValue());
			}

			String startTime = startTimeHour + ":" + startTimeMinutes;

			String endTimeMinutes;

			if ((Integer) endTimeMinuteSpin.getValue() < 10) {
				endTimeMinutes = String.valueOf("0"
						+ endTimeMinuteSpin.getValue());
			} else {
				endTimeMinutes = String.valueOf(endTimeMinuteSpin.getValue());
			}

			String endTimeHour;

			if ((Integer) endTimeHourSpin.getValue() < 10) {
				endTimeHour = String.valueOf("0" + endTimeHourSpin.getValue());
			} else {
				endTimeHour = String.valueOf(endTimeHourSpin.getValue());
			}

			String endTime = endTimeHour + ":" + endTimeMinutes;

			ArrayList<EventMaker> participants = new ArrayList<EventMaker>();

			System.out.println(roomModel.getSize());
			roomModel.removeAllElements();
			System.out.println(roomModel.getSize());

			try {
				localList = new Locales().getLocales(startTime, startDate,
						endTime, endDate);
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			for (int i = 0; i < localList.size(); i++) {
				roomModel.addElement(localList.get(i));

			}
			roomSelecter.setRenderer(new roomRenderer());
			roomSelecter.setPreferredSize(new Dimension(80, 16));

		}

	}

}
