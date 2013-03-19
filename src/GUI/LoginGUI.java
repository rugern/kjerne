package GUI;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import server.CommEnum;
import server.CommPack;

import client.SocketClient;

public class LoginGUI extends JPanel {

	JPanel centerPanel;
	static JFrame loginFrame;
	static JTextField emailTextField; //TODO maybe shouldn't be static ?
	static JPasswordField passwordField;
	static SocketClient client = new SocketClient("127.0.0.1", 1337);

	public static void main(String[] args) {
		loginFrame = new JFrame("Logg inn");
		loginFrame.getContentPane().add(new LoginGUI());
		loginFrame.setVisible(true);
		loginFrame.setMinimumSize(new Dimension(650, 660));
		loginFrame.setMaximumSize(new Dimension(650, 660));
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setSize(650, 660);
	}

	public LoginGUI() {

		JLabel emailLabel = new JLabel("Email:");
		emailTextField = new JTextField(30);

		JLabel passwordLabel = new JLabel("Passord:");
		passwordField = new JPasswordField(30);

		JButton loginButton = new JButton("Logg inn");
		loginButton.addActionListener(new LoginEvent());

		centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(350, 400));

		centerPanel.add(emailLabel);
		centerPanel.add(emailTextField);
		centerPanel.add(passwordLabel);
		centerPanel.add(passwordField);
		centerPanel.add(loginButton);

		add(centerPanel);
	}

	public class LoginEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				client.run();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String user = emailTextField.getText();
			String pw = passwordField.getText();
			ArrayList<String> al = new ArrayList();
			al.add(user);
			al.add(pw);
			client.sendMessage(new CommPack(CommEnum.LOGIN, al));

		}
	}

	public static void loginListener(String user, String password, boolean correctPassword)
	{
		if(correctPassword)
		{
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					MainProfileGUI mainProfileGUI = new MainProfileGUI();
					mainProfileGUI.setUserEmail(emailTextField.getText());
					String[] strigns = new String[10];
					mainProfileGUI.main(strigns);
				}
			});

			loginFrame.setVisible(false);
		} else //wrong password
			JOptionPane.showMessageDialog(null, "Feil passord!");
	}

}
