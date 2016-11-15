<script src="../../assets/js/plugins/echarts/echarts-all.js"></script>
<script>

    initChart();
    function initChart() {
        var chart = echarts.init(document.getElementById("echarts-line-chart"));
        $.get("/admin/${bizSys}/chart/${mainObj}s", function (result) {
            chart.setOption(result.bizData);
            $(window).resize(chart.resize);
        })
    }

    <!-- 自定义js请写在这个文件  以下这个查询方法只是个例子，请按照业务需求修改 -->
//    function buildRules() {
//        var courseName = $('#courseName').val();
//        var status = $('#status').val();
//        var classfyId = $('#classfyId').val();
//        var rules = [];
//        if (courseName != ''&&courseName!=null&&courseName!=undefined) {
//            var rule = {
//                'field': 'courseName',
//                'op': 'eq',
//                'data': courseName
//            }
//            rules.push(rule);
//        }
//        if (status != ''&&status!=null&&status!=undefined) {
//            var rule = {
//                'field': 'status',
//                'op': 'eq',
//                'data': status
//            }
//            rules.push(rule);
//        }
//        if (classfyId != ''&&classfyId!=null&&classfyId!=undefined) {
//            var rule = {
//                'field': 'classfyId',
//                'op': 'eq',
//                'data': classfyId
//            }
//            rules.push(rule);
//        }
//        return rules;
//    }
    function searchLoad(){
        var url = "/admin/${bizSys}/${mainObj}s";
        var rules = buildRules();
        var filters = {
            'groupOp': 'AND',
            "rules": rules
        };
        $("#grid-table").jqGrid('setGridParam', {url:url,mtype:"POST",postData:"filters="+encodeURIComponent(JSON.stringify(filters)),page: 1}).trigger("reloadGrid");
    }

//    $("#search").click(function () {
//        searchLoad();
//    });
</script>
