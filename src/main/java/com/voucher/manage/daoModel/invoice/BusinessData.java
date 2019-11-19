package com.voucher.manage.daoModel.invoice;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author admin
 *	蓝字发票必填项
 */
public class BusinessData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    //固定参数"API"(必填)
    
    private String data_resources="" ;
    
    //销售方纳税人识别号(必填)
    
    private String nsrsbh="" ;
    
    //业务单据号；必须是唯一的(必填)
    
    private String order_num="" ;
    
    //税收编码版本号，参数“13.0”，具体值请询问提供商(必填)
    
    private String bmb_bbh="" ;
    
    //征税方式 0：普通征税 1: 减按计增 2：差额征税(必填)
    
    private String zsfs="" ;
    
    //特殊票种标识:“00”=正常票种,“01”=农产品销售,“02”=农产品收购(非必填)
    
    private String tspz="" ;
    
    //销售方纳税人识别号(必填)
    
    private String xsf_nsrsbh="" ;
    
    //销售方名称(必填)
    
    private String xsf_mc="" ;
    
    //销售方地址、电话(必填)
    
    private String xsf_dzdh="" ;
    
    //销售方开户行名称与银行账号(非必填)
    
    private String xsf_yhzh="" ;
    
    //购买方纳税人识别号(税务总局规定企业用户为必填项)
    
    private String gmf_nsrsbh="" ;
    
    //购买方名称(必填)
    
    private String gmf_mc="" ;
    
    //购买方地址、电话(非必填)
    
    private String gmf_dzdh="" ;
    
    //购买方开户行名称与银行账号(非必填)
    
    private String gmf_yhzh="" ;
    
    //开票人(必填)
    
    private String kpr="" ;
    
    //收款人(非必填)
    
    private String skr="" ;
    
    //复核人(非必填)
    
    private String fhr="" ;
    
    //原发票代码(非必填)
    
    private String yfp_dm="" ;
    
    //原发票号码(非必填)
    
    private String yfp_hm="" ;
    
    //价税合计;单位：元（2位小数） 价税合计=合计金额(不含税)+合计税额 注意：不能使用商品的单价、数量、税率、税额来进行累加，最后四舍五入，只能是总合计金额+合计税额(必填)
    
    private String jshj="" ;
    
    //合计金额 注意：不含税，单位：元（2位小数）(必填)
    
    private String hjje="" ;
    
    //合计税额单位：元（2位小数）(必填)
    
    private String hjse="" ;
    
    //扣除额小数点后2位，当ZSFS为2时扣除额为必填项
    
    private String kce="" ;
    
    //备注 (长度100字符)(非必填)
    
    private String bz="" ;
    
    //开票终端标示(开票点代码,通常不用传；如需要按照开票点固定开票则传入此值)(非必填)
    
    private String kpzdbs="" ;
    
    //商品明细
    
    private List<Common_Fpkj_Xmxx> common_fpkj_xmxx ;

	public String getData_resources() {
		return data_resources;
	}

	public void setData_resources(String data_resources) {
		this.data_resources = data_resources;
	}

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}

	public String getBmb_bbh() {
		return bmb_bbh;
	}

	public void setBmb_bbh(String bmb_bbh) {
		this.bmb_bbh = bmb_bbh;
	}

	public String getZsfs() {
		return zsfs;
	}

	public void setZsfs(String zsfs) {
		this.zsfs = zsfs;
	}

	public String getTspz() {
		return tspz;
	}

	public void setTspz(String tspz) {
		this.tspz = tspz;
	}

	public String getXsf_nsrsbh() {
		return xsf_nsrsbh;
	}

	public void setXsf_nsrsbh(String xsf_nsrsbh) {
		this.xsf_nsrsbh = xsf_nsrsbh;
	}

	public String getXsf_mc() {
		return xsf_mc;
	}

	public void setXsf_mc(String xsf_mc) {
		this.xsf_mc = xsf_mc;
	}

	public String getXsf_dzdh() {
		return xsf_dzdh;
	}

	public void setXsf_dzdh(String xsf_dzdh) {
		this.xsf_dzdh = xsf_dzdh;
	}

	public String getXsf_yhzh() {
		return xsf_yhzh;
	}

	public void setXsf_yhzh(String xsf_yhzh) {
		this.xsf_yhzh = xsf_yhzh;
	}

	public String getGmf_nsrsbh() {
		return gmf_nsrsbh;
	}

	public void setGmf_nsrsbh(String gmf_nsrsbh) {
		this.gmf_nsrsbh = gmf_nsrsbh;
	}

	public String getGmf_mc() {
		return gmf_mc;
	}

	public void setGmf_mc(String gmf_mc) {
		this.gmf_mc = gmf_mc;
	}

	public String getGmf_dzdh() {
		return gmf_dzdh;
	}

	public void setGmf_dzdh(String gmf_dzdh) {
		this.gmf_dzdh = gmf_dzdh;
	}

	public String getGmf_yhzh() {
		return gmf_yhzh;
	}

	public void setGmf_yhzh(String gmf_yhzh) {
		this.gmf_yhzh = gmf_yhzh;
	}

	public String getKpr() {
		return kpr;
	}

	public void setKpr(String kpr) {
		this.kpr = kpr;
	}

	public String getSkr() {
		return skr;
	}

	public void setSkr(String skr) {
		this.skr = skr;
	}

	public String getFhr() {
		return fhr;
	}

	public void setFhr(String fhr) {
		this.fhr = fhr;
	}

	public String getYfp_dm() {
		return yfp_dm;
	}

	public void setYfp_dm(String yfp_dm) {
		this.yfp_dm = yfp_dm;
	}

	public String getYfp_hm() {
		return yfp_hm;
	}

	public void setYfp_hm(String yfp_hm) {
		this.yfp_hm = yfp_hm;
	}

	public String getJshj() {
		return jshj;
	}

	public void setJshj(String jshj) {
		this.jshj = jshj;
	}

	public String getHjje() {
		return hjje;
	}

	public void setHjje(String hjje) {
		this.hjje = hjje;
	}

	public String getHjse() {
		return hjse;
	}

	public void setHjse(String hjse) {
		this.hjse = hjse;
	}

	public String getKce() {
		return kce;
	}

	public void setKce(String kce) {
		this.kce = kce;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getKpzdbs() {
		return kpzdbs;
	}

	public void setKpzdbs(String kpzdbs) {
		this.kpzdbs = kpzdbs;
	}

	public List<Common_Fpkj_Xmxx> getCommon_fpkj_xmxx() {
		return common_fpkj_xmxx;
	}

	public void setCommon_fpkj_xmxx(List<Common_Fpkj_Xmxx> common_fpkj_xmxx) {
		this.common_fpkj_xmxx = common_fpkj_xmxx;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    
}
