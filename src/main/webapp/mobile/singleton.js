var campusId = "1";

/*定义项目名*/
var progress = "/voucher";

//var APPID="wx040a468505773e18";

// 工投
// var APPID="wxc4d4aea5eff9b124"

// 兴泸
// var APPID="wx99b133fdd397c949";

// 本地测试
var DOMAIN = "http://nwx.wtsms.net";
//var DOMAIN="http://lzxlzc.com";
//测试服务器
//var DOMAIN = "http://test.lzgtzh.com";

//工投演示服务器
//var DOMAIN="http://wx.lzgtzh.com";

// 叙兴
// var DOMAIN="http://xxsyjt.cn";
// var DOMAIN="http://xx.lzgtzh.com";

// 合江金宇
// var DOMAIN="http://jinyu.lzgtzh.com";


// 古蔺国资
//var DOMAIN="http://www.gulinguozi.com";

var APPID = "";

$.ajax({
	data : "get",
	url : progress+"/noticeset/mobile/getWeiXin.do?campusId=" + campusId,
	async : false,
	success : function(data) {
		data = JSON.parse(data);
		APPID = data.appId;
	}
})
