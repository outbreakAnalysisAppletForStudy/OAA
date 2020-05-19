package cn.uestc.returnjsonclass;

public class GetAreaInfo {

	private int code;
	private String area;
	private int level;
	private Detail detail;
	
	public void setCode(int code) {
	    this.code=code;
	}
	public int getCode() {
	    return this.code;
	}
	public void setArea(String area) {
	    this.area=area;
	}
	public String getArea() {
	    return this.area;
	}
	public void setLevel(int level) {
	    this.level=level;
	}
	public int getLevel() {
	    return this.level;
	}
	public void setDetail(Detail detail) {
	    this.detail=detail;
	}
	public Detail getDetail() {
	    return this.detail;
	}
	
}
