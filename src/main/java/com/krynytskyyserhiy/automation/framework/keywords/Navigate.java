package com.krynytskyyserhiy.automation.framework.keywords;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.krynytskyyserhiy.automation.framework.testng.SRHTestngListener;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static com.codeborne.selenide.Selenide.close;

/**
 * Created by serhiy.krynytskyy on 12.10.2016.
 */
public class Navigate {

        public synchronized static void open(String relativeOrAbsoluteUrl) {
        closeBrowserIfNeeded();
        Selenide.open(relativeOrAbsoluteUrl);
    }

    public synchronized static void open(URL absoluteUrl) {
        closeBrowserIfNeeded();
        Selenide.open(absoluteUrl);
    }

    public synchronized static void open(String relativeOrAbsoluteUrl, String domain, String login, String password) {
        closeBrowserIfNeeded();
        Selenide.open(relativeOrAbsoluteUrl, domain, login, password);
    }

    public synchronized static void open(URL absoluteUrl, String domain, String login, String password) {
        closeBrowserIfNeeded();
        Selenide.open(absoluteUrl, domain, login, password);
    }


    public synchronized static <PageObjectClass> PageObjectClass open(String relativeOrAbsoluteUrl, Class<PageObjectClass> pageObjectClassClass) {
        closeBrowserIfNeeded();
        return Selenide.open(relativeOrAbsoluteUrl, pageObjectClassClass);
    }

    public synchronized static <PageObjectClass> PageObjectClass open(URL absoluteUrl, Class<PageObjectClass> pageObjectClassClass) {
        closeBrowserIfNeeded();
        return Selenide.open(absoluteUrl,  pageObjectClassClass);
    }

    public synchronized static <PageObjectClass> PageObjectClass open(String relativeOrAbsoluteUrl, String domain, String login, String password, Class<PageObjectClass> pageObjectClassClass) {
        closeBrowserIfNeeded();
        return Selenide.open(relativeOrAbsoluteUrl, domain, login, password, pageObjectClassClass);
    }

    public synchronized static <PageObjectClass> PageObjectClass open(URL absoluteUrl, String domain, String login, String password, Class<PageObjectClass> pageObjectClassClass) {
        closeBrowserIfNeeded();
        return Selenide.open(absoluteUrl, domain, login, password, pageObjectClassClass);
    }



    private synchronized static void closeBrowserIfNeeded(){

        String currentBrowser = null;

        if(WebDriverRunner.hasWebDriverStarted()){

            WebDriver wd = WebDriverRunner.getWebDriver();

            if( wd != null) {
                Capabilities capabilities = ((RemoteWebDriver) WebDriverRunner.getWebDriver()).getCapabilities();
                currentBrowser = capabilities.getBrowserName();
            }
        }

        if(currentBrowser == null){
            Configuration.browser = Parameter.get("browser", SRHTestngListener.DEFAULT_BROWSER);
        }else if(Parameter.get("browser", SRHTestngListener.DEFAULT_BROWSER) != null && ! Parameter.get("browser", SRHTestngListener.DEFAULT_BROWSER).equals(currentBrowser) ) {
            Configuration.browser = Parameter.get("browser");
            close();
        }
    }

}
