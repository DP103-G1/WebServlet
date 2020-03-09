package Table_Web;

import java.util.List;

public interface Table_Dao {
	
	 List<Table> getAll();
	
	 Table getTableId(int tableId);
	
	 int insert(Table t);
	
	 int update(Table t);
	
	 int deleteId(int tableId);
	
	 List<Table> getAllOrdId();
	
	 int updateTableStatus(Table table);
	
	 int updateStatus(Table table);
	

		
	
}
