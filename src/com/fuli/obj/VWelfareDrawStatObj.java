package com.fuli.obj;

import java.sql.Timestamp;

import com.alibaba.fastjson.JSON;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.common.util.StringUtil;

import java.util.LinkedHashMap;

;
public class VWelfareDrawStatObj extends BaseDbObj
{
	private Long welfare_policy_id;
	private String welfare_policy_name;
	private Timestamp welfare_policy_start_time;
	private Timestamp welfare_policy_end_time;
	private Double welfare_policy_detail_id;
	private String product_id;
	private String product_name;
	private String product_spec;
	private Double product_price;
	private Long product_qunatity_sum;

	private static String drawed_time_start;// 查询条件，福利领取时间-开始时间，必须用static，以便构造sql
	private static String drawed_time_end;// 查询条件，福利领取时间-结束时间，必须用static，以便构造sql

	@Override
	public String findKeyColumnName()
	{
		return "welfare_policy_id";
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
		String table = " ( \n";
		table += " select \n";
		table += " welfare_policy_id,\n";
		table += " welfare_policy_name,\n";
		table += " welfare_policy_start_time,\n";
		table += " welfare_policy_end_time,\n";
		table += " welfare_policy_detail_id,\n";
		table += " product_id,\n";
		table += " product_name,product_spec,\n";
		table += " product_price,\n";
		table += " SUM(product_quantity_drawed) product_qunatity_sum\n";
		table += " from \n";
		table += " V_WELFARE_DRAW_DETAIL\n";
		table += " where 1=1 \n";
		if (!StringUtil.isEmpty(drawed_time_start))
		{
			table += " and datediff(s,draw_date,'" + drawed_time_start + " 00:00:00')<=0";
		}
		if (!StringUtil.isEmpty(drawed_time_end))
		{
			table += " and datediff(s,draw_date,'" + drawed_time_end + " 23:59:59')>=0";
		}
		table += " group by \n";
		table += " welfare_policy_id,\n";
		table += " welfare_policy_name,\n";
		table += " welfare_policy_start_time,\n";
		table += " welfare_policy_end_time,\n";
		table += " welfare_policy_detail_id,\n";
		table += " product_id,\n";
		table += " product_name,\n";
		table += " product_spec,\n";
		table += " product_price\n";
		table += " ) t0\n";
		return table;
	}

	@Override
	public String findDefaultOrderBy()
	{
		return " welfare_policy_start_time desc,welfare_policy_id,product_id";
	}

	@Override
	public String getBasePath()
	{
		return "VWelfareDrawStat";
	}

	@Override
	public String getCnName()
	{
		return "福利领取统计";
	}

	public LinkedHashMap<String, String> getProperties()
	{
		LinkedHashMap<String, String> pros = new LinkedHashMap<String, String>();

//		pros.put("welfare_policy_id", "welfare_policy_id");
		pros.put("welfare_policy_name", "福利政策");
//		pros.put("welfare_policy_start_time", "福利开始时间");
//		pros.put("welfare_policy_end_time", "福利过期时间");
//		pros.put("welfare_policy_detail_id", "welfare_policy_detail_id");
//		pros.put("product_id", "product_id");
		pros.put("product_name", "产品名称");
		pros.put("product_spec", "产品规格");
//		pros.put("product_price", "product_price");
		pros.put("product_qunatity_sum", "已领取数量");
		return pros;
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

	public Double getWelfare_policy_detail_id()
	{
		return welfare_policy_detail_id;
	}

	public void setWelfare_policy_detail_id(Double welfare_policy_detail_id)
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

	public String getProduct_spec()
	{
		return product_spec;
	}

	public void setProduct_spec(String product_spec)
	{
		this.product_spec = product_spec;
	}

	public Double getProduct_price()
	{
		return product_price;
	}

	public void setProduct_price(Double product_price)
	{
		this.product_price = product_price;
	}

	public Long getProduct_qunatity_sum()
	{
		return product_qunatity_sum;
	}

	public void setProduct_qunatity_sum(Long product_qunatity_sum)
	{
		this.product_qunatity_sum = product_qunatity_sum;
	}

	public String getDrawed_time_start()
	{
		return drawed_time_start;
	}

	public void setDrawed_time_start(String drawed_time_start)
	{
		this.drawed_time_start = drawed_time_start;
	}

	public String getDrawed_time_end()
	{
		return drawed_time_end;
	}

	public void setDrawed_time_end(String drawed_time_end)
	{
		this.drawed_time_end = drawed_time_end;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}

	public static void main(String[] args)
	{
		IBaseDAO dao = new DefaultBaseDAO(VWelfareDrawStatObj.class);

		VWelfareDrawStatObj statObj = new VWelfareDrawStatObj();

		PaginationObj page = dao.searchPaginationByDomainInstance(statObj, statObj.findDefaultOrderBy(), 0, Integer.MAX_VALUE);
		
		for (int i = 0; i < page.getDataList().size(); i++)
		{
			System.out.println(page.getDataList().get(i));
		}
	
	}
}
