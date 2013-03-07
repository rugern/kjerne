package datamodell;

import java.util.List;

public class Group extends EventMaker {
	
	private int ID;
	private List<Employee> employees;
	
	public Group(String email) {
		super(email);
	}
	
	public void addEmployee(Employee employee)
	{
		employees.add(employee);
	}
	
	public List<Employee> getEmployees()
	{
		return employees;
	}
}
