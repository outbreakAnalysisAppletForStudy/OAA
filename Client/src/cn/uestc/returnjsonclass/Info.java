package cn.uestc.returnjsonclass;

public class Info {

	private String id;
	private String tel;
	private String area;
	private String pos;
	private int perStatus;
	
	public Info() {
		;
	}
	public Info(String id,String tel,String area,String pos,int perStatus) {
		this.id = id;
		this.tel = tel;
		this.area = area;
		this.pos = pos;
		this.perStatus = perStatus;
	}
	
	public void setId(String id) {
	    this.id=id;
	}
	public String getId() {
	    return this.id;
	}
	public void setTel(String tel) {
	    this.tel=tel;
	}
	public String getTel() {
	    return this.tel;
	}
	public void setArea(String area) {
	    this.area=area;
	}
	public String getArea() {
	    return this.area;
	}
	public void setPos(String pos) {
	    this.pos=pos;
	}
	public String getPos() {
	    return this.pos;
	}
	public void setPerstatus(int perStatus) {
	    this.perStatus=perStatus;
	}
	public int getPerstatus() {
	    return this.perStatus;
	}
	
}
