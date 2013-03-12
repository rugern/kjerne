package GUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.SpinnerNumberModel;

import com.toedter.calendar.JDateChooser;

public class AddingEventGUI extends JPanel {

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

	JButton completeNewEventButton = new JButton("Fullfør");
	JButton cancelNewEventButton = new JButton("Avbryt");
	JTextField titleTextField;
	JComboBox eventTypeSelecter;
	JButton addParticipant;
	JButton removeParticipant;

	public AddingEventGUI() {

		setLayout(new BorderLayout());
		setBackground(new Color(0xd6d5d7));

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
		titleTextField.setPreferredSize(new Dimension(50, 16));
		
		JTextField descriptionTextField = new JTextField(20);
		descriptionTextField.setPreferredSize(new Dimension(50, 16));

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setPreferredSize(new Dimension(100, 16));

		JLabel widerLabel = new JLabel(
				"                                       ");
		JLabel widerLabel2 = new JLabel(
				"                                            ");
		JLabel widerLabel3 = new JLabel(
				"                                            ");
		JLabel widerLabel4 = new JLabel(
				"                                       ");

		JSpinner startTimeMinuteSpin = new JSpinner(new SpinnerNumberModel(0,
				0, 59, 1));
		JComponent startTimeMinuteField = ((JSpinner.DefaultEditor) ((JSpinner) startTimeMinuteSpin)
				.getEditor());
		startTimeMinuteField.setPreferredSize(new Dimension(20, 12));

		JSpinner startTimeHourSpin = new JSpinner(new SpinnerNumberModel(10, 0,
				23, 1));
		JComponent startTimeHourField = ((JSpinner.DefaultEditor) ((JSpinner) startTimeHourSpin)
				.getEditor());
		startTimeHourField.setPreferredSize(new Dimension(20, 12));

		JSpinner endTimeHourSpin = new JSpinner(new SpinnerNumberModel(10, 0,
				23, 1));
		JComponent endTimeHourField = ((JSpinner.DefaultEditor) ((JSpinner) endTimeHourSpin)
				.getEditor());
		endTimeHourField.setPreferredSize(new Dimension(20, 12));

		JSpinner endTimeMinuteSpin = new JSpinner(new SpinnerNumberModel(0, 0,
				59, 1));
		JComponent endtimeMinuteField = ((JSpinner.DefaultEditor) ((JSpinner) endTimeMinuteSpin)
				.getEditor());
		endtimeMinuteField.setPreferredSize(new Dimension(20, 12));

		JComboBox roomSelecter = new JComboBox(Room.values());
		roomSelecter.setPreferredSize(new Dimension(70, 16));
		
		JTextField placeField = new JTextField(13);
		placeField.setPreferredSize(new Dimension(50, 16));

		eventTypeSelecter = new JComboBox(EventTypes.values());
		eventTypeSelecter.setPreferredSize(new Dimension(100, 16));
		eventTypeSelecter.addActionListener(new selecetedEventType());

		eastUpperPanel.add(titleTextField);
		eastUpperPanel.add(descriptionTextField);
		eastUpperPanel.add(dateChooser);
		eastUpperPanel.add(widerLabel);
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

		JList chosenParticipants = new JList();
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
		addParticipant.setPreferredSize(new Dimension(45, 25));
		
		removeParticipant = new JButton(">");
		removeParticipant.setPreferredSize(new Dimension(45, 25));

		centerEastCenterPanel.add(addParticipant);
		centerEastCenterPanel.add(removeParticipant);

		// RightEastCenterPanel
		rightEastCenterPanel = new JPanel();
		rightEastCenterPanel.setPreferredSize(new Dimension(110, 140));
		rightEastCenterPanel.setBackground(Color.WHITE);

		JList potensialParticipants = new JList();
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
		
		southPanel.add(completeNewEventButton);
		southPanel.add(cancelNewEventButton);

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
	
	public class selecetedEventType implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(eventTypeSelecter.getSelectedIndex() ==1){
				addParticipant.setVisible(false);
				removeParticipant.setVisible(false);
			}else{
				addParticipant.setVisible(true);
				removeParticipant.setVisible(true);
			}
			
		}
		
		
	}

}