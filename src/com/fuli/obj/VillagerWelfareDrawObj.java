package com.fuli.obj;

import java.sql.Timestamp;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.util.TimeUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

;
public class VillagerWelfareDrawObj extends BaseDbObj
{
	private Long id;
	private String villager_id;
	private String draw_type;
	private String proxy_villager_id;
	private String proxy_villager_name;
	private Timestamp last_modify_time;
	private String last_modify_accout;
	private String draw_comment;
	private Timestamp draw_date;
	private String t1_billsn;
	
	List<VWelfareForDrawDetailObj> selfVWelfareForDrawDetailList=new ArrayList<VWelfareForDrawDetailObj>();//该村民自身可领取福利
	List<VWelfareForDrawDetailObj> bindingVWelfareForDrawDetailList = new ArrayList<VWelfareForDrawDetailObj>();//绑定到该村名下的其他村民的可领取福利

	@Override
	public String findKeyColumnName()
	{
		return "id";
	}

	@Override
	public String findParentKeyColumnName()
	{
		// TODO Auto-generated method stub
		return "villager_id";
	}

	@Override
	public String findTableName()
	{
		return "villager_welfare_draw";
	}

	@Override
	public String getBasePath()
	{
		// TODO Auto-generated method stub
		return "VillagerWelfareDraw";
	}

	@Override
	public String getCnName()
	{
		// TODO Auto-generated method stub
		return "领取福利";
	}

	public LinkedHashMap<String, String> getProperties()
	{
		LinkedHashMap<String, String> pros = new LinkedHashMap<String, String>();

		pros.put("id", "编号");
		pros.put("villager_id", "村民编号");
		pros.put("draw_type", "领取方式");
		pros.put("proxy_villager_id", "代领人编号");
		pros.put("proxy_villager_name", "代领人姓名");
//		pros.put("last_modify_time", "last_modify_time");
//		pros.put("last_modify_accout", "last_modify_accout");
		pros.put("draw_date", "领取日期");
		pros.put("draw_comment", "备注");
		
		return pros;
	}

	public List<VWelfareForDrawDetailObj> getSelfVWelfareForDrawDetailList()
	{
		return selfVWelfareForDrawDetailList;
	}

	public void setSelfVWelfareForDrawDetailList(List<VWelfareForDrawDetailObj> selfVWelfareForDrawDetailList)
	{
		this.selfVWelfareForDrawDetailList = selfVWelfareForDrawDetailList;
	}

	public List<VWelfareForDrawDetailObj> getBindingVWelfareForDrawDetailList()
	{
		return bindingVWelfareForDrawDetailList;
	}

	public void setBindingVWelfareForDrawDetailList(List<VWelfareForDrawDetailObj> bindingVWelfareForDrawDetailList)
	{
		this.bindingVWelfareForDrawDetailList = bindingVWelfareForDrawDetailList;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getVillager_id()
	{
		return villager_id;
	}

	public void setVillager_id(String villager_id)
	{
		this.villager_id = villager_id;
	}

	public String getDraw_type()
	{
		return draw_type;
	}

	public void setDraw_type(String draw_type)
	{
		this.draw_type = draw_type;
	}

	public String getProxy_villager_id()
	{
		return proxy_villager_id;
	}

	public void setProxy_villager_id(String proxy_villager_id)
	{
		this.proxy_villager_id = proxy_villager_id;
	}

	public String getProxy_villager_name()
	{
		return proxy_villager_name;
	}

	public void setProxy_villager_name(String proxy_villager_name)
	{
		this.proxy_villager_name = proxy_villager_name;
	}

	public Timestamp getLast_modify_time()
	{
		return last_modify_time;
	}

	public void setLast_modify_time(Timestamp last_modify_time)
	{
		this.last_modify_time = last_modify_time;
	}

	public String getLast_modify_accout()
	{
		return last_modify_accout;
	}

	public void setLast_modify_accout(String last_modify_accout)
	{
		this.last_modify_accout = last_modify_accout;
	}

	public String getDraw_comment()
	{
		return draw_comment;
	}

	public void setDraw_comment(String draw_comment)
	{
		this.draw_comment = draw_comment;
	}
	
	public String getDraw_date_show()
	{
		return TimeUtil.date2str(draw_date);
	}

	public Timestamp getDraw_date()
	{
		return draw_date;
	}

	public void setDraw_date(Timestamp draw_date)
	{
		this.draw_date = draw_date;
	}

	public String getT1_billsn()
	{
		return t1_billsn;
	}

	public void setT1_billsn(String t1_billsn)
	{
		this.t1_billsn = t1_billsn;
	}
}
