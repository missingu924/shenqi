package com.wuyg.common.dao;

import com.hz.util.SystemConstant;
import com.wuyg.common.obj.PaginationObj;

public class DefaultBaseDAO extends AbstractBaseDAOTemplate
{
	private BaseDbObj baseDbObject;

	public DefaultBaseDAO(BaseDbObj baseDbObject)
	{
		this(baseDbObject, SystemConstant.DEFAULT_DB);// 默认连接SystemConfig.xml里面的db数据库
	}

	public DefaultBaseDAO(BaseDbObj baseDbObject, String dbName)
	{
		this.baseDbObject = baseDbObject;
		this.dbName = dbName;
	}

	public DefaultBaseDAO(Class clz)
	{
		this(clz, SystemConstant.DEFAULT_DB);
	}

	public DefaultBaseDAO(Class clz, String dbName)
	{
		try
		{
			this.baseDbObject = (BaseDbObj) clz.newInstance();
			this.dbName = dbName;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public PaginationObj searchPaginationByCondition(Object condition)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKeyColumnName()
	{
		return baseDbObject.findKeyColumnName();
	}

	@Override
	public String getParentKeyColumnName()
	{
		return baseDbObject.findParentKeyColumnName();
	}

	@Override
	public String getTalbName()
	{
		return baseDbObject.findTableName();
	}
	
	@Override
	public String getDbType()
	{
		return dbType;
	}

	@Override
	public String getDbName()
	{
		return dbName;
	}

	
}
