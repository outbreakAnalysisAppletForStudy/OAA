package cn.uestc.dao;

public class PerAmount {

	private int doubt; //��������
	private int diagnosis; //ȷ������
	private int heal; //��������
	private int dead; //��������
	
	public PerAmount() {
		;
	}
	public PerAmount(int doubt,int diagnosis,int heal,int dead) {
		this.doubt = doubt;
		this.diagnosis = diagnosis;
		this.heal = heal;
		this.dead = dead;
	}
	public void setDoubt(int doubt) {
	    this.doubt = doubt;
	}
	public int getDoubt() {
	    return this.doubt;
	}
	public void setDiagnosis(int diagnosis) {
	    this.diagnosis = diagnosis;
	}
	public int getDiagnosis() {
	    return this.diagnosis;
	}
	public void setHeal(int heal) {
	    this.heal = heal;
	}
	public int getHeal() {
	    return this.heal;
	}
	public void setDead(int dead) {
	    this.dead = dead;
	}
	public int getDead() {
	    return this.dead;
	}
}
