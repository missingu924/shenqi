package com.hz.customer.obj;

import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.util.StringUtil;
import com.wuyg.task.TaskConstant;

public class MyCustomerDbObj extends BaseDbObj
{
	private String shouJiHao = "";// �ֻ���
	private String zhuangTai = "";// ״̬
	private String zhuanTaiShiJian = "";// ״̬ʱ��
	private String zuiHouTongHua = "";// ���ͨ��
	private String quXian = "";// ����
	private String danWei = "";// ��λ
	private String jiTuanMingCheng = "";// ��������
	private String keHuJingLiDaiMa = "";// �ͻ��������
	private String keHuJingLi = "";// �ͻ�����
	private String wlanTaoCan = "";// wlan�ײ�
	private String quanQiuTongTaoCan = "";// ȫ��ͨ�ײ�
	private String teHuiJiHua = "";// �ػݼƻ�
	private String quanJiaFu = "";// ȫ�Ҹ�
	private String huanLeJiaTing = "";// ���ּ�ͥ
	private String songJiaYouKa = "";// �ͼ��Ϳ�
	private String jiFenHuiBao = "";// ���ֻر�
	private String shengRiMianDan = "";// �����ⵥ
	private String taoCanDangCi = "";// �ײ͵���
	private String xieYiKuanKunBang = "";// Э�������
	private String zengSongTaoCanFeiKunBang = "";// �����ײ�����
	private String zengSongShiCHangKunBang = "";// ����ʱ������
	private String zuiDiXieYiXiaoFeiKunBang = "";// ���Э����������
	private String heHuKunBang = "";// �ϻ�����
	private String kunBangBiaoZhi = "";// �����־
	private String shangYueShiChang = "";// ����ʱ��
	private String dangYueShiChang = "";// ����ʱ��
	private String shiChangTiSheng = "";// ʱ������
	private String shouJiLeiXing = "";// �ֻ�����
	private String jiXing = "";// ����
	private String zhiNengJi = "";// ���ܻ�
	private String TdJi = "";// td��

	public String toHtml(String colsAndValuesStr)
	{
		if (!StringUtil.isEmpty(shouJiHao))// �����ݿ��в�ѯ��������
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
		td += "	<td width=\""+TaskConstant.CUST_COLNAME_WIDTH+"\" align=\"right\"><strong>" + (StringUtil.getNotEmptyStr(colName.replaceFirst((TaskConstant.CUST_COL_PLACEHOLDER + ":"), "")))
				+ "</strong></td>";
		td += "	<td width=\""+TaskConstant.CUST_COLVAUE_WIDTH+"\" align=\"left\">" + StringUtil.getNotEmptyStr(colValue) + "</td>";

		return td;
	}

	private String getColsAndVauesStr()
	{
		StringBuffer s = new StringBuffer();
		// s.append("�ֻ���=").append(shouJiHao);
		s.append("״̬=").append(zhuangTai);
		s.append(",״̬ʱ��=").append(zhuanTaiShiJian);
		s.append(",���ͨ��=").append(zuiHouTongHua);
		s.append(",����=").append(quXian);
		s.append(",��λ=").append(danWei);
		s.append(",��������=").append(jiTuanMingCheng);
		s.append(",�ͻ��������=").append(keHuJingLiDaiMa);
		s.append(",�ͻ�����=").append(keHuJingLi);
		s.append(",WLAN�ײ�=").append(wlanTaoCan);
		s.append(",ȫ��ͨ�ײ�=").append(quanQiuTongTaoCan);
		s.append(",�ػݼƻ�=").append(teHuiJiHua);
		s.append(",ȫ�Ҹ�=").append(quanJiaFu);
		s.append(",���ּ�ͥ=").append(huanLeJiaTing);
		s.append(",�ͼ��Ϳ�=").append(songJiaYouKa);
		s.append(",���ֻر�=").append(jiFenHuiBao);
		s.append(",�����ⵥ=").append(shengRiMianDan);
		s.append(",�ײ͵���=").append(taoCanDangCi);
		s.append(",Э�������=").append(xieYiKuanKunBang);
		s.append(",�����ײͷ�����=").append(zengSongTaoCanFeiKunBang);
		s.append(",����ʱ������=").append(zengSongShiCHangKunBang);
		s.append(",���Э����������=").append(zuiDiXieYiXiaoFeiKunBang);
		s.append(",�ϻ�����=").append(heHuKunBang);
		s.append(",�����־=").append(kunBangBiaoZhi);
		s.append(",����ʱ��=").append(shangYueShiChang);
		s.append(",����ʱ��=").append(dangYueShiChang);
		s.append(",ʱ������=").append(shiChangTiSheng);
		s.append(",�ֻ�����=").append(shouJiLeiXing);
		s.append(",����=").append(jiXing);
		s.append(",���ܻ�=").append(zhiNengJi);
		s.append(",TD��=").append(TdJi);
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