/*定义项目名*/
var progress = "/voucher";

// 本地测试
var DOMAIN = "";

//测试服务器
//var DOMAIN = "http://wx.lzgtzh.com/";

var APPID = "";

$.ajax({
	data : "get",
	url : progress + "/noticeset/mobile/getWeiXin.do?campusId="
			+ campusId,
	async : false,
	success : function(data) {
		console.log("data", data);
		data = JSON.parse(data);
		APPID = data.appId;
		DOMAIN =data.DOMAIN;
	}
})