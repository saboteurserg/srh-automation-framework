package samples;

import com.krynytskyyserhiy.automation.framework.keywords.Log;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
//import ru.yandex.qatools.allure.annotations.Step;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by serhiy.krynytskyy on 03.08.2016.
 */
public class Test2 {


    @BeforeMethod
    public void beforeTest() {
        Log.log("Before test");
        Log.log("Before test line 2");
        Assert.assertTrue(true);

    }

    @BeforeClass
    public void beforeTest333(){
        Log.log("Before asdfasdfasdf test");

        Assert.assertTrue(true, "as djadskjflas");
    }

//    @Step("{0}")
    private void log(String value) {
        //empty method
    }

//    @Step("sadf asdfasda")
    @Test(invocationCount = 3)
    public void successMethod() throws Exception {

        log("12313");
        log("12313123");
        log("123132435");
        log("123137835673457");

        Log.debug("debug information");
        Log.warn("some warning");

        Log.insertPicture("Screeeenshot <span style='font-weight:bold;'>Yeah</span>", "https://pbs.twimg.com/profile_images/739247958340698114/fVKY9fOv.jpg");

        Log.insertLink("Motanka.com", "http://www.motanka.com/ua/");

        Log.log("<a href='test.txt' target='_blank'>link</a>");

        Reporter.log("Reporter 99999");


        Log.log("Log line looks good!");
        Log.pass("PASSED MESSAGE");

        Log.insertPicture("Screeeenshot 2", "http://www.merchandisingplaza.co.uk/197778/2/Swimsuits-Wonder-Woman-WONDER-WOMAN-Chain-Triangle-Bikini-l.jpg");


//        Log.fail("FAILED MESSAGE");
        assertThat("aaa").as("assert message").contains("a");
        assertThat("aaa").as("assert message").contains("a");

        Log.insertPicture("Screeeenshot 3", "http://www.topmattressreviews.net/data/wallpapers/3/woman_707687.jpg");


        Log.log("Log line looks good!");

//        Assert.assertTrue(false);
    }

    @Test(groups = {"functest"})
    public void successMethod2() throws Exception {

        throw new Exception("asdfa");

//        Log.log("<strong style='color:orange;'>test method 244444</strong>");
//        Assert.assertTrue(true);
    }

    @Test (groups = {"all"}, testName = "Login negative", description = "This tests performs Login page testing by trying wrong credentials")
    public void method3() {
        Assert.assertTrue(false);
    }



}
