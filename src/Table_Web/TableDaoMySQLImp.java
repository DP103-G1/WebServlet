package Table_Web;

import static server_main.Common.CLASS_NAME;
import static server_main.Common.URL;
import static server_main.Common.USER;
import static server_main.Common.PASSWORD;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.jni.OS;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.Result;

public class TableDaoMySQLImp implements Table_Dao {

	public TableDaoMySQLImp() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Table t) {
		int count = 0;
		String sql = "INSERT INTO TABLE_DATA (TABLE_ID, TABLE_PEOPLE) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, t.getTableId());
			ps.setString(2, t.getTablePeople());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return count;
	}

	@Override
	public int update(Table t) {
		int count = 0;
		String sql = "";
		sql = "UPDATE TABLE_DATA SET TABLE_PEOPLE = ? WHERE TABLE_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, t.getTablePeople());
			ps.setString(2,t.getTableId());

			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return count;
	}

	@Override
	public int deleteId(String tableId) {
		int count = 0;
		String sql = "DELETE FROM TABLE_DATA WHERE TABLE_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, tableId);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return count;
	}
 
	@Override
	public Table getTableId(String tableId) {
		String sql = "SELECT TABLE_ID,TABLE_PEOPLE,TABLE_STATUS FROM TABLE_DATA = ?; ";
		Connection conn = null;
		PreparedStatement ps = null;
		Table table = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps.getConnection().prepareStatement(sql);
			ps.setString(1, tableId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String tableId1 = rs.getString(1);
				String tablePeople = rs.getString(2);
				int tableStatus = rs.getInt(3);
				table = new Table(tableId1, tablePeople, tableStatus);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return table;
	}

	@Override
	public List<Table> getAll() {
		String sql = "SELECT TABLE_ID ,TABLE_PEOPLE,TABLE_STATUS FROM TABLE_DATA ORDER BY TABLE_ID;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Table> tableList = new ArrayList<Table>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String tableId = rs.getString(1);
				String tablePeople = rs.getString(2);
				Table table = new Table(tableId, tablePeople, 0);
				tableList.add(table);
			}
			return tableList;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return tableList;
	}

	

}