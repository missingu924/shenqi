package com.fuli;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.log4j.Logger;

import com.fuli.searchcondition.VillagerObjSearchCondition;
import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.common.servlet.AbstractBaseServletTemplate;
import com.wuyg.common.util.StringUtil;

public class VillagerServlet extends AbstractBaseServletTemplate
{
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public String getBasePath()
	{
		return domainInstance.getBasePath();
	}

	@Override
	public IBaseDAO getDomainDao()
	{
		return new DefaultBaseDAO(getDomainInstanceClz());
	}

	@Override
	public Class getDomainInstanceClz()
	{
		return com.fuli.obj.VillagerObj.class;
	}
	
	@Override
	public Class getDomainSearchConditionClz()
	{
		return VillagerObjSearchCondition.class;
	}

	// ��ѯ
	public void list4this(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.list(request,response);
	}

	// ���ID�Ƿ���¼��ϵͳ
	public void checkId4this(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.checkId(request,response);
	}

	// ���� or �޸�
	public void addOrModify4this(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.addOrModify(request,response);
	}

	// �޸�ǰ��ѯ���������Ϣ
	public void preModify4this(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.preModify(request,response);
	}

	// ����
	public void detail4this(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.detail(request,response);
	}

	// ɾ��
	public void delete4this(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.delete(request,response);
	}

	// ����
	public void export4this(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.export(request,response);
	}

}
