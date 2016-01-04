package com.fuli.obj;

import java.sql.Timestamp;
import com.wuyg.common.dao.BaseDbObj;
import java.util.LinkedHashMap;

;
public class VVillagerWelfareDetailObj extends BaseDbObj
{
	private Long villager_id;
	private String id_card;
	private String villager_name;
	private Long welfare_policy_id;
	private String welfare_policy_name;
	private Timestamp welfare_policy_start_time;
	private Timestamp welfare_policy_end_time;
	private Long welfare_policy_detail_id;
	private String product_id;
	private String product_name;
	private Long product_quantity;
	private String product_measuring_unit;
	private Double product_price;
	public Double getProduct_price()
	{
		return product_price;
	}
	public void setProduct_price(Double product_price)
	{
		this.product_price = product_price;
	}

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
		return "v_villager_welfare_detail";
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
		pros.put("id_card", "id_card");
		pros.put("villager_name", "villager_name");
		pros.put("welfare_policy_id", "welfare_policy_id");
		pros.put("welfare_policy_name", "welfare_policy_name");
		pros.put("welfare_policy_start_time", "welfare_policy_start_time");
		pros.put("welfare_policy_end_time", "welfare_policy_end_time");
		pros.put("welfare_policy_detail_id", "welfare_policy_detail_id");
		pros.put("product_id", "product_id");
		pros.put("product_name", "product_name");
		pros.put("product_quantity", "product_quantity");
		pros.put("product_measuring_unit", "product_measuring_unit");
		return pros;
	}

	public Long getVillager_id()
	{
		return villager_id;
	}

	public void setVillager_id(Long villager_id)
	{
		this.villager_id = villager_id;
	}

	public String getId_card()
	{
		return id_card;
	}

	public void setId_card(String id_card)
	{
		this.id_card = id_card;
	}

	public String getVillager_name()
	{
		return villager_name;
	}

	public void setVillager_name(String villager_name)
	{
		this.villager_name = villager_name;
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

	public Timestamp getWelfare_policy_start_time()
	{
		return welfare_policy_start_time;
	}

	public void setWelfare_policy_start_time(Timestamp welfare_policy_start_time)
	{
		this.welfare_policy_start_time = welfare_policy_start_time;
	}

	public Timestamp getWelfare_policy_end_time()
	{
		return welfare_policy_end_time;
	}

	public void setWelfare_policy_end_time(Timestamp welfare_policy_end_time)
	{
		this.welfare_policy_end_time = welfare_policy_end_time;
	}

	public Long getWelfare_policy_detail_id()
	{
		return welfare_policy_detail_id;
	}

	public void setWelfare_policy_detail_id(Long welfare_policy_detail_id)
	{
		this.welfare_policy_detail_id = welfare_policy_detail_id;
	}

	public String getProduct_id()
	{
		return product_id;
	}

	public void setProduct_id(String product_id)
	{
		this.product_id = product_id;
	}

	public String getProduct_name()
	{
		return product_name;
	}

	public void setProduct_name(String product_name)
	{
		this.product_name = product_name;
	}

	public Long getProduct_quantity()
	{
		return product_quantity;
	}

	public void setProduct_quantity(Long product_quantity)
	{
		this.product_quantity = product_quantity;
	}

	public String getProduct_measuring_unit()
	{
		return product_measuring_unit;
	}

	public void setProduct_measuring_unit(String product_measuring_unit)
	{
		this.product_measuring_unit = product_measuring_unit;
	}
}
