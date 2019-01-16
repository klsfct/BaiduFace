package major;

public class Major {

	private String id;
	private String dId;
	private String name;
	private String dname;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getdId() {
		return dId;
	}

	public void setdId(String dId) {
		this.dId = dId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public Major(String id, String dId, String name, String dname) {
		super();
		this.id = id;
		this.dId = dId;
		this.name = name;
		this.dname = dname;
	}

}
