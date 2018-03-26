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
    },
    setVueModel: function (vueObj, targetModel, val) {
        var index = targetModel.indexOf('.');
        if(index === -1) {
            Vue.set(vueObj, targetModel, val);
            return;
        }
        Vue.set(vueObj[targetModel.substr(0, index)], targetModel.substr(index + 1), val);
    }
};

/*vue 格式化日期函数*/
Vue.filter('formatDate', function (timestamp) {
    var date = new Date(timestamp);
    var fmt = 'yyyy-MM-dd hh:mm';
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    let o = {
        'M+': date.getMonth() + 1,
        'd+': date.getDate(),
        'h+': date.getHours(),
        'm+': date.getMinutes(),
        's+': date.getSeconds()
    };
    for (let k in o) {
        if (new RegExp(`(${k})`).test(fmt)) {
            let str = o[k] + '';
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : ('00' + str).substr(str.length));
        }
    }
    return fmt;
});
