package order;

import java.util.Date;
import java.util.List;
import Table_Web.Table;
import member.Member;

public interface OrderDao {

	List<Order> getAll();
	
	Order getId(int ord_id);
	
	int add(Order order);
	
	int update(Order order);
	
	List<Order> getAllByMemberId(int memberId);
	
	int getBkid(int memberId);	

	List<Order> search(Date date, String type);
	
	int gettableid(int bkid);
	
	List<Order> getAllByOrdId(int ordId);
	
	int updateTableStatus(Table table); 
	
	int updateBillStatis(Table table);
	
	int updateState(Member member);
}