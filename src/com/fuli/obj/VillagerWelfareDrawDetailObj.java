package com.fuli.obj;
import java.sql.Timestamp;
import com.wuyg.common.dao.BaseDbObj;
import java.util.LinkedHashMap;;
public class VillagerWelfareDrawDetailObj extends BaseDbObj
{
private Long villager_welfare_draw_id;
private Long villager_welfare_id;
private String product_id;
private String product_name;
private Long product_quantity;
private String product_measuring_unit;
private Timestamp last_modify_time;
private String last_modify_accout;
private Long id;
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
	return "id";
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
	return "villager_welfare_draw_detail";
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

		pros.put("villager_welfare_draw_id", "villager_welfare_draw_id");
		pros.put("villager_welfare_id", "villager_welfare_id");
		pros.put("product_id", "product_id");
		pros.put("product_name", "product_name");
		pros.put("product_quantity", "product_quantity");
		pros.put("product_measuring_unit", "product_measuring_unit");
		pros.put("last_modify_time", "last_modify_time");
		pros.put("last_modify_accout", "last_modify_accout");
		pros.put("id", "id");
		return pros;
}
public Long getVillager_welfare_draw_id()
{
	return villager_welfare_draw_id;
}
public void setVillager_welfare_draw_id(Long villager_welfare_draw_id)
{
	this.villager_welfare_draw_id = villager_welfare_draw_id;
}
public Long getVillager_welfare_id()
{
	return villager_welfare_id;
}
public void setVillager_welfare_id(Long villager_welfare_id)
{
	this.villager_welfare_id = villager_welfare_id;
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
public Long getId()
{
	return id;
}
public void setId(Long id)
{
	this.id = id;
}
}

