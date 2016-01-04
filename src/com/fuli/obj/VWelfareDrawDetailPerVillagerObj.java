package com.fuli.obj;

import java.util.ArrayList;
import java.util.List;

public class VWelfareDrawDetailPerVillagerObj
{
	private VillagerObj villager;
	
	private List<VWelfareDrawDetailObj> vwelfareDrawDetailList=new ArrayList<VWelfareDrawDetailObj>();

	public VillagerObj getVillager()
	{
		return villager;
	}

	public void setVillager(VillagerObj village)
	{
		this.villager = village;
	}

	public List<VWelfareDrawDetailObj> getVwelfareDrawDetailList()
	{
		return vwelfareDrawDetailList;
	}

	public void setVwelfareDrawDetailList(List<VWelfareDrawDetailObj> vwelfareDrawDetailList)
	{
		this.vwelfareDrawDetailList = vwelfareDrawDetailList;
	}
}
