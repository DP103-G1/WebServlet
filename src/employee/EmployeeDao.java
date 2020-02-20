package employee;

import java.util.List;

public interface EmployeeDao {
	
	int updatePassword(int employee_Id ,String password);
	
	List<Employee> getAll();
	
	String login(String account, String password);

}
