package server;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Every object sent through the TCP connection is of the class CommMessage, messageName implicitly denotes type of paramList.
 * 
 * @author halvor
 *
 * @param <T> the type of the elements in the ArrayList
 * 
 */
public class CommPack<T> implements Serializable
{
	CommEnum messageName; //The 'header' of the packet (e.g. ADDEVENT, LOGIN. See CommEnum for more information)
	ArrayList<T> paramList; //The 'payload' of the packet, the parameters are ordered in an arraylist of size determined by the flag (e.g. LOGIN would have "username", "password", 2 parameters) 
	
	public CommPack(CommEnum c, ArrayList<T> paramList)
	{
		this.messageName = c;
		this.paramList =  paramList;
	}

	public CommEnum getMessageName() {
		return messageName;
	}

	public ArrayList<?> getParamList() {
		return paramList;
	}
	
	public String toString() {
		return messageName.name() + ": "+paramList;
	}

}
