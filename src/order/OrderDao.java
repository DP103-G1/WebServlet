package order;

import java.util.List;

import Table_Web.Table;

public interface OrderDao {

		List<Order> getAll();
		
		Order getId(int ord_id);
		
		int add(Order order);
		
		int update(Order order);
		
		List<Order> getAllByMemberId(int memberId);
		
		int getBkid(int memberId);	
		
		int gettableid(int bkid);
		
		List<Order> getAllByOrdId(int ordId);
		
		int updateTableStatus(Table t); 
		
	}

