function createKC0fByBAB721(dataString) {
    $('#sampleTable_kc0f').DataTable( {
        destroy: true,
        bAutoWidth: true,
        data : dataString,
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
            { "title": "参保地" , "targets": 0},
            { "title": "省本级" , "targets": 1},
            { "title": "宝鸡市" , "targets": 2}
        ],
        "columns": [
            { "data": "bAB721_Name" ,"class": "center" ,"sDefaultContent" : "-",
                "render": function ( data, type, full, meta ) {
                    if (full.bAB721=='61126001' || full.bAB721=='61120001')
                        return '韩城市';
                    else
                        return data;
                }},
            { "data": "bAZ201" ,"class": "center" ,"sDefaultContent" : "-"}
        ]
    } );
}

function createKC0CByBAB721(dataString) {
    $('#sampleTable_kc0c').DataTable( {
        destroy: true,
        bAutoWidth: true,
        data : dataString,
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
            { "title": "参保地" , "targets": 0},
            { "title": "结算人次" , "targets": 1},
            { "title": "医疗总费用" , "targets": 2},
            { "title": "医保支付总额" , "targets": 3}
        ],
        "columns": [
            { "data": "bAB721_Name" ,"class": "center" ,"sDefaultContent" : "-",
                "render": function ( data, type, full, meta ) {
                    if (full.bAB721=='61126001' || full.bAB721=='61120001')
                        return '韩城市';
                    else
                        return data;
                }},
            { "data": "person_time" ,"class": "center" ,"sDefaultContent" : "-"},
            { "data": "bKCA08" ,"class": "center" ,"sDefaultContent" : "-"},
            { "data": "bKCA09" ,"class": "center" ,"sDefaultContent" : "-"}
        ]
    } );
}
