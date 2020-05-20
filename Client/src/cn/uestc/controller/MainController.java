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

	public static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //sessions���ʱ���ʽ
	public static final String TOKEN = "DEEPDARKFANTASY"; //token
	
	@RequestMapping(value = "/login")
	public void loginController(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		//�������ݿ�
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//��Ӧ����
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//��Ӧjson����
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		Login jsonObject = new Login();
		//�жϹ���
		Admin admin = new Admin();
		String id = request.getParameter("id");
		String tel = request.getParameter("tel");
		//token
		String token = request.getParameter("token");
		if(token.equals(TOKEN) == false) { //���ɲ�������������
			jsonObject.setCode(2);
			jsonObject.setMessage("Parameter error");
		}
		else if(admin.validateId(id) == false || admin.validateTel(tel) == false) {//��������
			jsonObject.setCode(2);
			jsonObject.setMessage("Parameter error");
		}
		else {
			int res = ajt.login(id, tel);
			if(res == 0) {//������¼
				//��ȡ��ǰʱ��
				String time = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
				//���Ƿ����session
				String session = ajt.loginFindSession(id, tel, time);
				if(session == null) { //sessionΪ�վ��½�һ��
					//session  
					SessionGenerator sessiongenerator = new SessionGenerator();
					String newsession = sessiongenerator.gene(time);
					//����session
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
			else if(res == 1) {//��֤ʧ��
				jsonObject.setCode(res);
				jsonObject.setMessage("Validation failed");
			}
			else {//ת���󶨽���
				//��ȡ��ǰʱ��
				String time = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
				String session = ajt.firstLoginFindSession(id, tel, time);
				if(session == null) { //sessionΪ���½�һ��
					//session  
					SessionGenerator sessiongenerator = new SessionGenerator();
					String newsession = sessiongenerator.gene(time);
					session = newsession;
					//����session
					ajt.firstLoginSession(id,tel,time,session);
				}
				//json
				jsonObject.setCode(res);
				jsonObject.setMessage("Go to binding");
				jsonObject.setSession(session);
			}
		}
		//תΪjson���ҷ��͵�ǰ��
		String json = gson.toJson(jsonObject);
		out.write(json);
		out.flush();
	}
	
	@RequestMapping(value = "/newBind")
	public void newBindController(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		//�������ݿ�
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//��Ӧ����
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//��Ӧjson����
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		NewBind jsonObject = new NewBind();
		//�жϹ���
		String session = request.getParameter("sessionKey");
		String name = request.getParameter("name");
		String hashcode = request.getParameter("hashcode");
		//����û�ж�Ӧ��session
		String judge = ajt.newBindSession(session);
		System.out.println("newBind session:"+request.getParameter("sessionKey"));
		if(judge == null) {  //session����
			jsonObject.setCode(404);
			jsonObject.setMessage("session error");
		}
		else {//�ж�Ӧ��session
			String id = judge.split("-")[0];
			String tel = judge.split("-")[1];
			//��֤hashcode�Ƿ���ȷ
			int level = ajt.bind(name, hashcode, id, tel);
			if(level == -1) { //��֤ʧ��
				jsonObject.setCode(1);
				jsonObject.setMessage("Validation failed");
			}
			else {  //�󶨳ɹ�
				jsonObject.setCode(0);
				jsonObject.setMessage("success");
				AdminInfo adminInfo = new AdminInfo();
				adminInfo = ajt.returnAdminInfo(id, tel);
				jsonObject.setAdminInfo(adminInfo);
				//��ȫsessions��͹���Ա��
				String time = df.format(new Date());
				ajt.newBindCompleteSession(id, tel, session, name, level, time);
			}
		}
		//ת��json�����͵�ǰ��
		String json = gson.toJson(jsonObject);
		out.write(json);
		out.flush();
	}
	
	@RequestMapping(value = "/release")
	public void releaseController(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		//�������ݿ�
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//��Ӧ����
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//��Ӧjson����
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		Release jsonObject = new Release();
		//��������
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
		//�������ݿ�
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//��Ӧ����
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//��Ӧjson����
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		Report jsonObject = new Report();
		//��������
		String id = request.getParameter("id");
		String tel = request.getParameter("tel");
		//����area��["�ɶ���","ۯ��"]������ʽ��
		String area =new String(request.getParameter("area").getBytes("ISO-8859-1"),"UTF-8");
		System.out.println("area:"+area);
		String pos = request.getParameter("pos");
		int perStatus = Integer.parseInt(request.getParameter("perStatus"));
		String session = request.getParameter("sessionKey");
		//�ж�id��tel��perStatus�Ƿ���ȷ
		Admin admin = new Admin();
		if(admin.validateId(id) == false) {//���֤�Ŵ���
			jsonObject.setCode(2);
			jsonObject.setMessage("id error");
		}
		else if(admin.validateTel(tel) == false) {//�绰�Ŵ���
			jsonObject.setCode(2);
			jsonObject.setMessage("tel error");
		}
		else if(perStatus != 0 || perStatus != 1 || perStatus !=2 || perStatus != 3) {//����״������
			jsonObject.setCode(2);
			jsonObject.setMessage("perStatus error");
		}
		String time = df.format(new Date());
		admin = ajt.returnSession(session, time);
		if(admin.getId() == null) { //session����
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
		//�������ݿ�
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//��Ӧ����
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//��Ӧjson����
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		GetPersonInfo jsonObject = new GetPersonInfo();
		//��������
		String token = request.getParameter("token");
		String id = request.getParameter("id");
		if(token.equals(TOKEN) == false){
			jsonObject.setCode(404);
			jsonObject.setMessage("token error");
		}
		else{
			Person person = new Person();
			person = ajt.personInfoGet(id);
			if(person.getId() == null){ //�鲻��
				jsonObject.setCode(0);
				jsonObject.setMessage("success");
				jsonObject.setIsGet(0);
			}
			else{ //�鵽��
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
		//�������ݿ�
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//��Ӧ����
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//��Ӧjson����
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		GetNationInfo jsonObject = new GetNationInfo();
		//��������
		String token = request.getParameter("token");
		if(token.equals(TOKEN) == false){
			jsonObject.setCode(404);
		}
		else{//����
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
		//�������ݿ�
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//��Ӧ����
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//��Ӧjson����
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		GetAreaInfo jsonObject = new GetAreaInfo();
		//��������
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
		//�������ݿ�
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		AdminJDBCTemplate ajt = (AdminJDBCTemplate) context.getBean("adminJDBCTemplate");
		//��Ӧ����
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//��Ӧjson����
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Writer out = response.getWriter();
		Assign jsonObject = new Assign();
		//��������
		String session = request.getParameter("sessionKey");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String area = request.getParameter("area");//����area��["�ɶ���","ۯ��"]������ʽ��
		System.out.println("area:"+area);
		String tel =request.getParameter("tel");
		//��id��tel
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
		if(admin.getId() == null){ //session������
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
