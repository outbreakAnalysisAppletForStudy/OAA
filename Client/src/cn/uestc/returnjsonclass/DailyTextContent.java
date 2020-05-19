package cn.uestc.returnjsonclass;

public class DailyTextContent {

	private int confirmedCount;
	private int suspectedCount;
	private int deadCount;
	private int curedCount;
	
	public void setConfirmedcount(int confirmedCount) {
	    this.confirmedCount=confirmedCount;
	}
	public int getConfirmedcount() {
	    return this.confirmedCount;
	}
	public void setSuspectedcount(int suspectedCount) {
	    this.suspectedCount=suspectedCount;
	}
	public int getSuspectedcount() {
	    return this.suspectedCount;
	}
	public void setDeadcount(int deadCount) {
	    this.deadCount=deadCount;
	}
	public int getDeadcount() {
	    return this.deadCount;
	}
	public void setCuredcount(int curedCount) {
	    this.curedCount=curedCount;
	}
	public int getCuredcount() {
	    return this.curedCount;
	}
	
}
