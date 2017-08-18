import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import vcode.dao.JedisClient;

/**
 * Created by hubai on 2017/7/19.
 */
@RunWith(JUnit4ClassRunner.class)
public class RedisTest {
    @Autowired
    JedisClient jedisClient;
    @Test
    public void test1(){
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.set("test1","test");
        System.out.print(jedis.ping());

    }
    @Test
    public void test2(){
        //String token = jedisClient.get("token");
        Jedis jedis = new Jedis("127.0.0.1");
        String token = jedis.get("token");
        System.out.print(token);
    }
}
