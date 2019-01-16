package check;

public class AttendenceMessage {

	private String name;
	private String sid;
	private String classname;
	private String coursename;
	private String time1;  //Ç©µ½Ê±¼ä
	private String time2;  //Ç©ÍËÊ±¼ä
	private String flag1;  //Ç©µ½×´Ì¬
	private String flag2;  //Ç©ÍË×´Ì¬
	public AttendenceMessage(String name, String sid, String classname, String coursename, String time1, String time2,
			String flag1, String flag2) {
		super();
		this.name = name;
		this.sid = sid;
		this.classname = classname;
		this.coursename = coursename;
		this.time1 = time1;
		this.time2 = time2;
		this.flag1 = flag1;
		this.flag2 = flag2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public String getTime1() {
		return time1;
	}
	public void setTime1(String time1) {
		this.time1 = time1;
	}
	public String getTime2() {
		return time2;
	}
	public void setTime2(String time2) {
		this.time2 = time2;
	}
	public String getFlag1() {
		return flag1;
	}
	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}
	public String getFlag2() {
		return flag2;
	}
	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}
	

}
