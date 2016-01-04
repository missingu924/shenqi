package com.fuli.obj;
import java.sql.Timestamp;
import com.wuyg.common.dao.BaseDbObj;
import java.util.LinkedHashMap;
import com.alibaba.fastjson.JSON;
public class WelfarePolicyObj extends BaseDbObj
{
private Long id;
private String welfare_policy_name;
private Timestamp last_modify_time;
private String last_modify_account;
private Timestamp welfare_policy_start_time;
private Timestamp welfare_policy_end_time;
private String welfare_policy_comment;
private String welfare_policy_for_all;
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
	return "welfare_policy";
}
@Override
public String getBasePath()
{
	return "WelfarePolicy";
}
@Override
public String getCnName()
{
	return "福利政策";
}
public LinkedHashMap<String, String> getProperties()
{
		LinkedHashMap<String, String> pros = new LinkedHashMap<String, String>();

		pros.put("id", "编号");
		pros.put("welfare_policy_name", "政策名");
//		pros.put("last_modify_time", "last_modify_time");
//		pros.put("last_modify_account", "last_modify_account");
		pros.put("welfare_policy_start_time", "生效时间");
		pros.put("welfare_policy_end_time", "过期时间");
		pros.put("welfare_policy_comment", "备注");
		pros.put("welfare_policy_for_all", "所有村民都享受");
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
public String getWelfare_policy_name()
{
	return welfare_policy_name;
}
public void setWelfare_policy_name(String welfare_policy_name)
{
	this.welfare_policy_name = welfare_policy_name;
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
public String getWelfare_policy_comment()
{
	return welfare_policy_comment;
}
public void setWelfare_policy_comment(String welfare_policy_comment)
{
	this.welfare_policy_comment = welfare_policy_comment;
}
public String getWelfare_policy_for_all()
{
	return welfare_policy_for_all;
}
public void setWelfare_policy_for_all(String welfare_policy_for_all)
{
	this.welfare_policy_for_all = welfare_policy_for_all;
}
@Override
public String toString()
{
	return JSON.toJSONString(this);
}
}

