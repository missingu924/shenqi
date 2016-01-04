package com.fuli.obj;

import java.sql.Timestamp;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.util.TimeUtil;

import java.util.LinkedHashMap;

;
public class VWelfareForDrawDetailObj extends BaseDbObj
{
	private Long welfare_policy_detail_id;
	private Long villager_id;
	private String villager_name;
	private String id_card;
	private Long villager_welfare_id;
	private Long welfare_policy_id;
	private String welfare_policy_name;
	private Timestamp welfare_policy_start_time;
	private Timestamp welfare_policy_end_time;
	private String product_id;
	private String product_name;
	private String product_spec;
	private String product_measuring_unit;
	private Long product_quantity;
	private Long product_quantity_drawed;
	private Long product_quantity_remainder;
	private Double product_price;

	private String welfare_draw_status;// 供查询使用

	public static final String WELFARE_DRAW_STATUS_DRAWD_ALL = "已领完";
	public static final String WELFARE_DRAW_STATUS_DRAWD_PART = "部分领取";
	public static final String WELFARE_DRAW_STATUS_NOT_DRAWD = "未领取";

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
		return "welfare_policy_detail_id";
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
		return "v_welfare_for_draw_detail";
	}

	@Override
	public String getBasePath()
	{
		// TODO Auto-generated method stub
		return "VWelfareForDrawDetail";
	}

	@Override
	public String getCnName()
	{
		// TODO Auto-generated method stub
		return "福利领取明细";
	}

	@Override
	public String findDefaultOrderBy()
	{
		return "villager_name,welfare_policy_start_time desc,product_id";
	}

	public String getWelfare_draw_status()
	{
		return welfare_draw_status;
	}

	public void setWelfare_draw_status(String welfare_draw_status)
	{
		this.welfare_draw_status = welfare_draw_status;
	}

	public LinkedHashMap<String, String> getProperties()
	{
		LinkedHashMap<String, String> pros = new LinkedHashMap<String, String>();

		pros.put("villager_name", "村民姓名");
		pros.put("id_card", "身份证号");
		// pros.put("welfare_policy_id", "welfare_policy_id");
		pros.put("welfare_policy_name", "福利政策");
		pros.put("welfare_policy_start_time_show", "福利开始时间");
		pros.put("welfare_policy_end_time_show", "福利过期时间");
		// pros.put("welfare_policy_detail_id", "welfare_policy_detail_id");
		// pros.put("product_id", "product_id");
		pros.put("product_name", "产品名");
		pros.put("product_spec", "产品规格");
		pros.put("product_measuring_unit", "产品单位");
		pros.put("product_measuring_unit", "产品单位");
		pros.put("welfare_draw_status", "领取情况");
		pros.put("product_quantity", "应领数量");
		pros.put("product_quantity_drawed", "已领数量");
		pros.put("product_quantity_remainder", "未领数量");
		return pros;
	}

	public String getProduct_spec()
	{
		return product_spec;
	}

	public void setProduct_spec(String product_spec)
	{
		this.product_spec = product_spec;
	}

	public Long getVillager_welfare_id()
	{
		return villager_welfare_id;
	}

	public void setVillager_welfare_id(Long villager_welfare_id)
	{
		this.villager_welfare_id = villager_welfare_id;
	}

	public Long getVillager_id()
	{
		return villager_id;
	}

	public void setVillager_id(Long villager_id)
	{
		this.villager_id = villager_id;
	}

	public String getVillager_name()
	{
		return villager_name;
	}

	public void setVillager_name(String villager_name)
	{
		this.villager_name = villager_name;
	}

	public String getId_card()
	{
		return id_card;
	}

	public void setId_card(String id_card)
	{
		this.id_card = id_card;
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

	public String getWelfare_policy_start_time_show()
	{
		return TimeUtil.date2str(welfare_policy_start_time, "yyyy-MM-dd");
	}

	public Timestamp getWelfare_policy_start_time()
	{
		return welfare_policy_start_time;
	}

	public void setWelfare_policy_start_time(Timestamp welfare_policy_start_time)
	{
		this.welfare_policy_start_time = welfare_policy_start_time;
	}

	public String getWelfare_policy_end_time_show()
	{
		return TimeUtil.date2str(welfare_policy_end_time, "yyyy-MM-dd");
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

	public String getProduct_measuring_unit()
	{
		return product_measuring_unit;
	}

	public void setProduct_measuring_unit(String product_measuring_unit)
	{
		this.product_measuring_unit = product_measuring_unit;
	}

	public Long getProduct_quantity()
	{
		return product_quantity;
	}

	public void setProduct_quantity(Long product_quantity)
	{
		this.product_quantity = product_quantity;
	}

	public Long getProduct_quantity_drawed()
	{
		return product_quantity_drawed;
	}

	public void setProduct_quantity_drawed(Long product_quantity_drawed)
	{
		this.product_quantity_drawed = product_quantity_drawed;
	}

	public Long getProduct_quantity_remainder()
	{
		return product_quantity_remainder;
	}

	public void setProduct_quantity_remainder(Long product_quantity_remainder)
	{
		this.product_quantity_remainder = product_quantity_remainder;
	}
}
