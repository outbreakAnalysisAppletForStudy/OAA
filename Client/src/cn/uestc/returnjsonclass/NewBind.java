package cn.uestc.returnjsonclass;

public class NewBind {

	private int code;
	private String message;
	private AdminInfo adminInfo;
	
	public void setCode(int code) {
	    this.code=code;
	}
	public int getCode() {
	    return this.code;
	}
	public void setMessage(String message) {
	    this.message=message;
	}
	public String getMessage() {
	    return this.message;
	}
	public void setAdminInfo(AdminInfo adminInfo) {
		this.adminInfo = adminInfo;
	}
	public AdminInfo getAdminInfo() {
		return this.adminInfo;
	}
	
}
