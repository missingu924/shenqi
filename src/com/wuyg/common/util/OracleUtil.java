package com.wuyg.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hz.config.ConfigReader;

/**
 * @deprecated ����ʹ��com.inspur.ftpparserframework.dbloader.OracleLoader����com.inspur.ftpparserframework.dbloader.DbLoaderUtil
 * 
 */
@Deprecated
public class OracleUtil
{
	public static String LOAD_MODE_INSERT = "INSERT";
	public static String LOAD_MODE_APPEND = "APPEND";
	public static String LOAD_MODE_REPLACE = "REPLACE";
	public static String LOAD_MODE_TRUNCATE = "TRUNCATE";

	private static Logger log = Logger.getLogger(OracleUtil.class);

	private static final int ORA_IN_LIMIT = 1000;

	public static StringBuffer getOraInSql(String columnName, List<String> values)
	{
		StringBuffer sql = new StringBuffer();

		List<String> inList = new ArrayList<String>();
		String inSql = "";
		for (int i = 0; i < values.size(); i++)
		{
			inSql += "'" + values.get(i) + "',";

			if ((i + 1) % ORA_IN_LIMIT == 0 || (i == values.size() - 1))
			{
				inList.add(inSql.substring(0, inSql.length() - 1));
				inSql = "";
			}
		}

		for (int i = 0; i < inList.size(); i++)
		{
			if (i > 0)
			{
				sql.append(" or ");
			}
			sql.append(columnName).append(" in (").append(inList.get(i)).append(") ");
		}

		return sql;
	}

