package order;

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
import java.util.Date;
import java.util.List;

import Booking_Web.Booking;
import menudetail.MenuDetail;

public class OrderDaoMySQL implements OrderDao {
	public OrderDaoMySQL() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int add(Order order) {
		int count = 0;
		String sql = "INSERT INTO `ORDER_MEAL` "
				+ "(MEMBER_ID, BK_ID, ORD_TOTAL, ORD_STATUS, ORD_BILL) "
				+ "VALUES(?, ?, ?, ?, ?);";
		String sqlMenuDetail = "INSERT INTO `MENU_DETAIL`(ORD_ID, MENU_ID, FOOD_AMOUNT, FOOD_ARRIVAL, TOTAL, FOOD_STATUS) "
				+ " VALUES (?, ?, ?, ?, ?, ?);";
		String sqlGetOrderId = "SELECT LAST_INSERT_ID();";
//		String sqlGetOrderId = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'EZeats' AND TABLE_NAME = 'ORDER_MEAL';";
		Connection connection = null;
		PreparedStatement ps = null;
		PreparedStatement psMenuDetail = null;
		PreparedStatement psGetOrderId = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, order.getMEMBER_ID());
			ps.setInt(2, order.getBK_ID());
			ps.setInt(3, order.getORD_TOTAL());
			ps.setBoolean(4, order.isORD_STATUS());
			ps.setBoolean(5, order.isORD_BILL());
			count = ps.executeUpdate();
			if (count != 0) {
				int orderId = 0;
				psGetOrderId = connection.prepareStatement(sqlGetOrderId);
				ResultSet rs = psGetOrderId.executeQuery();
				if (rs.next()) {
					orderId = rs.getInt(1);
					System.out.println(orderId);
					List<MenuDetail> menuDetails = order.getMenuDetails();
					psMenuDetail = connection.prepareStatement(sqlMenuDetail);
					for (MenuDetail menuDetail : menuDetails) {
						psMenuDetail.setInt(1, orderId);
						psMenuDetail.setString(2, menuDetail.getMENU_ID());
						psMenuDetail.setInt(3, menuDetail.getFOOD_AMOUNT());
						psMenuDetail.setBoolean(4, menuDetail.isFOOD_ARRIVAL());
						psMenuDetail.setInt(5, menuDetail.getTOTAL());
						psMenuDetail.setBoolean(6, menuDetail.isFOOD_STATUS());
						count = psMenuDetail.executeUpdate();
						if (count == 0) {
							connection.rollback();
							break;
						}
					}
					connection.commit();
				} else {
					count = 0;
					connection.rollback();
				}
			} else {
				connection.rollback();
			}
		} catch (Exception e) {
			count = 0;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	public int update(Order order) {
		int count = 0;
		String sql = "UPDATE ORDER_MEAL SET MEMBER_ID = ?, BK_ID = ?,"
				+ " ORD_TOTAL = ?, ORD_STATUS = ?, ORD_BILL = ?" + "WHERE ORD_ID = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, order.getMEMBER_ID());
			ps.setInt(2, order.getBK_ID());
			ps.setInt(3, order.getORD_TOTAL());
			ps.setBoolean(4, order.isORD_STATUS());
			ps.setBoolean(5, order.isORD_BILL());
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
	public Order getId(int ord_id) {
		String sql = "SELECT MEMBER_ID, BK_ID, ORD_TOTAL, ORD_STATUS, ORD_BILL"
				+ "FROM ORDER_MEAL WHERE ORD_ID = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		Order order = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ord_id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int MEMBER_ID = rs.getInt(1);
				int BK_ID = rs.getInt(2);
				int ORD_TOTAL = rs.getInt(3);
				Boolean ORD_STATUS = rs.getBoolean(4);
				Boolean ORD_BILL = rs.getBoolean(5);
				order = new Order(ord_id, MEMBER_ID, BK_ID, ORD_TOTAL, ORD_STATUS, ORD_BILL);
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
		return order;
	}

	@Override
	public List<Order> getAll() {
		String sql = "SELECT ORD_ID, MEMBER_ID, BK_ID, ORD_TOTAL, ORD_STATUS, ORD_BILL FROM ORDER_MEAL;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Order> orderList = new ArrayList<Order>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int ORD_ID = rs.getInt(1);
				int MEMBER_ID = rs.getInt(2);
				int BK_ID = rs.getInt(3);
				int ORD_TOTAL = rs.getInt(4);
				boolean ORD_STATUS = rs.getBoolean(5);
				boolean ORD_BILL = rs.getBoolean(6);
				Order order = new Order(ORD_ID, MEMBER_ID, BK_ID, ORD_TOTAL, ORD_STATUS, ORD_BILL);
				orderList.add(order);
			}
			return orderList;
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

			}
		}
		return orderList;
	}

	@Override
	public List<Order> getAllByMemberId(int memberId) {
		String sql = "SELECT ORD_ID, BK_ID, ORD_TOTAL, ORD_STATUS, ORD_BILL FROM ORDER_MEAL WHERE MEMBER_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Order> orders = new ArrayList<Order>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, memberId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int ORD_ID = rs.getInt(1);
				int BK_ID = rs.getInt(2);
				int ORD_TOTAL = rs.getInt(3);
				boolean ORD_STATUS = rs.getBoolean(4);
				boolean ORD_BILL = rs.getBoolean(5);
				Order order = new Order(ORD_ID, BK_ID, ORD_TOTAL, ORD_STATUS, ORD_BILL);
				orders.add(order);
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

			}
		}
		return orders;
	}

	@Override
	public int getBkid(int memberId) {
		String sql = "SELECT BK_ID FROM EZeats.BOOKING where MEMBER_ID = ? order by BK_DATE desc limit 1;";
		Connection conn = null;
		PreparedStatement ps = null;
		int bkId = -1;
		try {
			conn = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1,memberId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bkId = rs.getInt(1);
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
		return bkId;
	}
	
	@Override
	public List<Order> getAllByOrdId(int ordId) {
		String sql = "SELECT d.MENU_ID, FOOD_NAME, FOOD_AMOUNT, FOOD_ARRIVAL, TOTAL FROM EZeats.MENU_DETAIL d join EZeats.MENU m on d.MENU_ID = m.MENU_ID where ORD_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Order> menuDetails = new ArrayList<Order>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, ordId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String menuId = rs.getString(1);
				String foodName = rs.getString(2);
				int foodAmount = rs.getInt(3);
				boolean foodArrival = rs.getBoolean(4);
				int total = rs.getInt(5);
				Order menuDetail = new Order(menuId, foodName, foodAmount, foodArrival,total);
				menuDetails.add(menuDetail);
			}
			

			return menuDetails;
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
		return menuDetails;
	}

}
