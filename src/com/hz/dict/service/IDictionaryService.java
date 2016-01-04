package com.hz.dict.service;

import java.util.List;

import com.inspur.common.dictionary.pojo.Dict;
import com.inspur.common.dictionary.pojo.DictItem;

public interface IDictionaryService
{
	/**
	 * �����ֵ�����ȡ�ֵ�����
	 * 
	 * @param dictName
	 * @return
	 */
	public List<DictItem> getDictItemsByDictName(String dictName, boolean addItemForAll);

	/**
	 * �����ֵ�������Ϣ��ȡ�ֵ�����
	 * 
	 * @param dict
	 * @return
	 */
	public List<DictItem> getDictItemsByDict(Dict dict, boolean addItemForAll);

	/**
	 * �����ֵ��Ĺ�ϵ��ȡ����ֵ�����
	 * 
	 * @param fromDictName
	 *            Դ�ֵ���
	 * @param toDictName
	 *            Ŀ���ֵ���
	 * @param fromDictKey
	 *            Ŀ���ֵ�����Դ�ֵ�������ֶ���
	 * @return
	 */
	public List<DictItem> getDictItemsFromTo(String fromDictName, String toDictName, String fromDictKey, boolean addItemForAll);
}
