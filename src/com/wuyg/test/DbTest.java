package com.wuyg.test;

import java.sql.Connection;
import java.sql.ResultSet;

import com.hz.util.SystemConstant;
import com.wuyg.common.util.MySqlUtil;

public class DbTest
{
	public static void main(String[] args)
	{
		try
		{
			Connection connection = null;

			connection = MySqlUtil.getConnection(SystemConstant.DEFAULT_DB);

			ResultSet rst = connection.getMetaData().getTables(connection.getCatalog(), "dbo", null, new String[]
			{ "TABLE", "VIEW" });

			while (rst.next())
			{
//				TABLE_CAT String => 表类别（可为 null）
//				TABLE_SCHEM String => 表模式（可为 null）
//				TABLE_NAME String => 表名称
//				TABLE_TYPE String => 表类型
//				REMARKS String => 表的解释性注释
//				TYPE_CAT String => 类型的类别（可为 null）
//				TYPE_SCHEM String => 类型模式（可为 null）
//				TYPE_NAME String => 类型名称（可为 null）
//				SELF_REFERENCING_COL_NAME String => 有类型表的指定 "identifier" 列的名称（可为 null）
//				REF_GENERATION String

				System.out.println(rst.getString("TABLE_CAT")+"\t"+rst.getString("TABLE_SCHEM")+"\t"+rst.getString("TABLE_NAME")+"\t"+rst.getString("TABLE_TYPE"));
			}

			connection.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
