package samples;

import org.testng.annotations.Test;
import samples.po.GoogleHomePage;

import static com.krynytskyyserhiy.automation.framework.keywords.Navigate.open;

public class Test7 {

    @Test
    public void test1() throws Exception {

        open("http://google.com");

        new GoogleHomePage().search_ResultsElements("Elvis");
    }


}
