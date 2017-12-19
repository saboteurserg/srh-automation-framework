package com.krynytskyyserhiy.automation.framework.keywords;

import static com.codeborne.selenide.Selenide.confirm;
import static com.codeborne.selenide.Selenide.dismiss;

/**
 * Created by serhiy.krynytskyy on 25.01.2017.
 */
public class Alert {

    public static void confirm_noFailing(){
        try {
            confirm();
        }catch (Exception e){
        }

    }


    public static void dismiss_noFailing(){
        try {
            dismiss();
        }catch (Exception e){
        }

    }


}
