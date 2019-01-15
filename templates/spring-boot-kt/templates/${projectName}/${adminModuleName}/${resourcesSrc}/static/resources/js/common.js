
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
    var o = {
        'M+': date.getMonth() + 1,
        'd+': date.getDate(),
        'h+': date.getHours(),
        'm+': date.getMinutes(),
        's+': date.getSeconds()
    };
    for (var k in o) {
        if (new RegExp(`(${k})`).test(fmt)) {
            var str = o[k] + '';
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : ('00' + str).substr(str.length));
        }
    }
    return fmt;
});
/*vue 格式化日期函数*/
Vue.filter('formatTime', function (timestamp) {
    var date = new Date(timestamp);
    var fmt = 'yyyy-MM-dd hh:mm';
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    var o = {
        'M+': date.getMonth() + 1,
        'd+': date.getDate(),
        'h+': date.getHours(),
        'm+': date.getMinutes(),
        's+': date.getSeconds()
    };
    for (var k in o) {
        if (new RegExp(`(${k})`).test(fmt)) {
            var str = o[k] + '';
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : ('00' + str).substr(str.length));
        }
    }
    return fmt;
});
/*vue 格式化日期函数*/
Vue.filter('formatDateTime', function (timestamp) {
    var date = new Date(timestamp);
    var fmt = 'yyyy-MM-dd hh:mm';
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    var o = {
        'M+': date.getMonth() + 1,
        'd+': date.getDate(),
        'h+': date.getHours(),
        'm+': date.getMinutes(),
        's+': date.getSeconds()
    };
    for (var k in o) {
        if (new RegExp(`(${k})`).test(fmt)) {
            var str = o[k] + '';
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : ('00' + str).substr(str.length));
        }
    }
    return fmt;
});

function getResponseMsg(response) {
    var msg = response.message;
    if (msg) {
        msg = '<h2>' + msg + '</h2>'
    }
    if (response.extData) {
        response.extData.forEach(function (item, index) {
            msg += '<br/>' + item;
        });
    }
}

Vue.prototype.ajaxPost = function (url, params, failMsg, successCallback, failCallback, errorCallback) {

    var self = this;
    axios.post(url, params).then(function (res) {
        var response = res.data;
        if (response.success == true) {
            if (successCallback) successCallback(response);
        } else {
            var msg;
            if (failMsg) {
                msg = failMsg;
            } else {
                msg = getResponseMsg(response)
            }
            self.$notify({
                dangerouslyUseHTMLString: true,
                message: msg,
                type: 'error'
            });
            if (fail) fail();
        }
    }).catch(function (error) {
        self.$notify({
            message: error,
            type: 'error'
        });
        if (errorCallback) errorCallback();
    });
};

Vue.prototype.ajaxGet = function (url, params, failMsg, successCallback, failCallback, errorCallback) {

    var self = this;
    axios.get(url, {
        params: params
    }).then(function (res) {
        var response = res.data;
        if (response.success == true) {
            if (successCallback) successCallback(response);
        } else {
            var msg;
            if (failMsg) {
                msg = failMsg;
            } else {
                msg = getResponseMsg(response)
            }
            self.$notify({
                dangerouslyUseHTMLString: true,
                message: msg,
                type: 'error'
            });
            if (failCallback) failCallback();
        }
    }).catch(function (error) {
        self.$notify({
            message: error,
            type: 'error'
        });
        if (errorCallback) errorCallback();
    });
};