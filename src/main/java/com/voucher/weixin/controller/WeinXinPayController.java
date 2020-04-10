package com.voucher.weixin.controller;

import java.io.BufferedReader;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.voucher.manage.controller.BillUController;
import com.voucher.manage.dao.BillDAO;
import com.voucher.manage.dao.FinanceDAO;
import com.voucher.manage.daoModel.TTT.BillServerInfo;
import com.voucher.manage.daoModel.TTT.CompanyMsg;
import com.voucher.manage.daoModel.TTT.HirePayInfo;
import com.voucher.manage.daoModel.TTT.Payment_Info;
import com.voucher.manage.daoModel.invoice.BusinessData;
import com.voucher.manage.daoModel.invoice.BusinessResult;
import com.voucher.manage.daoModel.invoice.Common_Fpkj_Xmxx;
import com.voucher.manage.mapper.WeiXinMapper;
import com.voucher.manage.model.Notice;
import com.voucher.manage.model.User_Asset;
import com.voucher.manage.model.WeiXin;
import com.voucher.manage.service.NoticeService;
import com.voucher.manage.service.UserService;
import com.voucher.manage.service.WeiXinService;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;
import com.voucher.weixin.util.HttpUtils;
import com.voucher.weixin.util.OrderNum;
import com.voucher.weixin.wxpay.sdk.WXConstant;
import com.voucher.weixin.wxpay.sdk.WXPayUtil;


@Controller
@RequestMapping("/mobile/weinXinPay")
public class WeinXinPayController {

    Logger logger = LoggerFactory.getLogger(WeinXinPayController.class);

    ApplicationContext applicationContext = new Connect().get();

    FinanceDAO financeDAO = (FinanceDAO) applicationContext.getBean("financeDao");
    BillDAO billDAO = (BillDAO) applicationContext.getBean("billDAO");
    private UserService userService;

    private WeiXinService weixinService;

    private NoticeService noticeService;

    @Value("${spbill_create_ip}")
    private String spbill_create_ip;

    @Value("${DOMAIN}")
    private String DOMAIN;

    @Autowired
    private WeiXinMapper weixinMapper;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setWeixinService(WeiXinService weixinService) {
        this.weixinService = weixinService;
    }

