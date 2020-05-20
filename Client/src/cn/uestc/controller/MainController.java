package cn.uestc.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.Writer;
import com.google.gson.Gson;
import cn.uestc.returnjsonclass.*;
import cn.uestc.dao.Admin;
import cn.uestc.dao.AdminJDBCTemplate;
import cn.uestc.dao.Person;
import cn.uestc.utils.*;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
@Controller
public class MainController {

	public static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //sessions表的时间格式
	public static final String TOKEN = "DEEPDARKFANTASY"; //token
	
	@RequestMapping(value = "/login")
	public void loginController(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		//操作数据库
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//响应设置
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//响应json部分
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		Login jsonObject = new Login();
		//判断过程
		Admin admin = new Admin();
		String id = request.getParameter("id");
		String tel = request.getParameter("tel");
		//token
		String token = request.getParameter("token");
		if(token.equals(TOKEN) == false) { //当成参数错误来处理
			jsonObject.setCode(2);
			jsonObject.setMessage("Parameter error");
		}
		else if(admin.validateId(id) == false || admin.validateTel(tel) == false) {//参数错误
			jsonObject.setCode(2);
			jsonObject.setMessage("Parameter error");
		}
		else {
			int res = ajt.login(id, tel);
			if(res == 0) {//正常登录
				//获取当前时间
				String time = df.format(new Date());// new Date()为获取当前系统时间
				//判是否存在session
				String session = ajt.loginFindSession(id, tel, time);
				if(session == null) { //session为空就新建一个
					//session  
					SessionGenerator sessiongenerator = new SessionGenerator();
					String newsession = sessiongenerator.gene(time);
					//保存session
					session = newsession;
					ajt.loginSession(id,tel,time,session);
				}
				AdminInfo adminInfo = new AdminInfo();
				adminInfo = ajt.returnAdminInfo(id, tel);
				//json
				jsonObject.setCode(res);
				jsonObject.setSession(session);
				jsonObject.setMessage("success");
				jsonObject.setAdminInfo(adminInfo);
			}
			else if(res == 1) {//验证失败
				jsonObject.setCode(res);
				jsonObject.setMessage("Validation failed");
			}
			else {//转到绑定界面
				//获取当前时间
				String time = df.format(new Date());// new Date()为获取当前系统时间
				String session = ajt.firstLoginFindSession(id, tel, time);
				if(session == null) { //session为空新建一个
					//session  
					SessionGenerator sessiongenerator = new SessionGenerator();
					String newsession = sessiongenerator.gene(time);
					session = newsession;
					//保存session
					ajt.firstLoginSession(id,tel,time,session);
				}
				//json
				jsonObject.setCode(res);
				jsonObject.setMessage("Go to binding");
				jsonObject.setSession(session);
			}
		}
		//转为json并且发送到前端
		String json = gson.toJson(jsonObject);
		out.write(json);
		out.flush();
	}
	
	@RequestMapping(value = "/newBind")
	public void newBindController(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		//操作数据库
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//响应设置
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//响应json部分
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		NewBind jsonObject = new NewBind();
		//判断过程
		String session = request.getParameter("sessionKey");
		String name = request.getParameter("name");
		String hashcode = request.getParameter("hashcode");
		//找有没有对应的session
		String judge = ajt.newBindSession(session);
		System.out.println("newBind session:"+request.getParameter("sessionKey"));
		if(judge == null) {  //session错误
			jsonObject.setCode(404);
			jsonObject.setMessage("session error");
		}
		else {//有对应的session
			String id = judge.split("-")[0];
			String tel = judge.split("-")[1];
			//验证hashcode是否正确
			int level = ajt.bind(name, hashcode, id, tel);
			if(level == -1) { //验证失败
				jsonObject.setCode(1);
				jsonObject.setMessage("Validation failed");
			}
			else {  //绑定成功
				jsonObject.setCode(0);
				jsonObject.setMessage("success");
				AdminInfo adminInfo = new AdminInfo();
				adminInfo = ajt.returnAdminInfo(id, tel);
				jsonObject.setAdminInfo(adminInfo);
				//补全sessions表和管理员表
				String time = df.format(new Date());
				ajt.newBindCompleteSession(id, tel, session, name, level, time);
			}
		}
		//转成json并发送到前端
		String json = gson.toJson(jsonObject);
		out.write(json);
		out.flush();
	}
	
	@RequestMapping(value = "/release")
	public void releaseController(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		//操作数据库
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//响应设置
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//响应json部分
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		Release jsonObject = new Release();
		//解析请求
		String token = request.getParameter("token");
		String session = request.getParameter("sessionKey");
		if(token.equals(TOKEN) == false) {
			jsonObject.setCode(1);
			jsonObject.setMessage("token error");
			String json = gson.toJson(jsonObject);
			out.write(json);
			out.flush();
			return ;
		}
		boolean judge = ajt.releaseSession(session);
		if(judge == false) {
			jsonObject.setCode(2);
			jsonObject.setMessage("session not found");
		}
		else {
			jsonObject.setCode(0);
			jsonObject.setMessage("success");
		}
		//
		String json = gson.toJson(jsonObject);
		out.write(json);
		out.flush();
	}
	
