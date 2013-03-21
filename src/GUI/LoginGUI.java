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

import client.Client;
import client.SocketClient;

public class LoginGUI extends JPanel {

	JPanel centerPanel;
	public JFrame loginFrame;
	JTextField emailTextField; //TODO maybe shouldn't be static ?
	JPasswordField passwordField;

	public void init() {
		
		if(!Client.serverUp){
			JOptionPane.showMessageDialog(this, "Could not connect to server", "Connection Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		loginFrame = new JFrame("Logg inn");
		loginFrame.getContentPane().add(this);

		loginFrame.setMinimumSize(new Dimension(650, 660));
		loginFrame.setMaximumSize(new Dimension(650, 660));
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setSize(650, 660);
	}

	public LoginGUI() {

		init();
		
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

			String user = emailTextField.getText(); 
			String pw = passwordField.getText(); 
			ArrayList<String> al = new ArrayList();
			al.add(user);
			al.add(pw);
			
			//If we're connected to a server, initiate login.
			if(Client.serverUp)
			{
				Client.sock.sendMessage(new CommPack(CommEnum.LOGIN, al));
				sleep(500); //sleep for a bit then ask server if we're logged in, eh
			}
			
			if(Client.loggedOn == false)
			{
				JOptionPane.showMessageDialog(null, "Check username and password!");
			}
		}
	}

/*	public class LoginListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if(true) //TODO
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
	}*/
	
	void sleep(int sec)
	{
		try {
			Thread.sleep(sec);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
