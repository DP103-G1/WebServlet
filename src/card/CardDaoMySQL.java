package card;


import static server_main.Common.CLASS_NAME;
import static server_main.Common.URL;
import static server_main.Common.USER;
import static server_main.Common.PASSWORD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class CardDaoMySQL implements CardDao {
	
	public CardDaoMySQL() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int add(Card card) {
		int count = 0;
		String sql = "INSERT INTO CARD(MEMBER_ID, MEMBER_NAME, CARD_NUMBER, CARD_DATE, CARD_LAST) " 
				+ "VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, card.getMember_id());
			ps.setString(2, card.getMember_name());
			ps.setString(3, card.getCard_number());
			ps.setString(4, card.getCard_date());
			ps.setInt(5, card.getCard_last());
			System.out.println(card);
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
	public List<Card> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
