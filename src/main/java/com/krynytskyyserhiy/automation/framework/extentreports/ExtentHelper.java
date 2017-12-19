package com.krynytskyyserhiy.automation.framework.extentreports;

import com.relevantcodes.extentreports.model.ITest;
import org.testng.IInvokedMethod;
import org.testng.ISuite;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by serhiy.krynytskyy on 10.08.2016.
 */
public class ExtentHelper {

    public static String printAssertionError(Throwable t){

        String message = t.getLocalizedMessage();
        String stacktrace = getStackTrace(t);

        Integer trace_id = new Random().nextInt(Integer.MAX_VALUE-1);

        return "<div id='"+trace_id+"'>"+message+"</div><a id='link_"+trace_id+"' href='#' onclick='showstacktrace("+trace_id+")'>show stacktrace</a><div class='srh-stacktrace' id='trace_"+trace_id+"'><pre>"+stacktrace+"</pre></div>";
    }


    public static String getStackTrace(Throwable t) {
        if (t == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    public static String getExceptionHeadline(Throwable t) {
        Pattern pattern = Pattern.compile("([\\w\\.]+)(:.*)?");
        String stackTrace = getStackTrace(t);
        Matcher matcher = pattern.matcher(stackTrace);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static void attachLinksToReport(ISuite iSuite){

        List<IInvokedMethod> invokedMethods = iSuite.getAllInvokedMethods();

        for (int i = 0; i < invokedMethods.size(); i++) {
            IInvokedMethod method = invokedMethods.get(i);
            ExtentTestManager.getExtent().setTestRunnerOutput("Main log can be found here:");
            ExtentTestManager.getExtent().setTestRunnerOutput(method.getTestResult().getTestClass().getName() + "." + method.getTestMethod().getMethodName());
        }

    }


    public static void addLineToTestDescription(ITest test, String newLine){
        String description = test.getDescription();
        if( description == null)
            description = "";

        if(description != "")
            description += "</br>";

        description += newLine;
        test.setDescription(description);
    }


}
