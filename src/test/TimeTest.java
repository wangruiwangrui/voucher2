import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTest {
	public static void main(String[] args) throws ParseException {
		/*
		 * String dj ="1.000000"; String sl = "0.13"; //不含税单价=含税单价/1+税率; 税额=不含税单价* 数量 *
		 * 税率 Float sl1 = Float.parseFloat(sl); Float dj1 = Float.parseFloat(dj); Float
		 * dj2 = (float) (dj1/1.0)+sl1; System.out.println(dj2); Float se = dj2*1*sl1;
		 * System.out.println(se); DecimalFormat fnum = new DecimalFormat("##0.00");
		 * String dd=fnum.format(se); System.out.println(dd);
		 * 
		 * BigDecimal ddBigDecimal = new BigDecimal(40);
		 * System.out.println(ddBigDecimal.divide(ddBigDecimal)); float floatValue =
		 * ddBigDecimal.floatValue(); System.out.println(floatValue);
		 */
		
		/*
		 * String startDate = "2016-04-30 00:00:00"; SimpleDateFormat sdf = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date dateD=null; try { dateD =
		 * sdf.parse(startDate); } catch (ParseException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */
		/*
		 * String string = new SimpleDateFormat("yyyy-MM-dd 00:00:00.000").format(new
		 * Date()).toString(); DateFormat format1 = new
		 * SimpleDateFormat("yyyy-MM-dd 00:00:00.000"); Date dateD =
		 * format1.parse(string); Date dataDate = new Date(); System.out.println(dateD);
		 */
 
		Object j = 0.01;
		 int hire = (int) ((Float.valueOf(j.toString()))* 100);
			String total_fee2 = String.valueOf(String.valueOf(hire));
			
			BigDecimal  b  =  new  BigDecimal(Float.parseFloat(total_fee2)/100);  
			float  total_fee =  b.setScale(2,  BigDecimal.ROUND_HALF_UP).floatValue();
		System.out.println(String.valueOf(total_fee));
	}
}
