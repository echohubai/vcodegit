import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import vcode.utils.EncryptUtils;
import vcode.utils.UuidUtils;

import static vcode.utils.EncryptUtils.md5;

/**
 * Created by hubai on 2017/7/19.
 */
@RunWith(JUnit4ClassRunner.class)
public class EncryptedTest {
    @Test
    public void EncryMD5Test(){
        String uuid = UuidUtils.generateShortUuid();
        String suffix = EncryptUtils.md5("滑动验证Demo");
        String encCode=EncryptUtils.md5("CMB");
        //System.out.print("UUID"+uuid);
        System.out.print("Token:" + encCode);
        System.out.print("\n");
        System.out.print("Length" + encCode.length());

    }
}
