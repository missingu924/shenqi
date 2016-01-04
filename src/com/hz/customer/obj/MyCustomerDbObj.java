package com.hz.customer.obj;

import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.util.StringUtil;
import com.wuyg.task.TaskConstant;

public class MyCustomerDbObj extends BaseDbObj
{
	private String shouJiHao = "";// 手机号
	private String zhuangTai = "";// 状态
	private String zhuanTaiShiJian = "";// 状态时间
	private String zuiHouTongHua = "";// 最后通话
	private String quXian = "";// 区县
	private String danWei = "";// 单位
	private String jiTuanMingCheng = "";// 集团名称
	private String keHuJingLiDaiMa = "";// 客户经理代码
	private String keHuJingLi = "";// 客户经理
	private String wlanTaoCan = "";// wlan套餐
	private String quanQiuTongTaoCan = "";// 全球通套餐
	private String teHuiJiHua = "";// 特惠计划
	private String quanJiaFu = "";// 全家福
	private String huanLeJiaTing = "";// 欢乐家庭
	private String songJiaYouKa = "";// 送加油卡
	private String jiFenHuiBao = "";// 积分回报
	private String shengRiMianDan = "";// 生日免单
	private String taoCanDangCi = "";// 套餐档次
	private String xieYiKuanKunBang = "";// 协议款捆绑
	private String zengSongTaoCanFeiKunBang = "";// 赠送套餐捆绑
	private String zengSongShiCHangKunBang = "";// 赠送时长捆绑
	private String zuiDiXieYiXiaoFeiKunBang = "";// 最低协议消费捆绑
	private String heHuKunBang = "";// 合户捆绑
	private String kunBangBiaoZhi = "";// 捆绑标志
	private String shangYueShiChang = "";// 上月时长
	private String dangYueShiChang = "";// 当月时长
	private String shiChangTiSheng = "";// 时长提升
	private String shouJiLeiXing = "";// 手机类型
	private String jiXing = "";// 机型
	private String zhiNengJi = "";// 智能机
	private String TdJi = "";// td机

	public String toHtml(String colsAndValuesStr)
	{
		if (!StringUtil.isEmpty(shouJiHao))// 从数据库中查询到了数据
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
		td += "	<td width=\""+TaskConstant.CUST_COLNAME_WIDTH+"\" align=\"right\"><strong>" + (StringUtil.getNotEmptyStr(colName.replaceFirst((TaskConstant.CUST_COL_PLACEHOLDER + ":"), "")))
				+ "</strong></td>";
		td += "	<td width=\""+TaskConstant.CUST_COLVAUE_WIDTH+"\" align=\"left\">" + StringUtil.getNotEmptyStr(colValue) + "</td>";

		return td;
	}

	private String getColsAndVauesStr()
	{
		StringBuffer s = new StringBuffer();
		// s.append("手机号=").append(shouJiHao);
		s.append("状态=").append(zhuangTai);
		s.append(",状态时间=").append(zhuanTaiShiJian);
		s.append(",最后通话=").append(zuiHouTongHua);
		s.append(",区县=").append(quXian);
		s.append(",单位=").append(danWei);
		s.append(",集团名称=").append(jiTuanMingCheng);
		s.append(",客户经理代码=").append(keHuJingLiDaiMa);
		s.append(",客户经理=").append(keHuJingLi);
		s.append(",WLAN套餐=").append(wlanTaoCan);
		s.append(",全球通套餐=").append(quanQiuTongTaoCan);
		s.append(",特惠计划=").append(teHuiJiHua);
		s.append(",全家福=").append(quanJiaFu);
		s.append(",欢乐家庭=").append(huanLeJiaTing);
		s.append(",送加油卡=").append(songJiaYouKa);
		s.append(",积分回报=").append(jiFenHuiBao);
		s.append(",生日免单=").append(shengRiMianDan);
		s.append(",套餐档次=").append(taoCanDangCi);
		s.append(",协议款捆绑=").append(xieYiKuanKunBang);
		s.append(",赠送套餐费捆绑=").append(zengSongTaoCanFeiKunBang);
		s.append(",赠送时长捆绑=").append(zengSongShiCHangKunBang);
		s.append(",最低协议消费捆绑=").append(zuiDiXieYiXiaoFeiKunBang);
		s.append(",合户捆绑=").append(heHuKunBang);
		s.append(",捆绑标志=").append(kunBangBiaoZhi);
		s.append(",上月时长=").append(shangYueShiChang);
		s.append(",当月时长=").append(dangYueShiChang);
		s.append(",时长提升=").append(shiChangTiSheng);
		s.append(",手机机型=").append(shouJiLeiXing);
		s.append(",机型=").append(jiXing);
		s.append(",智能机=").append(zhiNengJi);
		s.append(",TD机=").append(TdJi);
		return s.toString();
	}

