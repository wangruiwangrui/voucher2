//版本以用js
var campusId=1; //公众号id

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
   }

var code=getQueryString("code");
var state=getQueryString("state");

var VERSION='';

//进入index2.html初始化当前版本号
$.post('/voucher/mobile/flow/getVersion.do', {
	item : 'Version',
	limit : 1,
	offset : 0,
	sort : "",
	order : ""
}, function(data) {
	data = JSON.parse(data);
	VERSION = data.content;
});


//yayui弹出界面显示版本号
layui.use(['layer','form'],function() { //独立版的layer无需执行这一句
	var $ = layui.jquery, layer = layui.layer,form = layui.form; //独立版的layer无需执行这一句
	form.on("submit(zhushou)",function(data){
		//示范一个公告层
		layer.open({
				type : 1,
				title : false, //不显示标题栏
				closeBtn : false,
				area : '300px;',
				shade : 0.8,
				id : 'LAY_layuipro', //设定一个id，防止重复弹出,
				btn : [ '我知道了' ],
				btnAlign : 'c',
				moveType : 1, //拖拽模式，0或者1
				content : "<div style='padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;'>你知道吗？亲！<br><br>资产管理助手当前版本："+VERSION+"<br><br><br>",
				success : function(layero) {
					
					
				}
		});

	})
	//触发事件

});

