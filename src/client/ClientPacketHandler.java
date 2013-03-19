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
			handleLoginSuccessful((String)params.get(0), (String) params.get(1));
			break;
		case ALERTRECEIVED:
			System.out.println("I got an alert! Event: "+params.get(0));
			break;
		case LATESTUSERSRECEIVED:
			Client.latestLoggedInUsers = (ArrayList<String>) params;
		default:
			System.err.println("Header not recognized!");
			break;
		}

		return null; //TODO
	}

	private static void handleLoginSuccessful(String string, String string2) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(null);
		
	}

}