package com.wuyg.common.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.wuyg.common.obj.PaginationObj;
import com.wuyg.common.util.MyBeanUtils;
import com.wuyg.common.util.MySqlUtil;
import com.wuyg.common.util.StringUtil;
import com.wuyg.common.util.TimeUtil;

public abstract class AbstractBaseDAOTemplate implements IBaseDAO
{
	protected String dbType;
	protected String dbName;

	private Logger logger = Logger.getLogger(getClass());

	/**
	 * ��ȡ�ö����Ӧ�ı���
	 * 
	 * @return
	 */
	public abstract String getTalbName();

	/**
	 * ��ȡ�ö����Ӧ��������ֶ���
	 * 
	 * @return
	 */
	public abstract String getKeyColumnName();

	/**
	 * ��ȡ������������ڸñ��ж�Ӧ���ֶ���
	 * 
	 * @return
	 */
	public abstract String getParentKeyColumnName();

	public int deleteByKeys(List<String> keys)
	{
		String clause = getKeyColumnName() + " in (" + StringUtil.getStringByList(keys, true) + ")";

		return deleteByClause(clause);
	}

	public int deleteByParentKeys(List<String> parentKeys)
	{
		String clause = getParentKeyColumnName() + " in (" + StringUtil.getStringByList(parentKeys, true) + ")";

		return deleteByClause(clause);
	}

	public boolean save(List instances)
	{
		if (instances.size() == 0)
		{
			return true;
		}

		Connection conn = null;
		try
		{
			conn = this.getDbConnection();

			// ��ȡjava db����������ֶ�
			List<PropertyDescriptor> propertyDescriptors = MyBeanUtils.getPropertyDescriptors(instances.get(0), getTableMetaData());
			PropertyUtils.getPropertyDescriptors(instances.get(0));
			List<String> columnList = new ArrayList<String>();
			for (int i = 0; i < propertyDescriptors.size(); i++)
			{
				PropertyDescriptor p = propertyDescriptors.get(i);
				columnList.add(p.getName());
			}

			// ����sql
			String sql = "insert into " + getTalbName() + " (" + StringUtil.getStringByListNoQuotation(columnList) + ") values ("
					+ StringUtil.getQuestionMarkStringByList(columnList) + ")";

			logger.info(getTalbName() + " save sql:" + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);

			// ��������
			for (int i = 0; i < instances.size(); i++)
			{
				Object instance = instances.get(i);

				for (int j = 0; j < propertyDescriptors.size(); j++)
				{
					PropertyDescriptor p = propertyDescriptors.get(j);
					String pName = p.getName();

					Object pValue = MyBeanUtils.getPropertyValue(instance, pName);

					logger.debug(pName + "=" + pValue);

					if (pValue != null)
					{
						if (pValue.getClass().equals(Timestamp.class))
						{
							// ʱ�����ͣ�DB������һ��Ҫ��java.sql.TimeStamp����
							pstmt.setTimestamp(j + 1, TimeUtil.getTimeStamp(pValue.toString()));
						} else
						{
							pstmt.setString(j + 1, pValue.toString());
						}
					} else
					{
						pstmt.setString(j + 1, null);
					}
				}

				pstmt.addBatch();

				if ((i % 500) == 0)
				{
					// ����ִ��
					pstmt.executeBatch();
				}
			}

			// ����ִ��
			pstmt.executeBatch();

			logger.info(getTalbName() + " saved " + instances.size() + " rows");

			return true;
		} catch (Exception ex)
		{
			logger.error(ex.getMessage(), ex);
		} finally
		{
			releaseDbConnection(conn);
		}
		return false;
	}

	public List searchByKeys(Class clz, List<String> keys)
	{
		return this.searchByClause(clz, " " + getKeyColumnName() + " in (" + StringUtil.getStringByList(keys, true) + ") ", null, 0, Integer.MAX_VALUE);
	}

	public Object searchByKey(Class clz, String key)
	{
		List<String> keys = new ArrayList<String>();
		keys.add(key);
		List<Object> instances = this.searchByKeys(clz, keys);
		if (instances.size() > 0)
		{
			return instances.get(0);
		}
		return null;
	}

	public List searchByClause(Class clz, String clause, String orderBy, int offset, int rows)
	{
		// ����sql
		String sql = getSelectSql(clause, orderBy);

		if (MySqlUtil.isMySql(dbName))
		{
			sql += " limit " + offset + "," + rows;
		} else if (MySqlUtil.isOracle(dbName))
		{
			sql = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (" + sql + ") A WHERE ROWNUM <= " + (offset + rows) + ") WHERE RN > " + offset;
		} else if (MySqlUtil.isSqlServer(dbName))
		{
			// sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY
			// _TC_)_RN_,* FROM (SELECT TOP " + (offset + rows) + " 0 _TC_,*
			// FROM " + (sql.replaceFirst("select \\* from", ""))
			// + ") _TT_ ) _TTT_ WHERE _RN_>" + offset;

			return searchBySql4Sql2000(clz, sql, offset, rows);
		}

