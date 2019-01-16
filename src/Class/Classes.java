package Class;

public class Classes {

	private String classID;
	private String className;
	private String major;
	private String department;

	public String getClassID() {
		return classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Classes(String classID, String className, String major, String department) {
		super();
		this.classID = classID;
		this.className = className;
		this.major = major;
		this.department = department;
	}

}
