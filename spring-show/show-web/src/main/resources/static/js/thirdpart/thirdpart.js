var vm = new Vue({
    el : '#rrapp',
    data : {
        title : "第三方登录信息配置",
        sina : {
            client_ID : null
        },
        baidu : {
            client_ID : null
        },
        weixin : {
            appId : null
        },
        qq : {
            client_ID : null
        },
        github : {
            client_ID : null
        },
        thirdpart : 2
    },
    methods : {
        query : function() {
            vm.title = "第三方登录信息配置";
            $.ajax({
                type : "POST",
                url : rootPath + "/thirdpart/thirdpartconfig_do/info.do",
                dataType : "json",
                success : function(data) {
                	vm.baidu = data.baidu;
                    /*vm.qq = data.qq;
                    vm.sina = data.sina;
                    vm.weixin = data.weixin;
                    vm.github = data.github;*/
                }
            });
        },
        saveOrUpdate : function() {
            $.ajax({
                type : "POST",
                url : rootPath + "/thirdpart/thirdpartconfig_do/update.do",
                data : {
                    baiduid : vm.baidu.client_ID,
                    baiduSERCRET : vm.baidu.client_SERCRET,
                    sinaid : vm.sina.client_ID,
                    sinaSERCRET : vm.sina.client_SERCRET,
                    weixinid : vm.weixin.appId,
                    weixinSERCRET : vm.weixin.secret,
                    qqid : vm.qq.client_ID,
                    qqSERCRET : vm.qq.client_SERCRET,
                    githubid : vm.github.client_ID,
                    githubSERCRET : vm.github.client_SERCRET
                },
                success : function(data) {
                    alert(data.msg);
                }
            });
        }
    }
});
vm.query();
