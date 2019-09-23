package com.voucher.manage.daoModel.invoice;

import java.io.Serializable;

/**
 * 
 * @author admin
 *	蓝字发票商品详情
 */
public class Common_Fpkj_Xmxx implements Serializable {

	private static final long serialVersionUID = 1L;

    // 发票行性质 0正常行、1折扣行、2被折扣行(必填)
    
	public String fphxz ;
    
    // 商品编码(商品编码为税务总局颁发的19位税控编码)(非必填)
    
    public String spbm ;
    
    // 自行编码(一般不建议使用自行编码)(非必填)
    
    public String zxbm ;
    
    // 优惠政策标识 0：不使用，1：使用(非必填)
    
    public String yhzcbs ;
    
    // 零税率标识 空：非零税率， 1：免税，2：不征收，3普通零税率(非必填)
    
    public String lslbs ;
    
    // 增值税特殊管理-如果yhzcbs为1时，此项必填，具体信息取《商品和服务税收分类与编码》中的增值税特殊管理列。(值为中文)(必填)
    
    public String zzstsgl ;
    
    // 项目名称 (必须与商品编码表一致;如果为折扣行，商品名称须与被折扣行的商品名称相同，不能多行折扣。如需按照税控编码开票，则项目名称可以自拟,但请按照税务总局税控编码规则拟定)(必填)
    
    public String xmmc ;
    
    // 规格型号(折扣行请不要传)(非必填)
    
    public String ggxh ;
    
    // 计量单位(折扣行请不要传)(非必填)
    
    public String dw ;
    
    // 项目数量 小数点后6位,大于0的数字(非必填)
    
    public String xmsl ;
    
    // 项目单价 小数点后6位 注意：单价是含税单价,大于0的数字(非必填)
    
    public String xmdj ;
    
    // 项目金额 注意：金额是含税，单位：元（2位小数）(必填)
    
    public String xmje ;
    
    // 税率 例1%为0.01(必填)
    
    public String sl ;
    
    // 税额 单位：元（2位小数）(必填)
    
    public String se ;

	public String getFphxz() {
		return fphxz;
	}

	public void setFphxz(String fphxz) {
		this.fphxz = fphxz;
	}

	public String getSpbm() {
		return spbm;
	}

	public void setSpbm(String spbm) {
		this.spbm = spbm;
	}

	public String getZxbm() {
		return zxbm;
	}

	public void setZxbm(String zxbm) {
		this.zxbm = zxbm;
	}

	public String getYhzcbs() {
		return yhzcbs;
	}

	public void setYhzcbs(String yhzcbs) {
		this.yhzcbs = yhzcbs;
	}

	public String getLslbs() {
		return lslbs;
	}

	public void setLslbs(String lslbs) {
		this.lslbs = lslbs;
	}

	public String getZzstsgl() {
		return zzstsgl;
	}

	public void setZzstsgl(String zzstsgl) {
		this.zzstsgl = zzstsgl;
	}

	public String getXmmc() {
		return xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public String getGgxh() {
		return ggxh;
	}

	public void setGgxh(String ggxh) {
		this.ggxh = ggxh;
	}

	public String getDw() {
		return dw;
	}

	public void setDw(String dw) {
		this.dw = dw;
	}

	public String getXmsl() {
		return xmsl;
	}

	public void setXmsl(String xmsl) {
		this.xmsl = xmsl;
	}

	public String getXmdj() {
		return xmdj;
	}

	public void setXmdj(String xmdj) {
		this.xmdj = xmdj;
	}

	public String getXmje() {
		return xmje;
	}

	public void setXmje(String xmje) {
		this.xmje = xmje;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getSe() {
		return se;
	}

	public void setSe(String se) {
		this.se = se;
	}
    
}
