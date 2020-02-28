package order;

import java.util.List;

public interface OrderDao {

		List<Order> getAll();
		
		Order getId(int ord_id);
		
		int add(Order order);
		
		int update(Order order);
		
		List<Order> getAllByMemberId(int memberId);
		
<<<<<<< HEAD
		int getBkid(int memberId);	
		
		List<Order> getAllByOrdId(int ordId);
		
	}
=======
		int getBkid(int memberId);		
		
		
}
>>>>>>> c1b3a850f422fe978fadac82170b3d8e5638b3e1
