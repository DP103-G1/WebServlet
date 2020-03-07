package box;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static server_main.Common.CLASS_NAME;
import static server_main.Common.USER;
import static server_main.Common.URL;
import static server_main.Common.PASSWORD;


public class BoxDao implements Dao<Box>{
	
	public BoxDao() {
		super();
		try {
			Class.forName(CLASS_NAME);
		}catch (ClassNotFoundException e) {
			e.	printStackTrace();	
			}
	}
	
	
	@Override
	public int add(Box box) {
		int count = 0;
		String sql = "INSERT into SUG_BOX (MEMBER_ID,TOPIC,PURPOSE,INFO_SOURCE,DATE,SATISFIED,FEED_BACK) "+
				"VALUES(?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement ps= null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1,box.getMember());
			ps.setString(2, box.getTopic());
			ps.setString(3, box.getPurpose());
			ps.setString(4, box.getInfo());
			ps.setString(5, box.getDate());
			ps.setFloat(6, box.getSatisfied());
			ps.setString(7, box.getFeed_back());
			count = ps.executeUpdate();
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
		return count;
	}

	@Override
	public int update(String id, Box box) {
		int count = 0;
		String sql = "UPDATE SUG_BOX SET TOPIC = ?, PURPOSE = ?, INFO_SOURCE = ?, DATE = ?, "
				+ "SATISFIED = ?, FEED_BACK = ?, REPLY = ? WHERE SUG_ID = ?;"; 
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, box.getTopic());
			ps.setString(2, box.getPurpose());
			ps.setString(3, box.getInfo());
			ps.setString(4, box.getDate());
			ps.setFloat(5, box.getSatisfied());
			ps.setString(6, box.getFeed_back());
			ps.setString(7, box.getReply());
			ps.setInt(8, box.getId());
			count = ps.executeUpdate();
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
		return count;
	}

	@Override
	public int deleteById(String id) {
		int count = 0;
		String sql = "DELETE FROM box WHERE id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, id);
			count = ps.executeUpdate();
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
		return count;
	}
	
	
	@Override
	public Box getById(String id) {
		String sql ="SELECT SUG_ID,MEMBER_ID,TOPIC,PURPOSE,IFO_SOURCE,DATE,SATISFIED,FEED_BACK,REPLY;";
		Connection conn = null;
		PreparedStatement ps =null;
		Box box = null;
		try {
			conn = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int SUG_ID = rs.getInt(1);
				int Member_ID = rs.getInt(2);
				String TOPIC = rs.getString(3);
				String PURPOSE = rs.getString(4);
				String IFO_SOURCE = rs.getString(5);
				String DATE =rs.getString(6);
				Float SATISFIED = rs.getFloat(7);
				String FEED_BACK = rs.getString(8);
				String REPLY = rs.getString(9);
				box = new Box(SUG_ID, Member_ID, TOPIC ,PURPOSE, IFO_SOURCE, DATE, SATISFIED, FEED_BACK,REPLY);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
		return box;
	}
	
	@Override
	public List<Box> getAll() {
		String sql="SELECT SUG_ID,MEMBER_ID,TOPIC,PURPOSE,INFO_SOURCE, DATE,SATISFIED,FEED_BACK,REPLY FROM SUG_BOX;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Box> boxList = new ArrayList<Box>();
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int SUG_ID = rs.getInt(1);
				int MEMBER_ID = rs.getInt(2);
				String TOPIC = rs.getString(3);
				String PURPOSE = rs.getString(4);
				String INFO_SOURCE = rs.getString(5);
				String DATE = rs.getString(6);
				Float SATISFIED = rs.getFloat(7);
				String FEED_BACK = rs.getString(8);
				String REPLY = rs.getString(9);
				Box box = new Box(SUG_ID, MEMBER_ID, TOPIC,PURPOSE, INFO_SOURCE, DATE, SATISFIED, FEED_BACK,REPLY);
				boxList.add(box);
			}
			return boxList;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps != null);{
					ps.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return boxList;
	}
}

