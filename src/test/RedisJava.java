
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.voucher.manage.tools.MyTestUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
 
public class RedisJava {
    public static void main(String[] args) {
        //���ӱ��ص� Redis ����
    	JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.100.110", 6379, 10000, "redis",1);

        Jedis jedis;
        jedis= jedisPool.getResource();
        //jedis.auth("redis");
        System.out.println("���ӳɹ�");
        //�鿴�����Ƿ�����
        System.out.println("������������: "+jedis.ping());
        
        Set<String> keys=jedis.keys("*");
        System.out.println("keys= "+keys);
        //String map=jedis.get("assetMap");
        //System.out.print(map);
        for(String key:keys){
        	Object value=jedis.get(key);
        	System.out.println(key+" = ");
        	/*if(key.equals("[item_room]_columnName")){
        		jedis.del(key);
        	}*/
        	//jedis.del(key);
        	//MyTestUtil.print(value);
        }

    }
}