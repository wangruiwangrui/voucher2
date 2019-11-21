package com.voucher.manage.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.xalan.xsltc.compiler.sym;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.voucher.manage.dao.BillDAO;
import com.voucher.manage.daoModel.TTT.Bill;
import com.voucher.manage.daoModel.TTT.BillServerInfo;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModel.TTT.CompanyMsg;
import com.voucher.manage.daoModel.TTT.Payment_Info;
import com.voucher.manage.daoModel.invoice.BillImg;
import com.voucher.manage.daoModel.invoice.BusinessData;
import com.voucher.manage.daoModel.invoice.BusinessResult;
import com.voucher.manage.daoModel.invoice.Common_Fpkj_Xmxx;
import com.voucher.manage.daoModel.invoice.QueryDataBase;
import com.voucher.manage.daoModel.invoice.QueryResult;
import com.voucher.manage.daoModel.invoice.QueryResultBase;
import com.voucher.manage.daoModel.invoice.RedBusinessData;
import com.voucher.manage.daoModel.invoice.RedBusinessResult;
import com.voucher.manage.file.ImageFileFactory;
import com.voucher.manage.service.WeiXinService;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.Base64Test;
import com.voucher.manage.tools.CopyFile;
import com.voucher.manage.tools.FileConvect;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;
import com.voucher.weixin.util.BillAccessTokenUtil;
import com.voucher.weixin.util.HttpUtils;
import com.voucher.weixin.util.OrderNum;

import cn.hutool.core.lang.UUID;

@Controller
@RequestMapping("/mobile/bill")
public class BillUController {

	ApplicationContext applicationContext = new Connect().get();
	BillDAO billDAO = (BillDAO) applicationContext.getBean("billDAO");

	@Autowired
	private WeiXinService weixinService;
	@Autowired
	private BillAccessTokenUtil billAcc;

	HttpUtils ut = new HttpUtils();

	/**
	 * 开蓝字发票链接
	 * 
	 * @return
	 */
	@RequestMapping("/getBill")
	@ResponseBody
	public BusinessResult getTicket(String out_trade_no, String purchaser, String ein, HttpServletRequest request,
			HttpServletResponse response) {
		
		Payment_Info pInfo = billDAO.selectPayment_Info(out_trade_no); // 通过交易单号查询交易详情
		BillServerInfo billServerinfo = billDAO.getBillInfo(out_trade_no);
		CompanyMsg cMsg = billDAO.queryCompanyMsg(out_trade_no); // 获取销售方纳税人信息

		Common_Fpkj_Xmxx common_fpkj_xmxx = new Common_Fpkj_Xmxx();
		common_fpkj_xmxx.setFphxz(billServerinfo.getFphxz());
		common_fpkj_xmxx.setSpbm(billServerinfo.getSpbm());
		common_fpkj_xmxx.setXmmc(billServerinfo.getXmmc());
		common_fpkj_xmxx.setXmdj(pInfo.getTotal_fee().toString());
		common_fpkj_xmxx.setXmsl("1.000000");
		common_fpkj_xmxx.setXmje(pInfo.getTotal_fee().toString());
		common_fpkj_xmxx.setSl(billServerinfo.getSl().toString());

		// 不含税单价=含税单价/1+税率; 税额=不含税单价* 数量 * 税率
		Float dj = (float) Float.parseFloat(common_fpkj_xmxx.getXmdj())
				/ (1 + Float.parseFloat(common_fpkj_xmxx.getSl()));
		Float se = dj * 1 * Float.parseFloat(common_fpkj_xmxx.getSl());

		BusinessData bData = new BusinessData();
		bData.setData_resources(billServerinfo.getData_Resources());
		bData.setNsrsbh(cMsg.getNsrsbh());
		bData.setOrder_num(pInfo.getOut_trade_no());
		bData.setBmb_bbh(billServerinfo.getBmb_BBH());
		bData.setZsfs(billServerinfo.getZsfs());
		bData.setXsf_nsrsbh(cMsg.getNsrsbh());
		bData.setXsf_mc(cMsg.getCompanyName());
		bData.setXsf_dzdh(cMsg.getDzdh());
		bData.setXsf_yhzh(cMsg.getYhzh());

		if (ein != null) {
			bData.setGmf_nsrsbh(ein);
		}
		bData.setGmf_mc(purchaser);
		bData.setKpr(cMsg.getOperator());

		DecimalFormat fnum = new DecimalFormat("##0.00");

		bData.setHjje(fnum.format(dj));
		bData.setHjse(fnum.format(se));
		bData.setJshj(fnum.format(dj + se));

		String dd = fnum.format(se);
		common_fpkj_xmxx.setSe(dd.toString());

		List common_fpkj_xmxx1 = new ArrayList();
		common_fpkj_xmxx1.add(common_fpkj_xmxx);
		bData.setCommon_fpkj_xmxx(common_fpkj_xmxx1);

		JSONObject param = new JSONObject();
		param.put("access_token", billServerinfo.getTokenStr());
		param.put("serviceKey", "ebi_InvoiceHandle_newBlueInvoice");
		param.put("data", bData);

		String url = billServerinfo.getAPI_BUSS_URL();
		String returnString = ut.doPost2(url, param.toString());

		JSONObject jsonObject = JSONObject.parseObject(returnString);
		BusinessResult result = JSON.toJavaObject(jsonObject, BusinessResult.class);
		System.out.println("---------------");
		MyTestUtil.print(common_fpkj_xmxx);
		MyTestUtil.print(result);
		if (result.getResult().equals("SUCCESS")) {

			Integer re = billDAO.InsertBillFirst(result,billServerinfo.getCampusId());
		}

		return result;
	}
	
