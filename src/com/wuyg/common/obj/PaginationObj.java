package com.wuyg.common.obj;

import java.util.ArrayList;
import java.util.List;

public class PaginationObj
{
	private List dataList = new ArrayList(); // ��ǰҳ������
	private int totalCount;// �ܵ�����
	private int pageNo;// ��ǰ��ҳ��
	private int pageCount;// ÿҳ������
	public static final PaginationObj NULL_PAGE = new PaginationObj();// ��ҳ

	public PaginationObj()
	{
		this.dataList = new ArrayList<Object>();
		this.totalCount = 0;
		this.pageNo = 1;
		this.pageCount = 10;
	}

	public PaginationObj(List<Object> dataList, int totalCount, int pageNo, int pageCount)
	{
		this.dataList = dataList;
		this.totalCount = totalCount;
		this.pageNo = pageNo;
		this.pageCount = pageCount;
	}

	// ��ҳ��
	public int getTotalPage()
	{
		if (totalCount % pageCount == 0)
		{
			return totalCount / pageCount;
		} else
		{
			return totalCount / pageCount + 1;
		}
	}

	// ����һҳ
	public boolean isNext()
	{
		return pageNo < getTotalPage();
	}

	// ����һҳ
	public boolean isPrevious()
	{
		return pageNo > 1;
	}

	public List getDataList()
	{
		return dataList;
	}

	public void setDataList(List dataList)
	{
		this.dataList = dataList;
	}

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	public int getPageNo()
	{
		return pageNo;
	}

	public void setPageNo(int pageNo)
	{
		this.pageNo = pageNo;
	}

	public int getPageCount()
	{
		return pageCount;
	}

	public void setPageCount(int pageCount)
	{
		this.pageCount = pageCount;
	}

}
