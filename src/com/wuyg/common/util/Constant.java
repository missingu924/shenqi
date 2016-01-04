package com.wuyg.common.util;

import java.io.File;

/**
 * 各类常量
 * @author 武玉刚
 *
 */
public class Constant
{
	// 目录均是相对于该程序的bin目录而言
	public static final File BASE_DIR = new File("../");// bin目录上一级
	public static final File DATA_DIR = new File("../data");// 数据存放目录
	public static final File DATA_INPUT_DIR = new File("../data/input");// ftp文件采集下来放置的目录
	public static final File DATA_OUTPUT_DIR = new File("../data/output");// 解析后的格式文件放置的目录
	public static final File DATA_RECORD_DIR = new File("../data/record");// 处理过的文件做记录的目录
	public static final File CONF_DIR = new File("../conf");// 配置文件存放目录
	public static final File LOGS_DIR = new File("../logs");// 日志文件存放目录

	// sqlldr入库时使用的文件目录
	public static final File ORACLE_ARC = new File("../data/sqlldr/arc");
	public static final File ORACLE_BAD = new File("../data/sqlldr/bad");
	public static final File ORACLE_CTL = new File("../data/sqlldr/ctl");
	public static final File ORACLE_LOG = new File("../data/sqlldr/log");
	public static final File ORACLE_TXT = new File("../data/sqlldr/txt");
	
	// gbldr入库时使用的文件目录
	public static final File GBASE_ARC = new File("../data/gbldr/arc");
	public static final File GBASE_BAD = new File("../data/gbldr/bad");
	public static final File GBASE_CTL = new File("../data/gbldr/ctl");
	public static final File GBASE_LOG = new File("../data/gbldr/log");
	public static final File GBASE_TXT = new File("../data/gbldr/txt");

	// 文件解析线程相关常量
	public static int DEFAULT_THREAD_POLL_SIZE = 20;// 文件解析线程池默认线程数
	public static int DEFAULT_PARSE_INTERVAL = 30;// 文件解析轮询时间间隔，单位秒
	
	// ftp 相关常量
	public static final String FTP_SERVER_CONFIG_FILE = "FtpServerConfig.xml";
	public static final String FTP_DOWNLOAD = "download";
	public static final String FTP_UPLOAD = "upload";
	public static final String FTP_RE_DOWNLOAD = "redownload";
	public static final int DEFAULT_FTP_INTERVAL = 60;// FTP下载轮询时间间隔，单位秒
	public static final int FTP_FILELASTMODIFYTIME_DEFAULT_VALUE = 3600;// FTP采集最近多长时间内生成的文件,单位秒
	public static final int DEVAULT_FTP_TIME_OUT = 300; //FTP采集线程默认超时时间，单位秒，默认5分钟
	
	
	// 文件存储时长
	public static final int PARSER_FILE_SAVED_DAYS = 3; // 原始文件存储时长
	public static final int SQLLDR_FILE_SAVED_DAYS = 3; // sqlldr 过程文件存储时长
	
	// 厂家名称
	public static final String VENDOR_ERICSSON="ERICSSON";
	public static final String VENDOR_HUAWEI="HUAWEI";
	public static final String VENDOR_ZTE="ZTE";
	public static final String VENDOR_NSN="NSN";
	public static final String VENDOR_ALCATEL="ALCATEL";
	public static final String VENDOR_DATANG="DATANG";
	public static final String VENDOR_POTEVIO="POTEVIO"; // 普天
	public static final String VENDOR_NPC="NPC"; // 新邮通
	public static final String VENDOR_FIBER="FIBER";// 烽火
	
	// 制式
	public static final String TECH_GSM="GSM";
	public static final String TECH_TD_SCDMA="TD-SCDMA";
	public static final String TECH_TD_LTE="TD-LTE";	
	
	// 网元类型
	public static final String NETYPE_OMC = "OMC";
	public static final String NETYPE_BSC = "BSC";
	public static final String NETYPE_RNC = "RNC";
	
	// 时间类型
	public static final String TIMETYPE_15MIN = "15MIN";
	public static final String TIMETYPE_30MIN = "30MIN";
	public static final String TIMETYPE_HOUR = "HOUR";
	public static final String TIMETYPE_DAY = "DAY";
	public static final String TIMETYPE_WEEK = "WEEK";
	public static final String TIMETYPE_MONTH = "MONTH";
	public static final String TIMETYPE_QUARTER = "QUARTER";
	public static final String TIMETYPE_YEAR = "YEAR";
	
	// 数据类型
	public static final String DATATYPE_PM = "PM";
	public static final String DATATYPE_CM = "CM";
	public static final String DATATYPE_MR = "MR";
	public static final String DATATYPE_PARAM = "PARAM";
	public static final String DATATYPE_FM = "FM";
	
}
