package com.wuyg.common.obj;

import com.hz.auth.obj.AuthUser;
import com.hz.util.SystemConstant;

public abstract class BaseSearchCondition
{
	private Object domainObj;// 业务对象

	private AuthUser user;// 用户登录账号
	private int pageNo = 1;
	private int pageCount = SystemConstant.PAGE_ROWS;

	public AuthUser getUser()
	{
		return user;
	}

	public void setUser(AuthUser user)
	{
		this.user = user;
	}

	public int getPageNo()
	{
		return pageNo;
	}

	public void setPageNo(int pageNo)
	{
		this.pageNo = pageNo;
	}

	public int getPageCount()
	{
		return pageCount;
	}

	public void setPageCount(int pageCount)
	{
		this.pageCount = pageCount;
	}

	public Object getDomainObj()
	{
		return domainObj;
	}

	public void setDomainObj(Object domainObj)
	{
		this.domainObj = domainObj;
	}
}
