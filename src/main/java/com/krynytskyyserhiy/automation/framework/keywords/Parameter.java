package com.krynytskyyserhiy.automation.framework.keywords;

import com.krynytskyyserhiy.automation.framework.testng.TestHelper;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by serhiy.krynytskyy on 02.08.2016.
 */
public class Parameter {

    static public final String nestedTestMethodsParamName = "nestedMethodTests";

    static private final String FRAMEWORK_GLOBAL_VARIABLE_PREFIX = "srhauto.";

    //test name => {{parameter1, value}, {parameter2, value}, ...}
    static private HashMap<String, HashMap<String, String> > methodParams = new HashMap<String, HashMap<String, String> >();


    public static String getFromGlobalVariable(String paramName){
        return System.getProperty(FRAMEWORK_GLOBAL_VARIABLE_PREFIX + paramName);
    }

    public static String getAsString(String parameterName){
        String res = null;

        String cachedValue = getValueFromCache(parameterName);
        if( cachedValue != null ){
            return cachedValue;
        }

        ITestContext test = TestHelper.getTest();

        String resSuite = null;

        if(test != null){
            resSuite = test.getCurrentXmlTest().getParameter(parameterName);
        }else{
            ISuite currentSuite = TestHelper.getCurrentSuite();
            if (currentSuite!=null)
                resSuite = TestHelper.getCurrentSuite().getParameter(parameterName);
        }

        if(resSuite!=null)
            res = resSuite;

        String global = getFromGlobalVariable(parameterName);
        if (global != null)
            res = global;

        return res;
    }

    public static String get(String parameterName){
        return getAsString(parameterName);
    }

    public static String getAsString(String parameterName, String defaultValue){
        String res = getAsString(parameterName);
        if( res == null )
            return defaultValue;

        return res;
    }

    public static String get(String parameterName, String defaultValue){
        return getAsString(parameterName, defaultValue);
    }


    public static Integer getAsInt(String parameterName){
        return Integer.parseInt(getAsString(parameterName));
    }

    public static Boolean getAsBool(String parameterName){
        return Boolean.parseBoolean(getAsString(parameterName));
    }

    public static Boolean getAsBool(String parameterName, boolean defaultValue){
        String parameterValue = getAsString(parameterName);
        if(parameterValue == null){
            return defaultValue;
        }else {
            return Boolean.parseBoolean(parameterValue);
        }
    }


    public synchronized static void setMethodUniqeParam(String parameterName, String value){
        ITestNGMethod currentRunningMethod = TestHelper.getCurrentRunningMethod();
        String currentRunningMethodName = TestHelper.formCurrentMethodtName(currentRunningMethod);

        HashMap<String, String> currentParams = methodParams.get(currentRunningMethodName);

        if(currentParams == null){
            currentParams = new HashMap<String, String>();
        }
        currentParams.put(parameterName, value);

        methodParams.put(currentRunningMethodName, currentParams);
    }

    public static HashMap<String, String> getNameValuePair(String parameterName, String delimiter){

        HashMap <String, String> res = new HashMap<String, String >();

        String unparsed = get(parameterName);
        String[] parsed = unparsed.split(delimiter);

        res.put("name", parsed[0].trim());
        res.put("value", parsed[1].trim());

        return res;
    }



    public static boolean isReportMethodTestsNested(){
        return getAsBool( (nestedTestMethodsParamName),true);
    }


    public static List<HashMap <String, String>> getListOfNameValuePairs(String parameterName, String valuesDelimiter, String nameValueDelimeter){

        List <HashMap <String, String>> res = new ArrayList<HashMap<String, String>>();

        try{
            String unparsed = get(parameterName);

            String[] parsed = unparsed.split(valuesDelimiter);

            for (int i = 0; i < parsed.length; i++) {
                parsed[i] = parsed[i].trim();

                String[] parsedNameValue = parsed[i].split(nameValueDelimeter);
                HashMap <String, String> nameValuePair = new HashMap <String, String> ();

                nameValuePair.put("name", parsedNameValue[0].trim());
                nameValuePair.put("value", parsedNameValue[1].trim());

                res.add(nameValuePair);
            }

        }catch (Exception e){
        }

        return res;
    }


    private synchronized static String getValueFromCache(String paramName){
        ITestNGMethod currentRunningMethod = TestHelper.getCurrentRunningMethod();

        if(currentRunningMethod == null)
            return null;

        String currentRunningMethodName = TestHelper.formCurrentMethodtName(currentRunningMethod);
        HashMap<String, String> currentParams = methodParams.get(currentRunningMethodName);

        if(currentParams != null){
            return currentParams.get(paramName);
        }

        return null;
    }








}
