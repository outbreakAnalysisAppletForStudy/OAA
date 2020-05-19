package cn.uestc.dao;


public class Person { //人员

	private String id; //18位身份证号
	private String tel; //11位手机号
	private String area; //辖区
	private String pos; //具体住址
	private byte perStatus; //健康状况 0-疑似 1-确诊 2-治愈 3-死亡
	private String date; //录入数据库时间
	private String submit;//提交人身份证号
	
	public void output() { //格式化输出
		System.out.println("---身份证号："+this.id);
		System.out.println("---手机号："+this.tel);
		System.out.println("---辖区："+this.area);
		System.out.println("---具体住址："+this.pos);
		switch(this.perStatus) {
			case 0:
				System.out.println("---健康状况：" + "0-疑似");
				break;
			case 1:
				System.out.println("---健康状况：" + "1-确诊");
				break;
			case 2:
				System.out.println("---健康状况：" + "2-治愈");
				break;
			case 3:
				System.out.println("---健康状况：" + "3-死亡");
				break;
		}
		System.out.println("---登记时间：" + this.date);
		System.out.println("提交人身份证号：" + submit);
	}
	
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	public String getSubmit() {
		return this.submit;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
	public String getId() {
	    return this.id;
	}
	public void setTel(String tel) {
	    this.tel = tel;
	}
	public String getTel() {
	    return this.tel;
	}
	public void setArea(String area) {
	    this.area = area;
	}
	public String getArea() {
	    return this.area;
	}
	public void setPos(String pos) {
	    this.pos = pos;
	}
	public String getPos() {
	    return this.pos;
	}
	public void setPerStatus(byte perStatus) {
		this.perStatus = perStatus;
	}
	public byte getPerStatus() {
		return this.perStatus;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDate() {
		return this.date;
	}
}
