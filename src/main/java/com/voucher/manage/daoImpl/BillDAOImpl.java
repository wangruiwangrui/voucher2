package com.voucher.manage.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.alibaba.fastjson.JSONObject;
import com.voucher.manage.dao.BillDAO;
import com.voucher.manage.daoModel.TTT.Bill;
import com.voucher.manage.daoModel.TTT.BillServerInfo;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModel.TTT.CompanyMsg;
import com.voucher.manage.daoModel.TTT.Payment_Info;
import com.voucher.manage.daoModel.TTT.PreBill;
import com.voucher.manage.daoModel.invoice.BillImg;
import com.voucher.manage.daoModel.invoice.BusinessData;
import com.voucher.manage.daoModel.invoice.BusinessResult;
import com.voucher.manage.daoModel.invoice.BusinessRows;
import com.voucher.manage.daoModel.invoice.RedBill;
import com.voucher.manage.daoModel.invoice.RedBusinessResult;
import com.voucher.manage.daoModel.invoice.RedBusinessRows;
import com.voucher.manage.daoSQL.InsertExe;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.UpdateExe;
import com.voucher.manage.mapper.WeiXinMapper;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.CopyFile;
import com.voucher.weixin.util.HttpUtils;

public class BillDAOImpl extends JdbcDaoSupport implements BillDAO {

	HttpUtils ut = new HttpUtils();
	
	@Override
	public BillServerInfo getBillInfo(String out_trade_no, String sl) {
		
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
		String[] where = {"campusId = ",paInfo.getCampusId().toString(),"Sl=",sl.toString()};
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
   			String[] where2={"campusId =",bill.getCampusId().toString(),"Sl=",sl.toString()};
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

	@Override
	public Integer InserIntoPreBill(BusinessData bData, Integer campusId) {
		PreBill preBill = new PreBill();
		BeanUtils.copyProperties(bData, preBill);
		
		preBill.setOut_trade_no(bData.getOrder_num());
		preBill.setCampusId(campusId);
		preBill.setState(0);
		return InsertExe.get(getJdbcTemplate(), preBill);
	}

	@Override
	public Integer updateErrMsg(String out_trade_no,String result) {
		PreBill preBill = new PreBill();
		preBill.setErr(result);
		String[] where = {"out_trade_no = ",out_trade_no};
		preBill.setWhere(where);
		return UpdateExe.get(getJdbcTemplate(), preBill);
	}

	@Override
	public PreBill selectPreBill(String out_trade_no) {
		PreBill preBill = new PreBill();
		String[] where = {"out_trade_no=",out_trade_no};
		preBill.setWhere(where);
		preBill.setLimit(10);
		preBill.setOffset(0);
		preBill.setNotIn("ID");
		preBill.setSort("ID asc");
		List list = SelectExe.get(getJdbcTemplate(), preBill);
		if(list.size()>0) {
			preBill = (PreBill) list.get(0);
		}
		return preBill;
	}

	@Override
	public Integer InserIntoBill(BusinessData bData, Integer campusId) {
		Bill bill = new Bill();
		BeanUtils.copyProperties(bData, bill);
		BeanUtils.copyProperties(bData.getCommon_fpkj_xmxx().get(0), bill);
		bill.setCampusId(campusId);
		bill.setPreState(0);
		return InsertExe.get(getJdbcTemplate(), bill);
	}

	@Override
	public Integer updatePreBill(String out_trade_no, BusinessResult result) {
		Bill bill = new Bill();
		BusinessRows rows = (BusinessRows) result.getRows().get(0);
		BeanUtils.copyProperties(rows, bill);
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
		
		String[] where = {"order_num=",out_trade_no};
		bill.setWhere(where);
		//设置预开票状态
		bill.setPreState(1);
		//设置开票状态
		bill.setState(1);
		bill.setMsg(result.getMsg());
		BeanUtils.copyProperties(rows.getCommon_fpkj_xmxx().get(0), bill);
		return UpdateExe.get(getJdbcTemplate(), bill);
	}

	@Override
	public Integer updatePreBillMsg(String out_trade_no, BusinessResult result) {
		Bill bill = new Bill();
		String[] where = {"order_num=",out_trade_no};
		bill.setWhere(where);
		bill.setPreState(2);
		bill.setMsg(result.getMsg());
		return UpdateExe.get(getJdbcTemplate(), bill);
	}

	@Override
	public Bill selectBillByOrderNum(String out_trade_no) {
		Bill bill = new Bill();
		String[] where = {"order_num = ",out_trade_no};
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
	public Integer updateToRed(RedBusinessResult result, String out_trade_no) {
		Bill bill = new Bill();
		String[] where = {"order_num= ",out_trade_no};
		bill.setWhere(where);
		bill.setOrder_num(result.getRows().get(0).getOrder_num());
		bill.setOldorder_num(out_trade_no);
		bill.setState(2);
		BeanUtils.copyProperties(result.getRows().get(0).getCommon_fpkj_xmxx().get(0), bill);
		return UpdateExe.get(getJdbcTemplate(), bill);
	}

	@Override
	public Integer updateRedBillMsg(String out_trade_no, RedBusinessResult result) {
		Bill bill = new Bill();
		String[] where = {"order_num= ",out_trade_no};
		bill.setWhere(where);
		bill.setMsg(result.getMsg());
		return UpdateExe.get(getJdbcTemplate(), bill);
	}

	@Override
	public Integer updateToBlue(BusinessResult result,String out_trade_noRed) {
		BusinessRows rows = (BusinessRows) result.getRows().get(0);
		Bill bill = new Bill();
		bill.setOrder_num(result.getRows().get(0).getOrder_num());
		String[] where = {"order_num= ",out_trade_noRed};
		bill.setWhere(where);
		bill.setState(1);
		bill.setPreState(1);
		BeanUtils.copyProperties(result,bill);
		BeanUtils.copyProperties(result.getRows().get(0),bill);
		BeanUtils.copyProperties(result.getRows().get(0).getCommon_fpkj_xmxx().get(0), bill);
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
		return UpdateExe.get(getJdbcTemplate(), bill);
	}

	@Override
	public Integer updateBillMsg(String out_trade_noRed, BusinessResult result) {
		Bill bill = new Bill();
		String[] where = {"order_num= ",out_trade_noRed};
		bill.setWhere(where);
		bill.setMsg(result.getMsg());
		return UpdateExe.get(getJdbcTemplate(), bill);
	}

	@Override
	public Payment_Info getPaymenInfo(String out_trade_no_new) {
		Payment_Info pInfo = new Payment_Info();
		String[] where = {"out_trade_no=",out_trade_no_new};
		pInfo.setWhere(where);
		pInfo.setLimit(10);
		pInfo.setOffset(0);
		pInfo.setNotIn("PaymentId");
		pInfo.setSort("PaymentId asc");
		List list = SelectExe.get(getJdbcTemplate(), pInfo);
		
		if (list.size()>0) {
			pInfo = (Payment_Info) list.get(0);
		}
	
		return pInfo;
	}
}
