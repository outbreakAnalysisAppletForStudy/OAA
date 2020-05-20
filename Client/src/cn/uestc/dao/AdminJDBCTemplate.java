package cn.uestc.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.uestc.returnjsonclass.*;

public class AdminJDBCTemplate implements AdminDAO {

	static Logger logger = LogManager.getLogger(AdminJDBCTemplate.class);

	private DataSource dataSource;
	private JdbcTemplate jdbc;
	//初始化
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbc = new JdbcTemplate(dataSource);
	}
	//登录 0-正常登录 1-验证失败 3-转到绑定页面
	public int login(String id,String tel) {
		String SQL = "select count(*) from administrators where id = ? and tel = ?";
		int cnt = jdbc.queryForObject(SQL,int.class,id,tel);  //查询管理员表中是否有和id、tel匹配的记录
		System.out.println("-----login:");
		System.out.println("id:"+id);
		System.out.println("tel:"+tel);
		if(cnt == 0) {
			logger.info("The id (" + id + ") logined normally.");
			return 1;
		}
		else {
			String SQL1 = "select name from administrators where id = ? and tel = ?";
			String name = jdbc.queryForObject(SQL1, String.class,id,tel); //查询管理员表中是否有和id、tel匹配的name字段，如果有表示已绑定，否则未绑定。
			if(name != null) {//已绑定
				logger.info("The id (" + id + ") logined.");
				return 0;
			}
			else {
				logger.info("The id (" + id + ") is binding.");
				return 3;
			}
		}
	}
	//绑定 -1=验证失败 否则返回管理员等级
	public int bind(String name,String hashcode,String id,String tel) {
		String SQL = "select count(*) from administrators where id = ? and tel = ? and hashcode = ?";
		int cnt = jdbc.queryForObject(SQL,int.class,id,tel,hashcode);
		if(cnt == 0) { //验证失败
			logger.warn("The id (" + id + ") failed to validate.");
			return -1;
		}
		else { //绑定成功
			logger.info("The id (" + id + ") binded successfully.");
			String SQL1 = "update administrators set name = ? where id = ? and tel = ? and hashcode = ?";
			jdbc.update(SQL1,name,id,tel,hashcode);
			String SQL2 = "select level from administrators where id = ? and tel = ? and hashcode = ?";
			return jdbc.queryForObject(SQL2, int.class,id,tel,hashcode);
		}
	}
	//正常登录时新建session存入sessions表
	public int loginSession(String id,String tel,String time,String session) {
		String name = jdbc.queryForObject("select name from administrators where id = ? and tel = ?", String.class,id,tel);
		String area = jdbc.queryForObject("select area from administrators where id = ? and tel = ?", String.class,id,tel);
		int level = jdbc.queryForObject("select level from administrators where id = ? and tel = ?",int.class,id,tel);
		String SQL = "insert into sessions(name,time,id,tel,session,area,level) values(?,?,?,?,?,?,?)";
		jdbc.update(SQL,name,time,id,tel,session,area,level);
		System.out.println("正常登录时保存新建session：");
		System.out.println("id:"+id);
		System.out.println("tel:"+tel);
		System.out.println("name:"+name);
		System.out.println("area:"+area);
		System.out.println("level:"+level);
		System.out.println("time:"+time);
		System.out.println("session:"+session);
		logger.info("The id (" + id + ") created a new session " + session +".");
		return 0; //成功
	}
	//正常登录时寻找是否已存在session,存在返回session并更新时间，否则返回null
	public String loginFindSession(String id,String tel,String time) {
		String SQL = "select count(*) from sessions where id = ? and tel = ?";
		int cnt = jdbc.queryForObject(SQL, int.class,id,tel);
		if(cnt == 0) {//不存在
			logger.warn("The id (" + id + ") had no session before.");
			return null;
		}
		else {//存在
			String session = jdbc.queryForObject("select session from sessions where id = ? and tel = ?", String.class,id,tel);//取session
			jdbc.update("update sessions set time = ? where id = ? and tel = ?",time,id,tel);//更新时间
			logger.info("The id (" + id + ") updated the session.");
			return session;
		}
	}
	//首次登录时新建session存入sessions表
	public int firstLoginSession(String id,String tel,String time,String session) {
		String SQL = "insert into sessions(id,tel,time,session) values(?,?,?,?)";
		jdbc.update(SQL,id,tel,time,session);
		System.out.println("首次登录时保存session：");
		System.out.println("id:"+id);
		System.out.println("tel:"+tel);
		System.out.println("time:"+time);
		System.out.println("session:"+session);
		logger.info("The id (" + id + ") created a new session for the first login.");
		return 0; //成功
	}
	//首次登录时寻找是否已存在session，存在返回session并更新时间，否则返回null
	public String firstLoginFindSession(String id,String tel,String time) {
		String SQL = "select count(*) from sessions where id = ? and tel = ?";
		int cnt = jdbc.queryForObject(SQL, int.class,id,tel);
		if(cnt == 0) {//不存在
			logger.warn("The id (" + id + ") had no session before.");
			return null;
		}
		else {//存在
			String session = jdbc.queryForObject("select session from sessions where id = ? and tel = ?", String.class,id,tel);//取session
			jdbc.update("update sessions set time = ? where id = ? and tel = ?",time,id,tel);//更新时间
			logger.info("The id (" + id + ") updated the session.");
			return session;
		}
	}
	//绑定时判断session并通过session取出id和tel,存在返回"id-tel"这样格式的字符串，否则返回null
	public String newBindSession(String session) {
		String SQL = "select count(*) from sessions where session = ?";
		int cnt = jdbc.queryForObject(SQL, int.class,session);
		if(cnt == 0) {
			logger.warn("The session (" + session + ") was validated and did not exist.");
			return null;
		}
		else {
			String SQL1 = "select id from sessions where session = ?";
			String id = jdbc.queryForObject(SQL1, String.class,session);
			String SQL2 = "select tel from sessions where session = ?";
			String tel = jdbc.queryForObject(SQL2,String.class,session);
			logger.info("The session (" + session + ") was validated and returned id(" + id + ") and tel(" + tel + ").");
			return id+"-"+tel;
		}
	}
	//绑定时补全session，主要有更新时间，补上name,area,level,并且把管理员表中的名字补上
	public boolean newBindCompleteSession(String id,String tel,String session,String name,int level,String time) {
		String area = jdbc.queryForObject("select area from administrators where id = ? and tel = ? and level = ?",String.class,id,tel,level);
		//补全更新session表
		int cnt1 = jdbc.update("update sessions set name = ?,area = ?,level = ?,time = ? where session = ?",name,area,level,time,session);
		if(cnt1 != 0) {
			System.out.println("补全更新session表成功!");
			logger.info("The session table was completed with the session(" + session + ".");
		}
		else {
			logger.warn("The session table failed to be completed.");
			return false;
		}
		//补全更新administrators表
		int cnt2 = jdbc.update("update administrators set name = ? where id = ? and tel = ?",name,id,tel);
		if(cnt2 != 0) {
			logger.info("The administrators table was completed with the session(" + session + ".");
			System.out.println("补全更新administrators表成功!");
		}
		else {
			logger.warn("The administrators table failed to be completed.");
			return false;
		}	
		return true;
	}
	//释放session,返回true表示成功释放，否则未找到对应session
	public boolean releaseSession(String session) {
		int cnt = jdbc.update("delete from sessions where session = ?",session);
		if(cnt == 0) {
			System.out.println("未找到对应session："+session);
			logger.warn("The session (" + session + ") is not found.");
			return false;
		}
		else {
			logger.info("The session (" + session + ") is released.");
			System.out.println("成功释放session："+session);
			return true;
		}
	}
	//管理员操作时，查找对应session，如果session存在，返回admin，否则返回的admin id为空,并且更新时间
	public Admin returnSession(String session,String time) {
		Admin admin = new Admin();
		int cnt = jdbc.queryForObject("select count(*) from sessions where session = ?",int.class,session);
		if(cnt == 0) {
			logger.warn("The session (" + session + ") did not exist when running administrator operations.");
			return admin;
		}
		else {
			admin.setId(jdbc.queryForObject("select id from sessions where session = ?",String.class,session));
			admin.setArea(jdbc.queryForObject("select area from sessions where session = ?",String.class,session));
			admin.setName(jdbc.queryForObject("select name from sessions where session = ?",String.class,session));
			admin.setTel(jdbc.queryForObject("select tel from sessions where session = ?",String.class,session));
			admin.setLevel(jdbc.queryForObject("select level from sessions where session = ?",int.class,session).byteValue());
			jdbc.update("update sessions set time = ? where session = ?",time,session);
			System.out.println("管理员操作返回session成功，管理员：");
			admin.output();
			logger.info("The session (" + session + ") existed and return the admin (id:" + admin.getId() +").");
			return admin;
		}
	}
	//疫情上报，把人员存入cases病例表   这里area是["成都市","郫县"]这样格式的
	public boolean reportPerson(Person person) {
		String date = person.getDate();
		String id = person.getId();
		String tel = person.getTel();
		String pos = person.getPos();
		int perStatus = person.getPerStatus();
		String submit = person.getSubmit();
		String area = person.getArea();
		logger.info("reportPerson: area: "+area);
		String name1 = area.split(",")[0],name2 = area.split(",")[1],name3 = area.split(",")[2];
		logger.info("reportPerson: name1: "+name1);
		jdbc.update("insert into test(area) values(?)",area);
		String area1 = jdbc.queryForObject("select area from province where name = ?",String.class,name1);
		String area2 = jdbc.queryForObject("select area from city where name = ? and area like ?",String.class,name2,(area1.substring(0,2)+"%"));
		String area3 = jdbc.queryForObject("select area from county where name = ? and area like ?",String.class,name3,(area2.substring(0,4)+"%"));
		person.setArea(area3);
		String adminArea = jdbc.queryForObject("select area from administrators where id = ?", String.class,submit);
		int level = jdbc.queryForObject("select level from administrators where id = ?",int.class,submit);
		if(level == 1);
		else if(level == 2) {
			if(adminArea.substring(0,2).equals(area3.substring(0,2)) == false) {
				System.out.println("疫情填报错误：管理员权限不够");
			}
		}
		else if(level == 3) {
			if(adminArea.substring(0,4).equals(area3.substring(0,4)) == false) {
				System.out.println("疫情填报错误：管理员权限不够");
			}
		}
		else {
			if(adminArea.substring(0,6).equals(area3.substring(0,6)) == false) {
				System.out.println("疫情填报错误：管理员权限不够");
			}
		}
		jdbc.update("insert into cases(date,id,area,tel,pos,perStatus,submit) values(?,?,?,?,?,?,?)",date,id,person.getArea(),tel,pos,perStatus,submit);
		System.out.println("疫情上报：");
		person.output();
		logger.info("The case (id:" + person.getId() +") was reported.");
		return true;
	}
	//个人信息查询，若查询成功，返回person，否则person的id字段为空
	public Person personInfoGet(String id){
		Person person = new Person();
		int cnt = jdbc.queryForObject("select count(*) from cases where id = ?",int.class,id);
		System.out.println("个人信息查询：");
		System.out.println("所查身份证号：" + id);
		if(cnt == 0){
			System.out.println("查无此人");
			logger.warn("The id (" + id + ") was not found.");
			return person;
		}
		else{
			person.setArea(jdbc.queryForObject("select area from cases where id = ?",String.class,id));
			person.setTel(jdbc.queryForObject("select tel from cases where id = ?",String.class,id));
			person.setPos(jdbc.queryForObject("select pos from cases where id = ?",String.class,id));
			person.setDate(jdbc.queryForObject("select date from cases where id = ?",String.class,id));
			person.setSubmit(jdbc.queryForObject("select submit from cases where id = ?",String.class,id));
			person.setPerStatus(jdbc.queryForObject("select perStatus from cases where id = ?",int.class,id).byteValue());
			person.setId(id);
			person.output();
			logger.info("The information of id (" + id + ") was queried successfully.");
			return person;
		}
	}
	//指派下级管理员 返回false表示管理员已存在，成功返回true
	public boolean adminAssign(Admin newAdmin,String time,String session){//有问题
		String area = newAdmin.getArea();
		if(newAdmin.getLevel() == 2) {
			String name1 = area.split(",")[0];
			String area1 = jdbc.queryForObject("select area from province where name = ?",String.class,name1);
			newAdmin.setArea(area1);
		}
		else if(newAdmin.getLevel() == 3) {
			String name1 = area.split(",")[0],name2 = area.split(",")[1];
			String area1 = jdbc.queryForObject("select area from province where name = ?",String.class,name1);
			String area2 = jdbc.queryForObject("select area from city where name = ? and area like ?",String.class,name2,(area1.substring(0,2)+"%"));
			newAdmin.setArea(area2);
		}
		else if(newAdmin.getLevel() == 4) {
			String name1 = area.split(",")[0],name2 = area.split(",")[1],name3 = area.split(",")[2];
			String area1 = jdbc.queryForObject("select area from province where name = ?",String.class,name1);
			String area2 = jdbc.queryForObject("select area from city where name = ? and area like ?",String.class,name2,(area1.substring(0,2)+"%"));
			String area3 = jdbc.queryForObject("select area from county where name = ? and area like ?",String.class,name3,(area2.substring(0,4)+"%"));
			newAdmin.setArea(area3);
		}
		jdbc.update("update sessions set time = ? where session = ?",time,session);
		String name = jdbc.queryForObject("select name from sessions where session = ?",String.class,session);
		int cnt = jdbc.queryForObject("select count(*) from administrators where id = ?",int.class,newAdmin.getId());
		System.out.println("管理员：" + name);
		System.out.println("指派下级管理员：");
		newAdmin.output();
		if(cnt != 0){
			System.out.println("该管理员已存在xxxx");
			logger.warn("The administrator (name:" + newAdmin.getName() + ") already existed!");
			return false;
		}
		else{
			jdbc.update("insert into administrators(id,tel,area,hashcode,level) values(?,?,?,?,?)",newAdmin.getId(),newAdmin.getTel(),
				newAdmin.getArea(),newAdmin.getHashcode(),newAdmin.getLevel());
			System.out.println("指派成功!");
			logger.info("The new administrator (name:" + newAdmin.getName() + ") was assigned by administrator (name:" + name + ").");
			return true;
		}
	}

	//全国信息查询 
	public GetNationInfo nationInfoGet(String date){
		GetNationInfo getNationInfo = new GetNationInfo();
		List<CityInfo> info = new ArrayList<CityInfo>();
		DailyTextContent dailyTextContent = new DailyTextContent();
		IncrData incrData = new IncrData();
		int serial = 1;
		while(jdbc.queryForObject("select count(*) from province where serial = ?",int.class,serial) != 0){
			Detail detail = new Detail();
			CityInfo cityInfo = new CityInfo();
			detail.setName(jdbc.queryForObject("select name from province where serial = ?",String.class,serial));
			if(serial < 10){
				cityInfo.setProvince("0"+serial);
			}
			else{
				cityInfo.setProvince(""+serial);
			}
			String area = jdbc.queryForObject("select area from province where serial = ?",String.class,serial);
			String match = area.substring(0,2)+"%";  //sql模糊查询的like
			detail.setExist(jdbc.queryForObject("select count(*) from cases where area like ? and perStatus = 1",int.class,match));
			detail.setSuspect(jdbc.queryForObject("select count(*) from cases where area like ? and perStatus = 0",int.class,match));
			detail.setRecover(jdbc.queryForObject("select count(*) from cases where area like ? and perStatus = 2",int.class,match));
			detail.setDead(jdbc.queryForObject("select count(*) from cases where area like ? and perStatus = 3",int.class,match));
			detail.setRecent(jdbc.queryForObject("select count(*) from cases where area like ? and perStatus = 1 and date = ?",int.class,match,date));
			detail.setTotal(detail.getDead()+detail.getExist()+detail.getRecover());
			cityInfo.setDetail(detail);
			info.add(cityInfo);
			serial++;
		}
		dailyTextContent.setConfirmedcount(jdbc.queryForObject("select count(*) from cases where perStatus = 1", int.class));
		dailyTextContent.setCuredcount(jdbc.queryForObject("select count(*) from cases where perStatus = 2", int.class));
		dailyTextContent.setDeadcount(jdbc.queryForObject("select count(*) from cases where perStatus = 3", int.class));
		dailyTextContent.setSuspectedcount(jdbc.queryForObject("select count(*) from cases where perStatus = 0", int.class));
		incrData.setConfirmedincr(jdbc.queryForObject("select count(*) from cases where perStatus = 1 and date = ?", int.class,date));
		incrData.setCuredincr(jdbc.queryForObject("select count(*) from cases where perStatus = 2 and date = ?", int.class,date));
		incrData.setDeadincr(jdbc.queryForObject("select count(*) from cases where perStatus = 3 and date = ?", int.class,date));
		incrData.setSuspectedincr(jdbc.queryForObject("select count(*) from cases where perStatus = 0 and date = ?", int.class,date));
		System.out.println("查询全国信息成功");
		getNationInfo.setCode(0);
		getNationInfo.setInfo(info);
		getNationInfo.setDailytextcontent(dailyTextContent);
		getNationInfo.setIncrdata(incrData);
		logger.info("National information was queried successfully.");
		return getNationInfo;
	}

	//辖区疫情查询
	public GetAreaInfo areaInfoGet(Admin admin,String time,String session){
		jdbc.update("update sessions set time = ? where session = ?",time,session);
		System.out.println("辖区疫情查询：");
		admin.output();
		GetAreaInfo getAreaInfo = new GetAreaInfo();
		Detail detail = new Detail();
		if(admin.getLevel() == 2){
			detail.setName(jdbc.queryForObject("select name from province where area = ?",String.class,admin.getArea()));
		}
		else if(admin.getLevel() == 3){
			detail.setName(jdbc.queryForObject("select name from city where area = ?",String.class,admin.getArea()));
		}
		else if(admin.getLevel() == 4){
			detail.setName(jdbc.queryForObject("select name from county where area = ?",String.class,admin.getArea()));
		}
		String date = time.split(" ")[0];
		String match = admin.getArea().substring(0,2*(admin.getLevel()-1))+"%";  //sql模糊查询的like
		detail.setExist(jdbc.queryForObject("select count(*) from cases where area like ? and perStatus = 1",int.class,match));
		detail.setSuspect(jdbc.queryForObject("select count(*) from cases where area like ? and perStatus = 0",int.class,match));
		detail.setRecover(jdbc.queryForObject("select count(*) from cases where area like ? and perStatus = 2",int.class,match));
		detail.setDead(jdbc.queryForObject("select count(*) from cases where area like ? and perStatus = 3",int.class,match));
		detail.setRecent(jdbc.queryForObject("select count(*) from cases where area like ? and perStatus = 1 and date = ?",int.class,match,date));
		detail.setTotal(detail.getDead()+detail.getExist()+detail.getRecover());
		getAreaInfo.setArea(admin.getArea());
		getAreaInfo.setCode(0);
		getAreaInfo.setLevel(admin.getLevel());
		getAreaInfo.setDetail(detail);
		logger.info("The administrator queried (area:" + admin.getArea() + " level:" + admin.getLevel() + ") successfully.");
		return getAreaInfo;
	}
	//返回管理员部分信息
	public AdminInfo returnAdminInfo(String id,String tel) {
		AdminInfo adminInfo = new AdminInfo();
		int level = jdbc.queryForObject("select level from administrators where id = ? and tel = ?", int.class,id,tel);
		adminInfo.setLevel(level);
		List<String> area = new ArrayList<String>();
		if(level == 2) {
			String areaCode = jdbc.queryForObject("select area from administrators where id = ? and tel = ?",String.class,id,tel);
			String name = jdbc.queryForObject("select name from province where area = ?", String.class,areaCode);
			area.add(name);
		}
		else if(level == 3) {
			String areaCode1 = jdbc.queryForObject("select area from administrators where id = ? and tel = ?",String.class,id,tel);
			String name1 = jdbc.queryForObject("select name from city where area = ?", String.class,areaCode1);
			String upper = jdbc.queryForObject("select upper from city where area = ? ", String.class,areaCode1);
			String name = jdbc.queryForObject("select name from province where area = ?", String.class,upper);
			area.add(name);
			area.add(name1);
		}
		else if(level == 4){
			String areaCode1 = jdbc.queryForObject("select area from administrators where id = ? and tel = ?",String.class,id,tel);
			String name1 = jdbc.queryForObject("select name from county where area = ?", String.class,areaCode1);
			String upper = jdbc.queryForObject("select upper from county where area = ? ", String.class,areaCode1);
			String name = jdbc.queryForObject("select name from city where area = ?", String.class,upper);
			String upper2 = jdbc.queryForObject("select upper from city where area = ?", String.class,upper);
			String name2 = jdbc.queryForObject("select name from province where area = ?", String.class,upper2);
			area.add(name2);
			area.add(name);
			area.add(name1);
		}
		adminInfo.setArea(area);
		return adminInfo;
	}
	
}
