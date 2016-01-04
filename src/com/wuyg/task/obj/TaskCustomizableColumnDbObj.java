package com.wuyg.task.obj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hz.dict.service.DictionaryService;
import com.hz.dict.service.IDictionaryService;
import com.inspur.common.dictionary.pojo.DictItem;
import com.inspur.common.dictionary.util.DictionaryUtil;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.util.StringUtil;
import com.wuyg.task.TaskConstant;

public class TaskCustomizableColumnDbObj extends BaseDbObj
{
	private long id;// 编号
	private String taskType;// 工单大类
	private String taskSubType;// 工单小类
	private String taskProcessName;// 工单环节
	private String columnId;// input 字段id
	private String columnName;// input 字段名 以及 显示名
	private String selectValues;// select字段的可选值，如果该字段为空，则显示输入框，否则显示选择框
	private String notEmpty;// 不能为空的标示
	private String jsFunction;// js函数

	public TaskCustomizableColumnDbObj()
	{
	}

	public TaskCustomizableColumnDbObj(String taskType, String taskSubType, String taskProcessName, String columnId, String columnName, String notEmpty,
			String selectValues, String jsFunction)
	{
		this.taskType = taskType;
		this.taskSubType = taskSubType;
		this.taskProcessName = taskProcessName;
		this.columnId = columnId;
		this.columnName = columnName;
		this.selectValues = selectValues;
		this.notEmpty = notEmpty;
		this.jsFunction = jsFunction;
	}

	public String getTaskProcessName()
	{
		return taskProcessName;
	}

	public void setTaskProcessName(String taskProcessName)
	{
		this.taskProcessName = taskProcessName;
	}

	public String getJsFunction()
	{
		return jsFunction;
	}

	public void setJsFunction(String jsFunction)
	{
		this.jsFunction = jsFunction;
	}

	public String getNotEmpty()
	{
		return notEmpty;
	}

	public void setNotEmpty(String notEmpty)
	{
		this.notEmpty = notEmpty;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
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

	public String getColumnId()
	{
		return columnId;
	}

	public void setColumnId(String columnId)
	{
		this.columnId = columnId;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}

	public String getSelectValues()
	{
		return selectValues;
	}

	public void setSelectValues(String selectValues)
	{
		this.selectValues = selectValues;
	}

	@Override
	public String findKeyColumnName()
	{
		return "id";
	}

	@Override
	public String findParentKeyColumnName()
	{
		return "subTaskType";
	}

	@Override
	public String findTableName()
	{
		return "task_customizable_columns";
	}

	public String toHtml(String colsAndValuesStr)
	{
		StringBuffer s = new StringBuffer();

		// 1、分解用户已经输入的数据： colsAndValues格式为： key1:value1;key2:value2;key3:value3
		Map<String, String> colsAndValuesMap = new HashMap<String, String>();
		if (!StringUtil.isEmpty(colsAndValuesStr))
		{
			String[] colsAndValues = colsAndValuesStr.split(";");
			for (int i = 0; i < colsAndValues.length; i++)
			{
				String[] colAndValue = colsAndValues[i].split("=");
				if (colAndValue.length == 2)
				{
					colsAndValuesMap.put(colAndValue[0], colAndValue[1]);
				}
			}
		}

		// 2、构造输入框或下拉框
		String columnValue = StringUtil.getNotEmptyStr(colsAndValuesMap.get(columnName));// 该属性的值
		boolean isPlacehoder = TaskConstant.CUST_COL_PLACEHOLDER.equals(columnName);// 是否占位
		boolean isNotEmpty = "true".equalsIgnoreCase(notEmpty);// 能否为空

		String input = "";

		// 2.1、字段名不为空，就需要生成输入框
		if (!StringUtil.isEmpty(columnName))
		{
			input = "<input name=\"" + columnId + "\" id=\"" + columnId + "\" value=\"" + columnValue + "\" ";
			// 如果为占位符，则隐藏掉input框
			input += " type=\"" + (isPlacehoder ? "hidden" : "text") + "\" ";
			// label用于进行不能为空检查是提示使用
			input += " label=\"" + columnName + "\" ";
			// 设置不能为空的css
			input += " " + (isNotEmpty ? "class=\"notEmpty\"" : "");
			// 根据通配符获取预置的函数
			input += getWildcardFunction(selectValues);
			input += "/>";
		}

		if ("#多行#".equals(selectValues))
		{
			input = "<textarea name=\"" + columnId + "\" id=\"" + columnId + "\" label=\"" + columnName + "\" cols=\"22\" rows=\"3\">"
					+ StringUtil.getNotEmptyStr(columnValue) + "</textarea>";
		}

		// 2.2、可选值不为空，则需要生成下拉框
		if (!StringUtil.isEmpty(selectValues) && // 可选值不为空
				!"#多行#".equals(selectValues) && // 不是多行输入框
				getWildcardFunction(selectValues).length() == 0// 不是预置函数
		)
		{
			List<DictItem> items = new ArrayList<DictItem>();

			// 获取预置字典
			List<DictItem> wildcardItems = getWildcardDict(selectValues);

			if (wildcardItems.size() > 1)
			{
				// 字典存在
				items.addAll(wildcardItems);

			} else
			{
				// 字典不存在，加上一个 --请选择-- 的空选项
				items.add(new DictItem(0, "", "--请选择--"));
				String[] values = selectValues.split(",|;|，|；|、");

				for (int j = 0; j < values.length; j++)
				{
					DictItem item = new DictItem(j, values[j], values[j]);
					items.add(item);
				}
			}

			input = DictionaryUtil.getSelectHtml(items, columnId, columnName, columnValue, isNotEmpty ? "notEmpty" : "");
		}

		// 2.3、生成TD

		s.append("	<td width=\"" + TaskConstant.CUST_COLNAME_WIDTH + "\" align=\"right\"><strong>"
				+ (StringUtil.isEmpty(columnName) || TaskConstant.CUST_COL_PLACEHOLDER.equals(columnName) ? "" : columnName + ":") + "</strong></td>");
		s.append("	<td width=\"" + TaskConstant.CUST_COLVAUE_WIDTH + "\" align=\"left\">" + input + "</td>");

		// 2.4、增加函数
		if (!StringUtil.isEmpty(jsFunction))
		{
			s.append("<script>" + jsFunction + (jsFunction.trim().endsWith(")") ? "" : "()") + ";</script>");// 忘了写括号的话自动补全
		}

		return s.toString();
	}

	private List<DictItem> getWildcardDict(String selectValues)
	{
		IDictionaryService dictionaryService = new DictionaryService();

		return dictionaryService.getDictItemsByDictName(selectValues.replaceAll("字典:|字典：", "").trim(), false);
	}

	// 根据通配符获取预置的函数
	private String getWildcardFunction(String selectValues)
	{
		if ("#时间#".equals(selectValues))
		{
			return "onFocus=\"WdatePicker({isShowClear:false,readOnly:true,highLineWeekDay:true})\"";
		}

		return "";
	}

	@Override
	public String getBasePath()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCnName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap<String, String> getProperties()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
