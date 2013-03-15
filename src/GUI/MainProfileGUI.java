package GUI;

import datamodell.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListCellRenderer;

import com.toedter.calendar.JCalendar;

import database.Query;
import datamodell.Event;

public class MainProfileGUI extends JPanel {

	JFrame addingFrame = new JFrame("Ny avtale");
	JFrame employeeCalenderFrame = new JFrame("Kalender for ansatt");

	JPanel completePanel;
	JPanel mainWestPanel;
	JPanel mainEastPanel;

	JPanel eastNorthPanel;

	JPanel eastSouthPanel;
	JPanel eastSouthLowerPanel;
	JPanel eastSouthUpperPanel;
	JPanel eastSouthWhitePanel;

	JPanel westNorthPanel;
	JPanel westNorthUpperPanel;
	JPanel westNorthLowerPanel;
	JPanel westNorthWhitePanel;

	JPanel westSouthPanel;
	JPanel westSouthLowerPanel;

	JPanel receivedRequest;
	JPanel sentRequest;

	AddingEventGUI addingEventGUI = new AddingEventGUI();
	EmployeeCalenderGUI employeeCalenderGUI = new EmployeeCalenderGUI();

	DateToStringModifier dtsm;

	JCalendar cal;
	JList chosenDayEventList;
	JList weekEventsList;
	JList receivedRequestList;
	JList sentRequestList;
	JLabel chosenDayEvent;

	DefaultListModel weekModel;
	DefaultListModel todaysEventModel;

	ArrayList<Event> todaysEventsList;

	public static void main(String[] args) {
		JFrame profileFrame = new JFrame("Min Profil");
		profileFrame.getContentPane().add(new MainProfileGUI());
		profileFrame.setVisible(true);
		profileFrame.setMinimumSize(new Dimension(650, 660));
		profileFrame.setMaximumSize(new Dimension(650, 660));
		profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		profileFrame.setSize(650, 660);
	}

