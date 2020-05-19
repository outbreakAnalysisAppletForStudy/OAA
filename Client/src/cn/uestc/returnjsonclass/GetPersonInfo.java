package cn.uestc.returnjsonclass;

public class GetPersonInfo {

	private int code;
	private String message;
	private int isGet;
	private Info info;
	
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
	public void setIsGet(int isGet) {
	    this.isGet=isGet;
	}
	public int getIsGet() {
	    return this.isGet;
	}
	public void setInfo(Info info) {
	    this.info=info;
	}
	public Info getInfo() {
	    return this.info;
	}
	
}
