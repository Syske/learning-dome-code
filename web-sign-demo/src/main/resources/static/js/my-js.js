function aj(url, data, successFn, errorFn) {
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        data: data,
        success: function (data) {
            successFn(data);
        },
        error: function (error) {
            errorFn(error);
        }
    });
}