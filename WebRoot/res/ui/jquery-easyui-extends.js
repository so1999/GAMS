// ------------------格式化时间为 yyyy-MM-dd ---------------------------------------
$.fn.datebox.defaults.formatter = function(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
};
//  
$.fn.datebox.defaults.parser = function(s) {
	if (s) {
		var a = s.split('-');
		var d = new Date(parseInt(a[0]), parseInt(a[1]) - 1, parseInt(a[2]));
		return d;
	} else {
		return new Date();
	}

};

$.fn.calendar.defaults = {
	width : 180,
	height : 180,
	fit : false,
	border : true,
	weeks : [ "一", "二", "三", "四", "五", "六", "日" ],
	months : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月",
			"十一月", "十二月" ],
	year : new Date().getFullYear(),
	month : new Date().getMonth() + 1,
	current : new Date(),
	onSelect : function(_6f) {
	}
};
/**
 * $.fn.panel.defaults.onBeforeDestroy = function() {/* tab关闭时回收内存 var frame =
 * $('iframe', this); try { if (frame.length > 0) {
 * frame[0].contentWindow.document.write(''); frame[0].contentWindow.close();
 * frame.remove(); if ($.browser.msie) { CollectGarbage(); } } else {
 * $(this).find('.combo-f').each(function() { var panel =
 * $(this).data().combo.panel; panel.panel('destroy'); }); } } catch (e) { } };
 */
