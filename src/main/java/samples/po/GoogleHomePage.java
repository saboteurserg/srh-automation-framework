package samples.po;

import com.krynytskyyserhiy.automation.framework.keywords.Log;
import com.krynytskyyserhiy.automation.framework.pageobject.SRHPageObject;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;

import static com.krynytskyyserhiy.automation.framework.keywords.SelenideSRH.$;
import static com.krynytskyyserhiy.automation.framework.keywords.SelenideSRH.$$;

/**
 * Created by serhiy.krynytskyy on 11.05.2017.
 */
public class GoogleHomePage extends SRHPageObject {

    public static String searchField = "[name=q]";
    public static String resultLinks = ".r>a";
    public static String result_parameter = ".rc %s %s";


    public GoogleHomePage search_PrintResults(String searchString){
        $(GoogleHomePage.searchField)
                .val(searchString)
                .pressEnter();

        $(GoogleHomePage.resultLinks).waitUntil(Condition.appear,5*1000);

        ElementsCollection resultLinks = $$(GoogleHomePage.resultLinks).filter(Condition.visible);

        for (int i = 0; i < resultLinks.size(); i++){
            Log.info( resultLinks.get(i).text() );
        }

        return this;
    }

    public GoogleHomePage search_ResultsElements(String searchString){
        $(GoogleHomePage.searchField)
                .val(searchString)
                .pressEnter();

        $(GoogleHomePage.result_parameter, "h3", "a").waitUntil(Condition.appear,5*1000);

        ElementsCollection resultLinks = $$(GoogleHomePage.result_parameter, "h3", "a").filter(Condition.visible);

        for (int i = 0; i < resultLinks.size(); i++){
            Log.info( resultLinks.get(i).text() );
        }

        return this;
    }

}
