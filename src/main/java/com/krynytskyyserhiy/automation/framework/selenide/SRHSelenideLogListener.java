package com.krynytskyyserhiy.automation.framework.selenide;

import com.krynytskyyserhiy.automation.framework.common.ClassHelper;
import com.krynytskyyserhiy.automation.framework.keywords.Log;
import com.krynytskyyserhiy.automation.framework.keywords.Parameter;
import com.krynytskyyserhiy.automation.framework.keywords.SelenideSRH;
import com.krynytskyyserhiy.automation.framework.pageobject.SRHPageObject;
import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.LogEventListener;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.krynytskyyserhiy.automation.framework.keywords.SelenideSRH.__;

/**
 * Created by serhiy.krynytskyy on 24.10.2016.
 */
public class SRHSelenideLogListener implements LogEventListener {

    public void onEvent(LogEvent logEvent) {

        String elementSelectorValue = logEvent.getElement();

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        if( Parameter.getAsBool("variableNames") ) {

            outer:
            for (int i = 0; i < stackTraceElements.length; i++) {
                String className = stackTraceElements[i].getClassName();

                try {
                    Class<?> clsPO = Class.forName(className);

                    if (ClassHelper.isClassAChildOfClass(clsPO, SRHPageObject.class)) {
                        try {
                            Class<?> cls = Class.forName(className);
                            Field[] fields = cls.getFields();
                            for (int j = 0; j < fields.length; j++) {

                                Object fieldValue_ = fields[j].get(null);
                                if (fieldValue_ == null && !(fieldValue_ instanceof String))
                                    continue;

                                String fieldValue = __(fieldValue_ + "") + "";

                                if (!elementSelectorValue.contains("By."))
                                    elementSelectorValue = __(elementSelectorValue) + "";

                                //handle [n]
                                String nthRes[] = removeNthFromString(elementSelectorValue);
                                elementSelectorValue = nthRes[0];

                                //filter info
                                String filterRes[] = removeFilterInfoFromString(elementSelectorValue);
                                elementSelectorValue = filterRes[0];


                                List<String> params = SelenideSRH.extractParameters_ByComparingTwoSelectors(fieldValue, elementSelectorValue);

                                for (int k = 0; k < params.size(); k++)
                                    fieldValue = StringUtils.replace(fieldValue, "%s", params.get(k), 1);

                                if (elementSelectorValue.equals(fieldValue)) {

                                    String selectorPure = Parameter.getAsBool("traceSelectors") ? " (" + elementSelectorValue + ")" : "";

                                    elementSelectorValue = cls.getSimpleName() + "." + fields[j].getName() + selectorPure;

                                    //filterInfo
                                    String filters = removeFilterInfoFromString(logEvent.getElement())[1];
                                    if(filters!="")
                                        elementSelectorValue += " "+filters;

                                    //nths
                                    String nth = removeNthFromString(logEvent.getElement())[1];
                                    if(nth!="")
                                        elementSelectorValue += " "+nth;



                                    break outer;
                                }
                            }
                        } catch (ClassNotFoundException e) {

                        } catch (IllegalAccessException e) {

                        } catch (NullPointerException e) {

                        }
                    }

                } catch (ClassNotFoundException e) {
                }
            }
        }

        String s = logEvent.getStatus() + ": " + logEvent.getSubject() + " -> \"" + elementSelectorValue + "\"";
        Log.trace(s);
    }


    private String[] removeNthFromString(String s){
        String[] res = {s,""};

        String regex = "(\\[\\d+\\])";

        res[0] = s.replaceFirst(regex,"");

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            res[1] = matcher.group(1);
        }

        return res;
    }


    private String[] removeFilterInfoFromString(String s){
        String[] res = {s,""};

        String regex = "\\.filter\\(.*\\)";

        res[0] = s.replaceFirst(regex,"");

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            res[1] = matcher.group(0);
        }

        return res;
    }

}
