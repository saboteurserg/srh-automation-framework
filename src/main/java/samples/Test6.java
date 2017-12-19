package samples;

import com.krynytskyyserhiy.automation.framework.keywords.Log;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

public class Test6 {

    @BeforeClass
    public void beforeClass() throws Exception {
//        throw new Exception("<bold>Failing test</bold>");
    }


    @Test
    public void test1() throws Exception {
        open("http://google.com");
        throw new Exception("<bold>Failing test</bold>");
    }


    @Test(groups = {"Group1"})
    public void test2() throws Exception {
//        throw new Exception("<bold>Skipping test</bold>");
    }

    @Test(groups = {"Group2"})
    public void test2_1() throws Exception {
        com.krynytskyyserhiy.automation.framework.keywords.Test.skipTest();
    }


    @Test(dependsOnMethods = {"test1", "test2"}, groups = {"Group1"})
    public void test3() throws Exception {
        Log.info("This is skipped test");
    }


}
