package com.hz.customer.obj;

import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.util.StringUtil;
import com.wuyg.task.TaskConstant;

public class OtherCustomerDbObj extends BaseDbObj
{
	private String haoMa;// ����
	private String quXian;// ����
	private String xiangZhen;// ��λ
	private String zhuJiaoYiDong;// �����ƶ�
	private String jieTingYiDong;// �����ƶ�
	private String zuiZaoTongHuaShiJian;// ����ͨ��ʱ��
	private String zuiWanTongHuaShiJian;// ����ͨ��ʱ��
	private String haoMaDengJi;// ����ȼ�

	public String toHtml(String colsAndValuesStr)
	{
		if (!StringUtil.isEmpty(haoMa))// �����ݿ��в�ѯ��������
		{
			colsAndValuesStr = getColsAndVauesStr();
		}

		StringBuffer s = new StringBuffer("<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" >");

		// ����չ���û���Ϣ����
		if (!StringUtil.isEmpty(colsAndValuesStr))
		{
			if (colsAndValuesStr.indexOf("û�м�����") >= 0)
			{
				s.append("<tr class=\"list_table_tr2\"><td colspan=2><font color=red>" + colsAndValuesStr + "</font></td></tr>");
			} else
			{
				String[] colsAndValues = colsAndValuesStr.split(",|;|��|��");
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

		// һ��Ҫ��һ�����ص����ݿ������ύ�û�������Ϣ�����빤������
		s.append("<input type=\"hidden\" id=\"customerInfo\" name=\"customerInfo\" value=\"" + colsAndValuesStr + "\">");

		return s.toString();
	}

	private String getTd(String colsAndValue)
	{
		String td = "";
		String[] colAndValue = colsAndValue.split("=");

		String colName = colAndValue.length >= 1 ? colAndValue[0] + ":" : "";
		String colValue = colAndValue.length >= 2 ? colAndValue[1] : "";

		// ռλ���ֶβ���ʾ
		td += "	<td width=\"150\" align=\"right\"><strong>" + (StringUtil.getNotEmptyStr(colName.replaceFirst((TaskConstant.CUST_COL_PLACEHOLDER + ":"), "")))
				+ "</strong></td>";
		td += "	<td width=\"200\" align=\"left\">" + StringUtil.getNotEmptyStr(colValue) + "</td>";

		return td;
	}

	private String getColsAndVauesStr()
	{
		StringBuffer s = new StringBuffer();
		// s.append("�ֻ���=").append(shouJiHao);
		s.append("����=").append(quXian);
		s.append(",����=").append(xiangZhen);
		s.append(",�����ƶ�=").append(zhuJiaoYiDong);
		s.append(",�����ƶ�=").append(jieTingYiDong);
		s.append(",����ͨ��ʱ��=").append(zuiZaoTongHuaShiJian);
		s.append(",����ͨ��ʱ��=").append(zuiWanTongHuaShiJian);
		s.append(",����ȼ�=").append(haoMaDengJi);
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