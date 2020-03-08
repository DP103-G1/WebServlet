package member;

import static server_main.Common.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDaoMySqlImpl implements MemberDao {

	public MemberDaoMySqlImpl() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Member member) {
		int count = 0;
		int memId = 0;
		String getIdSql = "SELECT LAST_INSERT_ID();";
		String sql = "INSERT INTO `member`" + "(account, password, name, phone) "
				+ "VALUES(?, ?, ?, ?);";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement getCountPs = connection.prepareStatement(getIdSql);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			if (!getAll().stream().anyMatch(v -> v.getAccount().equals(member.getAccount()))) {
				ps.setString(1, member.getAccount());
				ps.setString(2, member.getpassword());
				ps.setString(3, member.getname());
				ps.setString(4, member.getphone());
				count = ps.executeUpdate();
				ResultSet rs = getCountPs.executeQuery();
				if (rs.next()) {
					memId = rs.getInt(1);
				}
			} else {
				count = -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count <= 0 ? count : memId;
	}

	@Override
	public int update(Member member) {
		int count = 0;
		String sql = "UPDATE `member` SET password = ?, name = ?, "
				+ "phone = ?, state = ? WHERE member_id = ?;";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, member.getpassword());
			ps.setString(2, member.getname());
			ps.setString(3, member.getphone());
			ps.setInt(4, member.getState());
			ps.setInt(5, member.getmember_Id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Member findByMemberId(int member_Id) {
		String sql = "SELECT account, password, name, phone, state FROM `member` WHERE member_Id = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		Member memberData = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, member_Id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String account = rs.getString(1);
				String password = rs.getString(2);
				String name = rs.getString(3);
				String phone = rs.getString(4);
				int state = rs.getInt(5);
				memberData = new Member(member_Id, account, password, name, phone, state);
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
		return memberData;
	}

	@Override
	public List<Member> getAll() {
		String sql = "SELECT member_Id, account, password, name, phone, state FROM `member`;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Member> memberList = new ArrayList<Member>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int member_Id = rs.getInt(1);
				String email = rs.getString(2);
				String password = rs.getString(3);
				String name = rs.getString(4);
				String phone = rs.getString(5);
				int state = rs.getInt(6);
				Member member = new Member(member_Id, email, password, name, phone, state);
				memberList.add(member);
			}
			return memberList;
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
		return memberList;
	}

	@Override
	public String login(String account, String password) {
		String memId = null;
		String sql = "SELECT MEMBER_ID, PASSWORD FROM `member` WHERE ACCOUNT = ?;";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, account);
			ResultSet rs = ps.executeQuery();
			if (rs.next() && rs.getString(2).equals(password)) {
				memId = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memId;
	}

	@Override
	public boolean forget(String account, String phone) {
		boolean passwordForget = false;
		String sql = "SELECT ACCOUNT,PHONE from EZeats.MEMBER WHERE ACCOUNT = ? and phone = ?;";
		try (Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, account);
			ps.setString(2, phone);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next() && rs.getString(1).equals(account) && rs.getString(2).equals(phone)) {
				passwordForget = true;
			}else {
				passwordForget = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return passwordForget;
	}

	@Override
	public int updatePassword(Member account) {
		int count = 0;
		String sql = "update EZeats.MEMBER set password = ? where account = ?;";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1,account.getpassword());
			ps.setString(2, account.getAccount());
			count = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int updateState(Member member) {
		int count = 0;
		String sql = "update EZeats.MEMBER set state = ? where member_id = ?;";
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, member.getState());
			ps.setInt(2, member.getmember_Id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return count;
	}

	}
	
	

