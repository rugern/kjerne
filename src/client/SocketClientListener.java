package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.JOptionPane;

import server.CommPack;
import server.ServerThread;

/**
 * 
 *
 */
public class SocketClientListener extends Thread
{
	static int count = 0;
	ObjectInputStream in;
	//LinkedList<CommMessage<?>> updateQueue; //A queue of updates pending towards client
	boolean stop = false;
	public SocketClientListener(ObjectInputStream in)
	{
		this.in = in;
		//updateQueue = new LinkedList<CommMessage<?>>();
	}
	@Override
	public void run()
	{
		listenToInput();
	}
	public void stopThread()
	{
		stop = true;
	}

	/**
	 * Listens for reply from server
	 */
	private void listenToInput()
	{
		while(!stop)
		{
			try {
				CommPack reply = (CommPack) in.readObject(); 
				System.out.println(reply.getMessageName()+" :"+reply.getParamList());

				ResultSet rs = (ResultSet)reply.getParamList().get(0);

				
				try {
					while(rs.next())
					{
						System.out.println(rs.getString(1));
						System.out.println(rs.getString(2));
						System.out.println(rs.getString(3));
						System.out.println(rs.getString(4));
						System.out.println(rs.getString(5));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//update must be handled according to what is updated in calendar
				//TODO here

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println("Class not recognized");
			} catch (IOException e) {
				//e.printStackTrace();
				System.err.println("Socket closed!");
				stopThread();
			}
		}
	}
}
