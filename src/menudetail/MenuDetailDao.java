package menudetail;

import java.util.List;

import order.Order;

public interface MenuDetailDao {
	
	List<MenuDetail> getAll();
	
	int update(MenuDetail menuDetail);
	
	List<MenuDetail> getAllByMemberId(int memberId);
	
	List<MenuDetail> getAllByTableId(int tableId);

}
