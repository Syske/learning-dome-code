function queryServer() {
    $.ajax({
        type: 'POST',
        url: "/hostInfo/list",//路径
        success: function (data) {
            var dataString = eval(data);
            console.log(dataString)
            // createServerList(dataString);
            drawTable(dataString)
        }
    });
}

function addServerModal(id) {
    $("#addServerPortForm")[0].reset();
    $("#serverId").val(id);
    $("#modal-add-server").modal('show');
}


function addModalClose() {
    $("#addForm")[0].reset();
    $("#modal-add").modal('hide');
}

function editModalClose() {
    $("#editForm")[0].reset();
    $("#modal-edit").modal('hide');
}

function addModalServerPortClose() {
    $("#addServerPortForm")[0].reset();
}

function addModalSave() {
    $.ajax({
        type: "post", //提交方式
        dataType: "json", //数据类型
        data: $("#addForm").serialize(),//自定义数据参数，视情况添加
        url: "/hostInfo/add", //请求url
        success: function (data) { //提交成功的回调函数
            //console.log(JSON.parse(data))
            $("#modal-add").modal('hide');
            queryServer();
        }
    });
}

function addModalServerPortSave() {
    $.ajax({
        type: "post", //提交方式
        dataType: "json", //数据类型
        data: $("#addServerPortForm").serialize(),//自定义数据参数，视情况添加
        url: "/serverInfo/add", //请求url
        success: function (data) { //提交成功的回调函数
            var dataString = eval(data);
            console.log(dataString);
            $("#modal-add-server").modal('hide');
            queryServer();
        }
    });
}


function editHost(data) {
    $("#editForm")[0].reset();

    $("#editForm input").each(function () {
        console.log(this.name)
        console.log(data[this.name])
        if ("password_again" == this.name) {
            $(this).val(data['password']);
        } else {
            $(this).val(data[this.name]);
        }
    });

    $("#modal-edit").modal('show');
}

function editModalSave() {
    $.ajax({
        type: "post", //提交方式
        dataType: "json", //数据类型
        data: $("#editForm").serialize(),//自定义数据参数，视情况添加
        url: "/hostInfo/update", //请求url
        success: function (data) { //提交成功的回调函数
            $("#modal-edit").modal('hide');
            queryServer();
        }
    });
}