package com.hz.util;

import com.hz.config.ConfigReader;

public class SystemConstant
{
	// ϵͳ����
	public static final String SYSTEM_NAME = "10088Эͬ����ϵͳ";

	// �û���Ϣ
	public static final String AUTH_USER_INFO = "AUTH_USER_INFO";
	public static final String AUTH_USER_ACCOUNT = "account";
	public static final String AUTH_USER_PASSWORD = "password";
	public static final String AUTH_USER_ADMIN="admin";

	// ÿҳ��������
	public static final int PAGE_ROWS = 20;
	public static final String PAGE_NUM = "PAGE_NUM";

	// ���ŷ���
	public static final String SMS_USERNAME = "52593";
	public static final String SMS_PASSWORD = "Wyg2461290!";
	
	public static final String ROLE_ADMIN = "ϵͳ����Ա";
	public static final String ROLE_DISTRICT_ADMIN = "���ع���Ա";
	public static final String ROLE_DEPARTMENT_ADMIN = "���Ź���Ա";
	public static final String ROLE_COMMON_USER = "��ͨԱ��";
	
	// Ĭ�����ݿ�
	public static final String DEFAULT_DB="db";
//	public static final String INNER_DB="innerDb";
	public static final String INNER_DB="db";
	public static final String DB_ORACLE="ORALCE";
	public static final String DB_MYSQL="MYSQL";
	public static final String DB_SQLSERVER="SQLSERVER";
	
	// T1���ݿ���
	public static final String T1_DB=ConfigReader.getProperties("t1.db_name");
	
}