	/**
	 * ͨ��oracle��sqlldr�����������
	 * 
	 * @param srcFile
	 *            ��ԭʼ���ļ�����¼��־����Ҫʹ����ԭʼ�ļ���ȫ·����Ϊ��ʶ��
	 * @param dataFile
	 *            ���������ļ�
	 * @param tableName
	 *            ���ı���
	 * @param columns
	 *            �����ֶ����б�
	 * @param loadMode
	 *            ���ģʽ
	 * @throws Exception
	 */
	public static void load(File srcFile, File dataFile, String tableName, List<String> columns, String loadMode)
			throws Exception
	{
		String errorMessage = "";

		StringBuffer succRows = new StringBuffer();
		StringBuffer errorRows = new StringBuffer();
		StringBuffer errorLog = new StringBuffer();

		String srcFileInfo = "[Դ�ļ�=" + srcFile.getCanonicalPath() + ",��С=" + srcFile.length() + "Byte,ʱ��="
				+ TimeUtil.date2str(srcFile.lastModified()) + "]";
		String dataFileInfo = "[������ļ�=" + dataFile.getCanonicalPath() + ",��С=" + dataFile.length() + "Byte,ʱ��="
				+ TimeUtil.date2str(dataFile.lastModified()) + "]";
		String tableInfo = "[����=" + tableName + "]";

		long startTime = System.currentTimeMillis();

		try
		{
			// #�������#-[��ʼ]-[Դ�ļ�=xx,��С=xxByte,ʱ��=yyyy-MM-dd HH:mm:ss]-[������ļ�=yy,��С=yyByte,ʱ��=yyyy-MM-dd HH:mm:ss]-[����=xx]
			log.info("#�������#-[��ʼ]-" + srcFileInfo + "-" + dataFileInfo + "-" + tableInfo);

			// 1 all files
			File baseDir = Constant.BASE_DIR;
			if (!baseDir.exists())
			{
				baseDir.mkdirs();
			}
			String baseFileName = dataFile.getName();

			File controlFile = new File(Constant.ORACLE_CTL, baseFileName + ".ctl");
			if (!controlFile.getParentFile().exists())
			{
				controlFile.getParentFile().mkdirs();
			}

			File logFile = new File(Constant.ORACLE_LOG, baseFileName + ".log");
			if (!logFile.getParentFile().exists())
			{
				logFile.getParentFile().mkdirs();
			}

			File badFile = new File(Constant.ORACLE_BAD, baseFileName + ".bad");
			if (!badFile.getParentFile().exists())
			{
				badFile.getParentFile().mkdirs();
			}

			File archiveFile = new File(Constant.ORACLE_ARC, baseFileName + ".arc");
			if (!archiveFile.getParentFile().exists())
			{
				archiveFile.getParentFile().mkdirs();
			}

			// 2racle control file
			PrintWriter pw = new PrintWriter(controlFile);
			pw.println(" LOAD DATA");
			pw.println(" CHARACTERSET ZHS16GBK");
			pw.println(" INFILE '" + dataFile.getAbsolutePath() + "'");
			if (StringUtil.isEmpty(loadMode))
			{
				loadMode = LOAD_MODE_INSERT;
			}
			pw.println(loadMode);
			pw.println(" INTO TABLE " + tableName + " FIELDS TERMINATED BY ',' TRAILING NULLCOLS ");
			pw.println(" (");

			for (int i = 0; i < columns.size(); i++)
			{
				String column = columns.get(i);
				pw.print("\t" + column);
				// modify by wuyg 2010-10-29 for td mr
				if ("starttime".equalsIgnoreCase(column) || "start_time".equalsIgnoreCase(column)
						|| "endtime".equalsIgnoreCase(column) || "end_time".equalsIgnoreCase(column)
						|| "timestamp".equalsIgnoreCase(column) || "time_stamp".equalsIgnoreCase(column))
				{
					pw.print(" date 'yyyymmddhh24miss'");
				}
				if (i != columns.size() - 1)
				{
					pw.println(",");
				}
			}

			pw.println("\n )");
			pw.close();

			// 3shell
			String userid = ConfigReader.getProperties("sqlldr.dbuserid");
			String shell = "sqlldr userid="
					+ userid
					+ " control="
					+ controlFile.getCanonicalPath()
					+ " log="
					+ logFile.getCanonicalPath()
					+ " bad= "
					+ badFile.getCanonicalPath()
					+ " skip=1 parallel=true bindsize=20000000 readsize=20000000 errors=999999999 rows=5000 columnarrayrows=10000";

			log.info("#�������#-" + srcFileInfo + "-" + dataFileInfo + "-[���ű�=" + shell + "]");

			// 4run
			String exeResult;
			Process process = Runtime.getRuntime().exec(shell);
			BufferedReader out = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while ((exeResult = out.readLine()) != null)
			{
				if (exeResult.length() > 0)
				{
					log.info("#�������#-" + srcFileInfo + "-" + dataFileInfo + "-[����׼���=" + exeResult + "]");
				}
			}
			while ((exeResult = error.readLine()) != null)
			{
				if (exeResult.length() > 0)
				{
					errorMessage += exeResult;
					log.error("#�������#-" + srcFileInfo + "-" + dataFileInfo + "-[���������=" + exeResult + "]");
				}
			}
			out.close();
			error.close();
			process.waitFor();

			// 5archive
			// dataFile.renameTo(archiveFile);

			// 6parse log file
			if (logFile.exists())
			{
				parseLog(logFile, succRows, errorRows, errorLog);
			}
			errorMessage += errorLog;

			// ��־��ʽ��#�������#-[����]-[Դ�ļ�=xx,��С=xxByte,ʱ��=yyyy-MM-dd HH:mm:ss]-[������ļ�=yy,��С=xxByte,ʱ��=yyyy-MM-dd
			// HH:mm:ss]-[�ɹ�����=x,ʧ������=y]-[��ʱ=n����]
			if (StringUtil.isEmpty(errorMessage))
			{
				log.info("#�������#-[����]-" + srcFileInfo + "-" + dataFileInfo + "-" + tableInfo + "-[�ɹ�����="
						+ (succRows.length() == 0 ? "0" : succRows) + ",ʧ������="
						+ (errorRows.length() == 0 ? "0" : errorRows) + "]-[��ʱ="
						+ (System.currentTimeMillis() - startTime) + "����]");
			} else
			{
				log.error("#�������#-[����]-" + srcFileInfo + "-" + dataFileInfo + "-" + tableInfo + "-[�ɹ�����="
						+ (succRows.length() == 0 ? "0" : succRows) + ",ʧ������="
						+ (errorRows.length() == 0 ? "0" : errorRows) + "]-[��ʱ="
						+ (System.currentTimeMillis() - startTime) + "����]-[������Ϣ=" + errorMessage + "]");
			}

			// 7delete files
			// ɾ��n��ǰ�����ݣ�Ϊ����ÿ������sqlldr֮ǰ�������ļ������ķѽ϶�ʱ�䣬�޶�ֻ��ÿ���0,6,12,18������ļ�ɾ������
			String hour = TimeUtil.nowTime2str("HH");
			if ("00".equals(hour) || "06".equals(hour) || "12".equals(hour) || "18".equals(hour))
			{
				log.info("ɾ�������ļ���ʼ");

				int sqlldrFileDays = 0;
				try
				{
					sqlldrFileDays = Integer.parseInt(ConfigReader.getProperties("sqlldr.fileSavedDays"));
					log.debug("SystemConfig.xml���õ�sqlldr�����ļ��洢ʱ��(sqlldr.fileSavedDays):" + sqlldrFileDays + "��");
				} catch (Exception e)
				{
					log.info("SystemConfig.xmlδ����sqlldr�����ļ��洢ʱ��(sqlldr.fileSavedDays),ϵͳ����Ĭ������:"
							+ Constant.SQLLDR_FILE_SAVED_DAYS + "��");
					sqlldrFileDays = Constant.SQLLDR_FILE_SAVED_DAYS;
				}

//				FileUtil.deleteFilesNdaysAgo(Constant.ORACLE_ARC, sqlldrFileDays);
//				FileUtil.deleteFilesNdaysAgo(Constant.ORACLE_BAD, sqlldrFileDays);
//				FileUtil.deleteFilesNdaysAgo(Constant.ORACLE_CTL, sqlldrFileDays);
//				FileUtil.deleteFilesNdaysAgo(Constant.ORACLE_LOG, sqlldrFileDays);
//				FileUtil.deleteFilesNdaysAgo(Constant.ORACLE_TXT, sqlldrFileDays);

				log.info("ɾ�������ļ�����");
			}
		} catch (Exception e)
		{
			log.error("#�������#-[����]-" + srcFileInfo + "-" + dataFileInfo + "-" + tableInfo + "-[�ɹ�����="
					+ (succRows.length() == 0 ? "0" : succRows) + ",ʧ������="
					+ (errorRows.length() == 0 ? "0" : errorRows) + "]-[��ʱ=" + (System.currentTimeMillis() - startTime)
					+ "����]-[������Ϣ=" + errorMessage + "]");
			throw e;
		}
	}

