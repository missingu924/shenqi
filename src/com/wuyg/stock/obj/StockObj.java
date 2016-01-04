package com.wuyg.stock.obj;

import java.util.LinkedHashMap;

import com.sun.org.apache.bcel.internal.generic.FLOAD;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.util.StringUtil;

public class StockObj extends BaseDbObj
{
	// var hq_str_sh601006="大秦铁路, 27.55, 27.25, 26.91, 27.55, 26.20,
	// 26.91,26.92,22114263, 589824680, 4695, 26.91, 57590, 26.90, 14700, 26.89,
	// 14300,26.88, 15100, 26.87, 3100, 26.92, 8900, 26.93, 14230, 26.94, 25150,
	// 26.95, 15220, 26.96, 2008-01-11, 15:05:32";
	// 0：”大秦铁路”，股票名字；
	// 1：”27.55″，今日开盘价；
	// 2：”27.25″，昨日收盘价；
	// 3：”26.91″，当前价格；
	// 4：”27.55″，今日最高价；
	// 5：”26.20″，今日最低价；
	// 6：”26.91″，竞买价，即“买一”报价；
	// 7：”26.92″，竞卖价，即“卖一”报价；
	// 8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
	// 9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万；
	// 10：”4695″，“买一”申请4695股，即47手；
	// 11：”26.91″，“买一”报价；
	// 12：”57590″，“买二”
	// 13：”26.90″，“买二”
	// 14：”14700″，“买三”
	// 15：”26.89″，“买三”
	// 16：”14300″，“买四”
	// 17：”26.88″，“买四”
	// 18：”15100″，“买五”
	// 19：”26.87″，“买五”
	// 20：”3100″，“卖一”申报3100股，即31手；
	// 21：”26.92″，“卖一”报价
	// (22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况”
	// 30：”2008-01-11″，日期；
	// 31：”15:05:32″，时间；

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
