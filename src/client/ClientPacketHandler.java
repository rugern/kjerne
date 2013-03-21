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
 */
public class ClientPacketHandler {

	public synchronized static CommPack<?> handlePacket(CommPack<?> message)
	{
		ArrayList<?> params = message.getParamList();
		CommEnum header = message.getMessageName();

		switch(header) {
		case LOGINSUCCESSFUL:
			System.out.println("Login successful!");
			Client.loggedOn((String) params.get(0));
			break;
		case LOGINFAILED: 
			System.out.println("Login failed!");
			Client.loggedOn = false;
			break;
		case ALREADYLOGGEDIN:
			System.out.println("You're already logged in!");
			break;
		case ALERTRECEIVED:
			System.out.println("I got an alert! Event: "+params.get(0)); //TODO
			Client.mainGui.eventChangedAlert((Integer)params.get(0)); //eventID
			break;
		default:
			System.err.println("Header not recognized!");
			break;
		}

		return null; //TODO
	}


}