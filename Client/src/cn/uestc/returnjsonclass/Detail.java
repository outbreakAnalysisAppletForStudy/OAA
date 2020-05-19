package cn.uestc.returnjsonclass;

public class Detail {

	private String name;
	private int total;
	private int recent;
	private int exist;
	private int dead;
	private int recover;
	private int suspect;

	public void setName(String name) {
	    this.name=name;
	}
	public String getName() {
	    return this.name;
	}
	public void setTotal(int total) {
	    this.total=total;
	}
	public int getTotal() {
	    return this.total;
	}
	public void setRecent(int recent) {
	    this.recent=recent;
	}
	public int getRecent() {
	    return this.recent;
	}
	public void setExist(int exist) {
	    this.exist=exist;
	}
	public int getExist() {
	    return this.exist;
	}
	public void setDead(int dead) {
	    this.dead=dead;
	}
	public int getDead() {
	    return this.dead;
	}
	public void setRecover(int recover) {
	    this.recover=recover;
	}
	public int getRecover() {
	    return this.recover;
	}
	public void setSuspect(int suspect) {
	    this.suspect=suspect;
	}
	public int getSuspect() {
	    return this.suspect;
	}
	
}
