package cn.uestc.dao;

import java.io.IOException;

import javax.sql.DataSource;

import cn.uestc.returnjsonclass.*;

public interface AdminDAO {

	//初始化
	public void setDataSource(DataSource ds);
	//登录 0-正常登录 1-验证失败 3-转到绑定页面
	public int login(String id,String tel);
	//绑定 -1=验证失败 否则返回管理员等级
	public int bind(String name,String hashcode,String id,String tel);
	//正常登录时新建session存入sessions表
	public int loginSession(String id,String tel,String time,String session);
	//正常登录时寻找是否已存在session,存在返回session并更新时间，否则返回null
	public String loginFindSession(String id,String tel,String time);
	//首次登录时新建session存入sessions表
	public int firstLoginSession(String id,String tel,String time,String session);
	//首次登录时寻找是否已存在session，存在返回session并更新时间，否则返回null
	public String firstLoginFindSession(String id,String tel,String time);
	//绑定时判断session并通过session取出id和tel,存在返回"id-tel"这样格式的字符串，否则返回null
	public String newBindSession(String session);
	//绑定时补全session，主要有更新时间，补上name,area,level,并且把管理员表中的名字补上
	public boolean newBindCompleteSession(String id,String tel,String session,String name,int level,String time);
	//释放session,返回true表示成功释放，否则未找到对应session
	public boolean releaseSession(String session);
	//管理员操作时，查找对应session，如果session存在，返回admin，否则返回的admin id为空,并且更新时间
	public Admin returnSession(String session,String time);
	//疫情上报，把人员存入cases病例表   这里area是["成都市","郫县"]这样格式的
	public boolean reportPerson(Person person);
	//个人信息查询，若查询成功，返回person，否则person的id字段为空
	public Person personInfoGet(String id);
	//指派下级管理员 返回false表示管理员已存在，成功返回true
	public boolean adminAssign(Admin newAdmin,String time,String session);
	//全国信息查询 
	public GetNationInfo nationInfoGet(String date);
	//辖区疫情查询
	public GetAreaInfo areaInfoGet(Admin admin,String time,String session);
	//返回管理员部分信息
	public AdminInfo returnAdminInfo(String id,String tel);
	/*
	//测试和数据库的编码
	public void test(String name)throws IOException;
	*/
}
