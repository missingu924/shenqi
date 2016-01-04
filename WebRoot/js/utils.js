// JavaScript Document

// 单元格选择初始化函数
function initTdSelect(selName, inputName) {
	$("[name='" + selName + "']").click(function() {
		var currentTd = $(this);
		$("[name='" + selName + "']").each(function() {
					if (currentTd.text() == $(this).text()) {
						$(this).attr("class", "blue_bg");
						$("[name='" + inputName + "']").val($.trim(currentTd
								.text()));
					} else {
						$(this).attr("class", "gray_bg");
					}
				});
	});
}

function initTdSelectWithAttrName(selName, attrName, inputName) {
	$("[name='" + selName + "']").click(function() {
		var currentTd = $(this);
		$("[name='" + selName + "']").each(function() {
			if (currentTd.text() == $(this).text()) {
				$(this).attr("class", "blue_bg");
				$("[name='" + inputName + "']").val($.trim(currentTd
						.attr(attrName)));
			} else {
				$(this).attr("class", "gray_bg");
			}
		});
	});
}

// 打开页面
function winOpen(url) {
	window.open(url, '_self');
}

// 检查是否为空
function checkNull(id, name) {
	if ($("#" + id).val() == "") {
		$("#" + id).focus();
		alert("请填写 " + name);
		return false;
	}
	return true;
}

// 检查手机号格式
function checkTelephone(id, name) {
	if (!checkNull(id, name))
		return false;

	var phoneNumReg = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
	if (!phoneNumReg.test($("#" + id).val())) {
		alert(name + " 中填写的不是电话号码");
		$("#" + id).focus();
		return false;
	}
	return true
}

// 检查账号格式
function checkAccount(id, name) {
	if (!checkNull(id, name))
		return false;

	var accountReg = /^[a-zA-Z][_a-zA-Z0-9]{4,17}$/;
	if (!accountReg.test($("#" + id).val())) {
		alert("账号不符合格式要求");
		$("#" + id).focus();
		return false;
	}
	return true
}

// 检查身份证号格式
function checkIDcard(id, name) {
	if (!checkNull(id, name))
		return false;

	var accountReg = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
	if (!accountReg.test($("#" + id).val())) {
		alert("身份证号不符合格式要求");
		$("#" + id).focus();
		return false;
	}
	return true
}

// 什么都不检查
function checkSomething(id,name)
{
	return true;
}

// 检查数字
function isNumber(s) {
	var regu = "^[0-9]+$";
	var re = new RegExp(regu);
	if (s.search(re) != -1) {
		return true;
	} else {
		return false;
	}
}

// 选择部门或者人员;输入参数为subject的前,取subject对应的input对象的id前缀。
function selectSubject(inputIdPre, toSubTree, district, departmentId,
		userAccount) {
	var returnValue = window.showModalDialog(
			'/10088/Auth/Servlet?method=getDepartmentsAndUsers&toSubTree='
					+ toSubTree + '&district=' + district + '&departmentId='
					+ departmentId + '&userAccount=' + userAccount, '',
			'dialogHeight:600px;dialogWidth:970px;resizable:yes;maximize:yes');

	// 返回的格式为
	// subjectType,subjectId,subjectName,departmentId,departmentName,district
	if (returnValue != null) {
		var subArray = returnValue.split(',');

		var SubjectType = $('#' + inputIdPre + 'SubjectType');
		if (SubjectType)
			$(SubjectType).val(subArray[0]);

		var SubjectId = $('#' + inputIdPre + 'SubjectId');
		if (SubjectId)
			$(SubjectId).val(subArray[1]);

		var SubjectName = $('#' + inputIdPre + 'SubjectName');
		if (SubjectName)
			$(SubjectName).val(subArray[2]);

		var SubjectDepartmentId = $('#' + inputIdPre + 'SubjectDepartmentId');
		if (SubjectDepartmentId)
			$(SubjectDepartmentId).val(subArray[3]);

		var SubjectDepartmentName = $('#' + inputIdPre
				+ 'SubjectDepartmentName');
		if (SubjectDepartmentName)
			$(SubjectDepartmentName).val(subArray[4]);

		var SubjectDepartmentName = $('#' + inputIdPre + 'SubjectDistrict');
		if (SubjectDepartmentName)
			$(SubjectDepartmentName).val(subArray[5]);
	}
}

