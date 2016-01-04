package com.wuyg.common.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import org.apache.commons.beanutils.BeanUtils;

import com.wuyg.common.util.StringUtil;

/**
 * 
 * 
 * @author wuyugang
 *
 */
public abstract class BaseDbObj
{
	/**
	 * 获取该对象对应的表名
	 * 
	 * @return
	 */
	public abstract String findTableName();

	/**
	 * 获取该对象对应表的主键字段名
	 * 
	 * @return
	 */
	public abstract String findKeyColumnName();

	/**
	 * 获取父对象的主键在该表中对应的字段名
	 * 
	 * @return
	 */
	public abstract String findParentKeyColumnName();
	
	/**
	 * 获取父对象的主键在该表中对应的字段名
	 * 
	 * @return
	 */
	public String findDefaultOrderBy()
	{
		return StringUtil.getNotEmptyStr(findKeyColumnName());
	}
	
	/**
	 * 获取目录名
	 * 
	 * @return
	 */
	public abstract String getBasePath();
	
	/**
	 * 获取该类的中文名
	 * 
	 * @return
	 */
	public abstract String getCnName();
	
	/**
	 * 获取导出excel是的列明及其字段对应关系
	 * @return
	 */
	public abstract LinkedHashMap<String, String> getProperties();
	
	/**
	 * 根据表的主键获取实例对象的主键值
	 * 
	 * @return
	 * @throws Exception
	 */
	public long getKeyValue() throws Exception
	{
		return Long.parseLong(StringUtil.getNotEmptyStr(BeanUtils.getProperty(this, findKeyColumnName()), "-1")) ;
	}
	
	/**
	 * 根据表的主键设置实例对象的主键值
	 * 
	 * @return
	 * @throws Exception
	 */
	public void setId(long value) throws Exception
	{
		BeanUtils.setProperty(this, findKeyColumnName(),value);
	}
	
	/**
	 * 获取主键中文名
	 * @return
	 * @throws Exception
	 */
	public String getKeyCnName() throws Exception
	{
		return getProperties().get(findKeyColumnName());
	}
	
	/**
	 * 根据对象属性的英文名获取中文名
	 * @param key
	 * @return
	 */
	public String getPropertyCnName(String cnName)
	{
		return StringUtil.getNotEmptyStr(getProperties().get(cnName));
	}
}
