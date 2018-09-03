package com.voucher.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.voucher.manage.model.Campus;
import com.voucher.manage.model.CampusAdmin;
import com.voucher.manage.service.CampusService;
import com.voucher.manage.tools.Constants;
import com.voucher.manage.tools.Md5;

@Controller
@RequestMapping("/campus")
public class CampusController {
	private CampusService campusService;


	public CampusService getCampusService() {
		return campusService;
	}

	@Autowired
	public void setCampusService(CampusService campusService) {
		this.campusService = campusService;
	}

	/**
	 * 閼惧嘲褰囬弽鈥冲隘
	 * @param limit
	 * @param page
	 * @return
	 */
	@RequestMapping("getAllCampus")
	public @ResponseBody JSONArray getAllCampus(HttpServletRequest request) {    //此处应传递city_id参数
		
		String type,cityId; 
		List<Campus> campus;
	   /*
		Cookie[] cookies = request.getCookies();  
			if(cookies!=null){
				for(Cookie i:cookies){
					if(i.getName().equalsIgnoreCase("type"))
						 type=Integer.parseInt(i.getValue());
				}
			}
		*/
		
		HttpSession session=request.getSession();  
		type=session.getAttribute("type").toString(); //取得session的type变量，判断是否为公众号管理员
		try{
		 cityId=session.getAttribute("cityId").toString();
		}catch(Exception ex){
			cityId=null;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityId", cityId);
				
		if(type.equals("0")){
		  campus = campusService.getAllCampus(paramMap);
		 }else {
		   campus=campusService.getAllFullCampus(paramMap);
		 }
		JSONArray array = JSON.parseArray(JSON.toJSONStringWithDateFormat(
				campus, "HH:mm:ss"));//yyyy-MM-dd HH:mm:ss
		return array;
	}
	
	/**
	 * 閼惧嘲褰囬惄绋跨安閻ㄥ嫭鐗庨崠鍝勬嫲閸╁骸绔�
	 * @return
	 */
	@RequestMapping("/getCampusAndCity")
	public @ResponseBody Map<String,Object> getCampusByCity(){
		Map<String,Object> resultMap=new HashMap<String,Object>();

		try {
			Map<String,Object> paramMap=new HashMap<String,Object>();	 
		    
	    	resultMap.put(Constants.STATUS, Constants.SUCCESS);
	    	resultMap.put(Constants.MESSAGE, "閼惧嘲褰囬弽鈥冲隘閹存劕濮�");
		   
		} catch (Exception e) {
			e.getStackTrace();
			resultMap.put(Constants.STATUS, Constants.FAILURE);
	    	resultMap.put(Constants.MESSAGE, "閼惧嘲褰囬弽鈥冲隘婢惰精瑙﹂敍锟�");
		}
		return resultMap;
	}
	
	/**
	 * @param campusName
	 * 閺嶈宓侀弽鈥冲隘閸氬秷骞忛崣鏍ㄧ墡閸栫瘨d
	 */
	
	@RequestMapping("/getIdByName")
	public @ResponseBody Map<String,Object> getIdByName(@RequestParam String campusName){
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			Map<String,Object> paramMap=new HashMap<String,Object>();	
			paramMap.put("campusName", campusName.trim());
			Integer campusId=campusService.getIdByName(paramMap);
			map.put(Constants.STATUS, Constants.SUCCESS);
	    	map.put(Constants.MESSAGE, "閼惧嘲褰囬弽鈥冲隘Id閹存劕濮涢敍锟�");
			map.put("campusId", campusId);	
			
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
	    	map.put(Constants.MESSAGE, "閼惧嘲褰囬弽鈥冲隘Id婢惰精瑙﹂敍锟�");
		}
		
		return map;
	}
	

	//selectByPrimaryKey
	/**
	 * @param campusName
	 * 閺嶈宓侀弽鈥冲隘id閼惧嘲褰囬弽鈥冲隘
	 */
	
	@RequestMapping("/getCampusById")
	public @ResponseBody Map<String,Object> getCampusById(@RequestParam Integer campusId){
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			Map<String,Object> paramMap=new HashMap<String,Object>();	
			paramMap.put("campusId", campusId);
			Campus campus=campusService.getCampusById(paramMap);
			map.put(Constants.STATUS, Constants.SUCCESS);
	    	map.put(Constants.MESSAGE, "閼惧嘲褰囬弽鈥冲隘閹存劕濮涢敍锟�");
			map.put("campus", JSON.toJSON(campus));	
			
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
	    	map.put(Constants.MESSAGE, "閼惧嘲褰囬弽鈥冲隘婢惰精瑙﹂敍锟�");
		}
		
