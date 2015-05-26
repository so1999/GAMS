/*实现公共的js方法*/
/*存放窗口变量的全局变量*/
var windialog = null;
/* 打开窗口 */
function openW(title, contentId) {
	windialog = $.dialog({
		id : contentId,
		title : title,
		lock : true,
		fix : true,
		content : $(contentId).html()
	});
}
/* 关闭对话框 */
function closeWin() {
	if (windialog != null && !windialog.closed) {
		windialog.close();
	}
}
/* 打开窗口 */
function openWin(formId, viewUrl, saveUrl, gridId) {
	windialog = $.dialog({
		id : 'window',
		title : '',
		padding : 0,
		// width : 500,
		// height : 100,
		cache : false,
		lock : true,
		fix : true,
		initialize : function() {
			$.ajax({
				url : viewUrl,
				success : function(data) {
					windialog.content(data);
				},
				cache : false
			});
		},
		button : [ {
			value : "保存",
			focus : true,
			callback : function() {
				winSave(formId, saveUrl, gridId);
			}
		}, {
			value : "关闭",
			callback : function() {
				if (windialog != null && !windialog.closed) {
					windialog.close();
				}
			}
		} ]
	});
}

/**
 * 上传文件对话框
 * 
 * @param viewUrl
 */
function withoutButtonWin(viewUrl) {
	windialog = $.dialog({
		id : 'window',
		title : '密码修改',
		padding : 0,
		cache : false,
		lock : true,
		fix : true
	});
	var content = null;
	$.ajax({
		url : viewUrl,
		success : function(data) {
			windialog.content(data);
		}
	});
}
/**
 * 添加编辑弹窗
 * 
 */
function withoutButtonWin2(viewUrl) {
	windialog = $.dialog({
		id : 'window',
		title : '',
		padding : 0,
		cache : false,
		lock : true,
		fix : true
	});
	var content = null;
	$.ajax({
		url : viewUrl,
		success : function(data) {
			windialog.content(data);
		}
	});
}



function mailWin(viewUrl) {
	var content = null;
	$.ajax({
		url : viewUrl,
		success : function(data) {
			var x = data.match("mailEmpty");
			var em = data.match("");
			if (x != "mailEmpty" && em != "") {
				windialog = $.dialog({
					id : 'window',
					title : ' ',
					padding : 0,
					cache : false,
					lock : true,
					fix : true
				});
				windialog.content(data);
			}
		}
	});
}

function uploadFile(formId, url) {
	$(document).ready(function() {
		$(formId).ajaxSubmit({
			url : url,
			type : "POST",
			dataType : "script",
			success : function(msg) {
				alert(msg);
			}
		});
		return false;
	});
}
function art_alert(b) {
	return $.dialog({
		id : "Alert",
		fixed : !0,
		lock : !0,
		content : b,
		okValue : "确定",
		ok : !0,
		// beforeunload : !0,
		title : '消息',
		width : 304,
		height : 50
	});
};
art_confirm = function(content, ok) {
	return $
			.dialog({
				id : 'Confirm',
				title : '消息',
				fixed : true,
				lock : true,
				padding : 0,
				content : '<div style="padding:20px;width:304px;text-align:left;font-weight:bold;">'
						+ content + '</div>',
				okValue : "确定",
				ok : ok,
				cancelValue : "取消",
				cancel : !0,
				width : 304,
				height : 50
			});
};
/**
 * 删除信息
 * 
 * @returns
 */
function deletebyIds(id, delUrl) {
	var rows = $(id).datagrid("getSelections");// 获取多条记录
	// alert(rows + "___" + rows.length);
	if (rows.length <= 0) {
		promotdialog("请选择要操作的记录");
		return false;
	}
	art_confirm("确认删除信息?", function() {
		if (rows && rows.length > 0) {
			var idss = new Array();
			for ( var i = 0; i < rows.length; i++) {
				idss.push(rows[i].id);
			}
			$.post(delUrl, {
				ids : idss.toString()
			}, function(data) {
				if (data.success) {
					$(id).datagrid('reload');
					promotdialog(data.msg);
				} else {
					promotdialog(data.msg);
				}
			}, "json");
		}
	});
}
/**
 * 根据href在panel中显示相应的界面
 * 
 * @param url
 */
function addTab(url) {
	closeWin();
	var p = $("#panel");
	p.panel('refresh', url);
}
/**
 * 保存信息
 * 
 * @param formId
 * @param saveUrl
 * @param toUrl
 */
function save(formId, saveUrl, toUrl) {
	var roles = $(formId).serializeArray();
	$.post(saveUrl, roles, function(data) {
		if (data != null) {
			if (data.success) {
				addTab(toUrl);
			}
			promotdialog(data.msg);
		}
	}, "json");
}
/* 保存打开窗口中的操作 */
function winSave(formId, saveUrl, dataId) {
	var roles = $(formId).serializeArray();
	$.post(saveUrl, roles, function(data) {
		if (data != null) {
			if (data.success) {
				if (dataId != null && dataId != "undefined") {
					$(dataId).datagrid('reload');
				}
				closeWin();
			}
			promotdialog(data.msg);
			
		}
	}, "json");
}

/**
 * 检查空值
 * 
 * @param id
 * @returns {Boolean}
 */
function checkNull(id) {
	var tempVal = $(id).val();
	if (tempVal == undefined || $.trim(tempVal).length <= 0) {
		$(id).css({
			border : "1px solid red"
		});
		promotdialog(id+"本字段不能为空");
		return false;
	} else {
		$(id).css({
			border : "1px solid #95B8E7"
		});
	}
	return true;
}
/**
 * 检查状态
 * 
 */
