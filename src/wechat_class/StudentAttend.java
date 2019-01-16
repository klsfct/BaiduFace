package wechat_class;

public class StudentAttend {
	private String name;
	private String flag1;
	private String flag2;
	private int timeFlag;
	private String classid;
	private String checkTime;

	public StudentAttend(String name, String flag1, String flag2, int timeFlag) {
		super();
		this.name = name;
		this.flag1 = flag1;
		this.flag2 = flag2;
		this.checkTime = null;
		this.classid = null;
		this.timeFlag = timeFlag;
	}

	public StudentAttend(String name, String flag1, String flag2, int timeFlag, String checkTime, String classid) {
		super();
		this.name = name;
		this.flag1 = flag1;
		this.flag2 = flag2;
		this.timeFlag = timeFlag;
		this.checkTime = checkTime;
		this.classid = classid;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(int timeFlag) {
		this.timeFlag = timeFlag;
	}

}
