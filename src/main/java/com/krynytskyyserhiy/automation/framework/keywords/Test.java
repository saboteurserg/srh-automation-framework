package com.krynytskyyserhiy.automation.framework.keywords;

import org.testng.SkipException;

/**
 * Created by serhiy.krynytskyy on 28.10.2016.
 */
public class Test {

    public static void skipTest(){
        throw new SkipException("Skipping test... ");
    }

    public static void skipTest(String message){
        throw new SkipException("Skipping test... " + message);
    }

}
