package Booking_Web;

import java.util.Date;
import java.util.List;

import Table_Web.Table;
import order.Order;

public interface BookingDao {
	
	int insert(Booking booking);
	
	Booking getbkId(int bkId);
	
	List<Booking> getAll();
	
	List<Booking> getAllByMemberId(int memberId);
	
	int update(Booking booking);
	
	int deleteByStatus(int bkId );
	
}
