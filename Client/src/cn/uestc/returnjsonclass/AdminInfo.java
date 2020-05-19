package cn.uestc.returnjsonclass;

import java.util.*;

public class AdminInfo {

	private int level;
	private List<String> area;
	
	public void setLevel(int level) {
		this.level = level;
	}
	public int getLevel() {
		return this.level;
	}
	public void setArea(List<String> area) {
		this.area = area;
	}
	public List<String> getArea(){
		return this.area;
	}
	
}