	public MainProfileGUI() {

		setBackground(new Color(0xd6d5d7));
		new BorderLayout();

		cal = new JCalendar();
		cal.setPreferredSize(new Dimension(300, 280));
		cal.setDecorationBackgroundColor(Color.WHITE);

		DefaultListModel testModel = new DefaultListModel();

		// WestNorthLowerPanel
		JButton addEventButton = new JButton("Ny hendelse");
		addEventButton.setPreferredSize(new Dimension(130, 25));
		addEventButton.addActionListener(new addNewEvent());

		JButton findEmployeeCalenderButton = new JButton("Ansattkalender");
		findEmployeeCalenderButton.setPreferredSize(new Dimension(130, 25));
		findEmployeeCalenderButton
				.addActionListener(new viewEmployeeCalender());

		westNorthLowerPanel = new JPanel();
		westNorthLowerPanel.setPreferredSize(new Dimension(300, 50));
		westNorthLowerPanel.setBackground(Color.WHITE);
		westNorthLowerPanel.add(addEventButton);
		westNorthLowerPanel.add(findEmployeeCalenderButton);

		// Requests
		receivedRequest = new JPanel();
		receivedRequest.setBackground(Color.WHITE);
		receivedRequest.setPreferredSize(new Dimension(300, 280));

		receivedRequestList = new JList(testModel);
		receivedRequestList.setFixedCellHeight(30);
		// receivedRequestList.setCellRenderer(new EventRenderer());
		receivedRequestList.addMouseListener(new EventInfo());

		JScrollPane receivedRequestScrollPane = new JScrollPane(
				receivedRequestList);
		receivedRequestScrollPane.setBorder(null);
		receivedRequestScrollPane.setPreferredSize(new Dimension(290, 180));
		receivedRequestScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		receivedRequestScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		receivedRequest.add(receivedRequestScrollPane, BorderLayout.SOUTH);

		sentRequest = new JPanel();
		sentRequest.setPreferredSize(new Dimension(300, 250));
		sentRequest.setBackground(Color.WHITE);

		sentRequestList = new JList(testModel);
		sentRequestList.setFixedCellHeight(30);
		sentRequestList.setCellRenderer(new EventRenderer());
		sentRequestList.addMouseListener(new EventInfo());

		JScrollPane sentRequestScrollPane = new JScrollPane(sentRequestList);
		sentRequestScrollPane.setBorder(null);
		sentRequestScrollPane.setPreferredSize(new Dimension(290, 180));
		sentRequestScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sentRequestScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sentRequest.add(sentRequestScrollPane, BorderLayout.SOUTH);

		JTabbedPane requests = new JTabbedPane();
		requests.setPreferredSize(new Dimension(305, 250));
		requests.addTab("Mottatte innkallinger", receivedRequest);
		requests.addTab("Utsendte innkallinger", sentRequest);

		// WestNorthUpperPanel
		westNorthUpperPanel = new JPanel();
		westNorthUpperPanel.setPreferredSize(new Dimension(300, 220));
		westNorthUpperPanel.setBackground(Color.WHITE);
		westNorthUpperPanel.add(requests, BorderLayout.SOUTH);

		// WestNorthWhitePanel - created to separate border color from rest of
		// WestNorthPanel
		westNorthWhitePanel = new JPanel();
		westNorthWhitePanel.setPreferredSize(new Dimension(300, 280));
		westNorthWhitePanel.setBackground(Color.WHITE);
		westNorthWhitePanel.add(westNorthUpperPanel, BorderLayout.NORTH);
		westNorthWhitePanel.add(westNorthLowerPanel, BorderLayout.SOUTH);

		// WestNorthPanel
		westNorthPanel = new JPanel();
		westNorthPanel.setPreferredSize(new Dimension(300, 300));
		westNorthPanel.setBackground(new Color(0x51bdf4));

		JLabel administrate = new JLabel("Administrer");

		westNorthPanel.add(administrate);
		westNorthPanel.add(westNorthWhitePanel, BorderLayout.NORTH);

		// WestSouthLowerPanel
		westSouthLowerPanel = new JPanel();
		westSouthLowerPanel.setPreferredSize(new Dimension(300, 290));
		westSouthLowerPanel.setBackground(Color.WHITE);

		try {
			ArrayList<Event> weekEvents = new Query().getThisWeeksEvents(
					"@gmail.com", cal.getDate(), cal.getDate().getYear());

			weekModel = new DefaultListModel();
			weekModel.addElement(new String());

			for (int i = 0; i < weekEvents.size(); i++) {
				weekModel.addElement(weekEvents.get(i));
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		weekEventsList = new JList(weekModel);
		weekEventsList.setCellRenderer(new EventRenderer());
		weekEventsList.setFixedCellWidth(280);
		weekEventsList.setFixedCellHeight(30);
		weekEventsList.addMouseListener(new EventInfo());

		JScrollPane weekEventScrollPane = new JScrollPane(weekEventsList);
		weekEventScrollPane.setBorder(null);
		weekEventScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		weekEventScrollPane.setPreferredSize(new Dimension(290, 255));

		westSouthLowerPanel.add(weekEventScrollPane, BorderLayout.SOUTH);

		// WestSouthPanel
		westSouthPanel = new JPanel();
		westSouthPanel.setPreferredSize(new Dimension(300, 300));
		westSouthPanel.setBackground(new Color(0xe35e6e));

		JLabel weekEvents = new JLabel(
				"             Ukens avtaler             ");
		westSouthPanel.add(weekEvents);
		westSouthPanel.add(westSouthLowerPanel, BorderLayout.SOUTH);

		// EastNorthPanel
		eastNorthPanel = new JPanel();
		eastNorthPanel.setPreferredSize(new Dimension(300, 300));
		eastNorthPanel.setBackground(new Color(0x00b099));

		JLabel calenderLabel = new JLabel("Kalender");

		eastNorthPanel.add(calenderLabel, BorderLayout.NORTH);
		eastNorthPanel.add(cal, BorderLayout.SOUTH);

		// EastSouthUpperPanel
		eastSouthUpperPanel = new JPanel();
		eastSouthUpperPanel.setPreferredSize(new Dimension(300, 210));
		eastSouthUpperPanel.setBackground(Color.WHITE);

		todaysEventModel = new DefaultListModel();

		chosenDayEventList = new JList(todaysEventModel);
		chosenDayEventList.setFixedCellHeight(30);
		chosenDayEventList.addMouseListener(new EventInfo());
		chosenDayEventList.setCellRenderer(new EventRenderer());

		JScrollPane chosenDayEventScrollPane = new JScrollPane(
				chosenDayEventList);
		chosenDayEventScrollPane.setBorder(null);
		chosenDayEventScrollPane.setPreferredSize(new Dimension(290, 180));
		chosenDayEventScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		chosenDayEventScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		eastSouthUpperPanel.add(chosenDayEventScrollPane, BorderLayout.SOUTH);

		// EastSouthLowerPanel
		JButton changeEventButton = new JButton("Endre hendelse");
		changeEventButton.addActionListener(new changeEvent());

		JButton deleteEventButton = new JButton("Slett hendelse");
		deleteEventButton.addActionListener(new deleteEvent());

		eastSouthLowerPanel = new JPanel();
		eastSouthLowerPanel.setPreferredSize(new Dimension(300, 50));
		eastSouthLowerPanel.setBackground(Color.WHITE);
		eastSouthLowerPanel.add(changeEventButton, BorderLayout.SOUTH);
		eastSouthLowerPanel.add(deleteEventButton, BorderLayout.SOUTH);

		// EastSouthWhitePanel - created to separate border color from rest of
		// EastSouthPanel
		eastSouthWhitePanel = new JPanel();
		eastSouthWhitePanel.setPreferredSize(new Dimension(300, 270));
		eastSouthWhitePanel.setBackground(Color.WHITE);
		eastSouthWhitePanel.add(eastSouthUpperPanel, BorderLayout.NORTH);
		eastSouthWhitePanel.add(eastSouthLowerPanel, BorderLayout.SOUTH);

		// EastSouthPanel
		eastSouthPanel = new JPanel();
		eastSouthPanel.setPreferredSize(new Dimension(300, 300));
		eastSouthPanel.setBackground(Color.YELLOW);

		chosenDayEvent = new JLabel(toDateLabel());
		cal.addPropertyChangeListener(new dateChoser());

		eastSouthPanel.add(chosenDayEvent);
		eastSouthPanel.add(eastSouthWhitePanel, BorderLayout.SOUTH);

		// MainWestPanel
		mainWestPanel = new JPanel();
		mainWestPanel.setPreferredSize(new Dimension(300, 600));
		mainWestPanel.setBackground(new Color(0xd6d5d7));
		mainWestPanel.add(westNorthPanel, BorderLayout.NORTH);
		mainWestPanel.add(westSouthPanel, BorderLayout.SOUTH);

		// MainEastPanel
		mainEastPanel = new JPanel();
		mainEastPanel.setPreferredSize(new Dimension(300, 600));
		mainEastPanel.setBackground(new Color(0xd6d5d7));
		mainEastPanel.add(eastNorthPanel, BorderLayout.NORTH);
		mainEastPanel.add(eastSouthPanel, BorderLayout.SOUTH);

		// Complete panel
		completePanel = new JPanel();
		completePanel.setPreferredSize(new Dimension(650, 650));
		completePanel.setBackground(new Color(0xd6d5d7));
		completePanel.add(mainWestPanel, BorderLayout.WEST);
		completePanel.add(mainEastPanel, BorderLayout.EAST);
		add(completePanel);

		// Creating AddingEvent pop-up frame
		addingEventGUI.cancelNewEventButton
				.addActionListener(new cancelNewEvent());
		addingEventGUI.completeNewEventButton
				.addActionListener(new completeAddNewEvent());

		addingFrame.setMinimumSize(new Dimension(480, 450));
		addingFrame.setMaximumSize(new Dimension(480, 450));
		addingFrame.setVisible(false);
		addingFrame.getContentPane().add(addingEventGUI);

		// Creating employeeCalender frame
		employeeCalenderGUI.closeEmployeeButton
				.addActionListener(new doneViewingEmployeeCalender());

		employeeCalenderFrame.setMaximumSize(new Dimension(650, 350));
		employeeCalenderFrame.setMinimumSize(new Dimension(650, 350));
		employeeCalenderFrame.setVisible(false);
		employeeCalenderFrame.getContentPane().add(employeeCalenderGUI);

	}

	// Control labels
	public String toMonth(int monthInt) {

		List<String> months = Arrays.asList("januar", "februar", "mars",
				"april", "mai", "juni", "juli", "august", "september",
				"oktober", "november", "desember");

		return months.get(monthInt);
	}

	public String toDateLabel() {
		return ("Avtaler for " + cal.getDate().getDate() + ". "
				+ toMonth(cal.getDate().getMonth()) + " " + (cal.getDate()
				.getYear() + 1900));
	}

	// ActionsListeners
	public class changeEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			addingEventGUI.titleTextField
					.setText("Settes ut ifra verdiene til valgt hendelse");
			addingFrame.setVisible(true);
		}
	}

