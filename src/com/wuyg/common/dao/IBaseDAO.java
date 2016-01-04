package com.wuyg.common.dao;

import java.util.List;

import com.wuyg.common.obj.PaginationObj;

public interface IBaseDAO
{
	/**
	 * ִ���ض���sql
	 * 
	 * @param sql
	 * @return
	 */
	public boolean executeSql(String sql);

	/**
	 * ��ȡ�������������ֵ
	 * 
	 * @return
	 */
	public long getMaxKeyValue();

	/**
	 * ����������ѯһ������
	 * 
	 * @param keys
	 * @return
	 */
	public List searchByKeys(Class clz, List<String> keys);

	/**
	 * ����������ѯһ������
	 * 
	 * @param keys
	 * @return
	 */
	public Object searchByKey(Class clz, String key);

	/**
	 * ���ݸ�������ѯһ������
	 * 
	 * @param keys
	 * @return
	 */
	public List searchByParentKeys(Class clz, List<String> parentKeys, String orderBy);

	/**
	 * ���ݸ�������ѯһ������
	 * 
	 * @param keys
	 * @return
	 */
	public List searchByParentKey(Class clz, String parentKey, String orderBy);

	/**
	 * �����ݿ��в�ѯ��������������
	 * 
	 * @param orderBy
	 * @param where
	 * @param offset
	 * @param rows
	 * @return
	 */
	public List searchByClause(Class clz, String where, String orderBy, int offset, int rows);
	
	/**
	 * �����ⲿ�����sql�����ݿ��в�ѯ����
	 * 
	 * @param orderBy
	 * @param sql
	 * @return
	 */
	public List searchBySql(Class clz, String sql);
	
	/**
	 * ��ҳ�����ݿ��в�ѯ��������������
	 * 
	 * @param orderBy
	 * @param where
	 * @param pageNo
	 * @param pageCount
	 * @return
	 */
	public PaginationObj searchPaginationByClause(Class clz, String where, String orderBy, int pageNo, int pageCount);

	/**
	 * ���������ʵ���е�����ֵ��ҳ�����ݿ��в�ѯ��������������
	 * 
	 * @param orderBy
	 * @param where
	 * @param pageNo
	 * @param pageCount
	 * @return
	 */
	public PaginationObj searchPaginationByDomainInstance(BaseDbObj domainInstance, String orderBy, int pageNo, int pageCount);

	/**
	 * ���������������ݿ��м�¼��
	 * 
	 * @param where
	 * @return
	 */
	public int countByClause(String where);

	/**
	 * ��һ�����ʵ���������
	 * 
	 * @param instances
	 * @return
	 */
	public boolean save(List instances);

	/**
	 * ������ʵ���������
	 * 
	 * @param instances
	 * @return
	 */
	public boolean save(Object instance);
	
	/**
	 * ������ʵ������������£����µ������Ǹ��������ܹ���ѯ���ö���
	 * 
	 * @param instances
	 * @return
	 */
	public boolean saveOrUpdate(Object instance);

	/**
	 * ������������������һ�����ʵ�������ݣ�ֻҪʵ���е�ֵ����null���ֶζ�������
	 * 
	 * @param instance
	 * @return
	 */
	public boolean update(List instances);

	/**
	 * �����������������ж���ʵ�������ݣ�ֻҪʵ���е�ֵ����null���ֶζ�������
	 * 
	 * @param instance
	 * @return
	 */
	public boolean update(Object instance);

	/**
	 * ����һ�����ʵ��������ɾ�����ݿ��ж�Ӧ������
	 * 
	 * @param keys
	 * @return ɾ������������
	 */
	public int deleteByKeys(List<String> keys);

	/**
	 * ���ݶ���ʵ��������ɾ�����ݿ��ж�Ӧ������
	 * 
	 * @param key
	 * @return ɾ������������
	 */
	public int deleteByKey(String key);

	/**
	 * ����һ�����ʵ���ĸ����������ɾ�������ж�Ӧ������
	 * 
	 * @param parentKeys
	 * @return ɾ������������
	 */
	public int deleteByParentKeys(List<String> parentKeys);

	/**
	 * ���ݶ���ʵ���ĸ����������ɾ�������ж�Ӧ������
	 * 
	 * @param parentKey
	 * @return ɾ������������
	 */
	public int deleteByParentKey(String parentKey);
	
	/**
	 * ��������ɾ������
	 * @param clause
	 * @return ɾ������������
	 */
	public int deleteByClause(String clause);
	
	/**
	 * ���ݲ�ѯ������ѯ����
	 * @param condition
	 * @return
	 */
	public PaginationObj searchPaginationByCondition(Object condition);
	
	/**
	 * ��ȡ����ʵ�����Ӧ�����ݿ���
	 * @return
	 */
	public String getDbName();
	
	/**
	 * ��ȡ�ñ��metaData
	 * 
	 * @return
	 */
	public List<String> getTableMetaData();
}
