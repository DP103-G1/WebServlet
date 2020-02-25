package ezeats;

import java.util.List;

public interface MenuDao {

	List<Menu> getAll();
	
	List<Menu> getAllShow();

	Menu getId(String menu_id);

	int add(Menu menu, byte[] image);

	int update(Menu menu, byte[] image);

	byte[] getImage(String menu_id);
	
	List<byte[]> getImageList();
	
	 

	
}
