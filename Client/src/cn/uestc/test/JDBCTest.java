package cn.uestc.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import cn.uestc.dao.*;

public class JDBCTest {

	public static int num = 0;
	
	public void try1() {
		num ++;
	}
	
	public static void main(String []args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		String id = "450981200003043936";
		String tel = "15919241377";
		
		System.out.println(num);
		System.out.println(ajt.login(id, tel));
		System.out.println("hello world");
		
	}
	
}