	/**
	 * 重开开蓝字发票链接
	 * 
	 * @return
	 */
	@RequestMapping("/getNewBill")
	@ResponseBody
	public Map getNewBill(String out_trade_noRed, String purchaser, String ein, HttpServletRequest request,
			HttpServletResponse response) {
		String out_trade_no = out_trade_noRed.substring(0,17);
		Payment_Info pInfo = billDAO.selectPayment_Info(out_trade_no); // 通过交易单号查询交易详情
		BillServerInfo billServerinfo = billDAO.getBillInfo(out_trade_no);
		CompanyMsg cMsg = billDAO.queryCompanyMsg(out_trade_no); // 获取销售方纳税人信息

		Common_Fpkj_Xmxx common_fpkj_xmxx = new Common_Fpkj_Xmxx();
		common_fpkj_xmxx.setFphxz(billServerinfo.getFphxz());
		common_fpkj_xmxx.setSpbm(billServerinfo.getSpbm());
		common_fpkj_xmxx.setXmmc(billServerinfo.getXmmc());
		common_fpkj_xmxx.setXmdj(pInfo.getTotal_fee().toString());
		common_fpkj_xmxx.setXmsl("1.000000");
		common_fpkj_xmxx.setXmje(pInfo.getTotal_fee().toString());
		common_fpkj_xmxx.setSl(billServerinfo.getSl().toString());

		// 不含税单价=含税单价/1+税率; 税额=不含税单价* 数量 * 税率
		Float dj = (float) Float.parseFloat(common_fpkj_xmxx.getXmdj())
				/ (1 + Float.parseFloat(common_fpkj_xmxx.getSl()));
		Float se = dj * 1 * Float.parseFloat(common_fpkj_xmxx.getSl());

		BusinessData bData = new BusinessData();
		bData.setData_resources(billServerinfo.getData_Resources());
		bData.setNsrsbh(cMsg.getNsrsbh());
		
		//生成新订单号
		int i = (int)(Math.random()*900 + 100);
		String myStr = Integer.toString(i);
		bData.setOrder_num(out_trade_no+myStr);
		bData.setBmb_bbh(billServerinfo.getBmb_BBH());
		bData.setZsfs(billServerinfo.getZsfs());
		bData.setXsf_nsrsbh(cMsg.getNsrsbh());
		bData.setXsf_mc(cMsg.getCompanyName());
		bData.setXsf_dzdh(cMsg.getDzdh());
		bData.setXsf_yhzh(cMsg.getYhzh());

		if (ein != null) {
			bData.setGmf_nsrsbh(ein);
		}
		bData.setGmf_mc(purchaser);
		bData.setKpr(cMsg.getOperator());

		DecimalFormat fnum = new DecimalFormat("##0.00");

		bData.setHjje(fnum.format(dj));
		bData.setHjse(fnum.format(se));
		bData.setJshj(fnum.format(dj + se));

		String dd = fnum.format(se);
		common_fpkj_xmxx.setSe(dd.toString());

		List common_fpkj_xmxx1 = new ArrayList();
		common_fpkj_xmxx1.add(common_fpkj_xmxx);
		bData.setCommon_fpkj_xmxx(common_fpkj_xmxx1);

		JSONObject param = new JSONObject();
		param.put("access_token", billServerinfo.getTokenStr());
		param.put("serviceKey", "ebi_InvoiceHandle_newBlueInvoice");
		param.put("data", bData);

		String url = billServerinfo.getAPI_BUSS_URL();
		String returnString = ut.doPost2(url, param.toString());

		JSONObject jsonObject = JSONObject.parseObject(returnString);
		BusinessResult result = JSON.toJavaObject(jsonObject, BusinessResult.class);
		System.out.println("---------------");
		MyTestUtil.print(result);
		Map map = new HashMap(); 
		if (result.getResult().equals("SUCCESS")) {

			Integer re = billDAO.InsertBill(result,billServerinfo.getCampusId(),out_trade_no);
			if(re>0) {
				Integer res = billDAO.updateRedBill(out_trade_noRed);
				map.put("result", "SUCCESS");
				return map;
			}
			map.put("result", "false1");
			return map;
		}else {
			map.put("result", "false");
			return map;
		}
	}

