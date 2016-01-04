package com.wuyg.common.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil
{
	/**
	 * 截取字符串
	 * 
	 * @param s
	 * @param startStr
	 * @param endStr
	 * @return
	 */
	public static String substr(String s, String startStr, String endStr)
	{
		return s.substring(s.indexOf(startStr) + startStr.length(), s.indexOf(endStr, s.indexOf(startStr) + startStr.length()));
	}

	/**
	 * 根据String List获得用 号分割的字符串,每个数据项都用单引号引起来
	 * 
	 * @param stringList
	 * @return
	 */
	public static String getStringByListWithQuotation(List stringList)
	{
		return getStringByList(stringList, true);
	}

	/**
	 * 根据String List获得用逗号分割的字符串,每个数据项都不用单引号引起来
	 * 
	 * @param stringList
	 * @return
	 */
	public static String getStringByListNoQuotation(List stringList)
	{
		return getStringByList(stringList, false);
	}

	/**
	 * 根据String List获得用逗号分割的问号字符串,用于数据库操作时的占位符
	 * 
	 * @param stringList
	 * @return
	 */
	public static String getQuestionMarkStringByList(List stringList)
	{
		if (stringList == null || stringList.size() == 0)
		{
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < stringList.size(); i++)
		{
			sb.append("?");
			if (i != stringList.size() - 1)
			{
				sb.append(",");
			}
		}

		return sb.toString();
	}

	/**
	 * 根据String List获得用逗号分割的字符串，如果withQuotation=true则每个数据项都用单引号引起来
	 * 
	 * 
	 * @param stringList
	 * @param withQuotation
	 * @return
	 */
	public static String getStringByList(List stringList, boolean withQuotation)
	{
		if (stringList == null || stringList.size() == 0)
		{
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < stringList.size(); i++)
		{
			if (withQuotation)
			{
				sb.append("'");
			}
			sb.append(stringList.get(i));
			if (withQuotation)
			{
				sb.append("'");
			}
			if (i != stringList.size() - 1)
			{
				sb.append(",");
			}
		}

		return sb.toString();
	}

	public static String getStringByList(String[] stringArray, boolean withQuotation)
	{
		if (stringArray == null || stringArray.length == 0)
		{
			return "";
		}

		List<String> stringList = new ArrayList<String>();

		for (int i = 0; i < stringArray.length; i++)
		{
			stringList.add(stringArray[i]);
		}

		return getStringByList(stringList, withQuotation);
	}

	/**
	 * 根据逗号或分号将字符串拆分为list，注意：逗号和分号都会认作是分割符
	 * 
	 * @param listString
	 * @return
	 */
	public static List<String> getStringListByString(String listString)
	{
		List<String> stringList = new ArrayList<String>();
		if (!isEmpty(listString))
		{
			String[] temp = listString.split(",|;|\\|");
			for (int i = 0; i < temp.length; i++)
			{
				if (!isEmpty(temp[i]))
				{
					stringList.add(temp[i]);
				}
			}
		}

		return stringList;
	}

	public static List<String> getStringListByString(String listString, char separator, char quoter)
	{
		List<String> stringList = new ArrayList<String>();
		if (!isEmpty(listString))
		{
			// String[] temp = listString.split(separator);
			//
			// String columnStr = "";
			// boolean inQuotation = false;
			// for (int i = 0; i < temp.length; i++)
			// {
			// columnStr += temp[i];
			// if (temp[i].trim().contains(quoter))
			// {
			// if (inQuotation == false)
			// {
			// inQuotation = true;
			// } else
			// {
			// inQuotation = false;
			// }
			// }
			//
			// if (inQuotation == true)
			// {
			// columnStr += separator;
			// } else
			// {
			// if (!isEmpty(columnStr))
			// {
			// stringList.add(columnStr);
			// }
			// columnStr = "";
			// }
			// }

			char[] chars = listString.toCharArray();
			String s = "";
			boolean inQuotation = false;
			for (int i = 0; i < chars.length; i++)
			{
				if (i == chars.length - 1)
				{
					s += chars[i];
					if (!isEmpty(s.trim()))
					{
						stringList.add(s);
					}
					break;
				}

				if (chars[i] == separator)
				{
					if (inQuotation)
					{
						s += chars[i];
					} else if (!isEmpty(s.trim()))
					{
						stringList.add(s);
						s = "";
					}
				} else
				{
					s += chars[i];

					if (chars[i] == quoter)
					{
						if (inQuotation)
						{
							inQuotation = false;
							if (!isEmpty(s.trim()))
							{
								stringList.add(s);
								s = "";
							}
						} else
						{
							inQuotation = true;
						}
					}
				}
			}
		}

		return stringList;
	}

	/**
	 * 判断ListA中的字符串是否都在ListB??
	 * 
	 * @param listA
	 * @param listB
	 * @return
	 */
	public static boolean isListAInListB(List<String> listA, List<String> listB)
	{
		for (int i = 0; i < listA.size(); i++)
		{
			boolean in = false;
			for (int j = 0; j < listB.size(); j++)
			{
				if (listB.get(j).equalsIgnoreCase(listA.get(i)))
				{
					in = true;
					break;
				}
			}
			if (!in)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否为null或为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str)
	{
		return str == null || str.trim().equals("") || "null".equalsIgnoreCase(str.trim());
	}

	public static String trim(String str)
	{
		if (isEmpty(str))
		{
			return "";
		} else
		{
			return str.trim();
		}
	}

	/**
	 * 根据String List获得用separator分割的字符串
	 * 
	 * @param stringList
	 * @param withQuotation
	 * @return
	 */
	public static String getStringByList(List stringList, String separator)
	{
		if (stringList == null || stringList.size() == 0)
		{
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < stringList.size(); i++)
		{
			sb.append(stringList.get(i));
			if (i != stringList.size() - 1)
			{
				sb.append(separator);
			}
		}

		return sb.toString();
	}

	/**
	 * 如果字符串为null则返回空字符串，否则返回原字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String getNotEmptyStr(Object checkObj)
	{
		return getNotEmptyStr(checkObj, "");
	}

	public static String getNotEmptyStr(Object checkObj, String returnString)
	{
		if (checkObj == null)
		{
			return returnString;
		} else
		{
			if (isEmpty(checkObj.toString()))
			{
				return returnString;
			} else
			{
				return checkObj.toString();
			}
		}
	}

	public static String getStringByList(String[] stringArray, String separator)
	{
		if (stringArray == null || stringArray.length == 0)
		{
			return "";
		}

		List<String> stringList = new ArrayList<String>();

		for (int i = 0; i < stringArray.length; i++)
		{
			stringList.add(stringArray[i]);
		}

		return getStringByList(stringList, separator);
	}

	public static int parseInt(String intStr) throws Exception
	{
		return isEmpty(intStr) ? 0 : Integer.parseInt(intStr);
	}

	public static long parseLong(String longStr) throws Exception
	{
		return isEmpty(longStr) ? 0 : Long.parseLong(longStr);
	}

	public static double parseDouble(String doubleStr) throws Exception
	{
		return isEmpty(doubleStr) ? 0 : Double.parseDouble(doubleStr);
	}

	// 首字母大写
	public static String upperFirstChar(String name)
	{
		if (!isEmpty(name))
		{
			return name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
		}

		return null;
	}

	// 将输入的名字转换为标准的类名
	public static String toClassName(String name)
	{
		// 首字母大写,下划线去掉，下划线后的第一个字母大写
		String[] tmp = name.split("_");

		String className = "";
		for (int i = 0; i < tmp.length; i++)
		{
			className += upperFirstChar(tmp[i]);
		}

		return className;
	}

	/**
	 * 数组中是否包含指定字符串
	 * 
	 * @param strArray
	 * @param key
	 * @return
	 */
	public static boolean contains(String[] strArray, String key)
	{
		if (strArray != null && key != null)
		{
			for (int i = 0; i < strArray.length; i++)
			{
				if (key.equals(strArray[i]))
				{
					return true;
				}
			}
		}

		return false;
	}

	public static void main(String[] args)
	{
		System.out.println(toClassName("a_b"));
	}
}
