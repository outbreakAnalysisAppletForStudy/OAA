package cn.uestc.returnjsonclass;

public class Login {

	private int code;
	private String session;
	private String message;
	private AdminInfo adminInfo;
	
	public void setCode(int code) {
	    this.code=code;
	}
	public int getCode() {
	    return this.code;
	}
	public void setSession(String session) {
	    this.session=session;
	}
	public String getSession() {
	    return this.session;
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
