package order;

import java.util.List;

public interface OrderDao {

		List<Order> getAll();
		
		Order getId(int ord_id);
		
		int add(Order order);
		
		int update(Order order);
				
}
