package com.voucher.manage.tools.KMeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.voucher.manage.tools.MyTestUtil;

public class Grid {
	
	private static int dimension = 2;
	
	public Map get(Map map) {
		double latmax = (double) map.get("latmax");
		double lngmax = (double) map.get("lngmax");


		double nmm = (1.141255544679108e-5) * 50;
		double amm = (8.993216192195822e-6) * 50;


		List list=new ArrayList<>();

		Map<Integer,List> map2=new HashMap<>();
		
		List names=(List) map.get("names");
		
		Iterator<ArrayList<Object>> iterator=names.iterator();
		
		while (iterator.hasNext()) {
			ArrayList<Object> cList=iterator.next();
			double clng=(double) cList.get(0);
			double clat=(double) cList.get(1);
			
			int n=(int) ((lngmax-clng)/nmm);
			int a=(int) ((latmax-clat)/amm);
			String aa=String.valueOf(n)+String.valueOf(a);
			int na=Integer.parseInt(aa);
			ArrayList caList=(ArrayList) map2.get(na);
			if(caList==null){
				caList=new ArrayList<>();
			}
			caList.add(cList);			
			map2.put(na, caList);
			
		}
		
		Map map4=new HashMap();
		
		List list2=new ArrayList<>();
		for (Map.Entry<Integer, List> entry : map2.entrySet()) {
			int i=entry.getKey();
			ArrayList list3 = (ArrayList) entry.getValue();
			List list4=calculateCentroid(list3);
			Map map3=new HashMap();
			map3.put("lng", list4.get(0));
			map3.put("lat", list4.get(1));
			map3.put("count", list4.get(2));
			map3.put("id", i);
			list2.add(map3);
		}
		
		map4.put("map2", map2);
		map4.put("list",list2);
		
		return map4;
	}
	
	private ArrayList<Double> calculateCentroid(ArrayList tData){
		ArrayList<Double> cen=new ArrayList<Double>();
		int i,j;
		for(i=0;i<dimension;i++){
			cen.add(0.0);
		}
		
		int size=tData.size();
		for(i=0;i<size;i++){
			ArrayList<Double> temp=(ArrayList<Double>) tData.get(i);
			for(j=0;j<dimension;j++){
				cen.set(j, (cen.get(j)+temp.get(j)));
			}
		}
		
		for(j=0;j<dimension;j++){
			cen.set(j, (cen.get(j)/size));
		}
		
		cen.add(Double.valueOf(size));
		
		return cen;
	}
	
}
