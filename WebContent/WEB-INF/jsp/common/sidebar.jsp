<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<a class="menu-toggler" id="menu-toggler" href="#"> 
	<span class="menu-text"></span>
</a>

<div class="sidebar" id="sidebar">

	<ul class="nav nav-list" id="leftMenu_ul">
	</ul>
	<!-- /.nav-list -->

	<div class="sidebar-collapse" id="sidebar-collapse">
		<i  id="sidebar_icon" 
			class="icon-double-angle-left" 
			data-icon1="icon-double-angle-left" 
			data-icon2="icon-double-angle-right">
		</i>
	</div>

</div>

<script type="text/javascript">
	$(document).ready(function() {
		if ($("#sidebar_icon").hasClass("icon-double-angle-left")) {
			$("#left_msg").show();
		} else {
			$("#left_msg").hide();
		}

		$("#sidebar-collapse").click(function() {
			if ($("#sidebar_icon").hasClass("icon-double-angle-left")) {
				$("#left_msg").show();
			} else {
				$("#left_msg").hide();
			}
		});

		$.ajax({
			type : "post",
			url : "${contextPath}/menu",
			dataType : "json",
			success : function(json) {
				var tree_data = $.parseJSON(json.result);
				buildLeftMenu(tree_data);

				$("#leftMenu_ul li.active").click(function() {
					var menu = this;
					if ($(this).find("ul.submenu")) {
						$("#leftMenu_ul li.active").addClass("open");
						if ($(this).find("ul.submenu").css("display") == "none") {
							$("ul.submenu").each(function() {
								if (menu != this && $(this).css("display") == "block") {
									$(this).css("display", "none");
								}
							});
						}
					}
				});
			},
			error : function() {
			}
		});
	});

	// 构建菜单
	function buildLeftMenu(json) {
		$.each(json, function(index, item) {
			var _li = $("<li class=\"active open\"/>").appendTo($("#leftMenu_ul"));
			if (item.children != '') {
				var _li_a = $("<a href=\"#\" class=\"dropdown-toggle\"/>").appendTo($(_li));
				var _li_a_i = $("<i class=\"icon-desktop\"></i>").appendTo($(_li_a));
				if (item.menuPath != null && item.menuPath != '') {
					$(_li_a).attr("href", "${contextPath}" + item.menuPath);
				}

				if (item.menuIcon != null && item.menuIcon != '') {
					$(_li_a_i).attr("class", item.menuIcon);
				}
				$("<span class=\"menu-text\">" + item.menuName + "</span>").appendTo($(_li_a));

				$("<b class=\"arrow icon-angle-down\"></b>").appendTo($(_li_a));

				var children = item.children;

				var _child_ul = $("<ul class=\"submenu\" style=\"display:none;\"/>").appendTo($(_li));

				$.each(children, function(index, childItem) {
					var _child_ul_li = $("<li/>").appendTo($(_child_ul));
					var _child_ul_li_a = $("<a href=\"#\">" + childItem.menuName + "</a>").appendTo($(_child_ul_li));
					if (childItem.menuPath != null && childItem.menuPath != '') {
						$(_child_ul_li_a).attr("href", "${contextPath}" + childItem.menuPath);
					}
					var _child_ul_li_a_i = $("<i class=\"icon-double-angle-right\"></i>").appendTo($(_child_ul_li_a));
					if (childItem.menuIcon != null && childItem.menuIcon != '') {
						$(_child_ul_li_a_i).attr("class", childItem.menuIcon);
					}
				});
			} else {
				var _child_ul_li_a = $("<a href=\"#\">" + "</a>").appendTo($(_li));
				var _child_ul_li_a_i = $("<i class=\"icon-desktop\"></i>").appendTo($(_child_ul_li_a));
				if (item.menuPath != null && item.menuPath != '') {
					$(_child_ul_li_a).attr("href", "${contextPath}" + item.menuPath);
				}
				if (item.menuIcon != null && item.menuIcon != '') {
					$(_child_ul_li_a_i).attr("class", item.menuIcon);
				}
				$("<span class=\"menu-text\">" + item.menuName + "</span>").appendTo($(_child_ul_li_a));

			}
		});

		var _li = $("<li class=\"active open\"/>").appendTo($("#leftMenu_ul"));
		var _quit_li = $("<a href=\"${contextPath}/j_spring_security_logout\">" + "</a>").appendTo($(_li));
		$("<i class=\"icon-signout\"></i>").appendTo($(_quit_li));
		$("<span class=\"menu-text\">" + "退出" + "</span>").appendTo($(_quit_li));

		// 展开当前URL的菜单栏
		var nowUrl = window.location.href;
		$.each($("#leftMenu_ul").find("a"), function(index, _a) {
			var aUrl = $(_a).attr("href");
			if (nowUrl.indexOf(aUrl) > 0) {
				$(_a).parent().parent().css("display", "");
				$(_a).parent().attr("class", "active");
			}
		});
	}
</script>