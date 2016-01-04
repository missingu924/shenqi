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
	private long id;// ���
	private String taskType;// ��������
	private String taskSubType;// ����С��
	private String taskProcessName;// ��������
	private String columnId;// input �ֶ�id
	private String columnName;// input �ֶ��� �Լ� ��ʾ��
	private String selectValues;// select�ֶεĿ�ѡֵ��������ֶ�Ϊ�գ�����ʾ����򣬷�����ʾѡ���
	private String notEmpty;// ����Ϊ�յı�ʾ
	private String jsFunction;// js����

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

		// 1���ֽ��û��Ѿ���������ݣ� colsAndValues��ʽΪ�� key1:value1;key2:value2;key3:value3
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

		// 2������������������
		String columnValue = StringUtil.getNotEmptyStr(colsAndValuesMap.get(columnName));// �����Ե�ֵ
		boolean isPlacehoder = TaskConstant.CUST_COL_PLACEHOLDER.equals(columnName);// �Ƿ�ռλ
		boolean isNotEmpty = "true".equalsIgnoreCase(notEmpty);// �ܷ�Ϊ��

		String input = "";

		// 2.1���ֶ�����Ϊ�գ�����Ҫ���������
		if (!StringUtil.isEmpty(columnName))
		{
			input = "<input name=\"" + columnId + "\" id=\"" + columnId + "\" value=\"" + columnValue + "\" ";
			// ���Ϊռλ���������ص�input��
			input += " type=\"" + (isPlacehoder ? "hidden" : "text") + "\" ";
			// label���ڽ��в���Ϊ�ռ������ʾʹ��
			input += " label=\"" + columnName + "\" ";
			// ���ò���Ϊ�յ�css
			input += " " + (isNotEmpty ? "class=\"notEmpty\"" : "");
			// ����ͨ�����ȡԤ�õĺ���
			input += getWildcardFunction(selectValues);
			input += "/>";
		}

		if ("#����#".equals(selectValues))
		{
			input = "<textarea name=\"" + columnId + "\" id=\"" + columnId + "\" label=\"" + columnName + "\" cols=\"22\" rows=\"3\">"
					+ StringUtil.getNotEmptyStr(columnValue) + "</textarea>";
		}

		// 2.2����ѡֵ��Ϊ�գ�����Ҫ����������
		if (!StringUtil.isEmpty(selectValues) && // ��ѡֵ��Ϊ��
				!"#����#".equals(selectValues) && // ���Ƕ��������
				getWildcardFunction(selectValues).length() == 0// ����Ԥ�ú���
		)
		{
			List<DictItem> items = new ArrayList<DictItem>();

			// ��ȡԤ���ֵ�
			List<DictItem> wildcardItems = getWildcardDict(selectValues);

			if (wildcardItems.size() > 1)
			{
				// �ֵ����
				items.addAll(wildcardItems);

			} else
			{
				// �ֵ䲻���ڣ�����һ�� --��ѡ��-- �Ŀ�ѡ��
				items.add(new DictItem(0, "", "--��ѡ��--"));
				String[] values = selectValues.split(",|;|��|��|��");

				for (int j = 0; j < values.length; j++)
				{
					DictItem item = new DictItem(j, values[j], values[j]);
					items.add(item);
				}
			}

			input = DictionaryUtil.getSelectHtml(items, columnId, columnName, columnValue, isNotEmpty ? "notEmpty" : "");
		}

		// 2.3������TD

		s.append("	<td width=\"" + TaskConstant.CUST_COLNAME_WIDTH + "\" align=\"right\"><strong>"
				+ (StringUtil.isEmpty(columnName) || TaskConstant.CUST_COL_PLACEHOLDER.equals(columnName) ? "" : columnName + ":") + "</strong></td>");
		s.append("	<td width=\"" + TaskConstant.CUST_COLVAUE_WIDTH + "\" align=\"left\">" + input + "</td>");

		// 2.4�����Ӻ���
		if (!StringUtil.isEmpty(jsFunction))
		{
			s.append("<script>" + jsFunction + (jsFunction.trim().endsWith(")") ? "" : "()") + ";</script>");// ����д���ŵĻ��Զ���ȫ
		}

		return s.toString();
	}

	private List<DictItem> getWildcardDict(String selectValues)
	{
		IDictionaryService dictionaryService = new DictionaryService();

		return dictionaryService.getDictItemsByDictName(selectValues.replaceAll("�ֵ�:|�ֵ䣺", "").trim(), false);
	}

	// ����ͨ�����ȡԤ�õĺ���
	private String getWildcardFunction(String selectValues)
	{
		if ("#ʱ��#".equals(selectValues))
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
