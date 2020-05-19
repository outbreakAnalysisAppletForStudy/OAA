package cn.uestc.dao;

import org.springframework.util.DigestUtils;

public class Admin {//����Ա

	private String name;
	private String id;  //18λ���֤��
	private String hashcode; //��ϣ��:hash(���֤+�ֻ���+114514)�ĺ�8λ
	private String tel; //11λ�ֻ���
	private String area; //Ͻ��
	private byte level; //����ԱȨ�޵ȼ� 1-ȫ�� 2-ʡ 3-�� 4-��
	//���캯��
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
	// ��ʽ�����
	public void output() {
		System.out.println("---������" + this.name);
		System.out.println("---���֤�ţ�" + this.id);
		System.out.println("---��֤�룺" + this.hashcode);
		System.out.println("---�ֻ��ţ�" + this.tel);
		System.out.println("---Ͻ����" + this.area);
		System.out.println("---�ȼ���" + this.level);
	}
	//��֤���֤�Ƿ���ȷ
	public boolean validateId(String id) {
		String regex = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
		return id.matches(regex);
	}
	//��֤�ֻ����Ƿ���ȷ
	public boolean validateTel(String tel) {
<<<<<<< HEAD
		String regex = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
=======
		String regex = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";
>>>>>>> a917d8e... cbh第一次提交
		return tel.matches(regex);
	}
	//md5����hashcode
	public String generateHashcode(String id,String tel) {
		String base = id + tel + "114514";
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5.substring(md5.length()-8, md5.length());
	}
	//getter��setter����
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
