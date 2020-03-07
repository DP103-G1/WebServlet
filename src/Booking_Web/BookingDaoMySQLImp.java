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

import member.Member;

import static server_main.Common.CLASS_NAME;
import static server_main.Common.USER;
import static server_main.Common.URL;
import static server_main.Common.PASSWORD;

public class BookingDaoMySQLImp implements BookingDao {

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
		"INSERT INTO BOOKING (MEMBER_ID,TABLE_ID,BK_TIME,BK_DATE,BK_CHILD,BK_ADULT,PHONE,STATUS) VALUES(?,?, ?, ?, ?, ?, ?,?);";
		Connection connection =null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, booking.getMember().getmember_Id());
			ps.setInt(2, booking.getTableId());
			ps.setString(3, booking.getBkTime());
			ps.setTimestamp(4, new Timestamp(booking.getBkDate().getTime()));
			ps.setString(5, booking.getBkChild());
			ps.setString(6, booking.getBkAdult());
			ps.setString(7, booking.getBkPhone());
			ps.setInt(8, booking.getStatus());
			
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
	public Booking getbkId(int bkId) {
		String sql = "SELECT `BOOKING`.MEMBER_ID,TABLE_ID, BK_TIME, BK_DATE, BK_CHILD, BK_ADULT, `BOOKING`.PHONE, "
				+ "account, password, name, `member`.phone, state FROM `BOOKING` "
				+ "JOIN `MEMBER` ON `MEMBER`.MEMBER_ID = `BOOKING`.MEMBER_ID WHERE BK_ID = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		Booking booking = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bkId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int memberId = rs.getInt(1);
				int tableId = rs.getInt(2);
				String bkTime = rs.getString(3);
				Date bkDate = rs.getDate(4);
				String bkChild = rs.getString(5);
				String bkAdult = rs.getString(6);
				String bkPhone = rs.getString(7);
				
				String account = rs.getString(8);
				String password = rs.getString(9);
				String name = rs.getString(10);
				String phone = rs.getString(11);
				int state = rs.getInt(12);
				booking = new Booking(new Member(memberId, account, password, name, phone, state), 
						tableId, bkTime, bkDate, bkChild, bkAdult, bkPhone);
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
		return booking;
	}

	@Override
	public List<Booking> getAll() {
		String sql = "SELECT BK_ID, `BOOKING`.MEMBER_ID, TABLE_ID, BK_TIME, BK_DATE, BK_CHILD, BK_ADULT, `BOOKING`.PHONE, "
				+ "account, password, name, `member`.phone, state FROM `BOOKING` "
				+ "JOIN `MEMBER` ON `MEMBER`.MEMBER_ID = `BOOKING`.MEMBER_ID;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Booking> bookingList = new ArrayList<Booking>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int bkId = rs.getInt(1);
				int memberId = rs.getInt(2);
				int tableId = rs.getInt(3);
				String bkTime = rs.getString(4);
				Date bkDate = rs.getDate(5);
				String bkChild = rs.getString(6);
				String bkAdult = rs.getString(7);
				String bkPhone = rs.getString(8);
				
				String account = rs.getString(9);
				String password = rs.getString(10);
				String name = rs.getString(11);
				String phone = rs.getString(12);
				int state = rs.getInt(13);
				Booking booking = new Booking(new Member(memberId, account, password, name, phone, state), 
						tableId, bkTime, bkDate, bkChild, bkAdult, bkPhone, bkId);
				bookingList.add(booking);
			}

			return bookingList;
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
		return bookingList;
	}

	@Override
	public List<Booking> getAllByMemberId(int memberId) {
		String sql = "SELECT BK_ID,TABLE_ID, BK_TIME, BK_DATE, BK_CHILD, BK_ADULT, `BOOKING`.PHONE, "
				+ "account, password, name, `member`.phone, state FROM `BOOKING` "
				+ "JOIN `MEMBER` ON `MEMBER`.MEMBER_ID = `BOOKING`.MEMBER_ID "
				+ "WHERE `BOOKING`.MEMBER_ID = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		List<Booking> bookings = new ArrayList<Booking>();
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, memberId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int bkId = rs.getInt(1);
				int tableId = rs.getInt(2);
				String bkTime = rs.getString(3);
				Date bkDate = rs.getDate(4);
				String bkChild = rs.getString(5);
				String bkAdult = rs.getString(6);
				String bkPhone = rs.getString(7);
				
				String account = rs.getString(8);
				String password = rs.getString(9);
				String name = rs.getString(10);
				String phone = rs.getString(11);
				int state = rs.getInt(12);
				Booking booking = new Booking(new Member(memberId, account, password, name, phone, state), 
						tableId, bkTime, bkDate, bkChild, bkAdult, bkPhone,bkId);
				bookings.add(booking);
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
		return bookings;
	}

	@Override
	public int update(int bk_id, int member_id) {
		int count = 0;
		String sql = "UPDATE BOOKING SET STATUS = 0 WHERE BK_ID = ? AND MEMBER_ID = ?; ";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, bk_id);
			ps.setInt(2, member_id);
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

}

