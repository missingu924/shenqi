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
//				TABLE_CAT String => ����𣨿�Ϊ null��
//				TABLE_SCHEM String => ��ģʽ����Ϊ null��
//				TABLE_NAME String => ������
//				TABLE_TYPE String => ������
//				REMARKS String => ��Ľ�����ע��
//				TYPE_CAT String => ���͵���𣨿�Ϊ null��
//				TYPE_SCHEM String => ����ģʽ����Ϊ null��
//				TYPE_NAME String => �������ƣ���Ϊ null��
//				SELF_REFERENCING_COL_NAME String => �����ͱ��ָ�� "identifier" �е����ƣ���Ϊ null��
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
