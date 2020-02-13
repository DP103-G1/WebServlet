package Booking_Web;

import java.util.List;

public interface BookingDao {
	int insert(Booking booking);
	int delete(String bkId);
	Booking getbkId(String bkId);
	List<Booking> getAll();
	
	
}
