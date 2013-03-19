package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import server.CommEnum;
import server.CommPack; 

public class SocketClientTest {

	private static SocketClient client;
	
	public static void main(String[] args)
	{
		client = new SocketClient("127.0.0.1", 1337);
		try {
			client.run();
			
			sendMessageTest(); //test 1
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests that packets can be sent to server
	 */
	public static void sendMessageTest()
	{
		ArrayList users = new ArrayList();
		users.add("username1");
		
		client.sendMessage(new CommPack(CommEnum.ALERT, 23));		
	}
}
