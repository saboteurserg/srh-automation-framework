package com.krynytskyyserhiy.automation.framework.common;

import org.apache.commons.io.FileUtils;

import java.io.*;


/**
 * Created by serhiy.krynytskyy on 11.01.2017.
 */
public class ResourceHelper {

    /**
     * Copies sourceFileName from resources folder to targetFileName under target folder.
     *
     * @param sourceFileName name of file to be copied. Should be located under resources folder
     * @param targetFileName name of destination file which then be available in target folder
     */
    public static void copyFile(String sourceFileName, String targetFileName)  {
        InputStream inputStream = ResourceHelper.class.getClassLoader().getResourceAsStream(sourceFileName);
        try {
            FileUtils.copyInputStreamToFile(inputStream, new File("target/"+targetFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
