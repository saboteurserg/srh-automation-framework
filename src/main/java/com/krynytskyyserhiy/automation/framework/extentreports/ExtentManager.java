package com.krynytskyyserhiy.automation.framework.extentreports;

import com.krynytskyyserhiy.automation.framework.log.slf4j.LogHelper;
import com.relevantcodes.extentreports.ExtentReports;

import java.io.*;

public class ExtentManager {
    private ExtentReports extent;
//    private ITestContext context;
    public static final String MAIN_REPORT_NAME = "ExtentReport.html";
    public static String reportName = "ExtentReport.html";
//    public static final String reportDirectory = "target/extentreports";


    public static String getExtentReportsFolder(){
        return LogHelper.getLogFolder() + File.separator + "extentreports";
    }



    public ExtentManager setReportName(String reportName){
        this.reportName = reportName;
        return this;
    }

    public synchronized ExtentReports getInstance() {
        if (this.extent == null) {
            File outputDirectory = new File("target");
            File resultDirectory = new File(outputDirectory.getParentFile(), getExtentReportsFolder());
            extent = new ExtentReports(resultDirectory + File.separator + reportName, true);
//            extent.loadConfig(new File("src/main/resources/extentreportsconfig.xml"));

//            extent.loadConfig(new File("target/classes/extentreportsconfig.xml"));

            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("extentreportsconfig.xml");

            String tmpFilePath = getExtentReportsFolder() + "/extentreportsconfig.xml";

            try {
                this.convertingAnInputStreamToAFile(inputStream, tmpFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            extent.loadConfig(new File(tmpFilePath));


            //for multiple test executions
//            extent.startReporter(ReporterType.DB, "target/extentreports/results/extent.db");

        }

        return extent;
    }



    private void convertingAnInputStreamToAFile(InputStream initialStream, String targetFilePath) throws IOException {
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        File targetFile = new File(targetFilePath);
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);
    }

}