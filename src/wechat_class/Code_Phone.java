package wechat_class;

public class Code_Phone {
	private String phone;
	private String code;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Code_Phone(String phone, String code) {
		super();
		this.phone = phone;
		this.code = code;
	}
}
