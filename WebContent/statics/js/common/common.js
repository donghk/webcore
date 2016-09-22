/* #####################################common start############################ */

/**
 * 超时验证
 */
$.ajaxSetup({
	complete : function(XMLHttpRequest, textStatus) {

		var login = XMLHttpRequest.getResponseHeader("login"); // 通过XMLHttpRequest取得响应头，sessionstatus，

		if (login == "true") {

			bootbox.dialog({
				message : "<span class='bigger-110'>您未登录或者登录超时，请点击确定重新登录！</span>",
				buttons : {
					"danger" : {
						"label" : "确定",
						"className" : "btn-sm btn-danger",
						"callback" : function() {
							var pathName = window.document.location.pathname;
							var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
							window.location.href = projectName;
						}
					}
				}
			});

		}
	}
});

/**
 * 初始化
 */
$(function($) {

	/**
	 * datepicker 初始化
	 */
	$('.datepicker').datepicker({
		format : 'yyyy-mm-dd',
		autoclose : true,
		minViewMode : 0,
		startView : 0,
		language : 'cn'
	});

	$("input").addClear();
	
	/**
	 * 输入框字数限制
	 */
	$('input[maxlength]').maxlength({
		threshold : 0,
		message : '最多输入 %charsTotal% 个字'
	});
	
	/**
	 * textarea框字数限制
	 */
	$('textarea[maxlength]').maxlength({
		threshold : 0,
		message : '最多输入 %charsTotal% 个字'
	});
	
});

/**
 * form.serialize转格式
 */
function getObjectByForm(idStr) {
	var _param = {};
	var json = $(idStr).serialize();
	json = json.replace(/\+/g, " ");
	var items = json.split("&");
	for (var i = 0; i < items.length; i++) {
		var item = items[i].split("=");
		_param[item[0]] = decodeURIComponent(item[1]);
	}
	return _param;
}

/**
 * 搜索框中收起更多
 */
function activateSearchMore() {
	$(".search-more").click(function() {
		var nextShow = true;
		if ($(".search-more i").attr("class") == "icon-chevron-up") {
			nextShow = false;
		}
		if (nextShow) {
			$(".search-more i").attr("class", "icon-chevron-up");
			$("#search_more_tip").html("收起");
			$(".search-hide").slideDown(150);
		} else {
			$(".search-more i").attr("class", "icon-chevron-down");
			$("#search_more_tip").html("更多");
			$(".search-hide").slideUp(150);
		}
	});
}

/**
 * 对象中参数的个数
 */
function arrayCount(o) {
	var t = typeof o;
	if (t == 'string') {
		return o.length;
	} else if (t == 'object') {
		var n = 0;
		for ( var i in o) {
			n++;
		}
		return n;
	}
	return false;
}

/* #####################################common end############################## */

/* #####################################jqgrid start############################ */

/**
 * 自定义jqgrid模板
 */
function createGrid(id, option) {
	var options = $.extend(true, defaultGridOptions, option);
	jQuery(id).jqGrid(options);
}

/**
 * 自定义jqgrid模板默认属性
 */
var defaultGridOptions = {
	url : 'list',
	datatype : 'json',
	mtype : "POST",
	height : "100%",
	rownumbers : true,
	rownumWidth : 30,
	colNames : null,
	colModel : null,
	viewrecords : true,
	rowNum : 10,
	rowList : [ 10, 20, 30 ],
	pager : "#grid-pager",
	multiselect : false,
	multiboxonly : false,
	altRows : true,
	autowidth : true,
	autoScroll : false,
	jsonReader : {
		root : "result",
		total : 'total',
		page : 'page',
		records : 'records'
	},
	loadText : '数据加载中，请稍后',
	pgtext : '第{0}页 /共{1}页',
	recordtext : '当前 {0} - {1}条/共{2}条',
	emptyrecords : '无法找到对应的记录',
	gridComplete : funcGridComplete
};

/**
 * jqgrid的分页样式
 */
function updatePagerIcons(table) {
	var replacement = {
		'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
		'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
		'ui-icon-seek-next' : 'icon-angle-right bigger-140',
		'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function() {
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

		if ($class in replacement)
			icon.attr('class', 'ui-icon ' + replacement[$class]);
	});
}

