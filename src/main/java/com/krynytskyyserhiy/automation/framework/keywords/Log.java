package com.krynytskyyserhiy.automation.framework.keywords;

import ch.qos.logback.classic.Level;
import com.krynytskyyserhiy.automation.framework.extentreports.ExtentHelper;
import com.krynytskyyserhiy.automation.framework.extentreports.ExtentTestManager;
import com.krynytskyyserhiy.automation.framework.html.HTMLHelper;
import com.krynytskyyserhiy.automation.framework.log.slf4j.LogHelper;
import com.krynytskyyserhiy.automation.framework.log.slf4j.LogManager;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.lang.StringEscapeUtils;

import java.net.MalformedURLException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.screenshot;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

/**
 * Created by serhiy.krynytskyy on 02.08.2016.
 */
public class Log {

    private static Level reportLogLevel = Level.INFO;
    private static Level[] levelSequence = {Level.ALL, Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR, Level.OFF};

    private static final String DEBUG_SPAN = "<span class='label debug'>DEBUG</span>";
    private static final String DEBUG_PARAGRAPH = "<p class='label debug'>DEBUG</p>";

    private static final String TRACE_SPAN = "<span class='label trace'>TRACE</span>";
    private static final String TRACE_PARAGRAPH = "<p class='label trace'>TRACE</p>";

    public static void setReportsLogLevel(Level logLevel){
        Log.reportLogLevel = logLevel;
    }

    public static Level getReportsLogLevel(){
        return reportLogLevel;
    }

    public static Level getFileLogLevel(){
        return LogManager.getInstance().getLevel();
    }

    public static void setLogFilesLogLevel(Level logLevel){
        LogManager.getInstance().setLevel(logLevel);
    }

    public static void log(String message) {
        LogManager.getInstance().info(HTMLHelper.html2text(message));

        if( isLogLevelGreatOrEqual(Level.INFO, reportLogLevel)) {
            ExtentTest test = ExtentTestManager.getTest();
            if (test != null) {
                test.log(LogStatus.INFO, escapeHtml(message) + "<br>");
            }
        }
    }


    public static void log(String message, Level logLevel) {

        Level tmp_level = reportLogLevel;
        setReportsLogLevel(logLevel);

        LogManager.getInstance().info(HTMLHelper.html2text(message));

        if( isLogLevelGreatOrEqual(logLevel, reportLogLevel)) {
            ExtentTest test = ExtentTestManager.getTest();
            if (test != null) {

                if(logLevel == Level.DEBUG)
                    message = DEBUG_PARAGRAPH + escapeHtml(message);

                test.log(LogStatus.INFO, message + "<br>");
            }
        }

        setReportsLogLevel(tmp_level);
        reportLogLevel = tmp_level;
    }


    public static void info(String message) {
        log(message);
    }

    public static void action(String message) {
        log(message);
    }

    public static void debug(final String message) {
        LogManager.getInstance().debug(HTMLHelper.html2text(message));

        if( isLogLevelGreatOrEqual(Level.DEBUG, reportLogLevel)) {
            ExtentTest test = ExtentTestManager.getTest();
            if (test != null) {
                test.log(LogStatus.INFO, "<pre>" + DEBUG_SPAN + escapeHtml(message) + "</pre>");
            }
        }
    }

    public static void trace(final String message) {
        Level tmp_level = reportLogLevel;
        setReportsLogLevel(Level.ALL);

        LogManager.getInstance().info("TRACE: " + HTMLHelper.html2text(message));

        if( isLogLevelGreatOrEqual(Level.INFO, reportLogLevel)) {
            ExtentTest test = ExtentTestManager.getTest();
            if (test != null) {
                test.log(LogStatus.INFO, "<pre>" + TRACE_SPAN + escapeHtml(message) + "</pre>");
            }
        }

        setReportsLogLevel(tmp_level);
        reportLogLevel = tmp_level;
    }

    public static void warn(final String message) {
        LogManager.getInstance().warn(HTMLHelper.html2text(message));

        if( isLogLevelGreatOrEqual(Level.WARN, reportLogLevel)) {
            ExtentTest test = ExtentTestManager.getTest();
            if (test != null) {
                test.log(LogStatus.WARNING, escapeHtml(message) + "<br>");
            }
        }

    }

    public static void error(String message) {
        LogManager.getInstance().error(HTMLHelper.html2text(message));

        if( isLogLevelGreatOrEqual(Level.ERROR, reportLogLevel)) {
            ExtentTest test = ExtentTestManager.getTest();
            if (test != null) {
                test.log(LogStatus.ERROR, escapeHtml(message) + "<br>");
            }
        }
    }

    public static void pass(String message) {
        LogManager.getInstance().info("[PASSED]: " + HTMLHelper.html2text(message));

        if( isLogLevelGreatOrEqual(Level.INFO, reportLogLevel)) {
            ExtentTest test = ExtentTestManager.getTest();
            if (test != null) {
                test.log(LogStatus.PASS, escapeHtml(message) + "<br>");
            }
        }
    }

    public static void fail(String message) {
        LogManager.getInstance().error("[FAILED]: " + HTMLHelper.html2text(message));

        if( isLogLevelGreatOrEqual(Level.ERROR, reportLogLevel)) {
            ExtentTest test = ExtentTestManager.getTest();
            if (test != null) {
                test.log(LogStatus.FAIL, escapeHtml(message) + "<br>");
            }
        }
    }


