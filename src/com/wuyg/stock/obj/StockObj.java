package com.wuyg.stock.obj;

import java.util.LinkedHashMap;

import com.sun.org.apache.bcel.internal.generic.FLOAD;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.util.StringUtil;

public class StockObj extends BaseDbObj
{
	// var hq_str_sh601006="������·, 27.55, 27.25, 26.91, 27.55, 26.20,
	// 26.91,26.92,22114263, 589824680, 4695, 26.91, 57590, 26.90, 14700, 26.89,
	// 14300,26.88, 15100, 26.87, 3100, 26.92, 8900, 26.93, 14230, 26.94, 25150,
	// 26.95, 15220, 26.96, 2008-01-11, 15:05:32";
	// 0����������·������Ʊ���֣�
	// 1����27.55�壬���տ��̼ۣ�
	// 2����27.25�壬�������̼ۣ�
	// 3����26.91�壬��ǰ�۸�
	// 4����27.55�壬������߼ۣ�
	// 5����26.20�壬������ͼۣ�
	// 6����26.91�壬����ۣ�������һ�����ۣ�
	// 7����26.92�壬�����ۣ�������һ�����ۣ�
	// 8����22114263�壬�ɽ��Ĺ�Ʊ�������ڹ�Ʊ������һ�ٹ�Ϊ������λ��������ʹ��ʱ��ͨ���Ѹ�ֵ����һ�٣�
	// 9����589824680�壬�ɽ�����λΪ��Ԫ����Ϊ��һĿ��Ȼ��ͨ���ԡ���Ԫ��Ϊ�ɽ����ĵ�λ������ͨ���Ѹ�ֵ����һ��
	// 10����4695�壬����һ������4695�ɣ���47�֣�
	// 11����26.91�壬����һ�����ۣ�
	// 12����57590�壬�������
	// 13����26.90�壬�������
	// 14����14700�壬��������
	// 15����26.89�壬��������
	// 16����14300�壬�����ġ�
	// 17����26.88�壬�����ġ�
	// 18����15100�壬�����塱
	// 19����26.87�壬�����塱
	// 20����3100�壬����һ���걨3100�ɣ���31�֣�
	// 21����26.92�壬����һ������
	// (22, 23), (24, 25), (26,27), (28, 29)�ֱ�Ϊ���������������ĵ������
	// 30����2008-01-11�壬���ڣ�
	// 31����15:05:32�壬ʱ�䣻

	private String id;
	private String code;
	private String name;
	private String jinRiKaiPanJia;
	private String zuoRiShouPanJia;
	private String dangQianJia;
	private String jinRiZuiGaoJia;
	private String jinRiZuiDiJia;
	private String jingBuyJia;
	private String jingSaleJia;
	private String chengJiaoGuPiaoShu;
	private String chengJiaoJinEr;
	private String buy1;
	private String buy1Jia;
	private String buy2;
	private String buy2Jia;
	private String buy3;
	private String buy3Jia;
	private String buy4;
	private String buy4Jia;
	private String buy5;
	private String buy5Jia;
	private String sale1;
	private String sale1Jia;
	private String sale2;
	private String sale2Jia;
	private String sale3;
	private String sale3Jia;
	private String sale4;
	private String sale4Jia;
	private String sale5;
	private String sale5Jia;
	private String day;
	private String time;

	public StockObj()
	{
	}

	public StockObj(String info)
	{
		code = info.substring(13, 19);

		String mainInfo = StringUtil.substr(info, "=\"", "\"");
		String tmp[] = mainInfo.split(",");

		name = tmp[0];
		jinRiKaiPanJia = tmp[1];
		zuoRiShouPanJia = tmp[2];
		dangQianJia = tmp[3];
		jinRiZuiGaoJia = tmp[4];
		jinRiZuiDiJia = tmp[5];
		jingBuyJia = tmp[6];
		jingSaleJia = tmp[7];
		chengJiaoGuPiaoShu = tmp[8];
		chengJiaoJinEr = tmp[9];
		buy1 = tmp[10];
		buy1Jia = tmp[11];
		buy2 = tmp[12];
		buy2Jia = tmp[13];
		buy3 = tmp[14];
		buy3Jia = tmp[15];
		buy4 = tmp[16];
		buy4Jia = tmp[17];
		buy5 = tmp[18];
		buy5Jia = tmp[19];
		sale1 = tmp[20];
		sale1Jia = tmp[21];
		sale2 = tmp[22];
		sale2Jia = tmp[23];
		sale3 = tmp[24];
		sale3Jia = tmp[25];
		sale4 = tmp[26];
		sale4Jia = tmp[27];
		sale5 = tmp[28];
		sale5Jia = tmp[29];
		day = tmp[30];
		time = tmp[31];

	}

	@Override
	public String findKeyColumnName()
	{
		return "id";
	}

	@Override
	public String findParentKeyColumnName()
	{
		return "code";
	}

