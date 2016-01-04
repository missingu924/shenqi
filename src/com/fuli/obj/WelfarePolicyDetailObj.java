package com.fuli.obj;

import java.sql.Timestamp;
import com.wuyg.common.dao.BaseDbObj;
import java.util.LinkedHashMap;

;
public class WelfarePolicyDetailObj extends BaseDbObj
{
	private Long id;
	private Long welfare_policy_id;
	private String product_id;
	private String product_name;
	private Long product_quantity;
	private String product_measuring_unit;
	private String product_u_code;
	private String product_spec;
	private Double product_price;
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
		return "welfare_policy_id";
	}

	@Override
	public String findTableName()
	{
		return "welfare_policy_detail";
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
		return "福利产品明细";
	}

	public LinkedHashMap<String, String> getProperties()
	{
		LinkedHashMap<String, String> pros = new LinkedHashMap<String, String>();

		pros.put("id", "编号");
		pros.put("welfare_policy_id", "福利政策编号");
		pros.put("product_id", "产品内部编号");
		pros.put("product_u_code", "产品编号");
		pros.put("product_name", "产品名称");
		pros.put("product_spec", "产品规格");
		pros.put("product_measuring_unit", "产品单位");
		pros.put("product_price", "产品单价");
		pros.put("product_quantity", "人均数量");
		pros.put("last_modify_time", "最后修改时间");
		pros.put("last_modify_account", "最后修改人");
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

	public String getProduct_u_code()
	{
		return product_u_code;
	}

	public void setProduct_u_code(String product_u_code)
	{
		this.product_u_code = product_u_code;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getWelfare_policy_id()
	{
		return welfare_policy_id;
	}

	public void setWelfare_policy_id(Long welfare_policy_id)
	{
		this.welfare_policy_id = welfare_policy_id;
	}

	public Double getProduct_price()
	{
		return product_price;
	}

	public void setProduct_price(Double product_price)
	{
		this.product_price = product_price;
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

	public String getLast_modify_account()
	{
		return last_modify_account;
	}

	public void setLast_modify_account(String last_modify_account)
	{
		this.last_modify_account = last_modify_account;
	}
}
