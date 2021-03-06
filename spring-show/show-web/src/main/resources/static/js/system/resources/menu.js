var setting = {
    data : {
        simpleData : {
            enable : true,
            idKey : "id",
            pIdKey : "parentId",
            rootPId : "0"
        },
        key : {
            url : "nourl"
        }
    }
};
var ztree;
var vm = new Vue({
    el : '#rrapp',
    data : {
        showList : true,
        title : null,
        menu : {
            parentName : null,
            parentId : "0",
            type : 1,
            level : 0
        }
    },
    methods : {
        getMenu : function(menuId) {
            // 加载菜单树
            $.get(rootPath + "/sys/res_do/resList.do", function(r) {
                ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
                var node = ztree.getNodeByParam("id", vm.menu.parentId);
                ztree.selectNode(node);
                try {
                    vm.menu.parentName = node.name;
                } catch (e) {
                }
            })
        },
        add : function() {
            vm.showList = false;
            vm.title = "新增";
            vm.menu = {
                parentName : null,
                parentId : 0,
                type : 1,
                level : 0
            };
            vm.getMenu();
        },
        update : function() {
            var menuId = getMenuId();
            if (menuId == null) {
                return;
            }
            $.get(rootPath + "/sys/res_do/get.do?id=" + menuId, function(r) {
                console.info(r);
            	vm.showList = false;
                vm.title = "修改授权资源";
                vm.menu = r.menu;
                vm.menu.tp = "9";
                vm.getMenu();
            });
        },
        del : function() {
            var menuId = getMenuId();
            if (menuId == null) {
                return;
            }
            confirm('确定要删除选中的记录？', function() {
                $.ajax({
                    type : "POST",
                    url : rootPath + "/sys/res_do/remove.do",
                    data : {
                        id : menuId
                    },
                    success : function(r) {
                        alert(r.msg);
                        vm.reload();
                    }
                });
            });
        },
        saveOrUpdate : function(event) {
            var url = vm.menu.id == null ? "/sys/res_do/create.do" : "/sys/res_do/update.do";
            $.ajax({
                type : "POST",
                url : rootPath + url,
                contentType : "application/json",
                data : JSON.stringify(vm.menu),
                success : function(r) {
                    alert(r.msg);
                    vm.reload();
                    // if (r.code === 0) {
                    // alert('操作成功', function() {
                    // vm.reload();
                    // });
                    // } else {
                    // alert(r.msg);
                    // }
                }
            });
        },
        menuTree : function() {
            layer.open({
                type : 1,
                offset : '50px',
                skin : 'layui-layer-molv',
                title : "选择菜单",
                area : [
                        '300px', '450px'],
                shade : 0,
                shadeClose : false,
                content : jQuery("#menuLayer"),
                btn : [
                        '确定', '取消'],
                btn1 : function(index) {
                    var node = ztree.getSelectedNodes();
                    // 选择上级菜单
                    vm.menu.parentId = node[0].id;
                    vm.menu.parentName = node[0].name;
                    layer.close(index);
                }
            });
        },
        reload : function() {
            vm.showList = true;
            Menu.table.refresh();
        }
    }
});
var Menu = {
    id : "menuTable",
    table : null,
    layerIndex : "0"
};
/**
 * 初始化表格的列
 */
Menu.initColumn = function() {
    var columns = [
            {
                field : 'selectItem',
                radio : true
            }, {
                title : '菜单ID',
                field : 'id',
                visible : false,
                align : 'center',
                valign : 'middle',
                width : '80px'
            }, {
                title : '菜单名称',
                field : 'name',
                align : 'center',
                valign : 'middle',
                sortable : true,
                width : '180px'
            },
            // {title: '上级菜单', field: 'parentid', align: 'center', valign:
            // 'middle',
            // sortable: true, width: '100px'},
            // {
            // title : '图标',
            // field : 'icon',
            // align : 'center',
            // valign : 'middle',
            // sortable : true,
            // width : '80px',
            // formatter : function(item, index) {
            // return item.icon == null ? '' : '<i class="' + item.icon + '
            // fa-lg"></i>';
            // }
            // },
            {
                title : '类型',
                field : 'type',
                align : 'center',
                valign : 'middle',
                sortable : true,
                width : '100px',
                formatter : function(item, index) {
                    if (item.type == "0") {
                        return '<span class="label label-primary">目录</span>';
                    }
                    if (item.type == "1") {
                        return '<span class="label label-success">菜单</span>';
                    }
                    if (item.type == "2") {
                        return '<span class="label label-warning">按钮</span>';
                    }
                }
            },
            // {
            // title : '排序号',
            // field : 'level',
            // align : 'center',
            // valign : 'middle',
            // sortable : true,
            // width : '100px'
            // },
            {
                title : '菜单URL',
                field : 'resUrl',
                align : 'center',
                valign : 'middle',
                sortable : true,
            }, {
                title : '授权标识',
                field : 'resKey',
                align : 'center',
                valign : 'middle',
                sortable : true
            }]
    return columns;
};
function getMenuId() {
    var selected = $('#menuTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}
$(function() {
    var colunms = Menu.initColumn();
    var table = new TreeTable(Menu.id, rootPath + "/sys/res_do/list.do", colunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("parentId");
    table.setExpandAll(true);
    table.init();
    Menu.table = table;
});
