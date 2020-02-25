package ezeats;

import static server_main.Common.CLASS_NAME;
import static server_main.Common.URL;
import static server_main.Common.USER;
import static server_main.Common.PASSWORD;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDaoMySQL implements MenuDao {

	public MenuDaoMySQL() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int add(Menu menu, byte[] image) {
		int count = 0;
		String sql = "INSERT INTO Menu" + "(MENU_ID, FOOD_NAME, FOOD_PRICE, FOOD_STATUS, FOOD_IMAGE, FOOD_CONTENT ) "
				+ "VALUES(?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, menu.getMENU_ID());
			ps.setString(2, menu.getFOOD_NAME());
			ps.setInt(3, menu.getFOOD_PRICE());
			ps.setInt(4, menu.getFOOD_STATUS());
			ps.setBytes(5, image);
			ps.setString(6, menu.getFOOD_CONTENT());
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
	public int update(Menu menu, byte[] image) {
		int count = 0;
		String sql = "";
		if (image != null) {
			sql = "UPDATE Menu SET FOOD_NAME = ?, FOOD_PRICE = ?,"
					+ "FOOD_STATUS = ?, FOOD_CONTENT = ?, FOOD_IMAGE = ? WHERE MENU_ID = ?;";
		} else {
			sql = "UPDATE Menu SET FOOD_NAME = ?, FOOD_PRICE = ?,"
					+ "FOOD_STATUS = ?, FOOD_CONTENT = ? WHERE MENU_ID = ?;";
		}
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, menu.getFOOD_NAME());
			ps.setInt(2, menu.getFOOD_PRICE());
			ps.setInt(3, menu.getFOOD_STATUS());
			ps.setString(4, menu.getFOOD_CONTENT());
			if (image != null) {
				ps.setBytes(5, image);
				ps.setString(6, menu.getMENU_ID());
			} else {
				ps.setString(5, menu.getMENU_ID());
			}
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public byte[] getImage(String menu_id) {
		String sql = "SELECT FOOD_IMAGE FROM Menu WHERE MENU_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] image = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, menu_id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getBytes(1);
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
		return image;
	}

	@Override
	public List<byte[]> getImageList() {
		String sql = "SELECT FOOD_IMAGE FROM MENU ORDER BY rand() limit 5;";
		Connection connection = null;
		List<byte[]> images = new ArrayList<>();
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				byte[] image = rs.getBytes(1);
				images.add(image);
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
		return images;
	}

	@Override
	public Menu getId(String menu_id) {
		String sql = "SELECT FOOD_NAME, FOOD_PRICE, FOOD_STATUS, FOOD_CONTENT FROM Menu WHERE MENU_ID = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		Menu menu = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1, menu_id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String FOOD_NAME = rs.getString(1);
				int FOOD_PRICE = rs.getInt(2);
				int FOOD_STATUS = rs.getInt(3);
				String FOOD_CONTENT = rs.getString(4);
				menu = new Menu(menu_id, FOOD_NAME, FOOD_PRICE, FOOD_STATUS, FOOD_CONTENT);
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
		return menu;
	}

	@Override
	public List<Menu> getAll() {
		String sql = "SELECT MENU_ID, FOOD_NAME, FOOD_PRICE, FOOD_STATUS, FOOD_CONTENT FROM MENU;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Menu> menuList = new ArrayList<Menu>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String MENU_ID = rs.getString(1);
				String FOOD_NAME = rs.getString(2);
				int FOOD_PRICE = rs.getInt(3);
				int FOOD_STATUS = rs.getInt(4);
				String FOOD_CONTENT = rs.getString(5);
				Menu menu = new Menu(MENU_ID, FOOD_NAME, FOOD_PRICE, FOOD_STATUS, FOOD_CONTENT);
				menuList.add(menu);
			}
			return menuList;
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
		return menuList;
	}

	@Override
	public List<Menu> getAllShow() {
		String sql = "SELECT MENU_ID, FOOD_NAME, FOOD_PRICE, FOOD_CONTENT FROM MENU WHERE FOOD_STATUS = 1;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Menu> menuList = new ArrayList<Menu>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String MENU_ID = rs.getString(1);
				String FOOD_NAME = rs.getString(2);
				int FOOD_PRICE = rs.getInt(3);
				String FOOD_CONTENT = rs.getString(4);
				Menu menu = new Menu(MENU_ID, FOOD_NAME, FOOD_PRICE, 1, FOOD_CONTENT);
				menuList.add(menu);
			}
			return menuList;
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
		return menuList;
	}

}
