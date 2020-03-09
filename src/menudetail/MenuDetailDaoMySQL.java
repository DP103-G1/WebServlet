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
import order.Order;

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
	public List<MenuDetail> getAll(String type) {
		String sqlPart = "";
		switch (type) {
		case "kitchen":
			sqlPart = "STATUS";
			break;
		case "waiter":
			sqlPart = "ARRIVAL";
			break;
		default:
			return new ArrayList<MenuDetail>();
		}
		String sql = "SELECT d.ORD_ID, d.MENU_ID, TABLE_ID, FOOD_NAME, FOOD_AMOUNT, FOOD_ARRIVAL, TOTAL, d.FOOD_STATUS, o.MEMBER_ID " + 
				"FROM MENU_DETAIL d " + 
				"join MENU m on d.MENU_ID = m.MENU_ID " + 
				"join ORDER_MEAL o on d.ORD_ID = o.ORD_ID " +
				"join BOOKING b on o.BK_ID = b.BK_ID and o.MEMBER_ID = b.MEMBER_ID " +
				"WHERE d.FOOD_" + sqlPart + " = 0;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<MenuDetail> detaiList = new ArrayList<MenuDetail>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int ORD_ID = rs.getInt(1);
				String MENU_ID = rs.getString(2);
				int TABLE_ID = rs.getInt(3);
				String FOOD_NAME = rs.getString(4);
				int FOOD_AMOUNT = rs.getInt(5);
				boolean FOOD_ARRIVAL = rs.getBoolean(6);
				int TOTAL = rs.getInt(7);
				boolean FOOD_STATUS = rs.getBoolean(8);
				int memberId = rs.getInt(9);
				MenuDetail menuDetail = new MenuDetail(ORD_ID, MENU_ID, TABLE_ID, FOOD_NAME, FOOD_AMOUNT, FOOD_ARRIVAL, TOTAL, FOOD_STATUS, memberId);
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
		String sql = "SELECT d.ORD_ID, d.MENU_ID, TABLE_ID, FOOD_NAME, FOOD_AMOUNT, FOOD_ARRIVAL, TOTAL, ORD_TOTAL, d.FOOD_STATUS, ORD_BILL " + 
				"FROM MENU_DETAIL d " +
				"join MENU m on d.MENU_ID = m.MENU_ID " + 
				"join ORDER_MEAL o on d.ORD_ID = o.ORD_ID " +
				"join BOOKING b on o.BK_ID = b.BK_ID and o.MEMBER_ID = b.MEMBER_ID " + 
				"WHERE o.MEMBER_ID = ? AND ORD_BILL = 0;";
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
				String MENU_ID = rs.getString(2);
				int TABLE_ID = rs.getInt(3);
				String FOOD_NAME = rs.getString(4);
				int FOOD_AMOUNT = rs.getInt(5);
				boolean FOOD_ARRIVAL = rs.getBoolean(6);
				int TOTAL = rs.getInt(7);
				int ORD_TOTAL = rs.getInt(8);
				boolean FOOD_STATUS = rs.getBoolean(9);
				boolean ORD_BILL = rs.getBoolean(10);
				MenuDetail menuDetail = new MenuDetail(ORD_ID, MENU_ID, TABLE_ID, FOOD_NAME, FOOD_AMOUNT, FOOD_ARRIVAL, TOTAL, ORD_TOTAL, FOOD_STATUS, ORD_BILL);
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
	


	@Override
	public List<MenuDetail> getAllByBkId(int bkId) {
		String sql = "SELECT `MENU_DETAIL`.ORD_ID, `MENU_DETAIL`.MENU_ID, FOOD_NAME, FOOD_AMOUNT, "
				+ "FOOD_ARRIVAL, TOTAL, `MENU_DETAIL`.FOOD_STATUS FROM `ORDER_MEAL` "
				+ "JOIN `MENU_DETAIL` ON `ORDER_MEAL`.ORD_ID = `MENU_DETAIL`.ORD_ID "
				+ "JOIN `MENU` ON `MENU`.MENU_ID = `MENU_DETAIL`.MENU_ID "
				+ "WHERE BK_ID = ? AND ORD_BILL = 0;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<MenuDetail> detail = new ArrayList<MenuDetail>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, bkId);
			ResultSet rs = ps.executeQuery();
			System.out.println(rs);
			while (rs.next()) {
				int ORD_ID = rs.getInt(1);
				String MENU_ID = rs.getString(2);
				String FOOD_NAME = rs.getString(3);
				int FOOD_AMOUNT = rs.getInt(4);
				boolean FOOD_ARRIVAL = rs.getBoolean(5);
				int TOTAL = rs.getInt(6);
				boolean FOOD_STATUS = rs.getBoolean(7);
				MenuDetail menuDetail = new MenuDetail(ORD_ID, MENU_ID, FOOD_NAME, FOOD_AMOUNT, FOOD_ARRIVAL, TOTAL, FOOD_STATUS);
				detail.add(menuDetail);
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
		return detail;
	}

}
