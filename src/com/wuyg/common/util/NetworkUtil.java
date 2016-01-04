package com.wuyg.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class NetworkUtil
{
	public static void main(String[] args) throws Exception
	{

		System.out.println(getLocalMac());
		
	}

	public static String getLocalMac() throws Exception
	{
		// 获取网卡，获取地址

		byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();

		StringBuffer sb = new StringBuffer("");

		for (int i = 0; i < mac.length; i++)
		{
			if (i != 0)
			{
				sb.append("-");
			}

			// 字节转换为整数

			int temp = mac[i] & 0xff;
			String str = Integer.toHexString(temp);

			if (str.length() == 1)
			{
				sb.append("0" + str);
			} else
			{
				sb.append(str);
			}
		}

		return sb.toString().toUpperCase();
	}
}
