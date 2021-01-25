function createServerList(dataString) {
    $('#dataTable').DataTable({
        destroy: true,
        bAutoWidth: true,
        lengthMenu: [5],
        data: dataString,
        searching: false, // 禁用搜索框
        bLengthChange: false,   //去掉每页显示多少条数据下拉框
        language: {
            paginate: {
                first: '首页',
                previous: '上一页',
                next: '下一页',
                last: '尾页',
            },
            info: "第 _START_ 至 _END_ 行，共 _TOTAL_ 行",
            search: "搜索:",
            searchPlaceholder: "请输入要搜索内容...",
            processing: "处理中...",
            lengthMenu: "显示 _MENU_ 项结果",
            zeroRecords: "没有找到相应的结果",
            infoEmpty: "第 0 至 0 项结果，共 0 项",
            infoFiltered: "(由 _MAX_ 项结果过滤)",
        },
        "columnDefs": [
            {"title": "主机名", "targets": 0},
            {"title": "主机ip", "targets": 1},
            {"title": "用户名", "targets": 2},
            {"title": "操作", "targets": 3},
        ],
        "columns": [
            {"data": "name", "class": "center", "sDefaultContent": "-"},
            {"data": "ip", "class": "center", "sDefaultContent": "-"},
            {"data": "username", "class": "center", "sDefaultContent": "-"},
            {
                "data": "-", "class": "center", "sDefaultContent": "-", "render": function (data, type, full, meta) {
                    return "<div class=\"col-xs-8\"><div class=\"col-xs-4\"><button type=\"button\" class=\"btn btn-block btn-success\" onclick=\"addServerModal('" + full.id + "')\">新增服务</button></div>" +
                        "<div class=\"col-xs-4\"><button type=\"button\" class=\"btn btn-block btn-danger\">删除主机</button></div></div>"
                }
            }
        ]
    });
}


function createCurrentRuningStagee(dataString) {
    $('#mainHostDataTable').DataTable({
        destroy: true,
        bAutoWidth: true,
        lengthMenu: [30],
        data: dataString,
        searching: false, // 禁用搜索框
        bLengthChange: false,   //去掉每页显示多少条数据下拉框
        language: {
            paginate: {
                first: '首页',
                previous: '上一页',
                next: '下一页',
                last: '尾页',
            },
            info: "第 _START_ 至 _END_ 行，共 _TOTAL_ 行",
            search: "搜索:",
            searchPlaceholder: "请输入要搜索内容...",
            processing: "处理中...",
            lengthMenu: "显示 _MENU_ 项结果",
            zeroRecords: "没有找到相应的结果",
            infoEmpty: "第 0 至 0 项结果，共 0 项",
            infoFiltered: "(由 _MAX_ 项结果过滤)",
        },
        "columnDefs": [
            {"title": "服务名", "targets": 0},
            {"title": "服务端口", "targets": 1},
            {"title": "主机ip", "targets": 2},
            {"title": "状态", "targets": 3},
            {"title": "上次启动时间", "targets": 4},
        ],
        "columns": [
            {"data": "serverName", "class": "center", "sDefaultContent": "-"},
            {"data": "port", "class": "center", "sDefaultContent": "-"},
            {"data": "ip", "class": "center", "sDefaultContent": "-"},
            {
                "data": "state", "class": "center", "sDefaultContent": "-",
                "render": function (data, type, full, meta) {
                    switch (data) {
                        case "RUNNING":
                            return "<span class=\"pull-right-container\">\n" +
                                "              <small class=\"label bg-green\">RUNNING</small>\n" +
                                "            </span>"
                        case "WARNING":
                            return "<span class=\"pull-right-container\">\n" +
                                "              <small class=\"label bg-yellow\">WARNING</small>\n" +
                                "            </span>"
                        case "SHUTDOWN":
                            return "<span class=\"pull-right-container\">\n" +
                                "              <small class=\"label bg-red\">SHUTDOWN</small>\n" +
                                "            </span>"
                    }
                }
            },
            {"data": "lastStartTime", "class": "center", "sDefaultContent": "-"}
        ]
    });

    $('#dataTable tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);
        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child(format(row.data())).show();
            tr.addClass('shown');
        }
    });
}


