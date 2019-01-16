package wechat_class;

public class Course {
	private String courseid;
	private String coursename;
	private String startTime;
	private String endTime;
	private int week;

	public Course(String courseid, String coursename, String startTime, String endTime, int week) {
		super();
		this.courseid = courseid;
		this.coursename = coursename;
		this.startTime = startTime;
		this.endTime = endTime;
		this.week = week;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
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

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

}
