package cn.uestc.dao;


public class Person { //��Ա

	private String id; //18λ���֤��
	private String tel; //11λ�ֻ���
	private String area; //Ͻ��
	private String pos; //����סַ
	private byte perStatus; //����״�� 0-���� 1-ȷ�� 2-���� 3-����
	private String date; //¼�����ݿ�ʱ��
	private String submit;//�ύ�����֤��
	
	public void output() { //��ʽ�����
		System.out.println("---���֤�ţ�"+this.id);
		System.out.println("---�ֻ��ţ�"+this.tel);
		System.out.println("---Ͻ����"+this.area);
		System.out.println("---����סַ��"+this.pos);
		switch(this.perStatus) {
			case 0:
				System.out.println("---����״����" + "0-����");
				break;
			case 1:
				System.out.println("---����״����" + "1-ȷ��");
				break;
			case 2:
				System.out.println("---����״����" + "2-����");
				break;
			case 3:
				System.out.println("---����״����" + "3-����");
				break;
		}
		System.out.println("---�Ǽ�ʱ�䣺" + this.date);
		System.out.println("�ύ�����֤�ţ�" + submit);
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
