var SysLog = {

	// 日志查询页面
	index : function() {

		// 日志grid
		createGrid('#grid-table', {
			url : 'list',
			colNames : [ 'id', '日志类型', '日志内容', '操作人', '操作时间' ],
			colModel : [ {
				name : 'id',
				hidden : true,
				width : 90,
			}, {
				name : 'logType',
				fixed : true,
				width : 90
			}, {
				name : 'logDetail',
				width : 200
			}, {
				name : 'createUserName',
				fixed : true,
				width : 90
			}, {
				name : 'createDate',
				formatter : dateFormatterSec,
				fixed : true,
				width : 150
			} ],
			caption : '日志列表'
		});
		
		// 查询
		$('#searchForm').submit(function() {
			refreshJqgrid();
			return false;
		});

	}
};