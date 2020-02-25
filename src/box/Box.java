package box;

public class Box {
	private int id;
	private int member;
	private String topic;
    private String purpose;
    private String info;
    private String date;
    private Float satisfied;
    private String feed_back;
    private String reply;
    
	public Box(int id, int member, String topic, String purpose, String info, String date, Float satisfied, String feed_back, String reply) {
		super();
		this.id = id;
		this.member = member;
		this.topic = topic;
		this.purpose = purpose;
		this.info = info;
		this.date = date;
		this.satisfied = satisfied;
		this.feed_back = feed_back;
		this.reply = reply;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMember() {
		return member;
	}

	public void setMember(int member) {
		this.member = member;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Float getSatisfied() {
		return satisfied;
	}

	public void setSatisfied(Float satisfied) {
		this.satisfied = satisfied;
	}

	public String getFeed_back() {
		return feed_back;
	}

	public void setFeed_back(String feed_back) {
		this.feed_back = feed_back;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
	
}
	
