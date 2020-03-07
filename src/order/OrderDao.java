package order;

import java.util.Date;
import java.util.List;

import Booking_Web.Booking;

public interface OrderDao {

		List<Order> getAll();
		
		Order getId(int ord_id);
		
		int add(Order order);
		
		int update(Order order);
		
		List<Order> getAllByMemberId(int memberId);
		
		int getBkid(int memberId);	
		
		List<Order> search(Date date, String type);
}
