package wechat_class;

public class AttendMessage {
	private String name;
	private String studentid;
	private String classid;
	private String time;
	private String flag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getTime() {
		return time;
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public AttendMessage(String name, String studentid, String classid, String time, String flag) {
		super();
		this.name = name;
		this.studentid = studentid;
		this.classid = classid;
		this.time = time;
		this.flag = flag;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