    public static void printFailMessageWithStacktrace (Throwable t){

        boolean screenshotOnFail = Parameter.getAsBool("screenshotOnFail", true);

        ExtentTest extentTest = ExtentTestManager.getTest();

        String errorScrenshotHtml = "";
        String errorScrenshotPath = "";
        if(WebDriverRunner.hasWebDriverStarted() && screenshotOnFail){
            String path = getScreenshot();
            errorScrenshotPath = "SCREENSHOT OF ERROR SCREEN is here: " + path + "\n";
            errorScrenshotHtml = "<br><br>Error screenshot:" + ExtentTestManager.getTest().addScreenCapture( path ) + "<br>";
        }
        String message = t.getLocalizedMessage() ;
        String stacktrace = ExtentHelper.getStackTrace(t) ;
        Integer trace_id = new Random().nextInt(Integer.MAX_VALUE-1);

        String errorHtml = "";
        if(message != null && !message.equals(""))
            errorHtml = "<pre id='"+trace_id+"'>"+ StringEscapeUtils.escapeHtml( message )+"</pre>";

        errorHtml = errorHtml + "<a id='link_"+trace_id+"' href='#' onclick='showstacktrace("+trace_id+")'>show stacktrace</a><div class='srh-stacktrace' id='trace_"+trace_id+"'><pre>"+ StringEscapeUtils.escapeHtml( stacktrace )+"</pre></div>" + errorScrenshotHtml;
        String errorLogString =  message+": \n" + errorScrenshotPath + stacktrace;

        if(t instanceof AssertionError) {
            if(extentTest != null){
                extentTest.log(LogStatus.FAIL, errorHtml);
                LogManager.getInstance().error("[FAILED]: " + errorLogString);
            }
                Log.log("----------------------- TEST FAILED -----------------------", Level.OFF);
        }

        else {
            if(extentTest != null){
                extentTest.log(LogStatus.ERROR, errorHtml);
                LogManager.getInstance().error("[ERROR]: " + errorLogString);
            }
            Log.log("----------------------- ERROR OCCURRED -----------------------", Level.OFF);
        }
    }


    public static void insertPicture( String message, String imagePath) {
        LogManager.getInstance().info(HTMLHelper.html2text(message) + " [ Image location: " + imagePath + " ]");

        if( isLogLevelGreatOrEqual(Level.INFO, reportLogLevel)) {
            ExtentTest test = ExtentTestManager.getTest();
            if (test != null) {
                test.log(LogStatus.INFO, escapeHtml(message) + ExtentTestManager.getTest().addScreenCapture(imagePath));
            }
        }
    }

    private static String generateRandomFileName(){
        return ((new Random()).nextInt((Integer.MAX_VALUE - 1) + 1) + 0) + "";
    }

    private static String getScreenshot(){
        String screenshot = screenshot(generateRandomFileName());

        if(screenshot!=null) {

            Pattern pattern = Pattern.compile(".+(?=target)");
            Matcher matcher = pattern.matcher(screenshot);
            screenshot = matcher.replaceAll("");

            try {
                screenshot = LogHelper.formFileOrUrlPath(screenshot);
            } catch (MalformedURLException e) {
                Log.warn("Error while getting URL of screenshot: " + e.getMessage());
            }

        }else {
            Log.warn("Cannot make screenshot :(");
        }

        return screenshot;
    }

    public static void makeScreenshot(){
        makeScreenshot("Screenshot:");
    }

    public static void makeScreenshot(String message){
        if(WebDriverRunner.hasWebDriverStarted())
            insertPicture(message,getScreenshot());
        else
            Log.info("(Screenshot is not made because webdriver has not been started)");

    }


    public static void makeScreenshotOfElement(SelenideElement $element){
        makeScreenshotOfElement($element, "Element Screenshot:");
    }


    public static void makeScreenshotOfElement(SelenideElement $element, String message){

        try {
            String fileURL = LogHelper.formFileOrUrlPath($element.screenshot().toString());
            insertPicture(message,fileURL);
        } catch (MalformedURLException e) {
            Log.warn("Error while getting URL of screenshot: " + e.getMessage());
        }
    }


    public static void insertLink( String text, String path) {
        LogManager.getInstance().info(HTMLHelper.html2text(text) + ": Link path: " + path);

        if( isLogLevelGreatOrEqual(Level.INFO, reportLogLevel)) {
            ExtentTest test = ExtentTestManager.getTest();
            if (test != null) {
                test.log(LogStatus.INFO, "<a target='_blank' href='" + path + "'>" + escapeHtml(text) + "</a>");
            }
        }
    }

    public static void insertCode(String code, Level level) {
        insertCode(code, null, level);
    }

    public static void insertCode(String code) {
        insertCode(code, null, Level.INFO);
    }


    public static void insertCode(String code, String message, Level level) {

        if( isLogLevelGreatOrEqual(level, LogManager.getInstance().getLevel())) {

            String text = "\n"+ code ;

            if(message != null && message!="")
                text = message + text;

            LogManager.getInstance().info(text);
        }

        if( isLogLevelGreatOrEqual(level, reportLogLevel)) {
            ExtentTest test = ExtentTestManager.getTest();
            if (test != null) {

                String text = "<pre>" + escapeHtml(code) + "</pre>";

                if(message != null && message!="")
                    text = "<p>" + escapeHtml(message) + "</p>" + text;

                if(level == Level.DEBUG)
                    text = DEBUG_PARAGRAPH + text;

                test.log(LogStatus.INFO,  text );
            }
        }
    }


    private static boolean isLogLevelGreatOrEqual(Level a, Level b){
        int level_a=-2;
        int level_b=-1;

        for(int i=0; i<levelSequence.length; i++){
            if(a==levelSequence[i])
                level_a = i;
            if(b==levelSequence[i])
                level_b = i;
        }

        if (level_a >= level_b)
            return true;

        return false;
    }



}
