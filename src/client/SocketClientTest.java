package client;

import java.io.IOException;

public class SocketClientTest {

	public static void main(String[] args)
	{
		SocketClient client = new SocketClient("localhost", 1337);
		try {
			client.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
