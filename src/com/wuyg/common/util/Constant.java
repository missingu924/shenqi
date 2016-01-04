package com.wuyg.common.util;

import java.io.File;

/**
 * ���ೣ��
 * @author �����
 *
 */
public class Constant
{
	// Ŀ¼��������ڸó����binĿ¼����
	public static final File BASE_DIR = new File("../");// binĿ¼��һ��
	public static final File DATA_DIR = new File("../data");// ���ݴ��Ŀ¼
	public static final File DATA_INPUT_DIR = new File("../data/input");// ftp�ļ��ɼ��������õ�Ŀ¼
	public static final File DATA_OUTPUT_DIR = new File("../data/output");// ������ĸ�ʽ�ļ����õ�Ŀ¼
	public static final File DATA_RECORD_DIR = new File("../data/record");// ��������ļ�����¼��Ŀ¼
	public static final File CONF_DIR = new File("../conf");// �����ļ����Ŀ¼
	public static final File LOGS_DIR = new File("../logs");// ��־�ļ����Ŀ¼

	// sqlldr���ʱʹ�õ��ļ�Ŀ¼
	public static final File ORACLE_ARC = new File("../data/sqlldr/arc");
	public static final File ORACLE_BAD = new File("../data/sqlldr/bad");
	public static final File ORACLE_CTL = new File("../data/sqlldr/ctl");
	public static final File ORACLE_LOG = new File("../data/sqlldr/log");
	public static final File ORACLE_TXT = new File("../data/sqlldr/txt");
	
	// gbldr���ʱʹ�õ��ļ�Ŀ¼
	public static final File GBASE_ARC = new File("../data/gbldr/arc");
	public static final File GBASE_BAD = new File("../data/gbldr/bad");
	public static final File GBASE_CTL = new File("../data/gbldr/ctl");
	public static final File GBASE_LOG = new File("../data/gbldr/log");
	public static final File GBASE_TXT = new File("../data/gbldr/txt");

	// �ļ������߳���س���
	public static int DEFAULT_THREAD_POLL_SIZE = 20;// �ļ������̳߳�Ĭ���߳���
	public static int DEFAULT_PARSE_INTERVAL = 30;// �ļ�������ѯʱ��������λ��
	
	// ftp ��س���
	public static final String FTP_SERVER_CONFIG_FILE = "FtpServerConfig.xml";
	public static final String FTP_DOWNLOAD = "download";
	public static final String FTP_UPLOAD = "upload";
	public static final String FTP_RE_DOWNLOAD = "redownload";
	public static final int DEFAULT_FTP_INTERVAL = 60;// FTP������ѯʱ��������λ��
	public static final int FTP_FILELASTMODIFYTIME_DEFAULT_VALUE = 3600;// FTP�ɼ�����೤ʱ�������ɵ��ļ�,��λ��
	public static final int DEVAULT_FTP_TIME_OUT = 300; //FTP�ɼ��߳�Ĭ�ϳ�ʱʱ�䣬��λ�룬Ĭ��5����
	
	
	// �ļ��洢ʱ��
	public static final int PARSER_FILE_SAVED_DAYS = 3; // ԭʼ�ļ��洢ʱ��
	public static final int SQLLDR_FILE_SAVED_DAYS = 3; // sqlldr �����ļ��洢ʱ��
	
	// ��������
	public static final String VENDOR_ERICSSON="ERICSSON";
	public static final String VENDOR_HUAWEI="HUAWEI";
	public static final String VENDOR_ZTE="ZTE";
	public static final String VENDOR_NSN="NSN";
	public static final String VENDOR_ALCATEL="ALCATEL";
	public static final String VENDOR_DATANG="DATANG";
	public static final String VENDOR_POTEVIO="POTEVIO"; // ����
	public static final String VENDOR_NPC="NPC"; // ����ͨ
	public static final String VENDOR_FIBER="FIBER";// ���
	
	// ��ʽ
	public static final String TECH_GSM="GSM";
	public static final String TECH_TD_SCDMA="TD-SCDMA";
	public static final String TECH_TD_LTE="TD-LTE";	
	
	// ��Ԫ����
	public static final String NETYPE_OMC = "OMC";
	public static final String NETYPE_BSC = "BSC";
	public static final String NETYPE_RNC = "RNC";
	
	// ʱ������
	public static final String TIMETYPE_15MIN = "15MIN";
	public static final String TIMETYPE_30MIN = "30MIN";
	public static final String TIMETYPE_HOUR = "HOUR";
	public static final String TIMETYPE_DAY = "DAY";
	public static final String TIMETYPE_WEEK = "WEEK";
	public static final String TIMETYPE_MONTH = "MONTH";
	public static final String TIMETYPE_QUARTER = "QUARTER";
	public static final String TIMETYPE_YEAR = "YEAR";
	
	// ��������
	public static final String DATATYPE_PM = "PM";
	public static final String DATATYPE_CM = "CM";
	public static final String DATATYPE_MR = "MR";
	public static final String DATATYPE_PARAM = "PARAM";
	public static final String DATATYPE_FM = "FM";
	
}
