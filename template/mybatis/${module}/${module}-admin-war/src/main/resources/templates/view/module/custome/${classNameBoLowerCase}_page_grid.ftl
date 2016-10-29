<!-- 增加过滤div + 主体表格 -->
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                </div>
                <div class="ibox-content">
                    <p>&nbsp;</p>
                ${r"<#include '../../page_grid.ftl'>"}

                    <div class="treeFixed" id="treeFixed-01">
                        <div class="widget-header"><span class="ui-jqdialog-title"
                                                         style="float: left; line-height: 38px;">添加资源</span><a
                                class="ui-jqdialog-titlebar-close ui-corner-all" style="right: 0.3em;"><span
                                class="ui-icon ui-icon-closethick"></span></a></div>
                        <ul id="tree1" class="ztree "></ul>

                        <div class="EditButton">
                            <a id="sData"
                               class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary"><i
                                    class="ace-icon fa fa-check"></i>提交<span class="ui-icon ui-icon-disk"
                                                                             style="display: none;"></span></a>
                            <a id="cData"
                               class="fm-button  close-btn ui-state-default ui-corner-all fm-button-icon-left btn btn-sm"><i
                                    class="ace-icon fa fa-times"></i>关闭<span class="ui-icon ui-icon-close"
                                                                             style="display: none;"></span></a>
                        </div>
                    </div>


                </div>
            </div>
        </div>
    </div>
</div>
</div><!-- /.page-content -->