	/**
	 * 开红字发票链接
	 * 
	 * @return
	 */
	@RequestMapping("/getRedTicket")
	@ResponseBody
	public Map getRedTicket(String out_trade_no, HttpServletRequest request, HttpServletResponse response) {
		
		String out_trade_no_new = out_trade_no.substring(0,17);
		Bill bill = new Bill();
		bill.setOrder_num(out_trade_no);

		bill = billDAO.queryBillByOrderNum(bill);
		BillServerInfo billServerinfo = billDAO.getBillInfo(out_trade_no_new);
		CompanyMsg cMsg = billDAO.queryCompanyMsg(out_trade_no_new); // 获取销售方纳税人信息

		RedBusinessData data = new RedBusinessData();
		data.setData_resources(billServerinfo.getData_Resources());
		
		int i = (int)(Math.random()*900 + 100);
		String myStr = Integer.toString(i);
		data.setOrder_num(out_trade_no_new + myStr);
		data.setNsrsbh(cMsg.getNsrsbh());
		data.setYfp_dm(bill.getFp_dm());
		data.setYfp_hm(bill.getFp_hm());

		JSONObject param = new JSONObject();
		param.put("access_token", billServerinfo.getTokenStr());
		param.put("serviceKey", "ebi_InvoiceHandle_newRedInvoice");
		param.put("data", data);

		String url = billServerinfo.getAPI_BUSS_URL();
		String returnString = ut.doPost2(url, param.toString());

		JSONObject jsonObject = JSONObject.parseObject(returnString);
		RedBusinessResult result = JSON.toJavaObject(jsonObject, RedBusinessResult.class);
		Map map = new HashMap();
		if (result.getResult().equals("SUCCESS")) {

			Integer re = billDAO.InsertRedBill(result,bill.getCampusId());
			if (re > 0) {
				Integer res = billDAO.updateBill(bill.getOrder_num());
				map.put("result", "SUCCESS");
				return map;
			}
			map.put("result", "false1");
			return map;
		}else {
			map.put("result", "false");
			return map;
		}
	}

