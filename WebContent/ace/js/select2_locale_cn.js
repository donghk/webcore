/**
 * Select2 Chinese translation
 */
(function ($) {
    "use strict";
    $.extend($.fn.select2.defaults, {
        formatNoMatches: function () { return "没有找到匹配项"; },
        formatInputTooShort: function (input, min) { return "请输入关键字";},
        formatInputTooLong: function (input, max) { var n = input.length - max; return "最多不得超过" + n + "个字";},
        formatSelectionTooBig: function (limit) { return "你只能选择最多" + limit + "项"; },
        formatLoadMore: function (pageNumber) { return "加载结果中..."; },
        formatSearching: function () { return "搜索中..."; }
    });
})(jQuery);
