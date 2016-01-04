package com.wuyg.task.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuyg.common.util.StringUtil;
import com.wuyg.task.TaskConstant;

public class TaskCustomizableColumns
{
	private String taskType;
	private String taskSubType;
	private String taskProcessName;
	private List<TaskCustomizableColumnDbObj> columns = new ArrayList<TaskCustomizableColumnDbObj>();

	public String getTaskProcessName()
	{
		return taskProcessName;
	}

	public void setTaskProcessName(String taskProcessName)
	{
		this.taskProcessName = taskProcessName;
	}

	public String getTaskType()
	{
		return taskType;
	}

	public void setTaskType(String taskType)
	{
		this.taskType = taskType;
	}

	public String getTaskSubType()
	{
		return taskSubType;
	}

	public void setTaskSubType(String taskSubType)
	{
		this.taskSubType = taskSubType;
	}

	public List<TaskCustomizableColumnDbObj> getColumns()
	{
		return columns;
	}

	public void setColumns(List<TaskCustomizableColumnDbObj> columns)
	{
		this.columns = columns;
	}

	public void addColumn(TaskCustomizableColumnDbObj column)
	{
		this.columns.add(column);
	}

	public String getColumnNameByColumnId(String columnId)
	{
		if (StringUtil.isEmpty(columnId))
		{
			return "";
		}
		for (int i = 0; i < columns.size(); i++)
		{
			if (columns.get(i).getColumnId().equals(columnId))
			{
				return columns.get(i).getColumnName();
			}
		}
		return "";
	}

	public String toHtml(String colsAndValuesStr, boolean forInput)
	{
		StringBuffer s = new StringBuffer("<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" >");

		// 用于编辑工单
		if (forInput)
		{
			for (int i = 0; i < this.columns.size(); i++)
			{
				TaskCustomizableColumnDbObj column1 = columns.get(i);
				TaskCustomizableColumnDbObj column2 = new TaskCustomizableColumnDbObj();
				if (i < columns.size() - 1)
				{
					column2 = columns.get(i + 1);
				}

				s.append("<tr class=\"" + (i % 4 == 0 ? "list_table_tr0" : "list_table_tr2") + "\">");
				s.append(column1.toHtml(colsAndValuesStr));
				s.append(column2.toHtml(colsAndValuesStr));
				s.append("</tr>");

				i++;
			}
		}
		// 用于展现工单详情
		else
		{
			if (!StringUtil.isEmpty(colsAndValuesStr))
			{
				String[] colsAndValues = colsAndValuesStr.split(";");
				for (int i = 0; i < colsAndValues.length; i = i + 2)
				{
					s.append("<tr class=\"" + (i % 4 == 0 ? "list_table_tr0" : "list_table_tr2") + "\">");
					s.append(getDetailTd(colsAndValues[i]));
					if (i < colsAndValues.length - 1)
					{
						s.append(getDetailTd(colsAndValues[i + 1]));
					} else
					{
						s.append(getDetailTd(TaskConstant.CUST_COL_PLACEHOLDER));// 空单元格
					}
					s.append("</tr>");
				}
			}
		}

		s.append("</table>");

		return s.toString();
	}

	private String getDetailTd(String colAndValueStr)
	{
		String td = "";
		String[] colAndValue = colAndValueStr.split("=");

		String colName = colAndValue.length >= 1 ? colAndValue[0] : "";
		String colValue = colAndValue.length >= 2 ? colAndValue[1] : "";
		
		// 占位的字段不显示
		colName = colName.replaceFirst((TaskConstant.CUST_COL_PLACEHOLDER), "");
		
		td += "	<td width=\"" + TaskConstant.CUST_COLNAME_WIDTH + "\" align=\"right\"><strong>"
				+ (StringUtil.isEmpty(colName)?"":(colName+":")) + "</strong></td>";
		td += "	<td id=\"" + colName + "\" width=\"" + TaskConstant.CUST_COLVAUE_WIDTH + "\" align=\"left\">" + StringUtil.getNotEmptyStr(colValue) + "</td>";

		return td;
	}
}
