package cn.uestc.dao;

import org.springframework.util.DigestUtils;

public class Admin {//管理员

	private String name;
	private String id;  //18位身份证号
	private String hashcode; //哈希码:hash(身份证+手机号+114514)的后8位
	private String tel; //11位手机号
	private String area; //辖区
	private byte level; //管理员权限等级 1-全国 2-省 3-市 4-县
	//构造函数
	public Admin() {
		;
	}
	public Admin(String name,String id,String hashcode,String tel,String area,Byte level) {
		this.name = name;
		this.id = id;
		this.hashcode = hashcode;
		this.tel = tel;
		this.area = area;
		this.level = level;
	}
	// 格式化输出
	public void output() {
		System.out.println("---姓名：" + this.name);
		System.out.println("---身份证号：" + this.id);
		System.out.println("---验证码：" + this.hashcode);
		System.out.println("---手机号：" + this.tel);
		System.out.println("---辖区：" + this.area);
		System.out.println("---等级：" + this.level);
	}
	//验证身份证是否正确
	public boolean validateId(String id) {
		String regex = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
		return id.matches(regex);
	}
	//验证手机号是否正确
	public boolean validateTel(String tel) {
		String regex = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";
		return tel.matches(regex);
	}
	//md5生成hashcode
	public String generateHashcode(String id,String tel) {
		String base = id + tel + "114514";
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5.substring(md5.length()-8, md5.length());
	}
	//getter和setter方法
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	public void setId(String id) {
	    this.id = id;
	}
	public String getId() {
	    return this.id;
	}
	public void setHashcode(String hashcode) {
	    this.hashcode = hashcode;
	}
	public String getHashcode() {
	    return this.hashcode;
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
	public void setLevel(byte level) {
		this.level = level;
	}
	public byte getLevel() {
		return this.level;
	}
}