function drawTable(dataString) {

    var table = $('#dataTable').DataTable({
        destroy: true,
        bAutoWidth: true,
        lengthMenu: [10],
        data: dataString,
        searching: false, // 禁用搜索框
        bLengthChange: false,   //去掉每页显示多少条数据下拉框
        language: {
            paginate: {
                first: '首页',
                previous: '上一页',
                next: '下一页',
                last: '尾页',
            },
            info: "第 _START_ 至 _END_ 行，共 _TOTAL_ 行",
            search: "搜索:",
            searchPlaceholder: "请输入要搜索内容...",
            processing: "处理中...",
            lengthMenu: "显示 _MENU_ 项结果",
            zeroRecords: "没有找到相应的结果",
            infoEmpty: "第 0 至 0 项结果，共 0 项",
            infoFiltered: "(由 _MAX_ 项结果过滤)",
        },
        "columnDefs": [
            {"title": "主机名", "targets": 0},
            {"title": "主机ip", "targets": 1},
            {"title": "用户名", "targets": 2},
            {"title": "操作", "targets": 3}
        ],
        "columns": [
            {"data": "name", "class": "center", "sDefaultContent": "-"},
            {"data": "ip", "class": "center", "sDefaultContent": "-"},
            {"data": "username", "class": "center", "sDefaultContent": "-"},
            {
                "data": "-", "class": "center", "sDefaultContent": "-", "render": function (data, type, full, meta) {
                    return "<div class=\"col-xs-12\">" +
                        "<div class=\"col-xs-3\"><button type=\"button\" class=\"btn btn-block btn-success\" onclick=\"addServerModal('" + full.id + "')\">新增服务</button></div>" +
                        "<div class=\"col-xs-3\"><button type=\"button\" class=\"btn btn-block btn-info\" onclick='editHost(" + JSON.stringify(full) + ")'>编辑主机</button></div>" +
                        "<div class=\"col-xs-3\"><button type=\"button\" class=\"btn btn-block btn-danger\">删除主机</button></div>" +
                        "<div class=\"col-xs-3\"><button type=\"button\" id='server_detail' class=\"btn btn-block btn-primary\">服务详情</button></div></div>"
                }
            }
        ]
    });


    // Add event listener for opening and closing details
    $('#dataTable tbody').on('click', '#server_detail', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        } else {
            // Open this row
            var child = format(row.data());
            row.child(child).show();
            tr.addClass('shown');
        }
    });
}

function format(d) {

    // `d` is the original data object for the row
    console.log(d);
    listServerInfoByHostId(d.id);
    return '<table id='+ d.id  +' cellpadding="5" cellspacing="0" border="0" style="padding-left:20px;' +
        ' width: 100% ;background: aliceblue;"></table>';
}

function listServerInfoByHostId(hostId) {
    $.ajax({
        type: 'POST',
        url: "/serverInfo/list",//路径
        data: {hostId: hostId},
        success: function (data) {
            var dataString = eval(data);
            var length = dataString.length;
            if (length > 0) {
                $.each(dataString, function () {
                    var tr = $("<tr><td style='font-weight: bold;padding:5px'>端口号：</td><td style='padding:5px'>"
                        + this.port + "</td><td style='font-weight: bold;padding:5px'>服务描述：</td><td style='padding:5px'>"
                        + this.description + "</td>" +
                        "<td  style='padding:5px'><div class=\"text-center\">\n" +
                        "<a class=\"btn btn-social-icon btn-info\" onclick='editServer(\" + JSON.stringify(dataString) + \")'><i class=\"fa fa-edit\"></i></a>\n" +
                        "<a class=\"btn btn-social-icon btn-danger\"><i class=\"fa fa-trash\"></i></a>\n" +
                        "</div></td></tr>");
                    $("#dataTable #"+ hostId).append(tr);

                });
            } else {
                console.log(length);
                console.log("#dataTable #" + hostId);
                $("#dataTable #" + hostId).append($("<tr><td style='padding:5px'>该服务器暂未添加任何服务</td></tr>"));
            }

        }
    });

}


function createRuningRedcord(dataString) {
    $('#histotyDataTable').DataTable({
        destroy: true,
        bAutoWidth: true,
        lengthMenu: [20],
        data: dataString,
        searching: false, // 禁用搜索框
        bLengthChange: false,   //去掉每页显示多少条数据下拉框
        language: {
            paginate: {
                first: '首页',
                previous: '上一页',
                next: '下一页',
                last: '尾页',
            },
            info: "第 _START_ 至 _END_ 行，共 _TOTAL_ 行",
            search: "搜索:",
            searchPlaceholder: "请输入要搜索内容...",
            processing: "处理中...",
            lengthMenu: "显示 _MENU_ 项结果",
            zeroRecords: "没有找到相应的结果",
            infoEmpty: "第 0 至 0 项结果，共 0 项",
            infoFiltered: "(由 _MAX_ 项结果过滤)",
        },
        "columnDefs": [
            {"title": "服务名", "targets": 0},
            {"title": "主机ip", "targets": 1},
            {"title": "状态", "targets": 2},
            {"title": "上次启动时间", "targets": 3},
            {"title": "记录时间", "targets": 4},
        ],
        "columns": [
            {"data": "serverName", "class": "center", "sDefaultContent": "-"},
            {"data": "serverId", "class": "center", "sDefaultContent": "-"},
            {
                "data": "state", "class": "center", "sDefaultContent": "-",
                "render": function (data, type, full, meta) {
                    switch (data) {
                        case "RUNNING":
                            return "<span class=\"pull-right-container\">\n" +
                                "              <small class=\"label bg-green\">RUNNING</small>\n" +
                                "            </span>"
                        case "WARNING":
                            return "<span class=\"pull-right-container\">\n" +
                                "              <small class=\"label bg-yellow\">WARNING</small>\n" +
                                "            </span>"
                        case "SHUTDOWN":
                            return "<span class=\"pull-right-container\">\n" +
                                "              <small class=\"label bg-red\">SHUTDOWN</small>\n" +
                                "            </span>"
                    }
                }
            },
            {"data": "lastStartTime", "class": "center", "sDefaultContent": "-"},
            {"data": "recordtime", "class": "center", "sDefaultContent": "-"}
        ]
    });
}
