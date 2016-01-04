package com.wuyg.dbmodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import com.hz.util.SystemConstant;
import com.wuyg.common.util.MySqlUtil;
import com.wuyg.common.util.StringUtil;
import com.wuyg.dbmodel.obj.ColumnObj;
import com.wuyg.dbmodel.obj.TableObj;

public class DbModelMain
{
	public static String srcBaseDir = "C:\\test\\java\\src\\";
//	public static String basePackage = "com.t1.obj";
	public static String basePackage = "com.fuli.obj";
	public static String[] tables = new String[]
	{ // "product" ,"family", "villager", "villager_welfare",
	// "villager_welfare_draw", "villager_welfare_draw_detail",
			// "welfare_policy", "welfare_policy_detail",
			// "v_family_welfare_detail",
			// "v_family_welfare_draw_detail",
			// "v_villager_welfare_detail",
			// "v_welfare_draw_detail",
			 "v_welfare_draw_stat",
			// "v_welfare_for_draw_detail"
			// "v_welfare_draw_stat_by_drawid"
			//"MasterBillDft", "ListSaleDft",
			//"a_vw_user"
			 };

	public static void main(String[] args)
	{
		Connection conn = null;
		for (int i = 0; i < tables.length; i++)
		{
			try
			{
				String tableName = tables[i].toLowerCase();

				System.err.println("--" + tableName + "\tbegin--");

				TableObj tableObj = new TableObj();
				tableObj.setTableName(tableName);
				tableObj.setJavaPackage(basePackage);
				tableObj.setJavaSrcDir(srcBaseDir);

				conn = MySqlUtil.getConnection(SystemConstant.DEFAULT_DB);
				ResultSet rst = conn.createStatement().executeQuery("select * from " + tableName);

				ResultSetMetaData metaData = rst.getMetaData();

				for (int j = 1; j <= metaData.getColumnCount(); j++)
				{
					ColumnObj col = new ColumnObj();
					col.setColumnName(metaData.getColumnName(j).toLowerCase());
					col.setColumnType(getColumnType(metaData.getColumnType(j)));
					tableObj.addColumn(col);
				}

				generateJavaBeanSrc(tableObj);

				System.err.println("--" + tableName + "\t end--\n");
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				try
				{
					conn.close();
				} catch (SQLException e)
				{
				}
			}
		}

	}

	private static void generateJavaBeanSrc(TableObj tableObj) throws FileNotFoundException
	{
		File srcFile = new File(tableObj.getJavaSrcDir() + "/" + tableObj.getJavaPackage().replace('.', '/') + "/" + StringUtil.toClassName(tableObj.getTableName()) + "Obj.java");
		if (!srcFile.getParentFile().exists())
		{
			srcFile.getParentFile().mkdirs();
		}
		PrintWriter pw = new PrintWriter(srcFile);
		pw.println(tableObj.toJavaSrc());
		pw.close();
	}

	private static String getColumnType(int columnTypeInt)
	{

		if (columnTypeInt == Types.SMALLINT || columnTypeInt == Types.INTEGER || columnTypeInt == Types.BIGINT)
		{
			return "Long";
		} else if (columnTypeInt == Types.DOUBLE || columnTypeInt == Types.FLOAT || columnTypeInt == Types.NUMERIC )
		{
			return "Double";
		} else if (columnTypeInt == Types.VARCHAR || columnTypeInt == Types.CHAR || columnTypeInt == Types.NCHAR || columnTypeInt == Types.NVARCHAR)
		{
			return "String";
		} else if (columnTypeInt == Types.DATE || columnTypeInt == Types.TIMESTAMP || columnTypeInt == Types.TIME)
		{
			return "Timestamp";
		} else if (columnTypeInt == Types.BIT)
		{
			return "Boolean";
		}

		return null;
	}

	// JDBC Type Java Type
	// CHAR String
	// VARCHAR String
	// LONGVARCHAR String
	// NUMERIC java.math.BigDecimal
	// DECIMAL java.math.BigDecimal
	// BIT boolean
	// BOOLEAN boolean
	// TINYINT byte
	// SMALLINT short
	// INTEGER int
	// BIGINT long
	// REAL float
	// FLOAT double
	// DOUBLE double
	// BINARY byte[]
	// VARBINARY byte[]
	// LONGVARBINARY byte[]
	// DATE java.sql.Date
	// TIME java.sql.Time
	// TIMESTAMP java.sql.Timestamp
	// CLOB Clob
	// BLOB Blob
	// ARRAY Array
	// DISTINCT mapping of underlying type
	// STRUCT Struct
	// REF Ref
	// DATALINK java.net.URL[color=red][/color]

}
