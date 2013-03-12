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
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import com.toedter.calendar.JCalendar;

public class EmployeeCalenderGUI extends JPanel {

	JPanel mainWestPanel;
	JPanel mainEastPanel;

	JPanel westNorthPanel;
	JPanel westCenterPanel;
	JPanel westSouthPanel;

	JPanel eastSouthPanel;

	JCalendar cal;
	JButton closeEmployeeButton;
	JComboBox employeeChoser;
	JLabel employeeEventLabel = new JLabel();
	JList chosenDayAndEmployeeEventList;

	public EmployeeCalenderGUI() {

		setPreferredSize(new Dimension(610, 510));
		setBackground(new Color(0xd6d5d7));

		// WestCenterPanel
		westCenterPanel = new JPanel();
		westCenterPanel.setBackground(Color.WHITE);
		westCenterPanel.setPreferredSize(new Dimension(200, 200));

		cal = new JCalendar();
		cal.addPropertyChangeListener(new dateAndEmployeeChoser());
		cal.setPreferredSize(new Dimension(200, 200));

		westCenterPanel.add(cal);

		// WestNorthPanel
		westNorthPanel = new JPanel();
		westNorthPanel.setBackground(Color.WHITE);
		westNorthPanel.setPreferredSize(new Dimension(200, 50));

		JLabel chooseEmployeeLabel = new JLabel(
				"                  Velg ansatt                  ");
		employeeChoser = new JComboBox(ExamplePersons.values());
		employeeChoser.addActionListener(new dateAndEmployeeChoser());

		westNorthPanel.add(chooseEmployeeLabel);
		westNorthPanel.add(employeeChoser);

		// WestSouthPanel
		westSouthPanel = new JPanel();
		westSouthPanel.setBackground(Color.WHITE);
		westSouthPanel.setPreferredSize(new Dimension(200, 40));

		closeEmployeeButton = new JButton("Ferdig");

		westSouthPanel.add(closeEmployeeButton);

		// EastSouthPanel
		eastSouthPanel = new JPanel();
		eastSouthPanel.setBackground(Color.WHITE);
		eastSouthPanel.setPreferredSize(new Dimension(400, 380));

		// Adds elements for testing purposes
		DefaultListModel testModel = new DefaultListModel();
		testModel.addElement(new TestingObject());
		testModel.addElement(new TestingObject());
		testModel.addElement(new TestingObject());
		testModel.addElement(new TestingObject());
		testModel.addElement(new TestingObject());
		testModel.addElement(new TestingObject());
		testModel.addElement(new TestingObject());
		testModel.addElement(new TestingObject());
		testModel.addElement(new TestingObject());
		testModel.addElement(new TestingObject());

		chosenDayAndEmployeeEventList = new JList(testModel);
		chosenDayAndEmployeeEventList.setFixedCellHeight(30);
		chosenDayAndEmployeeEventList.setCellRenderer(new EventRenderer());
		chosenDayAndEmployeeEventList.addMouseListener(new EventInfo());

		JScrollPane chosenDayAndEmployeeEventScrollPane = new JScrollPane(
				chosenDayAndEmployeeEventList);
		chosenDayAndEmployeeEventScrollPane.setBorder(null);
		chosenDayAndEmployeeEventScrollPane.setPreferredSize(new Dimension(380,
				250));
		chosenDayAndEmployeeEventScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		chosenDayAndEmployeeEventScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		eastSouthPanel.add(chosenDayAndEmployeeEventScrollPane);

		
		// MainWestPanel
		mainWestPanel = new JPanel();
		mainWestPanel.setBackground(Color.WHITE);
		mainWestPanel.setPreferredSize(new Dimension(200, 300));
		mainWestPanel.add(westNorthPanel, BorderLayout.NORTH);
		mainWestPanel.add(westCenterPanel, BorderLayout.CENTER);
		mainWestPanel.add(westSouthPanel, BorderLayout.SOUTH);

		// MainEastPanel
		mainEastPanel = new JPanel();
		mainEastPanel.setBackground(Color.YELLOW);
		mainEastPanel.setPreferredSize(new Dimension(400, 300));
		
		employeeEventLabel
		.setText("Velg ansatt og dato for � f� opp �nsket kalender");

		mainEastPanel.add(employeeEventLabel, BorderLayout.NORTH);
		mainEastPanel.add(eastSouthPanel, BorderLayout.SOUTH);

		//adds to Main
		add(mainWestPanel, BorderLayout.WEST);
		add(mainEastPanel, BorderLayout.EAST);

	}
	
	//Controls Labels
	public String toMonth(int monthInt) {

		List<String> months = Arrays.asList("januar", "februar", "mars",
				"april", "mai", "juni", "juli", "august", "september",
				"oktober", "november", "desember");

		return months.get(monthInt);
	}

	public String toDateLabel() {

		try {
			return ("Avtaler for " + cal.getDate().getDate() + ". "
					+ toMonth(cal.getDate().getMonth()) + " "
					+ (cal.getDate().getYear() + 1900) + " for " + employeeChoser
						.getSelectedItem());
		} catch (Exception e) {
			return ("Avtaler for " + cal.getDate().getDate() + ". "
					+ toMonth(cal.getDate().getMonth()) + " "
					+ (cal.getDate().getYear() + 1900) + " for (Velg Ansatt)");
		}
	}

	//PropertyChangeListeners and ActionListener
	public class dateAndEmployeeChoser implements PropertyChangeListener,
			ActionListener {

		public void propertyChange(PropertyChangeEvent e) {
			employeeEventLabel.setText(toDateLabel());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			employeeEventLabel.setText(toDateLabel());
		}
	}

	//MouseListener
	public class EventInfo implements MouseListener {

		TestingObject tOBJ = new TestingObject();

		@Override
		public void mouseClicked(MouseEvent e) {
			tOBJ = (TestingObject) ((JList) e.getSource()).getSelectedValue();
			if (e.getClickCount() == 2
					&& e.getSource() == chosenDayAndEmployeeEventList) {
				infoViewer();
			}
		}

		public void infoViewer() {

			JOptionPane.showMessageDialog(null, "INFORMASJON OM " + tOBJ.string
					+ "  " + tOBJ.value);

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

	//Renderers
	public class EventRenderer extends JLabel implements ListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList arg0, Object arg1,
				int arg2, boolean isSelected, boolean arg4) {

			setText("Tittel   Beskrivelse    Tid    Dato");

			if (isSelected) {
				setForeground(Color.RED);
			} else {
				setForeground(Color.BLACK);
			}

			return this;
		}
	}


}