	public class addNewEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			addingFrame.setVisible(true);
		}
	}

	public class cancelNewEvent implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			addingFrame.setVisible(false);

		}
	}

	public class completeAddNewEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// Event event = new Event(adminEmail, startDate, endDate,
			// startTime, endTime, place, description, title, participants,
			// eventTypes, roomNr)

			try {
				DateToStringModifier dtsm = new DateToStringModifier();

				String startDate = dtsm.getCompleteDate(
						addingEventGUI.startDateChooser.getDate(),
						addingEventGUI.startDateChooser.getDate().getYear());
				String endDate = dtsm.getCompleteDate(
						addingEventGUI.endDateChooser.getDate(),
						addingEventGUI.endDateChooser.getDate().getYear());

				String startTime = String
						.valueOf(addingEventGUI.startTimeHourSpin.getValue())
						+ ":"
						+ String.valueOf(addingEventGUI.startTimeMinuteSpin
								.getValue());
				String endTime = String.valueOf(addingEventGUI.endTimeHourSpin
						.getValue())
						+ ":"
						+ String.valueOf(addingEventGUI.endTimeMinuteSpin
								.getValue());

				String place = addingEventGUI.placeField.getText();
				String description = addingEventGUI.descriptionTextField
						.getText();
				String title = addingEventGUI.titleTextField.getText();

				ArrayList<EventMaker> participants = new ArrayList<EventMaker>();

				EventTypes eventTypes = (EventTypes) addingEventGUI.eventTypeSelecter
						.getSelectedItem();

				// Need to be altered
				int roomNr = 8;

				Event event = new Event("@gmail.com", startDate, endDate,
						startTime, endTime, place, description, title,
						participants, eventTypes, roomNr);


				JOptionPane.showMessageDialog(null,
						"Eventen ble lagt til i din kalender");
				addingFrame.setVisible(false);

			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null,
						"Du må fylle ut start - og sluttdato");
			}
		}
	}

	public class deleteEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Event event = (Event) chosenDayEventList.getSelectedValue();
				try {

					if (event.getAdminEmail().equals("@gmail.com")) {
						if (JOptionPane.showConfirmDialog(null,
								"Vil du slette " + event.getTitle() + " ?",
								"Slette event?", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == 0) {
							try {
								((Query) new Query()).deleteEvent(
										event.getID(), "@gmail.com");
								todaysEventsList = new Query().getEventByDate(
										"@gmail.com", cal.getDate(), cal
												.getDate().getYear());
								todaysEventModel.clear();
								todaysEventModel.addElement(new String());
								for (int i = 0; i < todaysEventsList.size(); i++) {
									todaysEventModel
											.addElement(todaysEventsList.get(i));
								}

								ArrayList<Event> weekEvents;
								try {
									weekEvents = new Query()
											.getThisWeeksEvents("@gmail.com",
													cal.getDate(), cal
															.getDate()
															.getYear());
									weekModel = new DefaultListModel();
									weekModel.clear();
									weekModel.addElement(new String());

									for (int i = 0; i < weekEvents.size(); i++) {
										weekModel.addElement(weekEvents.get(i));
									}
								} catch (ParseException e1) {
									System.out.println("doesnt update after delete");
								}

							} catch (SQLException e1) {
								System.out.println("Error on delete: ");
							}
						}
					} else {
						JOptionPane
								.showMessageDialog(null,
										"Du kan ikke slette eventen da du ikke opprettet den");
					}

				} catch (NullPointerException e2) {
					JOptionPane.showMessageDialog(null, "Du må velge en event");
				}

			} catch (ClassCastException e2) {
				JOptionPane.showMessageDialog(null,
						"DU kan ikke slette headeren!");
			}
		}
	}

	public class viewEmployeeCalender implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			employeeCalenderFrame.setVisible(true);
		}
	}

	public class doneViewingEmployeeCalender implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			employeeCalenderFrame.setVisible(false);
		}

	}

	// MouseListeners
	public class EventInfo implements MouseListener {

		Event event;

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				event = (Event) ((JList) e.getSource()).getSelectedValue();
				if (e.getClickCount() == 2) {
					if (e.getSource() == chosenDayEventList
							|| e.getSource() == weekEventsList
							|| e.getSource() == sentRequestList) {
						infoViewer();
					} else if (e.getSource() == receivedRequestList) {
						answerRequestViewer();
					}
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

		public void infoViewer() {

			JOptionPane.showMessageDialog(null, "INFORMASJON OM event");
		}

		public void answerRequestViewer() {

			JOptionPane.showConfirmDialog(null, "Ønsker du å delta?");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	// PropertyChangelisteners
	public class dateChoser implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent e) {
			if (e.getSource() == cal) {
				chosenDayEvent.setText(toDateLabel());
				todaysEventModel.clear();
				try {
					ArrayList<Event> todaysEventsList = new Query()
							.getEventByDate("@gmail.com", cal.getDate(), cal
									.getDate().getYear());
					todaysEventModel.addElement(new String());
					for (int i = 0; i < todaysEventsList.size(); i++) {
						todaysEventModel.addElement(todaysEventsList.get(i));
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
	}

	// Renderer
	public class EventRenderer extends JLabel implements ListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList arg0, Object arg1,
				int arg2, boolean isSelected, boolean arg4) {

			try {
				Event e = (Event) arg1;
				if (e.getTitle().length() < 15
						|| e.getDescription().length() < 15) {
					e.setTitle(e.getTitle() + "                  ");
					e.setDescription(e.getDescription() + "                  ");
				}
				setText(e.getTitle().substring(0, 15)
						+ e.getDescription().substring(0, 15) + "  "
						+ e.getStartTime() + "-" + e.getEndTime() + "    "
						+ e.getStartDate().substring(0, 5));
			} catch (Exception e) {
				setText("Tittel                  Beskrivelse                 Tid ");
			}

			if (isSelected) {
				setForeground(Color.RED);
			} else {
				setForeground(Color.BLACK);
			}

			return this;
		}
	}
}
