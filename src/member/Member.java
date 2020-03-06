package member;

public class Member {

	private int member_Id;
	private String account;
	private String password;
	private String name;
	private String phone;
	

	public Member(int member_Id, String account, String password, String name, String phone) {
		this.member_Id = member_Id;
		this.account = account;
		this.password = password;
		this.name = name;
		this.phone = phone;
	}
	
	public Member(String account, String password) {
		this.account = account;
		this.password = password;

	}
	
	public Member(String account, String password, String name, String phone) {
		this.account = account;
		this.password = password;
		this.name = name;
		this.phone = phone;
		}
	
	public Member(int member_Id, String password, String name, String phone) {
		this.member_Id = member_Id;
		this.password = password;
		this.name = name;
		this.phone = phone;
	}

	public int getmember_Id() {
		return member_Id;
	}

	public void setmember_Id(int member_Id) {
		this.member_Id = member_Id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getpassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}
	
	public String getphone() {
		return phone;
	}

	public void setphone(String phone) {
		this.phone = phone;
	}
	
}
