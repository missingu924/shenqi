package com.wuyg.task;

import java.util.ArrayList;
import java.util.List;

public class TaskConstant
{
	// 工单环节
	public static final String TP_DRAFT = "编辑草稿";
	public static final String TP_SUBMIT = "提交审批";
	public static final String TP_AUDIT = "派单审批";
	// public static final String TP_PROCESS = "TP_PROCESS";
	public static final String TP_FEEDBACK = "阶段反馈";
	public static final String TP_FORWRD = "工单转派";
	public static final String TP_REJECT = "驳回";
	public static final String TP_REVERT = "工单回复";
	public static final String TP_ARCHIVE_VERIFY = "归档审核";
	public static final String TP_ARCHIVE = "归档";

	// 工单状态
	public static final String STATUS_NEED_SUBMIT = "待提交";
	public static final String STATUS_NEED_AUDIT = "待审批";
	public static final String STATUS_NEED_REVERT = "待处理";
	public static final String STATUS_NEED_ARCHIVE_VERIFY = "待归档审核";
	public static final String STATUS_FEEDBACKED = "已阶段反馈";
	public static final String STATUS_FORWRDED = "已转派";
	public static final String STATUS_REJECTED = "已驳回";
	public static final String STATUS_ARCHIVED = "已归档";

	public static final String RESULT_AGREED = "同意";
	public static final String RESULT_REJECT = "驳回";
	public static final String RESULT_FAILED = "处理失败";
	public static final String RESULT_SUCCESS = "处理成功";

	public static final String CRITICAL_LEVEL_COMMON = "一般";
	public static final String CRITICAL_LEVEL_URGENT = "紧急";
	public static final String CRITICAL_LEVEL_VERY_URGENT = "非常紧急";

	// 处理主体类型
	public static final String SUBJECT_TYPE_PERSON = "SUBJECT_TYPE_PERSON";
	public static final String SUBJECT_TYPE_DEPARTMENT = "SUBJECT_TYPE_DEPARTMENT";
	public static final String SUBJECT_TYPE_GROUP = "SUBJECT_TYPE_GROUP";

	// 当前用户与工单的关系
	public static final String RELATIONSHITP_NEED_PROCESS = "NEED_PROCESS";// 待处理
	public static final String RELATIONSHITP_PROCESSED = "PROCESSED";// 已处理未归档
	public static final String RELATIONSHITP_ARCHIVED = "ARCHIVED";// 已归档
	public static final String RELATIONSHITP_RELEATED = "RELEATED";// 有关系（包括处理过的、待处理的、本部门相关的）

	// 定制化字段的最大数量
	public static final int MAX_CUST_COL_COUNT = 50;
	public static final String CUST_COL_PLACEHOLDER = "占位";
	public static String CUST_COLNAME_WIDTH = "20%";
	public static String CUST_COLVAUE_WIDTH = "30%";

	// 号码预约状态
	public static final String TEL_REVERSE_STATUS_REVERSING = "预约中";
	public static final String TEL_REVERSE_STATUS_TIMEOUT = "预约超时";
	public static final String TEL_REVERSE_STATUS_SUCCESS = "预约成功";
	public static final String TEL_REVERSE_STATUS_FAILED = "预约失败";

	// 号码等级
	public static final String TEL_LEVEL_NONE = "非吉祥号码";
	public static final String TEL_LEVEL_1 = "一类号码";
	public static final String TEL_LEVEL_2 = "二类号码";
	public static final String TEL_LEVEL_3 = "三类号码";
	public static final String TEL_LEVEL_4 = "四类号码";
	public static final String TEL_LEVEL_5 = "五类号码";

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
