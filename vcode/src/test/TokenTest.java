import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import vcode.utils.TokenGenerator;

/**
 * Created by hubai on 2017/7/19.
 */
@RunWith(JUnit4ClassRunner.class)
public class TokenTest {
    @Test
    public void TokenTest(){
        String token =TokenGenerator.tokenGenertor();
        System.out.print(token);
    }
}