	@Override
	public String findKeyColumnName()
	{
		return "shouJiHao";
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
		return "customer_my";
	}

	public String getShouJiHao()
	{
		return shouJiHao;
	}

	public void setShouJiHao(String shouJiHao)
	{
		this.shouJiHao = shouJiHao;
	}

	public String getZhuangTai()
	{
		return zhuangTai;
	}

	public void setZhuangTai(String zhuangTai)
	{
		this.zhuangTai = zhuangTai;
	}

	public String getZhuanTaiShiJian()
	{
		return zhuanTaiShiJian;
	}

	public void setZhuanTaiShiJian(String zhuanTaiShiJian)
	{
		this.zhuanTaiShiJian = zhuanTaiShiJian;
	}

	public String getZuiHouTongHua()
	{
		return zuiHouTongHua;
	}

	public void setZuiHouTongHua(String zuiHouTongHua)
	{
		this.zuiHouTongHua = zuiHouTongHua;
	}

	public String getQuXian()
	{
		return quXian;
	}

	public void setQuXian(String quXian)
	{
		this.quXian = quXian;
	}

	public String getDanWei()
	{
		return danWei;
	}

	public void setDanWei(String danWei)
	{
		this.danWei = danWei;
	}

	public String getJiTuanMingCheng()
	{
		return jiTuanMingCheng;
	}

	public void setJiTuanMingCheng(String jiTuanMingCheng)
	{
		this.jiTuanMingCheng = jiTuanMingCheng;
	}

	public String getKeHuJingLiDaiMa()
	{
		return keHuJingLiDaiMa;
	}

	public void setKeHuJingLiDaiMa(String keHuJingLiDaiMa)
	{
		this.keHuJingLiDaiMa = keHuJingLiDaiMa;
	}

	public String getKeHuJingLi()
	{
		return keHuJingLi;
	}

	public void setKeHuJingLi(String keHuJingLi)
	{
		this.keHuJingLi = keHuJingLi;
	}

	public String getWlanTaoCan()
	{
		return wlanTaoCan;
	}

	public void setWlanTaoCan(String wlanTaoCan)
	{
		this.wlanTaoCan = wlanTaoCan;
	}

	public String getQuanQiuTongTaoCan()
	{
		return quanQiuTongTaoCan;
	}

	public void setQuanQiuTongTaoCan(String quanQiuTongTaoCan)
	{
		this.quanQiuTongTaoCan = quanQiuTongTaoCan;
	}

	public String getTeHuiJiHua()
	{
		return teHuiJiHua;
	}

	public void setTeHuiJiHua(String teHuiJiHua)
	{
		this.teHuiJiHua = teHuiJiHua;
	}

	public String getQuanJiaFu()
	{
		return quanJiaFu;
	}

	public void setQuanJiaFu(String quanJiaFu)
	{
		this.quanJiaFu = quanJiaFu;
	}

	public String getHuanLeJiaTing()
	{
		return huanLeJiaTing;
	}

	public void setHuanLeJiaTing(String huanLeJiaTing)
	{
		this.huanLeJiaTing = huanLeJiaTing;
	}