/**
 * jqgrid修改选择框样式
 */
function funcAddCheckBox(eTable) {
	$(eTable).find('input:checkbox').addClass('ace').wrap('<label />').after('<span class="lbl align-top" />');

	$("#jqgh_" + $(eTable).attr('id') + "_cb").attr("style", "padding:3px");
	$('.ui-jqgrid-labels th[id*="_cb"]').find('input.cbox[type=checkbox]').addClass('ace').wrap('<label />').after('<span class="lbl align-top" />');
}

/**
 * jqgrid窗口自适应
 */
function funcGridResize(eTable) {
	var option = "";
	option = $.extend(true, {
		a : '.page-content',
		b : '.ui-jqgrid-bdiv'
	}, option);

	var winwidth = $(option.a).width(); // 当前页面的宽度
	$(eTable).setGridWidth(winwidth);
	$("#grid-body").find(option.b).css('width', winwidth + 1);

	/**
	 * 窗口缩放时，经动态变化宽度
	 */
	$(window).resize(function() {
		var winwidth = $(option.a).width(); // 当前页面的宽度
		$(eTable).setGridWidth(winwidth);
		$("#grid-body").find(option.b).css('width', winwidth + 1);
	});

	/**
	 * 点击菜单边框收缩菜单时，动态变化表格宽度
	 */
	$('#sidebar-collapse').click(function() {
		var winwidth = $(option.a).width(); // 当前窗口中，一行的宽度
		$(eTable).setGridWidth(winwidth);
		$("#grid-body").find(option.b).css('width', winwidth + 1);
	});
}

/**
 * jqgrid窗口自适应
 */
function commGridResize(eTable, ebody) {
	var option = "";
	option = $.extend(true, {
		a : '.page-content',
		b : '.ui-jqgrid-bdiv'
	}, option);

	var winwidth = $(option.a).width(); // 当前页面的宽度
	$(eTable).setGridWidth(winwidth);
	$(ebody).find(option.b).css('width', winwidth + 1);

	/**
	 * 窗口缩放时，经动态变化宽度
	 */
	$(window).resize(function() {
		var winwidth = $(option.a).width(); // 当前页面的宽度
		$(eTable).setGridWidth(winwidth);
		$(ebody).find(option.b).css('width', winwidth + 1);
	});

	/**
	 * 点击菜单边框收缩菜单时，动态变化表格宽度
	 */
	$('#sidebar-collapse').click(function() {
		var winwidth = $(option.a).width(); // 当前窗口中，一行的宽度
		$(eTable).setGridWidth(winwidth);
		$(ebody).find(option.b).css('width', winwidth + 1);
	});
}

/**
 * 冻结列
 */
function funcFrozen(eTable) {
	jQuery(eTable).jqGrid("setFrozenColumns");
}

/**
 * jqgrid完成时框口自适应
 */
function funcGridComplete() {
	updatePagerIcons(this);
	funcGridResize(this);
	funcFrozen(this);
}

/**
 * jqgrid完成时调用添加选择框和框口自适应
 */
function funcGridOper() {
	updatePagerIcons(this);
	funcAddCheckBox(this);
	funcGridResize(this);
	funcFrozen(this);
}

/**
 * jqgrid完成时调用添加选择框和水平有滚动条
 */
function funcGridOperUnScoll() {
	updatePagerIcons(this);
	funcAddCheckBox(this);
	funcFrozen(this);
}

/**
 * 刷新jqgrid数据
 */
function refreshJqgrid() {
	var json = getObjectByForm("#searchForm");
	jQuery("#grid-table").jqGrid('setGridParam', {
		postData : json
	}).jqGrid('setGridParam', {
		'page' : 1
	}).trigger("reloadGrid");
}

/**
 * jqgrid开关样式
 */
function aceSwitch(cellvalue, options, rowObject) {
	var check = '<input type="checkbox" class="ace ace-switch ace-switch-5" ';
	if (cellvalue == 1) {
		check = check + 'checked="checked"';
	}
	check = check + ' /> <label class="lbl"></label>';
	return check;
}

