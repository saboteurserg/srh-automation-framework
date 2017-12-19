package samples;

import static com.krynytskyyserhiy.automation.framework.keywords.SelenideSRH.$;

/**
 * Created by serhiy.krynytskyy on 20.12.2016.
 */
public class POTest2 extends POTest {

//    public static String $testSelector = "//*[@name='%s']";
    public static String $testSelector = "[name='%s'] %s";

    public void search(String searchTerm){
        $($testSelector,"par1", "par2").setValue(searchTerm).pressEnter();
    }


}
