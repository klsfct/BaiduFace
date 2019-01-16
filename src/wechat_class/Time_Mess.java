package wechat_class;

public class Time_Mess {
	private String nowDay;
	private String startTime;
	private String endTime;
	private String mess;

	public Time_Mess(String nowDay, String startTime, String endTime, String mess) {
		super();
		this.nowDay = nowDay;
		this.startTime = startTime;
		this.endTime = endTime;
		this.mess = mess;
	}

	public String getNowDay() {
		return nowDay;
	}

	public void setNowDay(String nowDay) {
		this.nowDay = nowDay;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}

}
