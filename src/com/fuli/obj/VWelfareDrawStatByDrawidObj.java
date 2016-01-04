package com.fuli.obj;

import java.sql.Timestamp;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.util.TimeUtil;

import java.util.LinkedHashMap;

;
public class VWelfareDrawStatByDrawidObj extends BaseDbObj
{
	private Long draw_id;
	private Long draw_villager_id;
	private String draw_villager_name;
	private String draw_villager_id_card;
	private String product_id;
	private String product_name;
	private String product_spec;
	private String product_measuring_unit;
	private Long product_quantity_drawed_sum;
	private Double product_price;
	private Timestamp draw_date;
	

	@Override
	public String findKeyColumnName()
	{
		// TODO Auto-generated method stub
		return "draw_id";
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
		return "v_welfare_draw_stat_by_drawid";
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

		pros.put("draw_id", "draw_id");
		pros.put("draw_villager_id", "draw_villager_id");
		pros.put("draw_villager_name", "draw_villager_name");
		pros.put("draw_villager_id_card", "draw_villager_id_card");
		pros.put("product_id", "product_id");
		pros.put("product_name", "product_name");
		pros.put("product_quantity_drawed_sum", "product_quantity_drawed_sum");
		return pros;
	}

	public String getDraw_date_show()
	{
		return TimeUtil.date2str(this.draw_date);
	}

	public Timestamp getDraw_date()
	{
		return draw_date;
	}

	public void setDraw_date(Timestamp draw_date)
	{
		this.draw_date = draw_date;
	}

	public String getProduct_measuring_unit()
	{
		return product_measuring_unit;
	}

	public void setProduct_measuring_unit(String product_measuring_unit)
	{
		this.product_measuring_unit = product_measuring_unit;
	}

	public String getProduct_spec()
	{
		return product_spec;
	}

	public void setProduct_spec(String product_spec)
	{
		this.product_spec = product_spec;
	}

	public Long getDraw_id()
	{
		return draw_id;
	}

	public void setDraw_id(Long draw_id)
	{
		this.draw_id = draw_id;
	}

	public Long getDraw_villager_id()
	{
		return draw_villager_id;
	}

	public void setDraw_villager_id(Long draw_villager_id)
	{
		this.draw_villager_id = draw_villager_id;
	}

	public String getDraw_villager_name()
	{
		return draw_villager_name;
	}

	public void setDraw_villager_name(String draw_villager_name)
	{
		this.draw_villager_name = draw_villager_name;
	}

	public String getDraw_villager_id_card()
	{
		return draw_villager_id_card;
	}

	public void setDraw_villager_id_card(String draw_villager_id_card)
	{
		this.draw_villager_id_card = draw_villager_id_card;
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

	public Long getProduct_quantity_drawed_sum()
	{
		return product_quantity_drawed_sum;
	}

	public void setProduct_quantity_drawed_sum(Long product_quantity_drawed_sum)
	{
		this.product_quantity_drawed_sum = product_quantity_drawed_sum;
	}

	public Double getProduct_price()
	{
		return product_price;
	}

	public void setProduct_price(Double product_price)
	{
		this.product_price = product_price;
	}
}
