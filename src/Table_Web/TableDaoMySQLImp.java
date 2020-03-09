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

import Booking_Web.BookingDaoMySQLImp;

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
			ps.setInt(1, t.getTableId());
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
			ps.setInt(2, t.getTableId());

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
	public int deleteId(int tableId) {
		int count = 0;
		String sql = "DELETE FROM TABLE_DATA WHERE TABLE_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, tableId);
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
	public Table getTableId(int tableId) {
		String sql = "SELECT TABLE_ID,TABLE_PEOPLE FROM TABLE_DATA = ?; ";
		Connection conn = null;
		PreparedStatement ps = null;
		Table table = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps.getConnection().prepareStatement(sql);
			ps.setInt(1, tableId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int tableId1 = rs.getInt(1);
				String tablePeople = rs.getString(2);
				table = new Table(tableId1, tablePeople);
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
		String sql = "SELECT TABLE_ID ,TABLE_PEOPLE FROM TABLE_DATA ORDER BY TABLE_ID;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Table> tableList = new ArrayList<Table>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int tableId = rs.getInt(1);
				String tablePeople = rs.getString(2);
				Table table = new Table(tableId, tablePeople);
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

	@Override
	public List<Table> getAllOrdId() {
		String sql = "SELECT TABLE_ID ,TABLE_PEOPLE, ORD_ID, status FROM TABLE_DATA ORDER BY TABLE_ID;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Table> tableord = new ArrayList<Table>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int tableId = rs.getInt(1);
				String tablePeople = rs.getString(2);
				int ord_id = rs.getInt(3);
				boolean status = rs.getBoolean(4);
				Table table = new Table(tableId, tablePeople, ord_id, status);
				tableord.add(table);
			}
			return tableord;

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
		return tableord;
	}

	@Override
	public int updateTableStatus(Table table) {
		int count = 0;
		String sql = "";
		sql = "UPDATE TABLE_DATA SET ORD_ID = ? WHERE TABLE_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, table.getORD_ID());
			ps.setInt(2, table.getTableId());
			count = ps.executeUpdate();
		} catch (Exception e) {
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
		return count;
	}

	@Override
	public int updateStatus(Table table) {
		int count = 0;
		String sql = "UPDATE TABLE_DATA SET status = ? WHERE TABLE_ID = ?;";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setBoolean(1, table.isStatus());
			ps.setInt(2, table.getTableId());
			count = ps.executeUpdate();
			if (count != 0) {
				count = new BookingDaoMySQLImp().getbkId(table.getORD_ID()).getMember().getmember_Id();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Table getUsingTableByMemberId(int memberId) {
		Table table = null;
		String sql = "SELECT `TABLE_DATA`.TABLE_ID, TABLE_PEOPLE, ORD_ID, `TABLE_DATA`.status FROM `TABLE_DATA` "
				+ "JOIN `BOOKING` ON `TABLE_DATA`.ORD_ID = `BOOKING`.BK_ID WHERE MEMBER_ID = ?;";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, memberId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int tableId = rs.getInt(1);
				String tablePeople = rs.getString(2);
				int ordId = rs.getInt(3);
				boolean status = rs.getBoolean(4);
				table = new Table(tableId, tablePeople, ordId, status);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table;
	}
}
