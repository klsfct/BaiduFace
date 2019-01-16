package wechat_class;

public class LoginMessage {

	private String name;
	private String classname;
	private String department;
	private String classid;
	private boolean flag;
	private String error;
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getError() {
		return error;
	}

	public LoginMessage(String name, String classname, String department, String classid, boolean flag, String error, String phone) {
		super();
		this.name = name;
		this.classname = classname;
		this.department = department;
		this.classid = classid;
		this.flag = flag;
		this.error = error;
		this.phone = phone;
	}

	public void setError(String error) {
		this.error = error;
	}

}