		return searchBySql(clz, sql);
	}

	private List searchBySql4Sql2000(Class clz, String sql, int offset, int rows)
	{
		List list = new ArrayList();
		Connection conn = null;
		try
		{
			conn = getDbConnection();

			logger.info(getTalbName() + " search sql:" + sql);

			// ���������������ֵ
			ResultSet rst = conn.createStatement().executeQuery(sql);

			List<PropertyDescriptor> propertyDescriptors = MyBeanUtils.getPropertyDescriptors(clz.newInstance(), getTableMetaData());

			while (rst.next())
			{
				if (offset-- > 0)
					continue;
				if (rows-- <= 0)
					break;

				Object instance = getDomainObjFromResultSet(clz, rst, propertyDescriptors);

				list.add(instance);
			}

			logger.info(getTalbName() + " searched " + list.size() + " rows");

			return list;
		} catch (Exception ex)
		{
			logger.error(ex.getMessage(), ex);
		} finally
		{
			releaseDbConnection(conn);
		}
		return list;
	}

	public List searchBySql(Class clz, String sql)
	{
		List list = new ArrayList();
		Connection conn = null;
		try
		{
			conn = getDbConnection();

			logger.info(getTalbName() + " search sql:" + sql);

			// ���������������ֵ
			ResultSet rst = conn.createStatement().executeQuery(sql);

			List<PropertyDescriptor> propertyDescriptors = MyBeanUtils.getPropertyDescriptors(clz.newInstance(), getTableMetaData());
			while (rst.next())
			{
				Object instance = getDomainObjFromResultSet(clz, rst, propertyDescriptors);

				list.add(instance);
			}

			logger.info(getTalbName() + " searched " + list.size() + " rows");

			return list;
		} catch (Exception ex)
		{
			logger.error(ex.getMessage(), ex);
		} finally
		{
			releaseDbConnection(conn);
		}
		return list;
	}

	private Object getDomainObjFromResultSet(Class clz, ResultSet rst, List<PropertyDescriptor> propertyDescriptors) throws InstantiationException, IllegalAccessException,
			SQLException, InvocationTargetException
	{
		Object instance = clz.newInstance();

		for (int i = 0; i < propertyDescriptors.size(); i++)
		{
			PropertyDescriptor p = propertyDescriptors.get(i);
			Class propertyType = p.getPropertyType();
			String pName = p.getName();
			Object pValue = null;

			if (propertyType.equals(Integer.class))
			{
				pValue = rst.getInt(pName);
			} else if (propertyType.equals(Long.class))
			{
				pValue = rst.getLong(pName);
			} else if (propertyType.equals(Float.class))
			{
				pValue = pValue = rst.getFloat(pName);
			} else if (propertyType.equals(Double.class))
			{
				pValue = pValue = rst.getDouble(pName);
			} else if (propertyType.equals(Boolean.class))
			{
				pValue = pValue = rst.getBoolean(pName);
			} else if (propertyType.equals(Timestamp.class))
			{
				pValue = rst.getTimestamp(pName);
			} else if (propertyType.equals(Date.class))
			{
				pValue = rst.getDate(pName);
			} else
			{
				pValue = rst.getObject(pName);
			}

			if (pValue != null)
			{
				logger.debug("set property :" + p.getName() + "=" + pValue);

				logger.debug("value class = " + pValue.getClass());

				BeanUtils.setProperty(instance, p.getName(), pValue);
			}
		}
		return instance;
	}

	// �ɱ�����
	public String getSelectSql(String clause, String orderBy)
	{
		String sql = "select * from " + getTalbName();
		if (!StringUtil.isEmpty(clause))
		{
			sql += " where " + clause;
		}
		if (!StringUtil.isEmpty(orderBy))
		{
			sql += " order by " + orderBy;
		}
		return sql;
	}

	public PaginationObj searchPaginationByClause(Class clz, String where, String orderBy, int pageNo, int pageCount)
	{
		int totalCount = countByClause(where);
		int offset = (pageNo - 1) * pageCount;
		if (pageCount == Integer.MAX_VALUE)
		{
			pageCount = Integer.MAX_VALUE - 1; // �����ѯʱ����int���ֵ
		}
		int rows = pageCount;
		List<Object> dataList = searchByClause(clz, where, orderBy, offset, rows);

		PaginationObj paginationObj = new PaginationObj(dataList, totalCount, pageNo, pageCount);

		return paginationObj;
	}

	public PaginationObj searchPaginationByDomainInstance(BaseDbObj domainInstance, String orderBy, int pageNo, int pageCount)
	{
		try
		{
			String where = " 1=1 ";
			// �ѷǿյĻ�������������
			where += MyBeanUtils.getWhereSqlFromBean(domainInstance, getTableMetaData());

			int totalCount = countByClause(where);
			if (pageNo==0)
			{
				pageNo=1;//��ֹĬ�ϵ�1ҳ����˵�0ҳ
			}
			int offset = (pageNo - 1) * pageCount;
			if (pageCount == Integer.MAX_VALUE)
			{
				pageCount = Integer.MAX_VALUE - 1; // �����ѯʱ����int���ֵ
			}
			int rows = pageCount;

			// Ĭ�ϰ���������������
			if (StringUtil.isEmpty(orderBy))
			{
				orderBy = StringUtil.getNotEmptyStr(domainInstance.findKeyColumnName());
			}

			List<Object> dataList = searchByClause(domainInstance.getClass(), where, orderBy, offset, rows);

			PaginationObj paginationObj = new PaginationObj(dataList, totalCount, pageNo, pageCount);

			return paginationObj;
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}

		return PaginationObj.NULL_PAGE;// �����ؿ�ҳ
	}

	public boolean update(List instances)
	{
		if (instances.size() == 0)
		{
			logger.info("���κ���Ҫ���µĶ���ֱ�ӷ��ء�");
			return true;
		}
		Connection conn = null;
		try
		{
			conn = this.getDbConnection();

			String sql = "update " + getTalbName() + " set ";

			// ֻ���²�Ϊnull���ֶ�
			List<PropertyDescriptor> propertyDescriptors = MyBeanUtils.getNotNullPropertyDescriptors(instances.get(0), getKeyColumnName(), getTableMetaData());

			for (int i = 0; i < propertyDescriptors.size(); i++)
			{
				PropertyDescriptor p = propertyDescriptors.get(i);

				sql += " " + p.getName() + " = ?,";

			}
			if (sql.endsWith(","))
			{
				sql = sql.substring(0, sql.length() - 1);
			}

			sql += " where " + getKeyColumnName() + "=? ";

			logger.info(getTalbName() + " udpate sql:" + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);

			for (int j = 0; j < instances.size(); j++)
			{
				Object instance = instances.get(j);
				Object key = BeanUtils.getProperty(instance, getKeyColumnName());
				if (key == null)
				{
					logger.info("Db ���������Ϊ���޷����£����Ըö���");
					continue;
				}
				// ����ֵ
				for (int i = 0; i < propertyDescriptors.size(); i++)
				{
					PropertyDescriptor p = propertyDescriptors.get(i);
					String pValue = BeanUtils.getProperty(instance, p.getName());
					if (p.getPropertyType().equals(Timestamp.class))
					{
						// ʱ�����ͣ�һ��Ҫ��java.sql.TimeStamp����
						pstmt.setTimestamp(i + 1, TimeUtil.getTimeStamp(pValue));

					} else
					{
						pstmt.setString(i + 1, pValue);
					}
				}

				// ��������ֵ
				pstmt.setString(propertyDescriptors.size() + 1, BeanUtils.getProperty(instance, getKeyColumnName()));

				pstmt.addBatch();

				if ((j % 500) == 0)
				{
					// ����ִ��
					pstmt.executeBatch();
				}
			}

			// ����ִ��
			pstmt.executeBatch();

			logger.info(getTalbName() + " updated " + instances.size() + " rows");

			return true;
		} catch (Exception ex)
		{
			logger.error(ex.getMessage(), ex);
		} finally
		{
			releaseDbConnection(conn);
		}
		return false;
	}

	/**
	 * ��ȡ�ñ��metaData
	 * 
	 * @return
	 */
	public List<String> getTableMetaData()
	{
		List<String> columnList = new ArrayList<String>();

		Connection conn = null;
		try
		{
			conn = this.getDbConnection();
			ResultSetMetaData metaData = conn.createStatement().executeQuery("select * from " + getTalbName()).getMetaData();

			for (int i = 1; i < metaData.getColumnCount() + 1; i++)
			{
				columnList.add(metaData.getColumnName(i));
			}
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		} finally
		{
			releaseDbConnection(conn);
		}
		return columnList;
	}

	/**
	 * �ر����ݿ�����
	 * 
	 * @param conn
	 */
	public void releaseDbConnection(Connection conn)
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			} catch (SQLException ex)
			{
			}
		}
	}

	public long getMaxKeyValue()
	{
		Connection conn = null;
		try
		{
			conn = this.getDbConnection();

			String sql = "select max(" + getKeyColumnName() + ") from " + getTalbName();

			logger.info(getTalbName() + " max key value sql:" + sql);

			long maxValue = -1;
			ResultSet rst = conn.createStatement().executeQuery(sql);
			if (rst.next())
			{
				maxValue = rst.getLong(1);
			}

			logger.info(getTalbName() + " max key value is " + maxValue);

			return maxValue + 1;
		} catch (Exception ex)
		{
			logger.error(ex.getMessage(), ex);
		} finally
		{
			releaseDbConnection(conn);
		}
		return -1;
	}

	public List searchByParentKeys(Class clz, List<String> parentKeys, String orderBy)
	{
		if (StringUtil.isEmpty(orderBy))
		{
			orderBy = getKeyColumnName() + " desc";
		}

		return this.searchByClause(clz, getParentKeyColumnName() + " in (" + StringUtil.getStringByListWithQuotation(parentKeys) + ")", orderBy, 0, Integer.MAX_VALUE);
	}

	public List searchByParentKey(Class clz, String parentKey, String orderBy)
	{
		List<String> parentKeys = new ArrayList<String>();
		parentKeys.add(parentKey);
		return this.searchByParentKeys(clz, parentKeys, orderBy);
	}

	/**
	 * ��ȡ���ݿ�����
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getDbConnection() throws SQLException
	{

		return MySqlUtil.getConnection(dbName);

	}

	public int deleteByClause(String clause)
	{
		if (StringUtil.isEmpty(clause))
		{
			logger.info("the delete clause is null, don't delete.");
			return 0;
		}
		Connection conn = null;
		try
		{
			conn = this.getDbConnection();

			String sql = "delete from " + getTalbName() + " where " + clause;
			logger.info(getTalbName() + " delete sql:" + sql);

			int rows = conn.createStatement().executeUpdate(sql);

			logger.info(getTalbName() + " deleted " + rows + " rows");

			return rows;
		} catch (Exception ex)
		{
			logger.error(ex.getMessage(), ex);
		} finally
		{
			releaseDbConnection(conn);
		}
		return 0;
	}

	public int deleteByKey(String key)
	{
		List<String> keys = new ArrayList<String>();
		keys.add(key);
		return this.deleteByKeys(keys);
	}

	public int deleteByParentKey(String parentKey)
	{
		List<String> parentKeys = new ArrayList<String>();
		parentKeys.add(parentKey);
		return this.deleteByParentKeys(parentKeys);
	}

	public boolean save(Object instance)
	{
		List<Object> instances = new ArrayList<Object>();
		instances.add(instance);
		return this.save(instances);
	}

	public boolean saveOrUpdate(Object instance)
	{
		try
		{
			if (this.searchByKey(instance.getClass(), BeanUtils.getProperty(instance, ((BaseDbObj) instance).findKeyColumnName())) != null)
			{
				return this.update(instance);
			} else
			{
				return this.save(instance);
			}
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	public boolean update(Object instance)
	{
		List<Object> instances = new ArrayList<Object>();
		instances.add(instance);
		return this.update(instances);
	}

	public int countByClause(String where)
	{
		Connection conn = null;
		try
		{
			conn = getDbConnection();

			String sql = "select count(*) from (" + getSelectSql(where, null) + ") t000000";
			// if (where != null && !"".equals(where))
			// {
			// sql += " where " + where;
			// }

			logger.info(getTalbName() + " count sql:" + sql);

			ResultSet rst = conn.createStatement().executeQuery(sql);
			if (rst.next())
			{
				int count = rst.getInt(1);
				logger.info(getTalbName() + " count " + count + " rows");
				return count;
			}
		} catch (Exception ex)
		{
			logger.error(ex.getMessage(), ex);
		} finally
		{
			releaseDbConnection(conn);
		}
		return -1;
	}

	public boolean executeSql(String sql)
	{
		Connection conn = null;
		try
		{
			conn = getDbConnection();
			logger.info("execute sql:" + sql);
			return conn.createStatement().execute(sql);
		} catch (Exception ex)
		{
			logger.error(ex.getMessage(), ex);
		} finally
		{
			releaseDbConnection(conn);
		}
		return false;
	}

	public String getDbType()
	{
		return dbType;
	}

	public void setDbType(String dbType)
	{
		this.dbType = dbType;
	}

	public String getDbName()
	{
		return dbName;
	}

	public void setDbName(String dbName)
	{
		this.dbName = dbName;
	}
}
