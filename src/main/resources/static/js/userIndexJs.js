$(function () {
    userIndexJs.bindEvent();
});
var userIndexJs = {
    bindEvent: function () {
        userIndexJs.event.workInfoList('all');
    },
    event: {
        workInfoList: function (type) {
            layui.use('table', function () {
                var table = layui.table;
                $("#work-list").removeClass('layui-hide');
                $("#wait-list").addClass('layui-hide');
                $("#my-list").addClass('layui-hide');
                table.render({
                    elem: '#work-list-table'
                    , height: 485
                    , url: '../work/list'
                    , page: true //开启分页
                    , where: {
                        type: type
                    }
                    , limits: [5, 10, 20]
                    , limit: 10
                    , cols: [[ //表头
                        {field: 'id', title: '编号', width: 70}
                        , {field: 'title', title: '主题', width: 180}
                        , {field: 'user_name', title: '发布者', width: 100}
                        , {field: 'contact', title: '联系方式', width: 150}
                        , {field: 'content', title: '内容', width: 200}
                        , {field: 'address', title: '工作地点', width: 180}
                        , {field: 'work_time', title: '工作时间', width: 200}
                        , {field: 'create_time', title: '发布时间', width: 190}
                        , {
                            field: 'operate',
                            title: '操作',
                            toolbar: "#work-list-table-operate"
                        }
                    ]]
                });
                table.on('tool(work-list-table-fit)', function (obj) {
                    if (obj.event === 'edit') {
                        layer.confirm('确定报名该兼职？', function (index) {
                            $.ajax({
                                url: '../work/apply',
                                data: {
                                    workId: obj.data.id
                                },
                                type: 'get',
                                success: function (result) {
                                    if (result.status_code == 200) {
                                        layer.msg("报名成功");
                                        userIndexJs.event.workInfoList('all');
                                    } else {
                                        layer.msg(result.message);
                                    }
                                },
                                error: function () {
                                    layer.msg("数据请求异常");
                                    layer.closeAll()
                                }
                            })
                        })
                    }
                })
            });
        }
    },
    method: {
        applyToUsList: function () {
            layui.use('table', function () {
                var table = layui.table;
                $("#wait-list").removeClass('layui-hide');
                $("#work-list").addClass('layui-hide');
                $("#my-list").addClass('layui-hide');
                table.render({
                    elem: '#wait-list-table'
                    , height: 485
                    , url: '../user-apply/list'
                    , page: true //开启分页
                    , limits: [5, 10, 20]
                    , limit: 10
                    , cols: [[ //表头
                        {field: 'id', title: '编号', width: 70}
                        , {field: 'work_info_id', title: '兼职编号', width: 100}
                        , {field: 'title', title: '申请的主题', width: 150}
                        , {field: 'user_name', title: '申请者', width: 110}
                        , {field: 'phone', title: '申请者联系方式', width: 150}
                        , {field: 'sex_str', title: '性别', width: 80}
                        , {field: 'age', title: '年龄', width: 80}
                        , {field: 'school', title: '学校', width: 140}
                        , {field: 'status_str', title: '状态', width: 100}
                        , {field: 'create_time', title: '申请时间', width: 180}
                        , {
                            field: 'operate',
                            title: '操作',
                            toolbar: "#wait-list-table-operate"
                        }
                    ]]
                });
                table.on('tool(wait-list-table-fit)', function (obj) {
                    if (obj.event === 'pass') {
                        userIndexJs.method.commonAuditWorkApply('确定通过审核吗？', obj.data.id, 1);
                    } else {
                        userIndexJs.method.commonAuditWorkApply('确定拒绝通过吗？', obj.data.id, -1);
                    }
                })
            });
        },
        commonAuditWorkApply: function (text, id, status) {
            layer.confirm(text, function (index) {
                $.ajax({
                    url: '../user-apply/audit',
                    data: {
                        id: id,
                        status: status
                    },
                    type: 'get',
                    success: function (result) {
                        if (result.status_code == 200) {
                            layer.msg("审核完成");
                            userIndexJs.method.applyToUsList();
                        } else {
                            layer.msg(result.message);
                        }
                    },
                    error: function () {
                        layer.msg("数据请求异常");
                        layer.closeAll()
                    }
                })
            })
        },
        myApplyList: function () {
            layui.use('table', function () {
                var table = layui.table;
                $("#my-list").removeClass('layui-hide');
                $("#wait-list").addClass('layui-hide');
                $("#work-list").addClass('layui-hide');
                table.render({
                    elem: '#my-list-table'
                    , height: 485
                    , url: '../user-apply/my-list'
                    , page: true //开启分页
                    , limits: [5, 10, 20]
                    , limit: 10
                    , cols: [[ //表头
                        {field: 'id', title: '编号', width: 70}
                        , {field: 'work_info_id', title: '兼职编号', width: 100}
                        , {field: 'title', title: '申请的主题', width: 150}
                        , {field: 'user_name', title: '申请者', width: 110}
                        , {field: 'phone', title: '申请者联系方式', width: 150}
                        , {field: 'sex_str', title: '性别', width: 80}
                        , {field: 'age', title: '年龄', width: 80}
                        , {field: 'school', title: '学校', width: 140}
                        , {field: 'status_str', title: '状态', width: 120}
                        , {field: 'create_time', title: '申请时间'}
                    ]]
                });
            });
        },
        toRelease: function () {
            layui.use('layer', function (layer) {
                layer.open({
                    type: 1
                    , skin: 'examine-refuse-popup'
                    , offset: 'auto'
                    , title: '我要发布'
                    , id: 'layer-id'
                    , area: ['500px', '600px']
                    , content: $("#dialog-add-work-info")
                    , btn: ['确定', '取消']
                    , shade: 0.5 //不显示遮罩
                    , end: function () {
                        $("#dialog-add-work-info").css("display", "none");
                    }
                    , yes: function () {
                        userIndexJs.method.addWorkInfo();
                    }
                    , btn2: function () {

                    }
                });
            });
        },
        addWorkInfo: function () {
            layer.close(layer.index);
            var data = {};
            data.title = $("#add-title").val();
            data.content = $("#add-content").val();
            data.address = $("#add-address").val();
            data.work_time = $("#add-work-time").val();
            if (!data.title) {
                layer.msg("主题不允许为空");
                return;
            }
            if (!data.content) {
                layer.msg("内容不允许为空");
                return;
            }
            if (!data.address) {
                layer.msg("地址不允许为空");
                return;
            }
            if (!data.work_time) {
                layer.msg("工作时间不允许为空");
                return;
            }
            $.ajax({
                url: '../work/add',
                type: 'post',
                data: JSON.stringify(data),
                contentType: 'application/json',
                success: function (result) {
                    if (result.status_code == 200) {
                        layer.msg('发布成功');
                        userIndexJs.event.workInfoList('my');
                    } else {
                        layer.msg(result.message);
                    }
                },
                error: function () {
                    layer.msg('数据异常');
                    layer.closeAll();
                }
            })
        }

    }
}
