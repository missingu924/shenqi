package com.fuli.obj;
import java.sql.Timestamp;
import com.wuyg.common.dao.BaseDbObj;
import java.util.LinkedHashMap;
import com.alibaba.fastjson.JSON;
public class VillagerObj extends BaseDbObj
{
private Long id;
private String villager_name;
private String id_card;
private String villager_sex;
private String villager_telephone;
private String villager_omment;
private String enable;
private String binding_to_id;
private Timestamp last_modify_time;
private String last_modify_account;
@Override
public String findKeyColumnName()
{
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
	return "Villager";
}
@Override
public String getBasePath()
{
	return "Villager";
}
@Override
public String getCnName()
{
	return "村民";
}
public LinkedHashMap<String, String> getProperties()
{
		LinkedHashMap<String, String> pros = new LinkedHashMap<String, String>();

		pros.put("id", "编号");
		pros.put("villager_name", "姓名");
		pros.put("id_card", "身份证");
		pros.put("villager_sex", "性别");
		pros.put("villager_telephone", "电话");
		pros.put("villager_omment", "备注");
		pros.put("enable", "启用");
		pros.put("binding_to_id", "绑定到");
		pros.put("last_modify_time", "修改时间");
		pros.put("last_modify_account", "修改人");
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
public String getVillager_sex()
{
	return villager_sex;
}
public void setVillager_sex(String villager_sex)
{
	this.villager_sex = villager_sex;
}
public String getVillager_telephone()
{
	return villager_telephone;
}
public void setVillager_telephone(String villager_telephone)
{
	this.villager_telephone = villager_telephone;
}
public String getVillager_omment()
{
	return villager_omment;
}
public void setVillager_omment(String villager_omment)
{
	this.villager_omment = villager_omment;
}
public String getEnable()
{
	return enable;
}
public void setEnable(String enable)
{
	this.enable = enable;
}
public String getBinding_to_id()
{
	return binding_to_id;
}
public void setBinding_to_id(String binding_to_id)
{
	this.binding_to_id = binding_to_id;
}
public Timestamp getLast_modify_time()
{
	return last_modify_time;
}
public void setLast_modify_time(Timestamp last_modify_time)
{
	this.last_modify_time = last_modify_time;
}
public String getLast_modify_account()
{
	return last_modify_account;
}
public void setLast_modify_account(String last_modify_account)
{
	this.last_modify_account = last_modify_account;
}
@Override
public String toString()
{
	return JSON.toJSONString(this);
}
}

