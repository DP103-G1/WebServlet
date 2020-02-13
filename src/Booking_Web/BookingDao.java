package Booking_Web;

import java.util.List;

import Table_Web.Table;

public interface BookingDao {
	
	int insert(Booking booking);
	
	int delete(String bkId);
	
	Booking getbkId(String bkId);
	
	List<Booking> getAll();
	
	List<Booking> getAllByMemberId(String memberId);
}
