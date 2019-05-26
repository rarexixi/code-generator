const commonFun = {
    getParam: function (name) {
        let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        let r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]);
        return '';
    },
    setVueModel: function (vueObj, targetModel, val) {
        let index = targetModel.indexOf('.');
        if (index === -1) {
            Vue.set(vueObj, targetModel, val);
            return;
        }
        Vue.set(vueObj[targetModel.substr(0, index)], targetModel.substr(index + 1), val);
    },
    formatDateTime: function (timestamp, fmt) {
        let date = new Date(timestamp);
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
    }
};

/*vue 格式化日期函数*/
Vue.filter('formatDate', timestamp => commonFun.formatDateTime(timestamp, 'yyyy-MM-dd'));
/*vue 格式化时间函数*/
Vue.filter('formatTime', timestamp => commonFun.formatDateTime(timestamp, 'hh:mm:ss'));
/*vue 格式化日期时间函数*/
Vue.filter('formatDateTime', timestamp => commonFun.formatDateTime(timestamp, 'yyyy-MM-dd hh:mm:ss'));

function getResponseMsg(failMsg, response) {
    let msg = response.msg || failMsg;
    if (response.extData) {
        response.extData.forEach((item, index) => {
            msg += '<br/>' + item;
        });
    }
    return msg;
}

Vue.prototype.ajaxPost = function (url, params, failMsg, successCallback, failCallback, errorCallback) {

    let self = this;
    axios.post(appConfig.baseApiPath + url, params).then(res => {
        let response = res.data;
        if (response.success == true) {
            if (successCallback) successCallback(response);
        } else {
            let msg = getResponseMsg(failMsg, response);
            self.$notify({
                dangerouslyUseHTMLString: true,
                message: msg,
                type: 'error'
            });
            if (failCallback) failCallback();
        }
    }).catch(error => {
        self.$notify({
            message: error,
            type: 'error'
        });
        if (errorCallback) errorCallback();
    });
};

Vue.prototype.ajaxGet = function (url, params, failMsg, successCallback, failCallback, errorCallback) {

    let self = this;
    axios.get(appConfig.baseApiPath + url, {
        params: params
    }).then(res => {
        let response = res.data;
        if (response.success == true) {
            if (successCallback) successCallback(response);
        } else {
            let msg = getResponseMsg(failMsg, response);
            self.$notify({
                dangerouslyUseHTMLString: true,
                message: msg,
                type: 'error'
            });
            if (failCallback) failCallback();
        }
    }).catch(error => {
        self.$notify({
            message: error,
            type: 'error'
        });
        if (errorCallback) errorCallback();
    });
};

Vue.prototype.confirmPost = function (confirmMsg, url, params, successMsg, failMsg, successCallback) {
    let self = this;
    self.$confirm(confirmMsg, '', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        self.ajaxPost(url, params, failMsg, response => {
            self.$notify({
                type: 'success',
                message: successMsg
            });
            if (successCallback) successCallback(response);
        });
    }, () => {});
};