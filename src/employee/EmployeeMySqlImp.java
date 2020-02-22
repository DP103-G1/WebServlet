package employee;

import static server_main.Common.CLASS_NAME;
import static server_main.Common.PASSWORD;
import static server_main.Common.URL;
import static server_main.Common.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

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
	public int updatePassword(int employee_id,String password) {
		int count = 0;
		String sql = "UPDATE Employee SET PASSWORD = ? WHERE EMPLOYEE_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, password);
			ps.setInt(2, employee_id);
			count = ps.executeUpdate();
			
		}catch(SQLException e) {
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

	@Override
	public List<Employee> getAll() {
		String sql = "SELECT EMPLOYEE_ID,ACCOUNT,PASSWORD FROM EMPLOYEE;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int employee_Id = rs.getInt(1);
				String account = rs.getString(2);
				String password = rs.getString(3);
				Employee employee = new Employee(employee_Id, account, password);
				employeeList.add(employee);
				
			}
			return employeeList;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return employeeList;
	}

	@Override
	public String login(String account, String password) {
		String employeeId = null;
		String sql = "SELECT EMPLOYEE_ID FROM `EMPLOYEE` WHERE ACCOUNT = ? AND PASSWORD = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, account);
			ps.setString(2, password);
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				employeeId = rs.getString(1);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeeId;
	}
	
	

}
