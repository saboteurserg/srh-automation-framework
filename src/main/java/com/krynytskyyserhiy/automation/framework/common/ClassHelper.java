package com.krynytskyyserhiy.automation.framework.common;

/**
 * Created by serhiy.krynytskyy on 20.12.2016.
 */
public class ClassHelper {


    public static boolean isClassAChildOfClass(Class<?> child, Class<?> parent ){
        Class<?> currentClass = child.getSuperclass();

        while (currentClass != null){

            if(currentClass == parent)
                return true;

            currentClass = currentClass.getSuperclass();
        }

        return false;
    }






}
