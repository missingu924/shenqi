package com.fuli;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.log4j.Logger;

import com.fuli.obj.VWelfareDrawStatObj;
import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.common.servlet.AbstractBaseServletTemplate;
import com.wuyg.common.util.StringUtil;

public class VWelfareDrawStatServlet extends AbstractBaseServletTemplate
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
		return com.fuli.obj.VWelfareDrawStatObj.class;
	}

	@Override
	public Class getDomainSearchConditionClz()
	{
		return com.fuli.searchcondition.VWelfareDrawStatObjSearchCondition.class;
	}

	// ��ѯ
	public void list4this(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		VWelfareDrawStatObj o = (VWelfareDrawStatObj)domainInstance;
		o.setDrawed_time_start(request.getParameter("drawed_time_start"));
		o.setDrawed_time_end(request.getParameter("drawed_time_end"));
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
	private void preModify4this(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.preModify(request,response);
	}

	// ����
	public void detail4this(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.detail(request,response);
	}

	// ɾ��
	protected void delete4this(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.delete(request,response);
	}

	// ����
	public void export4this(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.export(request,response);
	}

}