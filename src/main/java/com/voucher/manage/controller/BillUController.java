package com.voucher.manage.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.voucher.manage.dao.BillDAO;
import com.voucher.manage.daoModel.TTT.Bill;
import com.voucher.manage.daoModel.TTT.BillServerInfo;
import com.voucher.manage.daoModel.TTT.CompanyMsg;
import com.voucher.manage.daoModel.TTT.Payment_Info;
import com.voucher.manage.daoModel.TTT.PreBill;
import com.voucher.manage.daoModel.invoice.BusinessData;
import com.voucher.manage.daoModel.invoice.BusinessResult;
import com.voucher.manage.daoModel.invoice.Common_Fpkj_Xmxx;
import com.voucher.manage.daoModel.invoice.QueryDataBase;
import com.voucher.manage.daoModel.invoice.QueryResult;
import com.voucher.manage.daoModel.invoice.QueryResultBase;
import com.voucher.manage.daoModel.invoice.RedBusinessData;
import com.voucher.manage.daoModel.invoice.RedBusinessResult;
import com.voucher.manage.mapper.WeiXinMapper;
import com.voucher.manage.model.Notice;
import com.voucher.manage.model.WeiXin;
import com.voucher.manage.service.NoticeService;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.Base64Test;
import com.voucher.manage.tools.CopyFile;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;
import com.voucher.weixin.controller.WechatSendMessageController;
import com.voucher.weixin.util.HttpUtils;

import cn.hutool.core.lang.UUID;

@Controller
@RequestMapping("/mobile/bill")
public class BillUController {

	ApplicationContext applicationContext = new Connect().get();
	BillDAO billDAO = (BillDAO) applicationContext.getBean("billDAO");

	@Autowired
	private WeiXinMapper weixinMapper;
	
	@Autowired
	private NoticeService noticeService;
	HttpUtils ut = new HttpUtils();

	@Value("${DOMAIN}")
	private String DOMAIN;

	@RequestMapping("/getBillPDF")
	@ResponseBody
	public Map getBillPDF(String out_trade_no, HttpServletRequest request, HttpServletResponse response) {

		Bill bill = new Bill();
		bill.setOrder_num(out_trade_no);

		bill = billDAO.queryBillByOrderNum(bill);
		BillServerInfo billServerinfo = billDAO.getBillInfo(out_trade_no,bill.getSl());

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

		Map<String, String> map = new HashMap<>();
		if (urlstr) {

			String imgPath = request.getSession().getServletContext().getRealPath(Singleton.filePath);
			String oldFile = Singleton.ROOMINFOIMGPATH + "\\" + nameStr;

			CopyFile.set(imgPath, oldFile, nameStr);

			map.put("result", Singleton.filePath + "\\" + nameStr);
			return map;

		} else {
			map.put("result", "false");
			return map;
		}

	}

	// 获取原始PDF文件
	@ResponseBody
	@RequestMapping("getBillOriginalPDF")
	public Map getBillOriginalPDF(String out_trade_no, HttpServletRequest request, HttpServletResponse response) {

		String out_trade_no_new = out_trade_no.substring(0, 17);
		Payment_Info openId = billDAO.getPaymenInfo(out_trade_no_new);

		Bill bill = new Bill();
		bill.setOrder_num(out_trade_no);

		bill = billDAO.queryBillByOrderNum(bill);
		BillServerInfo billServerinfo = billDAO.getBillInfo(out_trade_no_new,bill.getSl());
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
		Map<String, String> map = new HashMap<>();
		if (urlstr) {
			String imgPath = request.getSession().getServletContext().getRealPath(Singleton.filePath);
			String oldFile = Singleton.ROOMINFOIMGPATH + File.separator + nameStr;
			CopyFile.set(imgPath, oldFile, nameStr);

			Integer res = billDAO.insertBillImage(out_trade_no, nameStr);

			if (res > 0) {
				map.put("result", Singleton.filePath + File.separator + nameStr);
				return map;
			} else {
				map.put("result", "false");
				return map;
			}
		} else {
			map.put("result", "false");
			return map;
		}
	}