/* #####################################jqgrid end############################## */

/* #####################################tag start############################### */
/**
 * jqgrid调用数据字典
 */
function getDictListValue(cellvalue, options, rowObject) {
	if (cellvalue || cellvalue == 0) {
		var name = "";
		var targetName = "";
		if (typeof (options.colModel.formatoptions) != "undefined") {
			name = options.colModel.formatoptions.name;
			targetName = options.colModel.formatoptions.targetName;
		}
		var baseList = $("#dict-" + name);
		if (baseList) {
			if (targetName) {
				cellvalue = rowObject[targetName];
			}
			var text = $(baseList).find("#dict-" + cellvalue).html();
			if (text) {
				return "<span data-id=" + cellvalue + ">" + text + "</span>";
			} else {
				return cellvalue;
			}
		} else {
			return cellvalue;
		}
	} else {
		return "";
	}
}

/**
 * jqgrid调用数据字典select框
 */
function getDictSelectValue(code) {
	var rs = '';
	var baseList = document.getElementById("dict-" + code).getElementsByTagName("li");
	for (var i = 0; i < baseList.length; i++) {
		rs = rs + baseList[i].id.substring(5) + ':' + $(baseList[i]).html();
		if (i != baseList.length - 1) {
			rs = rs + ';';
		}

	}
	return rs;
}

/**
 * jqgrid调用枚举
 */
function getEnumListValue(cellvalue, options, rowObject) {
	if (cellvalue || cellvalue == 0) {
		var name = "";
		var targetName = "";
		if (typeof (options.colModel.formatoptions) != "undefined") {
			name = options.colModel.formatoptions.name;
			targetName = options.colModel.formatoptions.targetName;
		}
		var baseList = $("#path-" + name);
		if (baseList) {
			if (targetName) {
				cellvalue = rowObject[targetName];
			}
			var text = $(baseList).find("#path-" + cellvalue).html();
			if (text) {
				return "<span data-id=" + cellvalue + ">" + text + "</span>";
			} else {
				return cellvalue;
			}
		} else {
			return cellvalue;
		}
	} else {
		return "";
	}
}

/**
 * jqgrid调用枚举select框
 */
function getEnumSelectValue(code) {
	var rs = '';
	var baseList = document.getElementById("path-" + code).getElementsByTagName("li");
	for (var i = 0; i < baseList.length; i++) {
		rs = rs + baseList[i].id.substring(5) + ':' + $(baseList[i]).html();
		if (i != baseList.length - 1) {
			rs = rs + ';';
		}

	}
	return rs;
}

/**
 * js调用数据字典
 */
function getDictListValueForJS(name, value) {
	var baseList = $("#dict-" + name);
	if (baseList) {
		var text = $(baseList).find("#dict-" + value).html();
		if (text) {
			return text;
		} else {
			return name;
		}
	} else {
		return name;
	}
}

/**
 * js调用枚举
 */
function getEnumListValueForJS(name, value) {
	var baseList = $("#path-" + name);
	if (baseList) {
		var text = $(baseList).find("#path-" + value).html();
		if (text) {
			return text;
		} else {
			return name;
		}
	} else {
		return name;
	}
}

/* #####################################tag end################################# */

/* #####################################validator start######################### */

/**
 * jQuery.validator 默认错误提示样式
 */
jQuery.extend(jQuery.validator.defaults, {
	errorElement : 'div',
	errorClass : 'help-block',
	focusInvalid : true,
	highlight : function(e) {
		$(e).closest('.form-group').removeClass('has-info').addClass('has-error');
	},
	success : function(e) {
		$(e).closest('.form-group').removeClass('has-error').addClass('has-info');
		$(e).remove();
	},
	errorPlacement : function(error, element) {
		if (element.is('input')) {
			if ("clearfix" == element.parent().attr("class")) {
				error.insertAfter(element.parent());
			} else {
				error.insertAfter(element);
			}
		} else {
			error.insertAfter(element);
		}
	}
});

/**
 * jQuery.validator 默认警示语汉化
 */
