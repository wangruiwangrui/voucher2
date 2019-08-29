/**
 * 
 */
// 公房
// var APPID="wx040a468505773e18";
var campusId = "1";
// 工投
// var APPID="wxc4d4aea5eff9b124"

// 兴泸
// var APPID="wx99b133fdd397c949";

// 本地测试
var DOMAIN = "http://nwx.wtsms.net";

//测试服务器
//var DOMAIN = "http://test.lzgtzh.com";

//工投演示服务器
//var DOMAIN="http://wx.lzgtzh.com";

// 叙兴
// var DOMAIN="http://xxsyjt.cn";
// var DOMAIN="http://xx.lzgtzh.com";
<<<<<<< HEAD

// 合江金宇
// var DOMAIN="http://jinyu.lzgtzh.com";
=======
>>>>>>> 1bcb55cc73926c8ca8de173170efc028e8df4faa

// 合江金宇
// var DOMAIN="http://jinyu.lzgtzh.com";

//var VERSION="V10.0.004Beta";
var APPID = "";

$.ajax({
	data : "get",
	url : "/voucher/noticeset/mobile/getWeiXin.do?campusId=" + campusId,
	async : false,
	success : function(data) {
		data = JSON.parse(data);
		APPID = data.appId;
	}
})
