package com.fuli.obj;

import java.sql.Timestamp;
import com.wuyg.common.dao.BaseDbObj;
import java.util.LinkedHashMap;

;
public class VVillagerWelfareDrawStatObj extends BaseDbObj
{
	private String villager_id;
	private Long welfare_policy_id;
	private String welfare_policy_name;
	private String inventory_id;
	private String inventory_name;
	private String inventory_measuring_unit;
	private Long inventory_quantity_drawed;

	@Override
	public String findKeyColumnName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findParentKeyColumnName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findTableName()
	{
		return "v_villager_welfare_draw_stat";
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
		return null;
	}

	public LinkedHashMap<String, String> getProperties()
	{
		LinkedHashMap<String, String> pros = new LinkedHashMap<String, String>();

		pros.put("villager_id", "villager_id");
		pros.put("welfare_policy_id", "welfare_policy_id");
		pros.put("welfare_policy_name", "welfare_policy_name");
		pros.put("inventory_id", "inventory_id");
		pros.put("inventory_name", "inventory_name");
		pros.put("inventory_measuring_unit", "inventory_measuring_unit");
		pros.put("inventory_quantity_drawed", "inventory_quantity_drawed");
		return pros;
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

	public String getWelfare_policy_name()
	{
		return welfare_policy_name;
	}

	public void setWelfare_policy_name(String welfare_policy_name)
	{
		this.welfare_policy_name = welfare_policy_name;
	}

	public String getInventory_id()
	{
		return inventory_id;
	}

	public void setInventory_id(String inventory_id)
	{
		this.inventory_id = inventory_id;
	}

	public String getInventory_name()
	{
		return inventory_name;
	}

	public void setInventory_name(String inventory_name)
	{
		this.inventory_name = inventory_name;
	}

	public String getInventory_measuring_unit()
	{
		return inventory_measuring_unit;
	}

	public void setInventory_measuring_unit(String inventory_measuring_unit)
	{
		this.inventory_measuring_unit = inventory_measuring_unit;
	}

	public Long getInventory_quantity_drawed()
	{
		return inventory_quantity_drawed;
	}

	public void setInventory_quantity_drawed(Long inventory_quantity_drawed)
	{
		this.inventory_quantity_drawed = inventory_quantity_drawed;
	}
}
