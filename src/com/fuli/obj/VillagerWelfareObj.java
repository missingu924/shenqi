package com.fuli.obj;

import java.sql.Timestamp;
import com.wuyg.common.dao.BaseDbObj;
import java.util.LinkedHashMap;

;
public class VillagerWelfareObj extends BaseDbObj
{
	private Long id;
	private String villager_id;
	private Long welfare_policy_id;
	private String last_modify_account;
	private Timestamp last_modify_time;

	@Override
	public String findKeyColumnName()
	{
		return "id";
	}

	@Override
	public String findParentKeyColumnName()
	{
		return "villager_id";
	}

	@Override
	public String findTableName()
	{
		return "villager_welfare";
	}

	@Override
	public String getBasePath()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCnName()
	{
		// TODO Auto-generated method stub
		return "村名-福利对应关系";
	}

	public LinkedHashMap<String, String> getProperties()
	{
		LinkedHashMap<String, String> pros = new LinkedHashMap<String, String>();

		pros.put("villager_id", "villager_id");
		pros.put("welfare_policy_id", "welfare_policy_id");
		pros.put("last_modify_account", "last_modify_account");
		pros.put("last_modify_time", "last_modify_time");
		return pros;
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

	public Long getWelfare_policy_id()
	{
		return welfare_policy_id;
	}

	public void setWelfare_policy_id(Long welfare_policy_id)
	{
		this.welfare_policy_id = welfare_policy_id;
	}

	public String getLast_modify_account()
	{
		return last_modify_account;
	}

	public void setLast_modify_account(String last_modify_account)
	{
		this.last_modify_account = last_modify_account;
	}

	public Timestamp getLast_modify_time()
	{
		return last_modify_time;
	}

	public void setLast_modify_time(Timestamp last_modify_time)
	{
		this.last_modify_time = last_modify_time;
	}
}
