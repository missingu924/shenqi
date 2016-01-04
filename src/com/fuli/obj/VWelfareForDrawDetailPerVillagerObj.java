package com.fuli.obj;

import java.util.ArrayList;
import java.util.List;

public class VWelfareForDrawDetailPerVillagerObj
{
	// 村民信息
	private VillagerObj villager;

	// 该村民享受的福利
	private List<VWelfareForDrawDetailObj> vwelfareForDrawDetailList = new ArrayList<VWelfareForDrawDetailObj>();

	public VillagerObj getVillager()
	{
		return villager;
	}

	public void setVillager(VillagerObj villager)
	{
		this.villager = villager;
	}

	public List<VWelfareForDrawDetailObj> getVwelfareForDrawDetailList()
	{
		return vwelfareForDrawDetailList;
	}

	public void setVwelfareForDrawDetailList(List<VWelfareForDrawDetailObj> vwelfareForDrawDetailList)
	{
		this.vwelfareForDrawDetailList = vwelfareForDrawDetailList;
	}
}
