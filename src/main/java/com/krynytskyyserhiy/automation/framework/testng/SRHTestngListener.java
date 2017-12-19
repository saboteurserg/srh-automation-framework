package com.krynytskyyserhiy.automation.framework.testng;

import ch.qos.logback.classic.Level;
import com.krynytskyyserhiy.automation.framework.extentreports.ExtentHelper;
import com.krynytskyyserhiy.automation.framework.extentreports.ExtentManager;
import com.krynytskyyserhiy.automation.framework.extentreports.ExtentTestManager;
import com.krynytskyyserhiy.automation.framework.html.HTMLHelper;
import com.krynytskyyserhiy.automation.framework.keywords.Log;
import com.krynytskyyserhiy.automation.framework.keywords.Parameter;
import com.krynytskyyserhiy.automation.framework.log.slf4j.LogHelper;
import com.krynytskyyserhiy.automation.framework.log.slf4j.MDCHelper;
import com.krynytskyyserhiy.automation.framework.selenide.SRHSelenideLogListener;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.krynytskyyserhiy.automation.framework.extentreports.ExtentManager.getExtentReportsFolder;
import static com.krynytskyyserhiy.automation.framework.extentreports.ExtentTestManager.endClassLevelTest;
import static com.krynytskyyserhiy.automation.framework.extentreports.ExtentTestManager.startClassLevelTest;
import static com.krynytskyyserhiy.automation.framework.log.slf4j.LogHelper.getLogFolder;

/**
 * Created by serhiy.krynytskyy on 03.08.2016.
 */
public class SRHTestngListener implements ITestListener, IExecutionListener, IInvokedMethodListener, ISuiteListener, IClassListener  {

    public static final String DEFAULT_BROWSER = "chrome";

    private static final String BEFORE_REPORT_NAME = "before.html";
    private static final String AFTER_REPORT_NAME = "after.html";
    private long startTime;
    private static boolean reportSectionFilled = false;

    public void onExecutionStart() {
        startTime = System.currentTimeMillis();
    }

    public void onExecutionFinish() {
        Log.log("############################################################################################################################", Level.OFF);
        Log.log("# TestNG has FINISHED, took around " + (System.currentTimeMillis() - startTime) + "ms. #", Level.OFF);
        Log.log("############################################################################################################################", Level.OFF);

        //create copy of ExtentReport files for current run\build
        copyReportForCurrentRun();
    }


    private void copyReportForCurrentRun(){
        String extentReportFile = getExtentReportsFolder() + File.separator + ExtentManager.MAIN_REPORT_NAME;
        String destinationFile = LogHelper.getTopLogDirectory() + File.separator + ExtentManager.MAIN_REPORT_NAME;
        try {
            org.apache.commons.io.FileUtils.copyFile(new File(extentReportFile), new File(destinationFile));
        } catch (IOException e) {
            Log.printFailMessageWithStacktrace(e);
        }
    }



    public void onStart(ITestContext iTestContext) {

    }

    public void onFinish(ITestContext testContext){
    }


    public void onTestStart(ITestResult iTestResult){

    }


    public void onTestFailure(ITestResult result) {
        Log.printFailMessageWithStacktrace(result.getThrowable());
        endTest();
    }

    public void onTestSkipped(ITestResult tr) {

        String s1 = ExtentTestManager.getTest().getTest().getName();
        String s2 = TestHelper.formTestName(tr);

        //if different names - test s1 was already finished
        if( !s1.equals(s2) ) {
            String testName = TestHelper.formTestName(tr);

            if (isBeforeConfiguration(tr.getMethod()))
//                ExtentTestManager.startTest(testName, "", BEFORE_REPORT_NAME);
                ExtentTestManager.startTest(tr, "", BEFORE_REPORT_NAME);

            else if (isAfterConfiguration(tr.getMethod()))
//                ExtentTestManager.startTest(testName, "", AFTER_REPORT_NAME);
                ExtentTestManager.startTest(tr, "", AFTER_REPORT_NAME);
            else {
//                ExtentTestManager.startTest(testName);
                ExtentTestManager.startTest(tr);
            }

            addTestInfoToTestDescription(tr);

            Log.log("----------------------- TEST NOT EVEN STARTED - SKIPPED -----------------------", Level.OFF);

        }else {

//            if(Parameter.getAsBool("screenshotOnPass",false))
//                Log.makeScreenshot();

            Log.log("----------------------- TEST SKIPPED -----------------------", Level.OFF);
        }


        ExtentTestManager.getTest().getTest().setStatus(LogStatus.SKIP);
        endTest();
    }

