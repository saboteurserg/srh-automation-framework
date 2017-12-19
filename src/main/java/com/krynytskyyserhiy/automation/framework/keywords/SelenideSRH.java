package com.krynytskyyserhiy.automation.framework.keywords;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.*;

/**
 * Created by serhiy.krynytskyy on 13.12.2016.
 */
public class SelenideSRH extends Selenide {

    public static By __(String selector, String... parameters){

        for (String parameter : parameters)
            selector = selector.replaceFirst("%s",parameter);

        if (selector.startsWith("xpath="))
            return byXpath(selector.replaceFirst("xpath=",""));

        if (selector.startsWith("css="))
            return byCssSelector(selector.replaceFirst("css=",""));

        if (selector.startsWith("//"))
            return byXpath(selector);

        if (selector.startsWith("./"))
            return byXpath(selector);

        if (selector.startsWith("id="))
            return byId(selector.replaceFirst("id=",""));

        if (selector.startsWith("name="))
            return byName(selector.replaceFirst("name=",""));

        if (selector.startsWith("text="))
            return byText(selector.replaceFirst("text=",""));

        if (selector.startsWith("linkText="))
            return byLinkText(selector.replaceFirst("linkText=",""));

        if (selector.startsWith("className="))
            return byClassName(selector.replaceFirst("className=",""));

        if (selector.startsWith("class="))
            return byClassName(selector.replaceFirst("class=",""));

        return byCssSelector(selector);
    }


    public static SelenideElement $(String generalSelector) {
        return Selenide.$(__(generalSelector));
    }

    public static SelenideElement $(String generalSelector, String... parameters) {
        return Selenide.$(__(generalSelector, parameters));
    }

    public static SelenideElement $(String geeralSelector, int index) {
        return Selenide.$(__(geeralSelector ), index);
    }

    public static SelenideElement $(String generalSelector, int index, String... parameters) {
        return Selenide.$(__(generalSelector,parameters), index);
    }


    public static ElementsCollection $$(String generalSelector) {
        return  Selenide.$$(__(generalSelector));
    }

//    public static ElementsCollection $$(String generalSelector, String parameters) {
//        return  Selenide.$$(__(generalSelector, parameters));
//    }

    public static ElementsCollection $$(String generalSelector, String... parameters) {
        return  Selenide.$$(__(generalSelector, parameters));
    }



    public static List<String> extractParameters_ByComparingTwoSelectors(String selectorString_withPlaceholders, String selectorString_withParameters){

        List<String> res = new ArrayList<String>();

        if(selectorString_withPlaceholders.equals(selectorString_withParameters))
            return res;

        String[] splitted = selectorString_withPlaceholders.split("%s");

        String replaced = selectorString_withParameters;
        for (int i = 0; i < splitted.length; i++) {
            replaced = StringUtils.replace(replaced, splitted[i], "%%%", 1);
        }

        if(! replaced.equals(selectorString_withParameters) ){
            String[] diffs = replaced.split("%%%");

            for (int i = 0; i < diffs.length; i++) {
                if( !diffs[i].equals("") )
                    res.add(diffs[i]);
            }
        }

        return res;
    }



}
