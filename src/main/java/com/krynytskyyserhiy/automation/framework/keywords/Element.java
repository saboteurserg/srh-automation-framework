package com.krynytskyyserhiy.automation.framework.keywords;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.executeJavaScript;

/**
 * Created by serhiy.krynytskyy on 06.12.2016.
 */
public class Element {

    public static void setElementAttribute(WebElement element, String attributeName, String attributeValue){
        executeJavaScript("arguments[0].setAttribute(arguments[1], arguments[2]);",element,attributeName,attributeValue);
    }

    public static void setElementAttribute(SelenideElement element, String attributeName, String attributeValue){
        executeJavaScript("arguments[0].setAttribute(arguments[1], arguments[2]);",element.getWrappedElement(),attributeName,attributeValue);
    }

    public static void scrollToElement(SelenideElement element){
        executeJavaScript("arguments[0].scrollIntoView()", element);
    }

}