// 打开一个模态窗口
function openBigModalDialog(url) {
	window.showModalDialog(url, window,
			'dialogHeight:700px;dialogWidth:820px;resizable:yes;maximize:yes');
}

// 打开一个窗口
function openWindow(url) {
	// window.open(url,'_blank');
	var openobj = window;
	openobj.open(url, "_blank");
}

// 翻页，注意翻页form的id必须为pageForm
function toPage(pageNo) {
	$("#pageNo").val(pageNo);
	$("#pageForm").submit();
}

//显示所有数据
function showAllRows(rows)
{
	$("#pageCount").val(rows);
	toPage(1);
}

// 检查所有css为notEmpty的元素是否已经填写了值
function checkAllNotEmptyInput() {
	var isOk = true;

	$(".notEmpty").each(function() {
				var id = $(this).attr("id");
				var label = $(this).attr("label");

				if (checkNull(id, lable = null ? "不能为空的字段" : label) == false) {
					isOk = false;
					return false;;
				}
			});

	return isOk;
}

// 检查某个TR内所有css为notEmpty的元素是否已经填写了值
function checkTrNotEmptyInput(trId) {
	var isOk = true;

	$("tr#" + trId + " .notEmpty").each(function() {
				var id = $(this).attr("id");
				var label = $(this).attr("label");

				if (checkNullByLabel(label) == false)// 定制化字段在
				// 阶段反馈、工单回复等阶段可能都有，所以其id有可能会重复，这里使用label来标志需要检查的输入框
				{
					isOk = false;
					return false;;
				}
			});

	return isOk;
}

// 检查是否为空
function checkNullByLabel(label) {
	var input = $("[label='" + label + "']");

	if ($(input).val() == "") {
		$(input).focus();
		alert("请填写 " + label);
		return false;
	}
	return true;
}

// 导出
	function exportData(totalRows,url)
	{
		var exportExcel = true;
			
		if(totalRows > 500)
		{
			exportExcel = confirm("如果条数较多导出时可能需要等待一点时间，您确定导出吗？");
		}
		
		if (exportExcel)
		{
			var action = $("form").attr("action");
			
			$("form").attr("action",url);
			$("form").submit();
			
			$("form").attr("action",action);
		}
	}
	
	function printme()
	{
		$("div#printDiv").printArea();
	}

// =============================ArrayList=============================
// ArrayList.

function ArrayList() {
	this.index = -1;
	this.array = new Array();
}

ArrayList.prototype.clear = function() // 清空
{
	this.index = -1;
	this.array = new Array();
}

ArrayList.prototype.add = function(obj) // 添加
{
	if (this.getIndex(obj) == -1) {
		this.index = this.index + 1;
		this.array[eval(this.index)] = obj;
	}
}

ArrayList.prototype.get = function(index) // 根据索引取值
{
	return this.array[eval(index)];
}

ArrayList.prototype.size = function() // 获取ArrayList长度
{
	return this.index + 1;
}

ArrayList.prototype.getIndex = function(obj) // 根据值取出在ArrayList中索引
{
	var index = -1;
	for (var i = 0; i < this.array.length; i++) {
		if (this.array[i] == obj) {
			index = i;
			break;
		}
	}
	return index;
}

ArrayList.prototype.remove = function(index) // 根据索引删除
{
	var j = 0;
	var arrThis = this.array;
	var arrTemp = new Array();
	for (w = 0; w < arrThis.length; w++) {
		if (eval(index) != eval(w)) {
			arrTemp[j] = arrThis[w];
			j++;
		}
	}
	this.array = arrTemp;
	this.index = eval(j - 1);
}

ArrayList.prototype.removeValue = function(obj) // 根据值删除
{
	var j = 0;
	var arrThis = this.array;
	var arrTemp = new Array();
	for (w = 0; w < arrThis.length; w++) {
		if (obj != arrThis[w]) {
			arrTemp[j] = arrThis[w];
			j++;
		}
	}
	this.array = arrTemp;
	this.index = eval(j - 1);
}

ArrayList.prototype.toString = function() // 转换为字符串，中间用','分开
{
	var strResult = "";
	for (var i = 0; i < this.array.length; i++) {
		if (strResult == "")
			strResult = this.array[i];
		else
			strResult = strResult + "," + this.array[i];
	}
	return strResult;
}
