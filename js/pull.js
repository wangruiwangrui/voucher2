var pull=(function(){
    U.fillHead('#pull_wrapper');
	var scroll;
	initIscroll();
	initPage();
	function initPage() {
		$('.content').html('');
		loadDate();
	}
	function loadDate(){
		var images=[http://imgsrc.baidu.com/imgad/pic/item/267f9e2f07082838b5168c32b299a9014c08f1f9.jpg,http://img06.tooopen.com/images/20160712/tooopen_sy_170083325566.jpg]
			W.loadWaterfall({'selector':_('.content'),'images':images,'count':3,'callback':function(){
				scroll.refresh();
			}});
			scroll.refresh();
	}
	function initIscroll(){
		$('#pull_wrapper').height($(window).height()-45);
		pullDownEl = document.getElementById('pull_pullDown');
		pullDownOffset = pullDownEl.offsetHeight;
		pullUpEl = document.getElementById('pull_pullUp');
		pullUpOffset = pullUpEl.offsetHeight;
		scroll = new iScroll('pull_wrapper', {
			scrollbarClass: 'myScrollbar',
			useTransition: false,
			topOffset: pullDownOffset,
			onRefresh: function() {
				if(pullDownEl.className.match('loading')) {
					pullDownEl.className = '';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
					$(pullDownEl.querySelector('.pullDownIcon')).attr('src','images/icons/pullDown.png');
				} else if(pullUpEl.className.match('loading')) {
					pullUpEl.className = '';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
					$(pullUpEl.querySelector('.pullUpIcon')).attr('src','images/icons/pullUp.png');
				}
			},
			onScrollMove: function() {
				if(this.y > 15 && !pullDownEl.className.match('flip')) {
					pullDownEl.className = 'flip';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = '松手开始更新...';
					$(pullDownEl.querySelector('.pullDownIcon')).attr('src','images/icons/pullUp.png');
					this.minScrollY = 0;
				} else if(this.y < 15 && pullDownEl.className.match('flip')) {
					pullDownEl.className = '';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
					$(pullDownEl.querySelector('.pullDownIcon')).attr('src','images/icons/pullDown.png');
					this.minScrollY = -pullDownOffset;
				} else if(this.y < (this.maxScrollY - 15) && !pullUpEl.className.match('flip')) {
					pullUpEl.className = 'flip';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手开始更新...';
					$(pullUpEl.querySelector('.pullUpIcon')).attr('src','images/icons/pullDown.png');
					this.maxScrollY = this.maxScrollY;
				} else if(this.y > (this.maxScrollY + 15) && pullUpEl.className.match('flip')) {
					pullUpEl.className = '';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
					$(pullUpEl.querySelector('.pullUpIcon')).attr('src','images/icons/pullUp.png');
					this.maxScrollY = pullUpOffset;
				}
			},
			onScrollEnd: function() {
				if(pullDownEl.className.match('flip')) {
					pullDownEl.className = 'loading';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';
					$(pullDownEl.querySelector('.pullDownIcon')).attr('src','images/icons/refresh.gif');
					initPage();
				} else if(pullUpEl.className.match('flip')) {
					pullUpEl.className = 'loading';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
					$(pullUpEl.querySelector('.pullUpIcon')).attr('src','images/icons/refresh.gif');
					loadDate();
				}
				I.backTop(scroll,$('#pull_wrapper'),35);
			}
		});
	}
	return {'initPage':function(){
		null;
	}}
})()
