package server;

public class SocketServerTest {

	/**
	 * Tests that server starts
	 */
	public static void main (String[] args)
	{
		SocketServer server = new SocketServer(1337);
		server.run();
	}

}
