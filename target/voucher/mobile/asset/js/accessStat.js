/**
 * 
 */


  function pageName(){
	
        var a = location.href;
        var b = a.split("/");
        var c = b.slice(b.length-1, b.length).toString(String).split(".");
        return c.slice(0, 1);
    
	}

  var campusId=1;
  var page=pageName();
  
  var httpRequest=new XMLHttpRequest();
	  
  var xhm=new XMLHttpRequest();
  xhm.open("GET","/voucher/mobile/asset/insertAccess.do?campusId="+campusId+"&page="+page,false);
  xhm.setRequestHeader("Content-type","application/x-www-form-urlencoded");
  xhm.send();
    

  