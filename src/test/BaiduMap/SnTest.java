package BaiduMap;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSONObject;


public class SnTest {
	private static final String ak="jc9mqGeeOokCjqEEEtwYUL2wwjT1Iyfm";
	private static final String sk="p5TY7ErHMdAZ1XKKEmu8nRKGwQZw0CKE";
	
    public static void main(String[] args) throws Exception {
        SnTest snTest = new SnTest();
        snTest.testGet();
      //  snTest.testPost();
    }

    public JSONObject testGet() throws Exception {
        /**
         * 以http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=yourak为例
         * ak设置了sn校验不能直接使用必须在url最后附上sn值，get请求计算sn跟url中参数对出现顺序有关，需按序填充paramsMap，
         * post请求是按字母序填充，具体参照testPost()
         */
    	
    	String address="百度大厦";
    	
        Map paramsMap = new LinkedHashMap<String, String>();
       /* paramsMap.put("address", address);
        paramsMap.put("output", "json");
        paramsMap.put("ak", ak);
       */
        
        paramsMap.put("query", "银行");
        paramsMap.put("page_size", "10");
        paramsMap.put("page_num", "0");
        paramsMap.put("scope", "1");
        paramsMap.put("region", "泸州");
        paramsMap.put("output", "json");
        
        paramsMap.put("ak", ak);
        // 调用下面的toQueryString方法，对paramsMap内所有value作utf8编码
        String paramsStr = toQueryString(paramsMap);

        // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk
        //String wholeStr = new String("/geocoder/v2/?" + paramsStr + sk);
        String wholeStr = new String("/place/v2/search?" + paramsStr + sk);
        
        // 对上面wholeStr再作utf8编码
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

        // 调用下面的MD5方法得到sn签名值
        String sn = MD5(tempStr);

        // 算得sn后发送get请求
        HttpClient client = new DefaultHttpClient();
     /*  HttpGet httpget = new HttpGet(
                "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=jc9mqGeeOokCjqEEEtwYUL2wwjT1Iyfm&sn="
                        + sn);*/
        HttpGet httpget = new HttpGet("http://api.map.baidu.com/place/v2/search?query=银行&page_size=10"
        		+ "&page_num=0&scope=1&region=泸州&output=json&ak="+ak+"&sn="+sn);
        HttpResponse response = client.execute(httpget);
        InputStream is = response.getEntity().getContent();
        String result = inStream2String(is);
        JSONObject jsonObject=JSONObject.parseObject(result);
  
        // 打印响应内容        
        System.out.println(jsonObject);
        
        return jsonObject;
    }

  
 // 对Map内所有value作utf8编码，拼接返回结果
    public static String toQueryString(Map<?, ?> data)
                    throws UnsupportedEncodingException {
            StringBuffer queryString = new StringBuffer();
            for (Entry<?, ?> pair : data.entrySet()) {
                    queryString.append(pair.getKey() + "=");
                    queryString.append(URLEncoder.encode((String) pair.getValue(),
                                    "UTF-8") + "&");
            }
            if (queryString.length() > 0) {
                    queryString.deleteCharAt(queryString.length() - 1);
            }
            return queryString.toString();
    }
    
    // MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    // 将输入流转换成字符串
    private static String inStream2String(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        while ((len = is.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        return new String(baos.toByteArray(), "UTF-8");
    }
}
