package com.wuyg.stock;

import java.sql.Timestamp;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.common.util.TimeUtil;
import com.wuyg.stock.obj.StockObj;

public class StockCollectThread extends Thread
{
	private String code;

	public StockCollectThread(String code)
	{
		this.code = code;
	}

	@Override
	public void run()
	{
		try
		{

			HttpClient client = new HttpClient();

			GetMethod get = new GetMethod("http://hq.sinajs.cn/list=" + code);

			int status = client.executeMethod(get);

			if (status == HttpStatus.SC_OK)
			{
				String str = get.getResponseBodyAsString();

				if (str.length() > 30)
				{
					StockObj stockObj = new StockObj(str);

					IBaseDAO dao = new DefaultBaseDAO(stockObj.getClass());

					Object obj = dao.searchByKey(stockObj.getClass(), stockObj.getId());
					if (obj == null)
					{
						dao.save(stockObj);
//						System.out.println("save " + stockObj.getId() + " " + TimeUtil.nowTime2str());
					}
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
