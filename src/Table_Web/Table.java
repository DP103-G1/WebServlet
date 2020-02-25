package Table_Web;


public class Table {
	
	private int tableId;
	private String tablePeople;
	
	
	
	public Table(int tableId, String tablePeople) {
		super();
		this.tableId = tableId;
		this.tablePeople = tablePeople;
		
		
	}
	
	@Override
	public String toString() {
		String text = "Table Id" + tableId + "\nTable People" + tablePeople ;
		return text;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.tableId == tableId;
	}
	
	@Override
	public int hashCode() {
		return tableId;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public String getTablePeople() {
		return tablePeople;
	}

	public void setTablePeople(String tablePeople) {
		this.tablePeople = tablePeople;
	}

	
}
