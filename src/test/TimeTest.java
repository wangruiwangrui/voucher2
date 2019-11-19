import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTest {
	public static void main(String[] args) {
		String dj ="1.000000";
		String sl = "0.13";
		//不含税单价=含税单价/1+税率; 税额=不含税单价* 数量 * 税率 
		Float sl1 = Float.parseFloat(sl);
		Float dj1 = Float.parseFloat(dj);
		Float dj2 = (float) (dj1/1.0)+sl1;
		System.out.println(dj2);
		Float se = dj2*1*sl1;
		System.out.println(se);
		DecimalFormat fnum = new DecimalFormat("##0.00");  
		String dd=fnum.format(se);      
		System.out.println(dd); 
		  
		BigDecimal ddBigDecimal = new BigDecimal(40);
		System.out.println(ddBigDecimal.divide(ddBigDecimal));
		float floatValue = ddBigDecimal.floatValue();
		System.out.println(floatValue);
		  
	}
}
