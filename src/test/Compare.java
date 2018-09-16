import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.voucher.manage.daoModel.Assets.Hidden_Check;
import com.voucher.manage.daoModel.Assets.Hidden_Check_Item;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Item_Join;

public class Compare {

    public static <T> Map<String, String> compare(T obj1, T Obj2)
            throws Exception {

        Map<String, String> result = new HashMap<String, String>();

        Field[] fs = obj1.getClass().getDeclaredFields();//获取所有属性
        for (Field f : fs) {
            f.setAccessible(true);//设置访问性，反射类的方法，设置为true就可以访问private修饰的东西，否则无法访问
            Object v1 = f.get(obj1);
            Object v2 = f.get(Obj2);
            result.put(f.getName(), String.valueOf(equals(v1, v2)));
            System.out.println(f.getName());
            if(!f.getName().equals("serialVersionUID")&&!f.getName().equals("check_id")
            		&&!f.getName().equals("other")&&!f.getName().equals("notIn")
            		&&!f.getName().equals("sort")&&!f.getName().equals("order")
            		&&!f.getName().equals("where")&&!f.getName().equals("whereTerm")){
            	String getMethodName = "set" + f.getName().substring(0, 1).toUpperCase()  
                        + f.getName().substring(1);
            	Method getMethod =obj1.getClass().getDeclaredMethod(getMethodName,Integer.class);
            	getMethod.invoke(obj1,1);
            }
        }
        MyTestUtil.print(obj1);
        return result;
    }

    public static boolean equals(Object obj1, Object obj2) {

        if (obj1 == obj2) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }


    public static void main(String[] args) throws Exception {
        Hidden_Check_Item hidden_Check_Item=new Hidden_Check_Item();
        Hidden_Check_Item hidden_Check_Item2=new Hidden_Check_Item();
        
        hidden_Check_Item2.setBlow(1);
        Map<String, String> result = compare(hidden_Check_Item,hidden_Check_Item2);

        System.out.println(result);
        
        MyTestUtil.print(hidden_Check_Item);
    }
}