package samples;

import com.krynytskyyserhiy.automation.framework.keywords.Log;
import com.krynytskyyserhiy.automation.framework.keywords.SelenideSRH;
import com.codeborne.selenide.testng.GlobalTextReport;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

//@Listeners({BrowserPerClass.class})
//@Listeners({ SoftAsserts.class, GlobalTextReport.class})
//@Listeners({ TextReport.class})
//@Listeners({GlobalTextReport.class})
@Listeners(GlobalTextReport.class)
public class Test4 {


    public static final String tabs2 = ".hdtb-mitem";


    @Test(groups = "g1")
    public void testMethod1() throws Exception {

        Log.debug("debug info");

        open("http://google.com");

        SelenideSRH.$("//%s","a");

        $("[name=q]")
                .val("Elvis")
                .pressEnter();
        String first_link = ".r>a";
        $(first_link).shouldBe(visible);
        $(first_link).waitUntil(appear,10000).click();
        Log.info("test...");
        Log.makeScreenshotOfElement($(".infobox .image>img"),"Elvis picture");
        Log.makeScreenshot("Whole page screenshot");



//        assertThat(false).isTrue();
//        throw new Exception("sdfa");
    }




//    @Test (groups = "g2")
//    public void m1()  {
//
//        Log.info("CURRENT THREAD = " + Thread.currentThread().getId());
//        Log.info( "Browser in config: " + Parameter.get("browser") );
//
//        open("http://google.com");
//
//        String searchTerm = Parameter.get("customSearch");
//
//        $(By.name("q")).val(searchTerm).pressEnter();
//        $(".hdtb-imb:nth-child(2)").waitUntil(appear,10000).click();
//        $("#rg img").waitUntil(appear,10000);
//        Log.makeScreenshot("");
//    }

/*
    @Test
    public void m2()  {

        Log.info("CURRENT THREAD = " + Thread.currentThread().getId());
        Log.info( "Browser in config: " + Parameter.get("browser") );
        Log.info( "Browser in config: " + Parameter.get("customSearch") );

        Parameter.setMethodUniqeParam("browser_99","firefox_99");
        Log.info( "Browser in config99: " + Parameter.get("browser_99") );


//        Configuration.browser = "firefox";
        open("http://yandex.ru");
        Log.insertPicture("Screenshot",screenshot(new Random().toString()));

    }



    @Test
    public void m3()  {
//        Parameter.setMethodUniqeParam("browser","firefox");

        Log.info("CURRENT THREAD = " + Thread.currentThread().getId());
        Log.info( "Browser in config: " + Parameter.get("browser") );
        Log.info( "Browser in config: " + Parameter.get("customSearch") );

//        Configuration.browser = "firefox";
        open("http://motanka.com");
        Log.insertPicture("Screenshot",screenshot(new Random().toString()));
    }
*/


//    @Test
//    public void testMethod1()  {
//
//        Configuration.remote = Parameter.get("remote");
//
////        if (Configuration.browser == "chrome")
////            Configuration.browser ="firefox";
////        else
//            Configuration.browser ="chrome";
//
//        Log.info("CURRENT THREAD = " + Thread.currentThread().getId());
//
//        File file = new File("src/main/resources/drivers/chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
//
////        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
//
//        Log.info( "Browser in config: " + Parameter.get("browser") );
//
//        String searchTerm = Parameter.get("customSearch");
//
////        WebDriverRunner.getAndCheckWebDriver();
////        open("http://google.com");
//        OpenUrl("http://google.com");
//        $(By.name("q")).val(searchTerm).pressEnter();
//        $(".hdtb-imb:nth-child(2)").waitUntil(appear,10000).click();
//        $("#rg img").waitUntil(appear,10000);
//        Log.insertPicture("Screenshot",$("#lst-ib").waitUntil(appear,10000).screenshot().getAbsolutePath());
//        Log.insertPicture("Screenshot",screenshot(new Random().toString()));
//
////        WebDriverRunner.closeWebDriver();
////        close();
//    }


//    @Test
//    public void testMethod2()  {
//
////        Configuration.browser="firefox";
//
//
//        Log.info("CURRENT THREAD = " + Thread.currentThread().getId());
//
//        File file = new File("src/main/resources/drivers/chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
//
////        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
//
//        Log.info( "" + Parameter.get("customSearch") );
//
//        String searchTerm = Parameter.get("customSearch");
//
//        open("http://google.com");
//        $(By.name("q")).val(searchTerm).pressEnter();
//        $(".hdtb-imb:nth-child(2)").waitUntil(appear,10000).click();
//        $("#rg img").waitUntil(appear,10000);
//        Log.insertPicture("Screenshot",$("#lst-ib").waitUntil(appear,10000).screenshot().getAbsolutePath());
//        Log.insertPicture("Screenshot",screenshot(new Random().toString()));
//
////        WebDriverRunner.closeWebDriver();
////        close();
//    }


//
//    public synchronized void OpenUrl(String url, String browser){
//        Configuration.browser = browser;
//        open(url);
//    }





}
