package com.voucher.manage.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.alibaba.fastjson.JSONObject;
import com.voucher.manage.dao.BillDAO;
import com.voucher.manage.daoModel.TTT.Bill;
import com.voucher.manage.daoModel.TTT.BillServerInfo;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModel.TTT.CompanyMsg;
import com.voucher.manage.daoModel.TTT.Payment_Info;
import com.voucher.manage.daoModel.invoice.BillImg;
import com.voucher.manage.daoModel.invoice.BusinessResult;
import com.voucher.manage.daoModel.invoice.BusinessRows;
import com.voucher.manage.daoModel.invoice.Common_Fpkj_Xmxx;
import com.voucher.manage.daoModel.invoice.RedBill;
import com.voucher.manage.daoModel.invoice.RedBusinessResult;
import com.voucher.manage.daoModel.invoice.RedBusinessRows;
import com.voucher.manage.daoSQL.InsertExe;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.UpdateExe;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.CopyFile;
import com.voucher.weixin.util.HttpUtils;

public class BillDAOImpl extends JdbcDaoSupport implements BillDAO {

	HttpUtils ut = new HttpUtils();
	@Override
	public BillServerInfo getBillInfo(String out_trade_no) {
		
		String TokenStr; 
		Date currenTime;
		long timeDifference;

		currenTime=new Date();
		
		Payment_Info paInfo = new Payment_Info();
		paInfo.setOut_trade_no(out_trade_no);
		paInfo.setOffset(0);
		paInfo.setLimit(10);
		paInfo.setNotIn("PaymentId");
		paInfo.setSort("PaymentId asc");
		String[] where3 = {"out_trade_no = ",out_trade_no};
		paInfo.setWhere(where3);
		
		List list = SelectExe.get(this.getJdbcTemplate(), paInfo);
		paInfo = (Payment_Info) list.get(0);
		
		BillServerInfo bill = new BillServerInfo();
		bill.setLimit(10);
		bill.setOffset(0);
		bill.setNotIn("ID");
		bill.setSort("ID asc");
		String[] where = {"campusId = ",paInfo.getCampusId().toString()};
		bill.setWhere(where);
		List list2 = SelectExe.get(this.getJdbcTemplate(), bill);
		bill=(BillServerInfo) list2.get(0);
		
		timeDifference=currenTime.getTime()-bill.getTokenCreateDate().getTime();
	   
		if(timeDifference>5100*1000){
	       
    	   	String url = bill.getAPI_TOKEN_URL();
   		
   			JSONObject param = new JSONObject();
   			param.put("openid", bill.getOpenID());
   			param.put("app_secret",bill.getAppSecret());
   			TokenStr = ut.doPost2(url, param.toString());
   			
   			JSONObject parseObject = JSONObject.parseObject(TokenStr);
   			JSONObject row = (JSONObject) parseObject.get("rows");
   			String access_token = (String) row.get("access_token");
   			
   			bill.setTokenStr(access_token);
   			bill.setTokenCreateDate(currenTime);
   			String[] where2={"campusId =",bill.getCampusId().toString()};
   			bill.setWhere(where2);
   			Integer i = updateBillServerInfo(bill);
	       }
		
		return bill;
	}

	@Override
	public Integer updateBillServerInfo(BillServerInfo billServerinfo) {
		
		return UpdateExe.get(this.getJdbcTemplate(), billServerinfo);
	}

	@Override
	public Payment_Info selectPayment_Info(String out_trade_no) {

		Payment_Info pInfo = new Payment_Info();
		String[] where = {"out_trade_no = ",out_trade_no};
		pInfo.setWhere(where);
		pInfo.setLimit(10);
		pInfo.setOffset(0);
		pInfo.setNotIn("PaymentId");
		pInfo.setSort("PaymentId asc");
		List list = SelectExe.get(this.getJdbcTemplate(), pInfo);
		if (list.size()>0) {
			pInfo = (Payment_Info) list.get(0);
		}
		return pInfo;
	}

	@Override
	public List qureyAll(String out_trade_no) {
		Payment_Info pInfo = new Payment_Info();
		
		return null;
	}

