<!-- 增加过滤div + 主体表格 -->
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                </div>

                <#-- 报表数据图标方式展示部分  暂时 二维折线图 -->
                <#--<div class="col-sm-12">-->
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>折线图</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                                <a class="dropdown-toggle" data-toggle="dropdown" href="graph_flot.html#">
                                    <i class="fa fa-wrench"></i>
                                </a>
                                <ul class="dropdown-menu dropdown-user">
                                    <li><a href="graph_flot.html#">选项1</a>
                                    </li>
                                    <li><a href="graph_flot.html#">选项2</a>
                                    </li>
                                </ul>
                                <a class="close-link">
                                    <i class="fa fa-times"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content">
                            <div class="echarts" id="echarts-line-chart" >
                            </div>
                        </div>
                    </div>
                <#--</div>-->

                <#-- 报表数据表格方式展示部分 -->
                <div class="ibox-content">
                    <p>&nbsp;</p>
                <#include '../../page_grid.ftl'>



                </div>
            </div>
        </div>
    </div>
</div>
</div><!-- /.page-content -->
