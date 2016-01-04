package com.wuyg.dbmodel.obj;

import java.util.LinkedHashMap;

import com.alibaba.fastjson.JSON;
import com.wuyg.common.dao.BaseDbObj;

public class ColumnObj extends BaseDbObj
{
	private Long id;
	private Long tableId;
	private String columnName;
	private String columnType;
	private Integer columnScale;
	private String columnCnName;
	private String dictName;
	private Boolean columnShow;
	private Boolean columnForSearch;
	private Boolean isKeyColumn;
	private Boolean notNull;
	
	public Long getTableId()
	{
		return tableId;
	}

	public void setTableId(Long tableId)
	{
		this.tableId = tableId;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Boolean getNotNull()
	{
		return notNull;
	}

	public void setNotNull(Boolean notNull)
	{
		this.notNull = notNull;
	}

	public String getDictName()
	{
		return dictName;
	}

	public void setDictName(String dictName)
	{
		this.dictName = dictName;
	}

	public Integer getColumnScale()
	{
		return columnScale;
	}

	public void setColumnScale(Integer columnScale)
	{
		this.columnScale = columnScale;
	}

	public Boolean getIsKeyColumn()
	{
		return isKeyColumn;
	}

	public void setIsKeyColumn(Boolean isKeyColumn)
	{
		this.isKeyColumn = isKeyColumn;
	}

	public Boolean getColumnShow()
	{
		return columnShow;
	}

	public void setColumnShow(Boolean columnShow)
	{
		this.columnShow = columnShow;
	}

	public Boolean getColumnForSearch()
	{
		return columnForSearch;
	}

	public void setColumnForSearch(Boolean columnForSearch)
	{
		this.columnForSearch = columnForSearch;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}

	public String getColumnType()
	{
		return columnType;
	}

	public void setColumnType(String columnType)
	{
		this.columnType = columnType;
	}

	public String getColumnCnName()
	{
		return columnCnName;
	}

	public void setColumnCnName(String columnCnName)
	{
		this.columnCnName = columnCnName;
	}
	
	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
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
		return "tableId";
	}

	@Override
	public String findTableName()
	{
		// TODO Auto-generated method stub
		return "shenqi..dbmodel_column";
	}

	@Override
	public String getBasePath()
	{
		// TODO Auto-generated method stub
		return "DbModel";
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
