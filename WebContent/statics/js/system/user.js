var SysUser = {

	// 用户查询页面
	index : function() {

		// 用户grid
		createGrid('#grid-table', {
			url : 'list',
			colNames : [ 'id', '用户名称', '真实姓名', '备注', '是否有效', '操作人', '操作时间' ],
			colModel : [ {
				name : 'id',
				hidden : true,
			}, {
				name : 'userName',
				formatter : SysUser.showNameLink,
				fixed : true,
				index : 'USER_NAME',
				width : 90
			}, {
				name : 'realName',
				fixed : true,
				index : 'REAL_NAME',
				width : 90
			}, {
				name : 'remark',
				index : 'REMARK',
				width : 600
			}, {
				name : 'isValid',
				index : 'IS_VALID',
				formatter : validFormatter,
				width : 90
			}, {
				name : 'createUserName',
				fixed : true,
				index : 'CREATE_USER_NAME',
				width : 90
			}, {
				name : 'createDate',
				formatter : dateFormatterSec,
				fixed : true,
				index : 'CREATE_DATE',
				width : 150
			} ],
			caption : '用户列表',
			multiselect : true,
			gridComplete : funcGridOper
		});
		
		// 查询
		$('#searchForm').submit(function() {
			refreshJqgrid();
			return false;
		});

		// 新增
		$('#addBtn').click(function() {
			$('#id').val('');
			$('#postForm').attr('action', contextPath + '/system/user/edit');
			$('#postForm').submit();
		});

		// 删除
		$('#delBtn').click(function() {
			var rowIds = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
			if (rowIds.length == 0) {
				CommUtils.commAlert('alertModal', '请选择一条记录！');
			} else {
				var options = new Object;
				options.modalId = 'alertModal';
				options.msg = '确认您是否要删除选中的记录？';
				options.delIds = rowIds;
				options.url = 'delete';
				options.gridTableId = '#grid-table';
				CommUtils.commDelete(options);
			}
		});

	},

	// 显示跳转链接
	showNameLink : function(cellvalue, options, rowObject) {
		var html = '';
		if (cellvalue != null) {
			html = '<a href="javascript:void(0);" style="text-decoration: none;" '
			+ 'onclick="SysUser.editLink(\'' + rowObject.id + '\')" >' + cellvalue + '</a>';
		}
		return html;
	},

	// 编辑链接
	editLink : function(id) {
		$('#id').val(id);
		$('#postForm').attr('action', contextPath + '/system/user/edit');
		$('#postForm').submit();
	},

	// 用户编辑页面
	edit : function() {

		// 标题
		if ($('#id').val()) {
			$('#title').html('编辑用户');
		} else {
			$('#title').html('新增用户');
		}

		// 返回
		$('#backBtn').click(function() {
			window.location.href = contextPath + '/system/user/';
		});

		// 用户信息验证规则
		$('#userForm').validate({
			rules : {
				userName : {
					required : true,
					isExist : true,
					maxlength : 16
				},
				passwd : {
					required : true,
					maxlength : 16
				},
				passwdConfirm : {
					required : true,
					maxlength : 16
				},
				realName : {
					required : true,
					maxlength : 16
				},
				isValid : {
					required : true
				}
			}
		});

		// 判断用户名是否已存在
		jQuery.validator.addMethod('isExist', function(value, element) {
			var returnVal = false;
			$.ajax({
				url : contextPath + '/system/user/validUserNameIsExist',
				type : 'POST',
				async : false,
				dataType : 'json',
				data : {
					userName : value,
					id : $('#id').val()
				},
				success : function(json) {
					returnVal = json.result;
				}
			});
			return returnVal;
		}, '用户名已存在');

		// 保存
		$('#saveBtn').click(function() {
			var isValid = $('#userForm').valid();
			
			if (isValid) {
				
				$('#saveBtn').attr('disabled', 'disabled');
				$('#saveText').html('保存中');

				// 保存
				$.ajax({
					type : 'POST',
					url : contextPath + '/system/user/save',
					data : $('#userForm').serialize(),
					dataType : 'json',
					success : function(json) {
						if (json.result == 'success') {
							$('#save').removeAttr('disabled');
							$('#saveText').html('保存');

							$.gritter.add({
								text : '保存成功',
								time : '800',
								class_name : 'gritter-success gritter-top-25'
							});

							// 返回列表页
							setTimeout(function() {
								window.location.href = contextPath + '/system/user/';
							}, 800);

						} else {
							$('#saveBtn').removeAttr('disabled');
							$('#saveText').html('保存');

							$.gritter.add({
								text : '保存失败',
								time : '2000',
								class_name : 'gritter-error gritter-top-25'
							});
						}
					}
				});
			}
		});
	}

};