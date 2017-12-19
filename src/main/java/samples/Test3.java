package samples;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.krynytskyyserhiy.automation.framework.keywords.Log;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by serhiy.krynytskyy on 20.09.2016.
 */
public class Test3 {

    @FindAll(@FindBy(css = ".hdtb-mitem"))
    public List<WebElement> tabs;

    public final String tabs2 = ".hdtb-mitem";

    @FindBys({
            @FindBy(css = ".hdtb-mitem"),
            @FindBy(css = ".q.qs")    })
    public WebElement tabs2_;

    public final String dataRi = "[data-ri]";


    @Test
    public void testMethod1() throws InterruptedException, IOException {

        Configuration.timeout = 2000;

        Log.log("Test starts");

        open("http://google.com");
        $(By.name("q")).val("pretty girl").pressEnter();
        $(By.name("q")).shouldBe(appear);

        $(By.name("q")).shouldBe(appear);

        PageFactory.initElements(WebDriverRunner.getWebDriver(),this);

//        Thread.sleep(1000);

//        tabs.get(1).click();


        $$(tabs).get(1).click();

//        $(new ByChained(By.cssSelector(".hdgftb-mitem"),By.cssSelector(".q.qs"))).click();

//        $(dataRi,1).waitUntil(appear, 5000);

//        $(dataRi).screenshot().getAbsolutePath();

//        takeScreenShot("sss13434");

        Selenide.screenshot("dasfas");



        File scrFile = ((TakesScreenshot)WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.FILE);
// Now you can do whatever you need to do with it, for example copy somewhere
        FileUtils.copyFile(scrFile, new File("screenshot.png"));

//        Log.insertPicture("Screenshot",$(dataRi).screenshot().getAbsolutePath());
//        Log.insertPicture("Screenshot",takeScreenShot("sss13434"));

        for (int i = 0; i < 3; i++) {
            $(dataRi,i).click();
//            Log.insertPicture("Screenshot", $(".irc_bg").screenshot().getAbsolutePath());
        }


//        Thread.sleep(5000);
//        $$(tabs).get(2).click();

//        $$(tabs).get(1).click();

//        $(By.name("q")).val("chevrolet").sendKeys(Keys.CONTROL+"a");
//        Thread.sleep(500);
//        $(By.name("q")).sendKeys(Keys.CONTROL+"c");
//        Thread.sleep(500);
//        $(By.name("q")).sendKeys("cruze ");
//        Thread.sleep(500);
//        $(By.name("q")).sendKeys(Keys.CONTROL+"v");
//        Thread.sleep(500);
//        $(By.name("q")).pressEnter();


//        Log.insertPicture("Screenshot",takeScreenShot("sss13434677"));

    }

}
