package samples;

import com.krynytskyyserhiy.automation.framework.keywords.Log;
import org.testng.annotations.Test;

import static com.krynytskyyserhiy.automation.framework.keywords.Navigate.open;

public class Test5 {

    @Test
    public void testPO() throws Exception {

        Log.info("test1");
        Log.info("test1");
        Log.trace("trace example");
        Log.info("test1");

        throw new Exception("<bold>asdfladksf</bold>");

//        open("http://www.google.com");
//        new POTest2().search("nice girls");
    }


    @Test
    public void testPO1() throws Exception {

        Log.info("test2");
        Log.info("test2");
        Log.trace("trace example");
        Log.info("test2");

//        ExtentReports extent = ExtentTestManager.getExtent();
//
//        ExtentTest test = ExtentTestManager.getTest();
//
//        ExtentTest child = extent.startTest("Child1");
//
//        if (test != null) {
//            test.appendChild(child);
//
//            child.log(LogStatus.INFO, escapeHtml("bla bla bla") + "<br>");
//
//            test.log(LogStatus.INFO, escapeHtml("test9999") + "<br>");
//        }


//        open("http://www.google.com");
//        new POTest2().search("nice girls");
    }


    @Test
    public void test3() throws Exception {

        Log.info("test1");
        Log.info("test1");
        Log.trace("trace example");
        Log.info("test1");

        open("http://www.google.com");
        new POTest2().search("nice girls");
    }


}