function checkStatus(it) {
	var tempVal = $(it).combobox("getValue");
	if (tempVal.indexOf("在库") >= 0) {
		promotdialog( "请修改借出状态");
		return false;
	} else {

	}
	return true;
}

var sign = null;
var workload = workload || function() {
};
workload.remote = function(id, promotId, url) {
	// alert("标识：" + sign + "___值：" + $(id).val() + "_iD;"+id+" bijiao:" + (sign
	// == $(id).val()));
	if ($(id).val().length <= 0) {
		return false;
	} else if (sign == $(id).val()) {
		return false;
	} else {
		$.post(url, {
			sign : $(id).val()
		}, function(data) {
			sign = data.sign;
			// alert(data.success);
			if (data.success) {
				$(id).css({
					border : "1px solid green"
				});
				$(promotId).html(data.msg).css({
					color : "green"
				});
			} else {
				$(id).css({
					border : "1px solid red"
				});
				$(promotId).html(data.msg).css({
					color : "red"
				});
				// promotdialog(data.msg);
				$(id).val("");
				sign = null;
			}
		}, "json");
	}
};
function remoteCheck(promotId, url) {
	if ($(this).val().length <= 0) {
		return false;
	} else if (sign == $(this).val()) {
		return false;
	}
	$.post(url, {
		sign : $(this).val()
	}, function(data) {
		sign = data.sign;
		if (data.success) {
			$(id).html(data.msg).css({
				"color" : "green",
				"padding-left" : "4px"
			});
		} else {
			$(id).html(data.msg).css({
				"color" : "red",
				"padding-left" : "4px"
			});
		}
	}, "json");
}

function formate(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	var h = date.getHours();
	var M = date.getMinutes();
	var s = date.getSeconds();
	return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d)
			+ "  " + (h < 10 ? '0' + h : h) + ":" + (M < 10 ? '0' + M : M)
			+ ":" + (s < 10 ? '0' + s : s);
}
/**
 * 只能 获取Jquery easyui 中tree的选择时节点层次
 * 
 * @param node
 *            tree的node节点
 * @returns
 */
function getNodeLevel(node) {
	var p = $(node.target);
	var pnode = p.parent();
	return pnode.find("span[class='tree-indent']").size();
}
/**
 * 弹出显示信息的对话框 ($(window).height() / 2) - (pnotify.height() / 2)
 * 
 * @param text
 */
function promotdialog(text) {
	$.pnotify({
		titl : "Tip",
		text : text,
		history : false,
		icon : 'icon-envelope',
		delay : 3800,
		before_open : function(pnotify) {
			pnotify.css({
				"min-height" : 70,
				"top" : 80,
				"left" : ($(window).width() / 2) - (pnotify.width() / 2)
			});
		}
	});
}

function edit(id, url) {
	var row = $(id).datagrid('getSelected');
	if (row) {
		addTab(url + "/" + row.id + ".jspx");
	} else {
		promotdialog("请选择操作的记录");
	}
}

/**
 * collapsible : false, nowrap : false, 自定义显示数据的表格方法
 * 
 * @param id
 * @param title
 * @param dataUrl
 * @param toolbar
 * @param sortName
 * @param columns
 * @param fcolumn
 */
function datagrid(id, title, dataUrl, toolbar, sortName, columns, fcolumn) {
	pageList = [ 15, 10, 20, 30, 50, 100, 150 ];
	pageSize = 15;
	dataGrid(id, title, dataUrl, toolbar, pageList, pageSize, sortName,
			columns, fcolumn);
}
function dataGrid(id, title, dataUrl, toolbar, pageList, pageSize, sortName,
		columns, fcolumn) {
	$(id).datagrid({
		title : title,
		columns : columns,
		url : dataUrl,
		iconCls : 'easyui-icon-ok',
		width : '100%',
		height : 'auto',
		fit : true,
		pageList : pageList,
		pageSize : pageSize,
		striped : true,
		loadMsg : '正在加载信息',
		sortName : sortName,
		sortOrder : 'asc',
		remoteSort : false,
		toolbar : toolbar,
		frozenColumns : fcolumn,
		pagination : true,
		rownumbers : true,
		fitColumns : true,
		checkOnSelect : false
	});
	$(id).datagrid('getPager').pagination({
	/*
	 * beforePageText : '第', afterPageText : '页 , 共{pages}页', displayMsg :
	 * '从{from}到{to}条记录，共{total}条记录', onBeforeRefresh : function(pageNumber,
	 * pageSize) { $(this).pagination('loading'); $(this).pagination('loaded'); },
	 */
	});
}

/**
 * 实现文本框聚焦时改变其边框颜色
 */
function inputfocus() {
	$("input").focus(function() {
		$(this).css({
			"border-color" : "green"
		});
	});
	$("input").blur(function() {
		$(this).css({
			"border-color" : "#95B8E7"
		});
	});
	$("textarea").focus(function() {
		$(this).css({
			"border-color" : "green"
		});
	});
	$("textarea").blur(function() {
		$(this).css({
			"border-color" : "#95B8E7"
		});
	});
}
var stack_custom = {
	"dir1" : "right",
	"dir2" : "up"
};
function show_stack_custom(type) {
	var opts = {
		title : "Over Here",
		text : "Check me out. I'm in a different stack.",
		addclass : "stack-custom",
		stack : stack_custom,
		delay : 700
	};
	switch (type) {
	case 'error':
		opts.title = "Oh No";
		opts.text = "Watch out for that water tower!";
		opts.type = "error";
		break;
	case 'info':
		opts.title = "Breaking News";
		opts.text = "Have you met Ted?";
		opts.type = "info";
		break;
	case 'success':
		opts.title = "Good News Everyone";
		opts.text = "I've invented a device that bites shiny metal asses.";
		opts.type = "success";
		break;
	}
	$.pnotify(opts);
}