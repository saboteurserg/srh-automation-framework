package com.krynytskyyserhiy.automation.framework.keywords;

import com.krynytskyyserhiy.automation.framework.common.LibHelper;
import com.krynytskyyserhiy.automation.framework.common.ResourceHelper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.*;

/**
 * Created by serhiy.krynytskyy on 11.01.2017.
 */
public class DB {

    public enum DRIVER_TYPE {
        ORACLE ("oracle.jdbc.driver.OracleDriver", "ojdbc7.jar");

        public String driverName_;
        public String resourceFilePath_;
        DRIVER_TYPE(String driverNmae, String resourceFilePath) {
            driverName_ = driverNmae;
            resourceFilePath_ = resourceFilePath;
        }
    }

    private String driverName;
    private String username;
    private String password;
    private String url;
    private Connection connection = null;
    private static DBConfig conf;


    public static JdbcTemplate getJdbcTemplate(){
        ResourceHelper.copyFile(DRIVER_TYPE.ORACLE.resourceFilePath_, DRIVER_TYPE.ORACLE.resourceFilePath_);
        LibHelper.loadLibraryFromFile("target/"+DRIVER_TYPE.ORACLE.resourceFilePath_);
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        try{
            dataSource.setDriverClass((Class<? extends Driver>) Class.forName("oracle.jdbc.driver.OracleDriver"));
        } catch (Exception e){
            Log.error(e.getMessage());
        }

        dataSource.setUrl(conf.getDatabaseUrl());
        dataSource.setUsername(conf.getDatabaseUser());
        dataSource.setPassword(conf.getDatabasePassword());
        return new JdbcTemplate(dataSource);
    }

    public static void setDbConfig(DBConfig config){
        DB.conf = config;
    }
    public static DBConfig getDbConfig(){
        return DB.conf;
    }
    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if(!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        }
        finally {
            if(source != null) {
                source.close();
            }
            if(destination != null) {
                destination.close();
            }
        }
    }

    public DB (DRIVER_TYPE driver_type, String serverName, int db_port, String SID, String username, String password) {

        this.username = username;
        this.password = password;

        switch (driver_type){
            case ORACLE:
                driverName = driver_type.driverName_;
                url = "jdbc:oracle:thin:@" + serverName +  ":" + db_port + ":" + SID;

                //copy file from resources to target folder (so it will be availble in derived projects)
                ResourceHelper.copyFile(driver_type.resourceFilePath_, driver_type.resourceFilePath_);

//                LibHelper.loadLibraryFromResources(driver_type.resourceFilePath_);
                LibHelper.loadLibraryFromFile("target/" + driver_type.resourceFilePath_);
                break;
        }
    }


    public boolean connect(){
        boolean res = false;
            try {
                if( connection==null || (connection!=null && connection.isClosed()) ) {
                    Class.forName(driverName);
                    connection = DriverManager.getConnection(url, username, password);
                    Log.debug("Successfully Connected to the database!");
                    res = true;
                }
            } catch (ClassNotFoundException e) {
                Log.error("Could not find the database driver");
                Log.printFailMessageWithStacktrace(e);
                res =false;
            } catch (SQLException e) {
                Log.error("Could not connect to the database");
                Log.printFailMessageWithStacktrace(e);
                res = false;
            }
            return res;
    }

    public boolean close(){
        try {
            if(connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            Log.error("Could not close connection to the database");
            Log.printFailMessageWithStacktrace(e);
            return false;
        }
        return true;
    }

    public ResultSet executeQuery(String query) throws SQLException {
        ResultSet res = null;
        connect();
        if(isConnected()) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            res = statement.executeQuery(query);
            System.out.println("Successfully executed query:  " + query);

        }
        return res;
    }

    /**
     *
     * @param query
     * @param resultSetType java.sql.ResultSet: TYPE_FORWARD_ONLY, TYPE_SCROLL_INSENSITIVE, TYPE_SCROLL_SENSITIVE
     * @param resultSetConcur java.sql.ResultSet: CONCUR_READ_ONLY, CONCUR_UPDATABLE
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery(String query, int resultSetType, int resultSetConcur ) throws SQLException {
        ResultSet res = null;
        connect();
        if(isConnected()) {
            Statement statement = null;
            statement = connection.createStatement();
            statement = connection.createStatement(resultSetType, resultSetConcur);
            res = statement.executeQuery(query);
            System.out.println("Successfully executed query:  " + query);
        }
        return res;
    }

    public int executeUpdate(String query) throws SQLException {
        int res = -1;
        connect();
        if(isConnected()) {
                Statement statement = connection.createStatement();
                res = statement.executeUpdate(query);
                System.out.println("Successfully executed query:  " + query);
        }
        return res;
    }


    private boolean isConnected(){
        try {
            return connection!=null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }



}