	// �÷�����Ȼ��������֤δ���µ��°汾���ϳ������ܹ�������⡣
	public static void load(String tableName, List<String> columns, String loadMode, String dataFilePath)
			throws Exception
	{
		File dataFile = new File(dataFilePath);
		load(dataFile, dataFile, tableName, columns, loadMode);
	}

	/**
	 * ����sqlldr��log�ļ�������������ɹ���ʧ�ܵ�����
	 * 
	 * @param logFile
	 * @param succRows
	 * @param errorRows
	 * @param errorLog
	 */
	private static void parseLog(File logFile, StringBuffer succRows, StringBuffer errorRows, StringBuffer errorLog)
	{
		FileReader fr = null;
		BufferedReader bf = null;
		try
		{
			fr = new FileReader(logFile);
			bf = new BufferedReader(fr);
			String line = null;
			while ((line = bf.readLine()) != null)
			{
				// Oracle sqlldr Ӣ����־��¼�ɹ�ʧ������ʱ���ֵ�������ֻ��һ���ɹ���ʱ��log���磺 "1 Row successfully loaded." ����ʱ���磺 "10 Rows
				// successfully loaded. "
				// Oracle sqlldr Ӣ����־log���磺
				// 137 Rows successfully loaded.
				// 0 Rows not loaded due to data errors.

				// Oracle sqlldr ������־log���磺
				// 5 �� ���سɹ���
				// �������ݴ���, 2�� û�м��ء�

				line = line.replaceAll("Rows", "Row");

				int succRowsIndex = line.indexOf("Row successfully loaded");// Ӣ����־
				if (succRowsIndex < 0)
				{
					succRowsIndex = line.indexOf("�� ���سɹ�");// ������־
				}
				if (succRowsIndex >= 0)
				{
					succRows.append(line.substring(0, succRowsIndex).trim());
				}

				int errorRowsIndex = line.indexOf("Row not loaded due to data errors");// Ӣ����־
				if (errorRowsIndex < 0 && line.indexOf("�������ݴ���,") >= 0)
				{
					line = line.replaceAll("�������ݴ���,", "");// ���磺" 2�� û�м��ء�"
					errorRowsIndex = line.indexOf("�� û�м���");// ������־
				}
				if (errorRowsIndex >= 0)
				{
					errorRows.append(line.substring(0, errorRowsIndex).trim());
				}

				if (line.indexOf("ORA-") >= 0 && errorLog.length() == 0)// ֻȡһ�д�����Ϣ��������Ϣ������־����
				{
					errorLog.append(line);
				}
			}
		} catch (Exception e)
		{
			log.error(e.getMessage(), e);
		} finally
		{
			try
			{
				if (bf != null)
				{
					bf.close();
				}
				if (fr != null)
				{
					fr.close();
				}
			} catch (Exception e)
			{
			}
		}
	}

	public static void main(String[] args)
	{
		try
		{
			// File srcFile = new File("c:/test.xml");
			// File dataFile = new File("c:/test_data.txt");
			// String tableName = "pm.f_g_c_cell_hour";
			// List<String> columns = new ArrayList<String>();
			// columns.add("start_time");
			// columns.add("id");
			//
			// load(srcFile, dataFile, tableName, columns, null);

			File logFile = new File("c:/TD-SCDMA_MRS_DATANG_OMCR_905_20121120150000_UtranTxPower.txt.log");
			StringBuffer succRows = new StringBuffer();
			StringBuffer errorRows = new StringBuffer();
			StringBuffer errorLog = new StringBuffer();

			parseLog(logFile, succRows, errorRows, errorLog);

			System.out.println(succRows);
			System.out.println(errorRows);
			System.out.println(errorLog);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
