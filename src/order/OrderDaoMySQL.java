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
import java.util.List;

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
				+ "(MEMBER_ID, TABlE_ID, ORD_TOTAL, ORD_STATUS, ORD_BILL) "
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
			ps.setInt(2, order.getTABLE_ID());
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
		String sql = "UPDATE ORDER_MEAL SET MEMBER_ID = ?, TABLE_ID = ?,"
				+ "TABLE_BELL = ?, ORD_TOTAL = ?, ORD_STATUS = ?, ORD_BILL = ?" + "WHERE ORD_ID";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, order.getMEMBER_ID());
			ps.setInt(2, order.getTABLE_ID());
			ps.setBoolean(3, order.isTABLE_BELL());
			ps.setInt(4, order.getORD_TOTAL());
			ps.setBoolean(5, order.isORD_STATUS());
			ps.setBoolean(6, order.isORD_BILL());
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
		String sql = "SELECT MEMBER_ID, TABLE_ID, TABLE_BELL, ORD_TOTAL, ORD_STATUS, ORD_BILL"
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
				int TABLE_ID = rs.getInt(2);
				Boolean TABLE_BELL = rs.getBoolean(3);
				int ORD_TOTAL = rs.getInt(4);
				Boolean ORD_STATUS = rs.getBoolean(5);
				Boolean ORD_BILL = rs.getBoolean(6);
				order = new Order(ord_id, MEMBER_ID, TABLE_ID, TABLE_BELL, ORD_TOTAL, ORD_STATUS, ORD_BILL);
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
		String sql = "SELECT ORD_ID, MEMBER_ID, TABLE_ID, TABLE_BELL, ORD_TOTAL, ORD_STATUS, ORD_BILL FROM ORDER_MEAL;";
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
				int TABLE_ID = rs.getInt(3);
				boolean TABLE_BELL = rs.getBoolean(4);
				int ORD_TOTAL = rs.getInt(5);
				boolean ORD_STATUS = rs.getBoolean(6);
				boolean ORD_BILL = rs.getBoolean(7);
				Order order = new Order(ORD_ID, MEMBER_ID, TABLE_ID, TABLE_BELL, ORD_TOTAL, ORD_STATUS, ORD_BILL);
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
		String sql = "SELECT ORD_ID, TABLE_ID, TABLE_BELL, ORD_TOTAL, ORD_STATUS, ORD_BILL FROM ORDER_MEAL WHERE MEMBER_ID = ?;";
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
				int TABLE_ID = rs.getInt(2);
				boolean TABLE_BELL = rs.getBoolean(3);
				int ORD_TOTAL = rs.getInt(4);
				boolean ORD_STATUS = rs.getBoolean(5);
				boolean ORD_BILL = rs.getBoolean(6);
				Order order = new Order(ORD_ID, TABLE_ID, TABLE_BELL, ORD_TOTAL, ORD_STATUS, ORD_BILL);
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
}
