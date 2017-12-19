package samples;

import com.krynytskyyserhiy.automation.framework.keywords.SSH;
import com.jcraft.jsch.JSchException;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by serhiy.krynytskyy on 25.11.2016.
 */
public class SSHTest {

    @Test
    public void test() throws JSchException, IOException {
        new SSH()
            .connect("hfqalnxvm03","glassfish","glassfish")
            .execute("cd glassfish3/bin/ && asadmin stop-domain domain1")
            .printLastResultOutput()
            .disconnect();
    }

}