	@Override
	public CompanyMsg queryCompanyMsg(String out_trade_no) {
		
		Payment_Info paInfo = new Payment_Info();
		
		paInfo.setOffset(0);
		paInfo.setLimit(10);
		paInfo.setNotIn("PaymentId");
		paInfo.setSort("PaymentId asc");
		String[] where = {"out_trade_no= " , out_trade_no};
		paInfo.setWhere(where);
		List list = SelectExe.get(getJdbcTemplate(), paInfo);
		paInfo = (Payment_Info) list.get(0);
		
		CompanyMsg cMsg = new CompanyMsg();
		
		cMsg.setLimit(10);
		cMsg.setOffset(0);
		cMsg.setNotIn("ID");
		cMsg.setSort("ID asc");
		String[] where2 = {"campusId = ",paInfo.getCampusId().toString()};
		cMsg.setWhere(where2);
		List list2 = SelectExe.get(getJdbcTemplate(), cMsg);
		cMsg = (CompanyMsg) list2.get(0);
		return cMsg;
	}


	@Override
	public Integer InsertBillFirst(BusinessResult result, Integer campusId) {
		Bill bill = new Bill();
		BusinessRows rows = (BusinessRows) result.getRows().get(0);
		//Common_Fpkj_Xmxx common = (Common_Fpkj_Xmxx) rows.getCommon_fpkj_xmxx();
		bill.setMsg(result.getMsg());
		bill.setFhr(rows.getFhr());
		bill.setXsf_mc(rows.getXsf_mc());
		bill.setYfp_hm(rows.getYfp_hm());
		bill.setBz(rows.getBz());
		bill.setXsf_yhzh(rows.getXsf_yhzh());
		bill.setGmf_mc(rows.getGmf_mc());
		bill.setHjse(rows.getHjse());
		bill.setFp_dm(rows.getFp_dm());
		bill.setKce(rows.getKce());
		bill.setYfp_dm(rows.getYfp_dm());
		bill.setFp_hm(rows.getFp_hm());
		bill.setGmf_nsrsbh(rows.getGmf_nsrsbh());
		bill.setItype(rows.getItype());
		bill.setJym(rows.getJym());
		bill.setKplx(rows.getKplx());
		bill.setPdf_item_key(rows.getPdf_item_key());
		bill.setOrder_num(rows.getOrder_num());
		bill.setZsfs(rows.getZsfs());
		bill.setXsf_dzdh(rows.getXsf_dzdh());
		bill.setJqbh(rows.getJqbh());
		bill.setHjje(rows.getHjje());
		bill.setGmf_dzdh(rows.getGmf_dzdh());
		bill.setFpqqlsh(rows.getFpqqlsh());
		bill.setSkr(rows.getSkr());
		bill.setGmf_yhzh(rows.getGmf_yhzh());
		bill.setKpr(rows.getKpr());
		bill.setXsf_nsrsbh(rows.getXsf_nsrsbh());
		bill.setFp_mw(rows.getFp_mw());
		bill.setJshj(rows.getJshj());
		bill.setPdf_key(rows.getPdf_key());
		bill.setCampusId(campusId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String year = rows.getKprq().substring(0, 4);
		String month = rows.getKprq().substring(4, 6);
		String day = rows.getKprq().substring(6, 8);
		String hour = rows.getKprq().substring(8, 10);
		String minute = rows.getKprq().substring(10, 12);
		String ss = rows.getKprq().substring(12,14);
		String time = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+ss;
		Date dat=null;
		try {
			dat = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bill.setKprq(dat);
		
		bill.setEXT_CODE(rows.getEXT_CODE());
		
		bill.setState(1);
		
		return InsertExe.get(this.getJdbcTemplate(), bill);
	}
	
	@Override
	public Integer InsertBill(BusinessResult result,Integer campusId,String out_trade_no) {
		Bill bill = new Bill();
		BusinessRows rows = (BusinessRows) result.getRows().get(0);
		//Common_Fpkj_Xmxx common = (Common_Fpkj_Xmxx) rows.getCommon_fpkj_xmxx();
		bill.setMsg(result.getMsg());
		bill.setFhr(rows.getFhr());
		bill.setXsf_mc(rows.getXsf_mc());
		bill.setYfp_hm(rows.getYfp_hm());
		bill.setBz(rows.getBz());
		bill.setXsf_yhzh(rows.getXsf_yhzh());
		bill.setGmf_mc(rows.getGmf_mc());
		bill.setHjse(rows.getHjse());
		bill.setFp_dm(rows.getFp_dm());
		bill.setKce(rows.getKce());
		bill.setYfp_dm(rows.getYfp_dm());
		bill.setFp_hm(rows.getFp_hm());
		bill.setGmf_nsrsbh(rows.getGmf_nsrsbh());
		bill.setItype(rows.getItype());
		bill.setJym(rows.getJym());
		bill.setKplx(rows.getKplx());
		bill.setPdf_item_key(rows.getPdf_item_key());
		bill.setOrder_num(rows.getOrder_num());
		bill.setZsfs(rows.getZsfs());
		bill.setXsf_dzdh(rows.getXsf_dzdh());
		bill.setJqbh(rows.getJqbh());
		bill.setHjje(rows.getHjje());
		bill.setGmf_dzdh(rows.getGmf_dzdh());
		bill.setFpqqlsh(rows.getFpqqlsh());
		bill.setSkr(rows.getSkr());
		bill.setGmf_yhzh(rows.getGmf_yhzh());
		bill.setKpr(rows.getKpr());
		bill.setXsf_nsrsbh(rows.getXsf_nsrsbh());
		bill.setFp_mw(rows.getFp_mw());
		bill.setJshj(rows.getJshj());
		bill.setPdf_key(rows.getPdf_key());
		bill.setCampusId(campusId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String year = rows.getKprq().substring(0, 4);
		String month = rows.getKprq().substring(4, 6);
		String day = rows.getKprq().substring(6, 8);
		String hour = rows.getKprq().substring(8, 10);
		String minute = rows.getKprq().substring(10, 12);
		String ss = rows.getKprq().substring(12,14);
		String time = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+ss;
		Date dat=null;
		try {
			dat = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bill.setKprq(dat);
		
		bill.setEXT_CODE(rows.getEXT_CODE());
		
		bill.setState(1);
		bill.setOldorder_num(out_trade_no);
		
		return InsertExe.get(this.getJdbcTemplate(), bill);
	}
	
	@Override
	public Integer InsertRedBill(RedBusinessResult result,Integer campusId) {
		
		RedBill redBill = new RedBill();
		RedBusinessRows redRows = result.getRows().get(0);
		
		redBill.setMsg(result.getMsg());
		redBill.setFhr(redRows.getFhr());
		redBill.setXsf_mc(redRows.getXsf_mc());
		redBill.setYfp_hm(redRows.getYfp_hm());
		redBill.setBz(redRows.getBz());
		redBill.setXsf_yhzh(redRows.getXsf_yhzh());
		redBill.setGmf_mc(redRows.getGmf_mc());
		redBill.setHjse(redRows.getHjse());
		redBill.setFp_dm(redRows.getFp_dm());
		redBill.setKce(redRows.getKce());
		redBill.setYfp_dm(redRows.getYfp_dm());
		redBill.setFp_hm(redRows.getFp_hm());
		redBill.setGmf_nsrsbh(redRows.getGmf_nsrsbh());
		redBill.setItype(redRows.getItype());
		redBill.setJym(redRows.getJym());
		redBill.setKplx(redRows.getKplx());
		redBill.setPdf_item_key(redRows.getPdf_item_key());
		redBill.setOrder_num(redRows.getOrder_num());
		redBill.setZsfs(redRows.getZsfs());
		redBill.setXsf_dzdh(redRows.getXsf_dzdh());
		redBill.setJqbh(redRows.getJqbh());
		redBill.setHjje(redRows.getHjje());
		redBill.setGmf_dzdh(redRows.getGmf_dzdh());
		redBill.setFpqqlsh(redRows.getFpqqlsh());
		redBill.setSkr(redRows.getSkr());
		redBill.setGmf_yhzh(redRows.getGmf_yhzh());
		redBill.setKpr(redRows.getKpr());
		redBill.setXsf_nsrsbh(redRows.getXsf_nsrsbh());
		redBill.setFp_mw(redRows.getFp_mw());
		redBill.setJshj(redRows.getJshj());
		redBill.setPdf_key(redRows.getPdf_key());
		redBill.setCampusId(campusId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String year = redRows.getKprq().substring(0, 4);
		String month = redRows.getKprq().substring(4, 6);
		String day = redRows.getKprq().substring(6, 8);
		String hour = redRows.getKprq().substring(8, 10);
		String minute = redRows.getKprq().substring(10, 12);
		String ss = redRows.getKprq().substring(12,14);
		String time = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+ss;
		Date dat=null;
		try {
			dat = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		redBill.setKprq(dat);
		redBill.setState(1);
		return InsertExe.get(this.getJdbcTemplate(), redBill);
	}

	@Override
	public Bill queryBillByOrderNum(Bill bill) {
		
		String[] where = {"order_num = ",bill.getOrder_num()};
		bill.setWhere(where);
		bill.setLimit(10);
		bill.setOffset(0);
		bill.setNotIn("BillId");
		bill.setSort("BillId asc");
		List list = SelectExe.get(this.getJdbcTemplate(), bill);
		if (list.size()>0) {
			bill = (Bill) list.get(0);
		}
		return bill;
	}


	@Override
	public Integer updateBill(String order_num) {
		Bill bill = new Bill();
		String[] where = {"order_num= ",order_num};
		bill.setState(2);
		bill.setWhere(where);
		
		return UpdateExe.get(this.getJdbcTemplate(), bill);
	}
	
	@Override
	public Integer updateBillImage(String nameStr,String out_trade_no) {
		Bill bill = new Bill();
		bill.setImgUrl(nameStr);
		String[] where = {"order_num = ",out_trade_no};
		bill.setWhere(where);
		
		return UpdateExe.get(this.getJdbcTemplate(), bill);
	}

	@Override
	public ChartInfo queryChartInfo(String guid) {
		ChartInfo chartInfo = new ChartInfo();
		String[] where = {"GUID = " ,guid};
		chartInfo.setLimit(10);
		chartInfo.setOffset(0);
		
		chartInfo.setNotIn("GUID");
		chartInfo.setSort("GUID asc");
		chartInfo.setWhere(where);
		List list = SelectExe.get(this.getJdbcTemplate(), chartInfo);
		return (ChartInfo) list.get(0);
	}

	@Override
	public Integer insertBillImage(String orderNum ,String imgUrl) {
		Date date = new Date();
		BillImg billImg = new BillImg();
		billImg.setImgUrl(imgUrl);
		billImg.setOrder_num(orderNum);
		billImg.setDate(date);
		
		return InsertExe.get(this.getJdbcTemplate(), billImg);
	}

	@Override
	public String queryBillImgByOrderNum(String orderNum,HttpServletRequest request) {
		
		String pathRoot = System.getProperty("user.home");
		
		String filePath=pathRoot+Singleton.filePath;
        
		String imgPath=request.getSession().getServletContext().getRealPath(Singleton.filePath);
		
		BillImg billImg = new BillImg();
		String[] where = {"order_num = ",orderNum};
		billImg.setWhere(where);
		billImg.setLimit(10);
		billImg.setOffset(0);
		billImg.setSort("ID asc");
		billImg.setNotIn("ID");
		
		List list = SelectExe.get(this.getJdbcTemplate(), billImg);
		
		String imgUrl = "";
		
		if (list.size()>0) {
			billImg = (BillImg) list.get(0);
			imgUrl = billImg.getImgUrl();
		}
		
		String oldFile=Singleton.ROOMINFOIMGPATH+imgUrl;
		
		System.out.println("oldFile="+oldFile);
		
		System.out.println("imgPath="+imgUrl);
		
		CopyFile.set(imgPath, oldFile, imgUrl);
		
		return imgUrl;
	}

	@Override
	public Integer updateRedBill(String out_trade_noRed) {
		RedBill redBill = new RedBill();
		String[] where = {"order_num = ",out_trade_noRed};
		redBill.setState(2);
		redBill.setWhere(where);
		
 		return UpdateExe.get(getJdbcTemplate(), redBill);
	}


}
