package com.voucher.manage.daoModel.invoice;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author admin
 *	蓝字发票返回项
 */
public class BusinessRows implements Serializable{

	private static final long serialVersionUID = 1L;
	

    // 复核人				
	private String fhr ;
    
    // 销售方名称
    private String xsf_mc ;
    
    // 原发票号码
    private String yfp_hm ;
    
    // 备注
    private String bz ;
    
    // 销售方银行账号
    private String xsf_yhzh ;
    
    // 购买方名称	
    private String gmf_mc ;
    
    // 合计税额
    private String hjse ;
    
    // 发票代码
    private String fp_dm ;
    
    // 扣税额
    private String kce ;
    
    // 原发票代码
    private String yfp_dm ;
    
    // 发票号码
    private String fp_hm ;
    
    // 购买方纳税人识别号
    private String gmf_nsrsbh ;
    
    // 发票类型（026=电票，004=专票，007=普票，025=卷票）
    private String itype ;
    
    // 校验码
    private String jym ;
    
    // 开票类型
    private String kplx ;
    
    // 发票清单PDF文件KEY
    private String pdf_item_key ;
    
    // 业务单据号
    private String order_num ;
    
    // 征税方式（0：普通征税   2：差额征税）
    private String zsfs ;
    
    // 销售方地址、电话
    private String xsf_dzdh ;
    
    // 税控设备机器编号
    private String jqbh ;
    
    // 合计金额（不含税）
    private String hjje ;
    
    // 购买方地址、电话
    private String gmf_dzdh ;
    
    // 发票请求流水号
    private String fpqqlsh ;
    
    // 收款人
    private String skr ;
    
    // 购买方银行账号
    private String gmf_yhzh ;
    
    // 开票人
    private String kpr ;
    
    // 销售方纳税人识别号
    private String xsf_nsrsbh ;
    
    // 发票密文
    private String fp_mw ;
    
    // 价税合计
    private String jshj ;
    
    // 发票PDF文件获取KEY
    private String pdf_key ;
    
    // 开票日期（20161107145525  格式yyyymmddhhmiss)
    private String kprq ;
    
    // 提取码
    private String EXT_CODE ;
    
    // 商品明细
    private List<Common_Fpkj_Xmxx> common_fpkj_xmxx;

	public String getFhr() {
		return fhr;
	}

	public void setFhr(String fhr) {
		this.fhr = fhr;
	}

	public String getXsf_mc() {
		return xsf_mc;
	}

	public void setXsf_mc(String xsf_mc) {
		this.xsf_mc = xsf_mc;
	}

	public String getYfp_hm() {
		return yfp_hm;
	}

	public void setYfp_hm(String yfp_hm) {
		this.yfp_hm = yfp_hm;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getXsf_yhzh() {
		return xsf_yhzh;
	}

	public void setXsf_yhzh(String xsf_yhzh) {
		this.xsf_yhzh = xsf_yhzh;
	}

	public String getGmf_mc() {
		return gmf_mc;
	}

	public void setGmf_mc(String gmf_mc) {
		this.gmf_mc = gmf_mc;
	}

	public String getHjse() {
		return hjse;
	}

	public void setHjse(String hjse) {
		this.hjse = hjse;
	}

	public String getFp_dm() {
		return fp_dm;
	}

	public void setFp_dm(String fp_dm) {
		this.fp_dm = fp_dm;
	}

	public String getKce() {
		return kce;
	}

	public void setKce(String kce) {
		this.kce = kce;
	}

	public String getYfp_dm() {
		return yfp_dm;
	}

	public void setYfp_dm(String yfp_dm) {
		this.yfp_dm = yfp_dm;
	}

	public String getFp_hm() {
		return fp_hm;
	}

	public void setFp_hm(String fp_hm) {
		this.fp_hm = fp_hm;
	}

	public String getGmf_nsrsbh() {
		return gmf_nsrsbh;
	}

	public void setGmf_nsrsbh(String gmf_nsrsbh) {
		this.gmf_nsrsbh = gmf_nsrsbh;
	}

	public String getItype() {
		return itype;
	}

	public void setItype(String itype) {
		this.itype = itype;
	}

	public String getJym() {
		return jym;
	}

	public void setJym(String jym) {
		this.jym = jym;
	}

	public String getKplx() {
		return kplx;
	}

	public void setKplx(String kplx) {
		this.kplx = kplx;
	}

	public String getPdf_item_key() {
		return pdf_item_key;
	}

	public void setPdf_item_key(String pdf_item_key) {
		this.pdf_item_key = pdf_item_key;
	}

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}

	public String getZsfs() {
		return zsfs;
	}

	public void setZsfs(String zsfs) {
		this.zsfs = zsfs;
	}

	public String getXsf_dzdh() {
		return xsf_dzdh;
	}

	public void setXsf_dzdh(String xsf_dzdh) {
		this.xsf_dzdh = xsf_dzdh;
	}

	public String getJqbh() {
		return jqbh;
	}

	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}

	public String getHjje() {
		return hjje;
	}

	public void setHjje(String hjje) {
		this.hjje = hjje;
	}

	public String getGmf_dzdh() {
		return gmf_dzdh;
	}

	public void setGmf_dzdh(String gmf_dzdh) {
		this.gmf_dzdh = gmf_dzdh;
	}

	public String getFpqqlsh() {
		return fpqqlsh;
	}

	public void setFpqqlsh(String fpqqlsh) {
		this.fpqqlsh = fpqqlsh;
	}

	public String getSkr() {
		return skr;
	}

	public void setSkr(String skr) {
		this.skr = skr;
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

	public String getXsf_nsrsbh() {
		return xsf_nsrsbh;
	}

	public void setXsf_nsrsbh(String xsf_nsrsbh) {
		this.xsf_nsrsbh = xsf_nsrsbh;
	}

	public String getFp_mw() {
		return fp_mw;
	}

	public void setFp_mw(String fp_mw) {
		this.fp_mw = fp_mw;
	}

	public String getJshj() {
		return jshj;
	}

	public void setJshj(String jshj) {
		this.jshj = jshj;
	}

	public String getPdf_key() {
		return pdf_key;
	}

	public void setPdf_key(String pdf_key) {
		this.pdf_key = pdf_key;
	}

	public String getKprq() {
		return kprq;
	}

	public void setKprq(String kprq) {
		this.kprq = kprq;
	}

	public String getEXT_CODE() {
		return EXT_CODE;
	}

	public void setEXT_CODE(String eXT_CODE) {
		EXT_CODE = eXT_CODE;
	}

	public List<Common_Fpkj_Xmxx> getCommon_fpkj_xmxx() {
		return common_fpkj_xmxx;
	}

	public void setCommon_fpkj_xmxx(List<Common_Fpkj_Xmxx> common_fpkj_xmxx) {
		this.common_fpkj_xmxx = common_fpkj_xmxx;
	}
    
}
