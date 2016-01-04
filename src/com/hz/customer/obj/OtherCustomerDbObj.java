package com.hz.customer.obj;

import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.util.StringUtil;
import com.wuyg.task.TaskConstant;

public class OtherCustomerDbObj extends BaseDbObj
{
	private String haoMa;// 号码
	private String quXian;// 区县
	private String xiangZhen;// 单位
	private String zhuJiaoYiDong;// 主叫移动
	private String jieTingYiDong;// 被叫移动
	private String zuiZaoTongHuaShiJian;// 最早通话时间
	private String zuiWanTongHuaShiJian;// 最晚通话时间
	private String haoMaDengJi;// 号码等级

	public String toHtml(String colsAndValuesStr)
	{
		if (!StringUtil.isEmpty(haoMa))// 从数据库中查询到了数据
		{
			colsAndValuesStr = getColsAndVauesStr();
		}

		StringBuffer s = new StringBuffer("<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" >");

		// 用于展现用户信息详情
		if (!StringUtil.isEmpty(colsAndValuesStr))
		{
			if (colsAndValuesStr.indexOf("没有检索到") >= 0)
			{
				s.append("<tr class=\"list_table_tr2\"><td colspan=2><font color=red>" + colsAndValuesStr + "</font></td></tr>");
			} else
			{
				String[] colsAndValues = colsAndValuesStr.split(",|;|，|；");
				for (int i = 0; i < colsAndValues.length; i = i + 2)
				{
					s.append("<tr class=\"" + (i % 4 == 0 ? "list_table_tr2" : "list_table_tr0") + "\">");
					s.append(getTd(colsAndValues[i]));
					if (i < colsAndValues.length - 1)
					{
						s.append(getTd(colsAndValues[i + 1]));
					}
					s.append("</tr>");
				}
			}
		}

		s.append("</table>");

		// 一定要加一个隐藏的数据框，用来提交用户基本信息保存入工单表中
		s.append("<input type=\"hidden\" id=\"customerInfo\" name=\"customerInfo\" value=\"" + colsAndValuesStr + "\">");

		return s.toString();
	}

	private String getTd(String colsAndValue)
	{
		String td = "";
		String[] colAndValue = colsAndValue.split("=");

		String colName = colAndValue.length >= 1 ? colAndValue[0] + ":" : "";
		String colValue = colAndValue.length >= 2 ? colAndValue[1] : "";

		// 占位的字段不显示
		td += "	<td width=\"150\" align=\"right\"><strong>" + (StringUtil.getNotEmptyStr(colName.replaceFirst((TaskConstant.CUST_COL_PLACEHOLDER + ":"), "")))
				+ "</strong></td>";
		td += "	<td width=\"200\" align=\"left\">" + StringUtil.getNotEmptyStr(colValue) + "</td>";

		return td;
	}

	private String getColsAndVauesStr()
	{
		StringBuffer s = new StringBuffer();
		// s.append("手机号=").append(shouJiHao);
		s.append("区县=").append(quXian);
		s.append(",乡镇=").append(xiangZhen);
		s.append(",主叫移动=").append(zhuJiaoYiDong);
		s.append(",接听移动=").append(jieTingYiDong);
		s.append(",最早通话时间=").append(zuiZaoTongHuaShiJian);
		s.append(",最晚通话时间=").append(zuiWanTongHuaShiJian);
		s.append(",号码等级=").append(haoMaDengJi);
		return s.toString();
	}

	@Override
	public String findKeyColumnName()
	{
		return "haoMa";
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
		return "customer_other";
	}

	public String getHaoMa()
	{
		return haoMa;
	}

	public void setHaoMa(String haoMa)
	{
		this.haoMa = haoMa;
	}

	public String getQuXian()
	{
		return quXian;
	}

	public void setQuXian(String quXian)
	{
		this.quXian = quXian;
	}

	public String getXiangZhen()
	{
		return xiangZhen;
	}

	public void setXiangZhen(String xiangZhen)
	{
		this.xiangZhen = xiangZhen;
	}

	public String getZhuJiaoYiDong()
	{
		return zhuJiaoYiDong;
	}

	public void setZhuJiaoYiDong(String zhuJiaoYiDong)
	{
		this.zhuJiaoYiDong = zhuJiaoYiDong;
	}

	public String getJieTingYiDong()
	{
		return jieTingYiDong;
	}

	public void setJieTingYiDong(String jieTingYiDong)
	{
		this.jieTingYiDong = jieTingYiDong;
	}

	public String getZuiZaoTongHuaShiJian()
	{
		return zuiZaoTongHuaShiJian;
	}

	public void setZuiZaoTongHuaShiJian(String zuiZaoTongHuaShiJian)
	{
		this.zuiZaoTongHuaShiJian = zuiZaoTongHuaShiJian;
	}

	public String getZuiWanTongHuaShiJian()
	{
		return zuiWanTongHuaShiJian;
	}

	public void setZuiWanTongHuaShiJian(String zuiWanTongHuaShiJian)
	{
		this.zuiWanTongHuaShiJian = zuiWanTongHuaShiJian;
	}

	public String getHaoMaDengJi()
	{
		return haoMaDengJi;
	}

	public void setHaoMaDengJi(String haoMaDengJi)
	{
		this.haoMaDengJi = haoMaDengJi;
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

	@Override
	public LinkedHashMap<String, String> getProperties()
	{
		// TODO Auto-generated method stub
		return null;
	}
}