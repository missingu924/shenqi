package com.hz.dict.service;

import java.util.List;

import com.inspur.common.dictionary.pojo.Dict;
import com.inspur.common.dictionary.pojo.DictItem;

public interface IDictionaryService
{
	/**
	 * 根据字典名获取字典数据
	 * 
	 * @param dictName
	 * @return
	 */
	public List<DictItem> getDictItemsByDictName(String dictName, boolean addItemForAll);

	/**
	 * 根据字典配置信息获取字典数据
	 * 
	 * @param dict
	 * @return
	 */
	public List<DictItem> getDictItemsByDict(Dict dict, boolean addItemForAll);

	/**
	 * 根据字典间的关系获取相关字典数据
	 * 
	 * @param fromDictName
	 *            源字典名
	 * @param toDictName
	 *            目的字典名
	 * @param fromDictKey
	 *            目的字典中与源字典关联的字段名
	 * @return
	 */
	public List<DictItem> getDictItemsFromTo(String fromDictName, String toDictName, String fromDictKey, boolean addItemForAll);
}
