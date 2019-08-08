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
// 叙兴
// var APPID="wx3ac0d670268db73c";
// var DOMAIN="http://xx.lzgtzh.com";
var DOMAIN = "http://nwx.wtsms.net";
// 叙兴
// var DOMAIN="http://xxsyjt.cn";
// var DOMAIN="http://xx.lzgtzh.com";
// var DOMAIN="http://wx.lzgtzh.com";

var VERSION="V10.0.004Beta";
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