    @Autowired
    public void setNoticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @RequestMapping("/getHire")
    public @ResponseBody
    List getHire(@RequestBody JSONObject value, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map valueMap = (Map) value.get("value");

        Integer campusId = Integer.valueOf(valueMap.get("campusId").toString());

        String homeUrl = request.getHeader("Host") + request.getContextPath();

        WeiXin weixin = weixinService.getCampusById(campusId);

        String notify_url = weixin.getUrl() + WXConstant.notify_url;

        String guids = (String) valueMap.get("guid");
        int hire = (int) ((Float.valueOf(valueMap.get("hire").toString())) * 100);

        String[] guidsString = guids.split(",");

        String openId = (String) request.getSession().getAttribute("openId");

        String purchaser = valueMap.get("purchaser").toString();
        String ein = valueMap.get("ein").toString();
        String sl = valueMap.get("sl").toString();

        String out_trade_no = OrderNum.getOrderNum();

        //获取所有支付信息（合同信息，月租金信息）
        HirePayInfo hirePayInfo = new HirePayInfo();

        hirePayInfo.setChartGUID(valueMap.get("chartGUID").toString());
        hirePayInfo.setHireCount(Integer.valueOf((valueMap.get("count")).toString()));
        hirePayInfo.setHire(Float.valueOf(hire) / 100);
        hirePayInfo.setHireListGUID(guids);
        hirePayInfo.setGuidsCount(guidsString.length);
        hirePayInfo.setOpenId(openId);
        hirePayInfo.setSl(sl);
        Date date = new Date();
        hirePayInfo.setOut_trade_no(out_trade_no);
        hirePayInfo.setPaytime(date);

        int i = financeDAO.insertHirePayInfo(hirePayInfo);

        String appId = weixin.getAppId();

        String mch_id = weixin.getMchId();

        String api = weixin.getApi();

        String nonce_str = WXPayUtil.generateNonceStr();

        int total_fee = hire;

        Map<String, String> map = new HashMap<String, String>();
        //生成sign所需字段
        map.put("appid", appId);
        map.put("body", WXConstant.body);
        map.put("mch_id", mch_id);
        map.put("nonce_str", nonce_str);
        map.put("openid", openId);
        map.put("out_trade_no", out_trade_no);
        map.put("spbill_create_ip", spbill_create_ip);
        map.put("total_fee", String.valueOf(total_fee));
        map.put("trade_type", WXConstant.trade_type);
        map.put("notify_url", notify_url);

        String sign = WXPayUtil.generateSignature(map, weixin.getApi());

        map.put("sign", sign);

        String xml = WXPayUtil.mapToXml(map);

        HttpUtils httpUtils = new HttpUtils();

        String xmlStr = httpUtils.doPost(WXConstant.unifiedorder_url, xml);

        String prepay_id = "";// 预支付id

        List result = new ArrayList();

        Map<String, String> returnMap = new HashMap<String, String>();

        returnMap = WXPayUtil.xmlToMap(xmlStr);

        String return_code = returnMap.get("return_code");

        if (return_code.equals("SUCCESS")) {

            String result_code = returnMap.get("result_code");

            if (result_code.equals("SUCCESS")) {

                Map<String, String> payMap = new HashMap<String, String>();

                payMap.put("appId", appId);

                payMap.put("timeStamp", String.valueOf(WXPayUtil.getCurrentTimestampMs()));

                payMap.put("nonceStr", WXPayUtil.generateNonceStr());

                payMap.put("signType", "MD5");

                map.put("prepay_id", prepay_id);

                prepay_id = returnMap.get("prepay_id");

                payMap.put("package", "prepay_id=" + prepay_id);

                String paySign = WXPayUtil.generateSignature(payMap, weixin.getApi());

                payMap.put("paySign", paySign);

                payMap.put("total_fee", String.valueOf(total_fee));

                payMap.put("guids", guids);

                payMap.put("out_trade_no", out_trade_no);

                result.add("SUCCESS");

                result.add(payMap);

                LinkedHashMap<String, Map<String, Object>> registerMap = Singleton.getInstance().getRegisterMapLong();

                Map tradeMap = new HashMap<>();

                tradeMap.put("guids", guids);

                tradeMap.put("startTime", new Date());

                tradeMap.put("map", map);
                tradeMap.put("total_fee", total_fee);
                tradeMap.put("campusId", campusId);
                tradeMap.put("purchaser", purchaser);
                tradeMap.put("ein", ein);
                tradeMap.put("sl", sl);
                tradeMap.put("out_trade_no", out_trade_no);
                registerMap.put("tradeMap", tradeMap);

                return result;

            } else {

                Map<String, String> payMap = new HashMap<String, String>();

                String err_code = returnMap.get("err_code");

                String err_code_des = returnMap.get("err_code_des");

                payMap.put("err_code", err_code);

                payMap.put("err_code_des", err_code_des);

                result.add("ERR");

                result.add(payMap);

                return result;
            }
        } else if (return_code.equals("FAIL")) {

            Map<String, String> payMap = new HashMap<String, String>();

            String return_msg = returnMap.get("return_msg");

            payMap.put("return_msg", return_msg);

            result.add("FAIL");

            result.add(payMap);

            return result;
        }
        return result;
    }

    @RequestMapping("/get")
    public void get(@RequestParam String out_trade_no) {


        LinkedHashMap<String, Map<String, Object>> registerMap = Singleton.getInstance().getRegisterMapLong();

        Map tradeMap = registerMap.get(out_trade_no);

    }

    //微信支付回调函数
    @RequestMapping("/callback")
    public String callback(HttpServletRequest request, HttpServletResponse response) throws Exception {

        BufferedReader reader = null;
        String xmlString = null;
        try {
            reader = request.getReader();
            String line = "";

            StringBuffer inputString = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                inputString.append(line);
            }

            xmlString = inputString.toString();

        } finally {
            request.getReader().close();
        }

        Map map = new HashMap();

        String result_code = "";

        map = WXPayUtil.xmlToMap(xmlString);

        result_code = (String) map.get("result_code");