	@RequestMapping("/getBillPDF")
	@ResponseBody
	public Map getBillPDF(String out_trade_no, HttpServletRequest request, HttpServletResponse response) {

		BillServerInfo billServerinfo = billDAO.getBillInfo(out_trade_no);

		Bill bill = new Bill();
		bill.setOrder_num(out_trade_no);

		bill = billDAO.queryBillByOrderNum(bill);

		QueryDataBase data = new QueryDataBase();
		data.setFileKey(bill.getPdf_key());

		JSONObject param = new JSONObject();
		param.put("access_token", billServerinfo.getTokenStr());
		param.put("serviceKey", "ep_pdf_getPdfImgByte");
		param.put("data", data);

		String url = billServerinfo.getAPI_BUSS_URL();
		String returnString = ut.doPost2(url, param.toString());

		JSONObject jsonObject = JSONObject.parseObject(returnString);
		QueryResult result = JSON.toJavaObject(jsonObject, QueryResult.class);
		QueryResultBase urlBase = result.getRows();
		String nameStr = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
		String name = Singleton.ROOMINFOIMGPATH + nameStr;
		
		boolean urlstr = Base64Test.generateImage(urlBase.getUrl(), name);
		
		Map<String,String> map=new HashMap<>();
		if (urlstr) {
			
			String imgPath=request.getSession().getServletContext().getRealPath(Singleton.filePath);
			String oldFile=Singleton.ROOMINFOIMGPATH+"\\"+nameStr;
			
			CopyFile.set(imgPath, oldFile, nameStr);
		
			map.put("result", Singleton.filePath+"\\"+nameStr);
			return map;
			
		}else {
			map.put("result", "false");
			return map;
		}

	}
	
	// 获取原始PDF文件
	@ResponseBody
	@RequestMapping("getBillOriginalPDF")
	public Map getBillOriginalPDF(String out_trade_no, HttpServletRequest request, HttpServletResponse response) {
		
		String out_trade_no_new = out_trade_no.substring(0,17);
		
		BillServerInfo billServerinfo = billDAO.getBillInfo(out_trade_no_new);

		Bill bill = new Bill();
		bill.setOrder_num(out_trade_no);

		bill = billDAO.queryBillByOrderNum(bill);

		QueryDataBase data = new QueryDataBase();
		data.setFileKey(bill.getPdf_key());

		JSONObject param = new JSONObject();
		param.put("access_token", billServerinfo.getTokenStr());
		param.put("serviceKey", "ep_pdf_getPdfByte");
		param.put("data", data);

		String url = billServerinfo.getAPI_BUSS_URL();
		String returnString = ut.doPost2(url, param.toString());

		JSONObject jsonObject = JSONObject.parseObject(returnString);
		QueryResult result = JSON.toJavaObject(jsonObject, QueryResult.class);
		QueryResultBase urlBase = result.getRows();
		String nameStr = UUID.randomUUID().toString().replaceAll("-", "") + ".pdf";
		String name = Singleton.ROOMINFOIMGPATH + nameStr;
		boolean urlstr = Base64Test.generateImage(urlBase.getUrl(), name);
		Map<String,String> map=new HashMap<>();
		if (urlstr) {
			String imgPath=request.getSession().getServletContext().getRealPath(Singleton.filePath);
			String oldFile=Singleton.ROOMINFOIMGPATH+"\\"+nameStr;
			CopyFile.set(imgPath, oldFile, nameStr);
		
			Integer res = billDAO.insertBillImage(out_trade_no,nameStr );
			
			if (res > 0) {
				map.put("result", Singleton.filePath+"\\"+nameStr);
				return map;
			}else {
				map.put("result", "false");
				return map;
			}
		}else {
			map.put("result", "false");
			return map;
		}
	}

}