jQuery.extend(jQuery.validator.messages, {
	required : "必填字段",
	remote : "请修正该字段",
	email : "请输入正确格式的电子邮件",
	url : "请输入合法的网址",
	date : "请输入合法的日期",
	dateISO : "请输入合法的日期 ",
	number : "请输入合法的数字",
	digits : "只能输入整数",
	creditcard : "请输入合法的信用卡号",
	equalTo : "请再次输入相同的值",
	accept : "请输入拥有合法后缀名的字符串",
	// maxlength: jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串"),
	maxlength : jQuery.validator.format("长度最多是 {0} "),
	minlength : jQuery.validator.format("请输入一个 长度最少是 {0} 的字符串"),
	rangelength : jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
	range : jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
	max : jQuery.validator.format("请输入一个最大为{0} 的值"),
	min : jQuery.validator.format("请输入一个最小为{0} 的值")
});

/**
 * jQuery.validator 验证手机号
 */
jQuery.validator.addMethod("isMobile", function(value, element) {
	var length = value.length;
	var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写手机号码");

/**
 * jQuery.validator 验证联系电话
 */
jQuery.validator.addMethod("isTelphone", function(value, element) {
	var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	var tel = /^\d{3,4}-?\d{7,9}$/;
	var tel_comm = /^\d{7,9}$/;
	return this.optional(element) || (tel.test(value) || mobile.test(value) || tel_comm.test(value));
}, "请正确填写联系电话");

/**
 * jQuery.validator 验证邮箱
 */
jQuery.validator.addMethod("isEmail", function(value, element) {
	var email = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return this.optional(element) || (email.test(value));
}, "请正确填写邮箱");

/**
 * jQuery.validator 验证数字
 */
jQuery.validator.addMethod("isDouble", function(value, element) {
	return this.optional(element) || /^\d+\.\d+$/.test(value) || /^\d+$/.test(value);
}, "请填写数字");

/**
 * jQuery.validator 验证正整数
 */
jQuery.validator.addMethod("positiveinteger", function(value, element) {
	var aint = parseInt(value);
	return (aint > 0 && (aint + "") == value) || value == "";
}, "请输入正整数");

/**
 * jQuery.validator 验证密码复杂性
 */
jQuery.validator.addMethod("complPwd", function(value, element) {
	var num = /\w*[a-zA-Z]+\w*$/;
	var dir = /\w*[0-9]+\w*$/;
	return this.optional(element) || (num.test(value) && dir.test(value));
}, "包含字母和数字");

/* #####################################validator end########################### */

/* #####################################format start############################ */

/**
 * 默认
 */
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};

/**
 * 日期转格式 年月日
 */
function dateFormatter(cellvalue, options, rowObject) {
	var returnValue = "";
	if (cellvalue) {
		var dateValue = new Date(cellvalue);
		returnValue = dateValue.format("yyyy-MM-dd");
	}
	return returnValue;
}

/**
 * 日期转格式 年月
 */
function dateFormatterMonth(cellvalue, options, rowObject) {
	var returnValue = "";
	if (cellvalue) {
		var dateValue = new Date(cellvalue);
		returnValue = dateValue.format("yyyy-MM");
	}
	return returnValue;
}

/**
 * 日期转格式 年月日小时
 */
function dateFormatterHours(cellvalue, options, rowObject) {
	var returnValue = "";
	if (cellvalue) {
		var dateValue = new Date(cellvalue);
		returnValue = dateValue.format("yyyy-MM-dd hh") + "时";
	}
	return returnValue;
}

/**
 * 日期转格式 年月日时分秒
 */
function dateFormatterSec(cellvalue, options, rowObject) {
	var returnValue = "";
	if (cellvalue) {
		var dateValue = new Date(cellvalue);
		returnValue = dateValue.format("yyyy-MM-dd hh:mm:ss");
	}
	return returnValue;
}

/**
 * 数字转格式
 */
function doubleFormatter(cellvalue, options, rowObject) {
	var returnValue = "0";
	if (cellvalue) {
		returnValue = Math.round(cellvalue * 10000) / 100;
	}
	return returnValue;
}

/**
 * jqgrid是否有效格式化
 */
function validFormatter(cellvalue, options, rowObject) {
	if (cellvalue == 1) {
		return "有效";
	} else {
		return "无效";
	}
}

/**
 * datepicker 汉化
 */
$.fn.datepicker.dates['cn'] = {
	days : [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" ],
	daysShort : [ "日", "一", "二", "三", "四", "五", "六", "日" ],
	daysMin : [ "日", "一", "二", "三", "四", "五", "六", "日" ],
	months : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
	monthsShort : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
	today : "今天"
};

/* #####################################format end############################## */

/* ############################# CommUtils start ############################# */

var pathName = document.location.pathname;
var index = pathName.substr(1).indexOf("/");
var contextPath = pathName.substr(0, index + 1);

var CommUtils = {
	// 取Jqgrid的checkbox中的值
	getJqgridSelected : function(elementId) {
		var ids = [];
		var checkboxs = $(document.getElementById(elementId)).find("input[type=checkbox].ace:checked");
		$.each(checkboxs, function(index, element) {
			ids.push($(element).val());
		});
		if (ids.length == 0) {
			var radios = $(document.getElementById(elementId)).find("input[type=radio].ace:checked");
			$.each(radios, function(index, element) {
				ids.push($(element).val());
			});
		}
		return ids;
	},

	// 提示框
	commAlert : function(modalId, msg) {
		$(document.getElementById(modalId)).load(contextPath + "/statics/jsp/alert.jsp", {
			msg : msg,
			seconds : '3000',
			tips : ''
		}, '');
	},

	// 删除警告框
	commDelete : function(options) {
		options.delIds = options.delIds.join(",");
		if (!options.gridTableId) {
			options.gridTableId = "#grid-table";
		}
		if (!options.url) {
			options.url = "delete";
		}
		$(document.getElementById(options.modalId)).load(contextPath + "/statics/jsp/delete.jsp", {
			msg : options.msg,
			delIds : options.delIds,
			modalId : options.modalId,
			gridTableId : options.gridTableId,
			deleteUrl : options.url,
			seconds : '3000',
			tips : ''
		}, '');
	},

};
var CommDataUtils = {

	/**
	 * select2模板
	 */
	getSelect2Ajax : function(options) {
		$(options.el).select2({
			allowClear : true,
			placeholder : options.placeholder,
			minimumInputLength : 1,
			ajax : {
				type : 'POST',
				url : contextPath + options.url,
				dataType : 'json',
				data : function(term, page) {
					return {
						search : term,
					};
				},
				results : function(data, page) {
					return {
						results : data.result
					};
				},
				cache : true
			},
			initSelection : function(element, callback) {
				var id = $(element).val();
				var text = options.text;
				if (id !== "" && text !== "") {
					var data = {
						id : id,
						text : text
					};
					callback(data);
				}
			},
			formatSelection : function(item) {
				return item.text;
			},
			formatResult : function(item) {
				return item.text;
			}
		});
	}
};

/**
 * JS 生成UUID
 */
function UUID() {
	var s = [];
	var hexDigits = "0123456789abcdef";
	for (var i = 0; i < 36; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	}
	s[14] = "4";
	s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
	s[8] = s[13] = s[18] = s[23] = "-";

	var uuid = s.join("");
	return uuid;
}

/**
 * 获取UUID
 */
var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
function getUuid(len) {
	var chars = CHARS, uuid = [], i;
	var radix = chars.length;

	if (len) {
		for (i = 0; i < len; i++)
			uuid[i] = chars[0 | Math.random() * radix];
	} else {
		var r;

		uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
		uuid[14] = '4';

		for (i = 0; i < 36; i++) {
			if (!uuid[i]) {
				r = 0 | Math.random() * 16;
				uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
			}
		}
	}

	return uuid.join('');
};

// 验证邮箱
function checkEmail(el) {
	var regu = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z-]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]|net|NET|asia|ASIA|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT|cn|CN|cc|CC)$"
	var re = new RegExp(regu);
	if (el.search(re) == -1) {
		return false;
	}
	return true;
}
/* ############################# CommUtils end ############################# */
