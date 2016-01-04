package com.wuyg.task;

import java.util.ArrayList;
import java.util.List;

public class TaskConstant
{
	// ��������
	public static final String TP_DRAFT = "�༭�ݸ�";
	public static final String TP_SUBMIT = "�ύ����";
	public static final String TP_AUDIT = "�ɵ�����";
	// public static final String TP_PROCESS = "TP_PROCESS";
	public static final String TP_FEEDBACK = "�׶η���";
	public static final String TP_FORWRD = "����ת��";
	public static final String TP_REJECT = "����";
	public static final String TP_REVERT = "�����ظ�";
	public static final String TP_ARCHIVE_VERIFY = "�鵵���";
	public static final String TP_ARCHIVE = "�鵵";

	// ����״̬
	public static final String STATUS_NEED_SUBMIT = "���ύ";
	public static final String STATUS_NEED_AUDIT = "������";
	public static final String STATUS_NEED_REVERT = "������";
	public static final String STATUS_NEED_ARCHIVE_VERIFY = "���鵵���";
	public static final String STATUS_FEEDBACKED = "�ѽ׶η���";
	public static final String STATUS_FORWRDED = "��ת��";
	public static final String STATUS_REJECTED = "�Ѳ���";
	public static final String STATUS_ARCHIVED = "�ѹ鵵";

	public static final String RESULT_AGREED = "ͬ��";
	public static final String RESULT_REJECT = "����";
	public static final String RESULT_FAILED = "����ʧ��";
	public static final String RESULT_SUCCESS = "����ɹ�";

	public static final String CRITICAL_LEVEL_COMMON = "һ��";
	public static final String CRITICAL_LEVEL_URGENT = "����";
	public static final String CRITICAL_LEVEL_VERY_URGENT = "�ǳ�����";

	// ������������
	public static final String SUBJECT_TYPE_PERSON = "SUBJECT_TYPE_PERSON";
	public static final String SUBJECT_TYPE_DEPARTMENT = "SUBJECT_TYPE_DEPARTMENT";
	public static final String SUBJECT_TYPE_GROUP = "SUBJECT_TYPE_GROUP";

	// ��ǰ�û��빤���Ĺ�ϵ
	public static final String RELATIONSHITP_NEED_PROCESS = "NEED_PROCESS";// ������
	public static final String RELATIONSHITP_PROCESSED = "PROCESSED";// �Ѵ���δ�鵵
	public static final String RELATIONSHITP_ARCHIVED = "ARCHIVED";// �ѹ鵵
	public static final String RELATIONSHITP_RELEATED = "RELEATED";// �й�ϵ������������ġ�������ġ���������صģ�

	// ���ƻ��ֶε��������
	public static final int MAX_CUST_COL_COUNT = 50;
	public static final String CUST_COL_PLACEHOLDER = "ռλ";
	public static String CUST_COLNAME_WIDTH = "20%";
	public static String CUST_COLVAUE_WIDTH = "30%";

	// ����ԤԼ״̬
	public static final String TEL_REVERSE_STATUS_REVERSING = "ԤԼ��";
	public static final String TEL_REVERSE_STATUS_TIMEOUT = "ԤԼ��ʱ";
	public static final String TEL_REVERSE_STATUS_SUCCESS = "ԤԼ�ɹ�";
	public static final String TEL_REVERSE_STATUS_FAILED = "ԤԼʧ��";

	// ����ȼ�
	public static final String TEL_LEVEL_NONE = "�Ǽ������";
	public static final String TEL_LEVEL_1 = "һ�����";
	public static final String TEL_LEVEL_2 = "�������";
	public static final String TEL_LEVEL_3 = "�������";
	public static final String TEL_LEVEL_4 = "�������";
	public static final String TEL_LEVEL_5 = "�������";

	public static String getProcessEnName(String processCnName)
	{
		if (TP_DRAFT.equals(processCnName) || TP_SUBMIT.equals(processCnName))
		{
			return "draft";
		} else if (TP_AUDIT.equals(processCnName))
		{
			return "audit";
		} else if (TP_REVERT.equals(processCnName))
		{
			return "revert";
		} else if (TP_ARCHIVE_VERIFY.equals(processCnName))
		{
			return "archiveVerify";
		}
		return processCnName;
	}
}