	/**
	 * 开蓝字发票链接
	 * 
	 * @return
	 */
	@RequestMapping("/getNBill")
	@ResponseBody
	public BusinessResult getBill(String out_trade_no, String purchaser, String ein, String sl,HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("------------------------------------");
		System.out.println("out_trade_no="+out_trade_no+"              "+"purchaser="+purchaser+"               "+"ein="+ein+"           sl="+sl);
		WechatSendMessageController wechatSendMessageController = new WechatSendMessageController();

		Payment_Info pInfo = billDAO.selectPayment_Info(out_trade_no); // 通过交易单号查询交易详情
		BillServerInfo billServerinfo = billDAO.getBillInfo(out_trade_no,sl);//查询发票信息
		CompanyMsg cMsg = billDAO.queryCompanyMsg(out_trade_no); // 获取销售方纳税人信息

		//商品详情
		Common_Fpkj_Xmxx common_fpkj_xmxx = new Common_Fpkj_Xmxx();
		common_fpkj_xmxx.setFphxz(billServerinfo.getFphxz());
		common_fpkj_xmxx.setSpbm(billServerinfo.getSpbm());
		common_fpkj_xmxx.setXmmc(billServerinfo.getXmmc());
		common_fpkj_xmxx.setXmdj(pInfo.getTotal_fee().toString());
		common_fpkj_xmxx.setXmsl("1.000000");
		common_fpkj_xmxx.setXmje(pInfo.getTotal_fee().toString());
		
		/**
		 * 通过合同编号获取信息查询税率
		 */
		common_fpkj_xmxx.setSl(sl);
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
		String returnString = null;
		BusinessResult result = new BusinessResult();

		// 添加预开票信息
		Integer res = billDAO.InserIntoBill(bData, billServerinfo.getCampusId());

		int count = 0;
		for (int i = 0; i < 4; i++) {
			returnString = ut.doPost2(url, param.toString());
			JSONObject jsonObject = JSONObject.parseObject(returnString);
			result = JSON.toJavaObject(jsonObject, BusinessResult.class);
			System.out.println("---------------");
			MyTestUtil.print(common_fpkj_xmxx);
			MyTestUtil.print(result);
			if (result.getResult().equals("SUCCESS")) {
				// 设置开票状态
				Integer re = billDAO.updatePreBill(out_trade_no, result);
				Map map = getBillOriginalPDF(out_trade_no, request, response);
				char a = DOMAIN.charAt(4);
				String imgUrl = "";
				if (a==':') {
					imgUrl = DOMAIN.substring(7) + "/voucher/" + (String) map.get("result");
				}else {
					imgUrl = DOMAIN.substring(8) + "/voucher/" + (String) map.get("result");
				}
				imgUrl = imgUrl.replaceAll("\\\\", "/");
				
				System.out.println("----------------------------------------------");
				System.out.println((String) map.get("result"));
				System.out.println(imgUrl);
				Notice notice = new Notice();
				notice.setTitle("电子发票开具通知");
				notice.setCampusId(pInfo.getCampusId());

				notice = noticeService.getTemplateIdByTitle(notice);

				Date data = pInfo.getCreateTime();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dat = formatter.format(data);

				WeiXin weiXin = weixinMapper.getCampus(billServerinfo.getCampusId());
				String campany = weiXin.getCampusName();
				wechatSendMessageController.sendMessage(pInfo.getOpenid(), notice.getTemplateId(), "您的电子发票已开具成功",
						imgUrl, "您的电子发票已开具成功", campany, dat, result.getRows().get(0).getFp_dm(),
						result.getRows().get(0).getGmf_mc(), result.getRows().get(0).getJshj() + "元", "依法纳税，你我有责。");
				break;
			} else {

				if (count == 3) {
					// 开票失败添加错误原因
					Integer re = billDAO.updatePreBillMsg(out_trade_no, result);

					break;
				}

				count = count + 1;
			}

		}
		return result;
	}

