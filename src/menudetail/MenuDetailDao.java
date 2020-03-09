package menudetail;

import java.util.List;

import order.Order;

public interface MenuDetailDao {
	
	/**
	 * getAll is for kitchen
	 * 
	 * @return (1) all columns where FOOD_STATUS = 0 if type = "kitchen"
	 * (2) all columns where FOOD_ARRIVAL = 0 if type = "waiter"
	 */
	List<MenuDetail> getAll(String type);
	
	int update(MenuDetail menuDetail);
	
	List<MenuDetail> getAllByMemberId(int memberId);
	
	List<MenuDetail> getAllByBkId(int bkId);

}