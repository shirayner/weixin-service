/**
 * JS SDK 权限校验
 *
 * @type {string}
 */

var baseUrl='http://rayge.nat300.top/weixin-qy';
var state='ray';

// 1.获取JS SDK 配置
var _config;
var encodedUrl = encodeURIComponent(window.location.href);
$.ajax({
    type: "GET",
    url: baseUrl+'/auth/jsSdkConfig?state='+state+'&requestUrl=' + encodedUrl,
    //必须设置为同步请求，才会按顺序执行js
    async: false,
    success: function(data) {
        _config = data.data;
    },
    error: function(data) {
        var error = JSON.stringify(data);
        alert("jsSdkConfig请求失败:" + error);
    }
});

//alert("configTemp:" + JSON.stringify(_config));


//2.jsapi签名校验
wx.config({
    beta: true,
    debug: true,
    appId: _config.appId,
    timestamp: _config.timestamp,
    nonceStr: _config.nonceStr,
    signature: _config.signature,
    jsApiList: ['checkJsApi', 'onMenuShareAppMessage',
        'onMenuShareWechat', 'startRecord',
        'stopRecord', 'onVoiceRecordEnd',
        'playVoice', 'pauseVoice',
        'stopVoice', 'uploadVoice',
        'downloadVoice', 'chooseImage',
        'previewImage', 'uploadImage',
        'downloadImage', 'getNetworkType',
        'openLocation', 'getLocation',
        'hideOptionMenu', 'showOptionMenu',
        'hideMenuItems', 'showMenuItems',
        'hideAllNonBaseMenuItem', 'showAllNonBaseMenuItem',
        'closeWindow', 'scanQRCode',
        'previewFile', 'openEnterpriseChat',
        'selectEnterpriseContact', 'chooseInvoice'

    ],

    fail: function(res) {
        alert('config error: ' + JSON.stringify(res));
    }


});

wx.error(function(error){
    alert('weixin error: ' + JSON.stringify(error));
});

wx.ready(function(){
    //3.校验成功
    //alert("ready");

});


function checkJsApi(){
    wx.checkJsApi({
        jsApiList: ['checkJsApi', 'onMenuShareAppMessage',
            'onMenuShareWechat', 'startRecord',
            'stopRecord', 'onVoiceRecordEnd',
            'playVoice', 'pauseVoice',
            'stopVoice', 'uploadVoice',
            'downloadVoice', 'chooseImage',
            'previewImage', 'uploadImage',
            'downloadImage', 'getNetworkType',
            'openLocation', 'getLocation',
            'hideOptionMenu', 'showOptionMenu',
            'hideMenuItems', 'showMenuItems',
            'hideAllNonBaseMenuItem', 'showAllNonBaseMenuItem',
            'closeWindow', 'scanQRCode',
            'previewFile', 'openEnterpriseChat',
            'selectEnterpriseContact', 'chooseInvoice'

        ],
        // 需要检测的JS接口列表，所有JS接口列表见附录2,
        success: function(res) {
            alert(res);
            console.log(res);
            // 以键值对的形式返回，可用的api值true，不可用为false
            // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
        },
        fail:function(res) {
            alert(res);
            console.log(res);
            // 以键值对的形式返回，可用的api值true，不可用为false
            // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
        },
    });
}

