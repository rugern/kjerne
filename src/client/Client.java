package client;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import server.CommPack;

import GUI.LoginGUI;
import GUI.MainProfileGUI;

public class Client {

	public static LoginGUI login;
	public static MainProfileGUI mainGui;
	public static SocketClient sock;
	
	public static boolean loggedOn = false;
	public static String email;
	public static boolean serverUp;
	public static ArrayList<String> latestLoggedInUsers;
	public static CommPack<?> latestPack;
	
	public static void main(String[] args) {
		
		sock = new SocketClient("127.0.0.1", 1337);
		
		try {
			sock.run();
			serverUp = true;
		} catch (IOException e) {
			serverUp = false;
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				login = new LoginGUI();
				if(!serverUp) return; //exit completely if server not up
				login.loginFrame.setVisible(true);
			}
		});
	}
	
	public static void loggedOn(String email) {
		Client.loggedOn = true;
		Client.email = email;
		System.out.println("Setting login frame invisible");
		login.loginFrame.setVisible(false);
		mainGui.main(null);
	}
	
	public static void close()
	{
		sock.close();
	}
}