		return map;
	}
	
	@RequestMapping("getCampusIdByAdmin")
	public @ResponseBody Map<String, Object> getCampusIdByAdmin(@RequestParam String campusAdminName){
		Map<String,Object> responseMap = new HashMap<String,Object>();
		Map<String,Object> requestMap = new HashMap<String,Object>();

		requestMap.put("campusAdmin", campusAdminName);
		
		CampusAdmin campusAdminInfo = campusService.getCampusIdByAdmin(requestMap);
		
		responseMap.put("CampusAdmin", campusAdminInfo);
		
		return responseMap;
	}
	
	/**
	 * 鏉╂柨娲栭弽鈥冲隘缁狅紕鎮婇崨锟�
	 * @return
	 */
	@RequestMapping("getAllCampusAdmin")
	public @ResponseBody JSONArray getAllCampusAdmin(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", 0);
		
		List<CampusAdmin> campusAdmins = campusService.getAllCampusAdmin(paramMap);
		JSONArray array = JSON.parseArray(JSON.toJSONStringWithDateFormat(campusAdmins, "yyyy-MM-dd"));
		
		return array;
	}
	
	/**
	 * 娣囶喗鏁奸弽鈥冲隘缁狅紕鎮婇崨锟�
	 * @param campusId
	 * @param campusAdminName
	 * @return
	 */
	@RequestMapping("/updateCampusAdmin")
	public @ResponseBody Map<String, Object> updateCampusAdmin(@RequestParam String campusName, @RequestParam String campusAdminName){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		Map<String,Object> tempMap = new HashMap<String, Object>();
		tempMap.put("campusName", campusName);
		
		paramMap.put("campusId", campusService.getIdByName(tempMap));
		paramMap.put("campusAdmin", campusAdminName);
		
		Integer result = campusService.updateCampusAdmin(paramMap);
		
		if(result==0||result==-1){
			//濞屸剝娲块弬鐗堝灇閸旓拷
			responseMap.put(Constants.STATUS, Constants.FAILURE);
			responseMap.put(Constants.MESSAGE, "娣囶喗鏁奸弽鈥冲隘閻ㄥ嫮顓搁悶鍡楁喅婢惰精瑙﹂敍锟�");
		}else{
			responseMap.put(Constants.STATUS, Constants.SUCCESS);
			responseMap.put(Constants.MESSAGE, "娣囶喗鏁奸弽鈥冲隘閻ㄥ嫮顓搁悶鍡楁喅閹存劕濮涢敍锟�");
		}
		
		return responseMap;
	}
	
	
	/**
	 * 閸掔娀娅庨弽鈥冲隘閻ㄥ嫭鐓囬弽鈥冲隘缁狅紕鎮婇崨锟�
	 * @param campusId
	 * @param campusAdminName
	 * @return
	 */
	@RequestMapping("/deleteCampusAdmin")
	public @ResponseBody Map<String, Object> deleteCampusAdmin(@RequestParam Integer campusId, @RequestParam String campusAdminName){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("campusId", campusId);
		paramMap.put("campusAdmin", campusAdminName);
		
		Integer result = campusService.deleteCampusAdmin(paramMap);
		if(result!=0&&result!=-1){
			responseMap.put(Constants.STATUS, Constants.SUCCESS);
			responseMap.put(Constants.MESSAGE, "閸掔娀娅庨幋鎰");
		}else{
			responseMap.put(Constants.STATUS, Constants.FAILURE);
			responseMap.put(Constants.MESSAGE, "閸掔娀娅庢径杈Е");
		}
		return responseMap;
	}

	/**
	 * 濞ｈ濮為弽鈥冲隘缁狅紕鎮婇崨锟�
	 * @param campusId
	 * @param campusName
	 * @param campusAdminName
	 * @return
	 */
	@RequestMapping("addCampusAdmin")
	public @ResponseBody Map<String, Object> addCampusAdmin(@RequestParam String campusName, @RequestParam String campusAdminName, @RequestParam String password){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("campusName", campusName);
		
		paramMap.put("campusId", campusService.getIdByName(tempMap));
		paramMap.put("campusName", campusName);
		paramMap.put("campusAdmin", campusAdminName);
		paramMap.put("password", Md5.GetMD5Code(password));
		paramMap.put("type", 0);			//閸欘亣鍏樺ǎ璇插閺嶁�冲隘缁狅紕鎮婇崨姗堢礉閹粯鐗庨崠铏诡吀閻炲棗鎲抽崣顏囧厴娴犲孩鏆熼幑顔肩氨濞ｈ濮為敍灞炬纯缁楋箑鎮庨柅鏄忕帆
		
		Integer result = campusService.addCampusAdmin(paramMap);
		
		if(result!=0&&result!=-1){
			responseMap.put(Constants.STATUS, Constants.SUCCESS);
			responseMap.put(Constants.MESSAGE, "濞ｈ濮為弽鈥冲隘缁狅紕鎮婇崨妯诲灇閸旂噦绱濈拠宄板挤閺冭泛鐨㈢拹锔藉煕閸掑棙娣崇紒娆戞祲鎼存柧姹夐崨妯鸿嫙閹绘劙鍟嬫禒锟�/婵傞�涙叏閺�鐟扮槕閻拷");
		}else{
			responseMap.put(Constants.STATUS, Constants.FAILURE);
			responseMap.put(Constants.MESSAGE, "濞ｈ濮為弽鈥冲隘缁狅紕鎮婇崨妯恒亼鐠愶拷");
		}
		return responseMap;
	}
	
}
