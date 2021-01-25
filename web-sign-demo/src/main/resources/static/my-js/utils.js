function notice() {
    swal({
        title: "请稍等",
        text: "数据加载中",
        type: "info",
        showCancelButton: false,
        closeOnConfirm: false,
        showConfirmButton: false,
        showLoaderOnConfirm: false,
    });
}

/**
 * 组装form表单数据
 * 格式：json
 * 条件：不为空则加入，否则不加入该参数
 * @param id
 */
function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function (n, i) {
        if (n['value'] != "")
            indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

/**
 * 创建附件图片上传模块
 *
 * @param title 附件图片标题
 * @param id 表单id
 * @returns {string} 返回模块html
 */
function phpotoModal(title, id) {
    return "<div class=\"form-group col-md-4 photo\" id=\"box" + id + "\">\n" +
        "                                                      <label for=\"drop_area1\" class=\"drop_tooltip\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\""+title+"\">" + title + "</label>\n" +
        "                                                      <div class =\"drop_area\"  id=\"drop_area" + id + "\" style=\"width: 200px; height: auto; border: 1px solid rgb(204, 204, 204); padding: 10px; cursor: pointer;\">\n" +
        "                                                          <div class=\"preview\" id=\"photo" + id + "\"><img src=\"images/upload.png\" class=\"img-responsive\"  style=\"width: 100%;height: auto;\" alt=\"\" title=\"" + title + "\"> " +
        "<input id=\"" + id + "\" name=\"" + id + "\" type=\"hidden\">\n" +
        "                                                      </div>\n" +
        "\n" +
        "                                                  </div>"
}

/**
 * 设置图片附件表单base64值
 * @param id
 * @param file
 */
function setBase64(id, file) {
    var reader0 = new FileReader();
    var compressBas;
    reader0.readAsDataURL(file);
    reader0.onload = function (e) {
        $(".utilsCover").css("display", "block");
        var dataUrl = this.result;
        var fileType = file.type;
        var image = new Image();
        image.src = dataUrl;
        image.onload = function (e) {

            var w = this.naturalWidth, h = this.naturalHeight, resizeW = 0, resizeH = 0;
            var maxSize = {
                width: 1000,
                height: 1000,
                level: 0.5
            };
            if (w > maxSize.width || h > maxSize.height) {
                var multiple = Math.max(w / maxSize.width, h / maxSize.height);
                resizeW = w / multiple;
                resizeH = h / multiple;
            } else {// 如果图片尺寸小于最大限制，则不压缩直接上传
                resizeW = w;
                resizeH = h;
            }
            var canvas = document.createElement('canvas'),
                ctx = canvas.getContext('2d');
            canvas.width = resizeW;
            canvas.height = resizeH;
            ctx.drawImage(image, 0, 0, resizeW, resizeH);
            var base64 = canvas.toDataURL('image/jpeg', maxSize.level);
            $(id + " input").val(base64);
            console.log(base64)
        }
    }
}

function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function (n, i) {
        if (n['value'] != "")
            indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}



function dataURLtoFile(dataurl, filename) {//将base64转换为文件
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, { type: mime });
}


function getBlob(base64) {
    console.log(getContentType(base64))
    return b64toBlob(getData(base64), getContentType(base64));
}
function getContentType(base64) {
    return /data:([^;]*);/i.exec(base64)[1];
}

function getData(base64) {
    return base64.substr(base64.indexOf("base64,") + 7, base64.length);
}

function b64toBlob(b64Data, contentType, sliceSize) {
    contentType = contentType || '';
    sliceSize = sliceSize || 512;

    var byteCharacters = window.atob(b64Data);
    console.log("byteCharacters:", byteCharacters)
    var byteArrays = [];

    for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
        var slice = byteCharacters.slice(offset, offset + sliceSize);
        var byteNumbers = new Array(slice.length);
        for (var i = 0; i < slice.length; i++) {
            byteNumbers[i] = slice.charCodeAt(i);
        }
        var byteArray = new Uint8Array(byteNumbers);
        byteArrays.push(byteArray);
    }
    return new Blob(byteArrays, { type: contentType });
}

 //公共ajax错误处理方法
var errFunc = function () {
    swal("错误信息", " 软件出错了，请稍后访问或联系管理员！", "error");   
};




function isIE() {
    return (!!window.ActiveXObject || "ActiveXObject" in window);
}

function parseQueryString(url)
{
    var obj={};
    var keyvalue=[];
    var key="",value="";
    var paraString=url.substring(url.indexOf("?")+1,url.length).split("&");
    for(var i in paraString)
    {
        keyvalue=paraString[i].split("=");
        key=keyvalue[0];
        value=keyvalue[1];
        obj[key]=value;
    }
    return obj;
}
