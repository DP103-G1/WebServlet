package Table_Web;

import java.util.List;

public interface Table_Dao {
	
	public List<Table> getAll();
	public Table getTableId(String tableId);
	public int insert(Table t);
	public int update(Table t);
	public int deleteId(String tableId);
	

}
