package com.voucher.weixin.base;


import java.util.Date;

import com.alibaba.fastjson.JSONArray;

/**
* 类名: SNSUserInfo </br>
* 描述: 通过网页授权获取的用户信息 </br>
* 开发人员： souvc </br>
* 创建时间：  2015-11-27 </br>
* 发布版本：V1.0  </br>
 */
public class SNSUserInfo {
	//公众号ID
	private int campusId;
	//用户是否订阅该公众号标识
	private short subscribe;
    // 用户标识
    private String openId;
    // 用户昵称
    private String nickname;
    // 性别（1是男性，2是女性，0是未知）
    private short sex;
    //用户的语言
    private String language;
    // 国家
    private String country;
    // 省份
    private String province;
    // 城市
    private String city;
    // 用户头像链接
    private String headImgUrl;
    // 用户特权信息
    private JSONArray privilegeList;
    //用户关注时间，为时间戳
    private Date subscribeTime;
    //只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
    private String unionid;
    //公众号运营者对粉丝的备注
    private String remark;
    //用户所在的分组ID
    private String groupid;
    
    //error代码
    private String errorCode;
    
    public String getGroupId() {
		return groupid;
	}
    
    public void setGroupId(String groupid) {
		this.groupid=groupid;
	}
    
    public String getRemark() {
		return remark;
	}
    
    public void setRemark(String remark) {
		this.remark=remark;
	}
    
    
    public String getUnionid() {
		return unionid;
	}
    
    public void setUnionid(String unionid) {
		this.unionid=unionid;
	}
    
    
    public Date getSubScribeTime() {
		return subscribeTime;
	}
    
    public void setSubScribeTime(Date subscribeTime) {
		this.subscribeTime=subscribeTime;
	}
    
    public String getLanguage() {
		return language;
	}
    
    public void setLanguage(String language) {
		this.language=language;
	}
    
    
    public short getSubScribe() {
		return subscribe;
	}
    
    public void setSubScribe(short subscribe) {
		this.subscribe=subscribe;
	}
    
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getImgUrl() {         //用于写入数据库
		return headImgUrl;
	}
    
    public JSONArray getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(JSONArray jsonArray) {
        this.privilegeList = jsonArray;
    }
    
    public void setErrorCode(String errorCode) {
		this.errorCode=errorCode;
	}
    
    public String getErrorCode() {
		return errorCode;
	}

	public int getCampusId() {
		return campusId;
	}

	public void setCampusId(int campusId) {
		this.campusId = campusId;
	}
}