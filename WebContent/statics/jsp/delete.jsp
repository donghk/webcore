<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="modal-dialog" id="delModal">
	<div class="modal-content" style="border: 0">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<h3 class="modal-title">警告</h3>
		</div>
		<!-- ./modal-header -->
		<div class="modal-body">
			<div class="row">
				<div class="col-xs-12">
					<h5>${param.msg}</h5>
				</div>
			</div>
		</div>
	</div>
	<div class="modal-footer" style="margin-top: 0">
		<input type="hidden" id="delIds" value="${param.delIds }"> <input type="hidden" id="modalId" value="${param.modalId }"> <input type="hidden" id="gridTableId"
			value="${param.gridTableId }"> <input type="hidden" id="deleteUrl" value="${param.deleteUrl }">

		<button type="button" class="btn btn-info" id="confirm">确定</button>
		<button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
	</div>
</div>
<script type="text/javascript">
	var myModal = $('#delModal').parent('.modal');

	$(document).ready(function() {

		$('#confirm').removeAttr('disabled');
		var modalId = $("#modalId").val();
		var delIds = $("#delIds").val();
		var gridTableId = $("#gridTableId").val();
		var deleteUrl = $("#deleteUrl").val();

		$('#confirm').click(function() {
			$('#confirm').attr('disabled', 'disabled');
			$.ajax({
				type : 'POST',
				url : deleteUrl,
				data : "delIds=" + delIds,
				dataType : 'json',
				success : function(json) {
					$(myModal).modal("hide");
					if (json.status == "success") {
						if (gridTableId) {
							$(gridTableId).trigger("reloadGrid");
						}
						$.gritter.add({
							text : '删除成功',
							time : '1000',
							class_name : 'gritter-success gritter-top-25'
						});
					} else {
						$.gritter.add({
							text : '删除失败',
							time : '2000',
							class_name : 'gritter-error gritter-top-25'
						});
					}
				}
			});
		});

		myModal.modal('show');

		myModal.on('hide.bs.modal', function() {
			$('#confirm').removeAttr('disabled');
			$(this).removeData("bs.modal");
		});

	});
</script>