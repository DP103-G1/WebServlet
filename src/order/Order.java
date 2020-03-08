package order;

import java.sql.Timestamp;
import java.util.List;

import menudetail.MenuDetail;

public class Order {

	private int ORD_ID;
	private String MENU_ID;
	private String FOOD_NAME;
	private int FOOD_AMOUNT;
	private boolean FOOD_ARRIVAL;
	private int TOTAL;
	private int MEMBER_ID;
	private int BK_ID;
	private int ORD_TOTAL;
	private boolean ORD_STATUS;
	private boolean ORD_BILL;
	private Timestamp ORD_TIME; 
	private List<MenuDetail> menuDetails; 
	
	public Order(int ORD_ID, int MEMBER_ID, int BK_ID,  int ORD_TOTAL,
			boolean ORD_STATUS, boolean ORD_BILL) {
		this.ORD_ID = ORD_ID;
		this.MEMBER_ID = MEMBER_ID;
		this.BK_ID = BK_ID;
		this.ORD_TOTAL = ORD_TOTAL;
		this.ORD_STATUS = ORD_STATUS;
		this.ORD_BILL = ORD_BILL;
	}
	
	public Order(int ORD_ID, int BK_ID, int ORD_TOTAL,
			boolean ORD_STATUS, boolean ORD_BILL) {
		this.ORD_ID = ORD_ID;
		this.BK_ID = BK_ID;
		this.ORD_TOTAL = ORD_TOTAL;
		this.ORD_STATUS = ORD_STATUS;
		this.ORD_BILL = ORD_BILL;
	}

	public Order(int ORD_ID, int MEMBER_ID, int ORD_TOTAL, boolean ORD_BILL) {
		this.ORD_ID = ORD_ID;
		this.MEMBER_ID = MEMBER_ID;
		this.ORD_TOTAL = ORD_TOTAL;
		this.ORD_BILL = ORD_BILL;
	}

	public Order(int ORD_ID, int MEMBER_ID, int BK_ID, int ORD_TOTAL,
			boolean ORD_STATUS, boolean ORD_BILL, List<MenuDetail> menuDetails) {
		this.ORD_ID = ORD_ID;
		this.MEMBER_ID = MEMBER_ID;
		this.BK_ID = BK_ID;
		this.ORD_TOTAL = ORD_TOTAL;
		this.ORD_STATUS = ORD_STATUS;
		this.ORD_BILL = ORD_BILL;
		this.menuDetails = menuDetails;
	}
	

	public Order(int ORD_ID, int MEMBER_ID, int ORD_TOTAL,
			boolean ORD_STATUS, boolean ORD_BILL, Timestamp ORD_TIME) {
		this.ORD_ID = ORD_ID;
		this.MEMBER_ID = MEMBER_ID;
		this.ORD_TOTAL = ORD_TOTAL;
		this.ORD_STATUS = ORD_STATUS;
		this.ORD_BILL = ORD_BILL;
		this.ORD_TIME = ORD_TIME;
	}
	



	public Order(String menuId, String foodName, int foodAmount, boolean foodArrival, int total, boolean ordbill) {

		this.MENU_ID = menuId;
		this.FOOD_NAME = foodName;
		this.FOOD_AMOUNT = foodAmount;
		this.FOOD_ARRIVAL = foodArrival;
		this.TOTAL = total;
		this.ORD_BILL = ordbill;
	}
	
	public String getMENU_ID() {
		return MENU_ID;
	}
	
	public void setMENU_ID(String mENU_ID) {
		MENU_ID = mENU_ID;
	}

	public String getFOOD_NAME() {
		return FOOD_NAME;
	}

	public void setFOOD_NAME(String fOOD_NAME) {
		FOOD_NAME = fOOD_NAME;
	}

	public int getFOOD_AMOUNT() {
		return FOOD_AMOUNT;
	}

	public void setFOOD_AMOUNT(int fOOD_AMOUNT) {
		FOOD_AMOUNT = fOOD_AMOUNT;
	}

	public boolean isFOOD_ARRIVAL() {
		return FOOD_ARRIVAL;
	}

	public void setFOOD_ARRIVAL(boolean fOOD_ARRIVAL) {
		FOOD_ARRIVAL = fOOD_ARRIVAL;
	}

	public int getTOTAL() {
		return TOTAL;
	}

	public void setTOTAL(int tOTAL) {
		TOTAL = tOTAL;
	}

	public int getORD_ID() {
		return ORD_ID;
	}

	public void setORD_ID(int ord_id) {
		ORD_ID = ord_id;
	}

	public int getMEMBER_ID() {
		return MEMBER_ID;
	}

	public void setMEMBER_ID(int member_id) {
		MEMBER_ID = member_id;
	}

	public int getORD_TOTAL() {
		return ORD_TOTAL;
	}

	public void setORD_TOTAL(int ord_total) {
		ORD_TOTAL = ord_total;
	}

	public boolean isORD_STATUS() {
		return ORD_STATUS;
	}

	public void setORD_STATUS(boolean ord_status) {
		ORD_STATUS = ord_status;
	}

	public boolean isORD_BILL() {
		return ORD_BILL;
	}

	public void setORD_BILL(boolean ord_bill) {
		ORD_BILL = ord_bill;
	}
	
	public List<MenuDetail> getMenuDetails() {
		return menuDetails;
	}

	public void setMenuDetails(List<MenuDetail> menuDetails) {
		this.menuDetails = menuDetails;
	}

	public int getBK_ID() {
		return BK_ID;
	}

	public void setBK_ID(int bK_ID) {
		BK_ID = bK_ID;
	}
	

	public Timestamp getORD_TIME() {
		return ORD_TIME;
	}

	public void setORD_TIME(Timestamp ORD_TIME) {
		ORD_TIME = ORD_TIME;
	}


}
