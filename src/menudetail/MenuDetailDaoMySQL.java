package menudetail;


import static server_main.Common.CLASS_NAME;
import static server_main.Common.URL;
import static server_main.Common.USER;
import static server_main.Common.PASSWORD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDetailDaoMySQL implements MenuDetailDao {
	
	public MenuDetailDaoMySQL() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int update(MenuDetail menuDetail) {
		int count = 0;
		String sql = "UPDATE MENU_DETAIL SET FOOD_AMOUNT = ?," 
				+ "FOOD_ARRIVAL = ?, TOTAL = ?, FOOD_STATUS = ? WHERE ORD_ID = ? AND MENU_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, menuDetail.getFOOD_AMOUNT());
			ps.setBoolean(2, menuDetail.isFOOD_ARRIVAL());
			ps.setInt(3, menuDetail.getTOTAL());
			ps.setBoolean(4, menuDetail.isFOOD_STATUS());
			ps.setInt(5, menuDetail.getORD_ID());
			ps.setString(6, menuDetail.getMENU_ID());
			System.out.println(ps.toString());
			count = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
	public List<MenuDetail> getAll() {
//		String sql = "SELECT ORD_ID, MENU_ID, FOOD_AMOUNT, FOOD_ARRIVAL, TOTAL, FOOD_STATUS FROM MENU_DETAIL;";
		String sql = "SELECT d.ORD_ID, TABLE_ID, d.MENU_ID, FOOD_NAME, FOOD_AMOUNT, FOOD_ARRIVAL, TOTAL, d.FOOD_STATUS " + 
				"FROM MENU_DETAIL d " + 
				"join MENU m on d.MENU_ID = m.MENU_ID " + 
				"join ORDER_MEAL o on d.ORD_ID = o.ORD_ID;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<MenuDetail> detaiList = new ArrayList<MenuDetail>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int ORD_ID = rs.getInt(1);
				int TABLE_ID = rs.getInt(2);
				String MENU_ID = rs.getString(3);
				String FOOD_NAME = rs.getString(4);
				int FOOD_AMOUNT = rs.getInt(5);
				boolean FOOD_ARRIVAL = rs.getBoolean(6);
				int TOTAL = rs.getInt(7);
				boolean FOOD_STATUS = rs.getBoolean(8);
				MenuDetail menuDetail = new MenuDetail(ORD_ID, TABLE_ID, MENU_ID, FOOD_NAME, FOOD_AMOUNT, FOOD_ARRIVAL, TOTAL, FOOD_STATUS);
				detaiList.add(menuDetail);
			}
			return detaiList;
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
		return detaiList;
	}

	@Override
	public List<MenuDetail> getAllByMemberId(int memberId) {
		String sql = "SELECT d.ORD_ID, TABLE_ID, d.MENU_ID, FOOD_NAME, FOOD_AMOUNT, FOOD_ARRIVAL, TOTAL, d.FOOD_STATUS " + 
				"FROM MENU_DETAIL d " +
				"join MENU m on d.MENU_ID = m.MENU_ID " + 
				"join ORDER_MEAL o on d.ORD_ID = o.ORD_ID " +
				"WHERE MEMBER_ID = ?;";
//		String sql = "SELECT d.ORD_ID, TABLE_ID, d.MENU_ID, FOOD_NAME, FOOD_IMAGE, FOOD_AMOUNT, FOOD_ARRIVAL, TOTAL, d.FOOD_STATUS FROM MENU_DETAIL d join MENU m on d.MENU_ID = m.MENU_ID join ORDER_MEAL o on d.ORD_ID = o.ORD_ID WHERE MEMBER_ID = ?;\n" ;
		Connection connection = null;
		PreparedStatement ps = null;
		List<MenuDetail> detas = new ArrayList<MenuDetail>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1,memberId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int ORD_ID = rs.getInt(1);
				int TABLE_ID = rs.getInt(2);
				String MENU_ID = rs.getString(3);
				String FOOD_NAME = rs.getString(4);
				int FOOD_AMOUNT = rs.getInt(5);
				boolean FOOD_ARRIVAL = rs.getBoolean(6);
				int TOTAL = rs.getInt(7);
				boolean FOOD_STATUS = rs.getBoolean(8);
				MenuDetail menuDetail = new MenuDetail(ORD_ID, TABLE_ID, MENU_ID, FOOD_NAME, FOOD_AMOUNT, FOOD_ARRIVAL, TOTAL, FOOD_STATUS);
				detas.add(menuDetail);
			}
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
		return detas;
	}

}
