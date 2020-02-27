package Table_Web;


public class Table {
	
	private int tableId;
	private String tablePeople;
	private int ORD_ID;
	
	
	
	public Table(int tableId, String tablePeople) {
		super();
		this.tableId = tableId;
		this.tablePeople = tablePeople;
	}
	
	public Table(int tableId, String tablePeople, int ORD_ID) {
		super();
		this.tableId = tableId;
		this.tablePeople = tablePeople;
		this.setORD_ID(ORD_ID);
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

	public int getORD_ID() {
		return ORD_ID;
	}

	public void setORD_ID(int ord_id) {
		ORD_ID = ord_id;
	}

	
}
