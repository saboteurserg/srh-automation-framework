package com.krynytskyyserhiy.automation.framework.testng;

import org.testng.*;

import java.util.HashMap;

/**
 * Created by serhiy.krynytskyy on 11.10.2016.
 */
public class TestHelper {

    static private HashMap<Long, ITestContext> tests = new HashMap<Long, ITestContext>();
    static private HashMap<Long, ITestNGMethod> currentRunningMethod = new HashMap<Long, ITestNGMethod>();
//    static private HashMap<Long, ISuite> currentSuite = new HashMap<Long, ISuite>();
    static private ISuite currentSuite;


    public synchronized static void addTest(ITestContext tc){
        tests.put(Thread.currentThread().getId(), tc);
    }

    public synchronized static ITestContext getTest(){
        return tests.get(Thread.currentThread().getId());
    }

    public synchronized static ISuite getCurrentSuite(){
//        return currentSuite.get(Thread.currentThread().getId());
        return currentSuite;
    }

    public synchronized static void setCurrentRunningMethod(ITestNGMethod currentRunningMethodName){
        currentRunningMethod.put(Thread.currentThread().getId(),currentRunningMethodName);
    }

    public synchronized static void setCurrentRunningSuite(ISuite iSuite){
//        currentSuite.put(Thread.currentThread().getId(),iSuite);
        currentSuite = iSuite;
    }

    public synchronized static ITestNGMethod getCurrentRunningMethod(){
        return currentRunningMethod.get(Thread.currentThread().getId());
    }


    public static String formTestName(ITestResult iTestResult){
        return iTestResult.getTestClass().getName() + "." + iTestResult.getMethod().getMethodName() + " ("  + iTestResult.getTestContext().getName() + ")";
    }

    public static String formTestAndClassName(ITestResult iTestResult){
        return iTestResult.getTestClass().getName() + " ("  + iTestResult.getTestContext().getName() + ")";
    }

    public static String formTestAndClassName(ITestClass testClass){
        return testClass.getName() + " ("  + testClass.getXmlTest().getName()+ ")";
    }

    public static String formTestMethodName(ITestResult iTestResult){
        return iTestResult.getMethod().getMethodName();
    }

//    public static String formClassName(ITestClass testClass){
//        return testClass.getName() + "." + iTestResult.getMethod().getMethodName() + " ("  + iTestResult.getTestContext().getName() + ")";
//    }


    public static String formCurrentMethodtName(ITestNGMethod iTestNGMethod){

        String res;

        if(iTestNGMethod.getXmlTest() == null)
            res = iTestNGMethod.getTestClass().getName() + "." + iTestNGMethod.getMethodName() + Thread.currentThread().getId();
        else
            res = iTestNGMethod.getTestClass().getName() + "." + iTestNGMethod.getMethodName() + " ("  + iTestNGMethod.getXmlTest().getName() + ")_" + Thread.currentThread().getId();

        return res;
    }




//    public static int getMethodHash(ITestResult tr){
//
//        String testClassName = tr.getTestClass().getName();
////        String testMethodName = iInvokedMethod.getTestMethod().getMethodName();
//        String currentThread = Thread.currentThread().getId()+"";
//
////        String res = testClassName + "." + testMethodName + "." + currentThread;
//        String res = testClassName + "." + currentThread;
//
//        return res.hashCode();
//
//    }





}
