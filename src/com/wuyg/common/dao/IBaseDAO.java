package com.wuyg.common.dao;

import java.util.List;

import com.wuyg.common.obj.PaginationObj;

public interface IBaseDAO
{
	/**
	 * 执行特定的sql
	 * 
	 * @param sql
	 * @return
	 */
	public boolean executeSql(String sql);

	/**
	 * 获取表中主键的最大值
	 * 
	 * @return
	 */
	public long getMaxKeyValue();

	/**
	 * 根据主键查询一组数据
	 * 
	 * @param keys
	 * @return
	 */
	public List searchByKeys(Class clz, List<String> keys);

	/**
	 * 根据主键查询一条数据
	 * 
	 * @param keys
	 * @return
	 */
	public Object searchByKey(Class clz, String key);

	/**
	 * 根据父主键查询一组数据
	 * 
	 * @param keys
	 * @return
	 */
	public List searchByParentKeys(Class clz, List<String> parentKeys, String orderBy);

	/**
	 * 根据父主键查询一组数据
	 * 
	 * @param keys
	 * @return
	 */
	public List searchByParentKey(Class clz, String parentKey, String orderBy);

	/**
	 * 从数据库中查询符合条件的数据
	 * 
	 * @param orderBy
	 * @param where
	 * @param offset
	 * @param rows
	 * @return
	 */
	public List searchByClause(Class clz, String where, String orderBy, int offset, int rows);
	
	/**
	 * 根据外部构造的sql从数据库中查询数据
	 * 
	 * @param orderBy
	 * @param sql
	 * @return
	 */
	public List searchBySql(Class clz, String sql);
	
	/**
	 * 分页从数据库中查询符合条件的数据
	 * 
	 * @param orderBy
	 * @param where
	 * @param pageNo
	 * @param pageCount
	 * @return
	 */
	public PaginationObj searchPaginationByClause(Class clz, String where, String orderBy, int pageNo, int pageCount);

	/**
	 * 根据域对象实例中的属性值分页从数据库中查询符合条件的数据
	 * 
	 * @param orderBy
	 * @param where
	 * @param pageNo
	 * @param pageCount
	 * @return
	 */
	public PaginationObj searchPaginationByDomainInstance(BaseDbObj domainInstance, String orderBy, int pageNo, int pageCount);

	/**
	 * 根据条件计算数据库中记录数
	 * 
	 * @param where
	 * @return
	 */
	public int countByClause(String where);

	/**
	 * 将一组对象实例保存入库
	 * 
	 * @param instances
	 * @return
	 */
	public boolean save(List instances);

	/**
	 * 将对象实例保存入库
	 * 
	 * @param instances
	 * @return
	 */
	public boolean save(Object instance);
	
	/**
	 * 将对象实例保存入库或更新，更新的条件是根据主键能够查询到该对象
	 * 
	 * @param instances
	 * @return
	 */
	public boolean saveOrUpdate(Object instance);

	/**
	 * 根据主键更新数据中一组对象实例的数据，只要实例中的值不是null的字段都做更新
	 * 
	 * @param instance
	 * @return
	 */
	public boolean update(List instances);

	/**
	 * 根据主键更新数据中对象实例的数据，只要实例中的值不是null的字段都做更新
	 * 
	 * @param instance
	 * @return
	 */
	public boolean update(Object instance);

	/**
	 * 根据一组对象实例的主键删除数据库中对应的数据
	 * 
	 * @param keys
	 * @return 删除的数据条数
	 */
	public int deleteByKeys(List<String> keys);

	/**
	 * 根据对象实例的主键删除数据库中对应的数据
	 * 
	 * @param key
	 * @return 删除的数据条数
	 */
	public int deleteByKey(String key);

	/**
	 * 根据一组对象实例的父对象的主键删除数据中对应的数据
	 * 
	 * @param parentKeys
	 * @return 删除的数据条数
	 */
	public int deleteByParentKeys(List<String> parentKeys);

	/**
	 * 根据对象实例的父对象的主键删除数据中对应的数据
	 * 
	 * @param parentKey
	 * @return 删除的数据条数
	 */
	public int deleteByParentKey(String parentKey);
	
	/**
	 * 根据条件删除数据
	 * @param clause
	 * @return 删除的数据条数
	 */
	public int deleteByClause(String clause);
	
	/**
	 * 根据查询条件查询数据
	 * @param condition
	 * @return
	 */
	public PaginationObj searchPaginationByCondition(Object condition);
	
	/**
	 * 获取具体实现类对应的数据库名
	 * @return
	 */
	public String getDbName();
	
	/**
	 * 获取该表的metaData
	 * 
	 * @return
	 */
	public List<String> getTableMetaData();
}
