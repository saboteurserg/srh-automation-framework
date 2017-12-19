package com.krynytskyyserhiy.automation.framework.html;

import org.jsoup.Jsoup;

/**
 * Created by serhiy.krynytskyy on 07.09.2016.
 */
public class HTMLHelper {

    public static String formHyperlink(String linkText, String path){
        return "<a href='"+path+"' target='_blank'>"+linkText+"</a>";
    }

    public static String formHyperlink(String linkText, String path, String onclick){
        return "<a href='"+path+"' target='_blank' onclick='" + onclick + "'>"+linkText+"</a>";
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}
