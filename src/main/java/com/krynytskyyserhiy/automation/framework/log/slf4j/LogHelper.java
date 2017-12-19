package com.krynytskyyserhiy.automation.framework.log.slf4j;

import com.krynytskyyserhiy.automation.framework.html.HTMLHelper;
import com.krynytskyyserhiy.automation.framework.jenkins.JenkinsHelper;
import com.krynytskyyserhiy.automation.framework.keywords.Parameter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by serhiy.krynytskyy on 07.09.2016.
 */
public class LogHelper {

//    public static String LOG_DIRECTORY = "target/logs";
    public static String LOG_DIRECTORY;
    public static final String SEPARATE_LOG_DIRECTORY = "separate";
    public static final String FILE_EXTENSION = ".log";
    public static final String OTHER_LOG_NAME = "other";
    public static final String MAIN_LOG_NAME = "main";

//    public static String PATH_TO_ROOT = "";
//    public static final String PATH_TO_ROOT = "../../";


    private static String getPathToRoot(){
        if (JenkinsHelper.isJenkins()){
            return JenkinsHelper.getWorkspaceURL();
        }else{
            return "";
        }
    }

    public static String formFileOrUrlPath(String relativePath) throws MalformedURLException {
        if(JenkinsHelper.isJenkins()){
            return JenkinsHelper.getWorkspaceURL() + relativePath;
        }else{
            return new File(relativePath).toURI().toURL().toString();
        }
    }


    public static String getSeparateMethodLogLink(String fullMethodName) throws IOException {
        return formFileOrUrlPath(getLogFolder()+"/"+SEPARATE_LOG_DIRECTORY+"/"+fullMethodName+FILE_EXTENSION);
    }

    public static String getSeparateMethodLogLinkUrl(String fullMethodName) throws IOException {
        return HTMLHelper.formHyperlink(fullMethodName+FILE_EXTENSION, getSeparateMethodLogLink(fullMethodName), "window.event.stopPropagation();" );
    }

    public static String getOtherLogPath() throws IOException {
        return formFileOrUrlPath(getLogFolder()+"/"+SEPARATE_LOG_DIRECTORY+"/"+OTHER_LOG_NAME+FILE_EXTENSION);
    }

    public static String getOtherLogUrl() throws IOException {
        return HTMLHelper.formHyperlink(OTHER_LOG_NAME+FILE_EXTENSION, getOtherLogPath());
    }

    public static String getMainLogPath() throws IOException {
        return formFileOrUrlPath(getLogFolder()+"/"+MAIN_LOG_NAME+FILE_EXTENSION);

    }

    public static String getMainLogUrl() throws IOException {
        return HTMLHelper.formHyperlink(MAIN_LOG_NAME+FILE_EXTENSION, getMainLogPath());
    }

    public static String getSeparateDirectoryPath() throws IOException {
        return formFileOrUrlPath(getLogFolder()+"/"+SEPARATE_LOG_DIRECTORY);
    }


    public static String getCurrentRunName(){
        String reportFolderRunName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        reportFolderRunName = reportFolderRunName.replaceAll("\\D","_");
        if(JenkinsHelper.isJenkins()){
            reportFolderRunName = "build_" + JenkinsHelper.getBuildNumber();
        }

        return reportFolderRunName;
    }

    public static String getLogFolder(){

        if(LOG_DIRECTORY!=null)
            return LOG_DIRECTORY;

        String logDirectory = getTopLogDirectory();

        logDirectory += File.separator + getCurrentRunName();

        LOG_DIRECTORY = logDirectory;

        return logDirectory;
    }


    public static String getTopLogDirectory(){
        return  Parameter.getAsString("logDirectory", "target/logs");
    }



}
