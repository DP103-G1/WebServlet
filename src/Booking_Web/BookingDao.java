package Booking_Web;

import java.util.List;

import Table_Web.Table;

public interface BookingDao {
	
	int insert(Booking booking);
	
	int delete(int bkId);
	
	Booking getbkId(int bkId);
	
	List<Booking> getAll();
	
	List<Booking> getAllByMemberId(int memberId);
}
