package com.hz.dict.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hz.util.SystemConstant;
import com.inspur.common.dictionary.Dictionary;
import com.inspur.common.dictionary.pojo.Dict;
import com.inspur.common.dictionary.pojo.DictItem;
import com.wuyg.common.util.MySqlUtil;

public class DictionaryService implements IDictionaryService
{
	private Logger logger = Logger.getLogger(getClass());
	private Dictionary dictionary = new Dictionary();
	private final String pleaseSelect = "--«Î—°‘Ò--";

	public List<DictItem> getDictItemsByDict(Dict dict, boolean addItemForAll)
	{
		try
		{
			Connection conn = MySqlUtil.getConnection(SystemConstant.DEFAULT_DB);
			List<DictItem> items = dictionary.getDictItemsByDict(dict, addItemForAll, conn);
			if (addItemForAll==false)
			{
				items.add(0, new DictItem(0,"",pleaseSelect));
			}
			return items;
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return new ArrayList<DictItem>();
	}

	public List<DictItem> getDictItemsByDictName(String dictName, boolean addItemForAll)
	{
		try
		{
			Connection conn = MySqlUtil.getConnection(SystemConstant.DEFAULT_DB);
			List<DictItem> items = dictionary.getDictItemsByDictName(dictName, addItemForAll, conn);
			if (addItemForAll==false)
			{
				items.add(0, new DictItem(0,"",pleaseSelect));
			}
			return items;
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return new ArrayList<DictItem>();
	}
	
	public List<DictItem> getDictItemsByDictNameExcludeKey(String dictName, boolean addItemForAll, String excloudKey)
	{
		List<DictItem> tempItems = getDictItemsByDictName(dictName, addItemForAll);
		
		List<DictItem> items = new ArrayList<DictItem>();
		
		for (int i = 0; i < tempItems.size(); i++)
		{
			DictItem item = tempItems.get(i);
			if (!item.getK().equalsIgnoreCase(excloudKey))
			{
				items.add(item);
			}
		}
		
		return items;
	}

	public List<DictItem> getDictItemsFromTo(String fromDictName, String toDictName, String fromDictKey, boolean addItemForAll)
	{
		try
		{
			Connection conn = MySqlUtil.getConnection(SystemConstant.DEFAULT_DB);
			List<DictItem> items = dictionary.getDictItemsFromTo(fromDictName, toDictName, fromDictKey, addItemForAll, conn);
			if (addItemForAll==false)
			{
				items.add(0, new DictItem(0,"",pleaseSelect));
			}
			return items;
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return new ArrayList<DictItem>();
	}

}
