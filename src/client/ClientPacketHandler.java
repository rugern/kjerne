package client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.SwingUtilities;

import GUI.MainProfileGUI;

import server.CommEnum;
import server.CommPack;

import datamodell.Employee;

/**
 * Handles the replies the client gets from server appropriately. Similar to serverpackethandler
 * but lite.
 * @author Jama
 *
 */
public class ClientPacketHandler {

	public synchronized static CommPack<?> handlePacket(CommPack<?> message)
	{
		ArrayList<?> params = message.getParamList();
		CommEnum header = message.getMessageName();

		switch(header) {
		case LOGINSUCCESSFUL:
			//tell GUI that pw and username right
			System.out.println("Client: Login successful!");
			//handleLoginSuccessful((String)params.get(0), (String) params.get(1));
			GUI.LoginGUI.loginListener((String)params.get(0), (String) params.get(1), true);
			break;
		case LOGINFAILED:
			//tell GUI that pw and username wrong
			GUI.LoginGUI.loginListener((String)params.get(0), (String) params.get(1), false);
			break;
		case ALERTRECEIVED:
			System.out.println("I got an alert! Event: "+params.get(0));
			break;
		default:
			System.err.println("Header not recognized!");
			break;
		}

		return null; //TODO
	}
	
	public static void handleLoginSuccessful(String username, String password)
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

		GUI.loginFrame.setVisible(false);
	}
}