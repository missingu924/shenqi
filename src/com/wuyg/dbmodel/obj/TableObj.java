package com.wuyg.dbmodel.obj;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.util.StringUtil;

public class TableObj extends BaseDbObj
{
	private Long id;
	private String tableName;
	private String javaPackage;
	private String javaSrcDir;
	private String cnName;
	private String iconPath;
	private Boolean forDetail = false;
	private Boolean containsOperationCol = false;
	private String keyColumn;
	private String uniqueColumn;
	private List<ColumnObj> columns = new ArrayList<ColumnObj>();

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUniqueColumn()
	{
		return uniqueColumn;
	}

	public void setUniqueColumn(String uniqueColumn)
	{
		this.uniqueColumn = uniqueColumn;
	}

	public Boolean getContainsOperationCol()
	{
		return containsOperationCol;
	}

	public void setContainsOperationCol(Boolean containsOperationCol)
	{
		this.containsOperationCol = containsOperationCol;
	}

	public String getKeyColumn()
	{
		return keyColumn;
	}

	public void setKeyColumn(String keyColumn)
	{
		this.keyColumn = keyColumn;
	}

	public String getJavaPackage()
	{
		return javaPackage;
	}

	public void setJavaPackage(String javaPackage)
	{
		this.javaPackage = javaPackage;
	}

	public String getJavaSrcDir()
	{
		return javaSrcDir;
	}

	public void setJavaSrcDir(String javaSrcDir)
	{
		this.javaSrcDir = javaSrcDir;
	}

	public String getIconPath()
	{
		return iconPath;
	}

	public void setIconPath(String iconPath)
	{
		this.iconPath = iconPath;
	}

	public Boolean getForDetail()
	{
		return forDetail;
	}

	public void setForDetail(Boolean forDetail)
	{
		this.forDetail = forDetail;
	}

	public String getCnName()
	{
		return cnName;
	}

	public void setCnName(String cnName)
	{
		this.cnName = cnName;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public List<ColumnObj> getColumns()
	{
		return columns;
	}

	public void setColumns(List<ColumnObj> columns)
	{
		this.columns = columns;
	}

	public void addColumn(ColumnObj columnObj)
	{
		this.columns.add(columnObj);
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}

	public String toJavaSrc()
	{
		StringBuffer src = new StringBuffer();

		src.append("package " + this.getJavaPackage() + ".obj;\n");

		src.append("import java.sql.Timestamp;\n");
		src.append("import com.wuyg.common.dao.BaseDbObj;\n");
		src.append("import java.util.LinkedHashMap;\n");
		src.append("import com.alibaba.fastjson.JSON;\n");

		src.append("public class " + getTableClassName() + "Obj extends BaseDbObj\n");
		src.append("{\n");
		for (int i = 0; i < columns.size(); i++)
		{
			ColumnObj col = columns.get(i);
			src.append("private " + col.getColumnType() + " " + col.getColumnName() + ";\n");
		}

		src.append("@Override\n");
		src.append("public String findKeyColumnName()\n");
		src.append("{\n");
		src.append("	return \"" + keyColumn + "\";\n");
		src.append("}\n");

		src.append("@Override\n");
		src.append("public String findParentKeyColumnName()\n");
		src.append("{\n");
		src.append("	// TODO Auto-generated method stub\n");
		src.append("	return null;\n");
		src.append("}\n");

		src.append("@Override\n");
		src.append("public String findTableName()\n");
		src.append("{\n");
		src.append("	return \"" + tableName + "\";\n");
		src.append("}\n");

		src.append("@Override\n");
		src.append("public String getBasePath()\n");
		src.append("{\n");
		src.append("	return \"" + getTableClassName() + "\";\n");
		src.append("}\n");

		src.append("@Override\n");
		src.append("public String getCnName()\n");
		src.append("{\n");
		src.append("	return \"" + cnName + "\";\n");
		src.append("}\n");

		// ÖÐÓ¢ÎÄÃûÓ³Éä
		src.append("public LinkedHashMap<String, String> getProperties()\n");
		src.append("{\n");
		src.append("		LinkedHashMap<String, String> pros = new LinkedHashMap<String, String>();\n");
		src.append("\n");
		for (int i = 0; i < columns.size(); i++)
		{
			ColumnObj col = columns.get(i);
			if (!col.getColumnShow() || StringUtil.isEmpty(col.getColumnCnName()))
			{
				src.append("//");
			}
			src.append("		pros.put(\"" + col.getColumnName() + "\", \"" + StringUtil.getNotEmptyStr(col.getColumnCnName(), col.getColumnName()) + "\");\n");
		}
		src.append("		return pros;\n");
		src.append("}\n");

		for (int i = 0; i < columns.size(); i++)
		{
			ColumnObj col = columns.get(i);

			String columnType = col.getColumnType();
			String columnName = col.getColumnName();

			String columnNameUpperFirstChar = StringUtil.upperFirstChar(col.getColumnName());

			src.append("public " + columnType + " get" + columnNameUpperFirstChar + "()\n");
			src.append("{\n");
			src.append("	return " + columnName + ";\n");
			src.append("}\n");

			src.append("public void set" + columnNameUpperFirstChar + "(" + columnType + " " + col.getColumnName() + ")\n");
			src.append("{\n");
			src.append("	this." + columnName + " = " + columnName + ";\n");
			src.append("}\n");
		}

		src.append("@Override\n");
		src.append("public String toString()\n");
		src.append("{\n");
		src.append("	return JSON.toJSONString(this);\n");
		src.append("}\n");

		src.append("}\n");

		return src.toString();
	}

	public List<ColumnObj> getForSearchColumns()
	{
		List<ColumnObj> forSearchColumns = new ArrayList<ColumnObj>();

		for (int i = 0; i < columns.size(); i++)
		{
			if (columns.get(i).getColumnForSearch())
			{
				forSearchColumns.add(columns.get(i));
			}
		}

		return forSearchColumns;
	}

	public List<ColumnObj> getShowColumns()
	{
		List<ColumnObj> forShowColumns = new ArrayList<ColumnObj>();

		for (int i = 0; i < columns.size(); i++)
		{
			if (columns.get(i).getColumnShow())
			{
				forShowColumns.add(columns.get(i));
			}
		}

		return forShowColumns;
	}

	public List<ColumnObj> getNotNullColumns()
	{
		List<ColumnObj> notNullColumns = new ArrayList<ColumnObj>();

		for (int i = 0; i < columns.size(); i++)
		{
			if (columns.get(i).getNotNull())
			{
				notNullColumns.add(columns.get(i));
			}
		}

		return notNullColumns;
	}

	public String getJavaBeanFullName()
	{
		return this.javaPackage + ".obj." + getTableClassName() + "Obj";
	}

	public String getJavaBeanSimpleName()
	{
		return getTableClassName() + "Obj";
	}

	public String getSearchconditionBeanFullName()
	{
		return this.javaPackage + ".searchcondition." + getTableClassName() + "SearchCondition";
	}

	public String getTableClassName()
	{
		return StringUtil.toClassName(tableName);
	}

	@Override
	public String findKeyColumnName()
	{
		// TODO Auto-generated method stub
		return "id";
	}

	@Override
	public String findParentKeyColumnName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findTableName()
	{
		// TODO Auto-generated method stub
		return "shenqi..dbmodel_table";
	}

	@Override
	public String getBasePath()
	{
		// TODO Auto-generated method stub
		return "DbModel";
	}

	@Override
	public LinkedHashMap<String, String> getProperties()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
