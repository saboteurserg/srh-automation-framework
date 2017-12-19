package com.krynytskyyserhiy.automation.framework.keywords;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * Created by serhiy.krynytskyy on 28.10.2016.
 */
public class Wait {

    public static boolean tillDisappear(SelenideElement element, int maxWaitTime){

        long timeBegin = System.currentTimeMillis();

        if ( element.is( visible)  ){

            if( (System.currentTimeMillis() - timeBegin)  > maxWaitTime)
                return false;

            while (true){
                sleep(200);
                if( element.is(not(visible)) )
                        return true;
            }
        }

        return false;
    }

    public static boolean waitForElementCondition_NoFailing(SelenideElement element, Condition condition, int maxWaitTime){

        long timeBegin = System.currentTimeMillis();

        if(element.is(condition))
            return true;

        while (true){
            if( element.is(condition) )
                return true;

            if( (System.currentTimeMillis() - timeBegin)  > maxWaitTime)
                return false;

            sleep(200);
        }
    }


}
