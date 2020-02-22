package menudetail;

import java.util.List;

public interface MenuDetailDao {
	
	List<MenuDetail> getAll();
	
	int update(MenuDetail menuDetail);
	
	List<MenuDetail> getAllByMemberId(int memberId);

}
