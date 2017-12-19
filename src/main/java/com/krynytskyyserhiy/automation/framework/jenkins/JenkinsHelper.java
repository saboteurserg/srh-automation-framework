package com.krynytskyyserhiy.automation.framework.jenkins;

/**
 * Created by serhiy.krynytskyy on 27.10.2016.
 */
public class JenkinsHelper {

    public final static String WORKSPACE_FOLDER = "ws";

    public static boolean isJenkins(){
        if(getJobURL() == null)
            return false;

        return true;
    }

    public static String getJobURL(){
        return System.getenv("JOB_URL");
    }

    public static String getBuildNumber(){
        return System.getenv("BUILD_NUMBER");
    }

    public static String getWorkspaceURL(){
        return getJobURL() + WORKSPACE_FOLDER + "/";
    }


}
