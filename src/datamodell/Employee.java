package datamodell;

//Dette er en test fra Vikas
//dette er en test fra �yvind

public class Employee extends EventMaker {
	
	public Employee(String email, String name) {
		super(email, name);
	}
	
	private int ssn;

	public int getID()
	{
		return ssn;
	}
}
