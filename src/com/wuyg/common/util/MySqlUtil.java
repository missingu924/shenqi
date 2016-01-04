package com.wuyg.common.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import oracle.jdbc.driver.OracleDriver;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.hz.config.ConfigReader;
import com.hz.util.SystemConstant;

public class MySqlUtil
{
	private static Logger logger = Logger.getLogger(MySqlUtil.class);
	private static Map<String, BasicDataSource> dsMap = new HashMap<String, BasicDataSource>();

	// public static Connection getConnection() throws SQLException
	// {
	// return getConnection(SystemConstant.DEFAULT_DB);
	// }

	public static synchronized Connection getConnection(String dbName) throws SQLException
	{
		BasicDataSource ds = dsMap.get(dbName);

		String driverClassName = ConfigReader.getProperties(dbName + ".dbDriverClassName");
		String url = ConfigReader.getProperties(dbName + ".dbUrl");
		String user = ConfigReader.getProperties(dbName + ".dbUser");
		String password = ConfigReader.getProperties(dbName + ".dbPassword");

		if ((ds == null)
				|| (ds != null && (!ds.getDriverClassName().equals(driverClassName) || !ds.getUrl().equals(url) || !ds.getUsername().equals(user) || !ds.getPassword().equals(
						password))))
		{
			logger.info("Datasouce连接到" + dbName);
			ds = new BasicDataSource();
			ds.setDriverClassName(driverClassName);
			ds.setUrl(url);
			ds.setUsername(user);
			ds.setPassword(password);
			ds.setInitialSize(10);
			ds.setMaxActive(100);
			ds.setMaxIdle(5);
			ds.setMaxWait(5);

			dsMap.put(dbName, ds);
		}

		Connection conn = ds.getConnection();

		// orcle数据库预先执行语句
		if (isOracle(dbName))
		{
			conn.createStatement().execute("alter session set nls_date_format='yyyy-mm-dd hh24:mi:ss'");
			conn.createStatement().execute("alter session set nls_timestamp_format='yyyy-mm-dd hh24:mi:ss'");
			conn.createStatement().execute("alter SESSION set NLS_SORT = SCHINESE_PINYIN_M");// 按拼音排序
		}

		return conn;
	}

	public static void closeConnection(Connection conn)
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			} catch (SQLException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public static boolean isOracle(String dbName)
	{
		return isSomeDb(dbName, SystemConstant.DB_ORACLE);
	}

	public static boolean isMySql(String dbName)
	{
		return isSomeDb(dbName, SystemConstant.DB_MYSQL);
	}

	public static boolean isSqlServer(String dbName)
	{
		return isSomeDb(dbName, SystemConstant.DB_SQLSERVER);
	}

	public static boolean isSomeDb(String dbName, String dbType)
	{
		String driverClassName = ConfigReader.getProperties(dbName + ".dbDriverClassName");
		String url = ConfigReader.getProperties(dbName + ".dbUrl");
		return driverClassName.toUpperCase().contains(dbType.toUpperCase()) || url.toUpperCase().contains(dbType.toUpperCase());
	}

	public static String getLikeClause(String colName, String value)
	{
		if (StringUtil.isEmpty(colName) || StringUtil.isEmpty(value))
		{
			return " 1=1 ";
		} else
		{
			return " (" + colName + " like '%" + value + "%' or " + colName + " like '%" + value + "' or " + colName + " like '" + value + "%' or " + colName + " = '" + value
					+ "') ";
		}
	}

	public static String getCurrentTimeFunction(String dbName)
	{
		dbName = StringUtil.getNotEmptyStr(dbName, SystemConstant.DEFAULT_DB);

		if (isOracle(dbName))
		{
			return "sysdate";
		} else if (isSqlServer(dbName))
		{
			return "getdate()";
		} else if (isMySql(dbName))
		{
			return "now()";
		}

		return null;
	}
	
	public static String getIsNullFunction(String dbName)
	{
		dbName = StringUtil.getNotEmptyStr(dbName, SystemConstant.DEFAULT_DB);

		if (isOracle(dbName))
		{
			return "nvl";
		} else if (isSqlServer(dbName))
		{
			return "isnull";
		} else if (isMySql(dbName))
		{
			return "ifnull";
		}

		return null;
	}

	public static void main(String[] args)
	{
		try
		{
			getConnection("db");
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
