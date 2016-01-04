package com.fuli.obj;

import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.util.StringUtil;

;
public class ProductObj extends BaseDbObj
{
	private Long id;
	private String u_code;
	private String name;
	private String spec;
	private String prodw;
	private String pdwcode;
	private String pdwname;
	private Double price;// 成本价
	private Double price_retail;// 销售价
	private Double assitUnit1ID;// 单位换算id，对应commoninfo表
	private Double assitUnit1Rate;// 单位换算率
	private String assitUnit1Name;// 换单单位名

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
		return "v_product";
	}

	@Override
	public String getBasePath()
	{
		return "Product";
	}

	@Override
	public String getCnName()
	{
		return "商品信息";
	}

	public LinkedHashMap<String, String> getProperties()
	{
		LinkedHashMap<String, String> pros = new LinkedHashMap<String, String>();

		pros.put("id", "商品编号");
		pros.put("name", "商品名");
		pros.put("spec", "规格");
		pros.put("type", "型号");
		return pros;
	}

	public String getU_code()
	{
		return u_code;
	}

	public void setU_code(String u_code)
	{
		this.u_code = u_code;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSpec()
	{
		return spec;
	}

	public void setSpec(String spec)
	{
		this.spec = spec;
	}

	public String getProdw()
	{
		return prodw;
	}

	public void setProdw(String prodw)
	{
		this.prodw = prodw;
	}

	public String getPdwcode()
	{
		return pdwcode;
	}

	public void setPdwcode(String pdwcode)
	{
		this.pdwcode = pdwcode;
	}

	public String getPdwname()
	{
		return pdwname;
	}

	public void setPdwname(String pdwname)
	{
		this.pdwname = pdwname;
	}

	public Double getPrice()
	{
		return price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}

	public Double getPrice_retail()
	{
		return price_retail;
	}

	public void setPrice_retail(Double price_retail)
	{
		this.price_retail = price_retail;
	}

	public Double getAssitUnit1ID()
	{
		return assitUnit1ID;
	}

	public void setAssitUnit1ID(Double assitUnit1ID)
	{
		this.assitUnit1ID = assitUnit1ID;
	}

	public Double getAssitUnit1Rate()
	{
		return assitUnit1Rate;
	}

	public void setAssitUnit1Rate(Double assitUnit1Rate)
	{
		this.assitUnit1Rate = assitUnit1Rate;
	}

	public String getAssitUnit1Name()
	{
		return assitUnit1Name;
	}

	public void setAssitUnit1Name(String assitUnit1Name)
	{
		this.assitUnit1Name = assitUnit1Name;
	}

	public String getDWRatio()
	{
		if (assitUnit1Rate != null && assitUnit1Rate > 0)
		{
			return "1.00" + pdwname + "=1.00" + pdwname;// 1.00斤=1.00斤
		}
		return null;
	}

	public String getDWRatio1()
	{
		if (assitUnit1Rate != null && assitUnit1Rate > 0)
		{
			return "1.00" + assitUnit1Name + "=" + String.format("%.2f", assitUnit1Rate) + pdwname;// 1.00斤=1.00斤
		}
		return null;
	}

	public static void main(String[] args)
	{

		double d = 3.1415926;
		String result = String.format("%.2f", d);
		System.out.println(result);
	}
}