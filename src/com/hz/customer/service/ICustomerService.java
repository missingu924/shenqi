package com.hz.customer.service;

import java.sql.Timestamp;
import java.util.List;

import com.hz.customer.obj.MyCustomerDbObj;
import com.hz.customer.obj.OtherCustomerDbObj;
import com.hz.customer.obj.ReservedTelNumDbObj;
import com.hz.customer.obj.UsableTelNumDbObj;
import com.inspur.common.tree.Node;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.task.obj.TaskMainInfoDbObj;

public interface ICustomerService
{
	/**
	 * 根据用户号码获取我网客户信息
	 * 
	 * @param telNum
	 * @return
	 * @throws Exception
	 */
	public MyCustomerDbObj getMyCustomerInfo(String telNum) throws Exception;

	/**
	 * 根据用户号码获取他网客户信息
	 * 
	 * @param telNum
	 * @return
	 * @throws Exception
	 */
	public OtherCustomerDbObj getOtherCustomerInfo(String telNum) throws Exception;

	/**
	 * 根据对手号码获取可预约的号码，包括：后n位（9、8、7、6、5、4）相同的号码+吉祥号码（吉祥号等级<=他网号码等级+1级）+随机的20个非吉祥号
	 * 
	 * @param telNum
	 * @return
	 * @throws Exception
	 */
	public List<UsableTelNumDbObj> getUsableTelNum(String telNum) throws Exception;

	/**
	 * 预约号码
	 * 
	 * @param telNum
	 * @return
	 * @throws Exception
	 */
	public boolean reserveTelNum(ReservedTelNumDbObj reservedTelNum) throws Exception;

	/**
	 * 释放预约超时的号码。删除预约号码表中超过三天你的号码
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean releaseTimeOutReservedTelNum() throws Exception;

	/**
	 * 查询需要给BOSS报的已预约号码。"预约未过期" 且 "工单当前处理单位是一个具体部门" 且 "可用号码表中也存在" 且 "可用号码表中归属单位==SD.LJ"
	 * 
	 * @return
	 * @throws Exception
	 */
	public PaginationObj searchReservedTelNums4Boss(String reserveTelNum, String otherTelNum, int pageNo, int pageCount) throws Exception;

	/**
	 * 查询需释放的预约超时号码，放回BOSS的可用号码中。"预约已过期" 但 "可用号码表中仍然存在" 且 "可用号码表中归属单位!=SD.LJ" 
	 * 
	 * @return
	 * @throws Exception
	 */
	public PaginationObj searchReleasedTelNums4Boss(String reserveTelNum, String otherTelNum, int pageNo, int pageCount) throws Exception;

	/**
	 * 查询需释放的预约超时号码，放回BOSS的可用号码中。预约号码表中已过期 且 可用号码表中
	 * 
	 * @return
	 * @throws Exception
	 */
	public PaginationObj searchReservedTelNumPagintion(String startTime, String endTime, String reserveTelNum, String otherTelNum, String taskId, int pageNo,
			int pageCount) throws Exception;

	/**
	 * 查询可用号码表中当前的数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public PaginationObj searchAllUsableTelNumPagintion(boolean exceptReversedTelNum, String telNum, int pageNo, int pageCount) throws Exception;
}
