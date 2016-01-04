// JavaScript Document
// 用于工单定制化字段扩展的函数，注意这里面的函数都不能传递参数

function test() {
	alert("");
}

/**
 * 获取定制化字段并更新到制定的div中
 * 
 * @param {}
 *            taskType 工单大类
 * @param {}
 *            taskSubType 工单小类
 * @param {}
 *            taskProcessName 环节名称
 * @param {}
 *            custColValues 定制化列名值对
 * @param {}
 *            forInput 是否用于输入（是的话生成输入框，否的话只生成展现用的表格）
 * @param {}
 *            divId 指定返回的数据更新的那个div中
 */
function getCustColsInputHtml(taskType, taskSubType, taskProcessName,
		custColValues, forInput, divId) {
	$.get("Servlet?method=getTaskCustColsInputHtml", {
				taskType : taskType,
				taskSubType : taskSubType,
				taskProcessName : taskProcessName,
				custColValues : custColValues,
				forInput : forInput
			}, function(data, textStatus) {
				this;
				$("#" + divId).html(data);
			});
}

/**
 * 获取radio的选中值
 * 
 * @param {}
 *            RadioName
 * @return {}
 */
function getRadioValue(RadioName) {
	var obj;
	obj = document.getElementsByName(RadioName);
	if (obj != null) {
		var i;
		for (i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				return obj[i].value;
			}
		}
	}
	return null;
}

/**
 * 根据终端品牌级联更新终端型号
 * 
 * @param {}
 *            phoneBrandInputLabel 终端品牌input框的label值
 * @param {}
 *            phoneModelInputLable 终端型号input框的lable值
 */
function changePhoneModelByPhoneBrand(phoneBrandInputLabel,
		phoneModelInputLable) {
	var phoneBrandInput = $("[label='" + phoneBrandInputLabel + "']");
	var phoneModelInput = $("[label='" + phoneModelInputLable + "']");

	$(phoneBrandInput).change(function() {
		$.get("../Util/Servlet?method=getDictHtml", {
					fromDictName : "phoneBrand",
					toDictName : "phoneModel",
					fromDictKey : phoneBrandInput.val(),
					selectName : phoneModelInput.attr("name"),
					notEmpty : phoneModelInput.attr("class") ? phoneModelInput
							.attr("class") : ""
				}, function(data, textStatus) {
					this;
					// 级联更新
					$(phoneModelInput).html(data);
				});
	});
}

/**
 * 他网客户回归时， 根据“用户使用范围”改变“具体范围”字段
 * 
 * @param {}
 *            rangeInputLabel 用户使用范围：本地、省内异地、国内异地
 * @param {}
 *            addressInputLable 具体范围
 */
function changeAddressByRange(rangeInputLabel, addressInputLable) {
	var rangeInput = $("[label='" + rangeInputLabel + "']");
	var addressInput = $("[label='" + addressInputLable + "']");

	$(rangeInput).change(function() {
		var dictName = "";
		var rangeInputValue = rangeInput.val();
		if ("本地" == rangeInputValue)
			dictName = "district";
		else if ("省内异地" == rangeInputValue)
			dictName = "sdCity";
		else if ("国内异地" == rangeInputValue)
			dictName = "city";

		$.get("../Util/Servlet?method=getDictHtml", {
					dictName : dictName,
					selectName : addressInput.attr("name"),
					notEmpty : (addressInput.attr("class")) ? addressInput
							.attr("class") : ""
				}, function(data, textStatus) {
					this;
					// 级联更新
					$(addressInput).html(data);
				});
	});
}

/**
 * 终端归位-工单回复：如果 工单处理成功，则IMEI必须填写
 * 
 * @param {}
 *            inputLabel
 */
function checkImeiForZhongduanguiwei(imeiInputLabel) {
	var imeiInput = $("[label='" + imeiInputLabel + "']");
	
	if (getRadioValue("processResult") == "处理成功") {
		imeiInput.addClass("notEmpty");// 不能为空的css
		
		var imeiReg15 = /[0-9]{15}/;
		var imeiReg18 = /[0-9]{18}/;
		if (!imeiReg15.test($(imeiInput).val())  && !imeiReg18.test($(imeiInput).val()) ) {
				alert(name + ' IMEI必须是15位或18为数字');
				$(imeiInput).focus();
				return false;
		}
	}
	else
	{
		imeiInput.removeClass("notEmpty");
	}
}

/**
 *  如果处理成功则该字段必须填写
 * @param {} inputLabel 必须填写的字段对应的label
 */
function notEmptyWhenSuccess(inputLabel) {
	var myInput = $("[label='" + inputLabel + "']");
	
	if (getRadioValue("processResult") == "处理成功") {
		myInput.addClass("notEmpty");// 不能为空的css
	}
	else
	{
		myInput.removeClass("notEmpty");
	}
}

/**
 * 宽带促销-工单回复：如果捆绑了终端，而且处理成功，则IMEI必须填写
 * 
 * @param {}
 *            inputLabel
 */
function checkImeiForKuandaiyingxiao(imeiInputLabel) {
	if ($("#是否捆绑终端").text()=="捆绑") {
		checkImeiForZhongduanguiwei(imeiInputLabel);// 不能为空的css
	}
}

/**
 * 进行号码预约
 * @param {} inputId 选择预约的号码后填写到哪个输入框中
 */
function reserveTelNum(inputLabel) {
	
	var reserveTelNumInput = $("[label='" + inputLabel + "']");
	
	$(reserveTelNumInput).click(function(){
	if(!checkTelephone("telNum","用户号码")) return false; 
	
	var returnValue = window.showModalDialog(
			'/10088/Customer/Servlet?method=getUsableTelNums&otherTelNum='+$("#telNum").val(), '',
			'dialogHeight:650px;dialogWidth:600px;resizable:yes;maximize:yes');

	if (returnValue != null) {
		$("[label='" + inputLabel + "']").val(returnValue);
	}
	});
}