package cn.uestc.dao;

import org.springframework.util.DigestUtils;

public class Admin {//¹ÜÀíÔ±

	private String name;
	private String id;  //18Î»Éí·İÖ¤ºÅ
	private String hashcode; //¹şÏ£Âë:hash(Éí·İÖ¤+ÊÖ»úºÅ+114514)µÄºó8Î»
	private String tel; //11Î»ÊÖ»úºÅ
	private String area; //Ï½Çø
	private byte level; //¹ÜÀíÔ±È¨ÏŞµÈ¼¶ 1-È«¹ú 2-Ê¡ 3-ÊĞ 4-ÏØ
	//¹¹Ôìº¯Êı
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
	// ¸ñÊ½»¯Êä³ö
	public void output() {
		System.out.println("---ĞÕÃû£º" + this.name);
		System.out.println("---Éí·İÖ¤ºÅ£º" + this.id);
		System.out.println("---ÑéÖ¤Âë£º" + this.hashcode);
		System.out.println("---ÊÖ»úºÅ£º" + this.tel);
		System.out.println("---Ï½Çø£º" + this.area);
		System.out.println("---µÈ¼¶£º" + this.level);
	}
	//ÑéÖ¤Éí·İÖ¤ÊÇ·ñÕıÈ·
	public boolean validateId(String id) {
		String regex = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
		return id.matches(regex);
	}
	//ÑéÖ¤ÊÖ»úºÅÊÇ·ñÕıÈ·
	public boolean validateTel(String tel) {
<<<<<<< HEAD
		String regex = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
=======
		String regex = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";
>>>>>>> a917d8e... cbhç¬¬ä¸€æ¬¡æäº¤
		return tel.matches(regex);
	}
	//md5Éú³Éhashcode
	public String generateHashcode(String id,String tel) {
		String base = id + tel + "114514";
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5.substring(md5.length()-8, md5.length());
	}
	//getterºÍsetter·½·¨
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
