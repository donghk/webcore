<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="modal-dialog" id="alertModal">
	<div class="modal-content" style="border: 0">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<h3 class="modal-title">提示</h3>
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
		<button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
	</div>
</div>

<script type="text/javascript">
	var myModal = $('#alertModal').parent('.modal');

	$(document).ready(function() {

		myModal.modal('show');

		myModal.on('hide.bs.modal', function() {
			$(this).removeData("bs.modal");
		});
	});
</script>
