package Booking_Web;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Table_Web.Table;

import static server_main.Common.CLASS_NAME;
import static server_main.Common.USER;
import static server_main.Common.URL;
import static server_main.Common.PASSWORD;
public class BookingDaoMySQLImp implements BookingDao{
	
	public BookingDaoMySQLImp() {
		super();
		try {
			Class.forName(CLASS_NAME);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Booking booking) {
		int count = 0 ;
		String sql = 
		"INSERT INTO BOOKING (TABLE_ID,BK_TIME,BK_DATE,BK_CHILD,BK_ADULT,PHONE) VALUES(?, ?, ?, ?, ?, ?);";
		Connection connection =null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, booking.getTableId());
			ps.setString(2, booking.getBkTime());
			ps.setTimestamp(3, new Timestamp(booking.getBkDate().getTime()));
			ps.setString(4, booking.getBkChild());
			ps.setString(5, booking.getBkAdult());
			ps.setString(6, booking.getBkPhone());
			System.out.println(booking);
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
	public int delete(String bkId) {
		int count = 0 ;
		String sql = "DELETE FROM BOOKING WHERE BOOKING_ID = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, bkId);
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
	public Booking getbkId(String bkId) {
		String sql = "SELECT  TABLE_ID, BK_TIME, BK_DATE, BK_CHILD, BK_ADULT, PHONE FROM BOOKING WHERE BK_ID = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		Booking booking = null;
		try {
			conn = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1, bkId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String tableId = rs.getString(1);
				String bkTime = rs.getString(2);
				Date bkDate = rs.getDate(3);
				String bkChild = rs.getString(4);
				String bkAdult = rs.getString(5);
				String bkPhone = rs.getString(6);
				booking = new Booking(tableId, bkTime, bkDate, bkChild, bkAdult, bkPhone);
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
		return booking;
	}

	@Override
	public List<Booking> getAll() {
		String sql = "SELECT BK_ID, TABLE_ID, BK_TIME, BK_DATE, BK_CHILD, BK_ADULT, PHONE FROM EZeats.BOOKING ;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Booking> bookingList = new ArrayList<Booking>();
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String bkId = rs.getString(1);
				String tableId = rs.getString(2);
				String bkTime = rs.getString(3);
				Date bkDate = rs.getDate(4);
				String bkChild = rs.getString(5);
				String bkAdult = rs.getString(6);
				String bkPhone = rs.getString(7);
				Booking booking = new Booking(bkId,  tableId, bkTime, bkDate, bkChild, bkAdult, bkPhone);
				bookingList.add(booking);
			}
			return bookingList;
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
		return bookingList;
	}

	@Override
	public List<Booking> getAllByMemberId(String memberId) {
		String sql = "SELECT  BK_ID,TABLE_ID, BK_TIME, BK_DATE, BK_CHILD, BK_ADULT, PHONE FROM BOOKING WHERE MEMBER_ID = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		List<Booking> bookings = new ArrayList<Booking>();
		try {
			conn = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1,"2");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String bkId = rs.getString(1);
				String tableId = rs.getString(2);
				String bkTime = rs.getString(3);
				Date bkDate = rs.getDate(4);
				String bkChild = rs.getString(5);
				String bkAdult = rs.getString(6);
				String bkPhone = rs.getString(7);
				Booking booking = new Booking(bkId,tableId, bkTime, bkDate, bkChild, bkAdult, bkPhone);
				bookings.add(booking);
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
		return bookings;
		
	}
	
}