	/**
	 * 重开蓝字发票链接
	 * 
	 * @return
	 */
	@RequestMapping("/getRetNewBill")
	@ResponseBody
	public BusinessResult getRetNewBill(String out_trade_noRed, String purchaser, String ein,
			HttpServletRequest request, HttpServletResponse response) {
		WechatSendMessageController wechatSendMessageController = new WechatSendMessageController();
		String out_trade_no = out_trade_noRed.substring(0, 17);
		Bill bill = billDAO.selectBillByOrderNum(out_trade_noRed);

		Payment_Info pInfo = billDAO.selectPayment_Info(out_trade_no); // 通过交易单号查询交易详情
		BillServerInfo billServerinfo = billDAO.getBillInfo(out_trade_no,bill.getSl());

		Common_Fpkj_Xmxx common_fpkj_xmxx = new Common_Fpkj_Xmxx();
		common_fpkj_xmxx.setFphxz(bill.getFphxz());
		common_fpkj_xmxx.setSpbm(bill.getSpbm());
		common_fpkj_xmxx.setXmmc(bill.getXmmc());
		common_fpkj_xmxx.setXmdj(pInfo.getTotal_fee().toString());
		common_fpkj_xmxx.setXmsl("1.000000");
		common_fpkj_xmxx.setXmje(pInfo.getTotal_fee().toString());
		common_fpkj_xmxx.setSl(bill.getSl());

		// 不含税单价=含税单价/1+税率; 税额=不含税单价* 数量 * 税率
		Float dj = (float) Float.parseFloat(common_fpkj_xmxx.getXmdj())
				/ (1 + Float.parseFloat(common_fpkj_xmxx.getSl()));
		Float se = dj * 1 * Float.parseFloat(common_fpkj_xmxx.getSl());

		BusinessData bData = new BusinessData();
		bData.setData_resources(bill.getData_resources());
		bData.setNsrsbh(bill.getNsrsbh());

		// 生成新订单号
		int i = (int) (Math.random() * 900 + 100);
		String myStr = Integer.toString(i);
		String out_trade_no_new = out_trade_no + myStr;
		bData.setOrder_num(out_trade_no_new);
		bData.setBmb_bbh(bill.getBmb_bbh());
		bData.setZsfs(bill.getZsfs());
		bData.setXsf_nsrsbh(bill.getXsf_nsrsbh());
		bData.setXsf_mc(bill.getXsf_mc());
		bData.setXsf_dzdh(bill.getXsf_dzdh());
		bData.setXsf_yhzh(bill.getXsf_yhzh());

		if (ein != null) {
			bData.setGmf_nsrsbh(ein);
		}
		bData.setGmf_mc(purchaser);
		bData.setKpr(bill.getKpr());

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
		String returnString = null;
		BusinessResult result = new BusinessResult();
		int count = 0;
		for (int ii = 0; ii < 4; ii++) {
			returnString = ut.doPost2(url, param.toString());
			JSONObject jsonObject = JSONObject.parseObject(returnString);
			result = JSON.toJavaObject(jsonObject, BusinessResult.class);
			System.out.println("---------------");
			MyTestUtil.print(result);
			if (result.getResult().equals("SUCCESS")) {
				// 修改发票信息
				Integer re = billDAO.updateToBlue(result, out_trade_noRed);

				Map map = getBillOriginalPDF(out_trade_no_new, request, response);
				char a = DOMAIN.charAt(4);
				String imgUrl = "";
				if (a==':') {
					imgUrl = DOMAIN.substring(7) + "/voucher/" + (String) map.get("result");
				}else {
					imgUrl = DOMAIN.substring(8) + "/voucher/" + (String) map.get("result");
				}

				imgUrl = imgUrl.replaceAll("\\\\", "/");

				//获取短信模板
				Notice notice = new Notice();
				notice.setTitle("电子发票开具通知");
				notice.setCampusId(pInfo.getCampusId());
				notice = noticeService.getTemplateIdByTitle(notice);

				Date data = pInfo.getCreateTime();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dat = formatter.format(data);

				WeiXin weiXin = weixinMapper.getCampus(billServerinfo.getCampusId());
				String campany = weiXin.getCampusName();
				
				//推送消息
				wechatSendMessageController.sendMessage(pInfo.getOpenid(), notice.getTemplateId(), "您的电子发票已开具成功",
						imgUrl, "您的电子发票已开具成功", campany, dat, result.getRows().get(0).getFp_dm(),
						result.getRows().get(0).getGmf_mc(), result.getRows().get(0).getJshj() + "元", "依法纳税，你我有责。");
				
				break;
			} else {

				if (count == 3) {
					// 开票失败添加错误原因
					Integer re = billDAO.updateBillMsg(out_trade_noRed, result);

					break;
				}

				count = count + 1;
			}

		}
		return result;
	}

	/**
	 * 开红字发票链接
	 * 
	 * @return
	 */
	@RequestMapping("/getNRed")
	@ResponseBody
	public RedBusinessResult getNRed(String out_trade_no, HttpServletRequest request, HttpServletResponse response) {

		String out_trade_no_new = out_trade_no.substring(0, 17);

		Bill bill = billDAO.selectBillByOrderNum(out_trade_no);
		BillServerInfo billServerinfo = billDAO.getBillInfo(out_trade_no_new,bill.getSl());

		RedBusinessData data = new RedBusinessData();
		data.setData_resources(billServerinfo.getData_Resources());

		int i = (int) (Math.random() * 900 + 100);
		String myStr = Integer.toString(i);
		data.setOrder_num(out_trade_no_new + myStr);
		data.setNsrsbh(bill.getNsrsbh());
		data.setYfp_dm(bill.getFp_dm());
		data.setYfp_hm(bill.getFp_hm());

		JSONObject param = new JSONObject();
		param.put("access_token", billServerinfo.getTokenStr());
		param.put("serviceKey", "ebi_InvoiceHandle_newRedInvoice");
		param.put("data", data);

		String url = billServerinfo.getAPI_BUSS_URL();
		String returnString = null;
		RedBusinessResult result = new RedBusinessResult();
		int count = 0;
		for (int ii = 0; ii < 4; ii++) {
			returnString = ut.doPost2(url, param.toString());
			JSONObject jsonObject = JSONObject.parseObject(returnString);
			result = JSON.toJavaObject(jsonObject, RedBusinessResult.class);
			System.out.println("---------------");
			MyTestUtil.print(result);
			if (result.getResult().equals("SUCCESS")) {

				Integer re = billDAO.updateToRed(result, out_trade_no);

				break;
			} else {

				if (count == 3) {
					// 开票失败添加错误原因
					Integer re = billDAO.updateRedBillMsg(out_trade_no, result);

					break;
				}

				count = count + 1;
			}

		}
		return result;
	}

}