	@RequestMapping(value = "/report")
	public void reportController(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		//操作数据库
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//响应设置
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//响应json部分
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		Report jsonObject = new Report();
		//解析请求
		String id = request.getParameter("id");
		String tel = request.getParameter("tel");
		//这里area是["成都市","郫县"]这样格式的
		String area =new String(request.getParameter("area").getBytes("ISO-8859-1"),"UTF-8");
		System.out.println("area:"+area);
		String pos = request.getParameter("pos");
		int perStatus = Integer.parseInt(request.getParameter("perStatus"));
		String session = request.getParameter("sessionKey");
		//判断id，tel，perStatus是否正确
		Admin admin = new Admin();
		if(admin.validateId(id) == false) {//身份证号错误
			jsonObject.setCode(2);
			jsonObject.setMessage("id error");
		}
		else if(admin.validateTel(tel) == false) {//电话号错误
			jsonObject.setCode(2);
			jsonObject.setMessage("tel error");
		}
		else if(perStatus != 0 || perStatus != 1 || perStatus !=2 || perStatus != 3) {//健康状况错误
			jsonObject.setCode(2);
			jsonObject.setMessage("perStatus error");
		}
		String time = df.format(new Date());
		admin = ajt.returnSession(session, time);
		if(admin.getId() == null) { //session错误
			jsonObject.setCode(404);
			jsonObject.setMessage("session error");
		}
		else { 
			Person person = new Person();
			person.setId(id);
			person.setArea(area);
			person.setTel(tel);
			person.setPerStatus((byte)perStatus);
			person.setPos(pos);
			person.setSubmit(admin.getId());
			person.setDate(time.split(" ")[0]);
			ajt.reportPerson(person);
			jsonObject.setCode(0);
			jsonObject.setMessage("success");
		}
		//
		String json =gson.toJson(jsonObject);
		out.write(json);
		out.flush();
	}
	
	@RequestMapping(value = "/getPersonInfo")
	public void getPersonInfoController(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException {
		//操作数据库
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//响应设置
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//响应json部分
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		GetPersonInfo jsonObject = new GetPersonInfo();
		//解析请求
		String token = request.getParameter("token");
		String id = request.getParameter("id");
		if(token.equals(TOKEN) == false){
			jsonObject.setCode(404);
			jsonObject.setMessage("token error");
		}
		else{
			Person person = new Person();
			person = ajt.personInfoGet(id);
			if(person.getId() == null){ //查不到
				jsonObject.setCode(0);
				jsonObject.setMessage("success");
				jsonObject.setIsGet(0);
			}
			else{ //查到了
				jsonObject.setCode(0);
				jsonObject.setMessage("success");
				jsonObject.setIsGet(1);
				Info info = new Info(person.getId(),person.getTel(),person.getArea(),person.getPos(),person.getPerStatus());
				jsonObject.setInfo(info);
			}
		}
		//
		String json = gson.toJson(jsonObject);
		out.write(json);
		out.flush();
	}
	
	@RequestMapping(value = "/getNationInfo")
	public void getNationInfoController(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException {
		//操作数据库
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//响应设置
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//响应json部分
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		GetNationInfo jsonObject = new GetNationInfo();
		//解析请求
		String token = request.getParameter("token");
		if(token.equals(TOKEN) == false){
			jsonObject.setCode(404);
		}
		else{//操作
			String date = df.format(new Date()).split(" ")[0];
			jsonObject = ajt.nationInfoGet(date);
		}
		//
		String json = gson.toJson(jsonObject);
		out.write(json);
		out.flush();
	}
	
	@RequestMapping(value = "/getAreaInfo")
	public void getAreaInfoController(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException {
		//操作数据库
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//响应设置
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//响应json部分
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		GetAreaInfo jsonObject = new GetAreaInfo();
		//解析请求
		String session = request.getParameter("sessionKey");
		System.out.println(session);
		Admin admin =new Admin();
		String time = df.format(new Date());
		admin = ajt.returnSession(session, time);
		if(admin.getId() == null) {
			jsonObject.setCode(404);
		}
		else if(admin.getLevel() == 1) {
			GetNationInfo jsonobject = new GetNationInfo();
			String date = df.format(new Date()).split(" ")[0];
			jsonobject = ajt.nationInfoGet(date);
			String json = gson.toJson(jsonobject);
			out.write(json);
			out.flush();
			return ;
		}
		else {
			time = df.format(new Date());
			jsonObject = ajt.areaInfoGet(admin, time, session);
		}
		//
		String json = gson.toJson(jsonObject);
		out.write(json);
		out.flush();
	}
	
	@RequestMapping(value = "/assign")
	public void assignController(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException {
		//操作数据库
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//响应设置
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//响应json部分
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		Assign jsonObject = new Assign();
		//解析请求
		String session = request.getParameter("sessionKey");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String area = request.getParameter("area");//这里area是["成都市","郫县"]这样格式的
		System.out.println("area:"+area);
		String tel =request.getParameter("tel");
		//判id，tel
		Admin admin = new Admin();
		if(admin.validateId(id) == false || admin.validateTel(tel) == false){
			jsonObject.setCode(1);
			jsonObject.setMessage("Parameter error");
			jsonObject.setHashcode(null);
			String json = gson.toJson(jsonObject);
			out.write(json);
			out.flush();
			return ;
		}
		String time = df.format(new Date());
		admin = ajt.returnSession(session, time);
		if(admin.getId() == null){ //session不存在
			jsonObject.setCode(404);
			jsonObject.setMessage("session error");
		}
		else{
			Admin newAdmin =new Admin();
			newAdmin.setName(name);
			newAdmin.setId(id);
			newAdmin.setTel(tel);
			newAdmin.setArea(area);
			newAdmin.setLevel((byte)(admin.getLevel()+1));
			newAdmin.setHashcode(newAdmin.generateHashcode(id,tel));
			boolean judge = ajt.adminAssign(newAdmin, time, session);
			if(judge == false){
				jsonObject.setCode(1);
				jsonObject.setMessage("admin existed");
			}
			else{
				jsonObject.setCode(0);
				jsonObject.setMessage("success");
			}
		}
		//
		String json = gson.toJson(jsonObject);
		out.write(json);
		out.flush();
	}
	
}
