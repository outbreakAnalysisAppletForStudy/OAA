package cn.uestc.test;

import cn.uestc.utils.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class SessionTest {

	public static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //sessions���ʱ���ʽ
	
	public static void main(String []args) {
		
		SessionGenerator se = new SessionGenerator();
		System.out.println(se.gene(df.format(new Date())));
		
	}
	
}
