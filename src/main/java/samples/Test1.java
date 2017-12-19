package samples;

import ch.qos.logback.classic.Level;
import com.krynytskyyserhiy.automation.framework.keywords.Log;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups= "selenium-test")
public class Test1 {


    @BeforeClass
    public void beforeTest222(){
        Log.log("before Test222 method message");

        Log.info("BEFORE TEST");
        assertThat("aaa").contains("a");
    }


    @Test
    public void main2(){
        Log.info("main2");
        Log.insertLink("Links are here", "../logs/");
        Log.debug("123456787345678");
        Log.log("main2 method message");
        assertThat("aaa").contains("b");
        int a = 1;
        assertThat(a).isNotNull();
    }


    @Test
    public void main3(){

//        Log.setReportsLogLevel(Level.DEBUG);


        Log.log("main3", Level.DEBUG);
        Log.warn("main2 method message");

        for (int i=0; i<10; i++){
            Log.info("i = " + i);
        }

        String code = "int = 1;\n"+
                "a+2; funcction = 1231234; {asdjfjalsdhljasdjk}\n"+
                "bla bla bla";


        Log.insertCode(code, Level.DEBUG);

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat("aaa").contains("a");
        softly.assertThat("aaa").isNotEmpty();
        softly.assertThat(false).isTrue();
        softly.assertThat("aaa").contains("e");
        softly.assertThat("aaa").contains("a");
        softly.assertAll();
    }


    @Test(groups = {"Main page", "Login"})
    public void main4(){
        Log.info("main4");
    }

    @AfterClass
    public void after1(){
        Log.info("after1");
    }

}
