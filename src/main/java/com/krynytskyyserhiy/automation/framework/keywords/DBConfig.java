package com.krynytskyyserhiy.automation.framework.keywords;

/**
 * Created by Orest Bashmat on 13.04.2017.
 */
public class DBConfig {
    private static final String URL = "jdbc:oracle:thin:@//%s:%s/%s";

    /***
     * Database url for specific driver
     * e.g for oracle it is
     * @return
     */

    protected String databaseUrl;
    protected String databaseUser;
    protected String databasePassword;
    protected DB.DRIVER_TYPE driverType;


    public DBConfig(DB.DRIVER_TYPE driverType){
        this.driverType = driverType;
        databaseUrl = System.getProperty("db_url");
        databaseUser = System.getProperty("db_user");
        databasePassword = System.getProperty("db_password");
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabaseUrl() {

        return databaseUrl;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public DBConfig setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
        return this;
    }

    public DBConfig setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
        return this;
    }

    public DBConfig setDatabaseUrl(String host, String port, String sid) {
        switch (this.driverType){
            case ORACLE:
                setOracleUrl(host, port, sid);
        }
        return this;
    }

    public DBConfig setDatabaseUrl(String url){
        databaseUrl = url;
        return this;
    }

    private void setOracleUrl(String host, String port, String sid){
        databaseUrl = String.format(URL, host, port, sid);
    }
}