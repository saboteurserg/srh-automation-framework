package com.krynytskyyserhiy.automation.framework.common;

import com.krynytskyyserhiy.automation.framework.keywords.Log;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by serhiy.krynytskyy on 11.01.2017.
 */
public class LibHelper {

    public static void loadLibraryFromResources(String resourceFileName){
        //dynamically loading ojdbc library
        Method method = null;
        ClassLoader classLoader = LibHelper.class.getClassLoader();
        File file = new File(classLoader.getResource(resourceFileName).getFile());
        try {
            method =URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            method.invoke(ClassLoader.getSystemClassLoader(), new Object[]{file.toURI().toURL()});

        } catch (NoSuchMethodException e) {
            Log.printFailMessageWithStacktrace(e);
        } catch (IllegalAccessException e) {
            Log.printFailMessageWithStacktrace(e);
        } catch (MalformedURLException e) {
            Log.printFailMessageWithStacktrace(e);
        } catch (InvocationTargetException e) {
            Log.printFailMessageWithStacktrace(e);
        }
    }

    public static void loadLibraryFromFile(String resourceFileName){
        //dynamically loading ojdbc library
        Method method = null;
        File file = new File(resourceFileName);
        try {
            method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            method.invoke(ClassLoader.getSystemClassLoader(), new Object[]{file.toURI().toURL()});

        } catch (NoSuchMethodException e) {
            Log.printFailMessageWithStacktrace(e);
        } catch (IllegalAccessException e) {
            Log.printFailMessageWithStacktrace(e);
        } catch (MalformedURLException e) {
            Log.printFailMessageWithStacktrace(e);
        } catch (InvocationTargetException e) {
            Log.printFailMessageWithStacktrace(e);
        }
    }


}
