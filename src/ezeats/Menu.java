package ezeats;


public class Menu {

	private String MENU_ID;
	private String FOOD_NAME;
	private int FOOD_PRICE;
	private int FOOD_STATUS;
	private String FOOD_CONTENT;

	public Menu(String MENU_ID, String FOOD_NAME, int FOOD_PRICE, int FOOD_STATUS, String FOOD_CONTENT) {
		this.MENU_ID = MENU_ID;
		this.FOOD_NAME = FOOD_NAME;
		this.FOOD_PRICE = FOOD_PRICE;
		this.FOOD_STATUS = FOOD_STATUS;
		this.FOOD_CONTENT = FOOD_CONTENT;
	}
	
	public Menu(String MENU_ID, int FOOD_STATUS) {
        this.MENU_ID = MENU_ID;
        this.FOOD_STATUS = FOOD_STATUS;
    }
	
	public String getMENU_ID() {
		return MENU_ID;
	}

	public void setMENU_ID(String menu_id) {
		this.MENU_ID = menu_id;
	}

	public String getFOOD_NAME() {
		return FOOD_NAME;
	}

	public void setFOOD_NAME(String food_name) {
		this.FOOD_NAME = food_name;
	}

	public int getFOOD_PRICE() {
		return FOOD_PRICE;
	}

	public void setFOOD_PRICE(int food_price) {
		this.FOOD_PRICE = food_price;
	}

	public int getFOOD_STATUS() {
		return FOOD_STATUS;
	}

	public void setFOOD_STATUS(int food_status) {
		this.FOOD_STATUS = food_status;
	}

	public String getFOOD_CONTENT() {
		return FOOD_CONTENT;
	}

	public void setFOOD_CONTENT(String food_content) {
		this.FOOD_CONTENT = food_content;
	}


}
