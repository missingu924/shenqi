package com.wuyg.common.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSON;
import com.wuyg.common.obj.DwzResponseObj;

public class DwzUtil
{
	public static String getDwz(String url) throws Exception
	{
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://dwz.cn/create.php");
		post.setParameter("url", url);
		int status = client.executeMethod(post);
		if (status==HttpStatus.SC_OK)
		{
			System.out.println(post.getResponseBodyAsString());
			DwzResponseObj response = JSON.parseObject(post.getResponseBodyAsString(), DwzResponseObj.class);
			return response.getTinyurl();
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception
	{
		System.err.println(getDwz("http://115.29.146.96:8080/dafeng/PurchaseOrder/Servlet?method=detail&4m=true&cpoid=0000000003"));
	}
	
	
}