    public void onTestSuccess(ITestResult tr) {

        if(Parameter.getAsBool("screenshotOnPass",false))
            Log.makeScreenshot();

        Log.log("----------------------- TEST PASSED -----------------------", Level.OFF);
        endTest();
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        ExtentTestManager.getTest().getTest().setEndedTime(new Date());
        Log.log("---------- TEST FAILED BUT WITH SUCCESS PERCENTAGE ---------", Level.OFF);
        endTest();
    }


    public synchronized void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

        TestHelper.addTest(iTestResult.getTestContext());
        TestHelper.setCurrentRunningMethod(iInvokedMethod.getTestMethod());

        String testName = TestHelper.formTestName(iTestResult);

        if(isBeforeConfiguration(iInvokedMethod.getTestMethod()))
//            ExtentTestManager.startTest(testName,"", BEFORE_REPORT_NAME);
            ExtentTestManager.startTest(iTestResult,"", BEFORE_REPORT_NAME);
        else if(isAfterConfiguration(iInvokedMethod.getTestMethod()))
//            ExtentTestManager.startTest(testName,"", AFTER_REPORT_NAME );
            ExtentTestManager.startTest(iTestResult,"", AFTER_REPORT_NAME );
        else {
//            ExtentTestManager.startTest(testName);
            ExtentTestManager.startTest(iTestResult);
        }

        MDCHelper.startSeparateFileLogging(testName);


        Log.log("----------------------- TEST STARTED (" +testName+ ")-----------------------", Level.OFF);

        addTestInfoToTestDescription(iTestResult);