	@Override
	public String findTableName()
	{
		return "stock_info";
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getJinRiKaiPanJia()
	{
		return jinRiKaiPanJia;
	}

	public void setJinRiKaiPanJia(String jinRiKaiPanJia)
	{
		this.jinRiKaiPanJia = jinRiKaiPanJia;
	}

	public String getZuoRiShouPanJia()
	{
		return zuoRiShouPanJia;
	}

	public void setZuoRiShouPanJia(String zuoRiShouPanJia)
	{
		this.zuoRiShouPanJia = zuoRiShouPanJia;
	}

	public String getDangQianJia()
	{
		return dangQianJia;
	}

	public void setDangQianJia(String dangQianJia)
	{
		this.dangQianJia = dangQianJia;
	}

	public String getJinRiZuiGaoJia()
	{
		return jinRiZuiGaoJia;
	}

	public void setJinRiZuiGaoJia(String jinRiZuiGaoJia)
	{
		this.jinRiZuiGaoJia = jinRiZuiGaoJia;
	}

	public String getJinRiZuiDiJia()
	{
		return jinRiZuiDiJia;
	}

	public void setJinRiZuiDiJia(String jinRiZuiDiJia)
	{
		this.jinRiZuiDiJia = jinRiZuiDiJia;
	}

	public String getJingBuyJia()
	{
		return jingBuyJia;
	}

	public void setJingBuyJia(String jingBuyJia)
	{
		this.jingBuyJia = jingBuyJia;
	}

	public String getJingSaleJia()
	{
		return jingSaleJia;
	}

	public void setJingSaleJia(String jingSaleJia)
	{
		this.jingSaleJia = jingSaleJia;
	}

	public String getChengJiaoGuPiaoShu()
	{
		return chengJiaoGuPiaoShu;
	}

	public void setChengJiaoGuPiaoShu(String chengJiaoGuPiaoShu)
	{
		this.chengJiaoGuPiaoShu = chengJiaoGuPiaoShu;
	}

	public String getChengJiaoJinEr()
	{
		return chengJiaoJinEr;
	}

	public void setChengJiaoJinEr(String chengJiaoJinEr)
	{
		this.chengJiaoJinEr = chengJiaoJinEr;
	}

	public String getBuy1()
	{
		return buy1;
	}

	public void setBuy1(String buy1)
	{
		this.buy1 = buy1;
	}

	public String getBuy1Jia()
	{
		return buy1Jia;
	}

	public void setBuy1Jia(String buy1Jia)
	{
		this.buy1Jia = buy1Jia;
	}

	public String getBuy2()
	{
		return buy2;
	}

	public void setBuy2(String buy2)
	{
		this.buy2 = buy2;
	}

	public String getBuy2Jia()
	{
		return buy2Jia;
	}

	public void setBuy2Jia(String buy2Jia)
	{
		this.buy2Jia = buy2Jia;
	}

	public String getBuy3()
	{
		return buy3;
	}

	public void setBuy3(String buy3)
	{
		this.buy3 = buy3;
	}

	public String getBuy3Jia()
	{
		return buy3Jia;
	}

	public void setBuy3Jia(String buy3Jia)
	{
		this.buy3Jia = buy3Jia;
	}

	public String getBuy4()
	{
		return buy4;
	}

	public void setBuy4(String buy4)
	{
		this.buy4 = buy4;
	}

	public String getBuy4Jia()
	{
		return buy4Jia;
	}

	public void setBuy4Jia(String buy4Jia)
	{
		this.buy4Jia = buy4Jia;
	}

	public String getBuy5()
	{
		return buy5;
	}

	public void setBuy5(String buy5)
	{
		this.buy5 = buy5;
	}

	public String getBuy5Jia()
	{
		return buy5Jia;
	}

	public void setBuy5Jia(String buy5Jia)
	{
		this.buy5Jia = buy5Jia;
	}

	public String getSale1()
	{
		return sale1;
	}

	public void setSale1(String sale1)
	{
		this.sale1 = sale1;
	}

	public String getSale1Jia()
	{
		return sale1Jia;
	}

	public void setSale1Jia(String sale1Jia)
	{
		this.sale1Jia = sale1Jia;
	}

	public String getSale2()
	{
		return sale2;
	}

	public void setSale2(String sale2)
	{
		this.sale2 = sale2;
	}

	public String getSale2Jia()
	{
		return sale2Jia;
	}

	public void setSale2Jia(String sale2Jia)
	{
		this.sale2Jia = sale2Jia;
	}

	public String getSale3()
	{
		return sale3;
	}

	public void setSale3(String sale3)
	{
		this.sale3 = sale3;
	}

	public String getSale3Jia()
	{
		return sale3Jia;
	}

	public void setSale3Jia(String sale3Jia)
	{
		this.sale3Jia = sale3Jia;
	}

	public String getSale4()
	{
		return sale4;
	}

	public void setSale4(String sale4)
	{
		this.sale4 = sale4;
	}

	public String getSale4Jia()
	{
		return sale4Jia;
	}

	public void setSale4Jia(String sale4Jia)
	{
		this.sale4Jia = sale4Jia;
	}

	public String getSale5()
	{
		return sale5;
	}

	public void setSale5(String sale5)
	{
		this.sale5 = sale5;
	}

	public String getSale5Jia()
	{
		return sale5Jia;
	}

	public void setSale5Jia(String sale5Jia)
	{
		this.sale5Jia = sale5Jia;
	}

	public String getDay()
	{
		return day;
	}

	public void setDay(String day)
	{
		this.day = day;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getId()
	{
		return code + "_" + day + "_" + time;
	}

	public void setId(String id)
	{
		this.id = id;
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
