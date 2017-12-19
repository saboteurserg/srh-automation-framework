package com.krynytskyyserhiy.automation.framework.keywords;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by serhiy.krynytskyy on 25.11.2016.
 */
public class SSH {

    private Session session;
    private Channel channel;
    private String host;
    private boolean showOutput_inconsole = true;

    private String lastCommand;
    private String result = "";


    public SSH showOutputInConsole(boolean showOutput_inconsole){
        this.showOutput_inconsole = showOutput_inconsole;
        return this;
    }


    public SSH connect(String host, String user, String password) throws JSchException {

        this.host = host;

//        try {

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            Log.info("Connected to " + host);
//        } catch (Exception e) {
////            e.printStackTrace();
//            Log.printFailMessageWithStacktrace(e);
//        }

        return this;
    }


    public SSH execute(String command) throws JSchException, IOException {

        lastCommand = command;

//        try {
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp = new byte[1024];

            result = "";

            if(showOutput_inconsole)
               Log.info( "Executing command: " + command);
            else
                Log.info( "Executing command (output is off): " + command);

            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;

                    String piece = new String(tmp, 0, i);
                    result += piece;

                    if(showOutput_inconsole){
                        //print out in console only
                        System.out.print(piece);
//                        Log.info(piece);
                    }

                }
                if (channel.isClosed()) {
//                    Log.info("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                    Log.printFailMessageWithStacktrace(ee);
                }
            }

            Log.info( "Command execution finished. ("+command+")");

//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.printFailMessageWithStacktrace(e);
//        }

        return this;

    }

    public SSH printLastResultOutput(){
        if(!result.equals("")) {
            result = "----------------\n" +
                    "Command executed: " + lastCommand + "\n" +
                    "----------------\n" +
                    "Result output:\n" +
                    "----------------\n"+
                    result;
            Log.insertCode("\n" + result);
        }
        else {
            result += "Command executed (output is off): " + lastCommand;
            Log.info(result);
        }

        return this;
    }


    public void disconnect(){
        channel.disconnect();
        session.disconnect();
        Log.info("Disconnected from " + host);
    }




}
