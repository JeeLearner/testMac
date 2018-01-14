var pageii = null;
var grid = null;
$(function() {
    grid = lyGrid({
        id : 'paging',
        l_column : [
                {
                    colkey : "logId",
                    name : "任务ID",
                    width : "50px",
                    hide : true
                }, {
                    colkey : "beanName",
                    name : "Spring Bean"
                }, {
                    colkey : "jobId",
                    name : "任务ID"
                }, {
                    colkey : "methodName",
                    name : "方法名",
                }, {
                    colkey : "params",
                    name : "参数"
                }, {
                    colkey : "remark",
                    name : "备注"
                }, {
                    colkey : "createTime",
                    name : "创建时间"
                }, {
                    colkey : "status",
                    name : "执行状态",
                	renderData : function(rowindex, data, rowdata, colkeyn) {
                        if (data == 0) {
                            return "成功";
                        }
                        if (data == 1) {
                            return "失败";
                        }
                        return "数据异常";
                    }
                }, {
                    colkey : "error",
                    name : "失败信息"
                }, {
                    colkey : "times",
                    name : "耗时"
                }
                ],
        jsonUrl : rootPath + '/schedule/scheduleLog_do/list.do',
        checkbox : true,
        checkValue : 'logId',
    });
    $("#search").click("click", function() {// 绑定查询按扭
        var searchParams = $("#searchForm").serializeJson();// 初始化传参数
        grid.setOptions({
            data : searchParams
        });
    });
});
