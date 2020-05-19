package cn.uestc.returnjsonclass;

import java.util.List;

public class GetNationInfo {

	private int code;
	private List<CityInfo> info;
	private DailyTextContent dailyTextContent;
	private IncrData incrData;
	
	public void setCode(int code) {
	    this.code=code;
	}
	public int getCode() {
	    return this.code;
	}
	public void setInfo(List<CityInfo> info) {
	    this.info=info;
	}
	public List<CityInfo> getInfo() {
	    return this.info;
	}
	public void setDailytextcontent(DailyTextContent dailyTextContent) {
	    this.dailyTextContent=dailyTextContent;
	}
	public DailyTextContent getDailytextcontent() {
	    return this.dailyTextContent;
	}
	public void setIncrdata(IncrData incrData) {
	    this.incrData=incrData;
	}
	public IncrData getIncrdata() {
	    return this.incrData;
	}
	
}
