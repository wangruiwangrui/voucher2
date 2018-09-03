package com.voucher.manage.templet;

import java.security.KeyStore.PrivateKeyEntry;

import javax.imageio.IIOImage;

/**
 * 文章
 *
 */
public class Article {
    /*
     * 编号
     */
    private String id;
    /*
     * 公众号ID
     */
    private String campusId;
    
    /*
     * 管理员ID
     */
    private String campusAdmin;
    /*
     * 标题
     */
    private String title;
    /*
     * 内容
     */
    private String content;

    
    private Float price;
    
    /*
            有效期
            开始时间
     */       
    private String starttime;
    
    //结束时间
    private String endtime;
   
    
    //使用时间
    private String usetime="";
    
    //预约提醒
    private String bespoke="";
    
    //使用规则
    private String rule="";
    
    //温馨提示
    private String cue="";
    
    private String imgUrl="";
    
    private String foodUrl="";
    
    private String redirectUrl="";
    
    public Article() {
    }

    public String getRedirectUrl() {
	   return redirectUrl;
	}
    
    public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl=redirectUrl;
	}
    
    
    public String getFoodUrl() {
		return foodUrl;
	}
    
    
    public void setFoodUrl(String foodUrl) {
		this.foodUrl=foodUrl;
	}
    
    public String getCampusId() {
		return campusId;
	}
    
    public void setCampusId(String campusId) {
		this.campusId=campusId;
	}
    
    public String getImgUrl() {
		return imgUrl;
	}
    
    public void setImgUrl(String imgUrl) {
		this.imgUrl=imgUrl;
	}
    
    public Float getPrice() {
		return price;
	}
    
    public void setPrice(Float price) {
		this.price=price;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCue(String cue) {
		this.cue=cue;
	}
    
    public String getCue() {
		return cue;
	}
    
    public void setUseTime(String usetime) {
		this.usetime=usetime;
	}
    
    public String getUseTime() {
		return usetime;
	}
    
    public void setBespoke(String bespoke) {
		this.bespoke=bespoke;
	}
    
    public String getBespoke() {
		return bespoke;
	}
    
    public void setRule(String rule) {
		this.rule=rule;
	}
    
    public String getRule() {
		return rule;
	}
    
    public void setStarttime(String starttime) {
		this.starttime=starttime;
	}
    
    public String getStarttime() {
		return starttime;
	}
    
    public void setEndtime(String endtime) {
		this.endtime=endtime;
	}
    
    public String getEndtime() {
		return endtime;
	}
    
    @Override
    public String toString() {
        return "Article [id=" + id + ", title=" + title + ", content=" + content + "]";
    }

	public String getCampusAdmin() {
		return campusAdmin;
	}

	public void setCampusAdmin(String campusAdmin) {
		this.campusAdmin = campusAdmin;
	}
}