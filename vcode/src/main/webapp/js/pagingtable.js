/***加载操作相关信息****/
jQuery(document).ready(function() {
    $.getJSON("/getPicture", function(pageResult) {
        printRecord(pageResult);
    });

    $.getJSON("/getPageCount", function(result) {
        var pageCount = 1;
        //根据总页数判断，如果小于5页，则显示所有页数，如果大于5页，则显示5页。根据当前点击的页数生成
        if (pageCount > 5) {
            page_icon(1, 5, 0);
        } else {
            page_icon(1, pageCount, 0);
        }

        //点击分页按钮触发
        $("#pageGro li").live("click", function() {
            if (pageCount > 5) {
                var pageNum = parseInt($(this).html()); //获取当前页数
                pageGroup(pageNum, pageCount);
            } else {
                page_icon(1, pageCount, 0);
                $(this).addClass("on");
                $(this).siblings("li").removeClass("on");
                var pageNum = parseInt($(this).html()); //获取当前页数
                pageGroup(pageNum, pageCount);
            }
        });

        //点击上一页触发
        $("#pageGro .pageUp").click(function() {
            if (pageCount > 5) {
                var pageNum = parseInt($("#pageGro li.on").html()); //获取当前页
                if (pageNum == 1) {} else {
                    pageGroup(pageNum - 1, pageCount);
                }
            } else {
                var index = $("#pageGro ul li.on").index(); //获取当前页
                if (index > 0) {
                    $("#pageGro li").removeClass("on"); //清除所有选中
                    $("#pageGro ul li").eq(index - 1).addClass("on"); //选中上一页
                }
            }
        });

        //点击下一页触发
        $("#pageGro .pageDown").click(function() {
            if (pageCount > 5) {
                var pageNum = parseInt($("#pageGro li.on").html()); //获取当前页
                if (pageNum == pageCount) {} else {
                    pageGroup(pageNum + 1, pageCount);
                }
            } else {
                var index = $("#pageGro ul li.on").index(); //获取当前页
                if (index + 1 < pageCount) {
                    $("#pageGro li").removeClass("on"); //清除所有选中
                    $("#pageGro ul li").eq(index + 1).addClass("on"); //选中上一页
                }
            }
        });

    });
});

//点击跳转页面
function pageGroup(pageNum, pageCount) {
    if (pageCount > 5) {
        switch (pageNum) {
            case 1:
                page_icon(1, 5, 0);
                break;
            case 2:
                page_icon(1, 5, 1);
                break;
            case pageCount - 1:
                page_icon(pageCount - 4, pageCount, 3);
                break;
            case pageCount:
                page_icon(pageCount - 4, pageCount, 4);
                break;
            default:
                page_icon(pageNum - 2, pageNum + 2, 2);
                break;
        }
    } else {
        switch (pageNum) {
            case 1:
                page_icon(1, pageCount, 0);
                break;
            case 2:
                page_icon(1, pageCount, 1);
                break;
            case 3:
                page_icon(1, pageCount, 2);
                break;
            case 4:
                page_icon(1, pageCount, 3);
                break;
            case 5:
                page_icon(1, pageCount, 4);
                break;
        }
    }
    $.getJSON("/webgis/admin/logs/" + pageNum, function(pageResult) {
        printRecord(pageResult);
    });
}

//根据当前选中页生成页面点击按钮
function page_icon(page, count, eq) {
    var ul_html = "";
    for (var i = page; i <= count; i++) {
        ul_html += "<li>" + i + "</li>";
    }
    $("#pageGro ul").html(ul_html);
    $("#pageGro ul li").eq(eq).addClass("on");
}

function printRecord(pageResult) {
    var recordArray = pageResult;
    var printRecordArray = [];
    for (var i = 0; i < recordArray.length; i++) {
        var userId = pageResult[i].pictureWidth;
        var action = pageResult[i].pictureHeight;
        constrHtmlTbody = '<tr><td style=\"width:20%;\">' + userId + '</td>' + '<td  style=\"width:20%;\">' + action + '</td></tr>';
        printRecordArray.push(constrHtmlTbody);
    }
    $("#itemContainer").html(constrHtmlTbody);
}