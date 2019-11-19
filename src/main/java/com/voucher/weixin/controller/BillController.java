package com.voucher.weixin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;
import com.voucher.manage.dao.BillDAO;
import com.voucher.manage.daoModel.TTT.Payment_Info;
import com.voucher.manage.model.WeiXin;
import com.voucher.manage.service.WeiXinService;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;
import com.voucher.weixin.util.BillAccessTokenUtil;

@Controller
@RequestMapping("/mobile/bill")
public class BillController {
	
	ApplicationContext applicationContext = new Connect().get(); 
	BillDAO billDAO= (BillDAO) applicationContext.getBean("billDAO");
	
	@Autowired
	private WeiXinService weixinService;
	@Autowired
	private BillAccessTokenUtil billAcc;
	
	/**
	 * 获取授权页链接
	 * @return
	 */
	@RequestMapping("/getTicket")
	@ResponseBody
	public Map<String, String> getTicket(String out_trade_no,HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = billAcc.setContact();
		if(map.get("errmsg").equals("ok")) {
			Map<String, String> map2 = billAcc.getAuthorizationLink(out_trade_no);
			return map2;
		}
		return map;
	}
	
	@RequestMapping("/invoice")
	@ResponseBody
	public String invoice(HttpServletRequest request, HttpServletResponse response) {
		
		billAcc.getAccessToken("");
		return "111111122222";
	}
	
	
	/*
	public BillController(CarDatabaseContext context, IHostingEnvironment hostingEnvironment)
    {
        _context = context;
        _hostingEnvironment = hostingEnvironment;
    }

    public Task<IActionResult> Index()
    {
        var sphone = HttpContext.User.Identity.Name;
        if (sphone == null)
        {
            return RedirectToAction("login", "mogile");
        }
        return RedirectToAction("BillQryFinish", new { ph = sphone });
        //return View();            
    }
    /// 已开发票
    // GET: Mobile/Bill/BillFinish

    public Task<IActionResult> BillQryFinish(string ph, [Bind("UserPhone")] BillIndexModelView bimv)
    {
        if (ph == null)
        {
            ph = bimv.UserPhone;
        }

        var billview = new BillModelView();

        if (ph != null)
        {
            var user = await _context.User.FirstOrDefaultAsync(m => m.LinkPhone == ph);
            if (user != null)
            {
                billview.billList = await _context.Bill.Where(o => o.UserId == user.UserId).OrderByDescending(by => by.CreateDate).ToListAsync();
            }
            //billview.billFinishCount = billview.billList.Count;
            //billview.billWaitCount = _context.Bill.Where(o => o.UserId == user.UserId && o.BillState < 2).Count();
        }
        return View(billview);
    }
    /// 待开发票
    // GET: Mobile/Bill/BillWait
    public Task<IActionResult> BillQryWait([Bind("UserPhone")] BillIndexModelView bimv)
    {
        var billview = new BillModelView();
        var user = await _context.User.FirstOrDefaultAsync(m => m.LinkPhone == HttpContext.User.Identity.Name || m.WxAccount == User.Identity.Name);
        if (user != null)
        {
            billview.billList = await _context.Bill.Where(o => o.UserId == user.UserId && o.BillState < 2).OrderByDescending(by => by.CreateDate).ToListAsync();
        }
        billview.billFinishCount = _context.Bill.Where(o => o.UserId == user.UserId && o.BillState == 2).Count();
        billview.billWaitCount = billview.billList.Count;

        return View(billview);
    }
    /// 已开发票
    // GET: Mobile/Bill/BillFinish
    public Task<IActionResult> BillFinish()
    {
        var billview = new BillModelView();

        var user = await _context.User.FirstOrDefaultAsync(m => m.LinkPhone == HttpContext.User.Identity.Name || m.WxAccount == User.Identity.Name);
        if (user != null)
        {
            billview.billList = await _context.Bill.Where(o => o.UserId == user.UserId && o.BillState == 2).OrderByDescending(by => by.CreateDate).ToListAsync();
        }
        //billview.billFinishCount = billview.billList.Count;
        //billview.billWaitCount = _context.Bill.Where(o => o.UserId == user.UserId && o.BillState < 2).Count();

        return View(billview);
    }
    /// 待开发票
    // GET: Mobile/Bill/BillWait
    public Task<IActionResult> BillWait()
    {
        var billview = new BillModelView();
        var user = await _context.User.FirstOrDefaultAsync(m => m.LinkPhone == HttpContext.User.Identity.Name || m.WxAccount == User.Identity.Name);
        if (user != null)
        {
            billview.billList = await _context.Bill.Where(o => o.UserId == user.UserId && o.BillState < 2).OrderByDescending(by => by.CreateDate).ToListAsync();
        }
        billview.billFinishCount = _context.Bill.Where(o => o.UserId == user.UserId && o.BillState == 2).Count();
        billview.billWaitCount = billview.billList.Count;

        return View(billview);
    }

    /// 显示发票
    // GET: Mobile/Bill/BillWait
    public ActionResult BillView(int id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var bill = _context.Bill.FirstOrDefault(m => m.BillId == id);
        if (bill == null)
        {
            return NotFound();
        }
        //var stream = new MemoryStream();
        //var streamWriter = new StreamWriter(stream);
        //streamWriter.Flush();
        //stream.Seek(0, SeekOrigin.Begin);

        //return new FileStreamResult(stream, "application/pdf");
        //return FileContentResult();
        //string webRootPath = _hostingEnvironment.WebRootPath;
        //string contentRootPath = _hostingEnvironment.ContentRootPath;
        FileStream fs = new FileStream(_hostingEnvironment.WebRootPath + "\\BillPdf\\" + bill.BillUrl, FileMode.Open, FileAccess.Read);
        string contentType = "application/pdf";
        return File(fs, contentType);
    }
    /// 显示发票
    // GET: Mobile/Bill/Details/5
    public Task<IActionResult> Details(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var bill = await _context.Bill
            .FirstOrDefaultAsync(m => m.BillId == id);
        if (bill == null)
        {
            return NotFound();
        }
        
        FileStream fs = new FileStream(_hostingEnvironment.ContentRootPath + "\\BillPdf\\" + bill.BillUrl, FileMode.Open, FileAccess.Read);
        string contentType = "application/pdf";
        return File(fs, contentType,bill.CompanyName+".pdf");
    }

    // GET: Mobile/Bill/Create
    public IActionResult Create()
    {
        return View();
    }

    // POST: Mobile/Bill/Create
    // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
    // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
    //public async Task<IActionResult> Create([Bind("BillId,OrderId,Fp_Hm,BillState,BillUrl,CreateDate,BillTotal,Remark")] CustomerTax Tax)
    public Task<IActionResult> Create([Bind("CustomerInfoId,CustomerId,CompanyName,TaxpayerId,TaxType,LinkMan,LinkPhone,LinkAddr,BankName,BankID,DefaultFlag,Remark")] CustomerTax customerTax)
    {
        if (ModelState.IsValid)
        {
            _context.Add(customerTax);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }
        return View(customerTax);
    }
    public Task<IActionResult> GetBill(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }
        var order = await _context.Order.FindAsync(id);
        //var user= await _context.User.FindAsync(order.CustomerId);
        var tax = await _context.CustomerTax.Where(c => c.UserId == order.CustomerId).OrderByDescending(o => o.CreateDate).FirstOrDefaultAsync();
        order.CustomerId = tax.CustomerTaxId;
        order.TaxState = 1;
        var bill = new Bill();
        bill.UserId = Convert.ToInt32(order.CustomerId);
        bill.CompanyName = tax.CompanyName;
        bill.PayTaxId = tax.TaxpayerId;
        bill.BillState = 1;
        bill.BillTotal = order.OrderAmount;
        bill.OrderId = order.OrderId;
        bill.CustomerTaxId = tax.CustomerTaxId;
        bill.CreateDate = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
        bill.Remark = "客户端补开发票";
        _context.Bill.Add(bill);
        _context.SaveChanges();
        if (!GetTax(bill))
        {
            return NotFound();
        }
        return RedirectToAction("index", "orders");
    }
    public Task<IActionResult> EditBill(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var bill = await _context.Bill.FindAsync(id);
        if (bill == null)
        {
            return NotFound();
        }
        return View(bill);
    }
  
    public Task<IActionResult> EditBill(int id, [Bind("BillId,CompanyName,PayTaxId")] Bill bill)
    {
        if (id != bill.BillId)
        {
            return NotFound();
        }

        if (ModelState.IsValid)
        {
            var objbill = await _context.Bill.FindAsync(id);
            var objuser = await _context.User.FindAsync(objbill.UserId);
            var objtax = await _context.CustomerTax.Where(c => c.CustomerTaxId == objbill.CustomerTaxId && c.UserId == objbill.UserId).FirstOrDefaultAsync();

            try
            {
                if (this.GetTax(bill))
                {
                    if (objtax == null)
                    {
                        objtax = new CustomerTax();
                        _context.Add(objtax);
                    }
                    objtax.CompanyName = bill.CompanyName;
                    objtax.TaxpayerId = bill.PayTaxId;
                    //_context.Update(objtax);
                    _context.SaveChanges();
                }
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!BillExists(bill.BillId))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }
            return RedirectToAction("BillFinish");
        }
        return View(bill);
    }
    // GET: Mobile/Bill/Edit/5
    public Task<IActionResult> Edit(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var bill = await _context.Bill.FindAsync(id);
        if (bill == null)
        {
            return NotFound();
        }
        return View(bill);
    }

    public Task<IActionResult> Edit(int id, [Bind("BillId,OrderId,Fp_Hm,BillState,BillUrl,CreateDate,BillTotal,Remark")] Bill bill)
    {
        if (id != bill.BillId)
        {
            return NotFound();
        }

        if (ModelState.IsValid)
        {
            try
            {
                _context.Update(bill);
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!BillExists(bill.BillId))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }
            return RedirectToAction(nameof(Index));
        }
        return View(bill);
    }

    public Task<IActionResult> Delete(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var bill = await _context.Bill
            .FirstOrDefaultAsync(m => m.BillId == id);
        if (bill == null)
        {
            return NotFound();
        }

        return View(bill);
    }


    public Task<IActionResult> DeleteConfirmed(int id)
    {
        var bill = await _context.Bill.FindAsync(id);
        _context.Bill.Remove(bill);
        await _context.SaveChangesAsync();
        return RedirectToAction(nameof(Index));
    }

    private bool BillExists(int id)
    {
        return _context.Bill.Any(e => e.BillId == id);
    }
    public Boolean GetTax(Bill billn)
    {
        var resultstate = false;
        //支付成功处理发票
        if (billn != null && billn.CompanyName != null)
        {
            var bill = _context.Bill.Where(u => u.BillId == billn.BillId).FirstOrDefault();
            var order = _context.Order.Where(o => o.OrderId == billn.OrderId).FirstOrDefault();
            var user = _context.User.Where(u => u.UserId == bill.UserId).FirstOrDefault();

            var billapi = _context.BillServerInfo.FirstOrDefault();
            int tokenExpireTime = billapi.TokenExpireTime;
            tokenExpireTime = tokenExpireTime - 300;
            var client = new RestClient(billapi.ApiTokenUrl);
            var request = new RestRequest(Method.POST);
            //令牌过期处理
            if (billapi.TokenCreateDate < DateTime.Now.AddSeconds(-tokenExpireTime))
            {
                var billAcc = new BillAccount();
                billAcc.openid = billapi.OpenId;
                billAcc.app_secret = billapi.AppSecret;
                request.Parameters.Clear();
                request.AddJsonBody(billAcc);
                var result = client.Execute(request).Content;
                var loginresult = SimpleJson.DeserializeObject<LoingResultStr>(result);
                if (loginresult.result.Contains("SUCCESS") && loginresult.rows.access_token != null)
                {
                    billapi.TokenStr = loginresult.rows.access_token;
                    billapi.TokenExpireTime = Convert.ToInt32(loginresult.rows.expire_in);
                    billapi.TokenCreateDate = DateTime.Now;
                    _context.SaveChanges();
                }
                else
                {
                    //发票接口调用异常.错误信息
                }
            }
            //帐号验证成功
            if (billapi.TokenCreateDate > DateTime.Now.AddSeconds(-tokenExpireTime))
            {
                var companymsg = _context.CompanyMsg.FirstOrDefault();
                var data = new BusinessBase();
                data.access_token = billapi.TokenStr;
                data.serviceKey = HttpApiUrl.Key_Blue_Type;
                data.data = new BusinessData();
                data.data.data_resources = billapi.DataResources;
                data.data.nsrsbh = companymsg.Nsrsbh;
                data.data.order_num = bill.OrdItemId;
                //data.data.order_num = DateTime.Now.ToString("yyyMMddHHmmss");
                data.data.bmb_bbh = billapi.BmbBbh;
                data.data.zsfs = billapi.Zsfs;
                data.data.xsf_nsrsbh = companymsg.Nsrsbh;
                data.data.xsf_mc = companymsg.CompanyName;
                data.data.xsf_dzdh = companymsg.Dzdh;
                data.data.xsf_yhzh = companymsg.Yhzh;
                data.data.gmf_nsrsbh = billn.PayTaxId;
                data.data.gmf_mc = billn.CompanyName;
                data.data.kpr = companymsg.Operator;
                var billbuss = new BillBuss();
                billbuss.jshj = bill.BillTotal;
                //billbuss.hjje = billbuss.jshj / 1.03;
                var billsl = 1 + billapi.Sl;
                billbuss.hjje = Convert.ToDouble(Math.Round(Convert.ToDecimal(billbuss.jshj / billsl), 2, MidpointRounding.AwayFromZero));
                billbuss.hjse = billbuss.jshj - billbuss.hjje;
                billbuss.hjse = Convert.ToDouble(Math.Round(Convert.ToDecimal(billbuss.hjse), 2, MidpointRounding.AwayFromZero));
                data.data.jshj = billbuss.jshj.ToString();
                data.data.hjje = billbuss.hjje.ToString();
                data.data.hjse = billbuss.hjse.ToString();
                data.data.common_fpkj_xmxx = new System.Collections.Generic.List<Common_Fpkj_Xmxx>();
                var item = new Common_Fpkj_Xmxx
                {
                    fphxz = billapi.Fphxz,
                    spbm = billapi.Spbm,
                    xmmc = billapi.Xmmc,
                    ggxh = billapi.Ggxh,
                    dw = billapi.Dw,
                    xmje = billbuss.jshj.ToString(),
                    sl = billapi.Sl.ToString(),
                    se = billbuss.hjse.ToString()
                };
                data.data.common_fpkj_xmxx.Add(item);
                request.Parameters.Clear();
                request.AddJsonBody(data);
                client.BaseUrl = new Uri(HttpApiUrl.API_BUSS_URL);
                //申请发票
                var billresult = client.Execute(request).Content;
                var resultData = SimpleJson.DeserializeObject<BusinessResult>(billresult);
                bill.Result = resultData.result;
                bill.ResultMsg = resultData.msg;
                if (resultData.result.Contains("SUCCESS"))
                {
                    //开票成功
                    resultstate = true;
                    bill.PdfKey = resultData.rows[0].pdf_key;
                    request.Parameters.Clear();
                    var querydata = new QueryData();
                    querydata.access_token = billapi.TokenStr;
                    querydata.serviceKey = HttpApiUrl.Key_C_Pdf_Type;
                    querydata.data = new QueryDataBase();
                    querydata.data.fileKey = resultData.rows[0].pdf_key;
                    bill.Fp_Hm = resultData.rows[0].fp_hm;
                    request.AddJsonBody(querydata);
                    var billpdfresult = client.Execute(request).Content;
                    var resultPdf = SimpleJson.DeserializeObject<QueryResult>(billpdfresult);
                    if (resultPdf.result.Contains("SUCCESS"))
                    {
                        //bill.BillUrl = user.LinkPhone + "_" + bill.OrderId + ".pdf";
                        bill.BillUrl = bill.OrdItemId + ".pdf";
                    }
                    //开票成功,开票状态 
                    order.TaxState = 2;
                    order.OrderState = 3;
                    bill.BillState = 2;
                    bill.State = 2;
                    bill.CompanyName = billn.CompanyName;
                    bill.PayTaxId = billn.PayTaxId;
                    var sw = BillApiUtils.BytesToStream(_hostingEnvironment.ContentRootPath + "\\BillPdf\\" + bill.BillUrl, BillApiUtils.Base64ToBytes(resultPdf.rows.url));
                    sw.Close();
                    if (bill.InsCardState == 1)
                    {
                        //微信交易需要处理微信插卡
                        if (!string.IsNullOrEmpty(bill.CreateDate) && Convert.ToDateTime(bill.CreateDate) > DateTime.Now.AddDays(-2))
                        {
                            //2天之内可以补开发票
                            var filepath = _hostingEnvironment.ContentRootPath + "\\BillPdf\\" + bill.BillUrl;
                            var token = _context.WxToken.FirstOrDefault();
                            if (token.SetTestState == 1 || token.SetTestState == 2)
                            {
                                log.Info("文件：" + filepath + ",Token:" + token.WxTokenStr);
                            }
                            UploadPdfResult pdffile = new UploadPdfResult();
                            try
                            {
                                pdffile = WxCardHelp.UploadPdf(token.WxTokenStr, filepath);
                            }
                            catch (Exception ex)
                            {
                                log.Info("上传Pdf:" + ex.Message);
                            }
                            if (token.SetTestState == 1 || token.SetTestState == 2)
                            {
                                log.Info("上传pdf状态：" + pdffile.errcode + "---" + pdffile.errmsg + "---s_media_id:" + pdffile.s_media_id);
                            }
                            if (pdffile.errcode == 0)
                            {
                                if (token.SetTestState == 1 || token.SetTestState == 2)
                                {
                                    log.Info("开始插卡!");
                                }
                                try
                                {
                                    bill.InsCardState = 1;
                                    bill.S_MediaId = pdffile.s_media_id;
                                    var card = new InsertCard();
                                    card.order_id = order.OrderNumber;
                                    card.card_id = token.CardId;
                                    card.appid = token.AppId;
                                    card.card_ext.nonce_str = TimeHelper.GetTimeStamp(true).ToString();
                                    card.card_ext.user_card.invoice_user_data.fee = Convert.ToInt32(bill.BillTotal * 100);
                                    card.card_ext.user_card.invoice_user_data.title = bill.CompanyName;
                                    if (!string.IsNullOrEmpty(bill.PayTaxId))
                                    {
                                        card.card_ext.user_card.invoice_user_data.buyer_number = bill.PayTaxId;
                                    }
                                    card.card_ext.user_card.invoice_user_data.billing_time = TimeHelper.GetTimeStamp(true);
                                    card.card_ext.user_card.invoice_user_data.billing_no = resultData.rows[0].fp_dm;
                                    card.card_ext.user_card.invoice_user_data.billing_code = resultData.rows[0].fp_hm;
                                    card.card_ext.user_card.invoice_user_data.fee_without_tax = Convert.ToInt32(Convert.ToDouble(resultData.rows[0].hjje) * 100);
                                    card.card_ext.user_card.invoice_user_data.tax = Convert.ToInt32(Convert.ToDouble(resultData.rows[0].hjse) * 100);
                                    card.card_ext.user_card.invoice_user_data.s_pdf_media_id = pdffile.s_media_id;
                                    card.card_ext.user_card.invoice_user_data.check_code = resultData.rows[0].jym;
                                    var card_result = WxCardHelp.WxInsertCard(token.WxTokenStr, card);
                                    if (card_result.errcode == 0)
                                    {
                                        bill.InsCardState = 2;
                                        _context.SaveChangesAsync();

                                    }
                                    if (token.SetTestState == 1 || token.SetTestState == 2)
                                    {
                                        log.Info("后台插卡状态：" + card_result.errcode + ",信息：" + card_result.errmsg);
                                    }

                                }
                                catch (Exception ex)
                                {
                                    log.Info("插卡错误:" + ex.Message);
                                }



                            }
                            //bill.InsCardState = 2;
                        }
                    }
                }
                else
                {
                    resultstate = false;
                }
                _context.SaveChangesAsync();
            }

        }

        return resultstate;
    }
*/
}
