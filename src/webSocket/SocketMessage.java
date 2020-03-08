package webSocket;

import java.util.Optional;

import menudetail.MenuDetail;

public class SocketMessage {
	private String type;//{"menuDetail", "seat", "service"}
	private String receiver;
	private MenuDetail menuDetail;

	public SocketMessage(String type, String receiver, MenuDetail menuDetail) {
		this.type = type;
		this.receiver = receiver;
		this.menuDetail = menuDetail;
	}

	public SocketMessage(String type, String receiver) {
		this(type, receiver, null);
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Optional<MenuDetail> getMenuDetail() {
		return Optional.ofNullable(menuDetail);
	}
	
	public void setMenuDetail(MenuDetail menuDetail) {
		this.menuDetail = menuDetail;
	}
}
