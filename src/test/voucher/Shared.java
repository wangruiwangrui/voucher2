package voucher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Shared {

    public static void main(String arg[]) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Shared s1 = new Shared();

        Object obj[] = new Object[2];
        obj[0] = "object1";
        obj[1] = "object2";
        s1.testParam(null, obj);

        Class param[] = new Class[2];
        param[0] = String.class;
        param[1] = Object[].class; //// how to define the second parameter as array
        Method testParamMethod = s1.getClass().getDeclaredMethod("testParam", param);
       // testParamMethod.invoke("", obj); ///// here getting error
        testParamMethod.invoke(s1, "", obj);
    }

    public void testParam(String query,Object ... params){
        System.out.println("in the testparam method");
    }

}