	public String getSongJiaYouKa()
	{
		return songJiaYouKa;
	}

	public void setSongJiaYouKa(String songJiaYouKa)
	{
		this.songJiaYouKa = songJiaYouKa;
	}

	public String getJiFenHuiBao()
	{
		return jiFenHuiBao;
	}

	public void setJiFenHuiBao(String jiFenHuiBao)
	{
		this.jiFenHuiBao = jiFenHuiBao;
	}

	public String getShengRiMianDan()
	{
		return shengRiMianDan;
	}

	public void setShengRiMianDan(String shengRiMianDan)
	{
		this.shengRiMianDan = shengRiMianDan;
	}

	public String getTaoCanDangCi()
	{
		return taoCanDangCi;
	}

	public void setTaoCanDangCi(String taoCanDangCi)
	{
		this.taoCanDangCi = taoCanDangCi;
	}

	public String getXieYiKuanKunBang()
	{
		return xieYiKuanKunBang;
	}

	public void setXieYiKuanKunBang(String xieYiKuanKunBang)
	{
		this.xieYiKuanKunBang = xieYiKuanKunBang;
	}

	public String getZengSongTaoCanFeiKunBang()
	{
		return zengSongTaoCanFeiKunBang;
	}

	public void setZengSongTaoCanFeiKunBang(String zengSongTaoCanFeiKunBang)
	{
		this.zengSongTaoCanFeiKunBang = zengSongTaoCanFeiKunBang;
	}

	public String getZengSongShiCHangKunBang()
	{
		return zengSongShiCHangKunBang;
	}

	public void setZengSongShiCHangKunBang(String zengSongShiCHangKunBang)
	{
		this.zengSongShiCHangKunBang = zengSongShiCHangKunBang;
	}

	public String getZuiDiXieYiXiaoFeiKunBang()
	{
		return zuiDiXieYiXiaoFeiKunBang;
	}

	public void setZuiDiXieYiXiaoFeiKunBang(String zuiDiXieYiXiaoFeiKunBang)
	{
		this.zuiDiXieYiXiaoFeiKunBang = zuiDiXieYiXiaoFeiKunBang;
	}

	public String getHeHuKunBang()
	{
		return heHuKunBang;
	}

	public void setHeHuKunBang(String heHuKunBang)
	{
		this.heHuKunBang = heHuKunBang;
	}

	public String getKunBangBiaoZhi()
	{
		return kunBangBiaoZhi;
	}

	public void setKunBangBiaoZhi(String kunBangBiaoZhi)
	{
		this.kunBangBiaoZhi = kunBangBiaoZhi;
	}

	public String getShangYueShiChang()
	{
		return shangYueShiChang;
	}

	public void setShangYueShiChang(String shangYueShiChang)
	{
		this.shangYueShiChang = shangYueShiChang;
	}

	public String getDangYueShiChang()
	{
		return dangYueShiChang;
	}

	public void setDangYueShiChang(String dangYueShiChang)
	{
		this.dangYueShiChang = dangYueShiChang;
	}

	public String getShiChangTiSheng()
	{
		return shiChangTiSheng;
	}

	public void setShiChangTiSheng(String shiChangTiSheng)
	{
		this.shiChangTiSheng = shiChangTiSheng;
	}

	public String getShouJiLeiXing()
	{
		return shouJiLeiXing;
	}

	public void setShouJiLeiXing(String shouJiLeiXing)
	{
		this.shouJiLeiXing = shouJiLeiXing;
	}

	public String getJiXing()
	{
		return jiXing;
	}

	public void setJiXing(String jiXing)
	{
		this.jiXing = jiXing;
	}

	public String getZhiNengJi()
	{
		return zhiNengJi;
	}

	public void setZhiNengJi(String zhiNengJi)
	{
		this.zhiNengJi = zhiNengJi;
	}

	public String getTdJi()
	{
		return TdJi;
	}

	public void setTdJi(String tdJi)
	{
		TdJi = tdJi;
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