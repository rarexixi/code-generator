var commonNotify = {
    delay: 1000,

    info: function (message, callback) {
        var that = this;
        $.bootstrapGrowl(message, {
            type: 'info',
            delay: 1000,
            align: 'center'
        });
        if (callback) {
            setTimeout(callback, that.delay);
        }
    },
    success: function (message, callback) {
        var that = this;
        $.bootstrapGrowl(message, {
            type: 'success',
            delay: 1000
        });
        if (callback) {
            setTimeout(callback, that.delay);
        }
    },
    danger: function (message, callback) {
        var that = this;
        $.bootstrapGrowl(message, {
            type: 'danger',
            delay: 1000
        });
        if (callback) {
            setTimeout(callback, that.delay);
        }
    },
    warning: function (message, callback) {
        var that = this;
        $.bootstrapGrowl(message, {
            type: 'warning',
            delay: 1000
        });
        if (callback) {
            setTimeout(callback, that.delay);
        }
    },
    confirm: function (message, callback) {

        bootbox.confirm({
            buttons: {
                confirm: {
                    label: '确定',
                },
                cancel: {
                    label: '取消',
                }
            },
            size: "small",
            message: message,
            callback: function (result) {
                if (result && callback) {
                    callback();
                }
            }
        })
    }
};

var commonFun = {

    getParam: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]);
        return null;
    }
};

$(function() {

    $('input[type="datetime"]').datetimepicker({
        autoclose: 1
    });
    
    $('input[type="date"]').datetimepicker({
        format: 'yyyy-mm-dd',
        minView: "month",
        autoclose: 1
    });
});
