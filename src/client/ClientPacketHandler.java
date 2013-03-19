package client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
			System.out.println("CLient: Login successful!");
			break;
		case ALERT:
			System.out.println("I got an alert!");
			break;
		default:
			System.err.println("Header not recognized!");
			break;
		}

		return null; //TODO
	}
}