        if (result_code.equals("SUCCESS")) {

            LinkedHashMap<String, Map<String, Object>> registerMap = Singleton.getInstance().getRegisterMapLong();

            String out_trade_no = (String) map.get("out_trade_no");

            Map tradeMap = registerMap.get("tradeMap");

            if (tradeMap.get("guids") == null) {
                return WXConstant.FAIL;
            }
            if (tradeMap.get("guids") != null && !tradeMap.get("guids").equals("")) {

                String guids = (String) tradeMap.get("guids");

                String[] filesString = guids.split(",");

                ArrayList<String> list = new ArrayList<>();
                for (String fileString : filesString) {

                    list.add(URLDecoder.decode(fileString, "utf-8"));

                }

                Map map2 = (Map) tradeMap.get("map");

                String openId = (String) map2.get("openid");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date date = new Date();

                String time = sdf.format(date);

                User_Asset user_Asset = userService.getUserAssetByOnlyOpenId(openId);
                //Users users=userService.getUserByOnlyOpenId(openId);

                String name = user_Asset.getCharter();

                int total_fee = (int) tradeMap.get("total_fee");

                //通过campusId查询公众号支付成功提醒消息
                int campusId = (int) tradeMap.get("campusId");
                String chartGuid = (String) tradeMap.get("chartGuid");
                map.put("openId", openId);
                map.put("name", name);
                map.put("campusId", campusId);
                map.put("total_fee", total_fee);

                int i = financeDAO.updateHireSetHireListWinXinPay(map, list);

                if (i > 0) {

                    WechatSendMessageController wechatSendMessageController = new WechatSendMessageController();
                    //获取支付模板
                    Notice notice = new Notice();
                    notice.setTitle("支付成功提醒");
                    notice.setCampusId(campusId);
                    notice = noticeService.getTemplateIdByTitle(notice);
                    //获取交易详情(所有的所属日期)
                    List hireDates = financeDAO.queryHireDate(list);


                    Float totalfee = Float.parseFloat(String.valueOf(total_fee));

                    final String templateId=notice.getTemplateId();
                    
					try {
						Runnable r = new Runnable() {
							@Override
							public void run() {
								wechatSendMessageController.sendMessage2(campusId, openId, templateId, "支付成功提醒", "",
										name + "你好，你已支付成功", totalfee / 100 + "元", "微信支付 ",
										hireDates.toString() + "月房屋租金", out_trade_no, "", "感谢你的使用");
							}

						};
						Thread t = new Thread(r);
						t.start();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

                    //开启发票
                    String ein = (String) tradeMap.get("ein");
                    String purchaser = (String) tradeMap.get("purchaser");
                    String sl = (String) tradeMap.get("sl");

                    BillServerInfo billServerinfo = billDAO.getBillInfo(out_trade_no, sl);//查询发票信息
                    CompanyMsg cMsg = billDAO.queryCompanyMsg(out_trade_no); // 获取销售方纳税人信息

                    //商品详情
                    Common_Fpkj_Xmxx common_fpkj_xmxx = new Common_Fpkj_Xmxx();
                    common_fpkj_xmxx.setFphxz(billServerinfo.getFphxz());
                    common_fpkj_xmxx.setSpbm(billServerinfo.getSpbm());
                    common_fpkj_xmxx.setXmmc(billServerinfo.getXmmc());
                    common_fpkj_xmxx.setXmdj(String.valueOf(totalfee / 100));
                    common_fpkj_xmxx.setXmsl("1.000000");
                    common_fpkj_xmxx.setXmje(String.valueOf(totalfee / 100));

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
                    bData.setOrder_num(out_trade_no);
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
                    BillUController bController = new BillUController();
                    HttpUtils ut = new HttpUtils();

                    int count = 0;
                    for (int ij = 0; ij < 4; ij++) {
                        returnString = ut.doPost2(url, param.toString());
                        JSONObject jsonObject = JSONObject.parseObject(returnString);
                        result = JSON.toJavaObject(jsonObject, BusinessResult.class);

                        if (result.getResult().equals("SUCCESS")) {
                            // 设置开票状态
                            Integer re = billDAO.updatePreBill(out_trade_no, result);
                            Map map1 = bController.getBillOriginalPDF(out_trade_no, request, response);

                            String imgUrl = DOMAIN+ "/voucher/" + (String) map1.get("result");

                            Notice notice1 = new Notice();
                            notice1.setTitle("电子发票开具通知");
                            notice1.setCampusId(campusId);

                            notice1 = noticeService.getTemplateIdByTitle(notice1);

                            Date data = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String dat = formatter.format(data);

                            WeiXin weiXin = weixinMapper.getCampus(campusId);
                            String campany = weiXin.getCampusName();
                            
                            final String templateId2=notice1.getTemplateId();                          
                            final String urlString=imgUrl;                            
                            final String fp_dm=result.getRows().get(0).getFp_dm();
                            final String gmf_mc=result.getRows().get(0).getGmf_mc();
                            final String jshj=result.getRows().get(0).getJshj();
                            
        					try {
        						Runnable r = new Runnable() {
        							@Override
        							public void run() {
        								wechatSendMessageController.sendMessage2(campusId, openId, templateId2, "电子发票开具通知",
        	                                    urlString, "您的电子发票已开具", campany, dat, fp_dm,
        	                                    gmf_mc, jshj + "元", "依法纳税，你我有责。");
        							}

        						};
        						Thread t = new Thread(r);
        						t.start();
        					} catch (Exception e) {
        						// TODO: handle exception
        						e.printStackTrace();
        					}

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
                    return WXConstant.SUCCESS;

                } else if (i == -1) {
                    return WXConstant.SUCCESS;
                } else {
                    return WXConstant.FAIL;
                }

            } else {

                return WXConstant.FAIL;

            }

        }

        return WXConstant.FAIL;
    }

}
