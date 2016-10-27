

<script src="../../assets/js/jquery.min.js?v=2.1.4"></script>
<script src="../../assets/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="../../assets/js/bootstrap.min.js?v=3.3.6"></script>
<script src="../../assets/js/plugins/peity/jquery.peity.min.js"></script>
<script src="../../assets/js/plugins/jqgrid/i18n/grid.locale-cnffe4.js?0820"></script>
<script src="../../assets/js/plugins/jqgrid/jquery.jqGrid.minffe4.js?0820"></script>
<script src="../../assets/js/content.min.js?v=1.0.0"></script>


<!-- biz js引入 -->
<script src="../../assets/js/biz/biz-common.js"></script>
<script>

    $(document).ready(function () {
        $.jgrid.defaults.styleUI = "Bootstrap";
        var grid_selector = "#grid-table";
        var pager_selector = "#grid-pager";

        var gridUrl = '/admin/${bizSys}/${mainObj}s';
        if (isExitsFunction("initGridUrl")) {
            gridUrl = initGridUrl();
        }
        $(grid_selector).jqGrid({
            datatype: "json",
            url: gridUrl,
            height: 450,
            autowidth: true,
            shrinkToFit: true,
            rowNum:20,
            rowList:[10, 20, 30],
//            colNames: ["序号", "日期", "客户", "金额", "运费", "总额", "备注"],
            colNames:[
            <#list cols as col>
                '${col.displayName}'
                <#if col_has_next>
                    ,
                </#if>
            </#list>
            ],
            colModel:[<#list cols as col>
                {
                    <#if col.colId?exists>name: '${col.colId}',</#if>
                    <#if col.colId?exists>index: '${col.colId?if_exists}',</#if>
                    <#if col.width?exists>width:${col.width?if_exists},</#if>
                    sortable: false,
                    <#if col.editable?exists>editable: ${col.editable?if_exists},</#if>
                    <#if col.edittype?exists>edittype: "${col.edittype?if_exists}",</#if>
                    <#if col.editrules?exists>editrules:${col.editrules?if_exists},</#if>
                    <#if col.editoptions?exists>editoptions:${col.editoptions?if_exists},</#if>
                    <#if col.formatter?exists>formatter:${col.formatter?if_exists},</#if>
                    <#if col.formatoptions?exists>formatoptions:${col.formatoptions?if_exists},</#if>
                    <#if col.hide?exists>hidden:${(col.hide?if_exists==1)?string('true','false')}</#if>
                }

                <#if col_has_next>
                    ,
                </#if>
            </#list>],
            pager: pager_selector,
            jsonReader: {
                root: "bizData.rows",
                page: "bizData.page",
                total: "bizData.total",
                records: "bizData.records",
                id: "id"
            },
            //根据统一协议格式做处理
            beforeProcessing: function (data) {
                if ("0000000" != data.rtnCode) {
                    //TODO
                    console.log("请求远程数据失败！" + data.msg)
//                    alert("请求远程数据失败！" + data.msg)
                }
            },
            loadComplete: function (data) { //完成服务器请求后，回调函数
                var table = this;
                setTimeout(function () {
                    styleCheckbox(table);

                    updateActionIcons(table);
                    updatePagerIcons(table);
                    enableTooltips(table);
                }, 0);
            },
            onSelectRow: function (id, status) {
                //当前行id
                console.log("第" + id);
                if (status) {
                    currentGridId = id;
                }
                else {
                    currentGridId = null;
                }

            },
            viewrecords: true,
            caption: "${title}",
            add: true,
            edit: true,
            addtext: "Add",
            deltext:"Del",
        <#--addurl:  "/admin/${bizSys}/commonsave/${mainObj}",-->
            edittext: "Edit",
            editurl:  "/admin/${bizSys}/commonsave/${mainObj}",
            delurl:  "/admin/${bizSys}/commonsave/${mainObj}",
            hidegrid: false
        });
        $(grid_selector).setSelection(4, true);
        $(grid_selector).jqGrid('navGrid', pager_selector,
                { 	//navbar options
                    edit: ${actions?seq_contains("edit")?string("true", "false")}, //决定是否显示true
//                    editicon: 'ace-icon fa fa-pencil blue',
                    add: ${actions?seq_contains("add")?string("true", "false")},
//                    addicon: 'ace-icon fa fa-plus-circle purple',
                    del: ${actions?seq_contains("del")?string("true", "false")},
//                    delicon: 'ace-icon fa fa-trash-o red',
                    search: true,
//                    searchicon: 'ace-icon fa fa-search orange',
                    refresh: true,
//                    refreshicon: 'ace-icon fa fa-refresh green',
                    view: true,
//                    viewicon: 'ace-icon fa fa-search-plus grey'
                },
                {
                    //edit record form
                    closeAfterEdit: true,
                    width: 700,
                    recreateForm: true,
                    beforeShowForm: function (e) {
                        var form = $(e[0]);
                        form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                        style_edit_form(form);
                        if (isExitsFunction("enhanceBeforeShowForm")) { //如果有定义，执行之增强
                            enhanceBeforeShowForm(form);
                        }
                    },
                    onclickSubmit: function (params, postdata) {
                        if (isExitsFunction("enhancePostData")) { //如果有定义，执行之增强
                            postdata = enhancePostData(params, postdata);
                        }
                        return postdata;
                    },
                    afterSubmit: function (response, postdata) {
                        var rst = response.responseText;
                        if (null != rst && rst != undefined && rst != '') {
                            var result = response.responseJSON;
                            if (result.rtnCode != "0000000") {
                                return [false, result.msg];
                            }
                        } else {
                            return [false, '服务器内部错误!'];
                        }
                        return [true, ''];
                    }
                },
                {
                    //new record form
                    width: 700,
                    closeAfterAdd: true,
                    recreateForm: true,
                    viewPagerButtons: false,
                    beforeShowForm: function (e) {
                        var form = $(e[0]);
                        //console.log(form);
                        form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
                                .wrapInner('<div class="widget-header" />')
                        style_edit_form(form);
                        if (isExitsFunction("newBeforeShowForm")) { //如果有定义，执行之增强
                            newBeforeShowForm(form);
                        }
                    },
                    onclickSubmit: function (params, postdata) {
                        if (isExitsFunction("enhancePostData")) { //如果有定义，执行之增强
                            postdata = enhancePostData(params, postdata);
                        }
                        return postdata;
                    },
                    afterSubmit: function (response, postdata) {
                        var rst = response.responseText;
                        if (null != rst && rst != undefined && rst != '') {
                            var result = response.responseJSON;
                            if (result.rtnCode != "0000000") {
                                return [false, result.msg];
                            }
                        } else {
                            return [false, '服务器内部错误!'];
                        }
                        return [true, ''];
                    }
                },
                {
                    //delete record form
                    recreateForm: true,
                <#--url:"/admin/commondel/${mainObj}",-->
                    beforeShowForm: function (e) {
                        var form = $(e[0]);
                        //console.log(form);
                        if (form.data('styled')) return false;

                        form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                        style_delete_form(form);

                        form.data('styled', true);
                    },
                    onClick: function (e) {
                        alert(1);
                    },
                    afterSubmit: function (response, postdata) {
                        var rst = response.responseText;
                        if (null != rst && rst != undefined && rst != '') {
                            var result = response.responseJSON;
                            if (result.rtnCode != "0000000") {
                                return [false, result.msg];
                            }
                        } else {
                            return [false, '服务器内部错误!'];
                        }
                        return [true, ''];
                    }
                },
                {
                    //search form
                    recreateForm: true,
                    afterShowSearch: function (e) {
                        var form = $(e[0]);
                        form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
                        style_search_form(form);
                    },
                    afterRedraw: function () {
                        style_search_filters($(this));
                    }
                    ,
                    multipleSearch: true,
                    /**
                     multipleGroup:true,
                     showQuery: true
                     */
                },
                {
                    //view record form
                    recreateForm: true,
                    beforeShowForm: function (e) {
                        var form = $(e[0]);
                        form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
                    }
                }
                ,
                {
                    //import record form
                    recreateForm: true,
                    beforeShowForm: function (e) {
                        var form = $(e[0]);
                        form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
                    }
                }
        )
        $(window).bind("resize", function () {
            var width = $(".jqGrid_wrapper").width();
            $(grid_selector).setGridWidth(width);
        })
    });

    function style_edit_form(form) {
        //enable datepicker on "sdate" field and switches for "stock" field
        form.find('input[name=sdate]').datepicker({format: 'yyyy-mm-dd', autoclose: true})
                .end().find('input[name=stock]')
                .addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
        //don't wrap inside a label element, the checkbox value won't be submitted (POST'ed)
        //.addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');

        //update buttons classes
        var buttons = form.next().find('.EditButton .fm-button');
        buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();//ui-icon, s-icon
        buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
        buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')

        buttons = form.next().find('.navButton a');
        buttons.find('.ui-icon').hide();
        buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
        buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');
    }

    function style_delete_form(form) {
        var buttons = form.next().find('.EditButton .fm-button');
        buttons.addClass('btn btn-sm btn-white btn-round').find('[class*="-icon"]').hide();//ui-icon, s-icon
        buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
        buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>')
    }

    function style_search_filters(form) {
        form.find('.delete-rule').val('X');
        form.find('.add-rule').addClass('btn btn-xs btn-primary');
        form.find('.add-group').addClass('btn btn-xs btn-success');
        form.find('.delete-group').addClass('btn btn-xs btn-danger');
    }

    function style_search_form(form) {
        var dialog = form.closest('.ui-jqdialog');
        var buttons = dialog.find('.EditTable')
        buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
        buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'ace-icon fa fa-comment-o');
        buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'ace-icon fa fa-search');
    }

    function beforeDeleteCallback(e) {
        var form = $(e[0]);
        if (form.data('styled')) return false;

        form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        style_delete_form(form);

        form.data('styled', true);
    }

    function beforeEditCallback(e) {
        var form = $(e[0]);
        form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        style_edit_form(form);
    }


    //it causes some flicker when reloading or navigating grid
    //it may be possible to have some custom formatter to do this as the grid is being created to prevent this
    //or go back to default browser checkbox styles for the grid
    function styleCheckbox(table) {
        $(table).find('input:checkbox').addClass('ace')
                .wrap('<label />')
                .after('<span class="lbl align-top" />')


        $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
                .find('input.cbox[type=checkbox]').addClass('ace')
                .wrap('<label />').after('<span class="lbl align-top" />');
    }


    //unlike navButtons icons, action icons in rows seem to be hard-coded
    //you can change them like this in here if you want
    function updateActionIcons(table) {
        /**
         var replacement =
         {
             'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue',
             'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red',
             'ui-icon-disk' : 'ace-icon fa fa-check green',
             'ui-icon-cancel' : 'ace-icon fa fa-times red'
         };
         $(table).find('.ui-pg-div span.ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
         */
    }

    //replace icons with FontAwesome icons like above
    function updatePagerIcons(table) {
        var replacement =
        {
            'ui-icon-seek-first': 'ace-icon fa fa-angle-double-left bigger-140',
            'ui-icon-seek-prev': 'ace-icon fa fa-angle-left bigger-140',
            'ui-icon-seek-next': 'ace-icon fa fa-angle-right bigger-140',
            'ui-icon-seek-end': 'ace-icon fa fa-angle-double-right bigger-140'
        };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

            if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
        })
    }

    function enableTooltips(table) {
        $('.navtable .ui-pg-button').tooltip({container: 'body'});
        $(table).find('.ui-pg-div').tooltip({container: 'body'});
    }

    //var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');

    function setDefultValue(cellvalue, options, cell) {
        if (cellvalue) {
            return cellvalue;
        } else {
            return "";
        }
    }

    function mergerCatejory(cellvalue, options, cell) {
        if (cellvalue) {
            var mergerStr = "";
            for (var i = 0; i < cellvalue.length; i++) {
                mergerStr += cellvalue[i].name + "、";
            }
            if (mergerStr != "") {
                mergerStr = mergerStr.substring(0, mergerStr.length - 1)
            }
            return mergerStr;
        }
        console.log(cellvalue)
        return ""
    }

    function showImage(cellvalue, options, cell) {
        if (cellvalue) {
            var strHtml = '<a href="'+ cellvalue +'" target="_blank"><img src="' + cellvalue + '" style="width: 100px"></a>'
            return strHtml;
        }
        return ""
    }

    function subCellvalue(cellvalue, options, cell) {
        if (cellvalue) {
            var strHtml = cellvalue.substring(0,50)+"...";
            return strHtml;
        }
        return ""
    }

    <#if actions?if_exists?seq_contains("resource_assign")>
    .navButtonAdd(pager_selector, {
        id: "add-resource-action",
        title: "分配资源",
        caption: "",
        buttonicon: "ace-icon fa fa-pencil blue",
        onClickButton: function () {
            if (!currentGridId) {
                alert("请选择一行！");
                return;
            }

            var resourceUrl2 = "/admin/${bizSys}/${mainObj}/getAllResources?roleId=" + currentGridId;
            $.get(resourceUrl2, {}, function (result) {
                var setting = {
                    check: {
                        enable: true
                    },
                    data: {
                        simpleData: {
                            enable: true
                        },
                        key: {
                            name: "resourceName",
                            children: "resourceInfos"
                        }

                    }
                };
                if ("0000000" != result.rtnCode) {
                    alert("远程访问数据失败!");
                    return;
                }
                result = result.bizData;
                var zNodes = result;

                for (var i = 0; i < result.length; i++) {
                    var obj = result[i];
                    if (obj.resourceInfos) {
                        result[i].open = true;
                    }

                    var chidrens = obj.resourceInfos;
                    for (var m = 0; m < chidrens.length; m++) {
                        if (chidrens[m].roleId > 0) {
                            result[i].checked = true
                            chidrens[m].checked = true;
                        }
                        var third = chidrens[m].resourceInfos;
                        for (var n = 0; n < third.length; n++) {
                            if (third[n].roleId) {
                                third[n].checked = true;
                                chidrens[m].checked = true;
                                result[i].checked = true
                            }
                        }


                    }

                }
                var code;

                function setCheck() {
                    var zTree = $.fn.zTree.getZTreeObj("tree1"),
                            py = $("#py").attr("checked") ? "p" : "",
                            sy = $("#sy").attr("checked") ? "s" : "",
                            pn = $("#pn").attr("checked") ? "p" : "",
                            sn = $("#sn").attr("checked") ? "s" : "",
                            type = {"Y": py + sy, "N": pn + sn};
                    zTree.setting.check.chkboxType = type;
                    showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
                    var checked = zTree.getNodes()[0].checked;
                }

                function showCode(str) {
                    if (!code) code = $("#code");
                    code.empty();
                    code.append("<li>" + str + "</li>");
                }


                var treeObj = $.fn.zTree.init($("#tree1"), setting, zNodes);
                $("#sn").bind("change", setCheck);


                $("#treeFixed-01").find("#sData").click(function () {
                    var array = [];

                    var nodes = treeObj.getCheckedNodes(true);
                    for (var i = 0; i < nodes.length; i++) {
                        var pid = nodes[i].parentResourceId;
                        var resourceId = nodes[i].resourceId;
                        var obj = {
                            "parentResourceId": pid,
                            "resourceId": resourceId
                        }

                        array.push(obj);

                    }
                    var resource_submit_url = "/admin/${bizSys}/${mainObj}/assign";
                    $.post(resource_submit_url, {
                                objId: currentGridId,
                                resources: JSON.stringify(array)
                            }, function (result) {
                                alert(result.bizData)
                                $("#treeFixed-01").hide();
                            }
                    );

                });
                $("#treeFixed-01").show();

            });


        },
        position: "last"
    })
    </#if>
</script>
<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