        if(Parameter.getAsBool("trace"))
            SelenideLogger.addListener("srhtrace", new SRHSelenideLogListener());

    }

    private void addTestInfoToTestDescription(ITestResult iTestResult){

        //assign groups as categories in report
        String groups[] = iTestResult.getMethod().getGroups();
        ExtentTestManager.getTest().assignCategory(groups);

        //set description
        if(iTestResult.getMethod().getDescription() != null)
            ExtentTestManager.getTest().setDescription("<h5>Description:</h5>" + iTestResult.getMethod().getDescription());


        //add link to log file
        try {
            ExtentHelper.addLineToTestDescription(ExtentTestManager.getTest().getTest(),
                    "Link to log file: " + LogHelper.getSeparateMethodLogLinkUrl(TestHelper.formTestName(iTestResult)));
        } catch (IOException e) {
            Log.warn("Couldn't get file path: " + e.getMessage());
        }

//        ExtentHelper.addLineToTestDescription(ExtentTestManager.getTest().getTest(),
//                "<div>Show\\hide trace records: " + "<a href=\"#\" class='show_hide_trace_records' onclick='showHideTraceRecords(this);'>HIDE TRACE RECORDS</a>&nbsp;&nbsp; \\ &nbsp;&nbsp;<a href=\"#\" class='dim_brighten_trace_records' onclick='dimmBrightenTraceRecords(this);'>DIMM TRACE RECORDS</a></div>" );

//        if(Parameter.isReportMethodTestsNested()){
//            ExtentHelper.addLineToTestDescription(ExtentTestManager.getTest().getTest(),
//                    "<div>Show\\hide before\\after methods: " + "<a href=\"#\" class='show_hide_before_after_methods' onclick='showHideBeforeAfterMethods(this);'>HIDE Before\\After methods</a></div>" );
//        }

        if( iTestResult.getMethod().isTest() ){
            ExtentTestManager.getTest().assignCategory("TESTS");
        }

        if( isBeforeConfiguration(iTestResult.getMethod())) {
            ExtentTestManager.getTest().assignCategory("BEFORE");

            ExtentHelper.addLineToTestDescription(ExtentTestManager.getTest().getTest(),
                    "<div style='display:none;' class='before_after_invocation'></div>" );
        }


        if( isAfterConfiguration(iTestResult.getMethod())) {
            ExtentTestManager.getTest().assignCategory("AFTER");

            ExtentHelper.addLineToTestDescription(ExtentTestManager.getTest().getTest(),
                    "<div style='display:none;' class='before_after_invocation'></div>" );
        }


        String dependentOn[] = iTestResult.getMethod().getMethodsDependedUpon();
        if(dependentOn.length>0){
            String dependsonMethodString = dependentOn[0];
            for(int i=1; i<dependentOn.length; i++)
                dependsonMethodString += ", " +dependentOn[i];
            ExtentHelper.addLineToTestDescription(ExtentTestManager.getTest().getTest(), "Depends on Methods: " + dependsonMethodString );
        }

        String dependentOnGroups[] = iTestResult.getMethod().getGroupsDependedUpon();
        if(dependentOnGroups.length>0){
            String dependsonGroupsString = dependentOnGroups[0];
            for(int i=1; i<dependentOnGroups.length; i++)
                dependsonGroupsString += ", " +dependentOnGroups[i];
            ExtentHelper.addLineToTestDescription(ExtentTestManager.getTest().getTest(), "Depends on Groups: " + dependsonGroupsString );
        }
    }


    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if( ! iInvokedMethod.getTestMethod().isTest() ){
            if(!iTestResult.isSuccess()){
                Log.printFailMessageWithStacktrace(iTestResult.getThrowable());
            }else{
                Log.log("----------------------- TEST FINISHED -----------------------", Level.OFF);
            }

            endTest();

        }
    }

    public void onStart(ISuite iSuite) {
        TestHelper.setCurrentRunningSuite(iSuite);

        System.setProperty("logback.log_directory", getLogFolder());

        //set default log level
        Log.setReportsLogLevel(Level.INFO);
        Log.setLogFilesLogLevel(Level.INFO);

        Log.log(" ");
        Log.log("############################################################################################################################", Level.OFF);
        Log.log("#                                             Execution STARTED                                                            #", Level.OFF);
        Log.log("############################################################################################################################", Level.OFF);
        Log.log(" ");



        fillReportSectionOnce();

        readConfig();

        //change some default some Selenide config
        Configuration.screenshots = false;
        Configuration.savePageSource = false;
        Configuration.reportsFolder = LogHelper.getLogFolder() + File.separator + "screenshots";
    }

    public void onFinish(ISuite iSuite) {
        ExtentHelper.attachLinksToReport(iSuite);
//        SelenideLogger.removeListener("srhtrace");
    }



    public void onBeforeClass(ITestClass testClass) {
        startClassLevelTest(testClass,"");
    }

    public void onAfterClass(ITestClass testClass) {
        endClassLevelTest(testClass);
    }


    private boolean isBeforeConfiguration(ITestNGMethod testMethod){
        if(     testMethod.isBeforeTestConfiguration()      ||
                testMethod.isBeforeMethodConfiguration()    ||
                testMethod.isBeforeGroupsConfiguration()    ||
                testMethod.isBeforeClassConfiguration()     ||
                testMethod.isBeforeSuiteConfiguration()     )
        {
            return true;
        }

        return false;
    }

    private boolean isAfterConfiguration(ITestNGMethod testMethod){
        if(     testMethod.isAfterTestConfiguration()      ||
                testMethod.isAfterMethodConfiguration()    ||
                testMethod.isAfterGroupsConfiguration()    ||
                testMethod.isAfterClassConfiguration()     ||
                testMethod.isAfterSuiteConfiguration()     )
        {
            return true;
        }

        return false;
    }


    private void endTest(){
        ExtentTestManager.endTest();
        MDCHelper.finishSeparateFileLogging();
    }

    private static void fillReportSectionOnce(){
        if(!reportSectionFilled){
            String logOutput = "";
            try {
                logOutput += "Buid number: <div id='build_number_info'>"+LogHelper.getCurrentRunName()+"</div>";
                logOutput += "<h5>Logs:</h5>";
                logOutput += "Main log file: " + LogHelper.getMainLogUrl();
                logOutput += "</br>";
                logOutput += "Logs for separate methods: " + HTMLHelper.formHyperlink("could be found here", LogHelper.getSeparateDirectoryPath());
                logOutput += "</br>";
                logOutput += "Separate log, with messages not related to tests: " + LogHelper.getOtherLogUrl();
                logOutput += "</br>";
                logOutput += "<h5>Other reports:</h5>";
                logOutput += "Report to all @Before tests (if any): " + HTMLHelper.formHyperlink(BEFORE_REPORT_NAME, LogHelper.formFileOrUrlPath(getExtentReportsFolder())+"/"+BEFORE_REPORT_NAME);
                logOutput += "</br>";
                logOutput += "Report to all @After tests (if any): " + HTMLHelper.formHyperlink(AFTER_REPORT_NAME, LogHelper.formFileOrUrlPath(getExtentReportsFolder())+"/"+AFTER_REPORT_NAME);
                logOutput += "</br>";
                logOutput += "Reports for previous runs\\builds: " + HTMLHelper.formHyperlink("could be found here", LogHelper.formFileOrUrlPath(getExtentReportsFolder()));
            } catch (IOException e) {
                Log.warn("Couldn't get file path: " + e.getMessage());
            }

            ExtentTestManager.getExtent(ExtentTestManager.DEFAULT_NAME).setTestRunnerOutput(logOutput);
            reportSectionFilled = true;
        }
    }


    private synchronized static void readConfig(){
        Configuration.browser = Parameter.get("browser", DEFAULT_BROWSER);

        if(Parameter.get("browserVersion")!=null)
            Configuration.browserVersion = Parameter.get("browserVersion");

        if(Parameter.get("remote")!=null)
            Configuration.remote = Parameter.get("remote");

        //get system variables from parameters and set them
        List<HashMap<String,String>> globalVariables = Parameter.getListOfNameValuePairs("globalVariables", ";", "=");
        for (int i = 0; i < globalVariables.size(); i++) {
            System.setProperty(globalVariables.get(i).get("name"), globalVariables.get(i).get("value"));
        }

        if(Parameter.get("reportLogLevel")!=null){
            String reportLogLevel = Parameter.get("reportLogLevel").toLowerCase();

            if(reportLogLevel.equals("all")){
                Log.setReportsLogLevel(Level.ALL);
            }else if(reportLogLevel.equals("debug")){
                Log.setReportsLogLevel(Level.DEBUG);
            }else if(reportLogLevel.equals("info")){
                Log.setReportsLogLevel(Level.INFO);
            }else if(reportLogLevel.equals("warn")){
                Log.setReportsLogLevel(Level.WARN);
            }else if(reportLogLevel.equals("error")){
                Log.setReportsLogLevel(Level.ERROR);
            }else if(reportLogLevel.equals("off")){
                Log.setReportsLogLevel(Level.OFF);
            }
        }

        if(Parameter.get("fileLogLevel")!=null){
            String fileLogLevel = Parameter.get("fileLogLevel").toLowerCase();

            if(fileLogLevel.equals("all")){
                Log.setLogFilesLogLevel(Level.ALL);
            }else if(fileLogLevel.equals("debug")){
                Log.setLogFilesLogLevel(Level.DEBUG);
            }else if(fileLogLevel.equals("info")){
                Log.setLogFilesLogLevel(Level.INFO);
            }else if(fileLogLevel.equals("warn")){
                Log.setLogFilesLogLevel(Level.WARN);
            }else if(fileLogLevel.equals("error")){
                Log.setLogFilesLogLevel(Level.ERROR);
            }else if(fileLogLevel.equals("off")){
                Log.setLogFilesLogLevel(Level.OFF);
            }
        }

    }




}
