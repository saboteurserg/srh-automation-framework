package com.krynytskyyserhiy.automation.framework.extentreports;


import com.krynytskyyserhiy.automation.framework.keywords.Parameter;
import com.krynytskyyserhiy.automation.framework.testng.TestHelper;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.testng.ITestClass;
import org.testng.ITestResult;

import java.util.*;

public class ExtentTestManager {

    static Map<Long, ExtentTest> extentTestMap = new HashMap<Long, ExtentTest>();
    static Map<String, ExtentReports> reportsMap = new HashMap<String, ExtentReports>();
    static Map<String, String> testExtentEquivalent = new HashMap<String, String>();

    static Map<String, ExtentTest> classLevelTests = new HashMap<String, ExtentTest>();


    public static final String DEFAULT_NAME = "default";

    private synchronized static ExtentReports getCurrentReportMap(String reportName){

        if(reportsMap.get(reportName) == null){
            if(reportName == DEFAULT_NAME){
                reportsMap.put(DEFAULT_NAME, new ExtentManager().getInstance());

            }else{
                reportsMap.put(reportName, new ExtentManager().setReportName(reportName).getInstance());
            }
        }
        return reportsMap.get(reportName);
    }

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get(Thread.currentThread().getId());
    }

    private static String findReportNameByTestName(String testName){
        String res = testExtentEquivalent.get(testName);
        return res;
    }

    public static synchronized void endTest() {
        endTest(true);
    }


    public static synchronized ExtentTest startClassLevelTest(ITestClass testClass, String desc) {

        if ( !Parameter.isReportMethodTestsNested() )
            return null;

//        String testName = testClass.getName();
        String testName = TestHelper.formTestAndClassName(testClass);
        ExtentTest test = getCurrentReportMap(DEFAULT_NAME).startTest(testName, desc);
        classLevelTests.put(testName, test);
        test.getTest().setStartedTime(new Date());

        return test;
    }

    public static synchronized void endClassLevelTest(ITestClass testClass) {

        if ( !Parameter.isReportMethodTestsNested() )
            return;

//        String testName = testClass.getName();
        String testName = TestHelper.formTestAndClassName(testClass);
        ExtentTest test = classLevelTests.get(testName);

        test.setEndedTime(new Date());
        getCurrentReportMap(DEFAULT_NAME).endTest(test);
        getCurrentReportMap(DEFAULT_NAME).flush();
    }


    public static synchronized void endTest(boolean isFlush) {
        getTest().getTest().setEndedTime(new Date());

        Long currentThreadId = Thread.currentThread().getId();
        String testName = getTest().getTest().getName();

        String reportName = findReportNameByTestName(testName);

        getCurrentReportMap(reportName).endTest(extentTestMap.get(currentThreadId));

        if (isFlush)
            getCurrentReportMap(reportName).flush();
    }


    public static synchronized ExtentTest startTest(String testName) {
        return startTest(testName, "");
    }

    public static synchronized ExtentTest startTest(ITestResult iTestResult) {

        String testName = TestHelper.formTestName(iTestResult);

        if (Parameter.isReportMethodTestsNested() )
            testName = TestHelper.formTestMethodName(iTestResult);

        return startTest(iTestResult, "", DEFAULT_NAME);
    }


    public static synchronized ExtentTest startTest(String testName, String desc) {
        return startTest(testName, desc, DEFAULT_NAME);
    }

    public static synchronized ExtentTest startTest(String testName, String desc, String reportName) {
        ExtentTest test = getCurrentReportMap(reportName).startTest(testName, desc);
        extentTestMap.put((Thread.currentThread().getId()), test);
        testExtentEquivalent.put(testName, reportName);
        test.getTest().setStartedTime(new Date());
        return test;
    }

    public static synchronized ExtentTest startTest(ITestResult iTestResult, String desc, String reportName) {

        String testName = TestHelper.formTestName(iTestResult);
        if (Parameter.isReportMethodTestsNested() )
            testName = TestHelper.formTestMethodName(iTestResult);

        ExtentTest test = getCurrentReportMap(reportName).startTest(testName, desc);
        extentTestMap.put((Thread.currentThread().getId()), test);
        testExtentEquivalent.put(testName, reportName);
        test.getTest().setStartedTime(new Date());

        if (Parameter.isReportMethodTestsNested() ){
//            String className = iTestResult.getTestClass().getName();
            String className = TestHelper.formTestAndClassName(iTestResult);
            classLevelTests.get(className).appendChild(test);
        }

        return test;
    }


    public static ExtentReports getExtent(){
        ExtentTest currentTest = getTest();
        String reportName = null;
        if(currentTest!=null){
            reportName = findReportNameByTestName(currentTest.getTest().getName());
        }

        if (reportName == null)
            reportName = DEFAULT_NAME;

        return getExtent(reportName);
    }

    public static ExtentReports getExtent(String reportName){
        return getCurrentReportMap(reportName);
    }

}