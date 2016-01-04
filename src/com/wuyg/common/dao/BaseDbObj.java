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
	 * ��ȡ�ö����Ӧ�ı���
	 * 
	 * @return
	 */
	public abstract String findTableName();

	/**
	 * ��ȡ�ö����Ӧ��������ֶ���
	 * 
	 * @return
	 */
	public abstract String findKeyColumnName();

	/**
	 * ��ȡ������������ڸñ��ж�Ӧ���ֶ���
	 * 
	 * @return
	 */
	public abstract String findParentKeyColumnName();
	
	/**
	 * ��ȡ������������ڸñ��ж�Ӧ���ֶ���
	 * 
	 * @return
	 */
	public String findDefaultOrderBy()
	{
		return StringUtil.getNotEmptyStr(findKeyColumnName());
	}
	
	/**
	 * ��ȡĿ¼��
	 * 
	 * @return
	 */
	public abstract String getBasePath();
	
	/**
	 * ��ȡ�����������
	 * 
	 * @return
	 */
	public abstract String getCnName();
	
	/**
	 * ��ȡ����excel�ǵ����������ֶζ�Ӧ��ϵ
	 * @return
	 */
	public abstract LinkedHashMap<String, String> getProperties();
	
	/**
	 * ���ݱ��������ȡʵ�����������ֵ
	 * 
	 * @return
	 * @throws Exception
	 */
	public long getKeyValue() throws Exception
	{
		return Long.parseLong(StringUtil.getNotEmptyStr(BeanUtils.getProperty(this, findKeyColumnName()), "-1")) ;
	}
	
	/**
	 * ���ݱ����������ʵ�����������ֵ
	 * 
	 * @return
	 * @throws Exception
	 */
	public void setId(long value) throws Exception
	{
		BeanUtils.setProperty(this, findKeyColumnName(),value);
	}
	
	/**
	 * ��ȡ����������
	 * @return
	 * @throws Exception
	 */
	public String getKeyCnName() throws Exception
	{
		return getProperties().get(findKeyColumnName());
	}
	
	/**
	 * ���ݶ������Ե�Ӣ������ȡ������
	 * @param key
	 * @return
	 */
	public String getPropertyCnName(String cnName)
	{
		return StringUtil.getNotEmptyStr(getProperties().get(cnName));
	}
}
