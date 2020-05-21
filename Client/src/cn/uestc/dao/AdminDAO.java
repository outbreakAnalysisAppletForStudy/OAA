package cn.uestc.dao;

import java.io.IOException;

import javax.sql.DataSource;

import cn.uestc.returnjsonclass.*;

public interface AdminDAO {

	//��ʼ��
	public void setDataSource(DataSource ds);
	//��¼ 0-������¼ 1-��֤ʧ�� 3-ת����ҳ��
	public int login(String id,String tel);
	//�� -1=��֤ʧ�� ���򷵻ع���Ա�ȼ�
	public int bind(String name,String hashcode,String id,String tel);
	//������¼ʱ�½�session����sessions��
	public int loginSession(String id,String tel,String time,String session);
	//������¼ʱѰ���Ƿ��Ѵ���session,���ڷ���session������ʱ�䣬���򷵻�null
	public String loginFindSession(String id,String tel,String time);
	//�״ε�¼ʱ�½�session����sessions��
	public int firstLoginSession(String id,String tel,String time,String session);
	//�״ε�¼ʱѰ���Ƿ��Ѵ���session�����ڷ���session������ʱ�䣬���򷵻�null
	public String firstLoginFindSession(String id,String tel,String time);
	//��ʱ�ж�session��ͨ��sessionȡ��id��tel,���ڷ���"id-tel"������ʽ���ַ��������򷵻�null
	public String newBindSession(String session);
	//��ʱ��ȫsession����Ҫ�и���ʱ�䣬����name,area,level,���Ұѹ���Ա���е����ֲ���
	public boolean newBindCompleteSession(String id,String tel,String session,String name,int level,String time);
	//�ͷ�session,����true��ʾ�ɹ��ͷţ�����δ�ҵ���Ӧsession
	public boolean releaseSession(String session);
	//����Ա����ʱ�����Ҷ�Ӧsession�����session���ڣ�����admin�����򷵻ص�admin idΪ��,���Ҹ���ʱ��
	public Admin returnSession(String session,String time);
	//�����ϱ�������Ա����cases������   ����area��["�ɶ���","ۯ��"]������ʽ��
	public boolean reportPerson(Person person);
	//������Ϣ��ѯ������ѯ�ɹ�������person������person��id�ֶ�Ϊ��
	public Person personInfoGet(String id);
	//ָ���¼�����Ա ����false��ʾ����Ա�Ѵ��ڣ��ɹ�����true
	public boolean adminAssign(Admin newAdmin,String time,String session);
	//ȫ����Ϣ��ѯ 
	public GetNationInfo nationInfoGet(String date);
	//Ͻ�������ѯ
	public GetAreaInfo areaInfoGet(Admin admin,String time,String session);
	//���ع���Ա������Ϣ
	public AdminInfo returnAdminInfo(String id,String tel);
	/*
	//���Ժ����ݿ�ı���
	public void test(String name)throws IOException;
	*/
}
