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
			
			loginTest();
			sendMessageTest(); //test 1
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void loginTest() {
		ArrayList al = new ArrayList();
		al.add("username1");
		al.add("user1");
		client.sendMessage(new CommPack(CommEnum.LOGIN, al));	
	}

	/**
	 * Tests that packets can be sent to server
	 */
	public static void sendMessageTest()
	{
		ArrayList users = new ArrayList();
		users.add("username1");
		
		ArrayList al = new ArrayList();
		al.add(users);
		al.add(23);
		
		client.sendMessage(new CommPack(CommEnum.ALERTEVENTCHANGED, al));	
	}
}
