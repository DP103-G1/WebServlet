package employee;

import static server_main.Common.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class EmployeeMySqlImp implements EmployeeDao{
	
	 public EmployeeMySqlImp() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int updatePassword(Employee employee,int employee_id) {
		int count = 0;
		String sql = "UPDATE Employee SET PASSWORD = ? WHERE EMPLOYEE_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1,employee.getEmployee_Id());
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}

}
