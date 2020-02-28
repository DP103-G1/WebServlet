package menudetail;

import java.util.List;

import order.Order;

public interface MenuDetailDao {
	
	List<MenuDetail> getAll();
	
	int update(MenuDetail menuDetail);
	
	List<MenuDetail> getAllByMemberId(int memberId);
	
<<<<<<< HEAD
	
=======
	List<MenuDetail> getAllByTableId(int tableId);
>>>>>>> c1b3a850f422fe978fadac82170b3d8e5638b3e1

}
