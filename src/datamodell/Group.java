package datamodell;

import java.util.List;

public class Group extends EventMaker {
	
	private int ID;
	private List<Employee> employees;
	
	public Group(String email, String name) {
		super(email, name);
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
